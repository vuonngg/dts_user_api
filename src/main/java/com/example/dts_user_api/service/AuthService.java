package com.example.dts_user_api.service;

import com.example.dts_user_api.dto.AuthResponse;
import com.example.dts_user_api.entity.User;
import com.example.dts_user_api.dto.UserLoginDto;
import com.example.dts_user_api.dto.UserSigupDto;
import com.example.dts_user_api.repositorys.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    public AuthResponse login(UserLoginDto userLoginDto) {


        User user = userRepository.findByUsername(userLoginDto.getUsername()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Sai tài khoản hoặc mật khẩu"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tài khoản hoặc mật khẩu chưa đúng");
        }

        String token = generateToken(userLoginDto.getUsername());

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    private String generateToken(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        String role;
        if(user.get().getRole() == false){
            role = "ROLE_USER";
        }else {
            role = "ROLE_ADMIN";
        }

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("dts-test")
                .issueTime(new Date())
                .claim("role",role)
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Không thể tạo token",e);
            throw new RuntimeException(e);
        }
    }

    public String createUser(UserSigupDto user) {
        String username = user.getUsername();
        String email = user.getEmail();

        Optional<User> optionalUsername = userRepository.findByUsername(username);
        Optional<User> optionalEmail = userRepository.findByEmail(email);

        if (optionalUsername.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vui lòng đổi username khác");
        } else if (optionalEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email đã được sử dụng");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User user1 = new User();
        user1.setName(user.getName());
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail());
        user1.setPhone(user.getPhone());
        user1.setAvatar(user.getAvatar());
        user1.setIsDeleted(false);
        user1.setStatus(true);
        user1.setRole(false);

        userRepository.save(user1);
        return "Tạo tài khoản thành công";
    }


}

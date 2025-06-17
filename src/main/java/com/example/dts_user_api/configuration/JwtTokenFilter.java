package com.example.dts_user_api.configuration;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @PostConstruct
    public void init() {
        System.out.println("SIGNER_KEY: " + SIGNER_KEY);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // bỏ "Bearer "

            try {
                JWSObject jwsObject = JWSObject.parse(token);
                boolean isVerified = jwsObject.verify(new MACVerifier(SIGNER_KEY.getBytes()));
                if (isVerified) {
                    JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());

                    String username = claimsSet.getSubject();
                    String role = (String) claimsSet.getClaim("role");

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority(role))
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        filterChain.doFilter(request, response);
    }
}

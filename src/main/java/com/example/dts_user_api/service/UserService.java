package com.example.dts_user_api.service;

import com.example.dts_user_api.entity.User;
import com.example.dts_user_api.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAllByIsDeletedFalse();
    }

    public String deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Người dùng không tồn tại."
            );
        }
        User user = userOptional.get();
        user.setIsDeleted(true);
        userRepository.save(user);
        return "Người dùng đã được xóa thành công.";
    }

}

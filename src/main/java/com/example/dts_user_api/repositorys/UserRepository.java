package com.example.dts_user_api.repositorys;

import com.example.dts_user_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByIsDeletedFalse();

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
package com.example.dts_user_api.repositorys;

import com.example.dts_user_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
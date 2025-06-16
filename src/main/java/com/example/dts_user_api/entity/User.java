package com.example.dts_user_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull(message = "Tên không được để trống")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 50)
    @NotNull(message = "Username không được để trống")
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Size(max = 255)
    @NotNull(message = "Mật khẩu không được để trống")
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 100)
    @NotNull(message = "không được để trống email")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 10)
    @Column(name = "phone", length = 10)
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
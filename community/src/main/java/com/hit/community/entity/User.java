package com.hit.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String studentId;
    private String password;
    private String email;

    @Builder
    public User(String userName, String studentId, String password, String email) {
        this.userName = userName;
        this.studentId = studentId;
        this.password = password;
        this.email = email;
    }
}

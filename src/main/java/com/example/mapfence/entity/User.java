package com.example.mapfence.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String password;
    private String telephone;
    private int role_id;
}

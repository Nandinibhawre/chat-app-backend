package com.spring.chatApp.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStatusDTO {

    private boolean online;

    private String lastSeen;
}
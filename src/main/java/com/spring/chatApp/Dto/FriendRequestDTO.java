package com.spring.chatApp.Dto;

import com.spring.chatApp.Model.User;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDTO
{
    private String id;
    private String status;
    private User sender;
}

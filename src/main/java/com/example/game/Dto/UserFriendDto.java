package com.example.game.Dto;

import com.example.game.Entity.User;
import lombok.Data;

import java.util.List;
@Data
public class UserFriendDto {
    private Long id;
    private List<User> friends;
    private List<User> requests;
}

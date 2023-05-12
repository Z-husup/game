package com.example.game.Controller;

import com.example.game.Dto.UserDto;
import com.example.game.Entity.User;
import com.example.game.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<UserDto>> getFriends(@PathVariable Long userId) {
        List<UserDto> friends = userService.getAllFriends(userId);
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/{userId}/friend-requests")
    public ResponseEntity<List<UserDto>> getFriendRequests(@PathVariable Long userId) {
        List<UserDto> friendRequests = userService.getAllFriendRequests(userId);
        return ResponseEntity.ok(friendRequests);
    }

    @PostMapping("/{userId}/friend-requests")
    public ResponseEntity<Void> sendFriendRequest(@PathVariable Long userId, @RequestBody User targetUser) {
        userService.sendFriendRequest(userId, targetUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{userId}/friend-requests/{requestId}")
    public ResponseEntity<Void> acceptFriendRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        userService.acceptFriendRequest(userId, requestId);
        return ResponseEntity.ok().build();
    }
}

//    @Autowired
//    private final UserRepository userRepository;
//
//    @PostMapping("/create")//create user
//    public User newUser(@RequestBody NewUserDto dto){
//        return userService.create(dto);
//    }
//
//    @GetMapping("/{id}/friend-requests")//get list of requests
//    public List<User> getRequests(@PathVariable Long id){
//        return  userRepository.getById(id).getRequests();
//    }
//
//    @GetMapping("/{id}/friends")//get list of friends
//    public List<User> getFriends(@PathVariable Long id){
//        return  userRepository.getById(id).getFriends();
//    }
//
//    @PutMapping("/{userId}/friends/{friendId}")
//    public User addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
//        return userService.addFriendRequest(userId, friendId);
//    }
//
//    @PutMapping("/{userId}/friend-requests/{friendId}")
//    public User acceptFriendRequest(@PathVariable Long userId, @PathVariable Long friendId) {
//        return userService.acceptFriendRequest(userId, friendId);
//    }



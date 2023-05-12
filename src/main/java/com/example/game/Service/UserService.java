package com.example.game.Service;

import com.example.game.Dto.UserDto;
import com.example.game.Entity.User;
import com.example.game.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements InterfaceUserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<UserDto> getAllFriends(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));
        return user.getFriends().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getAllFriendRequests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));
        return user.getFriendRequests().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void sendFriendRequest(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid friend id: " + friendId));
        user.getFriendRequests().add(friend);
        userRepository.save(user);
    }

    public void acceptFriendRequest(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid friend id: " + friendId));
        user.getFriends().add(friend);
        user.getFriendRequests().remove(friend);
        userRepository.save(user);
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        return userDto;
    }
}





//@Service
//@AllArgsConstructor
//public class UserService {
//    private final UserRepository userRepository;
//
//    public User create(NewUserDto dto) {
//        return userRepository.save(
//                User.builder()
//                        .username(dto.getName())
//                        .build());
//    }
//
//    public User update(User user) {
//        return userRepository.save(user);
//    }
//
//    public User addFriendRequest(Long senderId, Long receiverId) {
//        User sender = userRepository.findById(senderId).orElse(null);
//        User receiver = userRepository.findById(receiverId).orElse(null);
//        if (sender != null && receiver != null) {
//            receiver.getRequests().add(sender);
//            userRepository.save(receiver);
//        }
//        return receiver;
//    }
//
//
//
//    public User acceptFriendRequest(Long userId, Long friendId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
//        User friend = userRepository.findById(friendId).orElseThrow(() -> new EntityNotFoundException("Friend not found"));
//        if (user.getRequests().contains(friend)) {
//            user.getRequests().remove(friend);
//            user.getFriends().add(friend);
//            friend.getFriends().add(user);
//            userRepository.save(user);
//            userRepository.save(friend);
//        }
//        return user;
//    }
//
//}

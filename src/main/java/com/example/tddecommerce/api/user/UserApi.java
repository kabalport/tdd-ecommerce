package com.example.tddecommerce.api.user;

import com.example.tddecommerce.domain.user.application.UserService;
import com.example.tddecommerce.api.user.dto.CreateUserRequest;
import com.example.tddecommerce.api.user.dto.CreateUserResponse;
import com.example.tddecommerce.api.user.dto.GetUserResponse;
import com.example.tddecommerce.api.user.dto.UpdateUserNameRequest;
import com.example.tddecommerce.domain.user.business.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserApi {

    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        User user = userService.addUser(request);
        CreateUserResponse response = new CreateUserResponse(user.getUserId(),user.getName(), user.getEmail());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        GetUserResponse response = new GetUserResponse(user.getUserId(),user.getName(),user.getEmail());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{userId}/name")
    public ResponseEntity<Void> updateUserName(@PathVariable Long userId, @RequestBody @Valid UpdateUserNameRequest request) {
        userService.updateUserName(userId, request.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

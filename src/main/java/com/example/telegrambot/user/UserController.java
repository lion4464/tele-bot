package com.example.telegrambot.user;

import com.example.telegrambot.exceptions.DataNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
public class UserController {
    private final UserService userService;
    private final Usermapper usermapper;

    public UserController(UserService userService, Usermapper usermapper) {
        this.userService = userService;
        this.usermapper = usermapper;
    }


    @GetMapping("/all")
    public ResponseEntity<List<HashMap<String,Object>>> all(){
    return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> changeStatus(@PathVariable("id") UUID id,
     @Valid @RequestBody UserRequest request) throws DataNotFoundException {
        UserEntity model = userService.changeStatus(id, request);
        return ResponseEntity.ok(usermapper.toDto(model));
    }





}

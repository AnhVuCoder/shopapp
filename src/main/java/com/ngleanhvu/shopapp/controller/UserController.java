package com.ngleanhvu.shopapp.controller;

import com.ngleanhvu.shopapp.dto.UserDTO;
import com.ngleanhvu.shopapp.dto.UserLoginDTO;
import com.ngleanhvu.shopapp.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService iUserService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO,
                                        BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword()))
                return ResponseEntity.badRequest().body("Password and Retype Password must be same");
            return ResponseEntity.ok().body(iUserService.createUser(userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        String token = iUserService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
        return ResponseEntity.ok("Some tokens");
    }
}

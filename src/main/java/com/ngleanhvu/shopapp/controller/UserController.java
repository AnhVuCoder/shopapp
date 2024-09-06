package com.ngleanhvu.shopapp.controller;

import com.ngleanhvu.shopapp.component.JwtTokenUtil;
import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.UpdatedUserDTO;
import com.ngleanhvu.shopapp.dto.UserDTO;
import com.ngleanhvu.shopapp.dto.UserLoginDTO;
import com.ngleanhvu.shopapp.response.LoginResponse;
import com.ngleanhvu.shopapp.response.UserResponse;
import com.ngleanhvu.shopapp.service.IUserService;
import com.ngleanhvu.shopapp.util.LocalizationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private LocalizationUtils localizationUtils;
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
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO,
                                    HttpServletRequest request) {
        try {

            String token = iUserService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword(), userLoginDTO.getRole_id() == null ? 1 : userLoginDTO.getRole_id());
            return ResponseEntity.ok().body(LoginResponse.builder()
                    .message(localizationUtils.getLocalizationUtils(Constant.LOGIN_SUCCESSFULLY))
                    .token(token)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(LoginResponse.builder()
                    .message(localizationUtils.getLocalizationUtils(Constant.LOGIN_FAILED, e.getMessage()))
                    .build());
        }
    }
    @PostMapping("/details")
    public ResponseEntity<?> getUserDetailsFromToken(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
        String extractToken = authorizationHeader.substring(7);
        try{
            return ResponseEntity.ok().body(iUserService.getUserDetailsFromToken(extractToken));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/details/{userId}")
    public ResponseEntity<?> updateUserDetails(@PathVariable Integer userId,
                                               @RequestBody UpdatedUserDTO updatedUserDTO,
                                               @RequestHeader("Authorization") String authorizationHeader) throws Exception {
       try{
           String token = authorizationHeader.substring(7);
           UserResponse user = iUserService.getUserDetailsFromToken(token);
           if(user == null || !Objects.equals(user.getId(), userId)){
               return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
           }
           UserResponse updatedUser = iUserService.updateUser(updatedUserDTO, userId);
           return ResponseEntity.ok().body(updatedUser);
       } catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
}

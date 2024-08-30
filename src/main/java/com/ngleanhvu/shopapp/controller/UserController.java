package com.ngleanhvu.shopapp.controller;

import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.UserDTO;
import com.ngleanhvu.shopapp.dto.UserLoginDTO;
import com.ngleanhvu.shopapp.response.LoginResponse;
import com.ngleanhvu.shopapp.service.IUserService;
import com.ngleanhvu.shopapp.util.LocalizationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;
import java.util.Locale;

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
}

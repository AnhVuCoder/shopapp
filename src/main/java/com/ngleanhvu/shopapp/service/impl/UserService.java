package com.ngleanhvu.shopapp.service.impl;

import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.UserDTO;
import com.ngleanhvu.shopapp.entity.Role;
import com.ngleanhvu.shopapp.entity.User;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;
import com.ngleanhvu.shopapp.repo.IRoleRepo;
import com.ngleanhvu.shopapp.repo.IUserRepo;
import com.ngleanhvu.shopapp.service.IUserService;
import com.ngleanhvu.shopapp.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final PasswordEncoder passwordEncoder;
    private final IUserRepo iUserRepo;
    private final ModelMapper modelMapper;
    private final IRoleRepo iRoleRepo;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public UserDTO createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        if (iUserRepo.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException(Constant.PHONE_NUMBER_EXISTS);
        }
        Role role = iRoleRepo.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException(Constant.ROLE_NOT_FOUND));
        String ps = userDTO.getPassword();
        if(userDTO.getFacebookAccountId() == null && userDTO.getGoogleAccountId()  == null){
            ps = passwordEncoder.encode(ps);
        }
        User user = User.builder()
                .fullName(userDTO.getFullName())
                .address(userDTO.getAddress())
                .password(ps)
                .dateOfBirth(userDTO.getDateOfBirth())
                .phoneNumber(userDTO.getPhoneNumber())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .active(true)
                .role(role)
                .build();
        iUserRepo.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public String login(String phoneNumber, String password) throws DataNotFoundException {
        Optional<User> optionalUser = iUserRepo.findUserByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()) throw new DataNotFoundException("Invalid phone number or password");
        User user = optionalUser.get();
        // authenticate java spring
        if(user.getFacebookAccountId() == null && user.getGoogleAccountId()  == null){
           if(!passwordEncoder.matches(password, user.getPassword()))
               throw new BadCredentialsException("Wrong phone number or password");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user.getPhoneNumber(), user.getPassword()
        );
        authenticationManager.authenticate(authentication);
        return jwtTokenUtil.generationToken(user);
    }
}

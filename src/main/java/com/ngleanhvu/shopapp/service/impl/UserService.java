package com.ngleanhvu.shopapp.service.impl;

import com.ngleanhvu.shopapp.component.JwtTokenUtil;
import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.UserDTO;
import com.ngleanhvu.shopapp.entity.Role;
import com.ngleanhvu.shopapp.entity.User;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;
import com.ngleanhvu.shopapp.repo.IRoleRepo;
import com.ngleanhvu.shopapp.repo.IUserRepo;
import com.ngleanhvu.shopapp.service.IUserService;
import com.ngleanhvu.shopapp.util.LocalizationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserRepo iUserRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IRoleRepo iRoleRepo;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LocalizationUtils localizationUtils;
    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        if (iUserRepo.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException(Constant.PHONE_NUMBER_EXISTS);
        }
        Role role = iRoleRepo.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException(Constant.ROLE_NOT_FOUND));
        String ps = userDTO.getPassword();
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
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
    @Transactional
    public String login(String phoneNumber, String password, Integer roleId) throws Exception {

        Optional<User> optionalUser = iUserRepo.findUserByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()) throw new DataNotFoundException("Invalid phone number or password");
        User user = optionalUser.get();
        // authenticate java spring
        if (user.getFacebookAccountId() == 0 && user.getGoogleAccountId() == 0) {
           if(!passwordEncoder.matches(password, user.getPassword()))
               throw new BadCredentialsException("Wrong phone number or password");
        }
        Optional<Role> optionalRole = iRoleRepo.findById(roleId);
        if(optionalRole.isEmpty() || !roleId.equals(user.getRole().getId())){
            throw new BadCredentialsException(localizationUtils.getLocalizationUtils(Constant.ROLE_NOT_EXIST, roleId));
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                phoneNumber, password, user.getAuthorities()
        );
        authenticationManager.authenticate(authentication);
        return jwtTokenUtil.generateToken(user);
    }
}

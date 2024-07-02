package com.ngleanhvu.shopapp.service.impl;

import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.UserDTO;
import com.ngleanhvu.shopapp.entity.Role;
import com.ngleanhvu.shopapp.entity.User;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;
import com.ngleanhvu.shopapp.repo.IRoleRepo;
import com.ngleanhvu.shopapp.repo.IUserRepo;
import com.ngleanhvu.shopapp.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepo iUserRepo;
    private final ModelMapper modelMapper;
    private final IRoleRepo iRoleRepo;

    @Override
    public UserDTO createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        if (iUserRepo.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException(Constant.PHONE_NUMBER_EXISTS);
        }
        Role role = iRoleRepo.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException(Constant.ROLE_NOT_FOUND));
        User user = User.builder()
                .fullName(userDTO.getFullName())
                .address(userDTO.getAddress())
                .password(userDTO.getPassword())
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
    public String login(String phoneNumber, String password) {
        return null;
    }
}

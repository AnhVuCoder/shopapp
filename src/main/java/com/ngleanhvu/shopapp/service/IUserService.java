package com.ngleanhvu.shopapp.service;

import com.ngleanhvu.shopapp.dto.UpdatedUserDTO;
import com.ngleanhvu.shopapp.dto.UserDTO;
import com.ngleanhvu.shopapp.entity.User;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;
import com.ngleanhvu.shopapp.response.UserResponse;

public interface IUserService {
    UserDTO createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(String phoneNumber, String password, Integer roleId) throws Exception;
    UserResponse getUserDetailsFromToken(String extractToken) throws Exception;
    UserResponse updateUser(UpdatedUserDTO updateUserDTO, Integer userId) throws Exception;
}

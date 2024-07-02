package com.ngleanhvu.shopapp.service;

import com.ngleanhvu.shopapp.dto.UserDTO;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;

public interface IUserService {
    UserDTO createUser(UserDTO userDTO) throws DataNotFoundException;

    String login(String phoneNumber, String password);
}

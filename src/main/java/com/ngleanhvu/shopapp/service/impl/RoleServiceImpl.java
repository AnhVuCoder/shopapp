package com.ngleanhvu.shopapp.service.impl;

import com.ngleanhvu.shopapp.entity.Role;
import com.ngleanhvu.shopapp.repo.IRoleRepo;
import com.ngleanhvu.shopapp.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepo iRoleRepo;
    @Override
    public List<Role> getAllRoles() {
        return iRoleRepo.findAll();
    }
}

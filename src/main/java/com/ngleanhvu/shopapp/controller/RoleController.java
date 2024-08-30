package com.ngleanhvu.shopapp.controller;

import com.ngleanhvu.shopapp.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/roles")
public class RoleController {
    private final IRoleService iRoleService;
    @GetMapping
    public ResponseEntity<?> getAllRoles(){
        return ResponseEntity.ok().body(iRoleService.getAllRoles());
    }
}

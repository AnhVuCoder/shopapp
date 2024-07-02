package com.ngleanhvu.shopapp.repo;

import com.ngleanhvu.shopapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepo extends JpaRepository<Role, Integer> {
}

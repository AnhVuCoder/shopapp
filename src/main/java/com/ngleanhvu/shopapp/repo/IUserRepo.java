package com.ngleanhvu.shopapp.repo;

import com.ngleanhvu.shopapp.dto.UserDTO;
import com.ngleanhvu.shopapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserRepo extends JpaRepository<User, Integer> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
            "FROM User u " +
            "WHERE u.phoneNumber = :phoneNumber"
    )
    boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT u FROM User u " +
            "WHERE u.phoneNumber = :phoneNumber"
    )
    Optional<UserDTO> findUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}

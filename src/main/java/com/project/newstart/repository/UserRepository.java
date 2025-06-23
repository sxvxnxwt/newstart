package com.project.newstart.repository;

import com.project.newstart.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);

    @Query(
            value="SELECT * FROM UserEntity WHERE id=:id",
            nativeQuery = true
    )
    UserEntity findByUserId(@Param("id") Long id);

}

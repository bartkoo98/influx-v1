package com.bartkoo98.influxv1.user.repository;

import com.bartkoo98.influxv1.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}

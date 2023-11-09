package com.ppn.mock.backendmockppn.repository;

import com.ppn.mock.backendmockppn.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}

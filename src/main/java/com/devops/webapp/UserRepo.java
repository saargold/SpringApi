package com.devops.webapp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {


    // You can add custom query methods or use the ones provided by JpaRepository
}

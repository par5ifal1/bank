package com.example.bankBackend.repository;

import com.example.bankBackend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UsersRepository extends JpaRepository<Users,Long> {
}

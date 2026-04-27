package com.taskflow.api.repository;

import com.taskflow.api.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // O JpaRepository<User, Long> significa:
    // User: A entidade que este repositório cuida
    // Long: O tipo do ID (Chave Primária) daquela entidade
}

package com.e_commerce.back_SemiSenior.repository;

import com.e_commerce.back_SemiSenior.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);
    boolean existsByEmail(String email);

}

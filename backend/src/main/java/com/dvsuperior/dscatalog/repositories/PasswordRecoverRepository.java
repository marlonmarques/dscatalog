package com.dvsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvsuperior.dscatalog.entities.PasswordRecover;

public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover, Long> {

}

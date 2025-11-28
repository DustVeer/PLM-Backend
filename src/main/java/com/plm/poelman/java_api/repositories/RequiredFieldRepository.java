package com.plm.poelman.java_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plm.poelman.java_api.models.RequiredField;

@Repository
public interface RequiredFieldRepository extends JpaRepository<RequiredField, Long> {
}

package com.plm.poelman.java_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.plm.poelman.java_api.models.ProductStatus;

@Repository
public interface ProductStatusRepository extends JpaRepository<ProductStatus, Long> {
    List<ProductStatus> findByActive(Integer active);
    Optional<ProductStatus> findByName(String name);
    Optional<ProductStatus> findBySortOrder(Long sortOrder);
}

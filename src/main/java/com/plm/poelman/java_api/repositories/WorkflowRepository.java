package com.plm.poelman.java_api.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.plm.poelman.java_api.models.Workflow;

import java.util.List;
import java.util.Optional;

public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

    @EntityGraph(attributePaths = {
            "createdBy",
            "updatedBy",
            "workflowStatuses",
            "workflowStatuses.status"    
    })
    @Query("select distinct w from Workflow w")
    List<Workflow> findAllWithStatuses();

    @EntityGraph(attributePaths = {
            "createdBy",
            "updatedBy",
            "workflowStatuses",
            "workflowStatuses.status"    
    })
    @Query("select distinct w from Workflow w where w.id = :id")
    Optional<Workflow> findByIdWithStatuses(Long id);
}

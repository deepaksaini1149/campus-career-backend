package com.campuscareer.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campuscareer.backend.entity.Opportunity;

public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

    List<Opportunity> findByType(String type);

    List<Opportunity> findByTitleContainingIgnoreCase(String title);
}
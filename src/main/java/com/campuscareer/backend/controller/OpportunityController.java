package com.campuscareer.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.campuscareer.backend.entity.Opportunity;
import com.campuscareer.backend.repository.OpportunityRepository;

@RestController
@RequestMapping("/api/opportunities")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "https://campus-career-frontend.onrender.com"
})
public class OpportunityController {

    private final OpportunityRepository opportunityRepository;

    public OpportunityController(
            OpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }

    @PostMapping
    public Opportunity addOpportunity(
            @RequestBody Opportunity opportunity) {
        opportunity.setId(null);
        return opportunityRepository.save(opportunity);
    }

    @GetMapping
    public List<Opportunity> getAllOpportunities() {
        return opportunityRepository.findAll();
    }

    @GetMapping("/{id}")
    public Opportunity getOpportunityById(@PathVariable Long id) {
        return opportunityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Opportunity not found"));
    }

    @GetMapping("/type/{type}")
    public List<Opportunity> getByType(@PathVariable String type) {
        return opportunityRepository.findByType(type);
    }

    @PutMapping("/{id}")
    public Opportunity updateOpportunity(
            @PathVariable Long id,
            @RequestBody Opportunity updatedOpportunity) {

        Opportunity existing = opportunityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Opportunity not found"));

        existing.setTitle(updatedOpportunity.getTitle());
        existing.setType(updatedOpportunity.getType());
        existing.setOrganization(updatedOpportunity.getOrganization());
        existing.setLocation(updatedOpportunity.getLocation());
        existing.setMode(updatedOpportunity.getMode());
        existing.setDuration(updatedOpportunity.getDuration());
        existing.setEligibility(updatedOpportunity.getEligibility());
        existing.setDeadline(updatedOpportunity.getDeadline());
        existing.setDescription(updatedOpportunity.getDescription());

        return opportunityRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOpportunity(@PathVariable Long id) {
        if (!opportunityRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Opportunity not found");
        }

        opportunityRepository.deleteById(id);
    }
}
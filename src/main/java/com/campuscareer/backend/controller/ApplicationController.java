
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

import com.campuscareer.backend.entity.Application;
import com.campuscareer.backend.repository.ApplicationRepository;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:5173")
public class ApplicationController {

    private final ApplicationRepository applicationRepository;

    public ApplicationController(
            ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    // CREATE: Save a new application
    @PostMapping
    public Application createApplication(
            @RequestBody Application application) {
        application.setId(null);
        return applicationRepository.save(application);
    }

    // READ: Get all applications
    @GetMapping
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // READ: Get one application by ID
    @GetMapping("/{id}")
    public Application getApplicationById(@PathVariable Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Application not found"));
    }

    // UPDATE: Update an existing application
    @PutMapping("/{id}")
    public Application updateApplication(
            @PathVariable Long id,
            @RequestBody Application updatedApplication) {

        Application existing = applicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Application not found"));

        existing.setOpportunityId(updatedApplication.getOpportunityId());
        existing.setName(updatedApplication.getName());
        existing.setEmail(updatedApplication.getEmail());
        existing.setPhone(updatedApplication.getPhone());
        existing.setCollege(updatedApplication.getCollege());
        existing.setBranch(updatedApplication.getBranch());
        existing.setYear(updatedApplication.getYear());
        existing.setResume(updatedApplication.getResume());
        existing.setCoverLetter(updatedApplication.getCoverLetter());

        return applicationRepository.save(existing);
    }

    // DELETE: Delete an application
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteApplication(@PathVariable Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Application not found");
        }

        applicationRepository.deleteById(id);
    }
}

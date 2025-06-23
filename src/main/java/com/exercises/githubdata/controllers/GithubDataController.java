package com.exercises.githubdata.controllers;

import com.exercises.githubdata.models.GithubData;
import com.exercises.githubdata.services.GithubDataService;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class GithubDataController {

    @Autowired
    GithubDataService githubDataService;

    @GetMapping("/api/v1/{githubUsername}")
    public ResponseEntity getGithubData(@PathVariable String githubUsername) {
        try {
            return ResponseEntity.ok(githubDataService.getGithubData(githubUsername));

        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }

}

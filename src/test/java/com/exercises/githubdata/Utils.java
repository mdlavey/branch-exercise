package com.exercises.githubdata;

import com.exercises.githubdata.repositories.GithubDataRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    @Autowired
    private GithubDataRedisRepository repository;

    public void cleanup() {
        repository.deleteAll();
    }
}

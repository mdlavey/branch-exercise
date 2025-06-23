package com.exercises.githubdata.client;

import com.exercises.githubdata.client.models.GithubRepo;
import com.exercises.githubdata.client.models.GetUser;
import jakarta.servlet.ServletException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class GithubClient {

    private static final Logger log = Logger.getLogger(GithubClient.class);
    @Autowired
    RestClient githubRestClient;

    public GetUser getUser(String username) throws HttpClientErrorException {
        ResponseEntity<GetUser> getUserResponse = githubRestClient.get().uri("users/" + username).retrieve().toEntity(GetUser.class);
        return getUserResponse.getBody();
    }

    public GithubRepo[] getRepos(String username) throws HttpClientErrorException {
        ResponseEntity<GithubRepo[]> getReposResponse = githubRestClient.get().uri("users/" + username + "/repos").retrieve().toEntity(GithubRepo[].class);
        return getReposResponse.getBody();
    }
}

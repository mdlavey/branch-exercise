package com.exercises.githubdata.services;

import com.exercises.githubdata.client.GithubClient;
import com.exercises.githubdata.client.models.GithubRepo;
import com.exercises.githubdata.client.models.GetUser;
import com.exercises.githubdata.entities.GithubData;
import com.exercises.githubdata.entities.Repo;
import com.exercises.githubdata.repositories.GithubDataRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GithubDataService {

    @Autowired
    GithubClient githubClient;

    @Autowired
    GithubDataRedisRepository repository;

    public GithubData getGithubData(String username) throws HttpClientErrorException {
        Optional<GithubData> cacheEntry = repository.findById(username);
        if(cacheEntry.isPresent()) {
            return cacheEntry.get();
        }

        GithubRepo[] getRepos = githubClient.getRepos(username);
        GetUser getUser = githubClient.getUser(username);

        if(getUser == null) {
            return null;
        }

        List<Repo> reposToReturn = new ArrayList<>();
        if(getRepos != null) {

            for (GithubRepo repo : getRepos) {
                Repo repoToReturn = new Repo();
                repoToReturn.setName(repo.getName());
                repoToReturn.setUrl(repo.getHtmlUrl());
                reposToReturn.add(repoToReturn);
            }
        }

        GithubData githubData = getGithubData(getUser, reposToReturn);
        repository.save(githubData);
        return githubData;
    }

    private GithubData getGithubData(GetUser getUser, List<Repo> reposToReturn) {
        GithubData githubData = new GithubData();
        githubData.setAvatar(getUser.getAvatarUrl());
        githubData.setCreatedAt(getUser.getCreatedAt());
        githubData.setDisplayName(getUser.getName());
        githubData.setEmail(getUser.getEmail());
        githubData.setUrl(getUser.getHtmlUrl());
        githubData.setUsername(getUser.getLogin());

        githubData.setRepos(reposToReturn);
        return githubData;
    }
}

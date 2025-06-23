package com.exercises.githubdata.services;

import com.exercises.githubdata.client.GithubClient;
import com.exercises.githubdata.client.models.GithubRepo;
import com.exercises.githubdata.client.models.GetUser;
import com.exercises.githubdata.models.GithubData;
import com.exercises.githubdata.models.Repo;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubDataService {

    @Autowired
    GithubClient githubClient;

    public GithubData getGithubData(String username) throws HttpClientErrorException {
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

        return getGithubData(getUser, reposToReturn);
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

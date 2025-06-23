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

        List<Repo> reposToReturn = new ArrayList<>();

        for(GithubRepo repo : getRepos) {
            Repo repoToReturn = new Repo();
            repoToReturn.setName(repo.getName());
            repoToReturn.setUrl(repo.getHtml_url());
            reposToReturn.add(repoToReturn);
        }

        return getGithubData(getUser, getRepos, reposToReturn);
    }

    private static GithubData getGithubData(GetUser getUser, GithubRepo[] getRepos, List<Repo> reposToReturn) {
        GithubData githubData = new GithubData();
        githubData.setAvatar(getUser.getAvatar_url());
        githubData.setCreatedAt(getUser.getCreated_at());
        githubData.setDisplayName(getUser.getName());
        githubData.setEmail(getUser.getEmail());
        githubData.setUrl(getUser.getHtml_url());
        githubData.setUsername(getUser.getLogin());

        Repo[] reposArray = new Repo[getRepos.length];
        reposArray = reposToReturn.toArray(reposArray);
        githubData.setRepos(reposArray);
        return githubData;
    }
}

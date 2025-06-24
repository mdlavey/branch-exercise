package com.exercises.githubdata.services;

import com.exercises.githubdata.client.GithubClient;
import com.exercises.githubdata.client.models.GetUser;
import com.exercises.githubdata.client.models.GithubRepo;
import com.exercises.githubdata.entities.GithubData;
import com.exercises.githubdata.entities.Repo;
import com.exercises.githubdata.repositories.GithubDataRedisRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
public class GithubDataServiceTest {

    @MockitoBean
    private GithubClient client;

    @MockitoBean
    private GithubDataRedisRepository repository;

    @Autowired
    private GithubDataService service;

    @Test
    public void successWithoutCache() {
        String email = "octocat@example.com";
        String username = "octocat";
        String testUrl = "testUrl";
        String repoName = "test repo";
        GetUser user = new GetUser();
        user.setAvatarUrl(null);
        user.setEmail(email);
        user.setName(username);
        user.setLogin(username);
        Mockito.when(client.getUser(username)).thenReturn(user);
        GithubRepo repo = new GithubRepo();
        repo.setHtmlUrl(testUrl);
        repo.setName(repoName);
        GithubRepo[] repos = new GithubRepo[] {repo};
        Mockito.when(client.getRepos(username)).thenReturn(repos);
        Mockito.when(repository.findById(username)).thenReturn(Optional.empty());

        GithubData githubData = service.getGithubData(username);
        Assertions.assertNull(githubData.getAvatar());
        Assertions.assertEquals(email, githubData.getEmail());
        Assertions.assertEquals(username, githubData.getDisplayName());
        Assertions.assertEquals(username, githubData.getUsername());
        Assertions.assertEquals(1, githubData.getRepos().size());
        Assertions.assertEquals(testUrl, githubData.getRepos().getFirst().getUrl());
        Assertions.assertEquals(repoName, githubData.getRepos().getFirst().getName());
    }

    @Test
    public void successWithCache() {
        String email = "octocat@example.com";
        String username = "octocat";
        String testUrl = "testUrl";
        String repoName = "test repo";
        GithubData githubData = new GithubData();
        githubData.setDisplayName(username);
        githubData.setUsername(username);
        githubData.setEmail(email);
        List<Repo> repos = new ArrayList<>();
        Repo repo = new Repo();
        repo.setName(repoName);
        repo.setUrl(testUrl);
        repos.add(repo);
        githubData.setRepos(repos);

        Mockito.verify(client, Mockito.never()).getUser(username);

        Mockito.verify(client, Mockito.never()).getRepos(username);
        Mockito.when(repository.findById(username)).thenReturn(Optional.of(githubData));

        Assertions.assertNull(githubData.getAvatar());
        Assertions.assertEquals(email, githubData.getEmail());
        Assertions.assertEquals(username, githubData.getDisplayName());
        Assertions.assertEquals(username, githubData.getUsername());
        Assertions.assertEquals(1, githubData.getRepos().size());
        Assertions.assertEquals(testUrl, githubData.getRepos().getFirst().getUrl());
        Assertions.assertEquals(repoName, githubData.getRepos().getFirst().getName());
    }

    @Test
    public void GetRepoNullResponse() {
        String email = "octocat@example.com";
        String username = "octocat";
        String testUrl = "testUrl";
        String repoName = "test repo";
        GetUser user = new GetUser();
        user.setAvatarUrl(null);
        user.setEmail(email);
        user.setName(username);
        user.setLogin(username);
        Mockito.when(client.getUser(username)).thenReturn(user);
        Mockito.when(client.getRepos(username)).thenReturn(null);
        Mockito.when(repository.findById(username)).thenReturn(Optional.empty());

        GithubData githubData = service.getGithubData(username);
        Assertions.assertNull(githubData.getAvatar());
        Assertions.assertEquals(email, githubData.getEmail());
        Assertions.assertEquals(username, githubData.getDisplayName());
        Assertions.assertEquals(username, githubData.getUsername());
        Assertions.assertEquals(0, githubData.getRepos().size());
    }


    @Test
    public void GetUserNullResponse() {
        String username = "octocat";
        Mockito.when(client.getUser(username)).thenReturn(null);
        Mockito.when(client.getRepos(username)).thenReturn(null);
        Mockito.when(repository.findById(username)).thenReturn(Optional.empty());

        GithubData githubData = service.getGithubData(username);
        Assertions.assertNull(githubData);
    }
}

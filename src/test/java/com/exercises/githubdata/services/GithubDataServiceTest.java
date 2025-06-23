package com.exercises.githubdata.services;

import com.exercises.githubdata.client.GithubClient;
import com.exercises.githubdata.client.models.GetUser;
import com.exercises.githubdata.client.models.GithubRepo;
import com.exercises.githubdata.models.GithubData;
import com.exercises.githubdata.models.Repo;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
public class GithubDataServiceTest {

    @MockitoBean
    private GithubClient client;

    @Autowired
    private GithubDataService service;

    @Test
    public void success() {
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

        GithubData githubData = service.getGithubData(username);
        Assertions.assertNull(githubData);
    }
}

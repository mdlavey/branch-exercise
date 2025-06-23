package com.exercises.githubdata.controllers;

import com.exercises.githubdata.models.GithubData;
import com.exercises.githubdata.services.GithubDataService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class GithubDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GithubDataService service;

    @Test
    void success() throws Exception {
        GithubData githubData = new GithubData();
        githubData.setUsername("octocat");
        githubData.setUrl("testurl");
        githubData.setRepos(null);
        githubData.setDisplayName("octocat");
        githubData.setEmail(null);
        githubData.setAvatar(null);
        Date createdAt = new Date();
        githubData.setCreatedAt(Long.toString(createdAt.getTime()));
        Mockito.when(service.getGithubData("octocat")).thenReturn(githubData);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/octocat")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void notFound() throws Exception {
        HttpClientErrorException error = new HttpClientErrorException(HttpStatus.NOT_FOUND);
        Mockito.when(service.getGithubData("octocat")).thenThrow(error);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/octocat")).andDo(print()).andExpect(status().isNotFound());

    }

    @Test
    public void otherError() throws Exception {
        HttpClientErrorException error = new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "test error");
        Mockito.when(service.getGithubData("octocat")).thenThrow(error);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/octocat"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("500 test error"));

    }
}

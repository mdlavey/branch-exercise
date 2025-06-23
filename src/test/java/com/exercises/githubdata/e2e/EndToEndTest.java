package com.exercises.githubdata.e2e;

import com.exercises.githubdata.models.GithubData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class EndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${e2eSuccessJsonBody}")
    private String e2eSuccessJsonBody;

    @Test
    void success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/octocat")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(e2eSuccessJsonBody));
    }


    @Test
    void userDoesntExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/nouserexists12345")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void nullUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/")).andDo(print())
                .andExpect(status().isNotFound());
    }
}

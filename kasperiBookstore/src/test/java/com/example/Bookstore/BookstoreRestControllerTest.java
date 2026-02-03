package com.example.Bookstore;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = BookstoreApplication.class)
@AutoConfigureMockMvc
public class BookstoreRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateBook() throws Exception {
        String newBookJson = "{\"title\":\"New Book\",\"author\":\"Author\",\"publicationYear\":2023,\"isbn\":\"123456\",\"price\":29.99}";
        ResultActions resultActions = mockMvc.perform(post("/api/books")
                .contentType("application/json")
                .content(newBookJson));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("New Book")))
                .andExpect(jsonPath("$.author", is("Author")))
                .andExpect(jsonPath("$.publicationYear", is(2023)))
                .andExpect(jsonPath("$.isbn", is("123456")))
                .andExpect(jsonPath("$.price", is(29.99)));
    }
}


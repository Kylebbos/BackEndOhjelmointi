package fi.haagahelia.ajokilometrit;

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

@SpringBootTest(classes = AjokilometritApplication.class)
@AutoConfigureMockMvc
public class AjokilometritRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateKilometrit() throws Exception {
        String newKilometritJson = "{\"date\":\"10-11-2023\",\"car\":\"Car\",\"odometerReading\":20000,\"litersFueled\":\"45\",\"price\":2.2}";
        ResultActions resultActions = mockMvc.perform(post("/api/kilometrit")
                .contentType("application/json")
                .content(newKilometritJson));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.date", is("10-11-2023")))
                .andExpect(jsonPath("$.car", is("Car")))
                .andExpect(jsonPath("$.odometerReading", is(20000)))
                .andExpect(jsonPath("$.litersFueled", is("45")))
                .andExpect(jsonPath("$.price", is(2.2)));
    }
}



package ua.sirkostya009.decenttesttask.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerIntegrationTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    public void testMarkDeleted() throws Exception {
        mvc.perform(delete("/api/customers/1"))
            .andExpect(status().isOk());

        mvc.perform(get("/api/customers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testInvalidCustomer() throws Exception {
        var response = mvc.perform(post("/api/customers")
                .contentType("application/json")
                .content("""
                        {
                            "fullName": null,
                            "email": "not an email",
                            "phoneNumber": "+asdasd"
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var result = (List<?>) new ObjectMapper().readValue(response, Map.class).get("error");

        assertThat(result).hasSize(3);
    }

    @Test
    @Transactional
    public void testUpdateDeletedUser() throws Exception {
        var response = mvc.perform(post("/api/customers")
                .contentType("application/json")
                .content("""
                        {
                            "fullName": "name",
                            "email": "example@test.com",
                            "phoneNumber": "+123456789"
                        }
                        """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var dto = new ObjectMapper().readValue(response, CustomerDto.class);

        mvc.perform(delete("/api/customers/"+dto.id()))
            .andExpect(status().isOk());

        mvc.perform(put("/api/customers/"+dto.id())
                .contentType("application/json")
                .content("""
                        {
                            "fullName": "new name",
                            "phoneNumber": "+987654321"
                        }
                        """))
                .andExpect(status().isNotFound());
    }
}

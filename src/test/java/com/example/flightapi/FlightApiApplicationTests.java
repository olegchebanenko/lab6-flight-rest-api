package com.example.flightapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FlightApiApplicationTests {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @Test
    void flightLifecycle() throws Exception {
        mvc.perform(get("/flights"))
            .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1));
        mvc.perform(get("/flights/1"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.flight_number").value("LO281"));
        String json = """
            {"departure":"Vienna","arrival":"Barcelona",
             "departureDateTime":"2026-12-15T10:00:00",
             "arrivalDateTime":"2026-12-15T12:30:00",
             "price":175.50,"flight_number":"OS401"}
            """;
        String response = mvc.perform(post("/flights")
                .contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.flight_number").value("OS401"))
            .andExpect(jsonPath("$.creation_date").exists())
            .andReturn().getResponse().getContentAsString();
        long id = mapper.readTree(response).get("id").asLong();
        mvc.perform(get("/flights/" + id)).andExpect(status().isOk());
        mvc.perform(delete("/flights/" + id))
            .andExpect(status().isNoContent()).andExpect(content().string(""));
        mvc.perform(get("/flights/" + id)).andExpect(status().isNotFound());
    }

    @Test
    void invalidFlight() throws Exception {
        mvc.perform(post("/flights").contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isBadRequest());
    }
}

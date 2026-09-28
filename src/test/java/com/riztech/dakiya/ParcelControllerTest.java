package com.riztech.dakiya;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParcelController.class)
@Import(SecurityConfig.class)
class ParcelControllerTest {
    @Autowired MockMvc mvc;
    @MockitoBean ParcelRepository repository;

    @Test @WithMockUser
    void getOne_returns404_whenMissing() throws Exception {
        when(repository.findById(999L)).thenReturn(Optional.empty());
        mvc.perform(get("/api/parcels/999")).andExpect(status().isNotFound());
    }

    @Test @WithMockUser
    void create_rejectsInvalidBody_with400() throws Exception {
        mvc.perform(post("/api/parcels").contentType("application/json")
                .content("{\"recipientName\":\"\",\"destinationCity\":\"P\"}"))
           .andExpect(status().isBadRequest());
    }

    @Test
    void list_requiresAuthentication() throws Exception {
        mvc.perform(get("/api/parcels")).andExpect(status().isUnauthorized());
    }
}

package com.example.weather_bot;

import com.example.weather_bot.repository.RequestLogEntity;
import com.example.weather_bot.service.RequestLogService;
import com.example.weather_bot.controller.RequestLogController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//@SpringBootTest
//@AutoConfigureWebTestClient
@WebMvcTest(RequestLogController.class)
class RequestLogEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestLogService requestLogService;

    @Test
    void testGetLogs() throws Exception {
        LocalDateTime timestamp = LocalDateTime.parse("2024-12-01 10:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Page<RequestLogEntity> mockPage = new PageImpl<>(
                List.of(new RequestLogEntity(1L, "GET", timestamp, "/test")),
                PageRequest.of(0, 5),
                1
        );
        when(requestLogService.getRequestLogs(null, null, 0, 5)).thenReturn(mockPage);

        // Act & Assert: Perform GET request and verify response
        mockMvc.perform(get("/logs")
                        .param("offset", "0")
                        .param("limit", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void testGetLogsByUserId() throws Exception {
        LocalDateTime timestamp = LocalDateTime.parse("2024-12-01 10:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Page<RequestLogEntity> mockPage = new PageImpl<>(
                List.of(new RequestLogEntity(1L, "GET", timestamp, "/user-test")),
                PageRequest.of(0, 5),
                1
        );
        when(requestLogService.getRequestLogsByUserId(1L, null, null, 0, 5)).thenReturn(mockPage);

        // Act & Assert: Perform GET request for specific user and verify response
        mockMvc.perform(get("/logs/1")
                        .param("offset", "0")
                        .param("limit", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }
}

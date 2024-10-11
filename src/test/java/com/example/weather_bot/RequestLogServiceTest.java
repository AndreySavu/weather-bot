package com.example.weather_bot;

import com.example.weather_bot.model.RequestLog;
import com.example.weather_bot.repository.RequestLogRepository;
import com.example.weather_bot.service.RequestLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RequestLogServiceTest {

    @Mock
    private RequestLogRepository requestLogRepository;

    @InjectMocks
    private RequestLogService requestLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRequestLog() {
        RequestLog requestLog = new RequestLog(1L, "/weather Новосибирск", LocalDateTime.now(), "Температура: 5°C");

        requestLogService.createRequestLog(requestLog);

        verify(requestLogRepository, times(1)).save(requestLog);
    }

    @Test
    void testGetRequestLogsWithTimeRange() {
        LocalDateTime startTime = LocalDateTime.parse("2024-10-10T12:00:00");
        LocalDateTime endTime = LocalDateTime.parse("2024-10-11T12:00:00");
        Pageable pageable = PageRequest.of(0, 5);

        List<RequestLog> logs = Arrays.asList(
                new RequestLog(1L, "/weather Новосибирск", LocalDateTime.now(), "Температура: 5°C"),
                new RequestLog(1L, "/weather Москва", LocalDateTime.now(), "Температура: 10°C")
        );
        Page<RequestLog> page = new PageImpl<>(logs, pageable, logs.size());

        when(requestLogRepository.findByRequestTimeBetween(any(), any(), any())).thenReturn(page);

        Page<RequestLog> result = requestLogService.getRequestLogs("2024-10-10 12:00", "2024-10-11 12:00", 0, 5);

        assertThat(result.getContent(), hasSize(2));
        assertThat(result.getTotalElements(), is(2L));
        verify(requestLogRepository, times(1)).findByRequestTimeBetween(any(), any(), any());
    }

    @Test
    void testGetRequestLogsByUserId() {
        Long userId = 778836599L;
        LocalDateTime startTime = LocalDateTime.parse("2024-10-10T12:00:00");
        LocalDateTime endTime = LocalDateTime.parse("2024-10-11T12:00:00");
        Pageable pageable = PageRequest.of(0, 5);

        List<RequestLog> logs = Arrays.asList(
                new RequestLog(userId, "/weather Новосибирск", LocalDateTime.now(), "Температура: 5°C"),
                new RequestLog(userId, "/weather Москва", LocalDateTime.now(), "Температура: 10°C")
        );
        Page<RequestLog> page = new PageImpl<>(logs, pageable, logs.size());

        when(requestLogRepository.findByUserIdAndRequestTimeBetween(eq(userId), any(), any(), any())).thenReturn(page);

        Page<RequestLog> result = requestLogService.getRequestLogsByUserId(userId, "2024-10-10 12:00", "2024-10-11 12:00", 0, 5);

        assertThat(result.getContent(), hasSize(2));
        assertThat(result.getTotalElements(), is(2L));
        verify(requestLogRepository, times(1)).findByUserIdAndRequestTimeBetween(eq(userId), any(), any(), any());
    }

    @Test
    void testGetRequestLogsWithoutTimeRange() {
        Pageable pageable = PageRequest.of(0, 5);
        List<RequestLog> logs = Arrays.asList(
                new RequestLog(1L, "/weather Новосибирск", LocalDateTime.now(), "Температура: 5°C"),
                new RequestLog(1L, "/weather Москва", LocalDateTime.now(), "Температура: 10°C")
        );
        Page<RequestLog> page = new PageImpl<>(logs, pageable, logs.size());

        when(requestLogRepository.findAll(pageable)).thenReturn(page);

        Page<RequestLog> result = requestLogService.getRequestLogs(null, null, 0, 5);

        assertThat(result.getContent(), hasSize(2));
        assertThat(result.getTotalElements(), is(2L));
        verify(requestLogRepository, times(1)).findAll(pageable);
    }

}

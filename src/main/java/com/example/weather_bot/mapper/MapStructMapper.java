package com.example.weather_bot.mapper;

import com.example.weather_bot.controller.RequestLogResponse;
import com.example.weather_bot.repository.RequestLogEntity;
import com.example.weather_bot.service.RequestLog;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper
public interface MapStructMapper {
    Page<RequestLog> fromRequestLogEntity(Page<RequestLogEntity> entity);
    Page<RequestLogResponse> toRequestLogResponse(Page<RequestLog> model);
    RequestLogEntity toRequestLogEntity(RequestLog model);
}
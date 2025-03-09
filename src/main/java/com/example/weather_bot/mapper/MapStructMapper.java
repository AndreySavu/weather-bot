package com.example.weather_bot.mapper;

import com.example.weather_bot.controller.RequestLogResponse;
import com.example.weather_bot.repository.RequestLogEntity;
import com.example.weather_bot.service.RequestLog;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    // Маппинг одиночных объектов
    RequestLog fromRequestLogEntity(RequestLogEntity entity);

    RequestLogEntity toRequestLogEntity(RequestLog model);

    RequestLogResponse toRequestLogResponse(RequestLog model);

    // Маппинг списков (вручную, потому что Page<T> нельзя маппить напрямую)
    default List<RequestLog> fromRequestLogEntityList(List<RequestLogEntity> entities) {
        return entities.stream().map(this::fromRequestLogEntity).collect(Collectors.toList());
    }

    default List<RequestLogResponse> toRequestLogResponseList(List<RequestLog> models) {
        return models.stream().map(this::toRequestLogResponse).collect(Collectors.toList());
    }

    // Маппинг Page<T>
    default Page<RequestLog> fromRequestLogEntityPage(Page<RequestLogEntity> entityPage) {
        return entityPage.map(this::fromRequestLogEntity);
    }

    default Page<RequestLogResponse> toRequestLogResponsePage(Page<RequestLog> modelPage) {
        return modelPage.map(this::toRequestLogResponse);
    }


}
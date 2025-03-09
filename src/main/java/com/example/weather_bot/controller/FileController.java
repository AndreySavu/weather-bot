package com.example.weather_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "Файлы", description = "Загрузка файлов с мета-информацией")
@RestController
public class FileController {

    @Operation(
            summary = "Загрузка JSON + файлов",
            description = "Отправляет JSON-данные и файлы в multipart-запросе",
            requestBody = @RequestBody(
                    content = {
                            @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    encoding = @Encoding(name = "entity", contentType = MediaType.APPLICATION_JSON_VALUE))
                    }
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Файл успешно загружен"),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации запроса")
            }
    )
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> upload(
            @RequestPart(value = "entity") @Valid FileInfoRequest fileInfoRequest,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        // Проверка загрузки JSON-данных
        System.out.println("Received file info: " + fileInfoRequest);

        // Проверка загруженных файлов
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                // Тип файла
                String fileType = file.getContentType();
                // Размер файла в байтах
                long fileSize = file.getSize();

                // Вывод информации о файле
                System.out.println("File received: " + file.getOriginalFilename());
                System.out.println("File type: " + fileType);
                System.out.println("File size: " + fileSize + " bytes");
            }
            return ResponseEntity.ok("Files uploaded successfully!");
        } else {
            return ResponseEntity.ok("No files uploaded.");
        }
    }
}

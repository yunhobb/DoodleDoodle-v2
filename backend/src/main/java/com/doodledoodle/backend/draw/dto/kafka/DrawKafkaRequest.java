package com.doodledoodle.backend.draw.dto.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Getter
@NoArgsConstructor
public class DrawKafkaRequest {
    private Long drawId;
    private String englishName;
    private String file;

    public DrawKafkaRequest(Long drawId, String englishName, MultipartFile file) {
        this.drawId = drawId;
        this.englishName = englishName;
        convertFile(file);
    }

    private void convertFile(MultipartFile file) {
        try {
            this.file = Base64.getEncoder().encodeToString(file.getBytes());
        } catch (IOException e) {}
    }
}

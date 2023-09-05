package com.nutrition;

import com.nutrition.data.CsvDataLoader;
import com.nutrition.service.NutritionSearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class NutritionSearchApplication {

    @Value("${nutrition-search.data.file.name}")
    private String nutritionDataFile;
    @Value("${nutrition-search.data.file.type}")
    private String nutritionDataFormat;

    @Bean
    public NutritionSearchService nutritionSearchService() {
        if (nutritionDataFormat.equalsIgnoreCase("CSV")) {
            return new NutritionSearchService(dataFile(), new CsvDataLoader());
        } else {
            throw new IllegalArgumentException("Unknown data format: " + nutritionDataFormat);
        }
    }

    @Bean
    public File dataFile() {
        try {
            var nutritionData = new ClassPathResource(nutritionDataFile);
            return nutritionData.getFile();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(NutritionSearchApplication.class, args);
    }
}

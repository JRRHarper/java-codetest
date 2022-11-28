package com.nutrition.controller;

import com.nutrition.model.Food;
import com.nutrition.service.NutritionSearchService;
import com.nutrition.dto.NutritionSearchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class NutritionController {

    private final NutritionSearchService nutritionSearchService;

    public NutritionController(NutritionSearchService nutritionSearchService) {
        this.nutritionSearchService = nutritionSearchService;
    }

    @GetMapping(path = "/nutrition", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Food> findFood(@RequestParam(name = "minCalories", required = false) Integer minCalories,
                               @RequestParam(name = "maxCalories", required = false) Integer maxCalories,
                               @RequestParam(name = "fatRating", required = false) String fatRating,
                               @RequestParam(name = "sort", required = false) List<String> sortCriteria,
                               @RequestParam(name = "limit", defaultValue = "1000") int limit) {
        try {
            var request = new NutritionSearchRequest(minCalories, maxCalories, sortCriteria, fatRating, limit);
            return nutritionSearchService.searchNutrition(request);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}

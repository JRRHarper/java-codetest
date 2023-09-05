package com.nutrition.data;

import com.nutrition.model.Food;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.nutrition.util.CsvColumnHeading.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public class CsvDataLoader implements DataLoader {

    @Override
    public List<Food> loadData(File file) {
        try (var csvReader = new CSVReaderHeaderAware(new FileReader(file, UTF_8))) {

            Map<String, String> rowData;
            var foods = new ArrayList<Food>();

            while ((rowData = csvReader.readMap()) != null) {
                if (isValidFood(rowData)) {
                    foods.add(createFood(rowData));
                }
            }

            return foods;
        } catch (IOException | CsvValidationException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static boolean isValidFood(Map<String, String> rowData) {
        return isValidRow(rowData) && rowData.get(SERVING_SIZE).equals("100 g");
    }

    private static boolean isValidRow(Map<String, String> rowData) {
        var requiredFields = List.of(NAME_FIELD, CALORIES_FIELD, TOTAL_FAT_FIELD, CAFFEINE_FIELD, SERVING_SIZE);

        return rowData.entrySet().stream()
                .filter(e1 -> requiredFields.contains(e1.getKey()))
                .noneMatch(e2 -> e2.getValue().isBlank());
    }

    private static Food createFood(Map<String, String> rowData) {
        var name = rowData.get(NAME_FIELD);
        var calories = Integer.parseInt(rowData.get(CALORIES_FIELD));
        var totalFat = Double.parseDouble(rowData.get(TOTAL_FAT_FIELD).substring(0, rowData.get(TOTAL_FAT_FIELD).indexOf('g')));
        var caffeine = rowData.get(CAFFEINE_FIELD);

        return new Food(name, calories, totalFat, caffeine);
    }
}

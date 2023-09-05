package com.nutrition.service;

import com.nutrition.data.CsvDataLoader;
import com.nutrition.data.DataLoader;
import com.nutrition.dto.NutritionSearchRequest;
import com.nutrition.dto.Sort;
import com.nutrition.dto.SortField;
import com.nutrition.dto.SortOrder;
import com.nutrition.model.Food;

import java.io.File;
import java.util.*;

import static java.util.stream.Collectors.toList;

public final class NutritionSearchService {

    private static final EnumMap<SortField, Comparator<Food>> FIELD_COMPARATORS = new EnumMap<>(SortField.class);

    static {
        FIELD_COMPARATORS.put(SortField.CALORIES, Comparator.comparing(Food::getCalories, Comparator.naturalOrder()));
        FIELD_COMPARATORS.put(SortField.NAME, Comparator.comparing(Food::getName, String.CASE_INSENSITIVE_ORDER));
    }

    private final File file;
    private final DataLoader dataLoader;

    public NutritionSearchService(File file, DataLoader dataLoader) {
        this.file = file;
        this.dataLoader = new CsvDataLoader();
    }

    public List<Food> searchNutrition(NutritionSearchRequest request) {
        return loadFromFile(file).stream()
                .filter(food -> Objects.isNull(request.getFatRating())
                        || food.getFatRating() == request.getFatRating())
                .filter(food -> Objects.isNull(request.getMinCalories())
                        || food.getCalories() >= request.getMinCalories())
                .filter(food -> Objects.isNull(request.getMaxCalories())
                        || food.getCalories() <= request.getMaxCalories())
                .sorted(buildComparator(request))
                .limit(request.getLimit())
                .collect(toList());
    }

    private Comparator<Food> buildComparator(NutritionSearchRequest request) {
        return request.getSortCriteria().stream()
            .map(this::getComparator)
            .reduce(Comparator::thenComparing)
            .orElse((h1, h2) -> 0);
    }

    private Comparator<Food> getComparator(Sort sort) {
        var comparator = FIELD_COMPARATORS.get(sort.getField());
        return sort.getOrder() == SortOrder.ASC ? comparator : comparator.reversed();
    }

    private List<Food> loadFromFile(File file) {
        return dataLoader.loadData(file);
    }

}

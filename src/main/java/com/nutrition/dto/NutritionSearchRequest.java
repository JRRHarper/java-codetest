package com.nutrition.dto;

import com.nutrition.model.Food;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

public final class NutritionSearchRequest {

    private final Integer minCalories;
    private final Integer maxCalories;
    private final List<Sort> sortCriteria;
    private final Food.FatRating fatRating;
    private final int limit;

    public NutritionSearchRequest(
            Integer minCalories,
            Integer maxCalories,
            List<String> sortParams,
            String fatRating,
            int limit
    ) {
        Validate.isTrue(limit > 0, "Limit must be greater than zero; found [%d]", limit);
        validateCaloriesBracket(minCalories, maxCalories);

        this.minCalories = minCalories;
        this.maxCalories = maxCalories;
        this.fatRating = parseFatRating(fatRating);
        this.sortCriteria = createSortCriteria(sortParams);
        this.limit = limit;
    }

    public Food.FatRating getFatRating() {
        return fatRating;
    }

    public Integer getMinCalories() {
        return minCalories;
    }

    public Integer getMaxCalories() {
        return maxCalories;
    }

    public List<Sort> getSortCriteria() {
        return sortCriteria;
    }

    public int getLimit() {
        return limit;
    }

    private Food.FatRating parseFatRating(String fatRating) {
        if (isBlank(fatRating)) return null;

        try {
            return Food.FatRating.valueOf(fatRating.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(format("Unknown Fat Rating [%s]", fatRating), ex);
        }
    }

    private List<Sort> createSortCriteria(List<String> sortCriteria) {
        if (sortCriteria == null) return emptyList();

        var sorts = sortCriteria.stream()
            .map(this::parseSort)
            .collect(toUnmodifiableList());

        var duplicateSortFields = findDuplicateSortFields(sorts);
        if (duplicateSortFields.isEmpty()) {
            return sorts;
        } else {
            var duplicateFields = duplicateSortFields.stream()
                .map(SortField::toString)
                .collect(Collectors.joining(", "));
            throw new IllegalArgumentException(format("Duplicate sort criteria found for fields named [%s]",
                duplicateFields));
        }
    }

    private Sort parseSort(String criterion) {
        Validate.isTrue(criterion.contains("_"), "Sort parameter format is 'fieldName_order'. Found [%s]",
            criterion);

        var parts = criterion.split("_", 2);
        SortField sortField;
        SortOrder sortOrder;

        try {
            sortField = SortField.valueOf(parts[0].trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(format("[%s] is not a sortable field", parts[0]));
        }

        try {
            sortOrder = SortOrder.valueOf(parts[1].trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(format("Unknown sort order [%s]", parts[1]));
        }

        return Sort.of(sortField, sortOrder);
    }

    private List<SortField> findDuplicateSortFields(List<Sort> sorts) {
        return sorts.stream()
            .collect(groupingBy(Sort::getField, counting()))
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue() > 1)
            .map(Map.Entry::getKey)
            .collect(toList());
    }

    private static void validateCaloriesBracket(
        Integer minCalories,
        Integer maxCalories
    ) {
        if (minCalories != null && minCalories < 0) {
            throw new IllegalArgumentException(format("minCalories must be greater than zero; found [%d]", minCalories));
        }
        if (maxCalories != null && maxCalories < 0) {
            throw new IllegalArgumentException(format("maxCalories must be greater than zero; found [%d]", maxCalories));
        }
        if (minCalories != null && maxCalories != null) {
            Validate.isTrue(minCalories <= maxCalories,
                "minCalories must be less than or equal to maxCalories");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof NutritionSearchRequest)) return false;

        NutritionSearchRequest other = (NutritionSearchRequest) obj;
        return Objects.equals(fatRating, other.fatRating)
            && Objects.equals(sortCriteria, other.sortCriteria)
            && Objects.equals(limit, other.limit)
            && Objects.equals(minCalories, other.minCalories)
            && Objects.equals(maxCalories, other.maxCalories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fatRating, sortCriteria, limit, minCalories, maxCalories);
    }
}

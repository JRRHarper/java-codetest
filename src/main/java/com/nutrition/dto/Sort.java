package com.nutrition.dto;

import java.util.Objects;

public final class Sort {

    private final SortField field;
    private final SortOrder order;

    private Sort(SortField field, SortOrder order) {
        this.field = field;
        this.order = order;
    }

    public static Sort of(SortField field, SortOrder order) {
        return new Sort(field, order);
    }

    public SortField getField() {
        return field;
    }

    public SortOrder getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Sort)) return false;

        Sort other = (Sort) obj;
        return Objects.equals(field, other.field)
            && Objects.equals(order, other.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, order);
    }
}

package com.nutrition.data;

import com.nutrition.model.Food;

import java.io.File;
import java.util.List;

public interface DataLoader {
    List<Food> loadData(File file);
}

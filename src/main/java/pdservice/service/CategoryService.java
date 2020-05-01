package pdservice.service;

import pdservice.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public boolean categoryExists(String categoryName);

    public boolean categoryExists(Category category);

    public Optional<Category> unAvailableCategory(Category category);

    public Optional<List<Category>> findByIds(List<Integer> ids);

    public Optional<List<Category>> findAll();
}

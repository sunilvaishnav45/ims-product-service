package pdservice.service.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdservice.dao.AttributeValuesDao;
import pdservice.dao.BrandDao;
import pdservice.dao.CategoryDao;
import pdservice.dao.custom.*;
import pdservice.entity.Category;
import pdservice.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryCustomDao categoryCustomDao;

    @Autowired
    private CategoryDao categoryDao;

    private static final Logger LOGGER = Logger.getLogger(CategoryServiceImpl.class);

    @Override
    public boolean categoryExists(String categoryName) {
        return categoryCustomDao.findByName(categoryName).isPresent();
    }

    @Override
    public boolean categoryExists(Category category) {
        return categoryDao.existsById(category.getId());
    }

    @Override
    public Optional<Category> unAvailableCategory(Category category) {
        return categoryCustomDao.unAvailableCategory(category);
    }

    @Override
    public Optional<List<Category>> findByIds(List<Integer> ids) {
        return categoryCustomDao.findByIds(ids);
    }

    @Override
    public Optional<List<Category>> findAll() {
        return categoryCustomDao.findAll();
    }


}

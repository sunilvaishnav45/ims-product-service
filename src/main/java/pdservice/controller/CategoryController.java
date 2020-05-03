package pdservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdservice.dao.CategoryDao;
import pdservice.entity.Brand;
import pdservice.entity.Category;
import pdservice.entity.User;
import pdservice.service.CategoryService;
import pdservice.service.ProductService;
import pdservice.service.UserService;
import pdservice.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = {"/"}, consumes = "application/json")
    public ResponseEntity<Category> saveCategory(HttpServletRequest request, HttpServletRequest response, @RequestBody Category category) throws  Exception {
        User loggedInUser = userService.getLoggedInUser();
        if (!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission", HttpStatus.FORBIDDEN);
        if (categoryService.categoryExists(category.getCategory()))
            return new ResponseEntity("Category already exist", HttpStatus.BAD_REQUEST);
        categoryDao.save(category);
        return new ResponseEntity(category, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = {"/"})
    public ResponseEntity<List<Category>> getCategory(HttpServletRequest request, HttpServletRequest response, @RequestParam(required = false) String categoryIds){
        User loggedInUser = userService.getLoggedInUser();
        List<Category> categoryList = new ArrayList<>();
        if(!userService.userHasReadPermission(loggedInUser))
            return new ResponseEntity("User doesn't has read permission",HttpStatus.FORBIDDEN);
        if(categoryIds!=null){
            if(!StringUtils.convertCommaSepratedIntoList(categoryIds).isPresent())
                return new ResponseEntity("Category ids are not valid",HttpStatus.BAD_REQUEST);
            Optional<List<Category>> categories = categoryService.findByIds(StringUtils.convertCommaSepratedIntoList(categoryIds).get());
            if(categories.isPresent())
                categoryList = (List<Category>) categories.get();
           }
        else{
            Optional<List<Category>> categories = categoryService.findAll();
            if(categories.isPresent())
                categoryList = (List<Category>) categories.get();
        }

        return new ResponseEntity(categoryList,HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value="/", consumes = "application/json")
    public ResponseEntity<Category> deleteCategory(@RequestBody Category category){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission",HttpStatus.FORBIDDEN);
        if(!categoryDao.existsById(category.getId()))
            return new ResponseEntity("Category id is not valid",HttpStatus.BAD_REQUEST);
        categoryService.unAvailableCategory(category);
        return new ResponseEntity(category,HttpStatus.ACCEPTED);
    }

    @PutMapping(value="/", consumes = "application/json")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission",HttpStatus.FORBIDDEN);
        if (categoryService.categoryExists(category.getCategory()))
            return new ResponseEntity("Category already exist", HttpStatus.BAD_REQUEST);
        if(!categoryDao.existsById(category.getId()))
            return new ResponseEntity("Category id is not valid",HttpStatus.BAD_REQUEST);
        Optional<Category> updateCategory = categoryService.updateCategory(category);
        if(!updateCategory.isPresent())
            return new ResponseEntity("Category updating failed",HttpStatus.BAD_REQUEST);
        return new ResponseEntity(updateCategory.get(),HttpStatus.ACCEPTED);
    }

}

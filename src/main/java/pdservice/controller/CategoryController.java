package pdservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdservice.dao.CategoryDao;
import pdservice.entity.Category;
import pdservice.entity.User;
import pdservice.service.ProductService;
import pdservice.service.UserService;
import pdservice.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductService productService;

    @PostMapping(value = {"/"}, consumes = "application/json")
    public ResponseEntity<Category> saveCategory(HttpServletRequest request, HttpServletRequest response, @RequestBody Category category) throws  Exception {
        User loggedInUser = userService.getLoggedInUser();
        if (!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission", HttpStatus.FORBIDDEN);
        if (productService.categoryExists(category.getCategory()))
            return new ResponseEntity("Category already exist", HttpStatus.BAD_REQUEST);
        categoryDao.save(category);
        return new ResponseEntity(category, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = {"/"})
    public ResponseEntity<List<Category>> getCategory(HttpServletRequest request, HttpServletRequest response, @RequestParam(required = false) String categoryIds){
        User loggedInUser = userService.getLoggedInUser();
        List<Category> categoryList = null;
        if(!userService.userHasReadPermission(loggedInUser))
            return new ResponseEntity("User doesn't has read permission",HttpStatus.FORBIDDEN);
        if(categoryIds!=null){
            if(!StringUtils.convertCommaSepratedIntoList(categoryIds).isPresent())
                return new ResponseEntity("Category ids are not valid",HttpStatus.BAD_REQUEST);
            categoryList = (List<Category>) categoryDao.findAllById(StringUtils.convertCommaSepratedIntoList(categoryIds).get());
        }
        else
            categoryList = (List<Category>) categoryDao.findAll();
        return new ResponseEntity(categoryList,HttpStatus.ACCEPTED);
    }

}

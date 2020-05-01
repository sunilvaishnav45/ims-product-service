package pdservice.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdservice.dao.ProductDao;
import pdservice.entity.Brand;
import pdservice.entity.Category;
import pdservice.entity.Product;
import pdservice.entity.User;
import pdservice.service.BrandService;
import pdservice.service.CategoryService;
import pdservice.service.ProductService;
import pdservice.service.UserService;
import pdservice.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    private static final Logger LOGGER = Logger.getLogger(ProductController.class);

    @PostMapping(value = {"/"}, consumes = "application/json")
    public ResponseEntity<Product> saveProduct(HttpServletRequest request, HttpServletRequest response, @RequestBody Product product) throws  Exception{
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission",HttpStatus.FORBIDDEN);
        if(!productService.productExists(product)){
            if(!categoryService.categoryExists(product.getCategory()))
                return new ResponseEntity("Category doesn't exist",HttpStatus.BAD_REQUEST);
            if(!brandService.brandExists(product.getBrand()))
                return new ResponseEntity("Brand doesn't exist",HttpStatus.BAD_REQUEST);
            productDao.save(product);
            return new ResponseEntity(product,HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity("Product already exist",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = {"/"})
    public ResponseEntity<List<Product>> getProduct(HttpServletRequest request, HttpServletRequest response, @RequestParam(required = false) String productIds){
        User loggedInUser = userService.getLoggedInUser();
        List<Product> productList = new ArrayList<>();
        if(!userService.userHasReadPermission(loggedInUser))
            return new ResponseEntity("User doesn't has read permission",HttpStatus.FORBIDDEN);
        if(productIds!=null){
            if(!StringUtils.convertCommaSepratedIntoList(productIds).isPresent())
                return new ResponseEntity("Product ids are not valid",HttpStatus.BAD_REQUEST);
            Optional<List<Product>> products = productService.findByIds(StringUtils.convertCommaSepratedIntoList(productIds).get());
            if(products.isPresent())
                productList = (List<Product>) products.get();
        }else{
            Optional<List<Product>> products = productService.findAll();
            if(products.isPresent())
                productList = (List<Product>) products.get();
        }
        return new ResponseEntity(productList,HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value="/", consumes = "application/json")
    public ResponseEntity<Product> deleteProduct(@RequestBody Product product){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission",HttpStatus.FORBIDDEN);
        if(!productDao.existsById(product.getId()))
            return new ResponseEntity("Product id are not valid",HttpStatus.BAD_REQUEST);
        productService.unAvailableProduct(product);
        return new ResponseEntity(product,HttpStatus.ACCEPTED);
    }

}

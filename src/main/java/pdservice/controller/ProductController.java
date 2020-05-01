package pdservice.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdservice.dao.ProductDao;
import pdservice.entity.Product;
import pdservice.entity.User;
import pdservice.service.ProductService;
import pdservice.service.UserService;
import pdservice.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    private static final Logger LOGGER = Logger.getLogger(ProductController.class);

    @PostMapping(value = {"","/"}, consumes = "application/json")
    public ResponseEntity<Product> saveProduct(HttpServletRequest request, HttpServletRequest response, @RequestBody Product product) throws  Exception{
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission",HttpStatus.FORBIDDEN);
        if(!productService.productExists(product)){
            if(!productService.categoryExists(product.getCategory()))
                return new ResponseEntity("Category doesn't exist",HttpStatus.BAD_REQUEST);
            if(!productService.brandExists(product.getBrand()))
                return new ResponseEntity("Brand doesn't exist",HttpStatus.BAD_REQUEST);
            productDao.save(product);
            return new ResponseEntity(product,HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity("Product already exist",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = {"","/"})
    public ResponseEntity<List<Product>> getProduct(HttpServletRequest request, HttpServletRequest response, @RequestParam(required = false) String productIds){
        User loggedInUser = userService.getLoggedInUser();
        List<Product> productList = null;
        if(!userService.userHasReadPermission(loggedInUser))
            return new ResponseEntity("User doesn't has read permission",HttpStatus.FORBIDDEN);
        if(productIds!=null){
            if(!StringUtils.convertCommaSepratedIntoList(productIds).isPresent())
                return new ResponseEntity("Product ids are not valid",HttpStatus.BAD_REQUEST);
            productList = (List<Product>) productDao.findAllById(StringUtils.convertCommaSepratedIntoList(productIds).get());
        }
        else
            productList = (List<Product>) productDao.findAll();
        return new ResponseEntity(productList,HttpStatus.ACCEPTED);
    }

}

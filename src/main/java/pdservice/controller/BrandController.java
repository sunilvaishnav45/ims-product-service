package pdservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdservice.dao.BrandDao;
import pdservice.entity.Brand;
import pdservice.entity.User;
import pdservice.service.ProductService;
import pdservice.service.UserService;
import pdservice.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private UserService userService;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private ProductService productService;

    @PostMapping(value = {"","/"}, consumes = "application/json")
    public ResponseEntity<Brand> saveBrand(HttpServletRequest request, HttpServletRequest response, @RequestBody Brand brand){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission", HttpStatus.FORBIDDEN);
        if (productService.brandExists(brand.getBrand()))
            return new ResponseEntity("Brand already exist", HttpStatus.BAD_REQUEST);
        brandDao.save(brand);
        return new ResponseEntity(brand, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = {"","/"})
    public ResponseEntity<List<Brand>> getBrand(HttpServletRequest request, HttpServletRequest response, @RequestParam(required = false) String brandIds){
        User loggedInUser = userService.getLoggedInUser();
        List<Brand> brandList = null;
        if(!userService.userHasReadPermission(loggedInUser))
            return new ResponseEntity("User doesn't has read permission",HttpStatus.FORBIDDEN);
        if(brandIds!=null){
            if(!StringUtils.convertCommaSepratedIntoList(brandIds).isPresent())
                return new ResponseEntity("Brand ids are not valid",HttpStatus.BAD_REQUEST);
            brandList = (List<Brand>) brandDao.findAllById(StringUtils.convertCommaSepratedIntoList(brandIds).get());
        }
        else
            brandList = (List<Brand>) brandDao.findAll();
        return new ResponseEntity(brandList,HttpStatus.ACCEPTED);
    }
}

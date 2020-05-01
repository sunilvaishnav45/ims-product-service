package pdservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdservice.dao.BrandDao;
import pdservice.entity.Brand;
import pdservice.entity.User;
import pdservice.service.BrandService;
import pdservice.service.ProductService;
import pdservice.service.UserService;
import pdservice.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private UserService userService;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private BrandService brandService;

    @PostMapping(value = {"/"}, consumes = "application/json")
    public ResponseEntity<Brand> saveBrand(HttpServletRequest request, HttpServletRequest response, @RequestBody Brand brand){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission", HttpStatus.FORBIDDEN);
        if (brandService.brandExists(brand.getBrand()))
            return new ResponseEntity("Brand already exist", HttpStatus.BAD_REQUEST);
        brandDao.save(brand);
        return new ResponseEntity(brand, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = {"/"})
    public ResponseEntity<List<Brand>> getBrand(HttpServletRequest request, HttpServletRequest response, @RequestParam(required = false) String brandIds){
        User loggedInUser = userService.getLoggedInUser();
        List<Brand> brandList = null;
        if(!userService.userHasReadPermission(loggedInUser))
            return new ResponseEntity("User doesn't has read permission",HttpStatus.FORBIDDEN);
        if(brandIds!=null){
            if(!StringUtils.convertCommaSepratedIntoList(brandIds).isPresent())
                return new ResponseEntity("Brand ids are not valid",HttpStatus.BAD_REQUEST);
            brandList = (List<Brand>) brandService.findByIds(StringUtils.convertCommaSepratedIntoList(brandIds).get()).get();
        }
        else
        {
           Optional<List<Brand>> brands = brandService.findAll();
            if(brands.isPresent())
                brandList = (List<Brand>) brands.get();
        }
        return new ResponseEntity(brandList,HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value="/", consumes = "application/json")
    public ResponseEntity<Brand> deleteBrand(@RequestBody Brand brand){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission",HttpStatus.FORBIDDEN);
        if(!brandDao.existsById(brand.getId()))
            return new ResponseEntity("Brand id are not valid",HttpStatus.BAD_REQUEST);
        brandService.unAvailableBrand(brand);
        return new ResponseEntity(brand,HttpStatus.ACCEPTED);
    }
}

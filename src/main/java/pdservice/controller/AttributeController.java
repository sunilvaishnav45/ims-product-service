package pdservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pdservice.dao.AttributeDao;
import pdservice.dao.AttributeValuesDao;
import pdservice.dto.AttributeValuesResponse;
import pdservice.entity.*;
import pdservice.service.AttributeService;
import pdservice.service.UserService;
import pdservice.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attribute")
public class AttributeController {

    @Autowired
    private UserService userService;

    @Autowired
    private AttributeDao attributeDao;

    @Autowired
    private AttributeValuesDao attributeValuesDao;

    @Autowired
    private AttributeService attributeService;

    @PostMapping(value = {"/"}, consumes = "application/json")
    public ResponseEntity<Attributes> saveAttribute(@RequestBody Attributes attributes){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't have write permission", HttpStatus.FORBIDDEN);
        if(attributeService.attributeExists(attributes.getAttribute()))
            return new ResponseEntity("Attribute already exists",HttpStatus.BAD_REQUEST);
        attributeDao.save(attributes);
        return new ResponseEntity(attributes,HttpStatus.ACCEPTED);
    }

    @GetMapping(value = {"/"})
    public ResponseEntity<List<Attributes>> getAttribute(HttpServletRequest request, HttpServletRequest response, @RequestParam(required = false) String attributeIds){
        User loggedInUser = userService.getLoggedInUser();
        List<Attributes> attributesList = null;
        if(!userService.userHasReadPermission(loggedInUser))
            return new ResponseEntity("User doesn't has read permission",HttpStatus.FORBIDDEN);
        if(attributeIds!=null){
            if(!StringUtils.convertCommaSepratedIntoList(attributeIds).isPresent())
                return new ResponseEntity("Attribute ids are not valid",HttpStatus.BAD_REQUEST);
            attributesList = (List<Attributes>) attributeService.findAttributesByIds(StringUtils.convertCommaSepratedIntoList(attributeIds).get()).get();
        }else{
            Optional<List<Attributes>> attributes = attributeService.findAllAttributes();
            if(attributes.isPresent())
                attributesList = (List<Attributes>) attributes.get();
        }
        return new ResponseEntity(attributesList,HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/values/", consumes = "application/json")
    public ResponseEntity<AttributeValuesResponse> saveAttribute(@RequestBody List<AttributeValues> attributeValuesList){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't have write permission",HttpStatus.FORBIDDEN);
        AttributeValuesResponse attributeValuesResponse = attributeService.saveAttributeValue(attributeValuesList);
        return new ResponseEntity(attributeValuesResponse,HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value="/", consumes = "application/json")
    public ResponseEntity<Attributes> deleteAttribute(@RequestBody Attributes attributes){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission",HttpStatus.FORBIDDEN);
        if(!attributeDao.existsById(attributes.getId()))
            return new ResponseEntity("Attribute id are not valid",HttpStatus.BAD_REQUEST);
        attributeService.unAvailableAttribute(attributes);
        return new ResponseEntity(attributes,HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value="/values/", consumes = "application/json")
    public ResponseEntity<AttributeValues> deleteAttributeValues(@RequestBody AttributeValues attributeValues){
        User loggedInUser = userService.getLoggedInUser();
        if(!userService.userHasWritePermission(loggedInUser))
            return new ResponseEntity("User doesn't has write permission",HttpStatus.FORBIDDEN);
        if(!attributeValuesDao.existsById(attributeValues.getId()))
            return new ResponseEntity("Attribute Values id are not valid",HttpStatus.BAD_REQUEST);
        attributeService.unAvailableAttributeValue(attributeValues);
        return new ResponseEntity(attributeValues,HttpStatus.ACCEPTED);
    }
}

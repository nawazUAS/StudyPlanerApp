package edu.fra.uas.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.financemodel.FinanceDTO;
import edu.fra.uas.service.ShoppingListService;
import edu.fra.uas.shoppinglistitemmodel.ShoppingListItem;

import java.net.URI;


import org.slf4j.Logger;

import org.springframework.web.bind.annotation.CrossOrigin; 
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ShoppingController {
     private final Logger log = org.slf4j.LoggerFactory.getLogger(ShoppingController.class);

    @Autowired
    ShoppingListService shoppingListService;


    @GetMapping(value = "/shoppings", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> list(){
    log.debug("list() is called");
    ResponseEntity<?> response = shoppingListService.getAll();
    if(response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)){
        return new ResponseEntity<>("no semesters", HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    
}

@GetMapping(value = "/shoppings/totalcosts", 
produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> getTotalCosts(){
log.debug("list() is called");
ResponseEntity<?> response = shoppingListService.getTotalCosts();
if(response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)){
return new ResponseEntity<>("no semesters", HttpStatus.NO_CONTENT);
}

return new ResponseEntity<>(response.getBody(), HttpStatus.OK);

}

@GetMapping(value = "/shoppings/{shoppingId}", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> find(@PathVariable("shoppingId") Long shoppingId){
    log.debug("find() is called");
    ResponseEntity<?> response = shoppingListService.getById(shoppingId);
    if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
        return response;
    }

    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    
}

 @PostMapping(value = "/shoppings", 
                 consumes = MediaType.APPLICATION_JSON_VALUE, 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody ShoppingListItem shoppingListItem) {
        log.info("Create new shoppingitem: ", shoppingListItem.getProductName());
        ResponseEntity<?> response = shoppingListService.createShoppingListItem(shoppingListItem);
        if(response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)){
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/event"));
            return new ResponseEntity<>(response.getBody(), headers, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(response.getBody(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }


    @PutMapping(value = "/shoppings/{shoppingId}", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public ResponseEntity<?> updateShopping(@PathVariable("shoppingId") Long shoppingId,@RequestBody ShoppingListItem shoppingListItem) {
log.info("updateShopping() is called: ");


ResponseEntity<?> response = shoppingListService.update(shoppingId, shoppingListItem);

if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
return response;
}
return new ResponseEntity<>(response.getBody(), HttpStatus.OK);


}


@DeleteMapping(value = "/shoppings/{shoppingId}", 
                produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
    public ResponseEntity<?> deleteEvent(@PathVariable("shoppingId") Long shoppingId) {
    log.info("deleteShoppingitem() is called");
    ResponseEntity<?> response = shoppingListService.deleteShoppingItem(shoppingId);

        if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
        return response;
            }
    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);


    }


@PostMapping(value = "/finances/shoppings", 
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
    public ResponseEntity<?> byMyShoppings(@RequestBody FinanceDTO financeDTO) {
    log.info("Create new finance: ", financeDTO.getDescription());
  ResponseEntity<?> response = shoppingListService.byMyShoppings(financeDTO);
        if(response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)){
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/finances"));
        return new ResponseEntity<>(response.getBody(), headers, HttpStatus.CREATED);
        }else{
return new ResponseEntity<>(response.getBody(), HttpStatus.UNPROCESSABLE_ENTITY);
}

}

}

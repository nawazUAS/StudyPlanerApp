package edu.fra.uas.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import edu.fra.uas.model.FinanceDTO;
import edu.fra.uas.model.ShoppingListItem;
import edu.fra.uas.service.ShoppingListService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;



// Annotation to indicate that this class is a REST controller
@RestController
public class ShoppingListController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(ShoppingListController.class);

    @Autowired
    private ShoppingListService shoppingListService;


    // Endpoint to get all shopping lists
    @GetMapping(value = "/shoppings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ShoppingListItem>> list() {
        log.info("list() is called");
        Iterable<ShoppingListItem> shoppingListIter = shoppingListService.getAllShoppingListItems();
        List<ShoppingListItem> shoppings = new ArrayList<>();
        shoppingListIter.forEach(shoppings::add);

        if (shoppings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(shoppings, HttpStatus.OK);
    }

    // Endpoint to get a shopping list by id
    @GetMapping(value = "/shoppings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShoppingListItem> getById(@PathVariable("id") Long id) {
        log.info("Get ShoppingList by id: {}", id);
        ShoppingListItem shoppingList = shoppingListService.getShoppingListItembyId(id);
        if (shoppingList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }

    // Endpoint to create a new shopping list
    @PostMapping(value = "/shoppings", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody ShoppingListItem shoppingList) {
        log.info("Create new ShoppingListItem: {}", shoppingList.getProductName());
// Was implemented by Saliem work on from here! And Work on DTO Class because it got lost
        String detail = null;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail);
        pd.setInstance(URI.create("/shoppings"));
        pd.setTitle("ShoppingList creation error");

        if (shoppingList.getProductName().isEmpty() || shoppingList.getProductName().isBlank()) {
            detail = "ProductName cannot be empty or null";
            return ResponseEntity.unprocessableEntity().body(pd);
        } else if (shoppingList.getQuantity() == 0) {
            detail = "Quantity cannot be empty or null";
            return ResponseEntity.unprocessableEntity().body(pd);
        }

       shoppingListService.createShoppingListItem(shoppingList);
        return new ResponseEntity<>(shoppingList, HttpStatus.CREATED);
    }

    // Endpoint to update a shopping list
    @PutMapping(value = "/shoppings/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShoppingListItem> update(@RequestBody ShoppingListItem shoppingList,
            @PathVariable("id") Long id) {
        log.info("Update ShoppingListItem: {}", shoppingList.getProductName());
        ShoppingListItem oldShoppingList = shoppingListService.getShoppingListItembyId(id);
        if (oldShoppingList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        oldShoppingList.setProductName(shoppingList.getProductName());
        oldShoppingList.setCategory(shoppingList.getCategory());
        oldShoppingList.setProductPrice(shoppingList.getProductPrice());
        oldShoppingList.setQuantity(shoppingList.getQuantity());
        oldShoppingList = shoppingListService.updateShoppingListItem(shoppingList);
        return new ResponseEntity<>(oldShoppingList, HttpStatus.OK);
    }

    // Endpoint to delete a shopping list
    @DeleteMapping(value = "/shoppings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShoppingListItem> delete(@PathVariable("id") Long id) {
        log.info("Delete ShoppingListItem: {}", id);
        ShoppingListItem deletedShoppingList = shoppingListService.deleteShoppingListItem(id);
        if (deletedShoppingList == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(deletedShoppingList, HttpStatus.OK);
    }


    @GetMapping(value = "/shoppings/totalcosts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getTotalCost() {
        log.info("list() is called");
        double totalCosts = shoppingListService.calculateTotalPrice();

        return new ResponseEntity<>(totalCosts, HttpStatus.OK);
    }






    //Financeservice integration by creating a new transaction by passing the totalcosts of my shoppings

       @PostMapping(value = "/finances/shoppings", 
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
        @ResponseBody
            public ResponseEntity<?> create(@RequestBody FinanceDTO financeDTO) {
            log.info("Create new finance: ", financeDTO.getDescription());
            double totalCosts = shoppingListService.calculateTotalPrice();
            financeDTO.setAmount(-totalCosts);
            financeDTO.setCategory("Lebensmittel etc.");
            financeDTO.setDescription("Einkauf");
            financeDTO.setType("Ausgabe");



        ResponseEntity<?> response = shoppingListService.buyShoppings(financeDTO);
            if(response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)){
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(URI.create("/finances"));
                return new ResponseEntity<>(response.getBody(), headers, HttpStatus.CREATED);
                    }else{
        return new ResponseEntity<>(response.getBody(), HttpStatus.UNPROCESSABLE_ENTITY);
}

}
}
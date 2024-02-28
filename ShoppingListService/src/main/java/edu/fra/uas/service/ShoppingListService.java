package edu.fra.uas.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import edu.fra.uas.model.ApiError;
import edu.fra.uas.model.FinanceDTO;
import edu.fra.uas.model.ShoppingListItem;
import edu.fra.uas.repository.ShoppingListRepository;

@Service // Marks the class as a service in Spring framework
public class ShoppingListService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ShoppingListService.class);

     //Read the URL of the external API (FinanceService) from the application.properties file 
    @Value("${financeservice.url}")
    String apiUrl;

    @Autowired // Spring annotation for dependency injection
    ShoppingListRepository shoppingListRepository;

    private long nextId = 1;

    // Method to create a new shopping list
    public ShoppingListItem createShoppingListItem(ShoppingListItem shoppingList) {
        shoppingList.setId(nextId++);
        log.debug("createShoppinglist" + shoppingList);
        shoppingListRepository.put(shoppingList.getId(), shoppingList);
        return shoppingListRepository.get(shoppingList.getId());
    }

    // Method to get all shopping lists
    public Iterable<ShoppingListItem> getAllShoppingListItems() {
        log.debug("getShoppingList");
        return shoppingListRepository.values();
    }

    // Method to get a shopping list by its ID
    public ShoppingListItem getShoppingListItembyId(long id) {
        log.debug("getShoppingListbyId" + id);
        return shoppingListRepository.get(id);
    }

    // Method to update a shopping list
    public ShoppingListItem updateShoppingListItem(ShoppingListItem shoppingList) {
        log.debug("updateShoppingList" + shoppingList);
        shoppingListRepository.put(shoppingList.getId(), shoppingList);
        return shoppingListRepository.get(shoppingList.getId());
    }

    // Method to delete a shopping list by its ID
    public ShoppingListItem deleteShoppingListItem(long id) {
        log.debug("deleteShoppingList" + id);
        return shoppingListRepository.remove(id);
    }

    // Method to calculate the total price of all items in the shopping list(to be
    // tested maybe better to retrieve shoppingList by Id or to automatically
    // calculate the total as a instance of ShoppingList)
    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (ShoppingListItem shoppingList : shoppingListRepository.values()) {
            totalPrice += shoppingList.getProductPrice() * shoppingList.getQuantity();
            totalPrice = Math.round(totalPrice* 1000.0) / 1000.0;
        }
        return totalPrice;
    }




    // ##############################################################################################################
    // #################################  Finance-Service Integration  ##############################################
    // ##############################################################################################################



            // create a new transaction with my shoppings by buying it      POST /finances
    public ResponseEntity<?>buyShoppings(FinanceDTO financeDTO){
        log.debug("\"forward request to \""+ apiUrl + "\"/finances\"");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/finances";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<FinanceDTO> request = new HttpEntity<FinanceDTO>(financeDTO,headers);

      
        ResponseEntity<?> response;
                try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}

  
        return response;

    }
}

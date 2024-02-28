package edu.fra.uas.service;
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

import edu.fra.uas.shoppinglistitemmodel.ShoppingListItem;
import edu.fra.uas.financemodel.FinanceDTO;
import edu.fra.uas.model.ApiError;

import org.slf4j.Logger;

@Service
public class ShoppingListService {


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ShoppingListService.class);


        //Read the URL of the external API (ShoppingListService) from the application.properties file 
        @Value("${shoppinglistservice.url}")
        String apiUrl;


        // get all shoppings        GET /shoppings
    public ResponseEntity<?> getAll(){
        log.debug("\"forward request to \""+ apiUrl + "\"/shoppings\"");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/shoppings";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
        }

                // get all shoppings        GET /shoppings
    public ResponseEntity<?> getTotalCosts(){
        log.debug("\"forward request to \""+ apiUrl + "\"/shoppings/totalcosts\"");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/shoppings/totalcosts";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
        }


         // get shoppingitem by id       GET /shoppings/{id} 
    public ResponseEntity<?> getById(Long id){
        log.debug("\"forward request to \""+ apiUrl + "\"shoppings/\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/shoppings/" + id;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);
      
        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;

    }



        // create a new shoppingitem       POST /shoppings
    public ResponseEntity<?> createShoppingListItem(ShoppingListItem shoppingListItem){
        log.debug("\"forward request to \""+ apiUrl + "\"/shoppings\"");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/shoppings";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<ShoppingListItem> request = new HttpEntity<ShoppingListItem>(shoppingListItem,headers);

      
        ResponseEntity<?> response;
                try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}

  
        return response;

    }



      //delete a shoppingitem         DELETE /shoppings/{shoppingId}
      public ResponseEntity<?> deleteShoppingItem(Long id){
        log.debug("\"forward request to \""+ apiUrl + "\"/shoppings/{id}\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/shoppings/"+ id;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

            ResponseEntity<?> response;
            try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
            } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}

  
        return response;

    
            }

                 //update shoppingitem      PUT /shoppings/{id}
        public ResponseEntity<?> update(Long id, ShoppingListItem shoppingListItem){
        log.debug("\"forward request to \""+ apiUrl + "\"/shoppings/\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/shoppings/"+ id ;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ShoppingListItem> request = new HttpEntity<ShoppingListItem>(shoppingListItem,headers);

            ResponseEntity<?> response;
            try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
            } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}

  
        return response;

    
            }


                // create a new transaction      POST /finances
    public ResponseEntity<?> byMyShoppings(FinanceDTO financeDTO){
        log.debug("\"forward request to \""+ apiUrl + "\"/finances/shoppings\"");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/finances/shoppings";
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

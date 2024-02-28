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

import edu.fra.uas.financemodel.FinanceDTO;
import edu.fra.uas.model.ApiError;

import org.slf4j.Logger;

@Service
public class FinanceService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FinanceService.class);

    //Read the URL of the external API (FinanceService) from the application.properties file 
    @Value("${financeservice.url}")
    String apiUrl;


     // get all finances      GET /finances
     public ResponseEntity<?> getAll(){
        log.debug("\"forward request to \""+ apiUrl + "\"/finances\"");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/finances";
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


    // get finance by id       GET /finances/{id}
    public ResponseEntity<?> getById(Long id){
        log.debug("\"forward request to \""+ apiUrl + "\"/finances/\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/finances/" + id;
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


    // create a new transaction      POST /finances
    public ResponseEntity<?> createFinance(FinanceDTO financeDTO){
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


      //delete a transaction        DELETE /finances/{id}
      public ResponseEntity<?> deleteFinance(Long id){
        log.debug("\"forward request to \""+ apiUrl + "\"/finances/\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/finances/"+ id;
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

            
     //update finance    PUT /finances/{id}
    public ResponseEntity<?> update(Long id, FinanceDTO financeDTO){
        log.debug("\"forward request to \""+ apiUrl + "\"/finances/\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/finances/"+ id ;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FinanceDTO> request = new HttpEntity<FinanceDTO>(financeDTO,headers);

         ResponseEntity<?> response;
        try {
        response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
            } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}
        return response;

            }



    // get all finances      GET /finances/total
    public ResponseEntity<?> getTotalCosts(){
        log.debug("\"forward request to \""+ apiUrl + "\"/finances/total\"");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/finances/total";
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

}

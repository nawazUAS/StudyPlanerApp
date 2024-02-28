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


import edu.fra.uas.model.ApiError;
import edu.fra.uas.todomodel.ToDoList;

import org.slf4j.Logger;

@Service
public class ToDoListService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FinanceService.class);

    //Read the URL of the external API (todolistservice) from the application.properties file 
    @Value("${todolistservice.url}")
    String apiUrl;

        // get all todos        GET /todos
        public ResponseEntity<?> getAll(){
            log.debug("\"forward request to \""+ apiUrl + "\"/todos\"");
            RestTemplate restTemplate = new RestTemplate();
            String url = apiUrl + "/todos";
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


                // get todos by id       GET /todos/{id}
    public ResponseEntity<?> getById(Long id){
        log.debug("\"forward request to \""+ apiUrl + "\"/todos/\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/todos/" + id;
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



    // create a new todo      POST /todos
    public ResponseEntity<?> createTodo(ToDoList todo){
        log.debug("\"forward request to \""+ apiUrl + "\"/todos\"");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/todos";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<ToDoList> request = new HttpEntity<ToDoList>(todo,headers);

      
        ResponseEntity<?> response;
                try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}

  
        return response;

    }
         //update todo      PUT /todos/{id}
    public ResponseEntity<?> update(Long id, ToDoList todo){
        log.debug("\"forward request to \""+ apiUrl + "\"/todos/\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/todos/"+ id ;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ToDoList> request = new HttpEntity<ToDoList>(todo,headers);
            
        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
           } catch (HttpClientErrorException e) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
        response = new ResponseEntity<>(apiError, apiError.getStatus());}
            
              
        return response;
    }

    //delete a todo        DELETE /todo/{id}
    public ResponseEntity<?> delete(Long id){
        log.debug("\"forward request to \""+ apiUrl + "\"/todos/\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/todos/"+ id;
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



        // get all completed todos       GET /todos?status=completed
        public ResponseEntity<?> getAllCompleted(){
            log.debug("\"forward request to \""+ apiUrl + "\"/todos?status=completed\"");
            RestTemplate restTemplate = new RestTemplate();
            String url = apiUrl + "/todos?status=completed";
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

        // get all incompleted totos        GET /todos?status=inclompleted
        public ResponseEntity<?> getAllInCompleted(){
            log.debug("\"forward request to \""+ apiUrl + "\"/todos?status=incompleted\"");
            RestTemplate restTemplate = new RestTemplate();
            String url = apiUrl + "/todos?status=incompleted";
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


         //update todo      PUT /todos/{id}
         public ResponseEntity<?> updateStatus(Long id, Boolean done){
            log.debug("\"forward request to \""+ apiUrl + "\"/todos/\""+ id +"\"status\"" );
            RestTemplate restTemplate = new RestTemplate();
            String url = apiUrl + "/todos/"+ id +"/status" ;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Boolean> request = new HttpEntity<Boolean>(done,headers);
                
            ResponseEntity<?> response;
            try {
                response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
               } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}
                
                  
            return response;
        }
}

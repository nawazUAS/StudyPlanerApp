package edu.fra.uas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

//Need this imports for the extractfact-method
import org.json.JSONArray;
import org.json.JSONObject;

import edu.fra.uas.model.ApiError;
import edu.fra.uas.model.ToDoList;
import edu.fra.uas.repository.ToDoListRepository;

import java.time.LocalDateTime;

@Service
public class ToDoListService {
    private static final Logger log = LoggerFactory.getLogger(ToDoListService.class);

    @Autowired
    ToDoListRepository toDoListRepository;

    private long nextId = 1;

    public Iterable<ToDoList> getAll() {
        log.debug("getAll");
        return toDoListRepository.values();
    }

    public ToDoList getById(Long id) {
        log.info("get todo by id: {}", id);
        return toDoListRepository.get(id);
    }

    public ToDoList createTodo(ToDoList toDo) {
        log.info("create todo: ", toDo.getTitle());
        toDo.setId(nextId++);
        toDo.setTimeStemp(LocalDateTime.now());
        String fact =extractFact();
        toDo.setDescription(toDo.getDescription() +"   [Today's fact: "+fact+"]");
        toDoListRepository.put(toDo.getId(), toDo);
        return toDo;
    }

    public ToDoList update(ToDoList todo) {
        log.info("update todo: {}", todo.getId());
        toDoListRepository.put(todo.getId(), todo);
        return todo;
    }

    public ToDoList delete(Long id) {
        log.info("delete todo: {}", id);
        return toDoListRepository.remove(id);
    }

    // ##############################################################################################################
    // #################################  Public API integration  ####################################################
    // ##############################################################################################################

            @Value("${publicapi.url}")
            String apiUrl;

     public ResponseEntity<?> getrandomfact() {
        log.debug("forward request to " + apiUrl);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl;

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> request = new HttpEntity<String>(headers);

 
        try{
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            String errorMessage = "Error: " + apiError.getMessage(); // Extract error message from ApiError
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }



    public String extractFact(){
      
        ResponseEntity<?> apiResponse = getrandomfact();
        String fact;


        if (apiResponse.getStatusCode() == HttpStatus.OK) {
            fact = (String) apiResponse.getBody(); // Extrahiere den Inhalt aus der ResponseEntity
        } else {
            // Handle error case if needed
            fact = "Error occurred: " + apiResponse.getBody();
        }
        try {
            JSONObject json = new JSONObject(fact);
            JSONArray dataArray = json.getJSONArray("data");
            String extractedFact = dataArray.getString(0);

            return extractedFact;
        } catch (Exception e) {
            // Handle JSON parsing exception
            e.printStackTrace(); 
            return "Error parsing JSON";
        }
        
    }

}

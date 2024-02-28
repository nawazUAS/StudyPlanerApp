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

import edu.fra.uas.service.ToDoListService;
import edu.fra.uas.todomodel.ToDoList;

import java.net.URI;


import org.slf4j.Logger;

import org.springframework.web.bind.annotation.CrossOrigin; 
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ToDoController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(ToDoController.class);

    @Autowired
    ToDoListService toDoListService;


@GetMapping(value = "/todos", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> list(){
    log.debug("list() is called");
    ResponseEntity<?> response = toDoListService.getAll();
    if(response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)){
        return new ResponseEntity<>("no todos", HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    
}


@GetMapping(value = "/todos/{todoId}", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> find(@PathVariable("todoId") Long todoId){
    log.debug("find() is called");
    ResponseEntity<?> response = toDoListService.getById(todoId);
    if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
        return response;
    }

    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    
}

@PostMapping(value = "/todos", 
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
    public ResponseEntity<?> create(@RequestBody ToDoList todo) {
    log.info("Create new todo: ", todo.getTitle());
    ResponseEntity<?> response = toDoListService.createTodo(todo);
        if(response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)){
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/todos"));
                return new ResponseEntity<>(response.getBody(), headers, HttpStatus.CREATED);
                }else{
    return new ResponseEntity<>(response.getBody(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}

@PutMapping(value = "/todos/{todoId}", 
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
    public ResponseEntity<?> updateTodo(@PathVariable("todoId")Long todoId,@RequestBody ToDoList todo) {
    log.info("updatetodo() is called: ");
ResponseEntity<?> response = toDoListService.update(todoId, todo);

    if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
        return response;
    }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);


}
    //to delete a todo  DELETE /todos/{id}
@DeleteMapping(value = "/todos/{todoId}", 
                produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public ResponseEntity<?> delete(@PathVariable("todoId") Long todoId) {
log.info("delete() is called");
ResponseEntity<?> response = toDoListService.delete(todoId);

if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
return response;
}
return new ResponseEntity<>(response.getBody(), HttpStatus.OK);


}



@GetMapping(value = "/todos", 
            params = "status=completed" ,
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> listCompleted(){
    log.debug("list() is called");
    ResponseEntity<?> response = toDoListService.getAllCompleted();
    if(response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)){
        return new ResponseEntity<>("no todos", HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    
}

@GetMapping(value = "/todos", 
            params = "status=incompleted" ,
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> listInCompleted(){
    log.debug("list() is called");
    ResponseEntity<?> response = toDoListService.getAllInCompleted();
    if(response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)){
        return new ResponseEntity<>("no todos", HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    
}


@PutMapping(value = "/todos/{todoId}/status", 
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
    public ResponseEntity<?> updateTodoStatus(@PathVariable("todoId")Long todoId,@RequestBody Boolean done) {
    log.info("updatetodo() is called: ");
ResponseEntity<?> response = toDoListService.updateStatus(todoId, done);

    if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
        return response;
    }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);


}
}

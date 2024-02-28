package edu.fra.uas.controller;

import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.model.ToDoList;
import edu.fra.uas.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;



@RestController
public class ToDoListController {


    private final Logger log = org.slf4j.LoggerFactory.getLogger(ToDoListController.class);

@Autowired
ToDoListService toDoListService;

    
    @GetMapping(value = "/todos", 
            produces = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
    public ResponseEntity<List<ToDoList>> list(){
    log.info("list() is called");
    Iterable<ToDoList> todoIter = toDoListService.getAll();
    List<ToDoList> todo = new ArrayList<>();
   for (ToDoList oneTodo : todoIter){
    todo.add(oneTodo);
   }
   if(todo.isEmpty()){
    return ResponseEntity.noContent().build();
   }

    return new ResponseEntity<List<ToDoList>>(todo, HttpStatus.OK);
    }

    @GetMapping(value = "/todos",  
            params = "status=completed", 
            produces = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
    public ResponseEntity<List<ToDoList>> listCompleted(){
    log.info("listallcompleted() is called");
    Iterable<ToDoList> todoIter = toDoListService.getAll();
    List<ToDoList> completedTodos = new ArrayList<>();
    for (ToDoList oneTodo : todoIter) {
        if (oneTodo.getCompleted()) { 
            completedTodos.add(oneTodo);
        }
    }

    if (completedTodos.isEmpty()) {
        return ResponseEntity.noContent().build();
    }

    return new ResponseEntity<>(completedTodos, HttpStatus.OK);
    }




    @GetMapping(value = "/todos",
                params = "status=incompleted",  
                produces = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
    public ResponseEntity<List<ToDoList>> listIncompleted(){
        log.info("listallIncompleted() is called");
        Iterable<ToDoList> todoIter = toDoListService.getAll();
    List<ToDoList> incompletedTodos = new ArrayList<>();
        for (ToDoList oneTodo : todoIter) {
            if (!oneTodo.getCompleted()) { 
                incompletedTodos.add(oneTodo);
        }
    }

    if (incompletedTodos.isEmpty()) {
    return ResponseEntity.noContent().build();
    }

return new ResponseEntity<>(incompletedTodos, HttpStatus.OK);
}

    @GetMapping(value = "/todos/{id}", 
    produces = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
        public ResponseEntity<?> getById(@PathVariable("id") Long id){
        log.info("Get todoby id" + id);
        ToDoList todo = toDoListService.getById(id);
        if(todo == null){
        return new ResponseEntity<>("No todo was found" + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ToDoList>(todo, HttpStatus.OK);
}


@PostMapping(value = "/todos",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody ToDoList todo){
        log.info("Create new todo:" + todo.getTitle());

        String detail= null;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail);
        pd.setInstance(URI.create("/todos"));
        pd.setTitle("Todo creation error");

        if(todo.getTitle() == null || todo.getTitle().isEmpty()){
            detail="title cannot be empty or null";
            return ResponseEntity.unprocessableEntity().body(pd);}
        else if(todo.getCompleted() == null ){
            detail="completed cannot be empty or null";
            return ResponseEntity.unprocessableEntity().body(pd);}
         else if(todo.getDescription() == null || todo.getDescription().isEmpty()){
                detail="description cannot be empty or null";
                return ResponseEntity.unprocessableEntity().body(pd);}


        toDoListService.createTodo(todo);
        return new ResponseEntity<ToDoList>(todo, HttpStatus.CREATED);
    } 

    @PutMapping(value = "/todos/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody ToDoList newTodo, @PathVariable("id") Long id){
        log.info("update todo:" + newTodo.getTitle());
        ToDoList oldTodo = toDoListService.getById(id);
        
        String detail= null;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail);
        pd.setInstance(URI.create("/todos"));
        pd.setTitle("Todo update error");

        if(newTodo.getTitle() == null || newTodo.getTitle().isEmpty()){
            detail="title cannot be empty or null";
            return ResponseEntity.unprocessableEntity().body(pd);}
        else if(newTodo.getCompleted() == null ){
            detail="Completed cannot be empty or null";
            return ResponseEntity.unprocessableEntity().body(pd);}
         else if(newTodo.getDescription() == null || newTodo.getDescription().isEmpty()){
                detail="description cannot be empty or null";
                return ResponseEntity.unprocessableEntity().body(pd);}


        oldTodo.setCompleted(newTodo.getCompleted());
        oldTodo.setDescription(newTodo.getDescription());
        oldTodo.setTitle(newTodo.getTitle());
        toDoListService.update(oldTodo);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/todos/" + id));
        return new ResponseEntity<ToDoList>(oldTodo, HttpStatus.CREATED);
    } 

    @DeleteMapping(value = "/todos/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> delete(@PathVariable("id") Long id){
log.debug("delete() is called");
ToDoList todo = toDoListService.delete(id);
if(todo == null){
return ResponseEntity.notFound().build();
}
return new ResponseEntity<ToDoList>(todo, HttpStatus.OK);
}


@PutMapping(value = "/todos/{id}/status",
produces = MediaType.APPLICATION_JSON_VALUE,
consumes = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public ResponseEntity<?> updateStatus(@RequestBody Boolean done, @PathVariable("id") Long id){
ToDoList oldTodo = toDoListService.getById(id);
log.info("update todo:" + oldTodo.getTitle());
String detail= null;
ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail);
pd.setInstance(URI.create("/todos"));
pd.setTitle("Todo update error");

if(done == null){
detail="done cannot be null be empty or null";

return ResponseEntity.unprocessableEntity().body(pd);}


oldTodo.setCompleted(done);
HttpHeaders headers = new HttpHeaders();
headers.setLocation(URI.create("/todos/" + id));
return new ResponseEntity<ToDoList>(oldTodo, HttpStatus.CREATED);
} 


}

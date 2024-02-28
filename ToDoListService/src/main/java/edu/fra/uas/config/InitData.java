package edu.fra.uas.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.fra.uas.model.ToDoList;
import edu.fra.uas.service.ToDoListService;
import jakarta.annotation.PostConstruct;

@Component
public class InitData {

        private final Logger log = org.slf4j.LoggerFactory.getLogger(InitData.class);

        @Autowired
        ToDoListService toDoListService;

     @PostConstruct
    public void init() {
        log.debug("### Initialize Data ###");

        ToDoList todo1 = new ToDoList();
        todo1.setCompleted(false);
        todo1.setDescription("Mathe machen");
        todo1.setTitle("Hausaufgaben");
        toDoListService.createTodo(todo1);

        log.debug("### Data initialized ###");
    }

}

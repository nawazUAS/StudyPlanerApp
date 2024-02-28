package edu.fra.uas.todomodel;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToDoList {
     private static final Logger log = LoggerFactory.getLogger(ToDoList.class);

    private long id;
    private String title;
    private String description;
    private LocalDateTime timeStemp;
    private boolean completed;

    public ToDoList(){
        log.debug("todo list created without values");
    }

    public ToDoList(long id, String title, String description, LocalDateTime timeStemp, boolean completed){

        this.id=id;
        this.completed=completed;
        this.title=title;
        this.description=description;
        this.timeStemp=timeStemp;
        this.completed=completed;
        }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public void setTimeStemp(LocalDateTime timeStemp) {
        this.timeStemp = timeStemp;
    }
    public LocalDateTime getTimeStemp() {
        return timeStemp;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public Boolean getCompleted(){
        return this.completed;
    }
}

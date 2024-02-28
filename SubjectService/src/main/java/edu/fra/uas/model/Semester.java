package edu.fra.uas.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Semester implements java.io.Serializable{

    private static final Logger log = LoggerFactory.getLogger(Subject.class);

    private long id;
    private String name;
    private LocalDate date;


    private HashMap<Long, ArrayList<Subject>> subjectsInSemester;

    public Semester() {
        log.debug("Semester created without values");
    }

    public Semester(long id, String name, LocalDate date , HashMap<Long, ArrayList<Subject>> subjectsInSemester){
        this.id=id;
        this.name=name;
        this.subjectsInSemester=subjectsInSemester;
        this.date=date;
        log.debug("Semester created: "+ getId());
        
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setSubjectsinSemester(HashMap<Long, ArrayList<Subject>> subjectsInSemester) {
        this.subjectsInSemester = subjectsInSemester;
    }
    public HashMap<Long, ArrayList<Subject>> getSubjectsinSemester() {
    return subjectsInSemester;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalDate getDate() {
        return date;
    }
}

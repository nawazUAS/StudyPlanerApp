package edu.fra.uas.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Subject implements java.io.Serializable{
         private static final Logger log = LoggerFactory.getLogger(Subject.class);
         
    private long id;
    private String subjectName;
    private double grade;

    public Subject(){
        log.debug("Semester created without values");
    }


    public Subject(long id, String subjectName, double grade) {

        this.id=id;
        this.grade=grade;
        this.subjectName=subjectName;
        log.debug("Subject created: "+ getId());
    }

public void setGrade(double grade) {
    this.grade = grade;
}
public double getGrade() {
    return grade;
}
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public String getSubjectName() {
        return subjectName;
    }

}

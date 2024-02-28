package edu.fra.uas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.model.Semester;
import edu.fra.uas.model.Subject;
import edu.fra.uas.repository.SemesterRepository;

@Service
public class SemesterService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SemesterService.class);

@Autowired
SemesterRepository semesterRepository;

    private long semesterID=1;
    private long subjectID=1;


    public Iterable<Semester> getAll() {
        log.debug("getAll");
        return semesterRepository.values();
    }


    public Semester createSemester(String semesterName) {
        log.info("createSemester: ",semesterName);
        Semester semester = new Semester();
        semester.setId(semesterID++);
        semester.setName(semesterName);
        semesterRepository.put(semester.getId(), semester);
        return semester;
    }


    public Semester getById(Long id){
        log.info("get semester by id: {}", id);
        return semesterRepository.get(id);
    }

    public Semester update(Semester semester) {
        log.info("update semester: {}", semester);
        semesterRepository.put(semester.getId(), semester);
        return semester;
    }

    public Semester delete(Long id){
        log.info("delete semester: {}", id);
        return semesterRepository.remove(id);
    }


    public Semester addSubject(Long semesterId, Subject subject) {
        log.info("addSubject: semesterId={}, subject={}", semesterId, subject.getSubjectName());
       Semester semester = semesterRepository.get(semesterId);

       // check if a semester has subjects in it
        if(semester.getSubjectsinSemester() == null) {
            semester.setSubjectsinSemester(new HashMap<>());
        }


        HashMap<Long, ArrayList<Subject>> mySubjects = semester.getSubjectsinSemester();

        //get the subjectlist or create a new ArrayList if there is none
        ArrayList<Subject> subjectList = mySubjects.getOrDefault(semesterId, new ArrayList<>());

        subjectList.add(subject);
        subject.setId(subjectID++);
        mySubjects.put(semesterId, subjectList);


        semester.setSubjectsinSemester(mySubjects);
        return semester;

    }

    public Semester removeSubject(Long semesterId, Long subjectId) {
        log.info("removeSubject: semesterId={}, subjectId={}", semesterId, subjectId);
        Semester semester = semesterRepository.get(semesterId);

        // check if a semester has subjects in it
    if (semester.getSubjectsinSemester() != null) {
        HashMap<Long, ArrayList<Subject>> mySubjects = semester.getSubjectsinSemester();

        // get the subjectlist or create a new ArrayList if there is none
        ArrayList<Subject> subjectList = mySubjects.getOrDefault(semesterId, new ArrayList<>());

        // Use Iterator to remove the subject with the specified subjectId, because of the ArrayList in the HashMap
        Iterator<Subject> iterator = subjectList.iterator();
        while (iterator.hasNext()) {
            Subject subject = iterator.next();
            if (subject.getId()==(subjectId)) {
                iterator.remove();
                break; //after the specified subject is removed, the iterator stops to move on 
            }
        }

        // update the subjects in the semester in which the wished subject is removed now
        mySubjects.put(semesterId, subjectList);
        semester.setSubjectsinSemester(mySubjects);
    }

    return semester;
    }




    public double averagePointsPerSemester(long id){
        Semester semester= getById(id);
        HashMap<Long, ArrayList<Subject>> mySubjects = semester.getSubjectsinSemester();
        ArrayList<Double> allGrades = new ArrayList<>();
 
        for (ArrayList<Subject> subjectList : mySubjects.values()){

         for(Subject subject: subjectList){
             allGrades.add(subject.getGrade());
         }
             } 
                    double sum=0;
 
                if(!allGrades.isEmpty()){
        
                    for(double oneGrade : allGrades){
                 sum+=oneGrade;
                  }
                    }                          
         double averagePoints = Math.round(sum/allGrades.size()* 1000.0) / 1000.0;
 
 
         return averagePoints;
 
     }

     public ArrayList<Subject> getAllSubjects(long id) {
        log.debug("getAllSubjects");
        Semester semester =getById(id);
        ArrayList<Subject> subjects = new ArrayList<>();
        for (ArrayList<Subject> subjectList : semester.getSubjectsinSemester().values()) {
            subjects.addAll(subjectList);
        }

        return subjects;
    }


}

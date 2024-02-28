package edu.fra.uas.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.model.Semester;
import edu.fra.uas.model.SemesterDTO;
import edu.fra.uas.model.Subject;
import edu.fra.uas.service.SemesterService;

@RestController
public class SemesterController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(SemesterController.class);

    @Autowired
    SemesterService semesterService;

    @GetMapping(value = "/semesters", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<List<Semester>> list(){
    log.debug("list() is called");
    Iterable<Semester> semesterIter = semesterService.getAll();
    List<Semester> semester = new ArrayList<>();
   for (Semester oneSemester : semesterIter){
    semester.add(oneSemester);
   }

   if(semester.isEmpty()){
    return ResponseEntity.noContent().build();
   }

    return new ResponseEntity<List<Semester>>(semester, HttpStatus.OK);
    
}


@GetMapping(value = "/semesters/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public ResponseEntity<?> find(@PathVariable("id") Long semesterId){
    log.debug("find() is called");
    Semester semester = semesterService.getById(semesterId);
    if(semester == null){
        return ResponseEntity.notFound().build();
    }
    return new ResponseEntity<Semester>(semester, HttpStatus.OK);
}


    @PostMapping(value = "/semesters", 
                 consumes = MediaType.APPLICATION_JSON_VALUE, 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody SemesterDTO semesterDTO) {
        log.info("Create new semester: ", semesterDTO.getName());
                    String detail=null;
        if (semesterDTO.getName().isEmpty() || semesterDTO.getName() == null) {
            detail = "Semester name must not be null or empty";
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail); 
            pd.setInstance(URI.create("/semesters"));
            pd.setTitle("Semester creation error");
            return ResponseEntity.unprocessableEntity().body(pd);
        }            

        Semester semester =  semesterService.createSemester(semesterDTO.getName());
        semester.setDate(LocalDate.of(semesterDTO.getYear(), semesterDTO.getMonth(), 1));

        //Hateoas enhancement: After creating a semester the user will get a link to put subjects in this semester as next step
        Link selfLink = linkTo(methodOn(SemesterController.class).create(semesterDTO)).withRel(IanaLinkRelations.FIRST); 
        Link subjectsLink = linkTo(methodOn(SemesterController.class).addSubject(semester.getId(), new Subject())).withRel("addSubject").withRel(IanaLinkRelations.NEXT);
        EntityModel<Semester> responseModel = EntityModel.of(semester);
        responseModel.add(selfLink,subjectsLink);

        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }
    @PutMapping(value = "/semesters/{id}", 
                consumes = MediaType.APPLICATION_JSON_VALUE, 
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
        public ResponseEntity<?> updateSemester(@PathVariable("id") Long semesterId, @RequestBody SemesterDTO semesterDTO) {
        log.info("update Semester: ", semesterId);
        Semester semester = semesterService.getById(semesterId);
            if (semester == null) {
        return new ResponseEntity<> ("Semester was not found for id {" + semesterId + "}", HttpStatus.NOT_FOUND);
    }
    String detail=null;
    if(semesterDTO.getName().isEmpty() || semesterDTO.getName().isBlank()){
        detail="name cannot be null";
        
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail); 
        pd.setInstance(URI.create("/semesters"));
        pd.setTitle("JSON Object Error");
        return ResponseEntity.unprocessableEntity().body(pd);}
    
    semester.setName(semesterDTO.getName());
    semester.setDate(LocalDate.of(semesterDTO.getYear(),semesterDTO.getMonth(),1));
    semesterService.update(semester);
    return new ResponseEntity<Semester>(semester, HttpStatus.OK);
}

        @DeleteMapping(value = "/semesters/{id}",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.info("Delete semester: ", id);
        Semester semester = semesterService.delete(id);
        if (semester == null) {
            return new ResponseEntity<> ("Semester was not found for id {" + id + "}", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Semester>(semester, HttpStatus.OK);
    }

        @PutMapping(value = "/semesters/{id}/subjects", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addSubject(@PathVariable("id") Long semesterId, @RequestBody Subject subject) {
        log.info("add Subject: ", semesterId);
        Semester semester = semesterService.getById(semesterId);
        if (semester == null) {
            return new ResponseEntity<> ("Semester was not found for id {" + semesterId + "}", HttpStatus.NOT_FOUND);
        }
        String detail=null;

        if(subject.getGrade() >6||subject.getGrade()<1){
            detail="Grade must be between 1 and 6";
        }


        if (detail != null) {
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail); 
            pd.setInstance(URI.create("/subject"));
            pd.setTitle("JSON Object Error");
            return ResponseEntity.unprocessableEntity().body(pd);
        }

        semesterService.addSubject(semesterId, subject);
        return new ResponseEntity<Semester>(semester, HttpStatus.OK);
    }


    @PatchMapping(value = "/semesters/{id}/subjects/{subjectId}", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
        public ResponseEntity<?> deleteSubject(@PathVariable("id") Long semesterId, @PathVariable("subjectId") Long subjectId) {
        log.info("delete subject: ", subjectId);
        Semester semester = semesterService.getById(semesterId);
            if (semester == null) {
                return new ResponseEntity<> ("Semester was not found for id {" + semesterId + "}", HttpStatus.NOT_FOUND);
        }
        semester = semesterService.removeSubject(semesterId, subjectId);
        return new ResponseEntity<Semester>(semester, HttpStatus.OK);
    }


    @GetMapping(value = "/semesters/{id}/averagepoints",
            produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public ResponseEntity<?> findAveragePointsPerSemester(@PathVariable("id") Long semesterId){
    log.debug("findAveragePointsPerSemester() is called");
    Semester semester = semesterService.getById(semesterId);

    if(semester == null){
        return ResponseEntity.notFound().build();
    } 
    if(semester.getSubjectsinSemester() == null){
        return ResponseEntity.noContent().build();
    }
       double averagepoints = semesterService.averagePointsPerSemester(semesterId);
    return new ResponseEntity<Double>(averagepoints, HttpStatus.OK);
}



@GetMapping(value = "/semesters/{id}/subjects",
            produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
    public ResponseEntity<?> listSubjects(@PathVariable("id") Long semesterId){
    log.debug("findAveragePointsPerSemester() is called");
    ArrayList<Subject> subjects = semesterService.getAllSubjects(semesterId);

        if(subjects == null){
        return ResponseEntity.notFound().build();
        } 


    return new ResponseEntity<ArrayList<Subject>>(subjects, HttpStatus.OK);
}

}

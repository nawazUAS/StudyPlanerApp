package edu.fra.uas.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.service.SubjectService;
import edu.fra.uas.subjectservicemodel.SemesterDTO;
import edu.fra.uas.subjectservicemodel.Subject;

import org.springframework.web.bind.annotation.CrossOrigin; 
@CrossOrigin
@RestController
@RequestMapping("/api")
public class SemesterController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(SemesterController.class);

    @Autowired
    SubjectService semesterService;


@GetMapping(value = "/semesters", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> list(){
    log.debug("list() is called");
    ResponseEntity<?> response = semesterService.getAll();
    if(response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)){
        return new ResponseEntity<>("no semesters", HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    
}

@GetMapping(value = "/semesters/{semesterId}", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> find(@PathVariable("semesterId") Long semesterId){
    log.debug("find() is called");
    ResponseEntity<?> response = semesterService.getById(semesterId);
    if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
        return response;
    }

    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    
}

 @PostMapping(value = "/semesters", 
                 consumes = MediaType.APPLICATION_JSON_VALUE, 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createSemester(@RequestBody SemesterDTO semesterDTO) {
        log.info("Create new semester: ", semesterDTO.getName());
        ResponseEntity<?> response = semesterService.createSemester(semesterDTO);
        if(response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)){
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/semesters"));
            return new ResponseEntity<>(response.getBody(), headers, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(response.getBody(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @DeleteMapping(value = "/semesters/{semesterId}", 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("semesterId") Long semesterId) {
        log.info("delete() is called");
        ResponseEntity<?> response = semesterService.deleteSemester(semesterId);

        if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
          return response;
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    

}

 @PutMapping(value = "/semesters/{semesterId}/subjects", 
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addSubject(@PathVariable("semesterId") Long semesterId,@RequestBody Subject subject) {
        log.info("addSubject() is called: ");
   

        ResponseEntity<?> response = semesterService.addSubject(semesterId, subject);

        if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
          return response;
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
  

    }

     @PatchMapping(value = "/semesters/{semesterId}/subjects/{subjectId}", 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> removeSubject(@PathVariable("semesterId") Long semesterId,@PathVariable("subjectId")Long subjectId) {
        log.info("removeSubject() is called: ");
   

        ResponseEntity<?> response = semesterService.removeSubject(semesterId, subjectId);
        if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
          return response;
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
  

    }



@GetMapping(value = "/semesters/{semesterId}/averagepoints", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> findAveragePointsPerSemester(@PathVariable("semesterId") Long semesterId){
    log.debug("findAveragePointsPerSemester() is called");
    ResponseEntity<?> response = semesterService.avaragePontsPerSemester(semesterId);
    if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
        return response;
    }

    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    
}

@GetMapping(value = "/semesters/{semesterId}/subjects", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> getAllSubjects(@PathVariable("semesterId") Long semesterId){
    log.debug("findAveragePointsPerSemester() is called");
    ResponseEntity<?> response = semesterService.getAllSubjects(semesterId);
    if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
        return response;
    }

    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    
}
}

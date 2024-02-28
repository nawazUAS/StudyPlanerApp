package edu.fra.uas.service;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import edu.fra.uas.model.ApiError;
import edu.fra.uas.subjectservicemodel.SemesterDTO;
import edu.fra.uas.subjectservicemodel.Subject;

@Service
public class SubjectService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SubjectService.class);


    //Read the URL of the external API (SubjectService) from the application.properties file 
    @Value("${subjectservice.url}")
    String apiUrl;


    // get all semesters        GET /semesters
    public ResponseEntity<?> getAll(){
        log.debug("\"forward request to \""+ apiUrl + "\"/semesters\"");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/semesters";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
        }

    
    // get semester by id       GET /semesters/{id} 
    public ResponseEntity<?> getById(Long id){
        log.debug("\"forward request to \""+ apiUrl + "\"/semesters/\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/semesters/" + id;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);
      
        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;

    }


    // create a new semester        POST /semester
    public ResponseEntity<?> createSemester(SemesterDTO semesterDTO){
        log.debug("\"forward request to \""+ apiUrl + "\"/semester\"");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/semesters";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<SemesterDTO> request = new HttpEntity<SemesterDTO>(semesterDTO,headers);

      
        ResponseEntity<?> response;
                try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}

  
        return response;

    }

    //delete a semester         DELETE /semesters/{id}
    public ResponseEntity<?> deleteSemester(Long id){
        log.debug("\"forward request to \""+ apiUrl + "\"/semesters/\""+ id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/semesters/"+ id;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

            ResponseEntity<?> response;
            try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
            } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}

  
        return response;

    
            }

        //update Semester       PUT   /semesters/{id}
        public ResponseEntity<?> updateSemester(Long id, SemesterDTO semesterDTO){
            log.debug("\"forward request to \""+ apiUrl + "\"/semesters/\""+ id);
            RestTemplate restTemplate = new RestTemplate();
            String url = apiUrl + "/semesters/"+ id;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<SemesterDTO> request = new HttpEntity<SemesterDTO>(semesterDTO,headers);
    
                ResponseEntity<?> response;
                try {
                response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
                } catch (HttpClientErrorException e) {
                ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
                response = new ResponseEntity<>(apiError, apiError.getStatus());}
    
      
            return response;
    
        
                }

        //add a Subject to a semester       PUT /semesters/{semesterId}/subject
        public ResponseEntity<?> addSubject(Long id, Subject subject){
        log.debug("\"forward request to \""+ apiUrl + "\"/semesters/\""+ id+"/subjects");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/semesters/"+ id + "/subjects";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Subject> request = new HttpEntity<Subject>(subject,headers);

            ResponseEntity<?> response;
            try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
            } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}

  
        return response;

    
            }

                
        //remove a Subject from a semester       PATCH /semesters/{semesterId}/subject
        public ResponseEntity<?> removeSubject(Long id, Long subjectId){
        log.debug("\"forward request to \""+ apiUrl + "\"/semesters/\""+ id+"/subjects/"+ subjectId);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/subjectservice/semesters/"+ id + "/subjects/" + subjectId;
        HttpHeaders headers = new HttpHeaders();
   
        HttpEntity<String> request = new HttpEntity<String>(headers);
    



            ResponseEntity<?> response;
            try {
            response = restTemplate.exchange(url, HttpMethod.PATCH, request, String.class);
            } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());}

  
        return response;
    
    
    }

    //get average points from a semester

    public ResponseEntity<?> avaragePontsPerSemester(Long id){
        log.debug("\"forward request to \""+ apiUrl + "\"/semesters/\""+ id+"/averagepoints");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/semesters/"+ id + "/averagepoints";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

         ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    
    }


        // get all semesters        GET /semesters/{id}/subjects
        public ResponseEntity<?> getAllSubjects(Long id){
            log.debug("\"forward request to \""+ apiUrl + "\"/semesters\""+id+ "/subjects");
            RestTemplate restTemplate = new RestTemplate();
            String url = apiUrl + "/semesters/" + id + "/subjects";
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> request = new HttpEntity<String>(headers);
    
            ResponseEntity<?> response;
            try {
                response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            } catch (HttpClientErrorException e) {
                ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
                response = new ResponseEntity<>(apiError, apiError.getStatus());
            }
            return response;
            }

}

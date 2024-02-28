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

import edu.fra.uas.financemodel.FinanceDTO;
import edu.fra.uas.service.FinanceService;

import java.net.URI;


import org.slf4j.Logger;

import org.springframework.web.bind.annotation.CrossOrigin; 
@CrossOrigin
@RestController
@RequestMapping("/api")
public class FinanceController {
    private final Logger log = org.slf4j.LoggerFactory.getLogger(FinanceController.class);

    @Autowired
    FinanceService financeService;


@GetMapping(value = "/finances", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> list(){
log.debug("list() is called");
ResponseEntity<?> response = financeService.getAll();
if(response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)){
return new ResponseEntity<>("no finances", HttpStatus.NO_CONTENT);
}

return new ResponseEntity<>(response.getBody(), HttpStatus.OK);

}



@GetMapping(value = "/finances/{financeId}", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> find(@PathVariable("financeId") Long financeId){
    log.debug("find() is called");
    ResponseEntity<?> response = financeService.getById(financeId);
    if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
        return response;
    }

    return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    
}



@PostMapping(value = "/finances", 
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public ResponseEntity<?> create(@RequestBody FinanceDTO financeDTO) {
log.info("Create new finance: ", financeDTO.getDescription());
ResponseEntity<?> response = financeService.createFinance(financeDTO);
if(response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)){
HttpHeaders headers = new HttpHeaders();
headers.setLocation(URI.create("/finances"));
return new ResponseEntity<>(response.getBody(), headers, HttpStatus.CREATED);
}else{
return new ResponseEntity<>(response.getBody(), HttpStatus.UNPROCESSABLE_ENTITY);
}

}

@PutMapping(value = "/finances/{financeId}", 
            consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public ResponseEntity<?> updateFinance(@PathVariable("financeId") Long financeId,@RequestBody FinanceDTO financeDTO) {
log.info("updatefinance() is called: ");


ResponseEntity<?> response = financeService.update(financeId, financeDTO);

if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
return response;
}
return new ResponseEntity<>(response.getBody(), HttpStatus.OK);


}

@DeleteMapping(value = "/finances/{financeId}", 
                produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public ResponseEntity<?> deleteFinance(@PathVariable("financeId") Long financeId) {
log.info("deletefinance() is called");
ResponseEntity<?> response =financeService.deleteFinance(financeId);

if(response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
return response;
}
return new ResponseEntity<>(response.getBody(), HttpStatus.OK);


}



@GetMapping(value = "/finances/total", 
            produces = MediaType.APPLICATION_JSON_VALUE)

@ResponseBody
public ResponseEntity<?> getTotalCosts(){
log.debug("list() is called");
ResponseEntity<?> response = financeService.getTotalCosts();
if(response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)){
return new ResponseEntity<>("no finances", HttpStatus.NO_CONTENT);
}

return new ResponseEntity<>(response.getBody(), HttpStatus.OK);

}

}

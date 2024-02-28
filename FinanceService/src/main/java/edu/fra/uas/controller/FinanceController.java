package edu.fra.uas.controller;

import edu.fra.uas.model.Finance;
import edu.fra.uas.model.FinanceDTO;
import edu.fra.uas.service.FinanceService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FinanceController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(FinanceController.class);

    @Autowired
    private FinanceService financeService;

    @GetMapping(value = "/finances", 
                produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Finance>> list() {
        log.info("list() is called");
        Iterable<Finance> financeIter = financeService.getAll();
        List<Finance> finances = new ArrayList<>();
        financeIter.forEach(finances::add);

        if (finances.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(finances, HttpStatus.OK);
    }

    @GetMapping(value = "/finances/{id}", 
                produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Finance> getById(@PathVariable("id") Long id) {
        log.info("Get finance by id: {}", id);
        Finance finance = financeService.getById(id);
        if (finance == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(finance, HttpStatus.OK);
    }

    @PostMapping(value = "/finances", 
                 produces =  MediaType.APPLICATION_JSON_VALUE, 
                consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Finance> create(@RequestBody FinanceDTO financeDTO) {
        log.info("Create new finance: {}", financeDTO.getDescription());
        Finance newFinance = financeService.createFinance(financeDTO.getDescription(), financeDTO.getAmount());
        newFinance.setCategory(financeDTO.getCategory());
        newFinance.setType(financeDTO.getType());
        return new ResponseEntity<>(newFinance, HttpStatus.CREATED);
    }

    @PutMapping(value = "/finances/{id}", 
                consumes =  MediaType.APPLICATION_JSON_VALUE, 
                produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Finance> update(@RequestBody FinanceDTO financedDTO, @PathVariable("id") Long id) {
        log.info("Update finance: {}", financedDTO.getDescription());
        Finance existingFinance = financeService.getById(id);
        if (existingFinance == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingFinance.setDescription(financedDTO.getDescription());
        existingFinance.setAmount(financedDTO.getAmount());
        existingFinance.setCategory(financedDTO.getCategory());
        existingFinance.setType(financedDTO.getType());
        financeService.update(existingFinance);
        return new ResponseEntity<>(existingFinance, HttpStatus.OK);
    }

    @DeleteMapping(value = "/finances/{id}", 
                    produces =  MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Finance> delete(@PathVariable("id") Long id) {
        log.info("Delete finance: {}", id);
        Finance deletedFinance = financeService.delete(id);
        if (deletedFinance == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(deletedFinance, HttpStatus.OK);
    }

    @PostMapping(value = "/finance/updateTotal/{id}", produces = "application/json")
    public ResponseEntity<Finance> updateFinanceTotal(@PathVariable("id") Long id, @RequestBody double amount) {
        log.info("Update finance total for id: {}", id);
        Finance updatedFinance = financeService.updateFinanceTotal(id, amount);
        if (updatedFinance == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedFinance, HttpStatus.OK);
    }

    @GetMapping(value = "/finances/total", produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getTotalFinance() {
        log.info("Get total finance amount");
        double totalFinance = financeService.calculateTotalFinance();
        return new ResponseEntity<>(totalFinance, HttpStatus.OK);
    }
}

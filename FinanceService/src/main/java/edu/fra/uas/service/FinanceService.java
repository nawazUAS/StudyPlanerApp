package edu.fra.uas.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.model.Finance;
import edu.fra.uas.repository.FinanceRepository;

@Service
public class FinanceService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FinanceService.class);

    private long financeId = 1;

    @Autowired
    FinanceRepository financeRepository;

    public Iterable<Finance> getAll() {
        log.debug("getAll");
        return financeRepository.values();
    }

    public Finance getById(Long id) {
        log.info("get finance by id: {}", id);
        return financeRepository.get(id);
    }

    public Finance createFinance(String description, double amount) {
        log.info("createFinance: {}", description);
        Finance finance = new Finance();
        finance.setId(financeId++);
        finance.setDescription(description);
        finance.setAmount(amount);
        finance.setDate(LocalDate.now());
        financeRepository.put(finance.getId(), finance);
        return finance;
    }

    public Finance update(Finance finance) {
        log.info("update finance: {}", finance);
        financeRepository.put(finance.getId(), finance);
        return finance;
    }

    public Finance delete(Long id) {
        log.info("delete finance: {}", id);
        return financeRepository.remove(id);
    }

    

     public double calculateTotalAmount() {
        log.debug("calculateTotalAmount");
        double total = 0;
        for (Finance finance : financeRepository.values()) {
            total += finance.getAmount();
        }
        return total;
    }

    public List<Finance> getFinancesByType(String type) {
        log.debug("getFinancesByType: {}", type);
        List<Finance> filteredFinances = new ArrayList<>();
        for (Finance finance : financeRepository.values()) {
            if (finance.getType().equalsIgnoreCase(type)) {
                filteredFinances.add(finance);
            }
        }
        return filteredFinances;
    }

    public List<Finance> getFinancesInPeriod(LocalDate startDate, LocalDate endDate) {
        log.debug("getFinancesInPeriod: {} to {}", startDate, endDate);
        List<Finance> filteredFinances = new ArrayList<>();
        for (Finance finance : financeRepository.values()) {
            if (!finance.getDate().isBefore(startDate) && !finance.getDate().isAfter(endDate)) {
                filteredFinances.add(finance);
            }
        }
        return filteredFinances;
    }

    public double calculateTotalByCategory(String category) {
        log.debug("calculateTotalByCategory: {}", category);
        double total = 0;
        for (Finance finance : financeRepository.values()) {
            if (finance.getCategory().equalsIgnoreCase(category)) {
                total += finance.getAmount();
            }
        }
        return total;
    }

    public Finance updateFinanceTotal(Long id, double amount) {
        Finance finance = financeRepository.get(id);
        if (finance != null) {
            double newTotal = finance.getFinanceTotal() + amount;
            finance.setFinanceTotal(newTotal);
            financeRepository.put(id, finance);
        }
        return finance;
    }

    public double calculateTotalFinance() {

        double total = 0;
        for (Finance finance : financeRepository.values()) {
            total += finance.getAmount(); 
            total = Math.round(total* 1000.0) / 1000.0;
        }
        return total;
    }
}

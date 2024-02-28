package edu.fra.uas.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.fra.uas.model.Finance;
import edu.fra.uas.service.FinanceService;
import jakarta.annotation.PostConstruct;


@Component
public class InitData {
    private final Logger log = org.slf4j.LoggerFactory.getLogger(InitData.class);

    @Autowired
    private FinanceService financeService;

    @PostConstruct
    public void init() {
        log.debug("### Initialize Finance Data ###");

        log.debug("create first finance record");
        Finance  finance1= financeService.createFinance("Gehaltseingang",2500);
        finance1.setType("Einkommen");
        finance1.setCategory("Arbeit");


        log.debug("create second finance record");
        Finance finance2 =  financeService.createFinance("Miete" , -800.00);
        finance2.setType("Ausgabe");
        finance2.setCategory("Wohnung");
 

        log.debug("### Finance Data initialized ###");
    }
}

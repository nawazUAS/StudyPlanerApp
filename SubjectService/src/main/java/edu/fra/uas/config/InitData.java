package edu.fra.uas.config;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class InitData {

        private final Logger log = org.slf4j.LoggerFactory.getLogger(InitData.class);

     @PostConstruct
    public void init() {
        log.debug("### Initialize Data ###");

        log.debug("### Data initialized ###");
    }
}

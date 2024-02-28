package edu.fra.uas.config;


import java.time.LocalDate;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.fra.uas.model.Semester;
import edu.fra.uas.model.Subject;
import edu.fra.uas.service.SemesterService;
import jakarta.annotation.PostConstruct;

@Component
public class InitData {

        private final Logger log = org.slf4j.LoggerFactory.getLogger(InitData.class);
        
        @Autowired
        SemesterService semesterService;

     @PostConstruct
    public void init() {
        log.debug("### Initialize Data ###");

        log.debug("create 1. Semester");
        Semester semester = 
        semesterService.createSemester("Wintersemster: WS23/24");
        semester.setDate(LocalDate.of(2023,11,1));

        log.debug("create 2. Semester");
        Semester semester2 =
        semesterService.createSemester("Sommersemester: SS24");
        semester2.setDate(LocalDate.of(2024,06,1));

        log.debug("create first subject for first semester");
        Subject subject = new Subject();
        subject.setSubjectName("OOP");
        subject.setGrade(2);

        log.debug("create second subject for first semester");
        Subject subject2 = new Subject();
        subject2.setSubjectName("Webanwendungen");
        subject2.setGrade(1);

        log.debug("create third subject for second semester");
        Subject subject3 = new Subject();
        subject3.setSubjectName("Englisch");
        subject3.setGrade(3);

        log.debug("create fourth subject for second semester");
        Subject subject4 = new Subject();
        subject4.setSubjectName("Analysis");
        subject4.setGrade(4);


        log.debug("add first subject to first semester");
        semesterService.addSubject(semester.getId(), subject);

        log.debug("add second subject to first semester");
        semesterService.addSubject(semester.getId(), subject2);

        
        log.debug("add third subject to second semester");
        semesterService.addSubject(semester2.getId(), subject3);

        log.debug("add fourth subject to second semester");
        semesterService.addSubject(semester2.getId(), subject4);


        log.debug("### Data initialized ###");
    }
}

package edu.fra.uas.controller;

import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.service.Eventservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.slf4j.Logger;


@RestController
@RequestMapping("/event")
public class EventController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(EventController.class);

@Autowired
Eventservice eventservice;


}

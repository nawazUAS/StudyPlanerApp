package edu.fra.uas.repository;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import edu.fra.uas.model.ToDoList;

@Repository
public class ToDoListRepository extends HashMap<Long, ToDoList>  {

}

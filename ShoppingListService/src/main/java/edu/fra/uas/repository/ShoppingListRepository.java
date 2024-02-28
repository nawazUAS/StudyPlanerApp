package edu.fra.uas.repository;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import edu.fra.uas.model.ShoppingListItem;

@Repository
public class ShoppingListRepository extends HashMap<Long, ShoppingListItem> {

}

package edu.fra.uas.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.fra.uas.model.ShoppingListItem;
import edu.fra.uas.service.ShoppingListService;
import jakarta.annotation.PostConstruct;

@Component
public class InitData {

  // Logger für Protokollierung initialisieren
  private final Logger log = org.slf4j.LoggerFactory.getLogger(InitData.class);

   @Autowired
    ShoppingListService shoppingListService;

  // Diese Methode wird nach der Konstruktion des Beans aufgerufen
  @PostConstruct
  public void init() {

    // Beginn der Initialisierung
    log.debug("### Initialize Data ###");

    // Erstellung und Initialisierung von "Wheat products"

    // Erstes Produkt erstellen und initialisieren
    log.debug("Creating the first product");
    ShoppingListItem product1 = new ShoppingListItem();
    product1.setProductName("Pasta");
    product1.setQuantity(2);
    product1.setProductPrice(1.99);
    product1.setCategory("Wheat products");
    shoppingListService.createShoppingListItem(product1);

    // Zweites Produkt erstellen und initialisieren
    log.debug("Creating the second product");
    ShoppingListItem product2 = new ShoppingListItem();
    product2.setProductName("Sourdough bread");
    product2.setQuantity(1);
    product2.setProductPrice(0.99);
    product2.setCategory("Wheat products");
    shoppingListService.createShoppingListItem(product2);

    // Drittes Produkt erstellen und initialisieren
    log.debug("Creating the third product");
    ShoppingListItem product3 = new ShoppingListItem();
    product3.setProductName("Multigrain bread");
    product3.setQuantity(1);
    product3.setProductPrice(1.50);
    product3.setCategory("Wheat products");
    shoppingListService.createShoppingListItem(product3);

    log.debug("Creating the fourth product");
    ShoppingListItem product4 = new ShoppingListItem();
    product4.setProductName("Baguette");
    product4.setQuantity(1);
    product4.setProductPrice(1.00);
    product4.setCategory("Wheat products");
    shoppingListService.createShoppingListItem(product4);

    log.debug("Creating the fifth product");
    ShoppingListItem product5 = new ShoppingListItem();
    product5.setProductName("Ciabatta");
    product5.setQuantity(1);
    product5.setProductPrice(1.50);
    product5.setCategory("Wheat products");
    shoppingListService.createShoppingListItem(product5);

    log.debug("Creating the sixth product");
    ShoppingListItem product6 = new ShoppingListItem();
    product6.setProductName("Flatbread");
    product6.setQuantity(1);
    product6.setProductPrice(1.20);
    product6.setCategory("Wheat products");
    shoppingListService.createShoppingListItem(product6);

    log.debug("Creating the seventh product");
    ShoppingListItem product7 = new ShoppingListItem();
    product7.setProductName("Cornflakes");
    product7.setQuantity(1);
    product7.setProductPrice(3.50);
    product7.setCategory("Wheat products");
    shoppingListService.createShoppingListItem(product7);

    log.debug("Creating the eighth product");
    ShoppingListItem product8 = new ShoppingListItem();
    product8.setProductName("Toast");
    product8.setQuantity(1);
    product8.setProductPrice(2.00);
    product8.setCategory("Wheat products");
    shoppingListService.createShoppingListItem(product8);

    log.debug("Creating the ninth product");
    ShoppingListItem product9 = new ShoppingListItem();
    product9.setProductName("Flour");
    product9.setQuantity(1);
    product9.setProductPrice(1.50);
    product9.setCategory("Wheat products");
    shoppingListService.createShoppingListItem(product9);

    log.debug("Creating the tenth product");
    ShoppingListItem product10 = new ShoppingListItem();
    product10.setProductName("Pizza");
    product10.setQuantity(1);
    product10.setProductPrice(5.50);
    product10.setCategory("Wheat products");
    shoppingListService.createShoppingListItem(product10);

  }
}
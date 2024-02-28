package edu.fra.uas.model;

import java.time.LocalDate;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Finance implements java.io.Serializable {
     private static final Logger log = LoggerFactory.getLogger(Finance.class);

    private long id;
    private String description;
    private Double amount;
    private LocalDate date;
    private String type;
    private String category;
    private double financeTotal;


    public Finance() {
        log.debug("FinanceRecord created without values");
    }

    public Finance(Long id, String description, Double amount, LocalDate date, String type, String category) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.category = category;
        log.debug("FinanceRecord created: " + description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getFinanceTotal() {
        return financeTotal;
    }

    public void setFinanceTotal(double financeTotal) {
        this.financeTotal = financeTotal;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) return true;

        if (object == null || getClass() != object.getClass()) return false;

        Finance that = (Finance) object;
        return Objects.equals(amount, that.amount) &&
               Objects.equals(date, that.date) &&
               Objects.equals(type, that.type) &&
               Objects.equals(description, that.description) &&
               Objects.equals(financeTotal, that.financeTotal);

    }

    @Override
    public String toString() {
        return "Finance{" +
               "id=" + id +
               ", description='" + description + '\'' +
               ", amount=" + amount +
               ", date=" + date +
               ", type='" + type + '\'' +
               ", category='" + category + '\'' +
               ", total='" + financeTotal + '\'' +
               '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, date, type, description, financeTotal);
    }

}


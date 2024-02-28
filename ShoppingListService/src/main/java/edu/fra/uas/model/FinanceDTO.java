package edu.fra.uas.model;

public class FinanceDTO {

    private String description;
    private Double amount;
    private String type;
    private String category;

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Double getAmount() {
        return amount;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    
}

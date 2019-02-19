package ru.valentin_gordienko.loftmoney;

public class TransactionListItem {

    public static final String TYPE_INCOME = "income";
    public static final String TYPE_CONSUMPTION = "expense";

    private Long id;
    private String type;
    private String name;
    private  Double price;

    TransactionListItem(String name, Double price, String type){
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

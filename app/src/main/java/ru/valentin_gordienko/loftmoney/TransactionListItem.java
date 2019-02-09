package ru.valentin_gordienko.loftmoney;

public class TransactionListItem {

    public static final String TYPE_INCOME = "income";
    public static final String TYPE_CONSUMPTION = "expense";

    private String type;
    private String name;
    private  Double price;

    TransactionListItem(String name, Double price, String type){
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public Double getPrice() {
        return this.price;
    }
}

package ru.valentin_gordienko.loftmoney;

public class TransactionListItem {

    private String name;
    private  String price;

    TransactionListItem(String name, String price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public String getPrice() {
        return this.price;
    }
}

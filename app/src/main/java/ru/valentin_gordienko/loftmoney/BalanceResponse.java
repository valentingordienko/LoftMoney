package ru.valentin_gordienko.loftmoney;

import com.google.gson.annotations.SerializedName;

public class BalanceResponse {

    @SerializedName("total_income")
    private int totalIncome;

    @SerializedName("total_expenses")
    private int totalConsumption;

    public BalanceResponse(int totalIncome, int totalConsumption) {
        this.totalIncome = totalIncome;
        this.totalConsumption = totalConsumption;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(int totalConsumption) {
        this.totalConsumption = totalConsumption;
    }
}

package ru.vaszol.yobit_2023.model;

import javax.persistence.*;

@Entity
@Table(name = "yobit_2023_info")
public class InfoPair {
    @Id
    @Column(name = "name")
    private String name;
    private Double decimal_places;
    private Double min_price;
    private Double max_price;
    private Double min_amount;
    private Double min_total;
    private Double hidden;
    private Double fee;
    private Double fee_buyer;
    private Double fee_seller;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDecimal_places() {
        return decimal_places;
    }

    public void setDecimal_places(Double decimal_places) {
        this.decimal_places = decimal_places;
    }

    public Double getMin_price() {
        return min_price;
    }

    public void setMin_price(Double min_price) {
        this.min_price = min_price;
    }

    public Double getMax_price() {
        return max_price;
    }

    public void setMax_price(Double max_price) {
        this.max_price = max_price;
    }

    public Double getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(Double min_amount) {
        this.min_amount = min_amount;
    }

    public Double getMin_total() {
        return min_total;
    }

    public void setMin_total(Double min_total) {
        this.min_total = min_total;
    }

    public Double getHidden() {
        return hidden;
    }

    public void setHidden(Double hidden) {
        this.hidden = hidden;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getFee_buyer() {
        return fee_buyer;
    }

    public void setFee_buyer(Double fee_buyer) {
        this.fee_buyer = fee_buyer;
    }

    public Double getFee_seller() {
        return fee_seller;
    }

    public void setFee_seller(Double fee_seller) {
        this.fee_seller = fee_seller;
    }
}

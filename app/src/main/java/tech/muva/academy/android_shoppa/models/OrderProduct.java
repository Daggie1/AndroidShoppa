package tech.muva.academy.android_shoppa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderProduct {

    @SerializedName("product")
    @Expose
    private Integer product;
    @SerializedName("buyer")
    @Expose
    private Integer buyer;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("amount")
    @Expose
    private float amount;

    public OrderProduct(Integer product, Integer buyer, Integer quantity, float amount) {
        this.product = product;
        this.buyer = buyer;
        this.quantity = quantity;
        this.amount = amount;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public Integer getBuyer() {
        return buyer;
    }

    public void setBuyer(Integer buyer) {
        this.buyer = buyer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

}

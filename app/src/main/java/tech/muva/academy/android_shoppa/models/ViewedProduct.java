package tech.muva.academy.android_shoppa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewedProduct {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product")
    @Expose
    private Integer product;
    @SerializedName("buyer")
    @Expose
    private Integer buyer;
    @SerializedName("checkout")
    @Expose
    private Object checkout;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Object getCheckout() {
        return checkout;
    }

    public void setCheckout(Object checkout) {
        this.checkout = checkout;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}

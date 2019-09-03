package tech.muva.academy.android_shoppa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("unit_cost")
    @Expose
    private Double unitCost;
    @SerializedName("product_brand")
    @Expose
    private String productBrand;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("featured_url")
    @Expose
    private String featuredUrl;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("quantity")
    @Expose
    private Double quantity;
    @SerializedName("total")
    @Expose
    private Double total;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getUnitCost() {
        return unitCost;
    }
    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }
    public String getProductBrand() {
        return productBrand;
    }
    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }
    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    public String getFeaturedUrl() {
        return featuredUrl;
    }
    public void setFeaturedUrl(String featuredUrl) {
        this.featuredUrl = featuredUrl;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public Double getQuantity() {
        return quantity;
    }
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
}



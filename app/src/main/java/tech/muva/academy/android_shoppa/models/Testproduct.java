package tech.muva.academy.android_shoppa.models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity  public class Testproduct {
    @Id long id;
    String category,name,price,strikedprice;
    int image, cart_icon, wishlist_icon;

    public Testproduct(String category, String name, String price, String strikedprice, int image, int cart_icon, int wishlist_icon) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.strikedprice = strikedprice;
        this.image = image;
        this.cart_icon = cart_icon;
        this.wishlist_icon = wishlist_icon;
    }

    public Testproduct() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStrikedprice() {
        return strikedprice;
    }

    public void setStrikedprice(String strikedprice) {
        this.strikedprice = strikedprice;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCart_icon() {
        return cart_icon;
    }

    public void setCart_icon(int cart_icon) {
        this.cart_icon = cart_icon;
    }

    public int getWishlist_icon() {
        return wishlist_icon;
    }

    public void setWishlist_icon(int wishlist_icon) {
        this.wishlist_icon = wishlist_icon;
    }
}

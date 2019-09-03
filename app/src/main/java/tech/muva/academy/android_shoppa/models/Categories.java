package tech.muva.academy.android_shoppa.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {
    @SerializedName("")
    private List<Category> Categories;

    public Categories(List<Category> categories) {
        Categories = categories;
    }

    public List<Category> getCategories() {
        return Categories;
    }
}

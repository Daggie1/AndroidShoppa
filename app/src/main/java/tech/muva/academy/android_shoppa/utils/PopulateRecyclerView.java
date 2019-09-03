package tech.muva.academy.android_shoppa.utils;

import java.util.ArrayList;

import tech.muva.academy.android_shoppa.adapters.ProductsRecyclerAdapter;
import tech.muva.academy.android_shoppa.models.Product;

public class PopulateRecyclerView {
    private String[] productnames;
    private String[] productprice;
    private String[] productDescription;
    private int[] productimage;
    private int cart_icon ;
    private int wishlist_icon;
    private ArrayList<Product> mArrayList;
    private ProductsRecyclerAdapter mMyRecyclerViewAdapter;

    public PopulateRecyclerView(String[] productnames, String[] productprice,
                                int[] productimage, String [] productDescription, int cart_icon,
                                int wishlist_icon, ArrayList<Product> arrayList,
                                ProductsRecyclerAdapter myRecyclerViewAdapter) {
        this.productnames = productnames;
        this.productprice = productprice;
        this.productimage = productimage;
        this.productDescription = productDescription;
        this.cart_icon = cart_icon;
        this.wishlist_icon = wishlist_icon;
        mArrayList = arrayList;
        mMyRecyclerViewAdapter=myRecyclerViewAdapter;
    }

    public void populate()
    {
//        mArrayList.clear();
//        for(int index=0; index<productnames.length; index++)
//        {
//            Product product = new Product();
//            product.setName(productnames[index]);
//            product.setPrice(productnames[index]);
//            product.setDescription(productDescription[index]);
//            product.setStrikedprice(productprice[index]);
//            product.setImage(productimage[index]);
//            product.setCart_icon(cart_icon);
//            product.setWishlist_icon(wishlist_icon);
//
//            mArrayList.add(product);
//        }
//        mMyRecyclerViewAdapter.notifyDataSetChanged();
//    }

}}


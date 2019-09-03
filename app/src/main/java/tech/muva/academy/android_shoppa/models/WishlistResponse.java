package tech.muva.academy.android_shoppa.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishlistResponse implements Parcelable {
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
    @SerializedName("wishlist_id")
    @Expose
    private Integer wishlistId;

    protected WishlistResponse(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            unitCost = null;
        } else {
            unitCost = in.readDouble();
        }
        productBrand = in.readString();
        shortDescription = in.readString();
        featuredUrl = in.readString();
        if (in.readByte() == 0) {
            wishlistId = null;
        } else {
            wishlistId = in.readInt();
        }
    }

    public static final Creator<WishlistResponse> CREATOR = new Creator<WishlistResponse>() {
        @Override
        public WishlistResponse createFromParcel(Parcel in) {
            return new WishlistResponse(in);
        }

        @Override
        public WishlistResponse[] newArray(int size) {
            return new WishlistResponse[size];
        }
    };

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

    public Integer getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Integer wishlistId) {
        this.wishlistId = wishlistId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        if (unitCost == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(unitCost);
        }
        dest.writeString(productBrand);
        dest.writeString(shortDescription);
        dest.writeString(featuredUrl);
        if (wishlistId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(wishlistId);
        }
    }
}

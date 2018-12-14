package io.github.abhishekwl.soilkart.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Item implements Parcelable {

    @SerializedName("name") private String name;
    @SerializedName("category") private String category;
    @SerializedName("unit") private String unit;
    @SerializedName("image") private String image;
    @SerializedName("price") private double price;
    @SerializedName("discount") private double discountPercentage;
    private int quantity;

    public Item(String name, String category, String unit, String image, double price, double discountPercentage) {
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.image = image;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.quantity = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.category);
        dest.writeString(this.unit);
        dest.writeString(this.image);
        dest.writeDouble(this.price);
        dest.writeDouble(this.discountPercentage);
        dest.writeInt(this.quantity);
    }

    private Item(Parcel in) {
        this.name = in.readString();
        this.category = in.readString();
        this.unit = in.readString();
        this.image = in.readString();
        this.price = in.readDouble();
        this.discountPercentage = in.readDouble();
        this.quantity = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", unit='" + unit + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", discountPercentage=" + discountPercentage +
                ", quantity=" + quantity +
                '}';
    }
}

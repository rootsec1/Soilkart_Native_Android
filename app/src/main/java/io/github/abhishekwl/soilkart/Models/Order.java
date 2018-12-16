package io.github.abhishekwl.soilkart.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Order implements Parcelable {

    @SerializedName("_id") private String id;
    @SerializedName("store") private String storeId;
    @SerializedName("products") private ArrayList<Item> itemArrayList;
    @SerializedName("status") private String status;

    public Order(String storeId, ArrayList<Item> itemArrayList) {
        this.storeId = storeId;
        this.itemArrayList = itemArrayList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public ArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }

    public void setItemArrayList(ArrayList<Item> itemArrayList) {
        this.itemArrayList = itemArrayList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.storeId);
        dest.writeTypedList(this.itemArrayList);
        dest.writeString(this.status);
    }

    private Order(Parcel in) {
        this.id = in.readString();
        this.storeId = in.readString();
        this.itemArrayList = in.createTypedArrayList(Item.CREATOR);
        this.status = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}

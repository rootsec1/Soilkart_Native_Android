package io.github.abhishekwl.soilkart.Helpers;

import java.util.ArrayList;

import io.github.abhishekwl.soilkart.Models.Item;

public class Constants {

    public static final int GST_PERCENTAGE = 12;
    public static final int DELIVERY_CHARGE_PERCENTAGE = 10;

    public static double computeGrandTotal(ArrayList<Item> itemArrayList) {
        double total = 0;
        for (Item item : itemArrayList)
            total += (item.getPrice() - (item.getDiscountPercentage() * item.getPrice())) * item.getQuantity();
        total += (GST_PERCENTAGE*total) + total;
        total += (DELIVERY_CHARGE_PERCENTAGE*total) + total;
        return total;
    }
}

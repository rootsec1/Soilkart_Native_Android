package io.github.abhishekwl.soilkart.Helpers;

import java.util.ArrayList;

import io.github.abhishekwl.soilkart.Models.Item;
import io.github.abhishekwl.soilkart.Models.Order;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitApiInterface {

    @GET("products")
    Call<ArrayList<Item>> getAllItems();

    @FormUrlEncoded
    @POST("orders")
    Call<Order> createNewOrder(@Field("store") String store, @Field("products") ArrayList<Item> itemArrayList);

}

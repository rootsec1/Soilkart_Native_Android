package io.github.abhishekwl.soilkart.Helpers;

import java.util.ArrayList;

import io.github.abhishekwl.soilkart.Models.Item;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApiInterface {

    @GET("products")
    Call<ArrayList<Item>> getAllItems();

}

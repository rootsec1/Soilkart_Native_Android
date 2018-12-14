package io.github.abhishekwl.soilkart.Helpers;

import android.content.Context;

import io.github.abhishekwl.soilkart.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(Context context) {
        if (retrofit==null) retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_server_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

}

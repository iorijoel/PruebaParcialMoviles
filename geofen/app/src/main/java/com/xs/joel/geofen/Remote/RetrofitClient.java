package com.xs.joel.geofen.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by joel_ on 15/2/2018.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    public static Retrofit getClient (String baseURL)
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}

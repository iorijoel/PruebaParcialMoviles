package com.xs.joel.geofen;

import com.xs.joel.geofen.Remote.APIService;
import com.xs.joel.geofen.Remote.RetrofitClient;

/**
 * Created by joel_ on 8/2/2018.
 */

public class Common {
    public static String currentToken = "";
    private static String baseUrl="https://fcm.googleapis.com/";

    public static APIService GETFCMClient()
    {
        return RetrofitClient.getClient(baseUrl).create(APIService.class);
    }


}

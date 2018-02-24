package com.example.yanez.geofence;

import com.example.yanez.geofence.Remote.APIService;
import com.example.yanez.geofence.Remote.RetrofitClient;

/**
 * Created by YANEZ on 08/02/2018.
 */

public class Common {
    public static String currentToken = "";

    private static String baseUrl="https://fcm.googleapis.com/";

    public static APIService GETFCMClient()
    {
        return RetrofitClient.getClient(baseUrl).create(APIService.class);
    }

}


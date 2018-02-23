package com.xs.joel.geofen.Remote;

import com.xs.joel.geofen.Model.MyResponse;
import com.xs.joel.geofen.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by joel_ on 15/2/2018.
 */

public interface APIService {
    @Headers({
            "Content-Type:application/json",

            "Authorization:key=AAAAzDNa-ZU:APA91bEQYlf4JCn-lqCaoT7rU1RQsc20PYOlx_vE5-JlI-ZZ51W09ZhtizdX8IW_hewsIYSxEPXCuvD98aNmKx3dVCFymOW1W-nNtdRQDMDNEeKv5KPkmCmR1cwHUSuc-hat6cc87_Ae"


    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}

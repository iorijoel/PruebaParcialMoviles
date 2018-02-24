package com.example.yanez.geofence;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yanez.geofence.Model.MyResponse;
import com.example.yanez.geofence.Model.Notification;
import com.example.yanez.geofence.Model.Sender;
import com.example.yanez.geofence.Remote.APIService;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YANEZ on 16/02/2018.
 */

public class Gts extends IntentService {
    private static final String TAG = Gts.class.getSimpleName();
    public static final int GEOFENCE_NOTIFICATION_ID = 0;
    public Gts() {
        super(TAG);
    }
    public String asunto="NOTIFICACION DE GEOFENCE!";
    public APIService mService = Common.GETFCMClient();


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        // Manejador de  errors
        if (geofencingEvent.hasError() ) {
            String errorMsg = getErrorString(geofencingEvent.getErrorCode() );
            Log.e( TAG, errorMsg );
            return;    }
        // Obtiene el tipo de transicion
        int geoFenceTransition = geofencingEvent.getGeofenceTransition();
        // Verificar si el tipo de transición es de interés
        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT ) {
            // Get the geofence QUE SE ACTIVO
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            // Obtiene los detalles de la transicion en tipo string
            String geofenceTransitionDetails = getGeofenceTransitionDetails(geoFenceTransition, triggeringGeofences );
            // ENVIAR  DETALLE DE notification COMO UN String
            sendNotification( geofenceTransitionDetails );
        }

    }

    private static String getErrorString(int errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GeoFence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Too many GeoFences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Too many pending intents";
            default:
                return "Unknown error.";
        }
    }



    private String getGeofenceTransitionDetails(int geoFenceTransition, List<Geofence> triggeringGeofences) {
        // obtener la identificación de cada geofence desencadenada
        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
        for ( Geofence geofence : triggeringGeofences ) {
            triggeringGeofencesList.add( geofence.getRequestId() );
        }

        String status = null;
        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER )
        {
            status = "Entrando en ";
        }

        else if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT )
        {
            status = "Saliendo de ";
        }
        return status + TextUtils.join( ", ", triggeringGeofencesList);
    }



    private void sendNotification( String msg) {

        String user = MainActivity.user;
        String content = user;

        FirebaseMessaging.getInstance().subscribeToTopic("estudiantes");
        mService = Common.GETFCMClient();

        try {
            Notification notification = new Notification(msg,content);
            Sender sender = new Sender("/topics/estudiantes", notification);
            //Sender sender = new Sender(Common.currentToken, notification);
            mService.sendNotification(sender)
                    .enqueue(new Callback<MyResponse>() {

                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                            if (response.body().success == 1) {
                            } else {
                                //Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {
                            Log.e("Error", t.getMessage());
                        }
                    });
        }
        catch (Exception ex){

            //Toast.makeText(MainActivity.this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

        /*
        Log.i(TAG, "sendNotification: " + msg );

        // Inicia el intent en el MainActivity
        Intent notificationIntent = MainActivity.makeNotificationIntent(
                getApplicationContext(), msg);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Crear y enviar notificaciones
        NotificationManager notificatioMng =
                (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
        notificatioMng.notify(
                GEOFENCE_NOTIFICATION_ID,
                createNotification(msg, notificationPendingIntent));*/

    }

    /*private Notification createNotification(String msg, PendingIntent notificationPendingIntent) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder
                .setSmallIcon(R.drawable.location)
                .setColor(Color.RED)
                .setContentTitle(msg)
                .setContentText(asunto)
                .setContentIntent(notificationPendingIntent).setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        return notificationBuilder.build();
    }*/

}

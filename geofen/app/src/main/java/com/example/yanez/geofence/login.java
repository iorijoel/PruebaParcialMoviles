package com.example.yanez.geofence;

import com.example.yanez.geofence.datos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity {

    private EditText user;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText)findViewById(R.id.user);
        btn = (Button) findViewById(R.id.btnSendData);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datos dt = new datos(user.toString());
                Intent intent = new Intent( login.this, MainActivity.class );
                Bundle b = new Bundle();
                b.putString("Nombre", user.getText().toString());
                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }
}

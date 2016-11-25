package com.example.mahmoud.datashow.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mahmoud.datashow.R;
import com.example.mahmoud.datashow.Fragments.SignUpFragment;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if(savedInstanceState==null)
        getSupportFragmentManager().beginTransaction().add(R.id.content_sign_up,new SignUpFragment()).commit();
    }
}

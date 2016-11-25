package com.example.mahmoud.datashow.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mahmoud.datashow.Fragments.ProfileFragment;
import com.example.mahmoud.datashow.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if(savedInstanceState==null)
        getSupportFragmentManager().beginTransaction().add(R.id.content_profile,new ProfileFragment()).commit();
    }
}

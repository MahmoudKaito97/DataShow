package com.example.mahmoud.datashow.Fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mahmoud.datashow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetFragment extends Fragment {

    private EditText email;
    private Button reset;
    private FirebaseAuth auth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reset,container,false);
            email = (EditText) rootView.findViewById(R.id.email_edt);
            reset = (Button) rootView.findViewById(R.id.reset_btn);

        auth = FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eml = email.getText().toString();

                if(eml.isEmpty()){
                    Toast.makeText(getContext(),"Enter your registered email in the field above",Toast.LENGTH_SHORT);
                    return;
                }
                auth.sendPasswordResetEmail(eml)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                            Toast.makeText(getContext(),"An email sent to the Entered Email.Please check your email",Toast.LENGTH_SHORT);
                           }else {
                               Toast.makeText(getContext(),"Something went wrong while resetting your password",Toast.LENGTH_SHORT);
                           }
                            }
                        });

            }
        });

        return rootView;
    }
}

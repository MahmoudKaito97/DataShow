package com.example.mahmoud.datashow.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud.datashow.Activities.ProfileActivity;
import com.example.mahmoud.datashow.Activities.SignUpActivity;
import com.example.mahmoud.datashow.R;
import com.example.mahmoud.datashow.Activities.ResetActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private Button signUp,login,forgot;
    private EditText email,password;
    private CheckBox remember;
    private FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login,container,false);

        signUp = (Button) rootView.findViewById(R.id.signup_button);
        login = (Button) rootView.findViewById(R.id.login_button);
        email = (EditText) rootView.findViewById(R.id.login_email);
        password = (EditText) rootView.findViewById(R.id.password);
        remember = (CheckBox) rootView.findViewById(R.id.checkBox);
        forgot = (Button) rootView.findViewById(R.id.forgot_btn);
        boolean signoutPressed;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String eml,pass;

        eml=pref.getString("name",null);
        pass=pref.getString("password",null);

        signoutPressed = getActivity().getIntent().getBooleanExtra("signout",false);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ResetActivity.class));
            }
        });

        if(signoutPressed){

            rootView.setVisibility(View.VISIBLE);
            email.setText(eml);
            password.setText(pass);

            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().clear().commit();

        }
        else if(eml!=null&&pass!=null){
            try{
                rootView.setVisibility(View.GONE);
                signIn(eml,pass);
            }
            catch(Exception e){
                Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            rootView.setVisibility(View.VISIBLE);
        }
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(),SignUpActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv;
                int x=0;

                if(email.getText().toString().isEmpty()){
                    tv= (TextView) rootView.findViewById(R.id.login_email_note);
                    tv.setVisibility(View.VISIBLE);
                    x++;
                }
                if(password.getText().toString().isEmpty()){
                    tv= (TextView) rootView.findViewById(R.id.login_password_note);
                    tv.setVisibility(View.VISIBLE);
                    x++;
                }
                if(x>0){
                    return;
                }

                signIn(email.getText().toString(),password.getText().toString());
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
    public void signIn(final String s0,final String s1){

        auth.signInWithEmailAndPassword(s0,s1)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(remember.isChecked()){

                                PreferenceManager.getDefaultSharedPreferences(getContext()).edit()
                                        .putString("name",s0)
                                        .putString("password",s1).commit();
                            }

                            Intent i = new Intent(getContext(),ProfileActivity.class);
                            i.putExtra("user_email",s0);
                            startActivity(i);
                            getActivity().finish();
                        }
                        else {
                            Toast.makeText(getContext(),
                                    "Something went wrong.check if you already signed up or check your connection",Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }
}

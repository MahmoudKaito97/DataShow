package com.example.mahmoud.datashow.Fragments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud.datashow.Activities.ProfileActivity;
import com.example.mahmoud.datashow.DBHelper;
import com.example.mahmoud.datashow.R;
import com.example.mahmoud.datashow.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.UUID;


public class SignUpFragment extends Fragment {


    private Button signUp,modify;
    private EditText name,email,password,phone;
    private Spinner jobTitle;
    private User user =new User();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DBHelper db ;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_sign_up,container,false);

        signUp = (Button) rootView.findViewById(R.id.register_btn);
        name = (EditText) rootView.findViewById(R.id.name);
        email = (EditText) rootView.findViewById(R.id.email);
        password = (EditText) rootView.findViewById(R.id.password_sign_up);
        phone = (EditText) rootView.findViewById(R.id.phone);
        jobTitle = (Spinner) rootView.findViewById(R.id.spinner);
        modify= (Button) rootView.findViewById(R.id.edit_btn);

        db = new DBHelper(getActivity().getApplicationContext());
        boolean editPressed = getActivity().getIntent().getBooleanExtra("edit",false);

        if(editPressed){
            modify.setVisibility(View.VISIBLE);
            signUp.setVisibility(View.GONE);
            getActivity().getIntent().removeExtra("edit");
        }
        else{
            signUp.setVisibility(View.VISIBLE);
            modify.setVisibility(View.GONE);
        }


        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //
                TextView tv;
                int x=0;
                if(name.getText().toString().isEmpty()){
                    tv= (TextView) rootView.findViewById(R.id.name_note);
                    tv.setVisibility(View.VISIBLE);
                    x++;
                }
                if(password.getText().toString().length()<6){
                    tv= (TextView) rootView.findViewById(R.id.password_note);
                    tv.setVisibility(View.VISIBLE);
                    x++;
                }
                if(email.getText().toString().isEmpty()){
                    tv= (TextView) rootView.findViewById(R.id.email_note);
                    tv.setVisibility(View.VISIBLE);
                    x++;
                }
                if(jobTitle.getSelectedItemPosition()==0){
                    tv= (TextView) rootView.findViewById(R.id.spinner_note);
                    tv.setVisibility(View.VISIBLE);
                    x++;
                }

                if(x>0){
                    return;
                }

                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPhone(phone.getText().toString());






                db.updateUser(user);

                FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                fbUser.updateEmail(email.getText().toString());
                fbUser.updatePassword(password.getText().toString());



            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //
                TextView tv;
                int x=0;
                if(name.getText().toString().isEmpty()){
                    tv= (TextView) rootView.findViewById(R.id.name_note);
                    tv.setVisibility(View.VISIBLE);
                    x++;
                }
                if(password.getText().toString().length()<6){
                    tv= (TextView) rootView.findViewById(R.id.password_note);
                    tv.setVisibility(View.VISIBLE);
                    x++;
                }
                if(email.getText().toString().isEmpty()){
                    tv= (TextView) rootView.findViewById(R.id.email_note);
                    tv.setVisibility(View.VISIBLE);
                    x++;
                }
                if(jobTitle.getSelectedItemPosition()==0){
                    tv= (TextView) rootView.findViewById(R.id.spinner_note);
                    tv.setVisibility(View.VISIBLE);
                    x++;
                }

                if(x>0){
                    return;
                }

                if(jobTitle.getSelectedItemPosition()>0)
                    user.setJobTitle(jobTitle.getSelectedItem().toString());

                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPhone(phone.getText().toString());

                String uid = UUID.randomUUID().toString();

                user.setId(uid);

                db.addUser(user);


                auth.createUserWithEmailAndPassword(user.getEmail(),password.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if(task.isSuccessful()){
                                    Intent i = new Intent(getContext(),ProfileActivity.class);
                                    i.putExtra("user_email",user.getEmail());
                                    startActivity(i);
                                    getActivity().finish();
                                }
                                else{
                                    Toast.makeText(getContext(),"This Email is registered before.Please Enter Another Email OR Login to your email",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        return rootView;
    }


}

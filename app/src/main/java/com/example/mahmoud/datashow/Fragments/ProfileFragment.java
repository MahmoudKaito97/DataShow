package com.example.mahmoud.datashow.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mahmoud.datashow.Activities.LoginActivity;
import com.example.mahmoud.datashow.Activities.SignUpActivity;
import com.example.mahmoud.datashow.DBHelper;
import com.example.mahmoud.datashow.R;
import com.example.mahmoud.datashow.User;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    public ProfileFragment(){
        setHasOptionsMenu(true);
    }
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private TextView name,email,phone,jobTitle;
    private Button modify;
    private DBHelper db ;
    private User user = new User();
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile,container,false);

        name = (TextView) rootView.findViewById(R.id.profile_name);
        email = (TextView) rootView.findViewById(R.id.profile_email);
        phone = (TextView) rootView.findViewById(R.id.profile_phone);
        jobTitle = (TextView) rootView.findViewById(R.id.profile_job);


        modify = (Button) rootView.findViewById(R.id.modify_btn);

        db = new DBHelper(getActivity().getApplicationContext());

        String eml = getActivity().getIntent().getStringExtra("user_email");

        user = db.searchForUserByEmail(eml);
        name.append(user.getName());
        email.append(user.getEmail());
        phone.append(user.getPhone());

        jobTitle.append(user.getJobTitle());

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),SignUpActivity.class);
                i.putExtra("edit",true);
                startActivity(i);
                getActivity().finish();
            }
        });


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_profile,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if(id==R.id.sign_out){

            Intent i = new Intent(getActivity(),LoginActivity.class);
            i.putExtra("signout",true);
            auth.signOut();
            startActivity(i);
            getActivity().finish();

        }

        return super.onOptionsItemSelected(item);
    }
}

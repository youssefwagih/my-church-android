package com.example.youssefwagih.mychurchapp.ui.signup.view;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.youssefwagih.mychurchapp.R;
import com.example.youssefwagih.mychurchapp.ui.signup.view.SignUpFragment;

public class SignUpActivity extends AppCompatActivity implements SignUpFragment.OnFragmentInteractionListener {
    private static final String TAG = "LoginActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the view now
        setContentView(R.layout.activity_sign_up);
        loadSignUpFragment();
    }

    private void loadSignUpFragment(){
        SignUpFragment signUpFragment = SignUpFragment.newInstance("", "");
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, signUpFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}


package com.kavireletronic.ali.kavireleclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.valdesekamdem.library.mdtoast.MDToast;

import net.igenius.customcheckbox.CustomCheckBox;

import Interface.Loginuser;
import model.LoginModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.Factory;

public class LoginActivity extends AppCompatActivity {
    EditText pswd,usrusr;
    TextView lin;
    ProgressBar progressBar;
    private CustomCheckBox customCheckBox;
    private SharedPreferences SP;
    private static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = SP.edit();


        lin = (TextView) findViewById(R.id.lin);
        usrusr = (EditText) findViewById(R.id.usrusr);
        pswd = (EditText) findViewById(R.id.pswrdd);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);
        customCheckBox=(CustomCheckBox)findViewById(R.id.save_pass_ckb);

        if (!SP.getString("username","").equals("")){
            usrusr.setText(SP.getString("username",""));
            pswd.setText(SP.getString("pass",""));
            customCheckBox.setChecked(true,true);

            MDToast mdToast=MDToast.makeText(getApplicationContext(), getString(R.string.login_now),MDToast.LENGTH_LONG,MDToast.TYPE_INFO);
            mdToast.show();
            progressBar.setVisibility(View.VISIBLE);
//            LoginUser.checkUser(getApplicationContext(),progressBar,SP.getString("username",""),SP.getString("pass",""),false);
            loginUser(SP.getString("username",""),SP.getString("pass",""),false);

        }else {
            customCheckBox.setChecked(false,true);
        }

        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=usrusr.getText().toString();
                String pass=pswd.getText().toString();
                if (user.equals("") || pass.equals("")){
//                    Toast.makeText(LoginActivity.this, getString(R.string.incomplete_feild), Toast.LENGTH_SHORT).show();
                    MDToast mdToast=MDToast.makeText(getApplicationContext(), getString(R.string.incomplete_feild),MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING);
                    mdToast.show();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
//                    LoginUser.checkUser(getApplicationContext(),progressBar,user,pass,customCheckBox.isChecked());
                    loginUser(user,pass,customCheckBox.isChecked());
                }

            }
        });
    }

    private void loginUser(final String username, final String pass, final Boolean save){
        Retrofit retrofit= Factory.getRetrofit();
        Loginuser loginuser=retrofit.create(Loginuser.class);
        loginuser.checkuser(username,pass).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getMsg().equals("ok")){
                        editor.putString("id_user",response.body().getId());
                        editor.putString("name_user",response.body().getName());
                        if (save){
                            editor.putString("username",username);
                            editor.putString("pass",pass);
                        }
                        editor.apply();
                        //// show msg welcome
                        MDToast mdToast=MDToast.makeText(getApplicationContext(), getString(R.string.welcome)+response.body().getName(),MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS);
                        mdToast.show();
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                MDToast mdToast=MDToast.makeText(getApplicationContext(), getString(R.string.no_conn),MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR);
                mdToast.show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}

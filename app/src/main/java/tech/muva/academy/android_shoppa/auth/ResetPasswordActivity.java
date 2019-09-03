package tech.muva.academy.android_shoppa.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.api.model.ChangePassword;
import tech.muva.academy.android_shoppa.api.model.LoginModel;
import tech.muva.academy.android_shoppa.api.model.UsersModel;
import tech.muva.academy.android_shoppa.api.services.ServiceGenerator;
import tech.muva.academy.android_shoppa.settings.SettingsActivity;
import tech.muva.academy.android_shoppa.ui.activities.ViewProductDetails;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;

public class ResetPasswordActivity extends AppCompatActivity  {

    ImageButton backToLoginButton, backToSettingsButton;
    Button buttonChangePassword, buttonResetPassword;
    EditText newPassword, confirmPassword;
    private Context mContext;
    private SharedPreferencesConfig sharedPreferencesConfig;


    private static final String IS_CHANGE_PASSWORD ="tech.muva.academy.android_shoppa.auth.isChangepassword" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        if(getIntent().getBooleanExtra(IS_CHANGE_PASSWORD,true)){
            setContentView(R.layout.activity_change_password);

            backToSettingsButton = findViewById(R.id.back_to_settings);

            backToSettingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ResetPasswordActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            buttonChangePassword = findViewById(R.id.button_change_password);
            newPassword = findViewById(R.id.new_password_text);
            confirmPassword = findViewById(R.id.confirm_password_text);

            buttonChangePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    Toast.makeText(ResetPasswordActivity.this,"Password Changed, Login",Toast.LENGTH_SHORT).show();
//                    finish();

                    changePassword();

                }
            });
        } else
            {
        setContentView(R.layout.activity_reset_password);

            backToLoginButton = findViewById(R.id.back_to_login);

            backToLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            buttonResetPassword = findViewById(R.id.button_reset_password);

            buttonResetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ResetPasswordActivity.this,"Password Reset, Check Your Email",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            });
        }

    }

    private void changePassword() {
        String new_password1 = newPassword.getText().toString();
        String new_password2 = confirmPassword.getText().toString();
        String token = sharedPreferencesConfig.readBuyerToken();


        ChangePassword changePassword = new ChangePassword(new_password1,new_password2);
        Call<UsersModel> call = ServiceGenerator.getInstance(mContext)
                .getAnotherApiConnector()
                .changePassword("Token "+token, changePassword);

        call.enqueue(new Callback<UsersModel>() {
            @Override
            public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                if (response.code() == 200){
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(ResetPasswordActivity.this,"Password Changed, Confirm Login",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(ResetPasswordActivity.this,"Login not correct", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsersModel> call, Throwable t) {
                Toast.makeText(ResetPasswordActivity.this,"Network Failure, Please Try Again", Toast.LENGTH_SHORT).show();
                Log.d("Error", "Another One");
            }
        });


    }


    public  static  Intent startSelfIntent(Context context,boolean isChangePassword){
        Intent i=new Intent(context,ResetPasswordActivity.class);
        i.putExtra(IS_CHANGE_PASSWORD,isChangePassword);
        return i;
    }
}

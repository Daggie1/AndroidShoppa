package tech.muva.academy.android_shoppa.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.RetrofitClient;
import tech.muva.academy.android_shoppa.api.model.LoginModel;
import tech.muva.academy.android_shoppa.api.model.RegisterModel;
import tech.muva.academy.android_shoppa.api.model.UsersModel;
import tech.muva.academy.android_shoppa.api.services.ServiceGenerator;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonRegister;
    TextView redirectLogin;
    private Context mContext;
    EditText usernameInput,firstnameInput,lastnameInput,emailInput,phone_numberInput,passwordInput,confirmpasswordInput;
    private String username,first_name,last_name,email,phone_number,password,confirmpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonRegister = findViewById(R.id.button_register);
        redirectLogin = findViewById(R.id.redirect_login);
        usernameInput = findViewById(R.id.username_input_text);
        firstnameInput = findViewById(R.id.first_name_input_text);
        lastnameInput = findViewById(R.id.last_name_input_text);
        emailInput = findViewById(R.id.email_input_text);
        phone_numberInput = findViewById(R.id.phone_number_input_text);
        passwordInput = findViewById(R.id.password_input_text);
        confirmpasswordInput = findViewById(R.id.password_confirm_input_text);



        buttonRegister.setOnClickListener(this);
        redirectLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.button_register) {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show();
//            finish();
            register();
        }
        else if (id == R.id.redirect_login){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void register() {
        username = usernameInput.getText().toString();
        first_name = firstnameInput.getText().toString();
        last_name = lastnameInput.getText().toString();
        phone_number = phone_numberInput.getText().toString();
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        confirmpassword = confirmpasswordInput.getText().toString();



            RegisterModel registerModel = new RegisterModel(username,first_name,last_name,password,email,phone_number);
            Call<UsersModel> call = RetrofitClient.getInstance(mContext)
                    .getApiConnector()
                    .register(registerModel);

            call.enqueue(new Callback<UsersModel>() {
                @Override
                public void onResponse(Call<UsersModel> call, Response<UsersModel> response) {
                    if (response.isSuccessful()){
//                        Toast.makeText(RegisterActivity.this,response.body()., Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(RegisterActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UsersModel> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this,"error", Toast.LENGTH_SHORT).show();
                    Log.d("Error", "Another One");
                }
            });




    }
}

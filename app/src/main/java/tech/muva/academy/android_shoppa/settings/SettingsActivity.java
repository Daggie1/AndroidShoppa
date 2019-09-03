package tech.muva.academy.android_shoppa.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;



import androidx.preference.PreferenceFragmentCompat;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.auth.ResetPasswordActivity;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;

public class SettingsActivity extends AppCompatActivity {
    ImageView firstLetterImageView;
    TextView usernameTextView, emailTextView;
    private SharedPreferencesConfig sharedPreferencesConfig;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());


        firstLetterImageView = findViewById(R.id.first_letter_image_view);
        usernameTextView = findViewById(R.id.username_text_view);
        emailTextView = findViewById(R.id.email_text_view);
        String status = sharedPreferencesConfig.readBuyerStatus();

        if(status.contentEquals("Active")) {
            String username = sharedPreferencesConfig.readBuyerUsername();
            String email = sharedPreferencesConfig.readBuyerEmail();
            usernameTextView.setText(username);
            emailTextView.setText(email);

            getFirstLetterInCircularBackground(firstLetterImageView, username);
        }
        else {
            String username = usernameTextView.getText().toString();
            getFirstLetterInCircularBackground(firstLetterImageView, username);

        }
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            final Context context = getContext();

            Preference preferenceChangePassword, preferencePersonalInformation;
            preferenceChangePassword = findPreference("security_information");
            preferencePersonalInformation = findPreference("personal_information");

            preferenceChangePassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = ResetPasswordActivity.startSelfIntent(context, true);
                    startActivity(intent);

                    return true;
                }
            });

            preferencePersonalInformation.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent in = new Intent(context, ChangePersonalInformationActivity.class);
                    startActivity(in);


                    return false;
                }
            });


        }
    }

    public void getFirstLetterInCircularBackground(ImageView imageView, String username){
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
//        generate random color
//        int color = generator.getColor(getItem());

        int color = generator.getRandomColor();
        String firstLetter = String.valueOf(username.charAt(0));

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        imageView.setImageDrawable(drawable);
    }
}

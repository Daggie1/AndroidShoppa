package tech.muva.academy.android_shoppa.settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.content.Context;
import android.os.Bundle;

import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;

public class ChangePersonalInformationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_personal_information);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new ChangePersonalInformationActivity.SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }





    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.personal_information_preferences);
            SharedPreferencesConfig sharedPreferencesConfig;

            final Context context = getContext();
            sharedPreferencesConfig = new SharedPreferencesConfig(context);


            Preference preferenceUsername, preferencefirstname, preferencelastname, preferenceemail;
            preferenceUsername = findPreference("username_edit_preference");
            preferencefirstname = findPreference("first_name_edit_preference");
            preferencelastname = findPreference("last_name_edit_preference");
            preferenceemail = findPreference("email_edit_preference");

            String username = sharedPreferencesConfig.readBuyerUsername();
            String firstname = sharedPreferencesConfig.readBuyerFirstname();
            String lastame = sharedPreferencesConfig.readBuyerLastname();
            String email = sharedPreferencesConfig.readBuyerEmail();


            if (preferenceUsername != null) {
                preferenceUsername.setSummary(username);
            }
            if (preferencefirstname != null) {
                preferencefirstname.setSummary(firstname);
            }
            if (preferencelastname != null) {
                preferencelastname.setSummary(lastame);
            }
            if (preferenceemail != null) {
                preferenceemail.setSummary(email);
            }


        }
    }
}
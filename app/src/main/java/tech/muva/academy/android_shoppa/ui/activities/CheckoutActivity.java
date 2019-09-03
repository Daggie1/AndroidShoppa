package tech.muva.academy.android_shoppa.ui.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.muva.academy.android_shoppa.R;
import tech.muva.academy.android_shoppa.api.services.ServiceGenerator;
import tech.muva.academy.android_shoppa.models.CartResponse;
import tech.muva.academy.android_shoppa.models.Checkout;
import tech.muva.academy.android_shoppa.models.QuantityResponse;
import tech.muva.academy.android_shoppa.settings.SettingsActivity;
import tech.muva.academy.android_shoppa.utils.SharedPreferencesConfig;

public class CheckoutActivity extends AppCompatActivity {
    private static final String TOTAL = "tech.muva.academy.android_shoppa.ui.activities.total" ;
    private static final String ITEMS = "tech.muva.academy.android_shoppa.ui.activities.items";
    private static final String S_REGION = "tech.muva.academy.android_shoppa.ui.activities.region";
    private static final String PHONE_NUMBER = "tech.muva.academy.android_shoppa.ui.activities.phonenumber";
    private static final String REFERNCE_CODE = "tech.muva.academy.android_shoppa.ui.activities.referencecode";

    final Context context = this;
    ImageView firstLetterImageView;
    TextView usernameTextView, dialogTitle, emailTextView, dialogText;
    TextView phone_number, shipping_region, total,items;
    EditText paymentphoneNumber;
    Button payByMpesaButton, payInCashButton;
    SharedPreferencesConfig sharedPreferencesConfig;
    ImageView changeContactInfo, changeShippingRegion;
    private ArrayList<Checkout> mCheckoutResponses=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        String amount = getIntent().getStringExtra(TOTAL);
        String no_of_items = getIntent().getStringExtra(ITEMS);
        String phonenumber = getIntent().getStringExtra(PHONE_NUMBER);
        String reference_code = getIntent().getStringExtra(REFERNCE_CODE);
        String shippingregion = getIntent().getStringExtra(S_REGION);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        firstLetterImageView = findViewById(R.id.first_letter_image_view);
        usernameTextView = findViewById(R.id.username_text_view);
        emailTextView = findViewById(R.id.email_text_view);
        payByMpesaButton = findViewById(R.id.button_pay_with_mpesa);
        payInCashButton = findViewById(R.id.button_pay_in_cash);
        phone_number = findViewById(R.id.chk_phone_number);
        shipping_region = findViewById(R.id.chk_shipping_region);
        total = findViewById(R.id.chk_amount);
        items = findViewById(R.id.chk_items);
        changeContactInfo = findViewById(R.id.change_phone_number_txt);
        changeShippingRegion = findViewById(R.id.change_shipping_region_txt);

        total.setText("Ksh "+amount);
        items.setText(no_of_items);
        shipping_region.setText(shippingregion);
        phone_number.setText(phonenumber);




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

        payByMpesaButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                inflatePromptToEnterPhoneNumberForMpesa();}});

        payInCashButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                inflatePromptToEnterPhoneNumber();
            }
        });
        changeShippingRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeShippingRegion();
            }
        });

        changeContactInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeContactInfo();
            }
        });

    }

    private void inflatePromptToEnterPhoneNumberForMpesa() {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.payment_prompts, null);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setView(promptsView);

        final EditText payPhone = promptsView.findViewById(R.id.editTextPaymentPhonenumber);
        dialogTitle = promptsView.findViewById(R.id.textView2);
        dialogTitle.setText("Pay Using Mpesa");

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Pay",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void inflatePromptToEnterPhoneNumber(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.payment_prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setView(promptsView);

        final EditText payPhone = promptsView.findViewById(R.id.editTextPaymentPhonenumber);
        dialogTitle = promptsView.findViewById(R.id.textView2);
        dialogTitle.setText("Pay In Cash On Delivery");

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Make Order",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public void changeContactInfo(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.payment_prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setView(promptsView);

        final EditText payPhone = promptsView.findViewById(R.id.editTextPaymentPhonenumber);
        dialogTitle = promptsView.findViewById(R.id.textView2);
        dialogText = promptsView.findViewById(R.id.textView);

        dialogTitle.setText("Contact Info:");
        dialogText.setText("Enter Contact Info");

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Edit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String contact = payPhone.getText().toString();
                                phone_number.setText(contact);
                                dialog.cancel();

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public void changeShippingRegion(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.payment_prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setView(promptsView);

        final EditText payPhone = promptsView.findViewById(R.id.editTextPaymentPhonenumber);
        dialogTitle = promptsView.findViewById(R.id.textView2);
        dialogText = promptsView.findViewById(R.id.textView);
        dialogTitle.setText("Shipping Region:");
        dialogText.setText("Enter Shipping Region");

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Edit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String region = payPhone.getText().toString();
                                shipping_region.setText(region);
                                dialog.cancel();

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }



    public void getFirstLetterInCircularBackground(ImageView imageView, String username){
//     ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
//     generate random color
//     int color = generator.getRandomColor();

        int color = getResources().getColor(R.color.colorPrimary);

        String firstLetter = String.valueOf(username.charAt(0));

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        imageView.setImageDrawable(drawable);
    }

    public  static  Intent startSelfIntent(Context context,String total, String items, String phone_number, String reference_code, String shipping_region){
        Intent i=new Intent(context,CheckoutActivity.class);
        i.putExtra(TOTAL, total);
        i.putExtra(ITEMS, items);
        i.putExtra(PHONE_NUMBER, phone_number);
        i.putExtra(REFERNCE_CODE, reference_code);
        i.putExtra(S_REGION, shipping_region);
        return i;
    }


}


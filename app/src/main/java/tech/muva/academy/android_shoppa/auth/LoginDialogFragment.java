package tech.muva.academy.android_shoppa.auth;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import tech.muva.academy.android_shoppa.R;

public class LoginDialogFragment extends DialogFragment {
    public static final String DIALOG_LOGIN = "Login Dialog";
    Context mContext;

    public LoginDialogFragment(Context context) {
        mContext = context;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
 MaterialButton mLogin;
 MaterialButton mcancel;
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_fragment_login, null);
        mLogin=v.findViewById(R.id.dialogLoginBtn);
        mcancel=v.findViewById(R.id.dialogCancelBtn);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,LoginActivity.class));
//                new LoginDialogFragment(mContext).dismiss();
            }
        });
        mcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // new LoginDialogFragment(mContext).dismiss();
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("")
                .create();

    }
    public void startDialog(FragmentManager fragmentManager){
       LoginDialogFragment dialog = new
               LoginDialogFragment(mContext);
        dialog.show(fragmentManager, DIALOG_LOGIN);
    }
}

package com.beveragebooker.customer_app.notifications;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.beveragebooker.customer_app.activities.PlaceOrderActivity;
import com.beveragebooker.customer_app.activities.PrimaryMenu;

public class DatabaseDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Error")
                .setMessage("An error has occurred while contacting the database." +
                        " Please try placing your order again." +
                        " If the problem persists, please contact the cafe directly.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), PrimaryMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }
}

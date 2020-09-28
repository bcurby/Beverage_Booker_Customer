package com.beveragebooker.customer_app.activities;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PrivacyDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Privacy Policy")
                .setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam maximus " +
                        "maximus maximus. Nulla eleifend urna sed lacus tempus, at iaculis ex porttitor. " +
                        "Nam sit amet molestie massa. Mauris luctus efficitur bibendum. Pellentesque at risus " +
                        "a arcu faucibus venenatis in sit amet dolor. Aliquam eget nulla mollis, blandit " +
                        "orci a, mattis nibh. Curabitur tincidunt, ex ut auctor malesuada, magna diam " +
                        "bibendum risus, nec iaculis nunc mauris et urna. Sed eu velit sed purus " +
                        "pulvinar aliquam eu varius turpis. Sed sollicitudin a nibh ut dictum. Proin " +
                        "eget turpis ac neque imperdiet venenatis. Phasellus sit amet tortor ut justo " +
                        "efficitur dictum. Cras rhoncus orci turpis, et varius arcu euismod ut. Aenean in " +
                        "hendrerit eros, a porta leo. Vestibulum sollicitudin hendrerit mauris non luctus.\n" +
                        "Morbi tincidunt diam sed nisi lacinia, et suscipit elit fringilla. Pellentesque " +
                        "tincidunt, justo quis condimentum interdum, risus dui scelerisque ipsum, in " +
                        "luctus lacus ligula eu nisl. In blandit, lacus sit amet mollis aliquet, urna " +
                        "enim viverra libero, et maximus ligula massa ut ligula. Praesent at tellus " +
                        "convallis, fermentum felis at, sodales enim. Orci varius natoque penatibus et " +
                        "magnis dis parturient montes, nascetur ridiculus mus. Suspendisse fermentum ac " +
                        "orci vel efficitur. Nulla at quam lacinia, vehicula risus in, malesuada enim. Integer. ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}

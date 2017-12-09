package com.apollo.andorid.apollosearch.view.webview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.apollo.andorid.apollosearch.R;

import io.reactivex.functions.Action;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public class JsAlertDialog extends DialogFragment {

    private static final String ARG_MESSAGE = "message";
    private static final String ARG_CANCELABLE = "cancelable";

    private Action onConfirmAction;
    private Action onCancelAction;

    public static JsAlertDialog newInstance(String message, boolean cancelable) {

        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        args.putBoolean(ARG_CANCELABLE, cancelable);

        JsAlertDialog fragment = new JsAlertDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public JsAlertDialog setOnConfirmAction(Action onConfirmAction) {
        this.onConfirmAction = onConfirmAction;
        return this;
    }

    public JsAlertDialog setOnCancelmAction(Action onCancelAction) {
        this.onCancelAction = onCancelAction;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = "'";
        boolean cancelable = true;
        if (savedInstanceState != null) {
            message = savedInstanceState.getString(ARG_MESSAGE);
            cancelable = savedInstanceState.getBoolean(ARG_CANCELABLE);
        } else if (getArguments() != null) {
            message = getArguments().getString(ARG_MESSAGE);
            cancelable = getArguments().getBoolean(ARG_CANCELABLE);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    if (onConfirmAction != null) {
                        try {
                            onConfirmAction.run();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        if (cancelable) {
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
                if (onCancelAction != null) {
                    try {
                        onCancelAction.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return builder.create();
    }
}

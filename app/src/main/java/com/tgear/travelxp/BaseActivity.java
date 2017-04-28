package com.tgear.travelxp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.NoSubscriberEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import config.AppConfig;
import network.framework.BaseController;
import network.framework.NetworkErrorObj;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkRequestFailure(NetworkErrorObj networkErrorObj) {
//        Toast.makeText(this, networkErrorObj.errorMessage, Toast.LENGTH_LONG).show();
        showNetworkErrorDialog(networkErrorObj);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoSubscriber(NoSubscriberEvent noSubscriberEvent) {
        if(AppConfig.DEBUG)
            Toast.makeText(this, "No subscribers for event " + noSubscriberEvent.originalEvent.toString(), Toast.LENGTH_LONG).show();
    }

    private void showNetworkErrorDialog(final NetworkErrorObj networkErrorObj) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setTitle("Uh oh..");
        dialog.setMessage(networkErrorObj.errorMessage);
        dialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                new BaseController(BaseActivity.this) {

                    @Override
                    public String getRequestFailureErrorMessage() {
                        return networkErrorObj.errorMessage ;
                    }
                }.enqueue(networkErrorObj.call.clone()); ;
            }
        }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BaseActivity.this.finish();
            }
        });

        final AlertDialog alert = dialog.create();
        alert.show();
    }
}

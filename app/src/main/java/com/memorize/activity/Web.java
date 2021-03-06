package com.memorize.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.memorize.component.MyAlertDialog;
import com.memorize.R;
import com.memorize.service.ConnectionDetector;

public class Web extends AppCompatActivity {

    Boolean isInternetPresent = false;
    MyAlertDialog alert = new MyAlertDialog();
    ConnectionDetector cd;
    private WebView myBlogWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        myBlogWeb = (WebView)findViewById(R.id.myBlogView);
        WebSettings settings = myBlogWeb.getSettings();
        CookieSyncManager.createInstance(this);
        settings.setJavaScriptEnabled(true);
        myBlogWeb.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        myBlogWeb.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        myBlogWeb.getSettings().setJavaScriptEnabled(true);
        cd = new ConnectionDetector(this);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            myBlogWeb.setWebViewClient(new WebViewClient() {

                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.i("WebContent", "Navigating to " + url);
                    view.loadUrl(url);
                    return true;
                }

                public void onPageFinished(WebView view, String url) {
                    Log.i("WebContent", "Finished loading of " + url);
                }

                public void onReceivedError(WebView view, int errorCode, String errorDescription, String errorUrl) {
                    Log.e("Programmer", "ERR AT   -> " + errorUrl);
                    Log.e("WebContent", "ERR CODE -> " + errorCode);
                    Log.e("WebContent", "ERR MSG  -> " + errorDescription);
                    //new Spawner().spawnView(WebContent.this, Offline.class); //TODO: Replace the offline-activity with an server-offline-activity
                }
            });
            myBlogWeb.loadUrl("http://tortuvshin.github.io");

        } else {
            new android.app.AlertDialog.Builder(this,R.style.AlertDialog)
                    .setIcon(R.drawable.fail)
                    .setTitle("Интернэт холболтоо шалгана уу !!!")
                    .setMessage("Интернэтэд холбогдоогүй байна !!!")
                    .setPositiveButton("Тийм", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("Үгүй", null)
                    .show();
        }
    }
}

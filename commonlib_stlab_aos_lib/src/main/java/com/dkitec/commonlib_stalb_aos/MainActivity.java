package com.dkitec.commonlib_stalb_aos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.dkitec.commonlib_stalb_aos.retrofit.RetrofitActivity;
import com.dkitec.commonlib_stalb_aos.utils.Logger;
import com.dkitec.commonlib_stalb_aos.utils.Popup;
import com.dkitec.commonlib_stalb_aos.webview.WebViewActivity;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Button webviewButton;
    private Button retrofitButton;
    private Button encryptionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webviewButton = findViewById(R.id.webviewButton);
        retrofitButton = findViewById(R.id.retrofitButton);
        encryptionButton = findViewById(R.id.encryptionButton);

        webviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });

        retrofitButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RetrofitActivity.class);
            startActivity(intent);
        });

        encryptionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EncryptionActivity.class);
            startActivity(intent);
        });

        Logger.d("MainActivity");
        Popup.showOopsDialog(this, R.string.oops);
    }
}
package com.example.bth09_webapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn1,btn2,btn3;
    EditText edt_url;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }
    private void anhXa(){
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        webView = findViewById(R.id.webview);
        edt_url = findViewById(R.id.edt_url);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {
            String url = "https://ou.edu.vn/";
            webView.loadUrl(url);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                    // Show toast for error
                    Toast.makeText(MainActivity.this, "Lỗi: " + description, Toast.LENGTH_SHORT).show();
                }
            });

        } else if (v.getId() == R.id.btn2) {
            String html = "<html>";
            html += "<head></head>";
            html += "<body>";
            html += "<h1><span style='color:red'>IT-Ouers</span></h1>";
            html += "<h2>Webview Example</h2>";
            html += "<h3>Class: CS2001</h3>";
            html += "<h4>Semester:223</h4>";
            html += "</body>";
            html += "</html>";
            webView.loadData(html, "text/html", "UTF-8");
        }
        else if (v.getId() == R.id.btn3) {
            String url = edt_url.getText().toString()+"/";
            webView.loadUrl(url);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                    // Show toast for error
                    Toast.makeText(MainActivity.this, "Lỗi: " + description, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
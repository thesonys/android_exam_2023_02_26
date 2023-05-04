package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUrl;
    private WebView webViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUrl = findViewById(R.id.activity_main_editTextUrl);
        webViewMain = findViewById(R.id.activity_main_webViewMain);

        //웹 세팅 객체 가져와서
        WebSettings webSettings = webViewMain.getSettings();
        //수정한다.(자바스크립트 엔진 활성화)
        webSettings.setJavaScriptEnabled(true);

        webViewMain.setWebViewClient(new WebViewClient());

        editTextUrl.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //검색
                String url = editTextUrl.getText().toString().trim();

                if (url.startsWith("http://") == false && url.startsWith("https://") == false) {
                    url = "http://" + url;
                    editTextUrl.setText(url);
                }

                goToUrl(url);

                //키보드 내리기
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);

                return true;
            }

            return false;
        });
    }

    public void goToUrl(String url) {
        webViewMain.loadUrl(url);
    }

    public void refreshButtonCicked(View view) {
        webViewMain.reload();
    }

    @Override
    public void onBackPressed(){
        if(webViewMain.canGoBack()){
            webViewMain.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
package com.jinwoo.android.myutility;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends Fragment {
    View view;
    WebView webView;

    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Holder 처리 : 이전에 접속했던 페이지가 나온다.
        if(view != null){
            return view;
        }
       view = inflater.inflate(R.layout.fragment_three, container, false);

        webView = (WebView)view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        // 줌 사용설정
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        // 3. 웹뷰 클라이언트를 지정
        webView.setWebViewClient(new WebViewClient());
        // 3.1 https 등을 처리하기 위한 핸들러
        webView.setWebChromeClient(new WebChromeClient());

        // 4. 최초 로드시 google.com 으로 이동
        webView.loadUrl("http://google.com");

        return view;
    }

    public boolean goBack() {
        if(webView.canGoBack()){
            webView.goBack();
            return true;
        }else{
            return false;
        }
    }
}

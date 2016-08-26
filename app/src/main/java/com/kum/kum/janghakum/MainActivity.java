package com.kum.kum.janghakum;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.*;
import com.microsoft.windowsazure.notifications.NotificationsManager;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
    //Google 권장 사항에 따라 Google Play Services의 가용성을 확인합니다.
    private static final String TAG = "MainActivity" ;
    public static MainActivity mainActivity;
    public static Boolean isVisible = false;
    private GoogleCloudMessaging gcm;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        //IntentService를 호출하는 다음 코드를 추가하여 GCM 등록 토큰을 가져와 알림 허브에 등록합니다.
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported by Google Play Services.");
                finish();
            }
            return false;
        }
        return true;
    }
    public void registerWithNotificationHubs()
    {
        Log.i(TAG, " Registering with Notification Hubs");

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        NotificationsManager.handleNotifications(this, NotificationSettings.SenderId, MyHandler.class);
        registerWithNotificationHubs();
        */


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        WebView myWebView = (WebView)findViewById(R.id.webview);

        //웹뷰 설정
        myWebView.setBackgroundColor(0); //배경색
        myWebView.setHorizontalScrollBarEnabled(false); //가로 스크롤
        myWebView.setVerticalScrollBarEnabled(false);   //세로 스크롤
        myWebView.setScrollBarStyle(myWebView.SCROLLBARS_OUTSIDE_OVERLAY); // 스크롤 노출 타입
        myWebView.setScrollbarFadingEnabled(true); // 스크롤 페이딩 처리 여부

        WebSettings set = myWebView.getSettings();

        set.setJavaScriptEnabled(true); // javascript를 실행할 수 있도록 설정
        set.setJavaScriptCanOpenWindowsAutomatically (true);   // javascript가 window.open()을 사용할 수 있도록 설정
        set.setBuiltInZoomControls(false); // 안드로이드에서 제공하는 줌 아이콘을 사용할 수 있도록 설정
        set.setPluginState(WebSettings.PluginState.ON_DEMAND); // 플러그인을 사용할 수 있도록 설정
        set.setSupportMultipleWindows(false); // 여러개의 윈도우를 사용할 수 있도록 설정
        set.setSupportZoom(false); // 확대,축소 기능을 사용할 수 없도록 설정
        set.setBlockNetworkImage(false); // 네트워크의 이미지의 리소스를 로드하지않음
        set.setLoadsImagesAutomatically(true); // 웹뷰가 앱에 등록되어 있는 이미지 리소스를 자동으로 로드하도록 설정
        set.setUseWideViewPort(true); // wide viewport를 사용하도록 설정
        myWebView.getSettings().setLoadWithOverviewMode(true); //size web에 맞춤
        set.setCacheMode(WebSettings.LOAD_NO_CACHE); // 웹뷰가 캐시를 사용하지 않도록 설정

        myWebView.loadUrl("http://52.78.108.12/php/login_and_register.php");
        myWebView.setWebViewClient(new myWebViewClient());

        //OnCreate 메서드에서 활동이 생성되면 등록 프로세스를 시작하는 다음 코드를 추가합니다
        mainActivity = this;
        NotificationsManager.handleNotifications(this, NotificationSettings.SenderId, MyHandler.class);
        registerWithNotificationHubs();
    }
    class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isVisible = false;
    }

    /*
    public void ToastNotify(final String notificationMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, notificationMessage, Toast.LENGTH_LONG).show();
                TextView helloText = (TextView) findViewById(R.id.text_hello);
                helloText.setText(notificationMessage);
            }
        });
    }
    */
}

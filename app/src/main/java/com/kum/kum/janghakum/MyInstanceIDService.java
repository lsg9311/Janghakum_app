package com.kum.kum.janghakum;

/**
 * Created by crit9 on 2016-08-06.
 * Google 인스턴스 ID API를 사용하여 등록 토큰 가져오기에 사용되는 인스턴스 ID 수신기 서비스
 *
 */
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.iid.InstanceIDListenerService;

public class MyInstanceIDService extends InstanceIDListenerService {

    private static final String TAG = "MyInstanceIDService";

    //IntentService를 호출하여 백그라운드에서 GCM 토큰을 새로 고칩니다.
    @Override
    public void onTokenRefresh() {

        Log.i(TAG, "Refreshing GCM Registration Token");

        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
};

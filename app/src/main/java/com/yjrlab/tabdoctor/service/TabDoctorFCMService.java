package com.yjrlab.tabdoctor.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.yjrlab.tabdoctor.libs.Dlog;
import com.yjrlab.tabdoctor.view.PopupActivity;
import com.yjrlab.tabdoctor.R;
import com.yjrlab.tabdoctor.model.PushQuestionModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jongrakmoon on 2017. 6. 12..
 */

public class TabDoctorFCMService extends FirebaseMessagingService {

    public static final int NOTIFICATION_ID = 183737;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Dlog.d(remoteMessage.getData().toString());
        //Main Thread이므로 UI 처리 핸들러 사용
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                //Toast.makeText(getApplicationContext(), "푸쉬가 왔습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        PushQuestionModel model = new PushQuestionModel();

        Map<String, String> data = remoteMessage.getData();

        try {
            Dlog.d(data + "mpr_id" + data.get("mpr_id") + " //");
            model.setMprId(Integer.parseInt(data.get("mpr_id")));
            model.setBsId(Integer.parseInt(data.get("bs_id")));
            model.setPobId(Integer.parseInt(data.get("pob_id")));
            model.setBsLevel(Integer.parseInt(data.get("bs_level")));
            model.setBsContent(data.get("bs_content"));
            model.setBsGroupCode(data.get("bs_group_code"));
            model.setDdExplain(data.get("dd_explain"));

            sendNotification(model);
            showDialog(model);
        } catch (NumberFormatException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "푸쉬 파싱 에러(관리자에게 문의하세요)", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void sendNotification(PushQuestionModel pushQuestionModel) {
        Intent intent = new Intent(this, PopupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(PopupActivity.INTENT_QUESTION_MODEL, pushQuestionModel);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_logo)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentTitle("당신의 생활 주치의 TabDoctor")
                .setContentText(pushQuestionModel.getBsContent())
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void showDialog(PushQuestionModel pushQuestionModel) {
        Intent dialogIntent = new Intent(this, PopupActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        dialogIntent.putExtra(PopupActivity.INTENT_QUESTION_MODEL, pushQuestionModel);
        startActivity(dialogIntent);
    }
}

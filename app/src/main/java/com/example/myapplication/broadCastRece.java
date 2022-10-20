package com.example.myapplication;

import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class broadCastRece extends BroadcastReceiver {
    public final static String MyAction = "com.example.myapplication.ACTION_MY_BROADCAST";

    NotificationManager manager;
    NotificationCompat.Builder builder;

    private static String CHANNEL_ID = "channel1";
    private static String CHANEL_NAME = "Channel1";

    @Override
    public void onReceive(Context context, Intent intent){
        if(Intent.ACTION_TIME_CHANGED == intent.getAction()){
            System.out.println("시간 변경");
        }
    }
    public void showNoti(Context context){
        builder = null;
        manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        //버전 오레오 이상일 경우
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            );

            builder = new NotificationCompat.Builder(context,CHANNEL_ID);

            //하위 버전일 경우
        }else{
            builder = new NotificationCompat.Builder(context);
        }


        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("name","abc");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 101,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);



        //알림창 제목
        builder.setContentTitle("알림");

        //알림창 메시지
        builder.setContentText("알림 메시지");

        //알림창 아이콘
        builder.setSmallIcon(R.mipmap.ic_ref_foreground);

        //알림창 터치시 상단 알림상태창에서 알림이 자동으로 삭제되게 합니다.
        builder.setAutoCancel(true);

        //pendingIntent를 builder에 설정 해줍니다.
        //알림창 터치시 인텐트가 전달할 수 있도록 해줍니다.
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();

        //알림창 실행
        manager.notify(1,notification);
    }

}

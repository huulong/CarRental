package com.greenhuecity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "ThongBao";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            // Nếu thông báo được gửi từ Firebase Console và có sử dụng notification payload
            handleNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            // Nếu có dữ liệu đi kèm với thông báo
            handleDataMessage(remoteMessage.getData());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void handleNotification(String title, String body) {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_carr)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);

        // Cài đặt âm thanh cho thông báo
        builder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.custom_sound));

        // Hiển thị thông báo
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(102, builder.build());
    }

    private void handleDataMessage(Map<String, String> data) {
        createNotificationChannel();

        // Xử lý dữ liệu đi kèm thông báo
        String customData = data.get("custom_key");
        // Có thể xử lý dữ liệu khác tùy thuộc vào cấu trúc của dữ liệu bạn nhận được

        // Hiển thị thông báo dựa trên dữ liệu nhận được nếu cần
        // Ví dụ:
        String title = "Custom Data Title";
        String body = "Custom Data Body: " + customData;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_carr)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);

        // Cài đặt âm thanh cho thông báo
        builder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.custom_sound));

        // Hiển thị thông báo
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(103, builder.build());
    }
}

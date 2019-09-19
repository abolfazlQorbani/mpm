package com.example.rezaei.mpmprojcet2.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.example.rezaei.mpmprojcet2.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;

import spencerstudios.com.bungeelib.Bungee;

public class LoginActivity extends AppCompatActivity {

    private EditText phone,code;
    private ImageView submit;
    private TextView msg;
    private Bitmap icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setComponent();

    }
    private void setComponent(){
        phone=findViewById(R.id.phone);
        code=findViewById(R.id.code);
        msg=findViewById(R.id.msg);
        submit=findViewById(R.id.submit);
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        icon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_launcher);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataForSimpleNotification();
                if(phone.isEnabled())
                getInfo();
                else
                    getInfoCode();

            }
        });

    }
    private void getInfo(){
        if(phone.getText()!=null &&  phone.getText().toString().matches("09\\d\\d\\d\\d\\d\\d\\d\\d\\d")){
            phone.setEnabled(false);
            code.setVisibility(View.VISIBLE);
            msg.setText(getString(R.string.reciveCode));

        }else
            phone.setError(getString(R.string.errorNull));
    }
    private void getInfoCode(){
        if(code.getText()!=null &&  code.getText().toString().matches("\\d\\d\\d\\d\\d\\d")){

            Intent intent=new Intent(this, MainActivity.class);
            this.startActivity(intent);
            Bungee.zoom(LoginActivity.this);
            finish();
        }else
            code.setError(getString(R.string.errorNull));
    }


    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private void sendNotification() {
        Intent notificationIntent = new Intent(this, LoginActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
         notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
     //   currentNotificationID++;
      //  int notificationId = currentNotificationID;
        int notificationId = 2;
        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0;
        notificationManager.notify(notificationId, notification);
    }

    private void setDataForSimpleNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "dsds";
            String description = "DFASFASDF";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("my_channel_01", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder= new NotificationCompat.Builder(this, "my_channel_01")
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle("My notification")
                    .setContentText("Much longer text that cannot fit one line...")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Much longer text that cannot fit one line..."))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }
        sendNotification();
    }
    private void setDataForNotificationWithActionButton() {
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
               // .setLargeIcon(icon)
                .setContentTitle("alisdfasd")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("fadfa"))
                .setContentText("fadsfa");
        Intent answerIntent = new Intent(this, MainActivity.class);
        answerIntent.setAction("Yes");
        PendingIntent pendingIntentYes = PendingIntent.getActivity(this, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.addAction(R.drawable.icon, "Yes", pendingIntentYes);
        answerIntent.setAction("No");
        PendingIntent pendingIntentNo = PendingIntent.getActivity(this, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.addAction(R.drawable.icon, "No", pendingIntentNo);
        sendNotification();
    }
   /* private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }*/

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "dsds";
            String description = "DFASFASDF";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("my_channel_01", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_channel_01")
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle("My notification")
                    .setContentText("Much longer text that cannot fit one line...")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Much longer text that cannot fit one line..."))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }
    }
}

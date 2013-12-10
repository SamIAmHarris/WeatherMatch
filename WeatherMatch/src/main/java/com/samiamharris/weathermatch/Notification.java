package com.samiamharris.weathermatch;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

/**
 * Created by samharris on 12/6/13.
 */
public class Notification {

    Context context;

    public Notification(Context context) {

       this.context = context;

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.one);

        RemoteViews customNotificationView = new RemoteViews(context.getPackageName(),
                R.layout.custom_notification);
        customNotificationView.setTextViewText(R.id.textone, "So Cold in the D!");
        customNotificationView.setImageViewResource(R.id.imageone, R.drawable.one);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                    .setLargeIcon(bm)
                    .setSmallIcon(R.drawable.one)
                    .setContentTitle("My notification")
                    .setContentText("Hello World!");

        mBuilder.setContent(customNotificationView);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, WeatherActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(WeatherActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
            );

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        int mId = 1;
        mNotificationManager.notify(mId, mBuilder.build());


    }
}

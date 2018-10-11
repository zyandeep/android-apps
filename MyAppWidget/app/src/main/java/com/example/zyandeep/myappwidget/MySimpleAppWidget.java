package com.example.zyandeep.myappwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


public class MySimpleAppWidget extends AppWidgetProvider {

   static final String MY_BROADCAST_ACTION = "zyandeep.appwidget.SHOW_TOAST";

   static final String TAG = "APP_WIDGET";

   private static int mCount = 0;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        // inflate the layout in a RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_simple_app_widget);


        // set the counter text
        views.setTextViewText(R.id.count_tv, "" + mCount);



        // Add a clickable functionality to the whole widget
        // So when clicked, launches the MainActivity
        PendingIntent pi =
                PendingIntent.getActivity(context, 12, new Intent(context, MainActivity.class), 0);

        views.setOnClickPendingIntent(R.id.my_widget_layout, pi);



        // Add a clickable functionality to the text view
        Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com/"));
        PendingIntent p2 = PendingIntent.getActivity(context, 155, i2, 0);

        views.setOnClickPendingIntent(R.id.textView, p2);


        // Add a clickable functionality to the button
        Intent i3 = new Intent(MY_BROADCAST_ACTION);
        i3.putExtra("widget_id", appWidgetId);

        PendingIntent p3 = PendingIntent.getBroadcast(context, 98, i3, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button, p3);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        //Log.d(TAG, intent.getAction());

        if (intent.getAction().equalsIgnoreCase(MY_BROADCAST_ACTION)) {
            // show a toast and update the counter
            Toast.makeText(context, "You clicked the widget", Toast.LENGTH_SHORT).show();

            mCount++;

            // update the widget
            updateAppWidget(context, AppWidgetManager.getInstance(context), intent.getIntExtra("widget_id", -1));

        }
    }




    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


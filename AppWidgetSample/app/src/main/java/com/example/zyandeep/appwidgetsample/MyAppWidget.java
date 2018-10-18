package com.example.zyandeep.appwidgetsample;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Arrays;


public class MyAppWidget extends AppWidgetProvider {

    private static final String PREF_FILE = "com.example.zyandeep.appwidgetsample.my_pref";
    private static final String COUNT_KEY = "count";



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        // Inflate the Layout
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);

        views.setTextViewText(R.id.id_tv, appWidgetId + "");


        // get the count from the sharedPreference file
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);

        int count = pref.getInt(COUNT_KEY + appWidgetId, 0);
        count++;

        views.setTextViewText(R.id.update_tv, String.format("%d @ %tr", count, System.currentTimeMillis()));

        // save the count in the sharedPreference file
        SharedPreferences.Editor editor =pref.edit();
        editor.putInt(COUNT_KEY + appWidgetId, count);
        editor.apply();



        // handle the button click event
        Intent i = new Intent(context, MyAppWidget.class);
        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{appWidgetId});

        views.setOnClickPendingIntent(R.id.button, PendingIntent.getBroadcast(context, appWidgetId, i,
                PendingIntent.FLAG_UPDATE_CURRENT));


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }




    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        //Log.d("MY_WIDGET", Arrays.toString(appWidgetIds));


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
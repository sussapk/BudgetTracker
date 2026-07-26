package com.sussapk.budgettracker

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import android.app.PendingIntent
import android.content.Intent

/**
 * Implementation of App Widget functionality.
 */
class QuickCashWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}



internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {

    val intent = Intent(
        context,
        MainActivity::class.java
    ).apply {
        putExtra("openAddCashEntry", true)
    }

    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val views = RemoteViews(
        context.packageName,
        R.layout.quick_cash_widget
    )

    // Set widget text
    views.setTextViewText(
        R.id.appwidget_text,
        "+ Add\nCash"
    )

    // Click action
    views.setOnClickPendingIntent(
        R.id.appwidget_text,
        pendingIntent
    )

    appWidgetManager.updateAppWidget(
        appWidgetId,
        views
    )
}
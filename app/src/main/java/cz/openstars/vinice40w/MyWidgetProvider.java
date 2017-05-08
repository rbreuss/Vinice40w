package cz.openstars.vinice40w;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;


public class MyWidgetProvider extends AppWidgetProvider {

    private static final String ACTION_CLICK = "ACTION_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String tbar = null;
        String tmin = null;
        String tmax = null;
        String srazky = null;
        String stredni = null;
        String kalamita = null;
        String pbar = null;
        String RH = null;



        ComponentName thisWidget = new ComponentName(context,
                MyWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {


            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

            // Set the text
            remoteViews.setTextViewText(R.id.vinice, "Vinice Ťuhýkov :      ");
            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();


            try {
                tbar = new Communicator().executeHttpGets("http://www.openstars.cz/emoncms/feed/value.json?apikey=04ccd96a8f3bdba5c7c4670c24a997d9&id=288");
                tmin = new Communicator().executeHttpGets("http://www.openstars.cz/emoncms/feed/value.json?apikey=04ccd96a8f3bdba5c7c4670c24a997d9&id=227");
                tmax = new Communicator().executeHttpGets("http://www.openstars.cz/emoncms/feed/value.json?apikey=04ccd96a8f3bdba5c7c4670c24a997d9&id=226");
                srazky = new Communicator().executeHttpGets("http://www.openstars.cz/emoncms/feed/value.json?apikey=04ccd96a8f3bdba5c7c4670c24a997d9&id=294");
                stredni = new Communicator().executeHttpGets("http://www.openstars.cz/emoncms/feed/value.json?apikey=04ccd96a8f3bdba5c7c4670c24a997d9&id=296");
                kalamita = new Communicator().executeHttpGets("http://www.openstars.cz/emoncms/feed/value.json?apikey=04ccd96a8f3bdba5c7c4670c24a997d9&id=295");
                RH = new Communicator().executeHttpGets("http://www.openstars.cz/emoncms/feed/value.json?apikey=04ccd96a8f3bdba5c7c4670c24a997d9&id=221");
                pbar = new Communicator().executeHttpGets("http://www.openstars.cz/emoncms/feed/value.json?apikey=04ccd96a8f3bdba5c7c4670c24a997d9&id=287");

                Log.w("WidgetExample", String.valueOf(tbar));
            } catch (Exception e) {
                //Console.WriteLine("{0} exception caught.", e);
                // TODO Auto-generated catch block
                e.printStackTrace();

            }

            remoteViews.setTextViewText(R.id.tbar, "Tbar: " + (tbar != null ? tbar.substring(1, tbar.length()-2) : null) + " C");
            remoteViews.setTextViewText(R.id.tmin, "Tmin: " + (tmin != null ? tmin.substring(1, tmin.length() - 2) : null) +" C");
            remoteViews.setTextViewText(R.id.tmax, "Tmax: " + (tmax != null ? tmax.substring(1, tmax.length() - 2) : null) +" C");
            remoteViews.setTextViewText(R.id.srazky, "Srážky: " + (srazky != null ? srazky.substring(1, srazky.length() - 3) : null) +" mm");
            remoteViews.setTextViewText(R.id.stredni, "Střední: " + (stredni != null ? stredni.substring(1, stredni.length() - 2) : null) +" mm              ");
            remoteViews.setTextViewText(R.id.kalamita, "Kalamita: " + (kalamita != null ? kalamita.substring(1, kalamita.length() - 2) : null) +" mm");
            remoteViews.setTextViewText(R.id.RH, "RH: " + (RH != null ? RH.substring(1, RH.length() - 2) : null) +" %");
            remoteViews.setTextViewText(R.id.pbar, "Dnešní: " + (pbar != null ? pbar.substring(1, pbar.length() - 8) : null) +" mm");
            remoteViews.setTextViewText(R.id.update, today.format("%k:%M:%S"));
            remoteViews.setTextViewText(R.id.placeholder1, "Vinice 4.0");
            remoteViews.setTextViewText(R.id.placeholder2, "www.aeromap.cz");

            // Register an onClickListener
            Intent intent = new Intent(context, MyWidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
            remoteViews.setOnClickPendingIntent(R.id.vinice, pendingIntent);
            remoteViews.setOnClickPendingIntent(R.id.tmin, pendingIntent);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }

    }

}
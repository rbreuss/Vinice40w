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
        String RH = null;
        String srazkyD = null;
        String Tavg = null;
        String pbar = null;
        String pero = null;
        String padli = null;



        ComponentName thisWidget = new ComponentName(context,
                MyWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {


            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

            // Set the text
            remoteViews.setTextViewText(R.id.vinice, "Mikulčice :      ");
            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();


            try {
                tbar = new Communicator().executeHttpGets("http://www.vinice40.cz/meteo/feed/value.json?apikey=0d53cb487ff215fee200a0003a4f80a4&id=171");
                tmin = new Communicator().executeHttpGets("http://www.vinice40.cz/meteo/feed/value.json?apikey=0d53cb487ff215fee200a0003a4f80a4&id=173");
                tmax = new Communicator().executeHttpGets("http://www.vinice40.cz/meteo/feed/value.json?apikey=0d53cb487ff215fee200a0003a4f80a4&id=172");
                srazky = new Communicator().executeHttpGets("http://www.vinice40.cz/meteo/feed/value.json?apikey=0d53cb487ff215fee200a0003a4f80a4&id=193");
                stredni = new Communicator().executeHttpGets("http://www.vinice40.cz/meteo/feed/value.json?apikey=0d53cb487ff215fee200a0003a4f80a4&id=31");
                kalamita = new Communicator().executeHttpGets("http://www.vinice40.cz/meteo/feed/value.json?apikey=0d53cb487ff215fee200a0003a4f80a4&id=32");
                RH = new Communicator().executeHttpGets("http://www.vinice40.cz/meteo/feed/value.json?apikey=0d53cb487ff215fee200a0003a4f80a4&id=157");
                srazkyD = new Communicator().executeHttpGets("http://www.vinice40.cz/meteo/feed/value.json?apikey=0d53cb487ff215fee200a0003a4f80a4&id=189");
                Tavg = new Communicator().executeHttpGets("http://www.vinice40.cz/meteo/feed/value.json?apikey=0d53cb487ff215fee200a0003a4f80a4&id=214");
                pbar = new Communicator().executeHttpGets("http://www.openstars.cz/emoncms/feed/value.json?apikey=04ccd96a8f3bdba5c7c4670c24a997d9&id=62");
                pero = new Communicator().executeHttpGets("http://www.vinice40.cz/meteo/feed/value.json?apikey=0d53cb487ff215fee200a0003a4f80a4&id=212");
                padli = new Communicator().executeHttpGets("http://www.vinice40.cz/meteo/feed/value.json?apikey=0d53cb487ff215fee200a0003a4f80a4&id=210");

                Log.w("WidgetExample", String.valueOf(tbar));
            } catch (Exception e) {
                //Console.WriteLine("{0} exception caught.", e);
                // TODO Auto-generated catch block
                e.printStackTrace();

            }

            remoteViews.setTextViewText(R.id.tbar, "Tbar: " + (tbar != null ? tbar.substring(0, tbar.length() -1) : null) + " C");
            remoteViews.setTextViewText(R.id.tmin, "Tmin: " + (tmin != null ? tmin.substring(0, tmin.length() -1) : null) +" C");
            remoteViews.setTextViewText(R.id.tmax, "Tmax: " + (tmax != null ? tmax.substring(0, tmax.length() -1) : null) +" C");
            remoteViews.setTextViewText(R.id.srazky, "Srážky: " + (srazky != null ? srazky.substring(0, srazky.length() -1) : null) +" mm");
            remoteViews.setTextViewText(R.id.stredni, "Střední: " + (stredni != null ? stredni.substring(0, stredni.length() -1) : null) +" mm              ");
            remoteViews.setTextViewText(R.id.kalamita, "Kalamita: " + (kalamita != null ? kalamita.substring(0, kalamita.length() -1) : null) +" mm");
            remoteViews.setTextViewText(R.id.RH, "RH: " + (RH != null ? RH.substring(0, RH.length() -1) : null) +" %");
            remoteViews.setTextViewText(R.id.srazkyD, "Dnešní: " + (srazkyD != null ? srazkyD.substring(0, srazkyD.length() -1) : null) +" mm");
            remoteViews.setTextViewText(R.id.Tavg, "Tavg: " + (Tavg != null ? Tavg.substring(0, Tavg.length() -1) : null) +" C  ");
            remoteViews.setTextViewText(R.id.pbar, "Pbar: " + (pbar != null ? pbar.substring(1, pbar.length() - 3) : null) +" hPa");
            remoteViews.setTextViewText(R.id.pero, "Plíseň [0-3]: " + (pero != null ? pero.substring(0, pero.length() -1) : null) +"");
            remoteViews.setTextViewText(R.id.padli, "Padlí: " + (padli != null ? padli.substring(0, padli.length() -1) : null) +" %");

            remoteViews.setTextViewText(R.id.update, today.format("%k:%M:%S"));
            remoteViews.setTextViewText(R.id.placeholder1, "Vinice 4.0");
            remoteViews.setTextViewText(R.id.placeholder2, "www.vinice40.cz");

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
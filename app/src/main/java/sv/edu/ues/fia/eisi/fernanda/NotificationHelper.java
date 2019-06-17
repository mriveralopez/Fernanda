package sv.edu.ues.fia.eisi.fernanda;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        //Compruebe si dispositivo tiene un vibrador.
        if (vibrator.hasVibrator()) {
//            long tiempo = 500;
//            vibrator.vibrate(tiempo);

            long[] pattern = {400, //sleep
                    600, //vibrate
                    100,300,100,150,100,75};
            // con -1 se indica desactivar repeticion del patron
            vibrator.vibrate(pattern, -1);
        }

        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Alarma!")
                .setContentText("Es hora de Despertar.")
                .setSmallIcon(R.drawable.ic_access_alarm_black_24dp);
    }
}

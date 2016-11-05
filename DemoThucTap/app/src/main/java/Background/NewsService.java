package Background;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by asus on 25/10/2016.
 */
public class NewsService extends Service {
    public static final String NEWS_INTENT = "bs.kalender.news";
    private Timer timer = new Timer();

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        startService();
    }

    private void startService() {
        timer.scheduleAtFixedRate(new NewsChecker(), 0, 5000);
    }

    private class NewsChecker extends TimerTask {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), NewsReceiver.class);
            sendBroadcast(intent);
        }
    }
}
package hu.daniels.servicetestapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class MainService extends Service {

    private Runnable runnable;
    private boolean run;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        runnable = new Runnable() {
            @Override
            public void run() {
                while(run) {
                    System.out.println("alalalalallala");
                }
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        run = true;
        new Thread(runnable).start();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        run = false;
        runnable = null;
        super.onDestroy();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }
}

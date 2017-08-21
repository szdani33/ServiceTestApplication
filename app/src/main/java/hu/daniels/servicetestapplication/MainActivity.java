package hu.daniels.servicetestapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button startBt;
    private Button bindBt;
    private Button stopBt;
    private Button unbindBt;
    private ServiceConnection serviceConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        startBt = (Button) findViewById(R.id.startBt);
        bindBt = (Button) findViewById(R.id.bindBt);
        stopBt = (Button) findViewById(R.id.stopBt);
        unbindBt = (Button) findViewById(R.id.unbindBt);
        startBt.setEnabled(true);
        bindBt.setEnabled(false);
        stopBt.setEnabled(false);
        unbindBt.setEnabled(false);
    }

    public void startService(View view) {
        startBt.setEnabled(false);
        bindBt.setEnabled(true);
        stopBt.setEnabled(true);
        unbindBt.setEnabled(false);
        startService(new Intent(this, MainService.class));
    }

    public void stopService(View view) {
        startBt.setEnabled(true);
        bindBt.setEnabled(false);
        stopBt.setEnabled(false);
        unbindBt.setEnabled(false);
        stopService(new Intent(this, MainService.class));
    }

    public void bindService(View view) {
        bindBt.setEnabled(false);
        unbindBt.setEnabled(true);
        serviceConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        bindService(new Intent(this, MainService.class), serviceConn, BIND_AUTO_CREATE);
    }


    public void unbindService(View view) {
        bindBt.setEnabled(true);
        unbindBt.setEnabled(false);
        unbindService(serviceConn);
    }
}

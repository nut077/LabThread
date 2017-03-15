package labthread.freedomseven.com.labthread;

import android.app.Application;

import labthread.freedomseven.com.labthread.manager.Contextor;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}

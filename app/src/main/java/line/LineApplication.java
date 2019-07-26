package line;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

/**
 * LineApplication
 * Created by ying.fu on 2018/7/16.
 */

public class LineApplication extends Application {
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }
}

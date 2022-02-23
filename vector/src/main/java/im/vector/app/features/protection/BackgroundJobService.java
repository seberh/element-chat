package im.vector.app.features.protection;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import timber.log.Timber;

public class BackgroundJobService extends JobIntentService {

    public static final int JOB_ID = 1000;
    public static final String SKIP_BG_CHECK = "skipBackgroundCheck";
    private final String LOCK_TIME = "LockTime";

    public static volatile boolean shouldContinue = false;

    private static final Object lock = new Object();
    private static final long BACKGROUND_TIMEOUT_WAIT_MS = 5000;

    final Handler mHandler = new Handler();

    public static boolean stopPassProtect = false;
    public static boolean isWorking = false;

    private static SharedSettings sharedSettings;

    public static void enqueueWork(Context context) {
        Intent bgJobService = new Intent(context, BackgroundJobService.class);
        BackgroundJobService.enqueueWork(
                context, BackgroundJobService.class, BackgroundJobService.JOB_ID, bgJobService);
        sharedSettings = new SharedSettings(context);
    }

    public static void stopWork() {
        synchronized (lock) {
            shouldContinue = false;
            lock.notifyAll();
        }
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        shouldContinue = true;
        try {
            synchronized (lock) {
                lock.wait(BACKGROUND_TIMEOUT_WAIT_MS);
                if (shouldContinue) {
                    // Do the background work here
                    toast("background work started");
                    isWorking = true;

                    int time = sharedSettings.getValueInt(LOCK_TIME);
                    Log.d("yyyy", "pref: " + time);
                    while (time > 0) {
                        Thread.sleep(1000);
                        Log.d("yyyy", time + "sec");
                        time--;
                    }
                    toast("background work finished");
                    isWorking = false;
                    stopPassProtect = true;
                }
            }
        } catch (InterruptedException ex) {
//            Log.e(
//                    BackgroundJobService.class.getName(),
//                    "There was an error waiting for background check",
//                    ex);
            Timber.d(ex, "There was an error waiting for background check");
        }
    }

    // Helper for showing tests
    void toast(final CharSequence text) {
        mHandler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BackgroundJobService.this, text, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

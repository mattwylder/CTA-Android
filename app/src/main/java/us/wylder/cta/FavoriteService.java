package us.wylder.cta;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import de.greenrobot.event.EventBus;
import us.wylder.cta.data.EtaObject;
import us.wylder.cta.data.StopDB;

public class FavoriteService extends Service {

    private static final String TAG = "Favorite Service";

    private IBinder mBinder = new LocalBinder();

    Thread thread;

    StopDB db;

    public FavoriteService() {
    }

    @Override
    public void onCreate(){
        Log.d(TAG, "Service On Create");
        db = StopDB.getInstance(getApplicationContext());

        Cursor c = db.getFavoriteCursor();

        c.moveToFirst();

        int staId;

        if(!c.isAfterLast()) {
            Log.d(TAG, "Found a favorite");
            staId = c.getInt(c.getColumnIndex("_id"));
            thread = new Thread(new NotifyRunnable(staId));
            thread.start();
        }


    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG, "onBind");
        return mBinder;
    }

    public class LocalBinder extends Binder {
        FavoriteService getService(){ return FavoriteService.this;}
    }

    private class NotifyRunnable extends APIConnectionRunnable{
        public NotifyRunnable(int staId){
            super(staId, false);
        }
        @Override
        public void postProcess(){
            Log.d(TAG, "Post Processing");
            if(result != null)
            {for(EtaObject obj: result){
                if(obj.getTimeDifferenceRaw() < 11){
                    EventBus.getDefault().post(obj);
                    Log.d(TAG, obj.destName + " is coming in " + obj.getTimeDifference());
                    break;
                }
            }
            }
        }
    }
}

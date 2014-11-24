package us.wylder.cta;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class FavoriteService extends Service {

    private IBinder mBinder = new LocalBinder();

    Thread thread;

    public FavoriteService() {
    }

    @Override
    public void onCreate(){
        thread = new Thread(new Runnable(){
           public void run(){

           }
        });
    }

    @Override
    public void onDestroy(){

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    public class LocalBinder extends Binder {
        FavoriteService getService(){ return FavoriteService.this;}
    }
}

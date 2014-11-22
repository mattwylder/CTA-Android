package us.wylder.cta.data;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mattwylder on 11/21/14.
 */
public class EtaObject {

    private static final String TAG = "ETA Object:";

    public String destName;
    public String eta;
    public String now;
    public EtaObject(String dest, String eta, String now)
    {
        this.destName = dest;
        this.eta = eta;
        this.now = now;
    }

    public String getTimeDifference()
    {
        String result = "";
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date timeQueried = null;
        Date arrivalTime = null;
        try{
            timeQueried = fmt.parse(now);
            arrivalTime = fmt.parse(eta);

            long diff = arrivalTime.getTime() - timeQueried.getTime();
            Log.d(TAG, "Difference is " + diff);

            long diffMinutes = diff/ (60*1000) % 60;
            result = ""+ diffMinutes + " min";
            Log.d(TAG, "Result is " + result);

        } catch (Exception e)
        {
            Log.d(TAG, e.getMessage());
        }
        return result;
    }

}

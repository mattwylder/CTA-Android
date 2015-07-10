package us.wylder.cta.data;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mattwylder on 11/21/14.
 */
public class Arrival {

    private static final String TAG = "ETA Object:";

    public String destName;
    public String eta;
    public String now;

    public Arrival(String dest, String eta, String now) {
        this.destName = dest;
        this.eta = eta;
        this.now = now;
    }

    public String getTimeDifference() {
        return "" + getTimeDifferenceRaw() + " min";
    }

    public long getTimeDifferenceRaw() {

        long result = 0;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date timeQueried = null;
        Date arrivalTime = null;
        try {
            timeQueried = fmt.parse(now);
            arrivalTime = fmt.parse(eta);

            long diff = arrivalTime.getTime() - timeQueried.getTime();
            Log.d(TAG, "Difference is " + diff);

            long diffMinutes = diff / (60 * 1000) % 60;

            result = diffMinutes;
            Log.d(TAG, "Result is " + diffMinutes);

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        return result;

    }

}

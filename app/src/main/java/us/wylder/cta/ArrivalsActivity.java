package us.wylder.cta;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import us.wylder.cta.data.StopDB;


public class ArrivalsActivity extends ListActivity {

    public final String KEY = "c23c0f6e332d4371b6a998158b018e0e";
    private static final String TAG = "ArrivalsActivity";

    private int staId;
    private String name;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrivals);
        staId = getIntent().getIntExtra("staId", 0);
        name = getIntent().getStringExtra("name");
        this.setTitle(name);
        Log.d(TAG, "About to run");
        //new Thread(new APIConnectionRunnable()).start();
        Log.d(TAG, "Ran");
        


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.arrivals, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class APIConnectionRunnable implements Runnable {

        private static final String TAG = "APIConnectionRunnable";

        @Override
        public void run() {
            Log.d(TAG, "Running");
            URL url;
            HttpURLConnection con;
            BufferedReader rd;
            String line;
            String result = "";

            try{
                url = new URL("http://lapi.transitchicago.com/api/1.0/ttarrivals.aspx?key=" + KEY +
                        "&mapid=" + staId);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while((line = rd.readLine()) != null){
                    result += line;
                }
                rd.close();
            } catch (final Exception e)
            {

                Log.d(TAG, "Exception"+ e);
            }
            Log.d(TAG, "Result: " + result);
            final String res = result;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    TextView t = (TextView) findViewById(R.id.hello);
                    t.setText(res);
                }
            });
        }
    }


}

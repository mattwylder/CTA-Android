package us.wylder.cta.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import us.wylder.cta.APIConnectionRunnable;
import us.wylder.cta.adapter.ArrivalAdapter;
import us.wylder.cta.R;
import us.wylder.cta.data.Arrival;


public class ArrivalsActivity extends ListActivity {

    public final String KEY = "c23c0f6e332d4371b6a998158b018e0e";
    private static final String TAG = "ArrivalsActivity";

    private int staId;
    private String name;
    private Handler mHandler = new Handler();
    private ArrayList<Arrival> etas;
    ArrivalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_arrivals);
        staId = getIntent().getIntExtra("staId", 0);
        name = getIntent().getStringExtra("name");
        this.setTitle(name);
        Log.d(TAG, "About to run");
        new Thread(new ArrivalRunnable(staId)).start();
        Log.d(TAG, "Ran");
        etas = new ArrayList<Arrival>();
        adapter = new ArrivalAdapter(getApplicationContext(), etas);
        setListAdapter(adapter);
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

    private class ArrivalRunnable extends APIConnectionRunnable {


        public ArrivalRunnable(int staId) {
            super(staId);
        }

        @Override
        public void postProcess() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    etas.clear();
                    for (Arrival obj : result) {
                        Log.d(TAG, "Adding obj: " + obj.destName);
                        etas.add(obj);
                    }


                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}
//
//    private class APIConnectionRunnable implements Runnable {
//
//        private static final String TAG = "APIConnectionRunnable";
//        private ArrayList<Arrival> result;
//
//
//        @Override
//        public void run() {
//            Log.d(TAG, "Running");
//            URL url;
//            HttpURLConnection con;
//            BufferedReader rd;
//            String line;
//
//
//            try {
//                url = new URL("http://lapi.transitchicago.com/api/1.0/ttarrivals.aspx?key=" + KEY +
//                        "&mapid=" + staId);
//                con = (HttpURLConnection) url.openConnection();
//                con.setRequestMethod("GET");
//                result = parse(con.getInputStream());
//
//            }
//            catch(Exception e)
//            {
//                Log.d(TAG, "Error: " + e);
//            }
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    etas.clear();
//                    for(Arrival obj: result){
//                        Log.d(TAG, "Adding obj: " + obj.destName);
//                        etas.add(obj);
//                    }
//
//
//                 adapter.notifyDataSetChanged();
//                }
//            });
//        }
//        private ArrayList<Arrival> parse(InputStream in) throws XmlPullParserException, IOException {
//            XmlPullParser parser = Xml.newPullParser();
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//            parser.setInput(in, null);
//            parser.nextTag();
//            Log.d(TAG, "About to readFeed()");
//            return readFeed(parser);
//        }
//        private ArrayList<Arrival> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
//            ArrayList<Arrival> entries = new ArrayList<Arrival>();
//            Log.d(TAG, "in readFeed()");
//            parser.require(XmlPullParser.START_TAG, null, "ctatt");
//            while (parser.next() != XmlPullParser.END_TAG) {
//                if (parser.getEventType() != XmlPullParser.START_TAG) {
//                    continue;
//                }
//                String name = parser.getName();
//                // Starts by looking for the entry tag
//                if (name.equals("eta")) {
//                    entries.add(readEta(parser));
//                } else {
//                    skip(parser);
//                }
//            }
//            return entries;
//        }
//        private Arrival readEta(XmlPullParser parser) throws XmlPullParserException, IOException {
//            parser.require(XmlPullParser.START_TAG, null, "eta");
//            String destNm = null;
//            String arrt = null;
//            String prdt = null;
//            while (parser.next() != XmlPullParser.END_TAG) {
//                if (parser.getEventType() != XmlPullParser.START_TAG) {
//                    continue;
//                }
//                String name = parser.getName();
//                if (name.equals("destNm")) {
//                    destNm = readTitle(parser);
//                } else if (name.equals("arrT")) {
//                    arrt = readArrT(parser);
//                } else if (name.equals("prdt")){
//                    prdt = readPrdt(parser);
//                }
//                else {
//                    skip(parser);
//                }
//            }
//            return new Arrival(destNm, arrt,prdt);
//        }
//        private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
//            parser.require(XmlPullParser.START_TAG, null, "destNm");
//            String title = readText(parser);
//            parser.require(XmlPullParser.END_TAG, null, "destNm");
//            return title;
//        }
//
//        private String readArrT(XmlPullParser parser) throws IOException, XmlPullParserException {
//            parser.require(XmlPullParser.START_TAG, null, "arrT");
//            String arrT = readText(parser);
//            parser.require(XmlPullParser.END_TAG, null, "arrT");
//            return arrT;
//        }
//        private String readPrdt(XmlPullParser parser) throws IOException, XmlPullParserException {
//            parser.require(XmlPullParser.START_TAG, null, "prdt");
//            String arrT = readText(parser);
//            parser.require(XmlPullParser.END_TAG, null, "prdt");
//            return arrT;
//        }
//
//        //Read between tags
//        private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
//            String result = "";
//            if (parser.next() == XmlPullParser.TEXT) {
//                result = parser.getText();
//                parser.nextTag();
//            }
//            return result;
//        }
//        //Skip tags you don't care about
//        private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
//            if (parser.getEventType() != XmlPullParser.START_TAG) {
//                throw new IllegalStateException();
//            }
//            int depth = 1;
//            while (depth != 0) {
//                switch (parser.next()) {
//                    case XmlPullParser.END_TAG:
//                        depth--;
//                        break;
//                    case XmlPullParser.START_TAG:
//                        depth++;
//                        break;
//                }
//            }
//        }
//
//
//    }




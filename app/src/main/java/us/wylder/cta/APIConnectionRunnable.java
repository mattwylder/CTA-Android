package us.wylder.cta;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import us.wylder.cta.data.Arrival;

/**
 * Created by mattwylder on 11/23/14.
 */
public abstract class APIConnectionRunnable implements Runnable {

    public final String KEY = "c23c0f6e332d4371b6a998158b018e0e";

    private static final String TAG = "APIConnectionRunnable";
    protected ArrayList<Arrival> result;

    private boolean runOnce;
    private int staId;

    public APIConnectionRunnable(int staId, boolean runOnce){
        this.staId = staId;
        this.runOnce = runOnce;
    }

    public APIConnectionRunnable(int staId) {
        this.staId = staId;
        this.runOnce = true;
    }


    public abstract void postProcess();

    @Override
    public void run() {
        Log.d(TAG, "Running");
        URL url;
        HttpURLConnection con;
        BufferedReader rd;
        String line;

        do {
            try {
                url = new URL("http://lapi.transitchicago.com/api/1.0/ttarrivals.aspx?key=" + KEY +
                        "&mapid=" + staId);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                result = parse(con.getInputStream());

            } catch (Exception e) {
                Log.d(TAG, "Error: " + e);
            }
            postProcess();
            if(!runOnce){
                Log.d(TAG, "Thread Sleeping");
                try{ Thread.sleep(60000);} catch(Exception e){Log.d(TAG,"Thread Sleep Exception");}
            }

        } while (!runOnce);
    }
    private ArrayList<Arrival> parse(InputStream in) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in, null);
        parser.nextTag();
        Log.d(TAG, "About to readFeed()");
        return readFeed(parser);
    }
    private ArrayList<Arrival> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Arrival> entries = new ArrayList<Arrival>();
        Log.d(TAG, "in readFeed()");
        parser.require(XmlPullParser.START_TAG, null, "ctatt");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("eta")) {
                entries.add(readEta(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }
    private Arrival readEta(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "eta");
        String destNm = null;
        String arrt = null;
        String prdt = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("destNm")) {
                destNm = readTitle(parser);
            } else if (name.equals("arrT")) {
                arrt = readArrT(parser);
            } else if (name.equals("prdt")){
                prdt = readPrdt(parser);
            }
            else {
                skip(parser);
            }
        }
        return new Arrival(destNm, arrt,prdt);
    }
    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "destNm");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "destNm");
        return title;
    }

    private String readArrT(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "arrT");
        String arrT = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "arrT");
        return arrT;
    }
    private String readPrdt(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "prdt");
        String arrT = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "prdt");
        return arrT;
    }

    //Read between tags
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
    //Skip tags you don't care about
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


}


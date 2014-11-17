package us.wylder.cta;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import us.wylder.cta.data.StopDB;


public class SecondaryListActivity extends ListActivity {

    private static final String TAG = "Second List Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_secondary_list);
        String line = getIntent().getStringExtra("Line");
        Log.d(TAG, "getStringExtra(\"line\")returned " + line);
        this.setTitle(line);
        ListAdapter adp;
        adp = new LineCursorAdapter(getApplicationContext(),
                StopDB.getInstance(getApplicationContext()).getCursor(line));

        this.setListAdapter(adp);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.secondary_list, menu);
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
}

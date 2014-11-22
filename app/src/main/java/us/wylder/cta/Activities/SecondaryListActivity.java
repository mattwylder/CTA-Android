package us.wylder.cta.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import us.wylder.cta.Adapters.LineCursorAdapter;
import us.wylder.cta.R;
import us.wylder.cta.data.StopDB;


public class SecondaryListActivity extends ListActivity {

    private static final String TAG = "Second List Activity";

    StopDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_secondary_list);
        String line = getIntent().getStringExtra("Line");
        Log.d(TAG, "getStringExtra(\"line\")returned " + line);
        this.setTitle(line);
        db = StopDB.getInstance(getApplicationContext());
        ListAdapter adp;
        adp = new LineCursorAdapter(getApplicationContext(),
                db.getCursor(line));

        this.setListAdapter(adp);
        ListView lv = getListView();
       // lv.setOnLongClickListener();
    }

    @Override
    protected void onListItemClick (ListView l, View v, int position, long id)
    {
        super.onListItemClick(l,v,position,id);
        TextView tv = (TextView) v.findViewById(R.id.line_name);
        TextView stid = (TextView) v.findViewById(R.id.staid);
        //Toast.makeText(getApplicationContext(), stid.getText().toString(),
        //        Toast.LENGTH_SHORT).show();

        Intent i = new Intent(getApplicationContext(), ArrivalsActivity.class);
        i.putExtra("name", tv.getText().toString());
        i.putExtra("staId", Integer.parseInt(stid.getText().toString()));
        startActivity(i);

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

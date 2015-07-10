package us.wylder.cta.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import us.wylder.cta.adapter.LineCursorAdapter;
import us.wylder.cta.R;
import us.wylder.cta.data.StopDB;


public class SecondaryListActivity extends ListActivity {

    private static final String TAG = "Second List Activity";

    StopDB db;
    LineCursorAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_secondary_list);
        String line = getIntent().getStringExtra("Line");
        Log.d(TAG, "getStringExtra(\"line\")returned " + line);
        this.setTitle(line);
        db = StopDB.getInstance(getApplicationContext());

        adp = new LineCursorAdapter(getApplicationContext(),
                db.getCursor(line));

        this.setListAdapter(adp);
        ListView lv = getListView();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                Log.v("long clicked","pos: " + pos);

                PopupMenu pop = new PopupMenu(getApplicationContext(), arg1);
                pop.getMenuInflater().inflate(R.menu.favorite, pop.getMenu());
                TextView staIdView = (TextView) arg1.findViewById(R.id.staid);

                Cursor c = (Cursor) adp.getItem(pos);
                final String staId= c.getString(c.getColumnIndex("_id"));

                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    public boolean onMenuItemClick(MenuItem item){
                        Toast.makeText(getApplicationContext(), "Added Favorite", Toast.LENGTH_SHORT).show();
                        db.addFavorite(staId);

                        return true;

                    }
                });
                pop.show();

                return true;
            }
        });
    }

    @Override
    protected void onListItemClick (ListView l, View v, int position, long id)
    {
        super.onListItemClick(l,v,position,id);
        TextView tv = (TextView) v.findViewById(R.id.line_name);

        Cursor c = (Cursor) adp.getItem(position);
        int staId = c.getInt(c.getColumnIndex("_id"));
        Intent i = new Intent(getApplicationContext(), ArrivalsActivity.class);
        i.putExtra("name", tv.getText().toString());
        i.putExtra("staId", staId);
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

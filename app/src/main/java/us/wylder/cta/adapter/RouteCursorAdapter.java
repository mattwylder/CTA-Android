package us.wylder.cta.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import us.wylder.cta.R;


/**
 * Adapter for the list of stops.
 *
 * Created by matt wylder on 11/15/14.
 */
public class RouteCursorAdapter extends CursorAdapter{
    private static final String TAG = "LocationCursorAdapter";

    public RouteCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_stop, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        int ndx = cursor.getColumnIndex("name");
        int ndx2 = cursor.getColumnIndex("_id");
        Log.d(TAG, "bindView " + ndx + " " + ndx2);

        TextView stName = (TextView) view.findViewById(R.id.stop_name);

        if(stName == null ){
            Log.e(TAG, "Failed to get text view");
            return;
        }
        String name = cursor.getString(ndx);
        String id = cursor.getString(ndx2);
        if(name == null || id == null){
            Log.e(TAG, "failed to get string");
            return;
        }
        stName.setText(name);

    }


}

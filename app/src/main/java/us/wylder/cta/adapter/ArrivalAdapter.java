package us.wylder.cta.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import us.wylder.cta.R;
import us.wylder.cta.data.Arrival;

/**
 * Created by mattwylder on 11/21/14.
 */
public class ArrivalAdapter extends ArrayAdapter<Arrival> {

    private static final String TAG = "ArrivalAdapter";


    public ArrivalAdapter(Context context, ArrayList<Arrival> etas){
        super(context, 0, etas);
    }


    public View getView(int position, View convertView, ViewGroup parent){

        Arrival curEta = getItem(position);
        Log.d(TAG, "Got Item: "+ curEta);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_arrival,
                    parent, false);
        }

        TextView destName = (TextView) convertView.findViewById(R.id.dest_name);
        TextView eta = (TextView) convertView.findViewById(R.id.eta);
        //TextView now = (TextView) convertView.findViewById(R.id.present_time);

        destName.setText(curEta.destName);
        eta.setText(curEta.getTimeDifference());
        //now.setText(curEta.now);

        return convertView;
    }

}

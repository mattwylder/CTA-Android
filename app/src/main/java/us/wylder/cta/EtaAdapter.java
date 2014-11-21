package us.wylder.cta;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import us.wylder.cta.data.EtaObject;

/**
 * Created by mattwylder on 11/21/14.
 */
public class EtaAdapter extends ArrayAdapter<EtaObject> {

    private static final String TAG = "EtaAdapter";


    public EtaAdapter(Context context, ArrayList<EtaObject> etas){
        super(context, 0, etas);
    }


    public View getView(int position, View convertView, ViewGroup parent){

        EtaObject curEta = getItem(position);
        Log.d(TAG, "Got Item: "+ curEta);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.eta_item,
                    parent, false);
        }

        TextView destName = (TextView) convertView.findViewById(R.id.dest_name);
        TextView eta = (TextView) convertView.findViewById(R.id.eta);

        destName.setText(curEta.destName);
        eta.setText(curEta.eta);

        return convertView;
    }

}

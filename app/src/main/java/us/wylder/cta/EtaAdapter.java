package us.wylder.cta;

import android.content.Context;
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



    public EtaAdapter(Context context, ArrayList<EtaObject> etas){
        super(context,R.layout.eta_item, etas);
    }


    public View getView(int position, View convertView, ViewGroup parent){
        View row = super.getView(position, convertView, parent);

        EtaObject curEta = getItem(position);

        TextView destName = (TextView) row.findViewById(R.id.dest_name);
        TextView eta = (TextView) row.findViewById(R.id.eta);

        destName.setText(curEta.destName);
        eta.setText(curEta.eta);

        return convertView;
    }

}

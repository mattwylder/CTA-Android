package us.wylder.cta.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

import us.wylder.cta.activity.ArrivalsActivity;
import us.wylder.cta.R;
import us.wylder.cta.data.StopDB;
import us.wylder.cta.data.TrainStation;


public class NearbyFragment extends ListFragment implements LocationListener {

    private static final String TAG = "ItemFragment?";
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100;
    private static final long MIN_TIME_BTWN_UPDATES = 20 * 1000;

    ArrayList<TrainStation> nearbyStations = new ArrayList<TrainStation>();
    ArrayAdapter<TrainStation> adapter;


    protected LocationManager locationManager = null;



    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static NearbyFragment newInstance() {
        NearbyFragment fragment = new NearbyFragment();
        Bundle args = new Bundle();
//
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NearbyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try{
           locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
           if(locationManager == null){
               Log.e(TAG, "Didn't get a locationmanager");
               return;
           }
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setPowerRequirement(Criteria.POWER_LOW);

            String provider = locationManager.getBestProvider(criteria,true);

            if(provider == null){
                Log.e(TAG, "Didn't get a provider.");
                return;
            }

            locationManager.requestLocationUpdates(provider,MIN_TIME_BTWN_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            Location location = locationManager.getLastKnownLocation(provider);
            if(location != null){
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                Log.d(TAG, "Latitude: " + lat + " Longitude" + lng);
            } else
                Log.e(TAG, "No location");

        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }


        // TODO: Change Adapter to display your content
        adapter = new ArrayAdapter<TrainStation>(getActivity(),
                R.layout.list_item_route, R.id.line_name, nearbyStations);
        setListAdapter(adapter);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.

        }

        TextView tv = (TextView) v.findViewById(R.id.line_name);

        int staId = nearbyStations.get(position).getStaId();
        Intent i = new Intent(getActivity().getApplicationContext(), ArrivalsActivity.class);
        i.putExtra("name", tv.getText().toString());
        i.putExtra("staId", staId);
        startActivity(i);

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Latitude: " + location.getLatitude() +" Longitude" + location.getLongitude());
        StopDB db = StopDB.getInstance(getActivity().getApplicationContext());
        ArrayList<TrainStation> stns = db.findNearby(location, 1000);
        nearbyStations.clear();
        for(TrainStation st : stns){
            nearbyStations.add(st);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    * <p>
    * See the Android Training lesson <a href=
    * "http://developer.android.com/training/basics/fragments/communicating.html"
    * >Communicating with Other Fragments</a> for more information.
    */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}

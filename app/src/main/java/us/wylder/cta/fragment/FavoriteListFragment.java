package us.wylder.cta.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import us.wylder.cta.activity.ArrivalsActivity;
import us.wylder.cta.adapter.RouteCursorAdapter;
import us.wylder.cta.R;
import us.wylder.cta.data.StopsManager;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {}
 * interface.
 */
public class FavoriteListFragment extends ListFragment{


    private static final String TAG = "FavoriteListFragment";

    private OnFragmentInteractionListener mListener;

    private StopsManager db;

    RouteCursorAdapter adp;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavoriteListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = StopsManager.getInstance(getActivity().getApplicationContext());
        adp = new RouteCursorAdapter(getActivity().getApplicationContext(),
                db.getFavoriteCursor());
        setListAdapter(adp);

    }

    public static FavoriteListFragment newInstance() {
        FavoriteListFragment fragment = new FavoriteListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

        Cursor c = (Cursor) adp.getItem(position);
        int staId = c.getInt(c.getColumnIndex("_id"));

        Intent i = new Intent(getActivity().getApplicationContext(), ArrivalsActivity.class);
        i.putExtra("name", tv.getText().toString());
        i.putExtra("staId", staId);
        startActivity(i);
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

    public void onClear()
    {
        StopsManager db = StopsManager.getInstance(getActivity().getApplicationContext());
        adp.changeCursor(db.getFavoriteCursor());
        Log.d(TAG, "Dataset notified");
    }

}

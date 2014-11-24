package us.wylder.cta.Fragments;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


import us.wylder.cta.Adapters.LineCursorAdapter;
import us.wylder.cta.R;
import us.wylder.cta.data.StopDB;
import us.wylder.cta.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {}
 * interface.
 */
public class FavoriteListFragment extends ListFragment {


    private static final String TAG = "FavoriteListFragment";

    private OnFragmentInteractionListener mListener;

    private StopDB db;

    LineCursorAdapter adp;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavoriteListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Log.d(TAG, "SectionNumber: " + sectionNumber);
        db = StopDB.getInstance(getActivity().getApplicationContext());


        adp = new LineCursorAdapter(getActivity().getApplicationContext(),
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
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
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
        StopDB db = StopDB.getInstance(getActivity().getApplicationContext());
        adp.changeCursor(db.getFavoriteCursor());
        Log.d(TAG, "Dataset notified");
    }

//    private class MyDSO extends DataSetObserver{
//        public void onChanged()
//        {
//
//        }
//    }
//
}

package us.wylder.cta.Fragments;

import android.app.Activity;
import android.app.ListActivity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import us.wylder.cta.Adapters.LineCursorAdapter;
import us.wylder.cta.R;
import us.wylder.cta.data.StopDB;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewFavoritesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewFavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewFavoritesFragment extends Fragment{

    private static final String TAG = "NewFavoritesFragment";

    private OnFragmentInteractionListener mListener;
    private ListView listView;
    private TextView emptyFavoritesTextView;



    public static NewFavoritesFragment newInstance() {
        NewFavoritesFragment fragment = new NewFavoritesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NewFavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_new_favorites, container, false);

        emptyFavoritesTextView = (TextView) rootView.findViewById(R.id.noFavoritesTextView);
        listView = (ListView) rootView.findViewById(R.id.favoriteListView);
        StopDB db = StopDB.getInstance(getActivity().getApplicationContext());
        LineCursorAdapter adp = new LineCursorAdapter(getActivity().getApplicationContext(),
                db.getFavoriteCursor());

        if(adp.isEmpty()){
            listView.setVisibility(View.INVISIBLE);
            emptyFavoritesTextView.setVisibility(View.VISIBLE);
        }
        else{
            listView.setAdapter(adp);
        }



        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onClear()
    {

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
        public void onFragmentInteraction(Uri uri);
    }

}

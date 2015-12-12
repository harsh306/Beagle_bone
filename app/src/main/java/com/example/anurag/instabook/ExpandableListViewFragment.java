package com.example.anurag.instabook;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpandableListViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpandableListViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpandableListViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ExpandableListView expListView;
    private ExpandableListAdapter2 listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private HashMap<String, List<String>> data2;
    SQLDBhelper  handler;
    public ExpandableListViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpandableListViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpandableListViewFragment newInstance(String param1, String param2) {
        ExpandableListViewFragment fragment = new ExpandableListViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_expandable_list_view, container, false);
        ExpandableListView expListView = (ExpandableListView) view.findViewById(R.id.lvExp);
        // preparing list data

        prepareListData();
        listAdapter = new ExpandableListAdapter2(this.getContext(), listDataHeader, listDataChild);
        // setting list adapter
        if(expListView==null)
            Log.d("Error ","It is null");
        expListView.setAdapter(listAdapter);
        registerForContextMenu(expListView.getRootView());
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                Toast.makeText(getContext(), new Integer(groupPosition).toString(), Toast.LENGTH_LONG).show();
                return false;
            }
        });
        return view;
    }
    public void refresh(int i){
        getActivity().finish();
//        expListView = (ExpandableListView)view.findViewById(R.id.lvExp);
//        expListView.removeViewAt(i);
//        listAdapter.notifyDataSetChanged();
//        expListView.setAdapter(listAdapter);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        handler=new SQLDBhelper(this.getContext());


        List<Passanger> l =handler.dBtoPassanger();
        Passanger [] p= l.toArray(new Passanger[l.size()]);
        Integer integer = Integer.valueOf(l.size());
        for (int i=0;i<integer;i++){
            listDataHeader.add(p[i].getName());
            List<String> data = new ArrayList<>();
            data.add("Age :"+p[i].getAge());
            data.add("Sex :"+p[i].getSex());
            data.add("UID :"+p[i].getUID());
            data.add("Berth Preference : " + p[i].getBerth());
            data.add(p[i].getuserID());
            listDataChild.put(listDataHeader.get(i), data);


        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    String msg = "Android : ";
    public void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");prepareListData();
        expListView = (ExpandableListView)getView().findViewById(R.id.lvExp);
        // preparing list data
        prepareListData();
        listAdapter = new ExpandableListAdapter2(this.getContext(), listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP;
            }
        });
    }
}

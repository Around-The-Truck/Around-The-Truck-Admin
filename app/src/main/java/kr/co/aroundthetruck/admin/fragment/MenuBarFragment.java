package kr.co.aroundthetruck.admin.fragment;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import kr.co.aroundthetruck.admin.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuBarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MenuBarFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public MenuBarFragment() {
        // Required empty public constructor
    }


    private Button menuSizeButton;
    private int menuBarState = 0; // 0-> 좁은거, 1-> 넓은거

    private LinearLayout smallMenuLayout;
    private LinearLayout largeMenuLayout;
    private LinearLayout containerLayout;

//    private LinearLayout.LayoutParams mLayoutParams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root =inflater.inflate(R.layout.fragment_menu_bar, container, false);

        initialize(root);

        setLayout(inflater);

        return root;
    }

    private void initialize(View view){

        smallMenuLayout = (LinearLayout) view.findViewById(R.id.fragment_menu_bar_small_layout);
        largeMenuLayout = (LinearLayout) view.findViewById(R.id.fragment_menu_bar_large_layout);
        containerLayout = (LinearLayout) view.findViewById(R.id.fragment_menu_bar_container_layout);

        ((LinearLayout)largeMenuLayout.getParent()).removeView(largeMenuLayout);
//        largeMenuLayout.setLayoutParams(new LinearLayout.LayoutParams(85,85));
//        mLayoutParams = ((LinearLayout.LayoutParams) largeMenuLayout.getLayoutParams());
//        mLayoutParams.width = 0;

        menuSizeButton = (Button) view.findViewById(R.id.fragment_menu_bar_small_button);
        menuSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMenuBar();
            }
        });

        Log.v("ystag", "initalize");


    }

    private void changeMenuBar(){
        if (menuBarState == 0){
            Log.v("ystag", "메뉴 커짐");
            containerLayout.addView(largeMenuLayout, 1);
//            mLayoutParams.width = 200;
//            largeMenuLayout.requestLayout();

            menuBarState = 1;
        }
        else if (menuBarState == 1){

            Log.v("ystag", "메뉴 작아짐");
            ((LinearLayout)largeMenuLayout.getParent()).removeView(largeMenuLayout);

            menuBarState = 0;
        }

        LayoutTransition transition = new LayoutTransition();
        containerLayout.setLayoutTransition(transition);
    }

    private void setLayout(LayoutInflater inflater){
//        adapter = new FoodMenuListAdapter(inflater);
//        menuSelectGridView.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        public void onFragmentInteraction(Uri uri);
    }

}

package come.manager.direct.hackuniversity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import come.manager.direct.hackuniversity.model.Event;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    ArrayList<Event> arrayList;
    private WebView webView;
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView rvNumbers;;
    // TODO: Rename and change types of parameters
    private ArrayList<Event> mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(ArrayList<Event> param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        ArrayList<Event> events = new ArrayList<>();
//        Event event = new Event();
//        event.setCity("London");
//        event.setName("Imagin");
//        event.setImage(R.drawable.logo+"");
//        event.setStatus("true");
//        Event event1 = new Event();
//        event1.setCity("Moscow");
//        event1.setName("Leningrad");
//        event1.setImage(R.drawable.logo+"");
//        event1.setStatus("false");
//
//        events.add(event);
//        events.add(event1);

        rvNumbers = (RecyclerView) view.findViewById(R.id.rv_numbers);
        rvNumbers.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNumbers.setAdapter(new RecyclerViewAdapter(mParam1,(ActivityWithWebView)getActivity()));
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.EventViewHolder> {
        ArrayList<Event> listData;
        private final OnItemClickListener listener;

        public RecyclerViewAdapter(ArrayList<Event> data,OnItemClickListener clickListener){
            this.listData = data;
            listener = clickListener;
        }

        @Override
        public RecyclerViewAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            RecyclerViewAdapter.EventViewHolder vh = new RecyclerViewAdapter.EventViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(EventViewHolder personViewHolder, int i) {
            personViewHolder.bind(listData.get(i),listener);
            personViewHolder.personName.setText(listData.get(i).getName());
            personViewHolder.personAge.setText(listData.get(i).getCity());
            personViewHolder.personPhoto.setImageResource(new Integer(listData.get(i).getImage()));

            if(listData.get(i).getStatus().equals("true")) {
                personViewHolder.status.setImageResource(R.drawable.open_letter);
            } else {
                personViewHolder.status.setImageResource(R.drawable.close_letter);
            }
        }
        @Override
        public int getItemCount() {
            return listData.size();
        }

        public class EventViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView personName;
            TextView personAge;
            ImageView personPhoto;
            ImageView status;
            ImageButton send;
            EventViewHolder(View itemView) {
                super(itemView);
                cv = (CardView)itemView.findViewById(R.id.cv);
                personName = (TextView)itemView.findViewById(R.id.person_name);
                personAge = (TextView)itemView.findViewById(R.id.person_age);
                personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
                status = itemView.findViewById(R.id.status_image);
                send = itemView.findViewById(R.id.send_button);
            }

            public void bind(final Event item, final OnItemClickListener listener) {
                personName.setText(item.getName());
                personAge.setText(item.getCity());
try {
    personPhoto.setImageResource(new Integer(item.getImage()));
}catch (Exception e) {
    personPhoto.setImageResource(R.drawable.logo);
}
                if(item.getStatus().equals("true")) {
                    status.setImageResource(R.drawable.open_letter);
                } else {
                    status.setImageResource(R.drawable.close_letter);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        listener.onItemClick(item);
                    }
                });
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Event item);
    }


}

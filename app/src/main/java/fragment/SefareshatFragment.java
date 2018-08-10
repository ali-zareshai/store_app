package fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kavireletronic.ali.kavireleclient.R;

import java.util.List;

import Adapters.SefareshatAdapter;
import Interface.Sefareshat;
import model.SefareshatModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.Factory;
import util.Option;


public class SefareshatFragment extends Fragment {
    private Retrofit retrofit;
    private List<SefareshatModel> sefareshatModelList;
    private RecyclerView recyclerView;
    private SefareshatAdapter sefareshatAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public SefareshatFragment() {
        // Required empty public constructor
    }


    public static SefareshatFragment newInstance() {
        SefareshatFragment fragment = new SefareshatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sefareshat, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.sefareshrecyc);

        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //__________________________________________________
        retrofit= Factory.getRetrofit();
        Sefareshat sefareshat=retrofit.create(Sefareshat.class);
        sefareshat.getAllSefareshat().enqueue(new Callback<List<SefareshatModel>>() {
            @Override
            public void onResponse(Call<List<SefareshatModel>> call, Response<List<SefareshatModel>> response) {
                if (response.isSuccessful()){
                    sefareshatAdapter=new SefareshatAdapter(response.body(),getActivity());
                    recyclerView.setAdapter(sefareshatAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<SefareshatModel>> call, Throwable t) {
                Log.e("error!!! ","error in sefaresh fragment in restofit");
            }
        });

        return view;
    }

}

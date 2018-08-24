package fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.kavireletronic.ali.kavireleclient.R;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

import Adapters.ProdectAdapter;
import Interface.Prodect;
import model.ProdectsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.Factory;

public class ProdectFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ProdectsModel> prodectModelList;
    private ProdectAdapter prodectAdapter;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LayoutAnimationController animationController;
    private int index = 0;
    private ImageButton searc_btn;
    private FloatingActionButton floatingActionButton;
    private Spinner spinnerfilter,shart;
    private EditText value_search;
    private Retrofit retrofit;
    private Button selectdate;

    public ProdectFragment() {
        // Required empty public constructor
    }


    public static ProdectFragment newInstance() {
        ProdectFragment fragment = new ProdectFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_prodect, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.prodectrecyc);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiprefreshprodect);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.flbaddprodect);

        searc_btn = (ImageButton) view.findViewById(R.id.serach_btn_prodect);
        spinnerfilter = (Spinner) view.findViewById(R.id.spinner_filter_prodect);
        value_search = (EditText) view.findViewById(R.id.value_prodect);
        shart=(Spinner)view.findViewById(R.id.spinner_filter_shart);
        selectdate=(Button)view.findViewById(R.id.selectdate_btn);


        ////////////////////////////////////////////
        animationController = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_slide_right);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutAnimation(animationController);

        progressDialog = new ProgressDialog(getActivity());

        floatingActionButton.setOnClickListener(this);
        selectdate.setOnClickListener(this);
        searc_btn.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                index = 0;
                prodectModelList = null;
                prodectModelList = new ArrayList<>();
                prodectAdapter=null;
                floatingActionButton.setVisibility(View.VISIBLE);
                showListProdect(0,"all","","");
            }
        });
        showListProdect(0,"all","","");
        changeSpinner();


        return view;
    }

    private void changeSpinner() {
        spinnerfilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                    case 1:
                    case 2:
                      shart.setVisibility(View.GONE);
                      value_search.setVisibility(View.VISIBLE);
                      selectdate.setVisibility(View.GONE);
                      break;
                    case 3:
                        shart.setVisibility(View.VISIBLE);
                        value_search.setVisibility(View.GONE);
                        selectdate.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        shart.setVisibility(View.VISIBLE);
                        value_search.setVisibility(View.VISIBLE);
                        selectdate.setVisibility(View.GONE);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void showListProdect(final int index, String type, final String query,final String shart) {
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.show();
        retrofit= Factory.getRetrofit();
        final Prodect prodect=retrofit.create(Prodect.class);
        prodect.getprodect(String.valueOf(index),type,query,shart).enqueue(new Callback<List<ProdectsModel>>() {
            @Override
            public void onResponse(Call<List<ProdectsModel>> call, Response<List<ProdectsModel>> response) {
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (response.isSuccessful()){
                    if (!query.equals("")){
                        prodectAdapter=null;
                    }
                    if (prodectModelList==null){
                        prodectModelList=new ArrayList<>();
                    }

                    for (ProdectsModel prodectsModel:response.body()){
                        prodectModelList.add(prodectsModel);
                    }
                    prodectAdapter=new ProdectAdapter(getActivity(),prodectModelList);
                    if (index!=0){
                        prodectAdapter.notifyDataSetChanged();
                    }

//                    Log.e("date",response.toString());
                    recyclerView.setAdapter(prodectAdapter);
                    if (prodectModelList.size()>=11 && query.equals("")){
                        recyclerView.scrollToPosition(prodectModelList.size() - 11);
                    }else {
                        recyclerView.scrollToPosition(0);
                    }
                }else {
                    Log.e("error",response.raw().toString());
                    Log.e("error-----",response.errorBody().toString());
                }
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<ProdectsModel>> call, Throwable t) {
                Log.e("error",call.toString());
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (floatingActionButton.getId()==view.getId()){
            index=index+10;
            showListProdect(index,"all","","");

        }else if (view.getId()==searc_btn.getId()){
            prodectModelList = null;
            prodectModelList = new ArrayList<>();
            prodectAdapter=null;
            String type="",shart_val="";
            floatingActionButton.setVisibility(View.GONE);
            String query=value_search.getText().toString().trim();
            switch (spinnerfilter.getSelectedItemPosition()){
                case 0:
                    type="id";
                    break;
                case 1:
                    type="ref";
                    break;
                case 2:
                    type="name";
                    break;
                case 3:
                    type="date";
                    query=selectdate.getText().toString().trim();
                    break;
                case 4:
                    type="moj";
                    break;
            }

            switch (shart.getSelectedItemPosition()){
                case 0:
                    shart_val=">";
                    break;
                case 1:
                    shart_val="=";
                    break;
                case 2:
                    shart_val="<";
                    break;
            }
            ///value_search
            prodectAdapter=null;
            showListProdect(0,type,query,shart_val);

        }else if (view.getId()==selectdate.getId()){
            PersianCalendar now = new PersianCalendar();
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                    monthOfYear++;
                    selectdate.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth);
                    }
                    }, now.getPersianYear(),
                    now.getPersianMonth(),
                    now.getPersianDay());

            datePickerDialog.setThemeDark(true);
            datePickerDialog.show(getActivity().getFragmentManager(), "ta");
        }





    }
}

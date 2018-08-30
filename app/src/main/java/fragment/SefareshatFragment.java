package fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.kavireletronic.ali.kavireleclient.R;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
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
    private Spinner spinner,spinnersearvh;
    private SefareshatAdapter sefareshatAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LayoutAnimationController animationController;
    private String edittxt,filter_type,az_datetxt,ta_datetxt,searchtxt=null;
    private int count=0;
    private EditText editText;
    private ImageButton search_btn;
    private SharedPreferences SP;
    private ProgressDialog progressDialog;
    private RelativeLayout relativeLayout_date;
    private Button az_date,ta_date;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private List<SefareshatModel> sefareshatModels=new ArrayList<>();
    private boolean refreshsw=false;
    public SefareshatFragment() {
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
        spinner=(Spinner)view.findViewById(R.id.spinner_filter);
        editText=(EditText)view.findViewById(R.id.filter_txt);
        search_btn=(ImageButton)view.findViewById(R.id.search_btn);
        spinnersearvh=(Spinner)view.findViewById(R.id.spinner_search);
        relativeLayout_date=(RelativeLayout)view.findViewById(R.id.rel_filter_date);
        az_date=(Button)view.findViewById(R.id.az_date_btn);
        ta_date=(Button)view.findViewById(R.id.ta_date_btn);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swiprefreshorder);
        toolbar=(Toolbar)view.findViewById(R.id.toolsearch);

        SP = PreferenceManager.getDefaultSharedPreferences(getActivity());
        progressDialog=new ProgressDialog(getActivity());
        setClick();


        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        setSpiner();


        animationController= AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_slide_right);
        recyclerView.setLayoutAnimation(animationController);
        recyclerView.scheduleLayoutAnimation();
        //__________________________________________________
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchQuery("all","");
            }
        });
        searchQuery("all","");
        return view;
    }

    private void setClick() {
        //////////////////
        az_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianCalendar now = new PersianCalendar();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear++;
                        Toast.makeText(getActivity(), "" + year + "/" + monthOfYear + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                        az_date.setText("" + year + "-" + monthOfYear+ "-" + dayOfMonth);
                        }
                        }, now.getPersianYear(),
                        now.getPersianMonth(),
                        now.getPersianDay());

                datePickerDialog.setThemeDark(true);
                datePickerDialog.show(getActivity().getFragmentManager(), "az");
            }
        });
        //////////
        ta_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianCalendar now = new PersianCalendar();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear++;
                        Toast.makeText(getActivity(), "" + year + "/" + monthOfYear + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                        ta_date.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth);
                        }
                        }, now.getPersianYear(),
                        now.getPersianMonth(),
                        now.getPersianDay());

                datePickerDialog.setThemeDark(true);
                datePickerDialog.show(getActivity().getFragmentManager(), "ta");
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                edittxt=editText.getText().toString().trim();
                az_datetxt=az_date.getText().toString();
                ta_datetxt=ta_date.getText().toString();
                switch (filter_type){
                    case "id":
                    case "reference":
                        if (!editText.equals("")){
                            searchtxt=edittxt;
                            searchQuery(filter_type,searchtxt);
                        }else {
                            MDToast mdToast=MDToast.makeText(getActivity(),getString(R.string.incomplete),MDToast.LENGTH_SHORT,MDToast.TYPE_INFO);
                            mdToast.show();
                        }
                        break;
                    case "date":
                        if (az_datetxt.equals(getString(R.string.az_date)) || ta_datetxt.equals(getString(R.string.ta_date))){
                            MDToast mdToast=MDToast.makeText(getActivity(),getString(R.string.incomplete),MDToast.LENGTH_SHORT,MDToast.TYPE_INFO);
                            mdToast.show();
                        }else {
                            searchtxt=az_datetxt+"#"+ta_datetxt;
                            searchQuery(filter_type,searchtxt);
                        }
                        break;
                        default:
                            searchtxt="";

                }

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                count=0;
                switch (i){
                    case 0:
                        filter_type="id";
                        editText.setVisibility(View.VISIBLE);
                        relativeLayout_date.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        filter_type="ref";
                        editText.setVisibility(View.VISIBLE);
                        relativeLayout_date.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        filter_type="date";
                        editText.setVisibility(View.INVISIBLE);
                        relativeLayout_date.setVisibility(View.VISIBLE);
                        break;
                    default:
                        filter_type="";

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void searchQuery(String filter_type, String searchtxt) {
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.show();
        //____________________________________________________________
        Retrofit retrofit=Factory.getRetrofit();
        Sefareshat sefareshat=retrofit.create(Sefareshat.class);
        Log.e("id_user",SP.getString("id_user",""));
        sefareshat.querySearch(filter_type,searchtxt,SP.getString("id_user","")).enqueue(new Callback<List<SefareshatModel>>() {
            @Override
            public void onResponse(Call<List<SefareshatModel>> call, Response<List<SefareshatModel>> response) {
                Log.e("msg",response.message());
                if (response.isSuccessful()){
                    sefareshatAdapter=null;
                    sefareshatAdapter=new SefareshatAdapter(response.body(),getActivity());
                    recyclerView.setAdapter(sefareshatAdapter);
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<SefareshatModel>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error",call.toString());
            }
        });
    }

    private void setSpiner() {
        //// set spinner ////
        List<String> category=new ArrayList<>();
        category.add(getString(R.string.id));
        category.add(getString(R.string.refrence));
        category.add(getString(R.string.customer));
        //
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //////////////////
    }

}

package com.kavireletronic.ali.kavireleclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import Adapters.GroupChatadapter;
import Interface.Chat;
import model.ChatGroupModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.Factory;

public class ChatGroupActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageButton back;
    private static SharedPreferences SP;
    private List<ChatGroupModel> chatGroupList=new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_group);

        SP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.show();

        back=(ImageButton)findViewById(R.id.back_btn_group);
        recyclerView=(RecyclerView)findViewById(R.id.groupchat);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        
        getDataFromServer();
    }

    private void getDataFromServer() {
        Retrofit retrofit= Factory.getRetrofit();
        Chat chat=retrofit.create(Chat.class);

        chat.getGroupsChat().enqueue(new Callback<List<ChatGroupModel>>() {
            @Override
            public void onResponse(Call<List<ChatGroupModel>> call, Response<List<ChatGroupModel>> response) {
                if (response.isSuccessful()){
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    GroupChatadapter groupChatadapter=new GroupChatadapter(response.body(),getApplicationContext());

                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(groupChatadapter);

                }
            }

            @Override
            public void onFailure(Call<List<ChatGroupModel>> call, Throwable t) {
                Log.e("error in recive data","on fail in ChatGroopchat");
            }
        });
    }


    ///////////////
    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}

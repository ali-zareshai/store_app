package com.kavireletronic.ali.kavireleclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapters.MsgChatAdapter;
import Interface.Chat;
import Interface.OnClickSoundChat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;
import model.ChatMsgModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.Factory;
import util.Option;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, OnClickSoundChat {
    private String msg,id_group;
    private SharedPreferences SP;
    private MsgChatAdapter msgChatAdapter;
    private JcPlayerView jcplayerView;
    private List<ChatMsgModel> chatMsgModelList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerViewChat;
    private static final int GALLERY=100;
    private static final int CAMERA =200;




    @BindView(R.id.emojiButton)
    ImageView upload_btn;        
            
            
    ImageButton btn_submit;

//    @BindView(R.id.chat_edit_text1)
    EditText msg_edittxt;

//    @BindView(R.id.chatrecyc)


//    @OnClick(R.id.enter_chat1)
//    void submitMsg(View view){
//        sendToserver();
//    }

    private void sendToserver() {
        btn_submit.setEnabled(false);
        msg=msg_edittxt.getText().toString().trim();
        Retrofit retrofit= Factory.getRetrofit();
        Chat chat=retrofit.create(Chat.class);
        chat.newTextMsg(SP.getString("id_user",""),id_group,msg).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

        msg_edittxt.setText("");
        btn_submit.setEnabled(true);
        showMsgs();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        btn_submit=(ImageButton)findViewById(R.id.enter_chat1);
        msg_edittxt=(EditText)findViewById(R.id.chat_edit_text1) ;
        recyclerViewChat=(RecyclerView)findViewById(R.id.chatrecyc) ;
        jcplayerView = (JcPlayerView) findViewById(R.id.jcplayerView);

        btn_submit.setOnClickListener(this);
        upload_btn.setOnClickListener(this);




        SP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        id_group=getIntent().getExtras().getString("id").toString();

        showMsgs();
    }

    private void showMsgs() {
        Retrofit retrofit=Factory.getRetrofit();
        Chat chat=retrofit.create(Chat.class);
        chat.getMsgChat(SP.getString("id_user",""),id_group).enqueue(new Callback<List<ChatMsgModel>>() {
            @Override
            public void onResponse(Call<List<ChatMsgModel>> call, Response<List<ChatMsgModel>> response) {
                if (response.isSuccessful()){
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerViewChat.setLayoutManager(layoutManager);
                    msgChatAdapter=new MsgChatAdapter(response.body(),getApplicationContext());
                    recyclerViewChat.setAdapter(msgChatAdapter);
                    chatMsgModelList=response.body();
                    msgChatAdapter.setOnClickSoundChat(this);
                    recyclerViewChat.scrollToPosition(response.body().size()-1);
                }
            }

            @Override
            public void onFailure(Call<List<ChatMsgModel>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==btn_submit.getId()){
            sendToserver();
        }else if (view.getId()==upload_btn.getId()){
            showSelectImg();
        }
    }

    private void showSelectImg() {
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setTitle(getString(R.string.select_input));
        builder.setItems(new String[]{getString(R.string.gallery), getString(R.string.camera), getString(R.string.microfon)}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                switch (index){
                    case 0:
                        selectGally();
                        break;
                    case 1:
                        selectCamera();
                        break;
                    case 2:
                        selectMic();
                        break;
                }
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void selectMic() {
        String filePath = Environment.getExternalStorageDirectory() + "/recorded_audio.wav";
        int color = getResources().getColor(R.color.colorPrimaryDark);
        int requestCode = 0;
        AndroidAudioRecorder.with(this)
                // Required
                .setFilePath(filePath)
                .setColor(color)
                .setRequestCode(requestCode)

                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.MONO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(true)
                .setKeepDisplayOn(true)

                // Start recording
                .record();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY && resultCode == RESULT_OK && data != null) {
            // Get the Image from data
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String mediaPath = cursor.getString(columnIndex);
            uploadBitmap(mediaPath);
            // Set the Image in ImageView for Previewing the Media
            cursor.close();
        }else if (requestCode== CAMERA && resultCode==RESULT_OK && data!=null){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
        }else if (requestCode == 0 && resultCode == RESULT_OK && data!=null) {
           /// recorde audio
        }
    }

    private void uploadBitmap(String mediaPath) {
        /// convert bitmap to file
        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Retrofit retrofit=Factory.getRetrofit();
        Chat chat=retrofit.create(Chat.class);
        chat.uploadImg(SP.getString("id_user",""),id_group,fileToUpload,filename).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    MDToast.makeText(getApplicationContext(),getString(R.string.success), MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                }
                Log.e("response::",response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error","error in upload file");

            }
        });
    }

    private void selectCamera() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pictureIntent,
                    CAMERA);
        }
    }

    private void selectGally() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GALLERY);
    }

    @Override
    public void OnclickPlaySoundChat(View view, int positon) {
        ChatMsgModel chatMsgModel=chatMsgModelList.get(positon);
        String url= Option.URL_SOUND+chatMsgModel.getMsg_video();
        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        jcAudios.add(JcAudio.createFromURL(chatMsgModel.getDate(), url));
        jcplayerView.initPlaylist(jcAudios, null);

    }
}

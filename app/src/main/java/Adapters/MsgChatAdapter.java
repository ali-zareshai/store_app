package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kavireletronic.ali.kavireleclient.R;

import java.util.List;

import model.ChatMsgModel;
import util.FormatHelper;
import util.Option;

public class MsgChatAdapter extends RecyclerView.Adapter{
    private List<ChatMsgModel> chatMsgModelList;
    private Context context;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    public MsgChatAdapter(List<ChatMsgModel> chatMsgModelList, Context context) {
        this.chatMsgModelList = chatMsgModelList;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_user2_item, parent, false);
            return new HolderSend(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_user1_item, parent, false);
            return new HolderRecive(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMsgModel msgChat=chatMsgModelList.get(position);

        if (msgChat.getSender().equals("1")){
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }else {
            return VIEW_TYPE_MESSAGE_SENT;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMsgModel chatMsgModel=chatMsgModelList.get(position);

        switch (holder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT:
                ((HolderSend) holder).bind(chatMsgModel);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((HolderRecive) holder).bind(chatMsgModel);
        }
    }


    @Override
    public int getItemCount() {
        return chatMsgModelList.size();
    }

    public class HolderSend extends RecyclerView.ViewHolder{
        TextView textview_message,textview_time;
        ImageView user_reply_status,upload_img;
        public HolderSend(View itemView) {
            super(itemView);
            textview_message=(TextView)itemView.findViewById(R.id.textview_message);
            textview_time=(TextView)itemView.findViewById(R.id.textview_time);
            user_reply_status=(ImageView)itemView.findViewById(R.id.user_reply_status);
            upload_img=(ImageView)itemView.findViewById(R.id.upload_img);

        }

        void bind(ChatMsgModel msgModel){
            /// download img
            if (msgModel.getMsg_img()!=null && !msgModel.getMsg_img().equals("")){
                textview_message.setVisibility(View.GONE);
                upload_img.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(Option.URL_IMG+msgModel.getMsg_img())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(upload_img);
                return;
            }else {
                upload_img.setVisibility(View.GONE);
            }
            if (msgModel.getMsg_txt()!=null){
                textview_message.setText(FormatHelper.toPersianNumber(msgModel.getMsg_txt()));
            }else {
                textview_message.setText("");
            }

            if (msgModel.getDate()!=null){
                textview_time.setText(FormatHelper.toPersianNumber(msgModel.getDate()));
            }else {
                textview_time.setText("");
            }
            if (msgModel.getRead_check()!=null){
                if (msgModel.getRead_check().equals("1")){
                    user_reply_status.setImageResource(R.drawable.message_got_receipt_from_server);
                }else {
                    user_reply_status.setImageResource(R.drawable.message_got_receipt_from_server_onmedia);
                }
            }


        }
    }

    public class HolderRecive extends RecyclerView.ViewHolder{
        TextView textview_message,textview_time;

        public HolderRecive(View itemView) {
            super(itemView);
            textview_message=(TextView)itemView.findViewById(R.id.textview_message);
            textview_time=(TextView)itemView.findViewById(R.id.textview_time);
        }

        void bind(ChatMsgModel msgModel){
            if (msgModel.getMsg_txt()!=null){
                textview_message.setText(FormatHelper.toPersianNumber(msgModel.getMsg_txt()));
            }else {
                textview_message.setText("");
            }

            if (msgModel.getDate()!=null){
                textview_time.setText(FormatHelper.toPersianNumber(msgModel.getDate()));
            }else {
                textview_time.setText("");
            }

        }
    }
}

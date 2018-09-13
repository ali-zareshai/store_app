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

import Interface.OnClickSoundChat;
import model.ChatMsgModel;
import util.FormatHelper;
import util.Option;

public class MsgChatAdapter extends RecyclerView.Adapter{
    private List<ChatMsgModel> chatMsgModelList;
    private Context context;
    private OnClickSoundChat onClickSoundChat;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_MSG_SEND_IMG=3;
    private static final int VIEW_TYPE_MSG_RECEIVED_IMG=4;
    private static final int VIEW_TYPE_MSG_SEND_SOUND=5;

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
        }else if (viewType==VIEW_TYPE_MSG_SEND_IMG){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_user2_item_img, parent, false);
            return new HolderSendImg(view);
        }else if (viewType==VIEW_TYPE_MSG_RECEIVED_IMG){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_user1_item_img, parent, false);
            return new HolderRecivImg(view);
        }else if (viewType==VIEW_TYPE_MSG_SEND_SOUND){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_user2_item_sound, parent, false);
            return new HolderRecivImg(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMsgModel msgChat=chatMsgModelList.get(position);
        Log.e("postion:::: ",String.valueOf(position));

        if (msgChat.getSender().equals("1") && msgChat.getMsg_img().equals("--")){
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }else if (msgChat.getSender().equals("2")&& msgChat.getMsg_img().equals("--")){
            return VIEW_TYPE_MESSAGE_SENT;
        }else if (msgChat.getSender().equals("2")&& !msgChat.getMsg_img().equals("--")){
            return VIEW_TYPE_MSG_SEND_IMG;
        }else if (msgChat.getSender().equals("1")&& !msgChat.getMsg_img().equals("--")){
            return VIEW_TYPE_MSG_RECEIVED_IMG;
        }else if (msgChat.getSender().equals("2")&& !msgChat.getMsg_video().equals("--")){
            return VIEW_TYPE_MSG_RECEIVED_IMG;
        }
        return 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMsgModel chatMsgModel=chatMsgModelList.get(position);
        Log.e("onBindViewHolder:: ",String.valueOf(holder.getItemViewType()));

        switch (holder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT:
                ((HolderSend) holder).bind(chatMsgModel);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((HolderRecive) holder).bind(chatMsgModel);
                break;
            case VIEW_TYPE_MSG_SEND_IMG:
                ((HolderSendImg)holder).bind(chatMsgModel);
                break;
            case VIEW_TYPE_MSG_RECEIVED_IMG:
                ((HolderRecivImg)holder).bind(chatMsgModel);
                break;
            case VIEW_TYPE_MSG_SEND_SOUND:
                ((HolderSendSound)holder).bind(chatMsgModel);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return chatMsgModelList.size();
    }

    public class HolderSend extends RecyclerView.ViewHolder{
        TextView textview_message,textview_time;
        ImageView user_reply_status;
        public HolderSend(View itemView) {
            super(itemView);
            textview_message=(TextView)itemView.findViewById(R.id.textview_message);
            textview_time=(TextView)itemView.findViewById(R.id.textview_time);
            user_reply_status=(ImageView)itemView.findViewById(R.id.user_reply_status);

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
            if (msgModel.getRead_check()!=null){
                if (msgModel.getRead_check().equals("1")){
                    user_reply_status.setImageResource(R.drawable.message_got_receipt_from_server);
                }else {
                    user_reply_status.setImageResource(R.drawable.message_got_receipt_from_server_onmedia);
                }
            }


        }
    }



    public class HolderSendImg extends RecyclerView.ViewHolder{
        TextView textview_time;
        ImageView user_reply_status,upload;
        public HolderSendImg(View itemView) {
            super(itemView);
            Log.e("Holder send Img","start class");
            upload=(ImageView)itemView.findViewById(R.id.upload_img_send) ;
            textview_time=(TextView)itemView.findViewById(R.id.textview_time);
            user_reply_status=(ImageView)itemView.findViewById(R.id.user_reply_status);

        }

        void bind(ChatMsgModel msgModel){
            // download img
            if (msgModel.getMsg_img()!=null){
                Glide.with(context)
                        .load(Option.URL_IMG+msgModel.getMsg_img())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(upload);
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

    public void setOnClickSoundChat(OnClickSoundChat onClickSoundChat) {
        this.onClickSoundChat = onClickSoundChat;
    }

    public class HolderSendSound extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textview_time;
        ImageView user_reply_status,play;

        public HolderSendSound(View itemView) {
            super(itemView);
            Log.e("Holder send Img","start class");
            play=(ImageView)itemView.findViewById(R.id.play_sound) ;
            textview_time=(TextView)itemView.findViewById(R.id.textview_time);
            user_reply_status=(ImageView)itemView.findViewById(R.id.user_reply_status);
            play.setOnClickListener(this);

        }

        void bind(ChatMsgModel msgModel){
            // download img
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



        @Override
        public void onClick(View view) {
            if (onClickSoundChat!=null){
                onClickSoundChat.OnclickPlaySoundChat(view,getAdapterPosition());
            }

        }
    }

    public class HolderRecivImg extends RecyclerView.ViewHolder{
        TextView textview_time;
        ImageView upload;
        public HolderRecivImg(View itemView) {
            super(itemView);
            Log.e("Holder send Img","start class");
            upload=(ImageView)itemView.findViewById(R.id.upload_img_send) ;
            textview_time=(TextView)itemView.findViewById(R.id.textview_time);

        }

        void bind(ChatMsgModel msgModel){
            // download img
            if (msgModel.getMsg_img()!=null){
                Glide.with(context)
                        .load(Option.URL_IMG+msgModel.getMsg_img())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(upload);
            }


            if (msgModel.getDate()!=null){
                textview_time.setText(FormatHelper.toPersianNumber(msgModel.getDate()));
            }else {
                textview_time.setText("");
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

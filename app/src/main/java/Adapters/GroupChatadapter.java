package Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kavireletronic.ali.kavireleclient.ChatActivity;
import com.kavireletronic.ali.kavireleclient.R;

import java.util.List;

import model.ChatGroupModel;
import util.FormatHelper;

public class GroupChatadapter extends RecyclerView.Adapter<GroupChatadapter.Holder> {
    private List<ChatGroupModel> chatGroupList;
    private Context context;

    public GroupChatadapter(List<ChatGroupModel> chatGroupList, Context context) {
        this.chatGroupList = chatGroupList;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.group_chat,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ChatGroupModel chatGroup=chatGroupList.get(position);
        ////////////////
        holder.name_view.setText(FormatHelper.toPersianNumber(chatGroup.getName()));
        holder.id.setText(chatGroup.getId());
        holder.url.setText(chatGroup.getImage());

        Glide.with(context)
                .load(chatGroup.getImage())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return chatGroupList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView name_view,id,url;
        CardView card_view;
        public Holder(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.thumbnail);
            name_view=(TextView)itemView.findViewById(R.id.title);
            id=(TextView)itemView.findViewById(R.id.id_groupchat);
            url=(TextView)itemView.findViewById(R.id.url_groupchat);
            card_view=(CardView)itemView.findViewById(R.id.card_view);

            card_view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId()==card_view.getId()){
                TextView id_t=(TextView)view.findViewById(R.id.id_groupchat);
                TextView title=(TextView)view.findViewById(R.id.title);
                TextView url=(TextView)view.findViewById(R.id.url_groupchat);

                String title_str=title.getText().toString();
                String id=id_t.getText().toString();
                String url_t=url.getText().toString();

                Intent intent=new Intent(context, ChatActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("title",title_str);
                intent.putExtra("url",url_t);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }
}

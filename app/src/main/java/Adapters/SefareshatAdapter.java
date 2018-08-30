package Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kavireletronic.ali.kavireleclient.R;

import java.util.List;

import model.SefareshatModel;
import util.FormatHelper;

public class SefareshatAdapter extends RecyclerView.Adapter<SefareshatAdapter.Holder> {
    private List<SefareshatModel> sefareshatModelList;
    private Context context;
    private static SharedPreferences SP;

    public SefareshatAdapter(List<SefareshatModel> sefareshatModelList, Context context) {
        this.sefareshatModelList = sefareshatModelList;
        this.context = context;
        SP = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sefareshat,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        SefareshatModel sefareshatModel=sefareshatModelList.get(position);

        holder.idtxt.setText(FormatHelper.toPersianNumber(sefareshatModel.getId()));
        holder.reftxt.setText(FormatHelper.toPersianNumber(sefareshatModel.getRefrence()));
        holder.statustxt.setText(FormatHelper.toPersianNumber(sefareshatModel.getStatus()));
        holder.met_pritxt.setText(FormatHelper.toPersianNumber(sefareshatModel.getMethod_price()));
        holder.pricetxt.setText(FormatHelper.toPersianNumber(sefareshatModel.getPrice()));
        holder.datetxt.setText(FormatHelper.toPersianNumber(sefareshatModel.getDate_sefaresh()));
        holder.right_anno.setBackgroundColor(Color.parseColor(sefareshatModel.getColor()));
    }

    @Override
    public int getItemCount() {
        return sefareshatModelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView idtxt,reftxt,pricetxt,met_pritxt,statustxt,datetxt;
        RelativeLayout right_anno;
        public Holder(View itemView) {
            super(itemView);

            idtxt=(TextView)itemView.findViewById(R.id.idtxt);
            reftxt=(TextView)itemView.findViewById(R.id.refrencetxt);
            pricetxt=(TextView)itemView.findViewById(R.id.pricetxt);
            met_pritxt=(TextView)itemView.findViewById(R.id.paytxt);
            statustxt=(TextView)itemView.findViewById(R.id.statustxt);
            datetxt=(TextView)itemView.findViewById(R.id.adddatetxt);
            right_anno=(RelativeLayout)itemView.findViewById(R.id.right_anno);

        }
    }
}

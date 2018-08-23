package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kavireletronic.ali.kavireleclient.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.ProdectsModel;
import util.CircleTransform;
import util.FormatHelper;
import util.Option;

public class ProdectAdapter extends RecyclerView.Adapter<ProdectAdapter.Holder>{
    private Context context;
    private List<ProdectsModel> prodectModelList;
    private String link;

    public ProdectAdapter(Context context, List<ProdectsModel> prodectModelList) {
        this.context = context;
        this.prodectModelList = prodectModelList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prodect,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ProdectsModel prodectModel=prodectModelList.get(position);

        holder.id.setText(FormatHelper.toPersianNumber(prodectModel.getId()));
        holder.name.setText(FormatHelper.toPersianNumber(prodectModel.getName()));
        holder.ref.setText(FormatHelper.toPersianNumber(prodectModel.getRef()));
        holder.price.setText(FormatHelper.toPersianNumber(prodectModel.getPrice()));
        holder.mojodi.setText(FormatHelper.toPersianNumber(prodectModel.getMojodi()));
//        holder.kharid.setText(FormatHelper.toPersianNumber(prodectModel.getKharid()));
        ////\
        holder.postion.setText(String.valueOf(position));
        //____________________________________________________________
        if (prodectModel.getAvailable().equals("1")){
            holder.available.setVisibility(View.VISIBLE);
        }
        if (prodectModel.getCombine().equals("1")){
            holder.combine.setVisibility(View.VISIBLE);
        }
        if (prodectModel.getShow_price().equals("1")){
            holder.showprice.setVisibility(View.VISIBLE);
        }
        //__________________________________________________________________
        String[] add=prodectModel.getImage().split("");
        if (prodectModel.getImage().length()==4){
            link= Option.LINK_IMAGE_PRODECT+add[1]+"/"+add[2]+"/"+add[3]+"/"+add[4]+"/"+prodectModel.getImage()+"-small_default.jpg";
        }else {
            link= Option.LINK_IMAGE_PRODECT+add[1]+"/"+add[2]+"/"+add[3]+"/"+prodectModel.getImage()+"-small_default.jpg";
        }
//        Log.e("link Image",link);
        Glide.with(context)
                .load(link)
                .override(90, 90)
                .centerCrop()
                .crossFade()
                .transform(new CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.prodect);

    }

    @Override
    public int getItemCount() {
        return prodectModelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView id,name,ref,price,mojodi,postion;
        ImageButton more;
        ImageView prodect,available,combine,showprice;
        public Holder(View itemView) {
            super(itemView);
            ///////////////////////////
            id=(TextView)itemView.findViewById(R.id.id_prodect);
            name=(TextView)itemView.findViewById(R.id.name_prodect);
            ref=(TextView)itemView.findViewById(R.id.ref_prodect);
            price=(TextView)itemView.findViewById(R.id.price_prodect);
            mojodi=(TextView)itemView.findViewById(R.id.mojodi_prodect);
            more=(ImageButton) itemView.findViewById(R.id.more_btn_prod);
//            kharid=(TextView)itemView.findViewById(R.id.kharid_prodect);
            postion=(TextView)itemView.findViewById(R.id.postion);
            ////////////////////////////////
            prodect=(ImageView)itemView.findViewById(R.id.image_prodect);
            available=(ImageView)itemView.findViewById(R.id.available_prodect);
            combine=(ImageView)itemView.findViewById(R.id.combine_prodect);
            showprice=(ImageView)itemView.findViewById(R.id.showprice_prodect);
            //////
            more.setTag(R.integer.btn_more,itemView);

            more.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId()==more.getId()){
                View view1=(View)more.getTag(R.integer.btn_more);
                TextView textView=(TextView)view1.findViewById(R.id.postion);

            }
        }
    }


}

package com.whty.xqt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.whty.xqt.R;
import com.whty.xqt.bean.TradeRecordBean;
import com.whty.xqt.utils.CommUtil;
import com.whty.xqt.utils.LogUtils;

import java.util.List;


/**
 * Created by jiangzhe on 2018/4/25.
 */

public class TradeRecordAdapter extends RecyclerView.Adapter<TradeRecordAdapter.MyViewHolder> {
    private Context mContext;
    private List<TradeRecordBean> mDatas;

    public TradeRecordAdapter(Context context, List<TradeRecordBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_trade, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemTradeIcon;
        private TextView itemTradeTypeTv;
        private TextView itemTradeTimeTv;
        private TextView itemTradeMoenyTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemTradeIcon = itemView.findViewById(R.id.item_trade_icon);
            itemTradeTypeTv = itemView.findViewById(R.id.item_trade_type_tv);
            itemTradeTimeTv = itemView.findViewById(R.id.item_trade_time_tv);
            itemTradeMoenyTv = itemView.findViewById(R.id.item_trade_moeny_tv);
        }

        protected void bind(int position) {
            TradeRecordBean tradeRecordBean = mDatas.get(position);
            itemTradeTimeTv.setText(tradeRecordBean.tradeTime);
            switch (tradeRecordBean.tradeType) {
                case "02"://充值
                    itemTradeIcon.setImageResource(R.mipmap.charge_record);
                    itemTradeTypeTv.setText(CommUtil.getString(R.string.recharge));
                    itemTradeMoenyTv.setTextColor(CommUtil.getColor(R.color.green_009944));
                    itemTradeMoenyTv.setText("+" + tradeRecordBean.tradeMoney + "元");
                    break;
                case "03":
                case "04":
                case "05":
                case "06":
                case "08":
                case "09"://消费
                    itemTradeIcon.setImageResource(R.mipmap.consume_record);
                    itemTradeTypeTv.setText(CommUtil.getString(R.string.consume));
                    itemTradeMoenyTv.setTextColor(CommUtil.getColor(R.color.red_ff3826));
                    itemTradeMoenyTv.setText("-" + tradeRecordBean.tradeMoney + "元");
                    break;
            }
        }
    }

}

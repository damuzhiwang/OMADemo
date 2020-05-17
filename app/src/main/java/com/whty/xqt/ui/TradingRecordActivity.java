package com.whty.xqt.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.whty.xqt.MyApplication;
import com.whty.xqt.R;
import com.whty.xqt.adapter.TradeRecordAdapter;
import com.whty.xqt.base.BaseActivity;
import com.whty.xqt.bean.TradeRecordBean;
import com.whty.xqt.cardoperate.SwpSimCardManager;
import com.whty.xqt.interf.view.ICardView;
import com.whty.xqt.presenter.CardOperatePresenter;
import com.whty.xqt.utils.CommUtil;
import com.whty.xqt.utils.RecyclerViewDivider;
import com.whty.xqt.views.TypeSelector;

import java.util.ArrayList;
import java.util.List;

public class TradingRecordActivity extends BaseActivity implements ICardView {

    private RelativeLayout back;
    private TypeSelector tradeTs;
    private RecyclerView tradeRv;
    private SmartRefreshLayout refreshLayout;

    private List<TradeRecordBean> lists = new ArrayList<>();
    private List<TradeRecordBean> chargeLists;
    private List<TradeRecordBean> consumeLists;
    private TradeRecordAdapter tradeRecordAdapter;
    private TypeSelector.OnStateListener mStateListener = new TypeSelector.OnStateListener() {
        @Override
        public void onStateChange(TypeSelector.CheckState checkState) {
            if (lists.size() != 0) {
                lists.clear();
            }
            switch (checkState) {
                case CHARGE:
                    lists.addAll(chargeLists);
                    break;
                case CONSUME:
                    lists.addAll(consumeLists);
                    break;
            }
            tradeRecordAdapter.notifyDataSetChanged();
        }
    };

    private SwpSimCardManager simCardManager;
    private CardOperatePresenter cardOperatePresenter;

    @Override
    public void initData() {
        chargeLists = new ArrayList<>();
        consumeLists = new ArrayList<>();
        createDatas();
        simCardManager = ((MyApplication) getApplication()).getSwpSimCardManager();
        cardOperatePresenter = new CardOperatePresenter(simCardManager, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        tradeRv.setLayoutManager(layoutManager);
        tradeRv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL, CommUtil.dp2px(1), CommUtil.getColor(R.color.line)));
        tradeRecordAdapter = new TradeRecordAdapter(this, lists);
        tradeRv.setAdapter(tradeRecordAdapter);
        tradeTs.setOnStateListener(mStateListener);

        getCardInfo();

    }

    private void getCardInfo() {
        showProgressDialog(CommUtil.getString(R.string.loading_card_info));
        cardOperatePresenter.readCardInfo();
    }

    private void createDatas() {
        TradeRecordBean tradeRecordBean = new TradeRecordBean();
        tradeRecordBean.tradeType = "02";
        tradeRecordBean.tradeTime = "20200120 09:35";
        tradeRecordBean.tradeMoney = "100";
        TradeRecordBean tradeRecordBean1 = new TradeRecordBean();
        tradeRecordBean1.tradeType = "02";
        tradeRecordBean1.tradeTime = "20200120 09:35";
        tradeRecordBean1.tradeMoney = "100";
        TradeRecordBean tradeRecordBean2 = new TradeRecordBean();
        tradeRecordBean2.tradeType = "02";
        tradeRecordBean2.tradeTime = "20200120 09:35";
        tradeRecordBean2.tradeMoney = "100";
        TradeRecordBean tradeRecordBean3 = new TradeRecordBean();
        tradeRecordBean3.tradeType = "02";
        tradeRecordBean3.tradeTime = "20200120 09:35";
        tradeRecordBean3.tradeMoney = "100";
        chargeLists.add(tradeRecordBean);
        chargeLists.add(tradeRecordBean1);
        chargeLists.add(tradeRecordBean2);
        chargeLists.add(tradeRecordBean3);
        TradeRecordBean tradeRecordBean4 = new TradeRecordBean();
        tradeRecordBean4.tradeType = "09";
        tradeRecordBean4.tradeTime = "20200119 08:35";
        tradeRecordBean4.tradeMoney = "2";
        TradeRecordBean tradeRecordBean5 = new TradeRecordBean();
        tradeRecordBean5.tradeType = "09";
        tradeRecordBean5.tradeTime = "20200119 15:20";
        tradeRecordBean5.tradeMoney = "2";
        TradeRecordBean tradeRecordBean6 = new TradeRecordBean();
        tradeRecordBean6.tradeType = "09";
        tradeRecordBean6.tradeTime = "20200119 08:35";
        tradeRecordBean6.tradeMoney = "2";
        TradeRecordBean tradeRecordBean7 = new TradeRecordBean();
        tradeRecordBean7.tradeType = "09";
        tradeRecordBean7.tradeTime = "20200119 15:20";
        tradeRecordBean7.tradeMoney = "2";
        consumeLists.add(tradeRecordBean4);
        consumeLists.add(tradeRecordBean5);
        consumeLists.add(tradeRecordBean6);
        consumeLists.add(tradeRecordBean7);
        lists.addAll(chargeLists);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_trading_record;
    }

    @Override
    protected void initView() {
        back = (RelativeLayout) findViewById(R.id.back);
        tradeTs = (TypeSelector) findViewById(R.id.trade_ts);
        tradeRv = (RecyclerView) findViewById(R.id.trade_rv);
        refreshLayout = (SmartRefreshLayout)findViewById(R.id.srl_refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void showCardInfo(List<TradeRecordBean> rechargeHistory) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissProgressDialog();
            }
        });
        if(rechargeHistory==null){
            return;
        }
        for (int i = 0; i < rechargeHistory.size(); i++) {
            TradeRecordBean tradeRecordBean = rechargeHistory.get(i);
            if ("09".equals(tradeRecordBean.tradeType) || "03".equals(tradeRecordBean.tradeType)
                    || "04".equals(tradeRecordBean.tradeType) || "05".equals(tradeRecordBean.tradeType)
                    || "06".equals(tradeRecordBean.tradeType) || "08".equals(tradeRecordBean.tradeType)) {//消费
                consumeLists.add(tradeRecordBean);
            } else if ("02".equals(tradeRecordBean.tradeType)) {
                chargeLists.add(tradeRecordBean);
            }
        }
        lists.addAll(chargeLists);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tradeRecordAdapter.notifyDataSetChanged();
            }
        });
    }
}

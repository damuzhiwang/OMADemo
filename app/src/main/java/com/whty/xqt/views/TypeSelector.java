package com.whty.xqt.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whty.xqt.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/4/21.
 */
public class TypeSelector extends RelativeLayout {

    @BindView(R.id.leftTv)
    TextView leftTv;
    @BindView(R.id.rightTv)
    TextView rightTv;
    @BindView(R.id.left_line)
    View leftLine;
    @BindView(R.id.right_line)
    View rightLine;
    private OnStateListener mOnStateListener;
    private CheckState mCheckState;

    public interface OnStateListener {
        void onStateChange(CheckState checkState);
    }

    public void setOnStateListener(OnStateListener onStateListener) {
        mOnStateListener = onStateListener;
    }

    public TypeSelector(Context context) {
        this(context, null);
    }

    public TypeSelector(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TypeSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_type, this);
        ButterKnife.bind(this, rootView);
        selectedType(CheckState.CHARGE);
    }

    private void selectedType(CheckState checkState) {
        if (checkState == CheckState.CHARGE) {
            leftLine.setVisibility(View.VISIBLE);
            rightLine.setVisibility(View.INVISIBLE);
        } else if (checkState == checkState.CONSUME) {
            leftLine.setVisibility(View.INVISIBLE);
            rightLine.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.leftTv, R.id.rightTv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftTv:
                if (leftLine.getVisibility() == VISIBLE) {
                    break;
                }
                selectedType(CheckState.CHARGE);
                if (mOnStateListener != null) {
                    mOnStateListener.onStateChange(CheckState.CHARGE);
                }
                break;
            case R.id.rightTv:
                if (rightLine.getVisibility() == VISIBLE) {
                    break;
                }
                selectedType(CheckState.CONSUME);
                if (mOnStateListener != null) {
                    mOnStateListener.onStateChange(CheckState.CONSUME);
                }
                break;
        }
    }

    public CheckState getCheckState() {
        if (mCheckState == null) {
            mCheckState = CheckState.CHARGE;
        }
        return mCheckState;
    }

    public void setCheckState(CheckState checkState) {
        this.mCheckState = checkState;
        if (checkState == CheckState.CONSUME) {
            selectedType(CheckState.CONSUME);
        } else {
            selectedType(CheckState.CHARGE);
        }
    }

    public void setText(String leftValue, String rightValue) {
        if (null != leftValue) {
            leftTv.setText(leftValue);
        }
        if (null != rightValue) {
            rightTv.setText(rightValue);
        }
    }

    public enum CheckState {
        CHARGE, CONSUME
    }
}

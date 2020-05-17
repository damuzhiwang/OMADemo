package com.whty.xqt.base;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
//        ButterKnife.bind(this, rootView);
        initData();
        return rootView;
    }


    public abstract void initData();

    public abstract int getLayoutId();


    public void gotoAtivity(Class clazz, Bundle bundle) {
        Intent it = new Intent(getActivity(), clazz);
        if (bundle != null) {
            it.putExtra("bundle", bundle);
        }
        getActivity().startActivity(it);
    }

}

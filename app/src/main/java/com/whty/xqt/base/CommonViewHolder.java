package com.whty.xqt.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

public class CommonViewHolder {
	public View convertView;

	public CommonViewHolder(View convertView) {
		this.convertView = convertView;
		convertView.setTag(this);
	}

	SparseArray<View> views = new SparseArray();

	// 类型推导
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id) {
		if (views.get(id) == null) {
			views.put(id, convertView.findViewById(id));
		}

		return (T) views.get(id);
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id, Class<T> clazz) {

		return (T) getView(id);
	}

	public static CommonViewHolder getCommonViewHolder(View convertView,
                                                       Context context, int layoutRes) {
		if (convertView == null) {
			convertView = View.inflate(context, layoutRes, null);
			return new CommonViewHolder(convertView);
		} else {
			return (CommonViewHolder) convertView.getTag();
		}

	}

}

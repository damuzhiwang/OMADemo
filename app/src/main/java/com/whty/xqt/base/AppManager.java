package com.whty.xqt.base;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;


/**
 * Created by Administrator on 2017/3/23.
 */
public class AppManager {

    public static AppManager appManager = null;

    private AppManager() {

    }

    public static AppManager getInstance() {
        if (appManager == null) {
            appManager = new AppManager();
        }
        return appManager;
    }

    private Stack<Activity> activityStack = new Stack<>();

    /**
     * 添加
     *
     * @param activity
     */
    public void addActivty(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 关闭指定activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity1 = activityStack.get(i);
            if (activity1.getClass().equals(activity.getClass())) {
                activity.finish();
                activityStack.remove(activity1);
                break;
            }
        }
    }

    /**
     * 关闭当前activity
     */
    public void removeCurrent() {
        Activity activity = activityStack.lastElement();
        activity.finish();
        activityStack.remove(activity);
    }

    public void clear() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity1 = activityStack.get(i);
            activity1.finish();
        }
        activityStack.clear();
    }

    public int getSize() {
        return activityStack.size();
    }

    public void goHome() {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()){
            Activity activity = iterator.next();
//            if (activity instanceof HomeActivity) {
//            } else {
//                activity.finish();
//                iterator.remove();
//            }
        }
    }
}

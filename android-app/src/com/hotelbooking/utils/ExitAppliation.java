package com.hotelbooking.utils;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class ExitAppliation extends Application
{
    private List<Activity> activityList = new LinkedList<Activity>();
    private static ExitAppliation instance;
    private ExitAppliation()
    {
    }
    // ����ģʽ�л�ȡΨһ��MyApplicationʵ��
    public static ExitAppliation getInstance()
    {
        if (null == instance)
        {
            instance = new ExitAppliation();
        }
        return instance;
    }
    //���Activity��������
    public void addActivity(Activity activity)
    {
        activityList.add(activity);
    }
    // ��������Activity��finish
    public void exit()
    {
        for (Activity activity : activityList)
        {
            activity.finish();
        }
        System.exit(0);
    }
}

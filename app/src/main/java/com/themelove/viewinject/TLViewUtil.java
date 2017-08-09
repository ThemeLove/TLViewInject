package com.themelove.viewinject;

import android.app.Activity;
import android.view.View;

import com.themelove.viewinject.annotation.OnClick;
import com.themelove.viewinject.annotation.ViewInject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * IOC/DI依赖注入框架，原理是注解加反射实现。
 * Created by qingshanliao on 2017/8/9.
 */
public class TLViewUtil {
    /**
     * 注入Activity
     * @param activity
     */
    public static void inject(Activity activity){
        bindView(activity);
        bindOnClick(activity);
    }

    /**
     * 绑定View
     * @param activity
     */
    private static void bindView(Activity activity){
//      1.获取当前Activity的字节码对象
        Class clazz = activity.getClass();
//      2.获取注入activity字节码对象中所有成员变量
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field: declaredFields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject!=null){
                int resId = viewInject.value();
                View view = activity.findViewById(resId);
                if (!field.isAccessible()){//暴力反射
                    field.setAccessible(true);
                }
                try {
                    field.set(activity,view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 绑定OnClick事件
     * @param activity
     */
    private static void bindOnClick(final Activity activity){
//        1.获取当前Activity的字节码对象
        Class clazz = activity.getClass();
//        2.获取注入activity字节码对象中所有成员方法
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (final Method method:declaredMethods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick!=null){
                int resId = onClick.value();
                final View view = activity.findViewById(resId);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!method.isAccessible()){//暴力反射
                            method.setAccessible(true);
                        }
                        try {
                            method.invoke(activity,view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}

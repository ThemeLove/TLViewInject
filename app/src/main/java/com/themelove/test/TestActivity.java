package com.themelove.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.themelove.viewinject.R;
import com.themelove.viewinject.TLViewUtil;
import com.themelove.viewinject.annotation.OnClick;
import com.themelove.viewinject.annotation.ViewInject;

/**
 * Created by qingshanliao on 2017/8/9.
 */
public class TestActivity extends AppCompatActivity{
    @ViewInject(R.id.textView1)
    private TextView mTv1;
    @ViewInject(R.id.textView2)
    private TextView mTv2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TLViewUtil.inject(this);
        Log.i("textView1",mTv1.getText().toString().trim());
        Log.i("textView2",mTv2.getText().toString().trim());
    }

    @OnClick(R.id.btn)
    private void onClick(View view){
        Log.i("onClick","我被点击了");
        Log.i("textView1",mTv1.getText().toString().trim());
        Log.i("textView2",mTv2.getText().toString().trim());
        Toast.makeText(TestActivity.this,"我被点击了",Toast.LENGTH_SHORT).show();
    }
}

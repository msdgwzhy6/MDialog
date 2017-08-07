package com.mdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mdialog.mdialog.MDialog;

public class MainActivity extends AppCompatActivity {

    MDialog mDialog;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        txt = (TextView) findViewById(R.id.txt);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog = new MDialog(MainActivity.this);
                mDialog.setTitlecharacter("测试");
                mDialog.setLeftcharacter("取消哦");
                mDialog.setRightcharacter("确定哦");
                mDialog.show();

                mDialog.setonLeftChilk(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                        // TODO: 2017/8/7
                        Toast.makeText(MainActivity.this, "左边按钮的其他操作", Toast.LENGTH_SHORT).show();
                    }
                });

                mDialog.setonRightChilk(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                        // TODO: 2017/8/7
                        Toast.makeText(MainActivity.this, "右边按钮的其他操作", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

}

package com.sunday.easy.easyview.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sunday.easy.easyview.EasyPopup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.bt_popup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List list = new ArrayList();
                list.add("选项一");
                list.add("选项二");
                EasyPopup easyPopup = new EasyPopup(getBaseContext(),"测试标题","详细描述");
                easyPopup.setListData(new EasyPopup.SimpleTextAdapter(getBaseContext(),list),null);
                easyPopup.show();
            }
        });
    }
}

package com.example.dell.rxjavademo.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.adaper.ListViewAdapter;
import com.example.dell.rxjavademo.weight.MyToogleView;

import java.util.ArrayList;
import java.util.List;





public class CustomViewTwoActivity extends AppCompatActivity {

    private EditText edittext;
    private ImageButton image_drop_button;
    private ListView listView;
    private List<String> list;
    private  PopupWindow popupWindow;
    private   ListViewAdapter  listAdapter;
    private MyToogleView toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_two);
        initView();
        IntentFilter intent = new IntentFilter("HIDE");
        registerReceiver(broadcastReceiver, intent);
        initClick();

    }

    private void initClick() {
        image_drop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomViewTwoActivity.this, "点击", Toast.LENGTH_SHORT).show();
                initListView();
                showPopopupWindow();
            }
        });

    }

    private void initListView() {
        listView = new ListView(this);
        initData();
        listAdapter = new ListViewAdapter(list,this,popupWindow);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CustomViewTwoActivity.this, "点击", Toast.LENGTH_SHORT).show();
                edittext.setText(list.get(position));
                popupWindow.dismiss();
            }
        });

    }

    private PopupWindow showPopopupWindow() {

        if (popupWindow == null) {
            popupWindow = new PopupWindow(listView, edittext.getWidth(), RelativeLayout.LayoutParams.WRAP_CONTENT);
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(edittext, 0, 0);

        return  popupWindow;


    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            list.add(i+"小雨");
        }

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            popupWindow.dismiss();
             if (popupWindow != null) {
                 popupWindow = null;
             } else  {
                 popupWindow = null;
             }
        }
    };


    private void initView() {
        edittext = ((EditText) findViewById(R.id.edittext_view));
        image_drop_button = ((ImageButton) findViewById(R.id.image_button));


        // toggle还差将以下 设置为自定义属性，使用更方便
        toggle = ((MyToogleView) findViewById(R.id.toggle_view));
        toggle.setBackgroundImageView(R.mipmap.switch_background);
        toggle.setBackgroundScroll(R.mipmap.slide_button);
        toggle.setCurrentState(true);
        toggle.setToggleSwitchStateListener(new MyToogleView.ToggleSwitchStateListener() {
            @Override
            public void onStateChange(boolean state) {
                Toast.makeText(CustomViewTwoActivity.this,"state:"+state,Toast.LENGTH_SHORT).show();
            }
        });
    }



}

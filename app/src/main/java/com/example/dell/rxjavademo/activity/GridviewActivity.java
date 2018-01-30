package com.example.dell.rxjavademo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.dell.rxjavademo.R;
import com.example.dell.rxjavademo.adaper.RedBallAdapter;
import com.example.dell.rxjavademo.bean.DoubleBall;
import com.example.dell.rxjavademo.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;

public class GridviewActivity extends AppCompatActivity implements RedBallAdapter.OnRedBallChangListener {

    private GridView gridview;
    private RedBallAdapter redBarAdapter;
    private List<DoubleBall> list;
    private  List<String> list_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        initView();
        initData();
    }

    private void initData() {
        list_select = new ArrayList<>();
    }

    private void initView() {
        gridview = ((GridView) findViewById(R.id.red_gridview));
        list = new ArrayList<>();
        list.add(new DoubleBall("011","017",1,false));
        list.add(new DoubleBall("012","016",2,false));
        list.add(new DoubleBall("013","015",3,false));
        list.add(new DoubleBall("014","014",4,false));
        list.add(new DoubleBall("015","013",5,false));
        list.add(new DoubleBall("016","012",6,false));
        list.add(new DoubleBall("017","011",7,false));


        redBarAdapter = new RedBallAdapter(this,list,R.layout.item_redball_gridview);
        gridview.setAdapter(redBarAdapter);
        redBarAdapter.setOnRedBallListener(this);
    }

    @Override
    public void setOnRedBallListener() {
        List<DoubleBall> list_result = redBarAdapter.getDatas();
        list_select.clear();
        for (int i = 0; i <list_result.size(); i++) {
            if (list_result.get(i).isCheck() == true) {
               list_select.add(list_result.get(i).getRedBall());
            }
        }
        BaseUtils.toast(list_select.toString().trim());

    }
}

package com.example.dell.rxjavademo.adaper;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.dell.rxjavademo.R;

import java.util.List;

/**
 * Created by DELL on 2017/6/21.
 */
public class ListViewAdapter extends BaseAdapter {

   private List<String> list;
   private Context context;


    public ListViewAdapter(List<String> list, Context context,PopupWindow popupWindow) {
        this.list = list;
        this.context = context;


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null){
            view = View.inflate(context, R.layout.item_listview_popup,null);
        }else {
            view = convertView;
        }

        TextView text_content = (TextView) view.findViewById(R.id.text_item_content);
        ImageButton iamge_delete = (ImageButton) view.findViewById(R.id.imageButton_delete);
        iamge_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
                if (list.size() == 0){
                    // 此时可以发送一个广播，去隐藏主界面的popupWindow
                    Intent intent = new Intent("HIDE");
                    context.sendBroadcast(intent);
                }
            }
        });

        text_content.setText(list.get(position));
        return view;
    }


}

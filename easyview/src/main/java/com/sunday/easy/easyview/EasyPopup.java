package com.sunday.easy.easyview;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;


public class EasyPopup {
    private String title;
    private String description;
    private View parent;
    private PopupWindow popupWindow;
    public EasyPopup(Context context,
                     String title,
                     String description
                     ){
        this.title = title;
        this.description = description;
        parent = LayoutInflater.from(context).inflate(R.layout.layout_easy_popup, null, false);
        parent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        setupViews(parent);
        popupWindow = new PopupWindow(parent,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
    }


    public void setListData(BaseAdapter baseAdapter, AdapterView.OnItemClickListener onItemClickListener){
        ListView listView = parent.findViewById(R.id.list_data);
        listView.setAdapter(baseAdapter);
        listView.setOnItemClickListener(onItemClickListener);
    }



    private void setupViews(View parent){
        TextView closeView = parent.findViewById(R.id.tv_back);
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        TextView titleView = parent.findViewById(R.id.tv_title);
        titleView.setText(title);

        TextView descriptionView = parent.findViewById(R.id.tv_description);
        descriptionView.setText(description);

    }



    public void show(){
        popupWindow.showAtLocation(parent, Gravity.BOTTOM,0,0);
    }

    public void dismiss(){
        popupWindow.dismiss();
    }


    public static class SimpleTextAdapter extends BaseAdapter{
        private List<String> data;
        private Context context;
        public SimpleTextAdapter(Context context,List<String> data){
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_easy_popup_list,null,false);
            }

            TextView textView = convertView.findViewById(R.id.tv_title);
            textView.setText(data.get(position));

            return convertView;
        }
    }

}

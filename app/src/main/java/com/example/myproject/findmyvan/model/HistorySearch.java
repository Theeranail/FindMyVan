package com.example.myproject.findmyvan.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myproject.findmyvan.R;

import java.util.List;

/**
 * Created by TheeranaiAsipong on 1/10/2559.
 */

public class HistorySearch {

    private int id_historysearch;
    private String keyword;

    public HistorySearch(){}

    public int getId_historysearch() {
        return id_historysearch;
    }

    public void setId_historysearch(int id_historysearch) {
        this.id_historysearch = id_historysearch;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public static class AdapterViewList extends BaseAdapter{

        Context context;
        List<HistorySearch>list;
        LayoutInflater inflater;
        public AdapterViewList(Context context, List<HistorySearch>list){
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (view == null) {
                view = inflater.inflate(R.layout.list_item_textview, null);
            }
            TextView textView = (TextView)view.findViewById(R.id.textview_item);
            textView.setText(list.get(i).getKeyword());

            return view;
        }
    }
}

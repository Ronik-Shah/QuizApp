package com.example.quizapp.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quizapp.R;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {

    private int layout;
    private String[] arr;
    private LayoutInflater inflater;


    public SpinnerAdapter(Context context,int layout,String[] arr){
        this.layout = layout;
        this.arr = arr;
        inflater = LayoutInflater.from(context);
    }

    public SpinnerAdapter(Context context, int layout, ArrayList<String> arr){
        this.layout = layout;
        this.arr = toArray(arr);
        inflater = LayoutInflater.from(context);
    }

    private String[] toArray(ArrayList<String> arr){
        int n = arr.size();
        String[] res = new String[n];
        for(int i=0;i<n;i++){
            res[i] = arr.get(i);
        }
        return res;
    }

    @Override
    public int getCount() {
        return arr.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(layout,null);
        TextView names = view.findViewById(R.id.spinner_item_text_view);
        names.setText(arr[i] + "\n");
        return view;
    }
}

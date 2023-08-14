package com.example.myadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bthbuoi10.R;
import com.example.myadapter.ImageDownloaderTask;
import com.example.myadapter.Tutorial;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Tutorial> {
    private List<Tutorial> list;
    private Bitmap bitmap;
    private Context context;

    public MyAdapter(List<Tutorial> lst, Context cntx) {
        super(cntx, R.layout.list_item, lst);
        this.list = lst;
        this.context = cntx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_item, null, true);
        holder = new ViewHolder();
        holder.textViewName = convertView.findViewById(R.id.textViewName);
        holder.textDescription = convertView.findViewById(R.id.textDescription);
        holder.imageView = convertView.findViewById(R.id.imageView);

        convertView.setTag(holder);


        Tutorial tut = list.get(position);
        String imgUrl = tut.getImageUrl();
        String des = tut.getDescription();
        String tit = tut.getName();

        holder.textDescription.setText(des);
        holder.textViewName.setText(tit);
        if(holder.imageView!=null)
        {
            new ImageDownloaderTask(holder.imageView).execute(imgUrl);
        }
        holder.imageView.setImageBitmap(bitmap);
        return convertView;
    }

    static class ViewHolder {
        TextView textViewName;
        TextView textDescription;
        ImageView imageView;
    }
}

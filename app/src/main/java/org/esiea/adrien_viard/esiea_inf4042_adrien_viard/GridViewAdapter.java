package org.esiea.adrien_viard.esiea_inf4042_adrien_viard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Clems on 19/12/2016.
 */

public class GridViewAdapter extends ArrayAdapter<ImageItems> {
    private Context context;
    private int layoutId;
    private ArrayList<ImageItems> myGridImages;

    //Constructor
    public GridViewAdapter(Context context, int layoutId, ArrayList<ImageItems> myGridImages) {
        super(context, layoutId, myGridImages);
        this.context = context;
        this.layoutId = layoutId;
        this.myGridImages = myGridImages;
    }

    //Updates
    public void setMyGridImages(ArrayList<ImageItems> myGridImages){
        this.myGridImages = myGridImages;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        //View row = view;
        ViewHolder myHolder;

        if (/*row*/ view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.grid_items, null);

            myHolder = new ViewHolder();

            myHolder.title = (TextView) /*row*/view.findViewById(R.id.grid_item_title);
            myHolder.image = (ImageView) /*row*/view.findViewById(R.id.grid_item_image);

            /*row*/view.setTag(myHolder);
        } else {
            myHolder = (ViewHolder) /*row*/view.getTag();
        }

        ImageItems item = myGridImages.get(i);

        myHolder.title.setText(item.getTitle());

        Picasso.with(context).load(item.getImageURL()).into(myHolder.image);

        return /*row*/view;
    }

    private static class ViewHolder {
        TextView title;
        ImageView image;
    }
}

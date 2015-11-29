package com.ips.nnkhuong.indoorpositiontracker;

import android.widget.ImageView;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Canvas;
/**
 * Created by nguyenkhuong on 11/26/15.
 */
public class MapView extends ImageView{

    /** CONSTRUCTORS */
    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }
    public void setMap(int resId) {
        String mSelectedMap = String.valueOf(resId);
        this.setImageResource(R.drawable.kerros); // change map image
    }
}

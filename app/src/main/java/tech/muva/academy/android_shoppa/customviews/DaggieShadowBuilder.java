package tech.muva.academy.android_shoppa.customviews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;
import java.net.URL;

import tech.muva.academy.android_shoppa.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * Created by Daggie on 3/8/2019.
 */

public class DaggieShadowBuilder extends View.DragShadowBuilder{

    private static Drawable shadow;

    public DaggieShadowBuilder(View v, String imageUrl) {
        super(v);

            shadow = loadImageFromWebURL(imageUrl)!=null?loadImageFromWebURL(imageUrl): getView().getContext().getResources().getDrawable(R.drawable.burger);
    }
    @Override
    public void onProvideShadowMetrics (Point size, Point touch) {
        int width, height, imageRatio;
     //imageRatio = shadow.getIntrinsicHeight() / shadow.getIntrinsicWidth();
        width = getView().getWidth() / 2;
        height = getView().getWidth() / 2;
   // height = width * imageRatio;

        shadow.setBounds(0, 0, width,height);
        size.set(width, height);
        touch.set(width / 2, height / 2);
    }
    @Override
    public void onDrawShadow(Canvas canvas) {
        shadow.draw(canvas);
    }

    public static Drawable loadImageFromWebURL(String url) {
        try {
            Bitmap bitmap= BitmapFactory.decodeStream(new URL(url).openStream());

            //InputStream iStream = (InputStream) new URL(url).getContent();
            Log.d(TAG, "loadImageFromWebURL: "+url);
            return new BitmapDrawable(bitmap);
                    //Drawable.createFromStream(iStream, url);
        } catch (Exception e) {
            return null;
        }}
}



















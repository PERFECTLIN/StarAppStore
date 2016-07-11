package com.example.perfectlin.starappstore.Activity.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.perfectlin.starappstore.Activity.Utils.Key;
import com.example.perfectlin.starappstore.R;


public class CoverFlowSampleAdapter extends CoverFlowAdapter {

    int a;
    // =============================================================================
    // Private members
    // =============================================================================

    private int[] images = {R.mipmap.s01, R.mipmap.s02, R.mipmap.s03, R.mipmap.s04,
            R.mipmap.s05, R.mipmap.s06, R.mipmap.s07, R.mipmap.s08};

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Integer getItem(int i) {
        return images[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
        CustomViewGroup customViewGroup = null;

        if (reuseableView != null) {
            customViewGroup = (CustomViewGroup) reuseableView;
            System.out.println("+++++++++++++++++++++++++++++++++++++++!null");
        } else {
            System.out.println("+++++++++++++++++++++++++++++++++++++++null");
            customViewGroup = new CustomViewGroup(viewGroup.getContext());
            customViewGroup.setLayoutParams(new CoverFlow.LayoutParams(Key.CoverFlow_Width, Key.CoverFlow_height));//图片高度
        }

        customViewGroup.getImageView().setImageResource(this.getItem(i));
        System.out.println("ppppppp"+customViewGroup.getImageView().getDrawable().toString());
//        customViewGroup.getTextView().setText(String.format("Item %d", i));
//        int pos=customViewGroup.getPos().String.format("Item %d", i);

        return customViewGroup;
    }

    private class CustomViewGroup extends LinearLayout {

        private ImageView imageView;
//        Button bt;

        public CustomViewGroup(final Context context) {
            super(context);
            this.imageView = new ImageView(context);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            this.imageView.setLayoutParams(layoutParams);
            this.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            this.imageView.setAdjustViewBounds(true);


            this.addView(this.imageView);
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++添加view成功");

        }

        public ImageView getImageView() {
            return imageView;
        }


    }
}

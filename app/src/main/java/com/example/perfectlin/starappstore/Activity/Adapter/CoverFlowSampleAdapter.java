package com.example.perfectlin.starappstore.Activity.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.perfectlin.starappstore.Activity.Thread.GetFirJsonThread;
import com.example.perfectlin.starappstore.Activity.Utils.Key;


public class CoverFlowSampleAdapter extends CoverFlowAdapter {

    private Bitmap[] bitmaps = GetFirJsonThread.bitmaps;
    private TextView tv_name,tv_desc;

    public CoverFlowSampleAdapter() {
        System.out.println("------------------>进入Adapter");
    }
    public CoverFlowSampleAdapter(TextView tv_name,TextView tv_desc) {
        this.tv_name=tv_name;
        this.tv_desc=tv_desc;

//      设置第一次打开获取数据更新界面
        String name = GetFirJsonThread.firAppListBeanList.get(0).getName();
        String desc=GetFirJsonThread.APP_DESC[0];
        tv_name.setText(name);
        tv_desc.setText(desc);
    }

    @Override
    public int getCount() {
        return bitmaps.length;
    }

    @Override
    public Bitmap getItem(int i) {
        return bitmaps[i];
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
        } else {
            customViewGroup = new CustomViewGroup(viewGroup.getContext());
            customViewGroup.setLayoutParams(new CoverFlow.LayoutParams(Key.CoverFlow_Width, Key.CoverFlow_height));//图片高度
        }

        customViewGroup.getImageView().setImageBitmap(this.getItem(i));
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

        }

        public ImageView getImageView() {
            return imageView;
        }


    }
}

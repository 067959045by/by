package com.lianzhihui.minitiktok.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.echofeng.common.utils.AESFileCrypt;
import com.echofeng.common.utils.FileUtils;
import com.echofeng.common.utils.SystemUtils;
import com.lianzhihui.minitiktok.bean.AdResponse;
import com.lianzhihui.onlyleague.R;
import com.net.core.EasyHttp;
import com.youth.banner.adapter.BannerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * 项目：Practice
 * 文件描述：图片轮播器图片适配器
 * 作者：ljj
 * 创建时间：2020/4/21
 */
public class BannerImageAdapter extends BannerAdapter<AdResponse, BannerImageAdapter.BannerViewHolder> implements View.OnClickListener {
    private Context context;
    public BannerImageAdapter(Context context,List<AdResponse> mDatas) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
        this.context = context;
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setBackgroundResource(R.drawable.shape_round_primty_8dp);
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerImageAdapter.BannerViewHolder holder, AdResponse data, int position, int size) {
        SimpleTarget<File> simpleTarget = new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                byte[] decryptBytes = AESFileCrypt.decryptData(resource);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decryptBytes, 0, decryptBytes.length);
                holder.imageView.setImageBitmap(bitmap);
            }
        };
        Glide.with(context).asFile().load(data.getCover()).error(R.mipmap.ic_launcher).into(simpleTarget);
        EasyHttp.downLoad(data.getCover());
        holder.imageView.setTag(data.getLink());
        holder.imageView.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        String link = (String) view.getTag();
        if (link.contains("url://")){
            String realLink = link.replace("url://","");
            SystemUtils.openBrowser(context,realLink);
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }

}

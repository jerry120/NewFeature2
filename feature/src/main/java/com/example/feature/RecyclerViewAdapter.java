package com.example.feature;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2017/6/19.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecycleViewHolder> {

    private List<RecyclerViewBean> mDatas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapter(List<RecyclerViewBean> datas, Context context) {
        mDatas = datas;
        mContext = context;
    }

    //设置recyclerview的长度
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //
    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建view
//        View view = View.inflate(mContext, R.layout.item_recyclerview, null);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview, parent,false);
        //根据view创建出viewholder
        RecycleViewHolder viewHolder = new RecycleViewHolder(view);
        //返回viewholder

        return viewHolder;
    }

    //当item显示时,该方法就会调用一次

    /**
     *
     * @param holder     onCreateViewHolder方法创建出来的RecycleViewHolder对象
     * @param position  当前条目的索引
     */
    @Override
    public void onBindViewHolder(RecycleViewHolder holder, final int position) {
        RecyclerViewBean recyclerViewBean = mDatas.get(position);
//        holder.mIv_recyclerview.setImageResource(recyclerViewBean.imageId);
        holder.mTv_recyclerview.setText(recyclerViewBean.title);
        Glide.with(mContext).load(recyclerViewBean.imageId).into(holder.mIv_recyclerview);

        //给图片,文字设置点击事件
        holder.mIv_recyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //3,在逻辑处理处,条用接口方法
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.OnPicClick(position);
                }
            }
        });

        holder.mTv_recyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnItemClickListener != null) {
                    mOnItemClickListener.OnTextClick(position);
                }
            }
        });

        //给整个条目设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnItemClickListener != null) {
                    mOnItemClickListener.OnViewClick(position);
                }
            }
        });
    }


    public class RecycleViewHolder extends RecyclerView.ViewHolder{

        private final ImageView mIv_recyclerview;
        private final TextView mTv_recyclerview;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            //获取view中控件进行复用操作
            mIv_recyclerview = (ImageView) itemView.findViewById(R.id.iv_recyclerview);
            mTv_recyclerview = (TextView) itemView.findViewById(R.id.tv_recyclerview);

        }
    }

    //1,创建接口
    public interface  OnItemClickListener{
        //图片点击方法
        public void OnPicClick(int position);

        public void OnTextClick(int position);

        public void OnViewClick(int position);
    }

    //2,创建set方法
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
}

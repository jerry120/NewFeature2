package com.example.feature;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class TabLayoutActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private boolean isStrat;//记录是否开启了轮播
    private Handler mHandler = new Handler();

    private int[] mStraggeredIcons = new int[]{R.mipmap.p1, R.mipmap.p2, R.mipmap.p3, R
            .mipmap.p4, R.mipmap.p5, R.mipmap.p6, R.mipmap.p7, R.mipmap.p8, R.mipmap.p9, R
            .mipmap.p10, R.mipmap.p11, R.mipmap.p12, R.mipmap.p13, R.mipmap.p14, R.mipmap
            .p15, R.mipmap.p16, R.mipmap.p17, R.mipmap.p18, R.mipmap.p19, R.mipmap.p20, R
            .mipmap.p21, R.mipmap.p22, R.mipmap.p23, R.mipmap.p24, R.mipmap.p25, R.mipmap
            .p26, R.mipmap.p27, R.mipmap.p28, R.mipmap.p29, R.mipmap.p30, R.mipmap.p31, R
            .mipmap.p32, R.mipmap.p33, R.mipmap.p34, R.mipmap.p35, R.mipmap.p36, R.mipmap
            .p37, R.mipmap.p38, R.mipmap.p39, R.mipmap.p40, R.mipmap.p41, R.mipmap.p42, R
            .mipmap.p43, R.mipmap.p44};
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);

        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        MyAdapter adapter = new MyAdapter();
        mViewPager.setAdapter(adapter);

        //绑定tablayout和ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

        //给floatingactionbutton设置点击事件
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingactionbutton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator oa = ObjectAnimator.ofFloat(mFloatingActionButton, "rotation", 0, 360);
                oa.setDuration(1000);
                oa.start();

                Snackbar.make(mFloatingActionButton,isStrat? "取消自动轮播":"开启自动轮播", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isStrat){
                            stopPlay();
                        }else{
                            startPlay();
                        }

                        isStrat = !isStrat;

                    }
                }).show();
            }
        });

        //给viewpager设置页面切换监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //页面滚动时,实时被调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面选中时调用一次
            @Override
            public void onPageSelected(int position) {

            }

            //当滚动状态改变时,调用一次
            /**
             * @param state The new scroll state.
             * @see ViewPager#SCROLL_STATE_IDLE
             * @see ViewPager#SCROLL_STATE_DRAGGING
             * @see ViewPager#SCROLL_STATE_SETTLING
             *
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void startPlay() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
               //获取viewpager当前的索引,跳转到下一页
                int currentItem = mViewPager.getCurrentItem();
                int newItem = (currentItem+1)%mStraggeredIcons.length;//去摸保证newItem不越界
                mViewPager.setCurrentItem(newItem);

                mHandler.postDelayed(this, 2000);           //执行循环任务
            }
        }, 2000);
    }

    private void stopPlay() {
        //停止handler任务
        mHandler.removeCallbacksAndMessages(null);
    }

    private class MyAdapter extends PagerAdapter {

        //设置viewpager的长度
        @Override
        public int getCount() {
            return mStraggeredIcons.length;
        }

        //判断将要显示的view对象是否是初始化得到的对象,如果是,则显示,否则不显示
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //创建出页面显示对象view
            ImageView iv = new ImageView(TabLayoutActivity.this);
            iv.setImageResource(mStraggeredIcons[position]);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

            //将view添加到容器中
            container.addView(iv);

            //将当前的view返回
            return iv;

        }

        /**
         * @param container
         * @param position
         * @param object    将要被销毁的iv对象
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }

        //viewpager界面标题显示方法
        @Override
        public CharSequence getPageTitle(int position) {
            return "图片" + (position + 1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mHandler.removeCallbacksAndMessages(null);          //避免自动轮播导致内存泄漏
    }
}

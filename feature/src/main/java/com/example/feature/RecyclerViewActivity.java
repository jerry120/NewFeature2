package com.example.feature;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    List<RecyclerViewBean> datas = new ArrayList<>();
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
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("RecyclerView的使用");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);


        for (int i = 0; i < mStraggeredIcons.length; i++) {
            RecyclerViewBean bean = new RecyclerViewBean();
            bean.title = "图片"+(i+1);
            bean.imageId = mStraggeredIcons[i];

            datas.add(bean);
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(datas,this);
        mRecyclerView.setAdapter(adapter);

        //给swiperefreshlayout设置监听
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //当刷新时调用该方法
            @Override
            public void onRefresh() {
                //访问网络,获取最新的数据展示
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RecyclerViewActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                        //刷新成功后必须通知mswipeRefreshlayout停止刷新
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);

            }
        });

        //修改颜色
        //拿color.xml文件中定义的颜色值getResources().getColor(R.color.colorAccent)
        mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN,Color.BLUE,getResources().getColor(R.color.colorAccent));

        //4,给recyclview设置条目点击事件
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnPicClick(int position) {
                Toast.makeText(RecyclerViewActivity.this, "图片被点击了"+(position+1), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnTextClick(int position) {

                Toast.makeText(RecyclerViewActivity.this, "文字被点击了"+(position+1), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void OnViewClick(int position) {

                Toast.makeText(RecyclerViewActivity.this, "条目视图被点击了"+(position+1), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rv,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.lv_vertical:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
                break;
            case R.id.lv_horizontal:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
                break;
            case R.id.gv_vertical:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,3,LinearLayoutManager.VERTICAL,false));
                break;
            case R.id.gv_horizontal:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,3,LinearLayoutManager.HORIZONTAL,false));
                break;
            case R.id.sv_vertical:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                break;
            case R.id.sv_horizontal:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL));
                break;
            default:

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

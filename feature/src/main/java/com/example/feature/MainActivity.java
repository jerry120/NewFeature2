package com.example.feature;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 100;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private EditText et_username;
    private TextInputLayout til_username;
    private EditText et_password;
    private TextInputLayout til_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //替换ActionBar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("主标题");//必须在替换actionbar之前改变主标题
        setSupportActionBar(mToolbar);
        //设置主标题子标题导航图标
        mToolbar.setSubtitle("子标题");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "导航图标被点击了", Toast.LENGTH_SHORT).show();
            }
        });

        //设置箭头显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //将drawablayout和toolbar设置关联
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawlayout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();//同步drawlayout 和 toolbar的关系
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        //给drawerLayout设置打开,关闭等监听
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {

            //抽屉滑动时

            /**
             *
             * @param drawerView mDrawlayout
             * @param slideOffset mdrawlayout滑动出来的比例
             */
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            //抽屉打开时
            @Override
            public void onDrawerOpened(View drawerView) {
                Toast.makeText(MainActivity.this, "抽屉打开", Toast.LENGTH_SHORT).show();
            }

            //关闭时
            @Override
            public void onDrawerClosed(View drawerView) {
                Toast.makeText(MainActivity.this, "抽屉关闭", Toast.LENGTH_SHORT).show();

            }

            //状态改变时
            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        //设置navigationview的点击事件
        mNavigationView = (NavigationView) findViewById(R.id.navigationview);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nv_AppBarLayout:
                        Toast.makeText(MainActivity.this, "nv_AppBarLayout", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, AppBarLayoutActivity.class));
                        break;
                    case R.id.nv_CollaspingToolBarLayout:
                        Toast.makeText(MainActivity.this, "nv_CollaspingToolBarLayout", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, CollaspsingToolbarLayoutActivity.class));
                        break;
                    case R.id.nv_RecyclerView:
                        Toast.makeText(MainActivity.this, "nv_RecyclerView", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
                        break;
                    case R.id.nv_TableLayout:
                        startActivity(new Intent(MainActivity.this, TabLayoutActivity.class));
                        Toast.makeText(MainActivity.this, "nv_TableLayout", Toast.LENGTH_SHORT).show();
                        break;
                    default:

                        break;
                }

                mDrawerLayout.closeDrawers();//关闭抽屉

                return false;
            }
        });

        //获取textInputlayout设置监听

        et_username = (EditText) findViewById(R.id.et_username);
        til_username = (TextInputLayout) findViewById(R.id.til_username);
        et_password = (EditText) findViewById(R.id.et_password);
        til_password = (TextInputLayout) findViewById(R.id.til_password);

        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId== EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(MainActivity.this, "输入法的完成被点击了", Toast.LENGTH_SHORT).show();
                }

                return false;//自己不消费,传递给系统来响应操作
            }
        });

    }


    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add("添加好友");//代码方式添加菜单
        //菜单填充器
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);//
        }


        return super.onCreateOptionsMenu(menu);
    }
    //菜单被点击时

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Toast.makeText(this, "添加好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Toast.makeText(this, "分享给好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about:
                Toast.makeText(this, "关于about", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void submit(View view) {

        String username = et_username.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            til_username.setErrorEnabled(true);
            til_username.setError("用户名不能为空");
            til_username.requestFocus();//将光标设置活来
            return;
        } else {
            til_username.setErrorEnabled(false);
        }

        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            til_password.setErrorEnabled(true);
            til_password.setError("密码不能为空");
            til_password.requestFocus();
            return;
        } else {
            til_password.setErrorEnabled(false);
        }


    }

    public void writeSDcard(View view){

        //先检查是够获取了权限.如果没有要直接请求权限
        int checkSelfPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(checkSelfPermission== PackageManager.PERMISSION_DENIED){
            //如果没有获取权限,需要动态请求(给用户弹出窗口)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},REQUEST_PERMISSION);
            return;
        }


        File file = new File(Environment.getExternalStorageDirectory(),"text21");
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("acfahfvafajf");
            fw.flush();
            Toast.makeText(this, "写入SDcard成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听权限授权结果
    /**
     *
     * @param requestCode       请求码
     * @param permissions       请求的权限值
     * @param grantResults      请求结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //先判断是否是自己的请求,
        if(requestCode==REQUEST_PERMISSION){
            int grantResult = grantResults[0];
            if(grantResult == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            }

        }
    }
}

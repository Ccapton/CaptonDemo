package com.capton.baseapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.capton.capton.R;
import com.capton.common.base.JsonUtil;
import com.capton.common.base.SpConstant;
import com.capton.common.user.LoginActivity;
import com.capton.common.user.User;
import com.capton.enb.EasyNaviBar;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    EasyNaviBar naviBar;
    ViewPager viewPager;
    List<Fragment> fragmentList;
    CircleImageView face;
    TextView nick;
    NavigationView navigationView;
      @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        initView();

     }

    @SuppressLint("WrongViewCast")
    private void initView() {
        drawerLayout = findViewById(R.id.drawerlayout);
        initFragment();
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                 naviBar.check(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        naviBar=  findViewById(R.id.navibar);
        String titles[]=new String[]{"首页","关注"};
        int icons[]=new int[]{R.drawable.ic_home_grey_600_24dp,
                R.drawable.ic_favorite_grey_600_24dp };
        int checkIcons[]=new int[]{R.drawable.ic_home_green_600_24dp,
                R.drawable.ic_favorite_green_600_24dp };
        naviBar.setTabData(titles, icons, checkIcons);
        naviBar.setCheckTextColor(getResources().getColor(R.color.colorPrimary2));
        naviBar.setOnTabClickListener(new EasyNaviBar.OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                 viewPager.setCurrentItem(position);
             }
        });
        navigationView = findViewById(R.id.leftMenuLayout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.settings){
                    ActivityUtils.startActivity(SettingActivity.class);
                }
                return false;
            }
        });
        face = navigationView.getHeaderView(0).findViewById(R.id.face);
        nick = navigationView.getHeaderView(0).findViewById(R.id.nick);

        if(!TextUtils.isEmpty(SPUtils.getInstance().getString(SpConstant.USER))){
            try {
                User user = (User) JsonUtil.strToObject(SPUtils.getInstance().getString(SpConstant.USER), User.class);
                if(user!=null) {
                    nick.setText(user.getNick());
                    Glide.with(this).load(user.getAvater()).into(face);
                    face.setOnClickListener(new AvaterOnClickListener());
                    nick.setOnClickListener(null);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            face.setImageResource(R.mipmap.ic_launcher_round);
            nick.setText("点击登录");
            face.setOnClickListener(new LoginOnClickListener());
            nick.setOnClickListener(new LoginOnClickListener());
        }
    }

    HomeFragment homeFragment;
    private void initFragment() {
          fragmentList = new ArrayList<>();
          homeFragment = new HomeFragment();
          fragmentList.add(homeFragment);
          fragmentList.add(new Fragment());
    }

    class LoginOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            ActivityUtils.startActivity(LoginActivity.class);
        }
    }
    class AvaterOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            ActivityUtils.startActivity(AvaterActivity.class);
        }
    }

    @Subscriber(tag = "loginSuccess",mode = ThreadMode.MAIN)
    public void loginSuccess(User user){
          if (user!=null){
              nick.setText(user.getNick());
              Glide.with(this).load(user.getAvater()).into(face);
              face.setOnClickListener(new AvaterOnClickListener());
              nick.setOnClickListener(null);
          }
    }
    @Subscriber(tag = "logoutSuccess",mode = ThreadMode.MAIN)
    public void logoutSuccess(boolean logout){
        if (logout){
            nick.setText("点击登录");
            face.setImageResource(R.mipmap.ic_launcher_round);
            face.setOnClickListener(new LoginOnClickListener());
            nick.setOnClickListener(new LoginOnClickListener());
         }
    }
    @Subscriber(tag = "updateSuccess",mode = ThreadMode.MAIN)
    public void updateSuccess(User user){
        if (user!=null){
            nick.setText(user.getNick());
            Glide.with(this).load(user.getAvater()).into(face);
            face.setOnClickListener(new AvaterOnClickListener());
            nick.setOnClickListener(null);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}

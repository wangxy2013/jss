package com.jss.sdd.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jss.sdd.R;
import com.jss.sdd.fragment.CategoryFragment;
import com.jss.sdd.fragment.HomeFragment;
import com.jss.sdd.fragment.InvitationFragment;
import com.jss.sdd.fragment.MineFragment;
import com.jss.sdd.widget.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity
{
    @BindView(android.R.id.tabhost)
    FragmentTabHost fragmentTabHost;
    private String texts[] = {"首页", "分类", "邀请", "我的"};
    private int imageButton[] = {R.drawable.ic_home_selector, R.drawable.ic_category_selector, R.drawable.ic_invitation_selector, R.drawable
            .ic_mine_selector};


    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        StatusBarUtil.setTransparentStatusBar(this);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setStatusBarBackground(this, R.color.main_bg);
        StatusBarUtil.StatusBarLightMode(MainActivity.this, false);


    }

    @Override
    protected void initEvent()
    {

    }

    @Override
    protected void initViewData()
    {
        fragmentList.add(new HomeFragment());
        fragmentList.add(new CategoryFragment());
        fragmentList.add(new InvitationFragment());
        fragmentList.add(new MineFragment());
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.main_layout);

        new Handler().post(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 0; i < texts.length; i++)
                {
                    TabHost.TabSpec spec = fragmentTabHost.newTabSpec(texts[i]).setIndicator(getView(i));

                    fragmentTabHost.addTab(spec, fragmentList.get(i).getClass(), null);

                    //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
                    // fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable
                    // .main_tab_selector);
                }
                fragmentTabHost.getTabWidget().setDividerDrawable(R.color.transparent);
            }
        });

    }

    private View getView(int i)
    {
        //取得布局实例
        View view = View.inflate(MainActivity.this, R.layout.tabcontent, null);
        //取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text);

        //设置图标
        imageView.setImageResource(imageButton[i]);
        //设置标题
        textView.setText(texts[i]);
        return view;
    }
}

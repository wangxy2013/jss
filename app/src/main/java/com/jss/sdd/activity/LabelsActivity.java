package com.jss.sdd.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jss.sdd.R;
import com.jss.sdd.adapter.LabelsAdapter;
import com.jss.sdd.entity.CategoryInfo;
import com.jss.sdd.http.DataRequest;
import com.jss.sdd.http.HttpRequest;
import com.jss.sdd.http.IRequestListener;
import com.jss.sdd.listener.MyItemClickListener;
import com.jss.sdd.parse.LoginHandler;
import com.jss.sdd.utils.AESUtils;
import com.jss.sdd.utils.ConfigManager;
import com.jss.sdd.utils.ToastUtil;
import com.jss.sdd.utils.Urls;
import com.jss.sdd.widget.FullyGridLayoutManager;
import com.jss.sdd.widget.statusbar.StatusBarUtil;
import com.sdd.jss.swiperecyclerviewlib.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

//我的标签
public class LabelsActivity extends BaseActivity implements IRequestListener
{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_woman)
    ImageView ivWoman;
    @BindView(R.id.iv_man)
    ImageView ivMan;
    @BindView(R.id.tv_marriages1)
    TextView tvMarriages1;
    @BindView(R.id.tv_marriages2)
    TextView tvMarriages2;
    @BindView(R.id.tv_marriages3)
    TextView tvMarriages3;
    @BindView(R.id.tv_marriages4)
    TextView tvMarriages4;
    @BindView(R.id.tv_ages1)
    TextView tvAges1;
    @BindView(R.id.tv_ages2)
    TextView tvAges2;
    @BindView(R.id.tv_ages3)
    TextView tvAges3;
    @BindView(R.id.tv_ages4)
    TextView tvAges4;
    @BindView(R.id.tv_profession1)
    TextView tvProfession1;
    @BindView(R.id.tv_profession2)
    TextView tvProfession2;
    @BindView(R.id.tv_profession3)
    TextView tvProfession3;
    @BindView(R.id.tv_profession4)
    TextView tvProfession4;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.btn_submit)
    Button btnSubmit;


    private String categorys;
    private int marriages, ages, profession, sex;
    private List<TextView> mMarriagesViewList = new ArrayList<>();
    private List<TextView> mAgeViewList = new ArrayList<>();
    private List<TextView> mProfessionViewList = new ArrayList<>();

    private List<CategoryInfo> labelInfoList = new ArrayList<>();
    private LabelsAdapter mLabelsAdapter;


    private static final String ADD_LABELS = "add_labels";
    private static final int REQUEST_SUCCESS = 0x01;
    private static final int REQUEST_FAIL = 0x02;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:
                    ToastUtil.show(LabelsActivity.this, "操作成功");
                    break;


                case REQUEST_FAIL:
                    ToastUtil.show(LabelsActivity.this, msg.obj.toString());
                    break;

            }
        }
    };

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_labels);
        StatusBarUtil.setStatusBarBackground(this, R.color.main_bg);
        StatusBarUtil.StatusBarLightMode(LabelsActivity.this, false);
    }

    @Override
    protected void initEvent()
    {

    }

    @Override
    protected void initViewData()
    {
        tvSubmit.setText("跳过");
        tvTitle.setText("标签设置");
        mMarriagesViewList.add(tvMarriages1);
        mMarriagesViewList.add(tvMarriages2);
        mMarriagesViewList.add(tvMarriages3);
        mMarriagesViewList.add(tvMarriages4);
        mAgeViewList.add(tvAges1);
        mAgeViewList.add(tvAges2);
        mAgeViewList.add(tvAges3);
        mAgeViewList.add(tvAges4);
        mProfessionViewList.add(tvProfession1);
        mProfessionViewList.add(tvProfession2);
        mProfessionViewList.add(tvProfession3);
        mProfessionViewList.add(tvProfession4);

        setMarriagesStatus(0);
        setAgeStatus(0);
        setProfessionStatus(0);
        ivWoman.setSelected(true);
        ivMan.setSelected(false);


        recyclerView.setLayoutManager(new FullyGridLayoutManager(LabelsActivity.this, 4));
        recyclerView.addItemDecoration(new SpaceItemDecoration(30, 30));

        String[] categoryNameArr = getResources().getStringArray(R.array.category_one_name);
        String[] categoryIdArr = getResources().getStringArray(R.array.category_one_id);

        for (int i = 0; i < categoryNameArr.length; i++)
        {
            CategoryInfo mCategoryInfo = new CategoryInfo();
            mCategoryInfo.setName(categoryNameArr[i]);
            mCategoryInfo.setId(categoryIdArr[i]);
            labelInfoList.add(mCategoryInfo);
        }


        mLabelsAdapter = new LabelsAdapter(labelInfoList, new MyItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                if (labelInfoList.get(position).isSelected())
                {
                    labelInfoList.get(position).setSelected(false);
                }
                else
                {
                    labelInfoList.get(position).setSelected(true);
                }
                mLabelsAdapter.notifyItemChanged(position);
            }
        });
        recyclerView.setAdapter(mLabelsAdapter);
    }


    private void setMarriagesStatus(int index)
    {
        this.marriages = index + 1;
        for (int i = 0; i < mMarriagesViewList.size(); i++)
        {
            if (i == index)
            {
                mMarriagesViewList.get(index).setSelected(true);
            }
            else
            {
                mMarriagesViewList.get(i).setSelected(false);
            }
        }
    }

    private void setAgeStatus(int index)
    {
        this.ages = index + 1;
        for (int i = 0; i < mAgeViewList.size(); i++)
        {
            if (i == index)
            {
                mAgeViewList.get(index).setSelected(true);
            }
            else
            {
                mAgeViewList.get(i).setSelected(false);
            }
        }
    }

    private void setProfessionStatus(int index)
    {
        this.ages = index + 1;
        for (int i = 0; i < mProfessionViewList.size(); i++)
        {
            if (i == index)
            {
                mProfessionViewList.get(index).setSelected(true);
            }
            else
            {
                mProfessionViewList.get(i).setSelected(false);
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_submit, R.id.iv_woman, R.id.iv_man, R.id.tv_marriages1, R.id.tv_marriages2, R.id.tv_marriages3, R.id
            .tv_marriages4, R.id.tv_ages1, R.id.tv_ages2, R.id.tv_ages3, R.id.tv_ages4, R.id.tv_profession1, R.id.tv_profession2, R.id
            .tv_profession3, R.id.tv_profession4, R.id.btn_submit})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                finish();
                break;
            case R.id.iv_woman:
                ivWoman.setSelected(true);
                ivMan.setSelected(false);
                sex = 0;
                break;
            case R.id.iv_man:
                ivWoman.setSelected(false);
                ivMan.setSelected(true);
                sex = 1;
                break;

            case R.id.tv_marriages1:
                setMarriagesStatus(0);
                break;
            case R.id.tv_marriages2:
                setMarriagesStatus(1);
                break;
            case R.id.tv_marriages3:
                setMarriagesStatus(2);
                break;
            case R.id.tv_marriages4:
                setMarriagesStatus(3);
                break;
            case R.id.tv_ages1:
                setAgeStatus(0);
                break;
            case R.id.tv_ages2:
                setAgeStatus(1);
                break;
            case R.id.tv_ages3:
                setAgeStatus(2);
                break;
            case R.id.tv_ages4:
                setAgeStatus(3);
                break;
            case R.id.tv_profession1:
                setProfessionStatus(0);
                break;
            case R.id.tv_profession2:
                setProfessionStatus(1);
                break;
            case R.id.tv_profession3:
                setProfessionStatus(2);
                break;
            case R.id.tv_profession4:
                setProfessionStatus(3);
                break;
            case R.id.btn_submit:
                break;
        }
    }

    private void addLabelsAction()
    {
        if (getCategoryCount() < 4)
        {
            ToastUtil.show(LabelsActivity.this, "请至少选择4项标签");
            return;
        }

        try
        {
            showProgressDialog();
            String encryptStr = ConfigManager.instance().getMobile() + ";" + ConfigManager.instance().getUserPwd() + System.currentTimeMillis();

            Map<String, Object> valuePairs = new HashMap<>();
            valuePairs.put("profession", profession);
            valuePairs.put("categorys", categorys);
            valuePairs.put("ages", ages);
            valuePairs.put("marriages", marriages);
            Gson gson = new Gson();
            Map<String, String> postMap = new HashMap<>();
            postMap.put("json", gson.toJson(valuePairs));
            postMap.put("encryptID", AESUtils.Encrypt(encryptStr, AESUtils.KEY));
            DataRequest.instance().request(LabelsActivity.this, Urls.getLoginUrl(), this, HttpRequest.POST, ADD_LABELS, postMap, new LoginHandler());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    private int getCategoryCount()
    {
        int count = 0;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < labelInfoList.size(); i++)
        {
            if (labelInfoList.get(i).isSelected())
            {
                count += 1;
                sb.append(labelInfoList.get(i).getId());
                sb.append(",");
            }
        }
        String str = sb.toString();
        if (!TextUtils.isEmpty(str))
        {
            categorys = str.substring(0, str.length() - 1);
        }
        return count;
    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        hideProgressDialog();
    }
}

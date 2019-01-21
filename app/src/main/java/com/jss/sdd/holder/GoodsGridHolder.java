package com.jss.sdd.holder;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jss.sdd.R;
import com.jss.sdd.entity.GoodsInfo;
import com.jss.sdd.listener.MyItemClickListener;
import com.jss.sdd.utils.StringUtils;


/**
 */
public class GoodsGridHolder extends BaseGoodHolder
{

    private Context mContext;
    private ImageView mFreePostageIv;
    private ImageView mGroupBuyIv;
    private ImageView mGoodsImg;
    private TextView mGoodsNameTv;
    private TextView mCouponTv;
    private TextView mCommissionTv;
    private TextView mNewPriceTv;
    private TextView mCostPriceTv;
    private TextView mNumberTv;

    public GoodsGridHolder(View rootView, Context mContext)
    {
        super(rootView);
        this.mContext = mContext;
        mGoodsNameTv = (TextView) rootView.findViewById(R.id.tv_goods_name);
        mCouponTv = (TextView) rootView.findViewById(R.id.tv_coupon);
        mCommissionTv = (TextView) rootView.findViewById(R.id.tv_commission);
        mNewPriceTv = (TextView) rootView.findViewById(R.id.tv_new_price);
        mCostPriceTv = (TextView) rootView.findViewById(R.id.tv_cost_price);
        mNumberTv = (TextView) rootView.findViewById(R.id.tv_number);
        mFreePostageIv = (ImageView) rootView.findViewById(R.id.iv_free_postage);
        mGoodsImg = (ImageView) rootView.findViewById(R.id.iv_goods_img);
        mGroupBuyIv = (ImageView) rootView.findViewById(R.id.iv_group_buy);

    }

    @Override
    public void setGoodsInfo(GoodsInfo mGoodsInfo, int p)
    {
        Glide.with(mContext).load(mGoodsInfo.getPiclink()).into(mGoodsImg);
        SpannableString spannableString = new SpannableString("  " + mGoodsInfo.getTitle());
        Drawable mDrawable = null;

        if (mGoodsInfo.getIsJdSale() == 1)
        {

            mDrawable = mContext.getResources().getDrawable(R.drawable.ic_jdzy);
        }
        else
        {
            mDrawable = mContext.getResources().getDrawable(R.drawable.ic_jd);
        }
        mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
        spannableString.setSpan(new ImageSpan(mDrawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mGoodsNameTv.setText(spannableString);


        if (mGoodsInfo.getIsPg() == 1)
        {
            mGroupBuyIv.setVisibility(View.VISIBLE);
        }
        else
        {
            mGroupBuyIv.setVisibility(View.GONE);
        }

        if (mGoodsInfo.getIsFreeShipping() == 1)
        {
            mFreePostageIv.setVisibility(View.VISIBLE);
        }
        else
        {
            mFreePostageIv.setVisibility(View.GONE);
        }
        mNumberTv.setText(String.format(mContext.getResources().getString(R.string.seller_number), StringUtils.intChange2Str(mGoodsInfo
                .getMonthlysales())));


        if (mGoodsInfo.getIsCoupon() == 1)
        {
            mCouponTv.setVisibility(View.VISIBLE);
            mCouponTv.setText("券:" + mGoodsInfo.getCoupon());
        }
        else
        {
            mCouponTv.setVisibility(View.GONE);
        }

        mCommissionTv.setText("预估佣金:¥" + mGoodsInfo.getCommission());
        mNewPriceTv.setText("¥" + mGoodsInfo.getRealPrice());

        mCostPriceTv.setText("¥" + mGoodsInfo.getOriginalPrice());

        mCostPriceTv.getPaint().setAntiAlias(true);//抗锯齿
        mCostPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
    }


}

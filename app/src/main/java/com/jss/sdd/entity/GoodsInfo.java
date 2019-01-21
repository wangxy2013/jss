package com.jss.sdd.entity;

public class GoodsInfo
{
    private long id; //自增长id
    private long goodsId = 0L;//商品的sku
    private String title = "";//商品标题
    private String details = "";//商品文案
    private String urllink = "";//商品url地址
    private String piclink = "";//图片url地址
    private float originalPrice = 0.0f;//原价
    private float realPrice = 0.0f;//券后价即成交价
    private int totalNum = 0;//优惠券数量
    private int usedNum = 0;//已领券数量
    private int residueNum = 0;//剩余券数量
    private String couponId = "";//券ID
    private String couponName = "";//券名称
    private String couponlink = "";//优惠券url
    private float coupon = 0.0f;//优惠券金额
    private long starttime = 0L;//优惠券开始时间
    private long expiredtime = 0L;//优惠券结束时间
    private String instructions = "";//优惠券使用说明
    private float rate = 0.0f;//佣金比例
    private float commission = 0.0f;//佣金
    private int cateId;//商品类别ID
    private String cateText = "";//商品类别名称
    private int monthlysales = 0;//商品月销量
    private int dailysales = 0;//当日销售量
    private int verifystatus = 0;//0,"待审核"1,"已上架"2, "已驳回"3, "已下架"
    private int status = 1;//商品状态 1 启用 0 未启用
    private String labels = "";//商品标签id集合(id1,id2,...)
    private int malls = 1;//电商类别id (1京东、2 淘宝、3 苏宁、4 石化、5 自营)
    private String mallsname = "";//电商名称(1京东、2淘宝、3天猫、4苏宁)
    private int goodsType = 0;//商品类型
    private int shop = 0;//
    private String shopId = "";//店铺id
    private int shopType = 2;//店铺类型 0 自营 1 pop 2 第三方
    private String shopText = "";//店铺名称
    private int origin = 0;//商品来源id
    private String originText = "第三方招商";//商品来源描述0 第三方招商、1 平台招商、2 商家投放、3  京推推、4 好京客、5 友邻管家
    private long uploadid = 0L;//商品上传者ID
    private String uploadname = "";//商品上传者
    private long facePeopleNum = 0L;//推广面向人数
    private long promotetimes = 0L;//推广次数
    private int secCateId;//商品二级类目ID
    private String secCateText = "";//商品二级类目名称
    private int isCoupon = 0;//是否优惠券 0 否 1 是
    private int isHot = 0;//是否爆款 0 否 1 是
    private int isPg = 0;//是否拼购 0 否 1 是
    private int isJdSale = 0;//是否自营 0 否 1 是
    private int isSecKill = 0;//是否秒杀 0 否 1 是
    private int isFreeShipping = 0;//是否包邮 0 否 1 是
    private int isFreeFreightRisk = 0;//是否支持运费险 0 否 1 是
    private long addtime = 0L;//创建时间
    private long modifytime = 0L;//修改时间
    private int categoryType;


    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getGoodsId()
    {
        return goodsId;
    }

    public void setGoodsId(long goodsId)
    {
        this.goodsId = goodsId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    public String getUrllink()
    {
        return urllink;
    }

    public void setUrllink(String urllink)
    {
        this.urllink = urllink;
    }

    public String getPiclink()
    {
        return piclink;
    }

    public void setPiclink(String piclink)
    {
        this.piclink = piclink;
    }

    public float getOriginalPrice()
    {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice)
    {
        this.originalPrice = originalPrice;
    }

    public float getRealPrice()
    {
        return realPrice;
    }

    public void setRealPrice(float realPrice)
    {
        this.realPrice = realPrice;
    }

    public int getTotalNum()
    {
        return totalNum;
    }

    public void setTotalNum(int totalNum)
    {
        this.totalNum = totalNum;
    }

    public int getUsedNum()
    {
        return usedNum;
    }

    public void setUsedNum(int usedNum)
    {
        this.usedNum = usedNum;
    }

    public int getResidueNum()
    {
        return residueNum;
    }

    public void setResidueNum(int residueNum)
    {
        this.residueNum = residueNum;
    }

    public String getCouponId()
    {
        return couponId;
    }

    public void setCouponId(String couponId)
    {
        this.couponId = couponId;
    }

    public String getCouponName()
    {
        return couponName;
    }

    public void setCouponName(String couponName)
    {
        this.couponName = couponName;
    }

    public String getCouponlink()
    {
        return couponlink;
    }

    public void setCouponlink(String couponlink)
    {
        this.couponlink = couponlink;
    }

    public float getCoupon()
    {
        return coupon;
    }

    public void setCoupon(float coupon)
    {
        this.coupon = coupon;
    }

    public long getStarttime()
    {
        return starttime;
    }

    public void setStarttime(long starttime)
    {
        this.starttime = starttime;
    }

    public long getExpiredtime()
    {
        return expiredtime;
    }

    public void setExpiredtime(long expiredtime)
    {
        this.expiredtime = expiredtime;
    }

    public String getInstructions()
    {
        return instructions;
    }

    public void setInstructions(String instructions)
    {
        this.instructions = instructions;
    }

    public float getRate()
    {
        return rate;
    }

    public void setRate(float rate)
    {
        this.rate = rate;
    }

    public float getCommission()
    {
        return commission;
    }

    public void setCommission(float commission)
    {
        this.commission = commission;
    }

    public int getCateId()
    {
        return cateId;
    }

    public void setCateId(int cateId)
    {
        this.cateId = cateId;
    }

    public String getCateText()
    {
        return cateText;
    }

    public void setCateText(String cateText)
    {
        this.cateText = cateText;
    }

    public int getMonthlysales()
    {
        return monthlysales;
    }

    public void setMonthlysales(int monthlysales)
    {
        this.monthlysales = monthlysales;
    }

    public int getDailysales()
    {
        return dailysales;
    }

    public void setDailysales(int dailysales)
    {
        this.dailysales = dailysales;
    }

    public int getVerifystatus()
    {
        return verifystatus;
    }

    public void setVerifystatus(int verifystatus)
    {
        this.verifystatus = verifystatus;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getLabels()
    {
        return labels;
    }

    public void setLabels(String labels)
    {
        this.labels = labels;
    }

    public int getMalls()
    {
        return malls;
    }

    public void setMalls(int malls)
    {
        this.malls = malls;
    }

    public String getMallsname()
    {
        return mallsname;
    }

    public void setMallsname(String mallsname)
    {
        this.mallsname = mallsname;
    }

    public int getGoodsType()
    {
        return goodsType;
    }

    public void setGoodsType(int goodsType)
    {
        this.goodsType = goodsType;
    }

    public int getShop()
    {
        return shop;
    }

    public void setShop(int shop)
    {
        this.shop = shop;
    }

    public String getShopId()
    {
        return shopId;
    }

    public void setShopId(String shopId)
    {
        this.shopId = shopId;
    }

    public int getShopType()
    {
        return shopType;
    }

    public void setShopType(int shopType)
    {
        this.shopType = shopType;
    }

    public String getShopText()
    {
        return shopText;
    }

    public void setShopText(String shopText)
    {
        this.shopText = shopText;
    }

    public int getOrigin()
    {
        return origin;
    }

    public void setOrigin(int origin)
    {
        this.origin = origin;
    }

    public String getOriginText()
    {
        return originText;
    }

    public void setOriginText(String originText)
    {
        this.originText = originText;
    }

    public long getUploadid()
    {
        return uploadid;
    }

    public void setUploadid(long uploadid)
    {
        this.uploadid = uploadid;
    }

    public String getUploadname()
    {
        return uploadname;
    }

    public void setUploadname(String uploadname)
    {
        this.uploadname = uploadname;
    }

    public long getFacePeopleNum()
    {
        return facePeopleNum;
    }

    public void setFacePeopleNum(long facePeopleNum)
    {
        this.facePeopleNum = facePeopleNum;
    }

    public long getPromotetimes()
    {
        return promotetimes;
    }

    public void setPromotetimes(long promotetimes)
    {
        this.promotetimes = promotetimes;
    }

    public int getSecCateId()
    {
        return secCateId;
    }

    public void setSecCateId(int secCateId)
    {
        this.secCateId = secCateId;
    }

    public String getSecCateText()
    {
        return secCateText;
    }

    public void setSecCateText(String secCateText)
    {
        this.secCateText = secCateText;
    }

    public int getIsCoupon()
    {
        return isCoupon;
    }

    public void setIsCoupon(int isCoupon)
    {
        this.isCoupon = isCoupon;
    }

    public int getIsHot()
    {
        return isHot;
    }

    public void setIsHot(int isHot)
    {
        this.isHot = isHot;
    }

    public int getIsPg()
    {
        return isPg;
    }

    public void setIsPg(int isPg)
    {
        this.isPg = isPg;
    }

    public int getIsJdSale()
    {
        return isJdSale;
    }

    public void setIsJdSale(int isJdSale)
    {
        this.isJdSale = isJdSale;
    }

    public int getIsSecKill()
    {
        return isSecKill;
    }

    public void setIsSecKill(int isSecKill)
    {
        this.isSecKill = isSecKill;
    }

    public int getIsFreeShipping()
    {
        return isFreeShipping;
    }

    public void setIsFreeShipping(int isFreeShipping)
    {
        this.isFreeShipping = isFreeShipping;
    }

    public int getIsFreeFreightRisk()
    {
        return isFreeFreightRisk;
    }

    public void setIsFreeFreightRisk(int isFreeFreightRisk)
    {
        this.isFreeFreightRisk = isFreeFreightRisk;
    }

    public long getAddtime()
    {
        return addtime;
    }

    public void setAddtime(long addtime)
    {
        this.addtime = addtime;
    }

    public long getModifytime()
    {
        return modifytime;
    }

    public void setModifytime(long modifytime)
    {
        this.modifytime = modifytime;
    }


    public int getCategoryType()
    {
        return categoryType;
    }

    public void setCategoryType(int categoryType)
    {
        this.categoryType = categoryType;
    }
}

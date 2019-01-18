package com.jss.sdd.parse;


import com.google.gson.Gson;
import com.jss.sdd.entity.GoodsInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：一句话简单描述
 */
public class GoodsListHandler extends JsonHandler
{

    private List<GoodsInfo> goodsInfoList = new ArrayList<>();

    public List<GoodsInfo> getGoodsInfoList()
    {
        return goodsInfoList;
    }

    @Override
    protected void parseJson(JSONObject jsonObject) throws Exception
    {
        try
        {

            if (null != jsonObject)
            {

                JSONArray arr = jsonObject.optJSONArray("data");

                for (int i = 0; i < arr.length(); i++)
                {
                    goodsInfoList.add(new Gson().fromJson(arr.optString(i), GoodsInfo.class));
                }
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
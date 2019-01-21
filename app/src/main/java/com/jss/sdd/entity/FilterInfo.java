package com.jss.sdd.entity;

public class FilterInfo
{
    private String title;

    private boolean isSelected;

    private String sort;


    public FilterInfo()
    {
    }

    public FilterInfo(String title, String sort)
    {
        this.title = title;
        this.sort = sort;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

    public String getSort()
    {
        return sort;
    }

    public void setSort(String sort)
    {
        this.sort = sort;
    }
}

package com.xinheng.mvp.model.online;

import com.xinheng.mvp.model.BaseEmptyItem;

/**
 * Created by Steven on 2015/8/31 0031.
 */
public class PostOnLineItem  extends BaseEmptyItem
{
    public static final String  DEFAULT_AD_SIZE= "5";
    public static final String DEFAULT_SUBJECT_SIZE="3";
    public static final String DEFAULT_PER__SUBJECT_SIZE="3";
    public static final String DEFAULT_TYPE_SIZE="6";
    public static final String DEFAULT_PER_TYPE_SIZE="6";

    /**
     * 广告数，默认是5
     */
    public String adsize = DEFAULT_AD_SIZE;
    /***
     * 推荐主题记录数， 默认3
     */
    public String  subjectSize = DEFAULT_SUBJECT_SIZE;
    /**
     *每类主题下药材的记录数，默认6
     */
    public String  perSubjectSize = DEFAULT_PER__SUBJECT_SIZE;
    /***
     * 推荐类型记录数， 默认3
     */
    public String typesize = DEFAULT_TYPE_SIZE;
    /**
     *每类推荐类型的记录数，默认6
     */
    public String  pertypesize = DEFAULT_PER_TYPE_SIZE;

    public PostOnLineItem()
    {
    }

    public PostOnLineItem(String adsize, String subjectSize, String perSubjectSize, String typesize, String pertypesize)
    {
        this.adsize = adsize;
        this.subjectSize = subjectSize;
        this.perSubjectSize = perSubjectSize;
        this.typesize = typesize;
        this.pertypesize = pertypesize;
    }
}

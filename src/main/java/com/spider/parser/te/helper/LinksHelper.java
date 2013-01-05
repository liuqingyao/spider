package com.spider.parser.te.helper;

import com.spider.parser.bean.ExtractUrlBean;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-21
 * Time: 下午6:00
 * To change this template use File | Settings | File Templates.
 */
public class LinksHelper {
    public static String links(Set<ExtractUrlBean> urls){
        StringBuffer sb = new StringBuffer();
        for(ExtractUrlBean urlBean:urls){
            sb.append(urlBean.getUrl());
            sb.append("\n");
        }
        return sb.toString();
    }
}

package com.spider.job.helper;

import com.spider.service.ArticleServiceImpl;
import com.spider.utils.StringUtil;
import com.sun.syndication.feed.synd.SyndEntry;
import org.apache.thrift.TException;
import com.spider.parser.bean.ExtractUrlBean;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-11-26
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
public class SpiderUrlUtil {
    private static final int MAX_URL_COUNT = 5;

    public static Set<ExtractUrlBean> urlsForExtract(Set<String> urls) {

        if (urls == null || urls.size() == 0) {
            return new HashSet<ExtractUrlBean>();
        }
        Set<ExtractUrlBean> beans = new HashSet<ExtractUrlBean>();
        for (String url : urls) {
            ExtractUrlBean urlBean = urlToBean(url);
            beans.add(urlBean);
        }

        Set<ExtractUrlBean> noStoredUrls = new HashSet<ExtractUrlBean>();
        Set<ExtractUrlBean> storedUrls = new HashSet<ExtractUrlBean>();

        Iterator<ExtractUrlBean> it = beans.iterator();
        int count = 0;
        while (it.hasNext()) {
            ExtractUrlBean bean = it.next();
            if (!bean.isExtracted()) {
                noStoredUrls.add(bean);
                count++;
                if(count == MAX_URL_COUNT){
                     break;
                }
            } else {
                storedUrls.add(bean);
            }

        }
        int noStoredUrlSize = noStoredUrls.size();
        if (noStoredUrlSize > 0 && noStoredUrlSize < MAX_URL_COUNT) {
            Iterator<ExtractUrlBean> storedIt = storedUrls.iterator();
            while (storedIt.hasNext() && noStoredUrlSize < MAX_URL_COUNT) {
                noStoredUrls.add(storedIt.next());
                noStoredUrlSize++;
            }

        }

        return noStoredUrls;
    }

    public static ExtractUrlBean urlToBean(String url) {
        return new ExtractUrlBean(url, isStored(url));
    }

    public static boolean isStored(String url) {
        try {
            return  ArticleServiceImpl.getInstance().exist(url);
        } catch (TException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static String getLink(SyndEntry syndEntry) {
        String link = syndEntry.getLink();
        if (!StringUtil.notNullAndNotEmpty(link)) {
            link = syndEntry.getUri();
        }
        link = jSessionIdFilter(link);
        return link != null ? link.trim() : "";
    }

    public static String getAbsPicUrl(String pic, String link) {
        if (!pic.startsWith("http") && link.indexOf("//") > -1 && link.indexOf("/", link.indexOf("//") + 2) > -1) {
            link = link.substring(0, link.indexOf("/", link.indexOf("//") + 2));
            pic = link + ((pic.startsWith("/")) ? pic : ("/" + pic));
        }
        return pic;
    }

    private static final Pattern p = Pattern.compile("([;]jsessionid=.*？)", Pattern.CASE_INSENSITIVE);

    public static String jSessionIdFilter(String url) {
        if (!StringUtil.notNullAndNotEmpty(url)) return "";

        Matcher m = p.matcher(url);
        String jSessionIdValue = "";
        if (m.find()) {
            jSessionIdValue = m.group(1);
        }
        return url.replace(jSessionIdValue, "");
    }


}

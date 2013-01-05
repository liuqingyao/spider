package com.spider.parser.te;

import com.spider.bean.Fetch;
import com.spider.bean.SourceVO;
import com.spider.reporter.Reporter;
import org.apache.commons.lang.Validate;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-11-23
 * Time: 上午11:36
 * To change this template use File | Settings | File Templates.
 */
public class HtmlExtract {

    public static Set<String> getUrls(SourceVO sourceVO,Fetch fetch) throws Exception {
        String fetchInfo = sourceVO.getFetchInfo();
        JSONObject object = null;
        Elements body = null;
        String reg = null;
        String url = "";
        try {
            object = new JSONObject(fetchInfo.replaceAll("\\\\","\\\\\\\\"));
            String xpath = object.getString("xpath");
            reg = object.getString("reg");
            url = object.getString("url");
            Document doc = null;
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1").timeout(20000).get();
            try{
                body = doc.select(xpath);
            }catch (Selector.SelectorParseException e){
                body = doc.select("body");
            }

            Validate.notNull(body);
            Validate.notEmpty(body);
        }  catch (IOException e) {
            Reporter.info(Reporter.NETWORK_ERROR, "source_name:" + url + ",source_id:" + sourceVO.getId()+",msg:"+e.getMessage());
            fetch.setFetchError(Reporter.NETWORK_ERROR);
            throw e;
        } catch (Exception e){
            Reporter.info(Reporter.HTML_FORMAT_ERROR, "source_url:" + sourceVO.getSourceUrl() + ",source_id:" + sourceVO.getId()+",msg:"+e.getMessage());
            fetch.setFetchError(Reporter.HTML_FORMAT_ERROR);
            throw e;
        }finally {

        }

        Set<String> urls = new HashSet<String>();
        if(body == null){
            System.out.println("body == null");
             return urls;
        }

        reg=reg.replaceAll("\\\\\\\\+","\\\\");

        Elements links = body.select("a[href]");
        for (Element link : links) {
            String href = link.attr("abs:href");
            if (href.matches(reg)) {
                urls.add(href);
            }
        }


        fetch.setFetchError("");
       return urls;
    }

}

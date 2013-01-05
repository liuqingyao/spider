package com.spider.parser.te;

import com.spider.bean.Fetch;
import com.spider.bean.SourceVO;
import com.spider.download.DownLoadUtil;
import com.spider.job.helper.SpiderUrlUtil;
import com.spider.reporter.Reporter;
import com.spider.utils.DataStreamUtil;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-11-23
 * Time: 上午11:36
 * To change this template use File | Settings | File Templates.
 */
public class RssExtract {
    private static Logger logger = Logger.getLogger(RssExtract.class);

    public static Set<String> getUrls(SourceVO rssSource, Fetch fetch) throws Exception {

        Set<String> urls = new HashSet<String>();


        InputStream inputStream = null;
        SyndFeed feed = null;
        String url = "";
        try {
            url = getFetchUrl(rssSource);
            String content = DownLoadUtil.html(url);
            inputStream = DataStreamUtil.bytesToInputStream(content.getBytes());
            SyndFeedInput input = new SyndFeedInput();
            feed = input.build(new XmlReader(inputStream));
        } catch (IOException e) {
            Reporter.info(Reporter.NETWORK_ERROR, "source_url:" + url + ",source_id:" + rssSource.getId() + ",msg:" + e.getMessage());
            fetch.setFetchError(Reporter.NETWORK_ERROR);
            throw e;
        } catch (Exception e) {
            Reporter.info(Reporter.RSS_FORMAT_ERROR, "source_url:" + url + ",source_id:" + rssSource.getId() + ",msg:" + e.getMessage());
            fetch.setFetchError(Reporter.RSS_FORMAT_ERROR);
            throw e;
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }

        if (null != feed) {
            List<SyndEntry> syndEntryList = feed.getEntries();
            if (null != syndEntryList) {
                for (SyndEntry syndEntry : syndEntryList) {
                    urls.add(SpiderUrlUtil.getLink(syndEntry));
                }
            }
        }
        fetch.setFetchError("");
        return urls;
    }

    private static String getFetchUrl(SourceVO rssSource) {
        String fetchInfo = rssSource.getFetchInfo();
        JSONObject object = null;
        String url = null;
        try {
            object = new JSONObject(fetchInfo);
            url = object.getString("url");
            // String url = "http://sports.ifeng.com/pinglun/list_0/0.shtml";
        } catch (JSONException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return url;
    }


}

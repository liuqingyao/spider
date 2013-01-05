package com.spider.service.util;

import com.spider.bean.SourceVO;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-11-21
 * Time: 下午12:06
 * To change this template use File | Settings | File Templates.
 */
public class SourceUtil {
    public static String sourceToString(SourceVO sourceVO) {
        JSONObject obj = new JSONObject(sourceVO);
        return obj.toString();
    }

    public static SourceVO stringToSource(String json) {
        SourceVO sourceVO = null;
        try {
            JSONObject obj = new JSONObject(json);

            sourceVO = new SourceVO();
            sourceVO.setId(obj.getLong("id"));
            sourceVO.setChannelId(obj.getLong("channelId"));
            sourceVO.setName(obj.getString("name"));
            sourceVO.setIntro(obj.getString("intro"));
            sourceVO.setLevel(obj.getInt("level"));
            sourceVO.setIconInfo(obj.getString("iconInfo"));
            sourceVO.setSourceUrl(obj.getString("sourceUrl"));

//            sourceVO.setFetchStatus(obj.getInt("fetchStatus"));
            sourceVO.setFetchType(obj.getInt("fetchType"));
            sourceVO.setFetchInfo(obj.getString("fetchInfo"));
            sourceVO.setFetchFrequence(obj.getLong("fetchFrequence"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return sourceVO;

    }
}

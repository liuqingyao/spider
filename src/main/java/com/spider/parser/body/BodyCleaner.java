package com.spider.parser.body;

import com.spider.utils.HTMLDecoder;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-9-28
 * Time: 上午9:46
 * To change this template use File | Settings | File Templates.
 */
public class BodyCleaner {


    /**
     * 文章去噪处理
     */
    public static String getCleanContent(String content) {

        //预处理
        content = HTMLDecoder.decode(content);//html转义decode
        content = cleanChSpace(content);  //清除特殊空格，中文，全角中文
        content = cleanNewLine(content);  //保证一些换行不影响正则匹配

        //清除不要的代码
        content = cleanComment(content);
        content = cleanCss(content);
        content = cleanScript(content);

        //文本转换
        content = someTagToNewLine(content);
        content = cleanAllTag(content);

        //板式调优
        content = cleanRedundancyNewLine(content);
        content = cleanHeadAndEndingBlank(content);

        return content.trim();
    }

    /**
     * 清除段落结尾的空白字符
     */
    public static String cleanHeadAndEndingBlank(String s){
       return s.replaceAll("\\s+$","").replaceAll("^\\s+","");
    }
    /*
    * 去掉中文空格
    * */
    public static String cleanChSpace(String content) {
        content = content.replaceAll("\u00A0"," "); //中文全角空格
        return content.replaceAll("\u3000", " ");
    }

    /**
     * 去掉其它的<>之间的东西
     */
    public static String cleanAllTag(String content) {
        content = content.replaceAll("(?is)[\f\r\t ]*?</?[^\\u4e00-\\u9fa5]*?[\\w-]+((\\s+[\\w-]+(\\s*=\\s*(?:\".*?\"|\\'.*?\\'|[^\\'\">\\s]+))?)+\\s*|\\s*)/?>", "");
        return content;
//        return content.replaceAll("<[^>]*>", "");
    }



    /**
     * p div 标签转化为换行*
     * 修改：加大小写问题
     */
    public static String someTagToNewLine(String content) {
        return content.replaceAll("</(div|p|DIV|P|li|LI|h\\d{1}|H\\d{1})[^>]*?>", "\n").replaceAll("<br\\s*/?>", "\n");
    }

    /**
     * 清除html注释
     */
    public static String cleanComment(String content) {
        content = content.replaceAll("(?is)[\f\r\t ]*?<!--.*?-->[\f\r\t ]*?", "");
        content = content.replaceAll("(?is)[\f\r\t ]*?<#--.*?-->[\f\r\t ]*?","");
        content = content.replaceAll("(?is)[\f\r\t ]*?<!--[if !IE]>.*?<![endif]-->[\f\r\t ]*?","");
        content = content.replaceAll("(?is)[\f\r\t ]*?<!\\s*/?\\s*[\\u4e00-\\u9fa5\\w]*\\s*/?\\s*>[\f\r\t ]*?","");
        return content;
    }

    /**
     * 清除html CSS 样式
     */
    public static String cleanCss(String content) {
        return content.replaceAll("(?is)<style.*?>.*?</style>", ""); // remove css
    }

    /**
     * 清除html script 脚本
     */
    public static String cleanScript(String content) {
        return content.replaceAll("(?is)<script.*?>.*?</script>", ""); // remove javascript
    }

    /**
     * 清除html换行符
     */
    public static String cleanNewLine(String content) {
        return content.replace("\n", "");
    }

    /**
     * 清除处理后正文中的冗余换行
     * 1：如果已有换行 加上了标签换行 有冗余换行
     * 2：多个标签连续出现被转化为换行
     */
    public static String cleanRedundancyNewLine(String content) {
        return content.replaceAll("\\s*\\n\\s*", "\n");
    }

}

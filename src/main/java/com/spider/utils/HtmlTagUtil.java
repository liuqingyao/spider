package com.spider.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-7-29
 * Time: 下午9:49
 * To change this template use File | Settings | File Templates.
 */
public class HtmlTagUtil {
    private static final Pattern htmlTagPat = Pattern.compile("<[^>]+>");

    public static String removeAllTags(String input) {
        if (input == null || input.trim().length() == 0) {
            return input;
        }
        return htmlTagPat.matcher(input).replaceAll("");
    }

    public static final Pattern imgTagPat = Pattern.compile("<img\\s+(?:[^>]*)src\\s*=\\s*(?:\"|')([^(\"|')]+)(?:\"|')", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    public static List extractPic(final String input) {
        if (input == null || input.trim().length() == 0) {
            return null;
        }
        List<String> list = new ArrayList<String>();

        Matcher m = imgTagPat.matcher(input);
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    private static final Pattern anchorTagPat = Pattern.compile("<a\\s+(?:[^>]*)href\\s*=\\s*(?:\"|')([^(\"|')]+)(?:\"|')", Pattern.CASE_INSENSITIVE);

    public static String extractAnchor(String input) {
        if (input == null || input.trim().length() == 0) {
            return input;
        }
        Matcher m = anchorTagPat.matcher(input);
        if (m.find()) {
            return m.group(1);
        }
        return input;
    }

    public static String unescape(final String input) {
        return input.replace("&lt;", "<").replace("&gt;", ">");
    }


    public static void main(String[] args) {
//        String html = "<p style=\"text-align: center;\"><a href=\"http://www.36kr.com/p/149115.html/samsung-keyboards-pen\" rel=\"attachment wp-att-149126\"><img class=\"aligncenter  wp-image-149126\" title=\"samsung keyboard&amp;S pen\" src=\"http://img05.36krcnd.com/wp-content/uploads/2012/08/samsung-keyboardS-pen.jpg\" alt=\"\" width=\"671\" height=\"238\" />";
        String html="<p>目前尚不知晓发贴内容是否属实。但把加班当敬业，视准时下班为不敬业的事例却时常有闻。把加班加点当作优秀标兵、模范事迹的报道更是层出不穷。在我们这个国度，不看业绩看表象的管理者真还大有人在。</p>\n" +
                "<p>就说我本人的工作经历吧，无不体现了“偏见”在我们国家多么常见。</p>\n<br>" +
                "<p>我当教师时，不给学生留课外作业，被校长说我不敬业，结果却是我的学生中考平均分全基地第一。</p>\n<br/>" +
                "<p>我到合资企业后，也是<br />按时上下班，被同事向外方老板告状，说我下班搞副业，当我离职三年后，老板说张先生任职时，企业的管理费用是最低的。</p>\n" +
                "<!-- asdfasdfasdfaf ->" +
                "<script language=\"javascript\">1231312adsfasdf</script>" +
                "<style>.css{" +
                "asdfafasd:123," +
                "}</style>" +
                "<p>当我在私营企业任职后，我多次拒绝参加老板晚上的公关活动，被老板说我不够投入，有趣的是我在企业主政时，企业的产量质量都是最高最好的，安全事故是最低的，管理水平亦是最高的，当我与企业分手两年后，曾经辉煌十多年的企业竟然倒闭了。</p>\n" +
                "<p>我在分智网做企业咨询时，有家企业的高管几乎都不休假，我问他们为什么不休息？他们说怕老板骂不敬业。天天上班就是敬业吗？老板也多次出台规定要求高管们休假。结果谁休假谁倒霉奖金必然被扣，因为有人根本就不休假！在干部大会上，老板还另外给不休假的干部增加额外奖励！<span id=\"more-1400\"></span></p>\n" +
                "<p>我问他们天天上班有事做吗？他们说，坦率地讲真没有那么多事做，但老板看不到你上班他心里就不舒服。我再问你们家里真的一点私事都不需要处理吗？被我问急了，有人竟然当我面哭了：谁家没点特殊事情，为从不休假的事她和老公都差点闹离婚。</p>\n" +
                "<p>什么加班加点是敬业精神、企业文化。说不好听点，就是有的老板心理变态。他以企业为家，就要求员工也以企业为家。员工如果真以企业为家，我看他准受不了。员工说企业就是我的家，家是没有原则的，我家里的物品想拿就拿，想送人就送人，我的地盘我做主，我看他还敢让员工以企业为家？</p>\n" +
                "<p>当然，我们&nbsp;也不能说加班加点就一定不好。必要时加班加点，特殊任务加班加点在所难免。但如果员工加班加点成了家常便饭，就说明企业管理有问题，不是效率低下，就是用人不当。</p>\n" +
                "<p>还有&nbsp;一种情况，有的员工能力突出，上班时间完成本职工作绰绰有余，你让他下班后加班加点干什么？我曾在一个知名企业工作半年。我的前任是华东一著名学府的毕业生，因为工作干不完而辞职。可是我去了之后，不到半个月，工作理清头绪后，我每天的例行工作不到二个小时就做完了。半年之后我不得不因“闲”辞职。</p>\n" +
                "<p>每天&nbsp;都保质保量完成工作，没有出现任何纰漏、没有生产事故，按时上下班，这样的员工应是好员工，说明他的精神状态好、工作能力强，也说明他的工作效率高。</p>\n" +
                "<p>谁说加班就是敬业呢？为什么不在上班时间内把工作全部做完呢？加班要占用自己休息时间，浪费企业水电，加班就意味着推迟完成任务的时间，增加顾客等待时间。上班时间不能按时完成工作，这有什么好的呢？</p>\n" +
                "<p>判断一个员工敬业与否的标准显然不能看其是否加班，而是看他在同一时间内与同事相比为企业创造的价值大小。不加班比同事创造的价值大，就是好员工，就是敬业表现。加班后比同事创造的价值大，也是敬业的表现。加班还比不上不加班同事创造的价值大，那就纯属“磨洋工”、纯属做表面功夫。</p><div style=\"padding-bottom:20px;\">From:<a href=\"http://www.fenzhi.com\">分智</a></div>\n";

//        System.out.println("pic:" + extractPic(html));
//        System.out.println("a:" + extractAnchor(html));
//        System.out.println(removeAllTags(html));
//        System.out.println(stripHtml(html));

    }

}

package com.spider.utils;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-7-26
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 */
public class TimeProfileUtil {
    private  long start = 0L;
    private  long end = 0L;
    
    private static String startTip = "从开始";
    private static String endTip = "到 结束";

    public  String getStartTip() {
        return startTip;
    }

    private  void setStartTip(String startTip) {
        TimeProfileUtil.startTip = startTip;
    }

    public  String getEndTip() {
        return endTip;
    }

    private  void setEndTip(String endTip) {
        TimeProfileUtil.endTip = endTip;
    }

    private  long getStart() {
        return start;
    }

    public  void start() {
        this.start = System.currentTimeMillis();
    }

    public  void start(String tip) {
        start();
        setStartTip(tip);
    }

    private  long getEnd() {
        return end;
    }

    public  void end() {
        this.end = System.currentTimeMillis();
    }
    
    public  long consume(){
        return System.currentTimeMillis() - start;
    }
    public  void end(String tip) {
        this.end = System.currentTimeMillis();
        setEndTip(tip);
    }

    public  void endAndPrintResult(){
        end();
        profileResult();
    }
    public  void endAndPrintResult(String tip){
        end(tip);
        profileResult();
    }

    public  void profileResult(){
        System.out.println();
        System.out.println(getStartTip()+" "+getEndTip()+" 共用时："+(getEnd()-getStart())+" 毫秒");
    }
}

package com.spider.job;

import com.spider.constants.Config;
import com.spider.constants.Path;
import com.spider.job.queue.SourceQueueUpdate;
import com.spider.reporter.Reporter;
import com.spider.utils.StringUtil;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhouwang
 * Date: 12-9-19
 * Time: 下午4:01
 * To change this template use File | Settings | File Templates.
 */

public class SourceJob {


    private static final Logger logger = Logger.getLogger(SourceJob.class);

    public static final int MAX_THREAD = Runtime.getRuntime().availableProcessors();

    private static final SourceJobTask[] JOBS = new SourceJobTask[MAX_THREAD];

    private static boolean started = false;

    static {
        for(int i=0;i<MAX_THREAD;i++){
            JOBS[i] = new SourceJobTask(i);
        }
    }

    public synchronized  void startSourceJob() {

        if(started)return;

        Config.init();
        Reporter.debug(Reporter.DEBUG_START_INFO,"start source job :init success");

        for (int i=0;i<MAX_THREAD;i++) {
            JOBS[i].start();
        }

        Reporter.debug(Reporter.DEBUG_START_INFO,"start source job :thread-s start success");
        Reporter.debug(Reporter.DEBUG_START_INFO,"evn update_queue:"+Path.getPath("UPDATE_QUEUE"));

        if("1".equals(StringUtil.ensureNotEmptyAndNotNull(Path.getPath("UPDATE_QUEUE"), "0"))){
            SourceQueueUpdate.getInstance().start();
            Reporter.debug(Reporter.DEBUG_START_INFO,"start update_queue:success");
        }else{
            Reporter.debug(Reporter.DEBUG_START_INFO,"start update_queue:fail");
        }

        started = true;
        Reporter.debug(Reporter.DEBUG_START_INFO,"start source job : all done.");
        Reporter.debug(Reporter.DEBUG_START_INFO,"**********************************");
    }


    //singleton
    private static SourceJob job = new SourceJob();
    private SourceJob(){}
    public static SourceJob getInstance() {
        return job;
    }
}




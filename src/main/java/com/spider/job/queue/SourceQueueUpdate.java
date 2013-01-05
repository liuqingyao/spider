package com.spider.job.queue;

import com.spider.reporter.MonitorCacheKey;
import com.spider.reporter.Profile;
import com.spider.reporter.Reporter;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-11-1
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */
public class SourceQueueUpdate extends Thread {
    private static Logger logger = Logger.getLogger(SourceQueueUpdate.class);
    private static boolean started = false;

    public synchronized void run() {

        if (started) {
            return;
        }
        started = true;
        Profile importantProfile = null;
        Profile unImportantProfile = null;

        Reporter.debug(Reporter.DEBUG_START_INFO,"source update queue startup.");
        while (true) {
            try {
                if (SourceQueue.getInstance().sizeOfImportantSource() < 10) {
                    if (importantProfile != null) {
                        importantProfile.end();
                    }
                    SourceQueueHelper.freshSourceQueue();    //同步问题考虑
                    importantProfile = Profile.getProfileOf(MonitorCacheKey.SOURCE_TRAVERSAL_5_CONSUME);
                }

                if (SourceQueue.getInstance().sizeOfUnimportantSource() < 10) {
                    if (unImportantProfile != null) {
                        unImportantProfile.end();
                    }
                    SourceQueueHelper.freshUnimportantSourceQueue();
                    Profile.getProfileOf(MonitorCacheKey.SOURCE_TRAVERSAL_LESS_5_CONSUME);
                }
            } catch (Throwable e) {
                logger.error(e.getMessage());
            } finally {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }

        }
    }

    private SourceQueueUpdate(){}
    private static SourceQueueUpdate instance = new SourceQueueUpdate();
    public static SourceQueueUpdate getInstance(){
        return instance;
    }
}

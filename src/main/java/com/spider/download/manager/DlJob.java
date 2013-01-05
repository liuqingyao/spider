package com.spider.download.manager;

import com.spider.constants.Config;
import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-26
 * Time: 下午6:26
 * To change this template use File | Settings | File Templates.
 */
public class DlJob {


    private static final Logger logger = Logger.getLogger(DlJob.class);

    private static final int MAX_THREAD = Integer.valueOf(Config.getValue("dl_max_thread",Runtime.getRuntime().availableProcessors()+""));

    private ExecutorService executorService = null;

    private static final int TIME_OUT = Integer.valueOf(Config.getValue("job_time_out",10 * 60+""));//单位秒

    public void startJob() throws Exception {

        try{
            executorService = Executors.newFixedThreadPool(MAX_THREAD);
        }catch (Exception e){
            logger.error("DL_JOB start error:"+e.getMessage());
            throw e;
        }

        while (true) {
            FutureTask futureTask = null;
            try {
                futureTask = new FutureTask(new DlJobTask());
                executorService.execute(futureTask);
                futureTask.get(TIME_OUT, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            } catch (TimeoutException e) {
            } finally {
                if(futureTask!=null){
                    futureTask.cancel(true);
                    futureTask = null;
                }
            }

        }
    }


    //singleton
    private static DlJob job = new DlJob();
    private DlJob(){}
    public static DlJob  getInstance() {
        return job;
    }
}

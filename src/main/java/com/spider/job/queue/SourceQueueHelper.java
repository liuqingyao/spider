package com.spider.job.queue;

import com.spider.bean.SourceVO;
import com.spider.service.SourceServiceImpl;
import org.apache.thrift.TException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-10-31
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
public class SourceQueueHelper {

    private static SourceQueue sourceQueue = SourceQueue.getInstance();
    private static SourceServiceImpl sourceService = SourceServiceImpl.getInstance();

    //更新redis队列中的内容
    public static long freshSourceQueue() {
        List<SourceVO> sourceList = new java.util.ArrayList<SourceVO>();
        List<SourceVO> tmpList = new java.util.ArrayList<SourceVO>();
        int page = 1;
        try {
            tmpList = sourceService.getPageSearchSources(-99, -99, -99, "", page, 100, 0, -99);
            while (tmpList != null && tmpList.size() > 0) {

                sourceList.addAll(tmpList);
                tmpList.clear();

                tmpList = SourceServiceImpl.getInstance().getPageSearchSources(-99, -99, -99, "", ++page, 100, 0, -99);

            }
            long freshImportantCount = freshImportantSourceQueue(sourceList);
            if (freshImportantCount <= 0) {
                freshUnimportantSourceQueue(sourceList);
            }

        } catch (TException e) {
            e.printStackTrace();
        }

        return sourceQueue.sizeOfImportantSource() + sourceQueue.sizeOfUnimportantSource();
    }

    public static long freshUnimportantSourceQueue() {
        List<SourceVO> sourceList = new java.util.ArrayList<SourceVO>();
        List<SourceVO> tmpList = new java.util.ArrayList<SourceVO>();
        int page = 1;
        try {
            tmpList = sourceService.getPageSearchSources(-99, -99, -99, "", page, 100, 0, -99);
            while (tmpList != null && tmpList.size() > 0) {

                sourceList.addAll(tmpList);
                tmpList.clear();

                tmpList = SourceServiceImpl.getInstance().getPageSearchSources(-99, -99, -99, "", ++page, 100, 0, -99);

            }
            freshUnimportantSourceQueue(sourceList);

        } catch (TException e) {
            e.printStackTrace();
        }

        return sourceQueue.sizeOfUnimportantSource();
    }

    private static long freshImportantSourceQueue(List<SourceVO> sourceList) {
        if (sourceQueue.sizeOfImportantSource() < 10) {
            for (SourceVO source : sourceList) {
                long time = System.currentTimeMillis();

                if (source.getFetchStatus() == -1) {
                    continue;
                }

                if (time - source.getLastFetchAt() > source.getFetchFrequence()) {
                    source.setLastFetchAt(time);
                    if (source.getLevel() >= 4) {
                        sourceQueue.offerImportantSource(source);
                    }
                }
            }
        }
        return sourceQueue.sizeOfImportantSource();
    }

    private static long freshUnimportantSourceQueue(List<SourceVO> sourceList) {
        if (sourceQueue.sizeOfUnimportantSource() < 10) {
            for (SourceVO source : sourceList) {
                long time = System.currentTimeMillis();

                if (source.getFetchStatus() == -1) {
                    continue;
                }

                if (time - source.getLastFetchAt() > source.getFetchFrequence()) {
                    source.setLastFetchAt(time);
                    if (source.getLevel() == 3) {
                        sourceQueue.offerUnimportantSource(source);
                    }

                }
            }
        }
        return sourceQueue.sizeOfUnimportantSource();
    }
}

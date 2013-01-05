package com.spider.utils;

import org.mozilla.intl.chardet.HtmlCharsetDetector;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

/**
 * Created by IntelliJ IDEA.
 * User: liuqingyao
 * Date: 12-12-21
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
public class CharsetUtil {

    public static final String DEFAULT_CHARSET = null;
    static final int LENGTH = 1024;

    public static boolean isAscii(byte[] aBuf, int aLen) {

        for (int i = 0; i < aLen; i++) {
            if ((0x0080 & aBuf[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    public static String charset(byte[] stream) {
        final String[] cs = {DEFAULT_CHARSET};
        nsDetector det = new nsDetector();

        // Set an observer...
        // The Notify() will be called when a matching charset is found.

        det.Init(new nsICharsetDetectionObserver() {
            public void Notify(String charset) {
                HtmlCharsetDetector.found = true;
                cs[0] = charset;
            }
        });

        byte[] buf = new byte[LENGTH];
        int len = stream.length , loop = 0;
        boolean done = false;
        boolean isAscii = true;

        for(;len>0;len-=LENGTH){
            buf = new byte[LENGTH];
            if(len<LENGTH){
                System.arraycopy(stream, loop * LENGTH,buf,0,len);
            }else{
                System.arraycopy(stream, loop * LENGTH,buf,0,LENGTH);
            }
            loop++;

            // Check if the stream is only ascii.
            if (isAscii)
                isAscii = det.isAscii(buf, buf.length);
            // DoIt if non-ascii and not done yet.
            if (!isAscii && !done)
                done = det.DoIt(buf, buf.length, false);
        }

        det.DataEnd();

        return cs[0];
    }



}

package com.spider.download.helper;

import com.spider.utils.CharsetUtil;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Internal static utilities for handling data.
 */
public class DataUtilEx {
    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*\"?([^\\s;\"]*)");
    public static final String defaultCharset = "UTF-8"; // used if not found in header or meta charset
    private static final int bufferSize = 0x20000; // ~130K.

    private DataUtilEx() {
    }

    /**
     * Loads a file to a Document.
     *
     * @param in          file to load
     * @param charsetName character set of input
     * @param baseUri     base URI of document, to resolve relative links against
     * @return Document
     * @throws java.io.IOException on IO error
     */
    public static Document load(File in, String charsetName, String baseUri) throws IOException {
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(in);
            ByteBuffer byteData = readToByteBuffer(inStream);
            return parseByteData(byteData, charsetName, baseUri, Parser.htmlParser());
        } finally {
            if (inStream != null)
                inStream.close();
        }
    }

    /**
     * Parses a Document from an input steam.
     *
     * @param in          input stream to parse. You will need to close it.
     * @param charsetName character set of input
     * @param baseUri     base URI of document, to resolve relative links against
     * @return Document
     * @throws java.io.IOException on IO error
     */
    public static Document load(InputStream in, String charsetName, String baseUri) throws IOException {
        ByteBuffer byteData = readToByteBuffer(in);
        return parseByteData(byteData, charsetName, baseUri, Parser.htmlParser());
    }

    /**
     * Parses a Document from an input steam, using the provided Parser.
     *
     * @param in          input stream to parse. You will need to close it.
     * @param charsetName character set of input
     * @param baseUri     base URI of document, to resolve relative links against
     * @param parser      alternate {@link org.jsoup.parser.Parser#xmlParser() parser} to use.
     * @return Document
     * @throws java.io.IOException on IO error
     */
    public static Document load(InputStream in, String charsetName, String baseUri, Parser parser) throws IOException {
        ByteBuffer byteData = readToByteBuffer(in);
        return parseByteData(byteData, charsetName, baseUri, parser);
    }

    // reads bytes first into a buffer, then decodes with the appropriate charset. done this way to support
    // switching the chartset midstream when a meta http-equiv tag defines the charset.
    public static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
        String docData;
        Document doc = null;
        if (charsetName == null) { // determine from meta. safe parse as UTF-8
            // look for <meta http-equiv="Content-Type" content="text/html;charset=gb2312"> or HTML5 <meta charset="gb2312">
            docData = Charset.forName(defaultCharset).decode(byteData).toString();
            doc = parser.parseInput(docData, baseUri);
            Element meta = doc.select("meta[http-equiv=content-type], meta[charset]").first();
            if (meta != null) { // if not found, will keep utf-8 as best attempt
                String foundCharset = meta.hasAttr("http-equiv") ? getCharsetFromContentType(meta.attr("content")) : meta.attr("charset");
                if (foundCharset != null && foundCharset.length() != 0 && !foundCharset.equals(defaultCharset)) { // need to re-decode
                    charsetName = foundCharset;
                    byteData.rewind();
                    docData = Charset.forName(foundCharset).decode(byteData).toString();
                    doc = null;
                }
            }
        } else { // specified by content type header (or by user on file load)
            Validate.notEmpty(charsetName, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
            docData = Charset.forName(charsetName).decode(byteData).toString();
        }
        if (doc == null) {
            // there are times where there is a spurious byte-order-mark at the start of the text. Shouldn't be present
            // in utf-8. If after decoding, there is a BOM, strip it; otherwise will cause the parser to go straight
            // into head mode
            if (docData.length() > 0 && docData.charAt(0) == 65279)
                docData = docData.substring(1);

            doc = parser.parseInput(docData, baseUri);
            doc.outputSettings().charset(charsetName);
        }
        return doc;
    }

    public static ByteBuffer readToByteBuffer(InputStream inStream) throws IOException {
        byte[] buffer = new byte[bufferSize];
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(bufferSize);
        int read;
        while (true) {
            read = inStream.read(buffer);
            if (read == -1) break;
            outStream.write(buffer, 0, read);
        }
        ByteBuffer byteData = ByteBuffer.wrap(outStream.toByteArray());
        return byteData;
    }


    //通过多种情况判断当前网页编码
    public static String getCharset(String contentType, ByteBuffer byteData) {
        String charsetName = getCharsetFromContentType(contentType);
        if (charsetName != null) {
            return encodeCorrect(charsetName);
        }
        charsetName = getCharsetFromMeta(byteData);
        if (charsetName != null) {
            return encodeCorrect(charsetName);
        }
        charsetName = jCharset(byteData.array());//jCharset
        return encodeCorrect(charsetName);
    }

    /**
     * Parse out a charset from a content type header. If the charset is not supported, returns null (so the default
     * will kick in.)
     *
     * @param contentType e.g. "text/html; charset=EUC-JP"
     * @return "EUC-JP", or null if not found. Charset is trimmed and uppercased.
     */
    private static String getCharsetFromContentType(String contentType) {
        if (contentType == null) return null;
        Matcher m = charsetPattern.matcher(contentType);
        if (m.find()) {
            String charset = m.group(1).trim();
            if (charset == null || charset.trim().length() == 0) return null;
            return charset.toLowerCase();
        }
        return null;
    }
    private static String getCharsetFromMeta(ByteBuffer byteData) {
        String docData;
        String charsetName = null;
        try {
            Document doc = null;
            // look for <meta http-equiv="Content-Type" content="text/html;charset=gb2312"> or HTML5 <meta charset="gb2312">
            docData = Charset.forName(defaultCharset).decode(byteData).toString();
            doc = Jsoup.parse(docData);
            Elements els = doc.select("meta[http-equiv=content-type], meta[charset]");
            for (Element meta : els) {
                String foundCharset = meta.hasAttr("http-equiv") ? getCharsetFromContentType(meta.attr("content")) : meta.attr("charset");
                if (foundCharset != null && foundCharset.length() != 0 && !foundCharset.equals(defaultCharset) && Charset.isSupported(foundCharset)) { // need to re-decode
                    charsetName = foundCharset;
                    break;
                }
            }
        } finally {
            byteData.rewind();
        }
        return charsetName;
    }

    private static String jCharset(byte stream[]) {
        return CharsetUtil.charset(stream);
    }


    private static String encodeCorrect(String charset) {
        if (charset == null || charset.trim().length() <= 0) return defaultCharset;
        String charsetLower = charset.toLowerCase();
        if ("gb2312".equals(charsetLower)
                || "gb18030".equals(charsetLower)
                || "gb-2312".equals(charsetLower)
                || "gb-18030".equals(charsetLower)
                || charsetLower.contains("gbk"))
            return "GBK";
        if ("utf8".equals(charsetLower) || "utf-8".equals(charsetLower)) {
            return "UTF-8";
        }
        if (Charset.isSupported(charset)) return charset;
            charset = charset.toUpperCase(Locale.ENGLISH);
        if (Charset.isSupported(charset)) return charset;
        return defaultCharset;
    }


}

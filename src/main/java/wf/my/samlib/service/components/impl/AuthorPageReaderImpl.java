package wf.my.samlib.service.components.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wf.my.samlib.error.PageReadException;
import wf.my.samlib.service.components.AuthorPageReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorPageReaderImpl implements AuthorPageReader {

    private static  final Logger logger = LoggerFactory.getLogger(AuthorPageReaderImpl.class);
    public static final String DEFAULT_ENCODING = "windows-1251";
    private static final Pattern charsetPattern = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");


    @Override
    public String readPage(String url) {
        logger.debug("read page [{}]", url);
        String pageString;
        try {
            pageString = readPageByLink(url);
        } catch (IOException e) {
            logger.error("can not read link {}" , url);
            throw new PageReadException(e, url);
        }
        logger.debug("downloaded {} symbols", pageString.length());
        return pageString;
    }

    private String readPageByLink(String link) throws IOException {
        logger.debug("read by link [{}]", link);
        URL url = new URL(link);
        URLConnection con = url.openConnection();
        Reader r = new InputStreamReader(con.getInputStream(), getCharset(con.getContentType(), DEFAULT_ENCODING));
        StringBuilder buf = new StringBuilder();
        while (true) {
            int ch = r.read();
            if (ch < 0)
                break;
            buf.append((char) ch);
        }

        return buf.toString();
    }

    protected static String getCharset(String contentType, String encoding){
        if(null == contentType){
            return encoding;
        }
        Matcher m = charsetPattern.matcher(contentType);
        return m.matches() ? m.group(1) : encoding;
    }

}

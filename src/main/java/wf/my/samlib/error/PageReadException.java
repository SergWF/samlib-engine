package wf.my.samlib.error;

import java.io.IOException;

public class PageReadException extends BaseSamlibException {

    private String link;


    public PageReadException(IOException e, String link) {
        super("Problems with link " + link);
    }

    public String getLink() {
        return link;
    }


}

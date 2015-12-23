package wf.my.samlib.entity;

import java.util.Date;
import java.util.List;

public interface Author {
    String getUrl();
    Date getUpdateDate();
    List<Writing> getWritings();
    Date getLastChangedDate();
}

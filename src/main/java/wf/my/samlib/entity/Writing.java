package wf.my.samlib.entity;

import java.util.Date;

public interface Writing {
    String getUrl();

    String getName();

    Date getUpdateDate();
    void setUpdatedDate(Date date);

    String getSize();

    String getDescription();
}

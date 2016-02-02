package wf.my.samlib.entity;

import java.util.Date;

/*
* Keeps old param value(s) that were changed by update
*
* */
public class WritingHistoryItem implements UpdateDateComparable {

    private Date updateDate;
    private String name;
    private Integer size;
    private String description;


    @Override
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

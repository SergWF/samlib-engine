package wf.my.samlib.service.impl.tools;

import wf.my.samlib.entity.Writing;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class WritingChanges {
    public enum ChangeItem {
        NAME,DESCRIPTION,SIZE
    }
    private Set<ItemChanging> changes = new HashSet<>();
    private Date checkDate;
    private Date prevChangeDate;
    private Writing writing;

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Set<ItemChanging> getChanges() {
        return changes;
    }

    public Writing getWriting() {
        return writing;
    }

    public void setWriting(Writing writing) {
        this.writing = writing;
    }

    public Date getPrevChangeDate() {
        return prevChangeDate;
    }

    public void setPrevChangeDate(Date prevChangeDate) {
        this.prevChangeDate = prevChangeDate;
    }

    public boolean isChanged(){
        return !changes.isEmpty();
    }
}

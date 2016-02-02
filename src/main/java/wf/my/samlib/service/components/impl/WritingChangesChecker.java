package wf.my.samlib.service.components.impl;

import wf.my.samlib.entity.Writing;
import wf.my.samlib.entity.WritingHistoryItem;

import java.util.Date;

public class WritingChangesChecker {
    public WritingHistoryItem getChanges(Writing newWriting, Writing oldWriting, Date checkDate){
        WritingHistoryItem item = new WritingHistoryItem();
        item.setUpdateDate(checkDate);
        if(null != oldWriting){
            item.setName(getNameDiff(newWriting, oldWriting));
            item.setDescription(getDescriptionDiff(newWriting, oldWriting));
            item.setSize(getSizeDiff(newWriting, oldWriting));
        }
        return hasChanges(item) ? item: null;
    }

    private Integer getSizeDiff(Writing newWriting, Writing oldWriting) {
        return (newWriting.getSize().equals(oldWriting.getSize())) ? null : oldWriting.getSize();
    }

    private String getDescriptionDiff(Writing newWriting, Writing oldWriting) {
        return (newWriting.getDescription().equals(oldWriting.getDescription())) ? null : oldWriting.getDescription();
    }


    private String getNameDiff(Writing newWriting, Writing oldWriting) {
        return (newWriting.getName().equals(oldWriting.getName())) ? null : oldWriting.getName();
    }

    private boolean hasChanges(WritingHistoryItem item) {
        return null != item.getName() || null != item.getDescription() || null != item.getSize();
    }


}

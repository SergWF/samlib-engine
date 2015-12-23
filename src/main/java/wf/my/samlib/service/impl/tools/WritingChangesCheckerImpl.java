package wf.my.samlib.service.impl.tools;

import wf.my.samlib.entity.Writing;
import wf.my.samlib.service.tools.WritingChangesChecker;

import java.util.Date;

public class WritingChangesCheckerImpl implements WritingChangesChecker {

    @Override
    public WritingChanges check(Writing oldWriting, Writing newWriting, Date checkDate){
        if(null == newWriting){
            throw new IllegalStateException("New Writing can not be null");
        }
        WritingChanges writingChanges = new WritingChanges();
        addIfChanged(writingChanges, getChange(WritingChanges.ChangeItem.NAME, null == oldWriting ? null : oldWriting.getName(), newWriting.getName()));
        addIfChanged(writingChanges, getChange(WritingChanges.ChangeItem.DESCRPTION, null == oldWriting ? null : oldWriting.getDescription(), newWriting.getDescription()));
        addIfChanged(writingChanges, getChange(WritingChanges.ChangeItem.SIZE, null == oldWriting ? null : oldWriting.getSize(), newWriting.getSize()));
        if(writingChanges.getChanges().isEmpty()){
            return null;
        }
        writingChanges.setCheckDate(checkDate);
        writingChanges.setWriting(newWriting);
        return writingChanges;
    }

    private <T> ItemChanging getChange(WritingChanges.ChangeItem paramName, T oldVal, T newVal){
        if(null == newVal){
            return null == oldVal ? null : new ItemChanging<>(paramName.name(), oldVal, null);
        }
        if(null == oldVal){
            return new ItemChanging<>(paramName.name(), null, newVal);
        }
        return newVal.equals(oldVal) ? null : new ItemChanging<>(paramName.name(), oldVal, newVal);
    }

    private <T> void addIfChanged(WritingChanges writingChanges, ItemChanging<T> change){
        if(null != change){
            writingChanges.getChanges().add(change);
        }
    }
}

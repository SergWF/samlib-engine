package wf.my.samlib.service.tools;

import wf.my.samlib.entity.Writing;
import wf.my.samlib.service.impl.tools.WritingChanges;

import java.util.Date;

public interface WritingChangesChecker {
    WritingChanges check(Writing newWriting, Writing oldWriting, Date checkDate);
}

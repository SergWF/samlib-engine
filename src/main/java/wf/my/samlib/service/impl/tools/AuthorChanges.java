package wf.my.samlib.service.impl.tools;

import java.util.HashSet;
import java.util.Set;

public class AuthorChanges {
    private Set<WritingChanges> writingChangesList = new HashSet<>();

    public boolean hasChanges(){
        return null != writingChangesList && !writingChangesList.isEmpty();
    }

    public Set<WritingChanges> getWritingChangesList() {
        return writingChangesList;
    }
}

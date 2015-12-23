package wf.my.samlib.service.impl.tools;

import wf.my.samlib.entity.Author;

import java.util.HashSet;
import java.util.Set;

public class AuthorChanges {
    private Author author;
    private Set<WritingChanges> writingChangesList = new HashSet<>();

    public boolean isChanged(){
        if(null == writingChangesList) {
            return false;
        }
        for(WritingChanges writingChanges: writingChangesList){
            if(writingChanges.isChanged()){
                return true;
            }
        }
        return false;
    }

    public Set<WritingChanges> getWritingChangesList() {
        return writingChangesList;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}

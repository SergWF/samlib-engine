package wf.my.samlib.tools;

import wf.my.samlib.comparator.UpdateDateComparator;
import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Writing;
import wf.my.samlib.error.NoUrlException;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class AuthorTools {
    public static Set<Writing> findUpdatedWritings(Author author, Date checkDate){
        Set<Writing> updatedWritings = new HashSet<>();
        for(Writing writing : author.getWritings()) {
            if(!writing.getUpdateDate().before(checkDate)) {
                    updatedWritings.add(writing);            }
        }
        return updatedWritings;
    }

    public static boolean isAuthorUpdated(Author author, Date checkDate){
        Writing lastUpdatedWriting = Collections.max(author.getWritings(), new UpdateDateComparator());
        return null != lastUpdatedWriting && !checkDate.after(lastUpdatedWriting.getUpdateDate());
    }


    public static Writing findWritingByUrl(Collection<Writing> writings, String url) {
        if(null == url || url.trim().isEmpty()){
            throw new NoUrlException();
        }
        for(Writing writing: writings){
            if(url.equals(writing.getUrl())){
                return writing;
            }
        }
        return null;
    }
}

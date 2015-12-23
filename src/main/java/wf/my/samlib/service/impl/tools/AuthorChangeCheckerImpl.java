package wf.my.samlib.service.impl.tools;

import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Writing;
import wf.my.samlib.error.UrlNotExistsException;
import wf.my.samlib.service.tools.AuthorChangeChecker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthorChangeCheckerImpl implements AuthorChangeChecker {

    private WritingChangesCheckerImpl writingChangesChecker;

    public void setWritingChangesChecker(WritingChangesCheckerImpl writingChangesChecker) {
        this.writingChangesChecker = writingChangesChecker;
    }

    @Override
    public AuthorChanges check(Author newAuthor, Author existingAuthor){
        List<WritingChanges> writingChangesList = new ArrayList<>(newAuthor.getWritings().size());
        Date checkDate = new Date();
        for(Writing newWriting: newAuthor.getWritings()){
            Writing oldWriting = findWritingByUrl(existingAuthor.getWritings(), newWriting.getUrl());
            WritingChanges writingChanges = writingChangesChecker.check(oldWriting, newWriting, checkDate);
            if(null != writingChanges) {
                writingChangesList.add(writingChanges);
            }
        }
        if(writingChangesList.isEmpty()){
            return null;
        }
        AuthorChanges authorChanges = new AuthorChanges();
        authorChanges.getWritingChangesList().addAll(writingChangesList);
        return authorChanges;
    }



    protected Writing findWritingByUrl(List<Writing> writings, String url) {
        if(null == url || url.trim().isEmpty()){
            throw new UrlNotExistsException();
        }
        for(Writing writing: writings){
            if(url.equals(writing.getUrl())){
                return writing;
            }
        }
        return null;
    }
}
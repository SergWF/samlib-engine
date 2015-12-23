package wf.my.samlib.service.impl.tools;

import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Writing;
import wf.my.samlib.service.tools.AuthorChangeChecker;
import wf.my.samlib.service.tools.AuthorPageParser;
import wf.my.samlib.service.tools.AuthorPageReader;
import wf.my.samlib.service.tools.AuthorUpdater;
import wf.my.samlib.storage.AuthorStorage;

import java.util.Date;
import java.util.List;

public class AuthorUpdaterImpl implements AuthorUpdater {

    private AuthorPageReader authorPageReader;
    private AuthorPageParser authorPageParser;
    private AuthorChangeChecker authorChangeChecker;
    private AuthorStorage authorStorage;

    public void setAuthorPageReader(AuthorPageReader authorPageReader) {
        this.authorPageReader = authorPageReader;
    }

    public void setAuthorPageParser(AuthorPageParser authorPageParser) {
        this.authorPageParser = authorPageParser;
    }

    public void setAuthorChangeChecker(AuthorChangeCheckerImpl authorChangeChecker) {
        this.authorChangeChecker = authorChangeChecker;
    }

    public void setAuthorStorage(AuthorStorage authorStorage) {
        this.authorStorage = authorStorage;
    }

    @Override
    public AuthorChanges updateAuthor(Author author, Date checkDate) {
        String pageString = authorPageReader.readPage(author.getUrl());
        Author authorData = authorPageParser.parsePage(pageString);
        AuthorChanges authorChanges = authorChangeChecker.check(authorData, author, checkDate);
        if(authorChanges.isChanged()) {
            updateWritingDates(authorChanges, authorData.getWritings());
            authorStorage.saveAuthor(authorData);
        }
        return authorChanges;
    }

    private void updateWritingDates(AuthorChanges authorChanges, List<Writing> newWritings) {
        for(Writing writing: newWritings){
            WritingChanges changes = findChanges(authorChanges, writing.getUrl());
            if(!changes.isChanged()){
                writing.setUpdatedDate(changes.getPrevChangeDate());
            }
        }
    }

    private WritingChanges findChanges(AuthorChanges authorChanges, String writingUrl){
        for(WritingChanges writingChanges: authorChanges.getWritingChangesList()){
            if(writingUrl.equals(writingChanges.getWriting().getUrl())){
                return writingChanges;
            }
        }
        throw new IllegalStateException("Can not find Changes for Writing");
    }
}

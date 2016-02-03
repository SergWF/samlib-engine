package wf.my.samlib.service.components.impl;

import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Writing;
import wf.my.samlib.entity.WritingHistoryItem;
import wf.my.samlib.service.components.AuthorPageParser;
import wf.my.samlib.service.components.AuthorPageReader;
import wf.my.samlib.service.components.AuthorChecker;
import wf.my.samlib.storage.AuthorStorage;
import wf.my.samlib.tools.AuthorTools;

import java.util.Date;

public class AuthorCheckerImpl implements AuthorChecker {

    private AuthorPageReader authorPageReader;
    private AuthorPageParser authorPageParser;
    private AuthorStorage authorStorage;
    private WritingChangesChecker writingChangesChecker;


    public void setAuthorPageReader(AuthorPageReader authorPageReader) {
        this.authorPageReader = authorPageReader;
    }

    public void setAuthorPageParser(AuthorPageParser authorPageParser) {
        this.authorPageParser = authorPageParser;
    }

    public void setAuthorStorage(AuthorStorage authorStorage) {
        this.authorStorage = authorStorage;
    }

    public void setWritingChangesChecker(WritingChangesChecker writingChangesChecker) {
        this.writingChangesChecker = writingChangesChecker;
    }


    @Override
    public Author updateAuthor(Author author, Date checkDate) {
        String pageString = authorPageReader.readPage(author.getUrl());
        Author parsedData = authorPageParser.parsePage(pageString);
        parsedData.setUrl(author.getUrl());
        author.setLastCheckedDate(checkDate);
        updateHistory(parsedData, author, checkDate);
        return parsedData;
    }

    private void updateHistory(Author author, Author oldData, Date checkDate) {
        for(Writing writing : author.getWritings()) {
            Writing oldWriting = null == oldData ? null: AuthorTools.findWritingByUrl(oldData.getWritings(), writing.getUrl());
            updateWritingHistory(writing, oldWriting, checkDate);
        }
    }

    protected boolean updateWritingHistory(Writing newWriting, Writing oldWriting, Date checkDate) {
        if(null != oldWriting){
            newWriting.getHistory().addAll(oldWriting.getHistory());
            WritingHistoryItem historyItem = writingChangesChecker.getChanges(newWriting, oldWriting, checkDate);
            if(null != oldWriting){
                newWriting.getHistory().addAll(oldWriting.getHistory());
            }

            if(null != historyItem){
                newWriting.getHistory().add(historyItem);
            }
            return null != historyItem;
        }
        return true;
    }



}

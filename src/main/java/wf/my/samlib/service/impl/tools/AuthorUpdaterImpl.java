package wf.my.samlib.service.impl.tools;

import wf.my.samlib.entity.Author;
import wf.my.samlib.service.tools.AuthorChangeChecker;
import wf.my.samlib.service.tools.AuthorPageParser;
import wf.my.samlib.service.tools.AuthorPageReader;
import wf.my.samlib.service.tools.AuthorUpdater;
import wf.my.samlib.storage.AuthorStorage;

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
    public AuthorChanges updateAuthor(Author author) {
        String pageString = authorPageReader.readPage(author.getUrl());
        Author authorData = authorPageParser.parsePage(pageString);
        AuthorChanges authorChanges = authorChangeChecker.check(authorData, author);
        if(null != authorChanges) {
            authorStorage.saveAuthor(authorData);
        }
        return authorChanges;
    }
}

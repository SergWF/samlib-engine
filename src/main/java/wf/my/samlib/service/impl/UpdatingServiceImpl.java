package wf.my.samlib.service.impl;

import wf.my.samlib.entity.Author;
import wf.my.samlib.service.UpdatingService;
import wf.my.samlib.service.impl.tools.AuthorChanges;
import wf.my.samlib.service.impl.tools.AuthorUpdaterImpl;
import wf.my.samlib.storage.AuthorStorage;

import java.util.Date;


public class UpdatingServiceImpl implements UpdatingService {

    private AuthorStorage authorStorage;
    private AuthorUpdaterImpl updater;

    public void setAuthorStorage(AuthorStorage authorStorage) {
        this.authorStorage = authorStorage;
    }

    public void setUpdater(AuthorUpdaterImpl updater) {
        this.updater = updater;
    }

    @Override
    public UpdatingStatistic updateAuthors() {
        UpdatingStatistic statistic = new UpdatingStatistic();
        Date checkDate = new Date();
        for(Author author : authorStorage.getAllAuthors()) {
            AuthorChanges authorChanges = updater.updateAuthor(author, checkDate);
            if(authorChanges.isChanged()){
                statistic.add(authorChanges);
            }
        }
        return statistic;
    }

}

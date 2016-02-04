package wf.my.samlib.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.UpdateState;
import wf.my.samlib.entity.Writing;
import wf.my.samlib.listener.AuthorRenewListener;
import wf.my.samlib.service.UpdatingService;
import wf.my.samlib.service.components.AuthorChecker;
import wf.my.samlib.storage.AuthorStorage;
import wf.my.samlib.tools.AuthorTools;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class UpdatingServiceImpl implements UpdatingService {

    private static final Logger logger = LoggerFactory.getLogger(UpdatingServiceImpl.class);
    private AuthorStorage authorStorage;
    private AuthorChecker authorChecker;
    private UpdateState updateState;
    private Long updatePause = 10000L;
    private Set<AuthorRenewListener> authorRenewListeners = new HashSet<>();

    public void setAuthorStorage(AuthorStorage authorStorage) {
        this.authorStorage = authorStorage;
    }

    public void setAuthorChecker(AuthorChecker authorChecker) {
        this.authorChecker = authorChecker;
    }

    public Long getUpdatePause() {
        return updatePause;
    }

    public void setUpdatePause(Long updatePause) {
        this.updatePause = updatePause;
    }

    @Override
    public boolean updateAuthors() {
        updateState = getUpdateState();
        if(updateState.isInProcess()){
            logger.warn("Update in process");
            return false;
        }
        updateState.setStartDate(new Date());
        Collection<Author> authors = authorStorage.getAllAuthors();
        updateState.setAuthorsTotal(authors.size());
        for(Author author : authors) {
            Author checkedAuthor = authorChecker.updateAuthor(author, updateState.getStartDate());
            authorStorage.saveAuthor(checkedAuthor);
            updateState.increaseChecked();
            if(isAuthorUpdated(checkedAuthor, updateState.getStartDate())){
                raiseAuthorUpdatedEvent(author, updateState.getStartDate());
            }
            makePause();
        }
        updateState.setEndDate(new Date());
        return true;
    }

    protected void makePause(){
        try {
            Thread.sleep(updatePause);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected boolean isAuthorUpdated(Author author, Date checkDate){
        return AuthorTools.isAuthorUpdated(author, checkDate);
    }

    private void raiseAuthorUpdatedEvent(Author author, Date updateDate) {
        Set<Writing> updatedWritings = AuthorTools.findUpdatedWritings(author, updateDate);
        for(AuthorRenewListener authorRenewListener : authorRenewListeners) {
            authorRenewListener.onRenew(author, updateDate, updatedWritings);
        }
    }

    public UpdateState getUpdateState() {
        if(null == updateState){
            updateState = new UpdateState();
        }
        return updateState;
    }

    public void registerAuthorRenewListener(AuthorRenewListener authorRenewListener){
        authorRenewListeners.add(authorRenewListener);
    }
}

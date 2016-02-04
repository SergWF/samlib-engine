package wf.my.samlib.factory.impl;

import wf.my.samlib.factory.ServiceFactory;
import wf.my.samlib.service.LibraryService;
import wf.my.samlib.service.SubscribtionServivce;
import wf.my.samlib.service.UpdatingService;
import wf.my.samlib.service.components.impl.AuthorCheckerImpl;
import wf.my.samlib.service.components.impl.AuthorPageParserImpl;
import wf.my.samlib.service.components.impl.AuthorPageReaderImpl;
import wf.my.samlib.service.components.impl.WritingChangesChecker;
import wf.my.samlib.service.impl.LibraryServiceImpl;
import wf.my.samlib.service.impl.UpdatingServiceImpl;
import wf.my.samlib.storage.AuthorStorage;
import wf.my.samlib.storage.SubscriptionStorage;

public class ServiceFactoryImpl implements ServiceFactory {

    private AuthorStorage authorStorage;
    private SubscriptionStorage subscriptionStorage;
    private long updatePause = 3000;

    public void setUpdatePause(long updatePause) {
        this.updatePause = updatePause;
    }

    public AuthorStorage getAuthorStorage() {
        if(null == authorStorage){
            throw new IllegalStateException("There is no AuthorStorage found");
        }
        return authorStorage;
    }

    public void setAuthorStorage(AuthorStorage authorStorage) {
        this.authorStorage = authorStorage;
    }

    public SubscriptionStorage getSubscriptionStorage() {
        if(null == subscriptionStorage){
            throw new IllegalStateException("There is no SubscriptionStorage found");
        }
        return subscriptionStorage;
    }

    public void setSubscriptionStorage(SubscriptionStorage subscriptionStorage) {
        this.subscriptionStorage = subscriptionStorage;
    }

    @Override
    public LibraryService getLibraryService() {
        LibraryServiceImpl libraryService = new LibraryServiceImpl();
        libraryService.setAuthorStorage(authorStorage);
        return libraryService;
    }

    @Override
    public UpdatingService getUpdatingService() {
        UpdatingServiceImpl updatingService = new UpdatingServiceImpl();
        AuthorCheckerImpl authorChecker = new AuthorCheckerImpl();
        authorChecker.setAuthorPageParser(new AuthorPageParserImpl());
        authorChecker.setAuthorPageReader(new AuthorPageReaderImpl());
        authorChecker.setWritingChangesChecker(new WritingChangesChecker());

        updatingService.setAuthorChecker(authorChecker);
        updatingService.setAuthorStorage(authorStorage);
        updatingService.setUpdatePause(updatePause);
        return updatingService;
    }

    @Override
    public SubscribtionServivce getSubscribtionServivce() {
        return null;
    }
}

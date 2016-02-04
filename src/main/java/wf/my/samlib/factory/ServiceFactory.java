package wf.my.samlib.factory;

import wf.my.samlib.service.LibraryService;
import wf.my.samlib.service.SubscribtionServivce;
import wf.my.samlib.service.UpdatingService;

public interface ServiceFactory {
    LibraryService getLibraryService();
    UpdatingService getUpdatingService();
    SubscribtionServivce getSubscribtionServivce();
}

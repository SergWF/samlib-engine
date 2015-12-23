package wf.my.samlib.service;

import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Subscriber;
import wf.my.samlib.entity.Subscription;

import java.util.List;

public interface SubscribtionServivce {
    Subscription subscribe(Subscriber subscriber, Author author);
    Subscription unSubscribe(Subscriber subscriber, Author author);
    Subscription getSubscription(Subscriber subscriber, Author author);
    List<Subscription> getAllSubscriptions(Subscriber subscriber);
    List<Subscriber> getAllSubscribers();
}

package wf.my.samlib.service.impl;

import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Subscriber;
import wf.my.samlib.entity.Subscription;
import wf.my.samlib.service.SubscribtionServivce;
import wf.my.samlib.storage.SubscriptionStorage;

import java.util.List;

public class SubscribtionServiceImpl implements SubscribtionServivce {

    private SubscriptionStorage subscriptionStorage;


    public void setSubscriptionStorage(SubscriptionStorage subscriptionStorage) {
        this.subscriptionStorage = subscriptionStorage;
    }

    @Override
    public Subscription subscribe(Subscriber subscriber, Author author) {
        Subscription subscription = subscriptionStorage.findSubscription(subscriber, author);
        return null == subscription ? subscriptionStorage.createNewSubscription(subscriber, author) : subscription;
    }

    @Override
    public Subscription unSubscribe(Subscriber subscriber, Author author) {
        Subscription subscription = subscriptionStorage.findSubscription(subscriber, author);
        if(null != subscription) {
            subscriptionStorage.removeSubscription(subscription);
        }
        return subscription;
    }

    @Override
    public Subscription getSubscription(Subscriber subscriber, Author author) {
        return subscriptionStorage.findSubscription(subscriber, author);
    }

    @Override
    public List<Subscription> getAllSubscriptions(Subscriber subscriber) {
        return subscriptionStorage.getAllSubscriptions(subscriber);
    }

    @Override
    public List<Subscriber> getAllSubscribers() {
        return subscriptionStorage.getAllSubscribers();
    }
}

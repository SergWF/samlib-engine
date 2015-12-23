package wf.my.samlib.storage;

import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Subscriber;
import wf.my.samlib.entity.Subscription;

import java.util.List;

public interface SubscriptionStorage {
    Subscription createNewSubscription(Subscriber subscriber, Author author);

    Subscription findSubscription(Subscriber subscriber, Author author);

    void removeSubscription(Subscription subscription);

    List<Subscription> getAllSubscriptions(Subscriber subscriber);

    List<Subscriber> getAllSubscribers();
}

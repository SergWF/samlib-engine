package wf.my.samlib.listener;

import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Writing;

import java.util.Collection;
import java.util.Date;

public interface AuthorRenewListener {
    void onRenew(Author author, Date updateDate, Collection<Writing> renewedWritings);
}

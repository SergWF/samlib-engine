package wf.my.samlib.service.components;

import wf.my.samlib.entity.Author;

import java.util.Date;

public interface AuthorUpdater {
    Author updateAuthor(String url, Date checkDate);
}

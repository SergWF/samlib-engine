package wf.my.samlib.service.components;

import wf.my.samlib.entity.Author;

import java.util.Date;

public interface AuthorChecker {
    Author updateAuthor(Author author, Date checkDate);
}

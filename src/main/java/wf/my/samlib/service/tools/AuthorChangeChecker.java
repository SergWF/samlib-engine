package wf.my.samlib.service.tools;

import wf.my.samlib.entity.Author;
import wf.my.samlib.service.impl.tools.AuthorChanges;

import java.util.Date;

public interface AuthorChangeChecker {
    AuthorChanges check(Author newAuthor, Author existingAuthor, Date checkDate);
}

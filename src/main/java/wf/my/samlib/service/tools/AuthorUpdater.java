package wf.my.samlib.service.tools;

import wf.my.samlib.entity.Author;
import wf.my.samlib.service.impl.tools.AuthorChanges;

import java.util.Date;

public interface AuthorUpdater {
    AuthorChanges updateAuthor(Author author, Date checkDate);
}

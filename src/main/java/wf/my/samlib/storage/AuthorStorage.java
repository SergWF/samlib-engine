package wf.my.samlib.storage;

import wf.my.samlib.entity.Author;

import java.util.List;

public interface AuthorStorage {

    Author getAuthorByUrl(String url);

    Author createNewAuthor(String url);

    List<Author> getAllAuthors();

    void saveAuthor(Author newAuthor);

    void removeAuthor(Author author);
}

package wf.my.samlib.service;

import wf.my.samlib.entity.Author;

import java.util.Collection;
import java.util.List;

public interface LibraryService {
    Author addAuthorByUrl(String url);
    Author removeAuthor(Author author);
    List<Author> addAuthorsByUrls(Collection<String> urls);
    List<Author> getAllAuthors();
}

package wf.my.samlib.service.impl;

import wf.my.samlib.entity.Author;
import wf.my.samlib.service.LibraryService;
import wf.my.samlib.storage.AuthorStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LibraryServiceImpl implements LibraryService {

    private AuthorStorage authorStorage;

    @Override
    public Author addAuthorByUrl(String url) {
        Author author = authorStorage.getAuthorByUrl(url);
        if(null == author){
            author = authorStorage.createNewAuthor(url);
        }
        return author;
    }

    @Override
    public Author removeAuthor(Author author) {
        authorStorage.removeAuthor(author);
        return author;
    }

    @Override
    public List<Author> addAuthorsByUrls(Collection<String> urls) {
        List<Author> authorList = new ArrayList<>(urls.size());
        for(String url: urls){
            authorList.add(addAuthorByUrl(url));
        }
        return authorList;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorStorage.getAllAuthors();
    }

}

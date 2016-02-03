package wf.my.samlib.storage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wf.my.samlib.entity.Author;
import wf.my.samlib.storage.AuthorStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InMemoryAuthorStorage implements AuthorStorage {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryAuthorStorage.class);
    private Set<Author> authors = new HashSet<>();

    @Override
    public Author getAuthorByUrl(String url) {
        logger.debug("search for author by url [{}]", url);
        for(Author author : authors) {
            if(author.getUrl().equals(url)){
                logger.debug("author [{}] found for url [{}]", author.getName(), url);
                return author;
            }
        }
        logger.debug("author not found for url [{}]", url);
        return null;
    }

    @Override
    public Author createNewAuthor(String url) {
        Author author = new Author();
        author.setUrl(url);
        author.setName("unknown");
        return author;
    }

    @Override
    public List<Author> getAllAuthors() {
        return new ArrayList<>(authors);
    }

    @Override
    public void saveAuthor(Author newAuthor) {
        if(authors.contains(newAuthor)){
            authors.remove(newAuthor);
        }
        authors.add(newAuthor);
    }

    @Override
    public void removeAuthor(Author author) {
        authors.remove(author);
    }

}

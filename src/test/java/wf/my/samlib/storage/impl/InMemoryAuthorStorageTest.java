package wf.my.samlib.storage.impl;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import wf.my.samlib.TestHelper;
import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Writing;

import java.util.List;

import static org.junit.Assert.*;

public class InMemoryAuthorStorageTest {


    private InMemoryAuthorStorage inMemoryAuthorStorage;
    private Author a1;
    private Author a2;
    private Author a3;
    private Author a4;
    private Author a5;

    @Before
    public void setUp() throws Exception {
        inMemoryAuthorStorage = new InMemoryAuthorStorage();
        a1 = TestHelper.getAuthor("a1u", "a1n");
        a2 = TestHelper.getAuthor("a2u", "a2n");
        a3 = TestHelper.getAuthor("a3u", "a3n");
        a4 = TestHelper.getAuthor("a4u", "a4n");
        a5 = TestHelper.getAuthor("a5u", "a5n");

        inMemoryAuthorStorage.saveAuthor(a1);
        inMemoryAuthorStorage.saveAuthor(a2);
        inMemoryAuthorStorage.saveAuthor(a3);
        inMemoryAuthorStorage.saveAuthor(a4);
        inMemoryAuthorStorage.saveAuthor(a5);
    }

    @Test
    public void testGetAuthorByUrl() throws Exception {
        String url  = "a3u";
        Assert.assertNull(inMemoryAuthorStorage.getAuthorByUrl("wrong"));
        Assert.assertThat(inMemoryAuthorStorage.getAuthorByUrl(url),
                Matchers.allOf(
                        Matchers.hasProperty("url", Matchers.equalTo(url)),
                        Matchers.hasProperty("name", Matchers.equalTo("a3n"))
                )
        );
    }

    @Test
    public void testCreateNewAuthor() throws Exception {
        String url  = "new_url";
        Assert.assertThat(inMemoryAuthorStorage.createNewAuthor(url),
                Matchers.allOf(
                        Matchers.hasProperty("url", Matchers.equalTo(url)),
                        Matchers.hasProperty("name", Matchers.equalTo("unknown")),
                        Matchers.hasProperty("writings", Matchers.emptyCollectionOf(Writing.class)),
                        Matchers.hasProperty("updateDate", Matchers.nullValue())
                )
        );
    }

    @Test
    public void testGetAllAuthors() throws Exception {
        List<Author> allAuthors = inMemoryAuthorStorage.getAllAuthors();
        Assert.assertThat(allAuthors, Matchers.hasSize(5));
        Assert.assertThat(allAuthors,
                Matchers.containsInAnyOrder(
                        Matchers.hasProperty("url", Matchers.equalTo("a1u")),
                        Matchers.hasProperty("url", Matchers.equalTo("a2u")),
                        Matchers.hasProperty("url", Matchers.equalTo("a3u")),
                        Matchers.hasProperty("url", Matchers.equalTo("a4u")),
                        Matchers.hasProperty("url", Matchers.equalTo("a5u"))
                )
        );
    }

    @Test
    public void testAddAuthor() throws Exception {
        String url = "a_new_u";
        Assert.assertNull(inMemoryAuthorStorage.getAuthorByUrl(url));
        Assert.assertTrue(5 == inMemoryAuthorStorage.getAllAuthors().size());
        inMemoryAuthorStorage.saveAuthor(TestHelper.getAuthor(url, "a_new_n"));
        Assert.assertTrue(6 == inMemoryAuthorStorage.getAllAuthors().size());
        Assert.assertThat(inMemoryAuthorStorage.getAuthorByUrl(url),
                Matchers.allOf(
                        Matchers.hasProperty("url", Matchers.equalTo(url)),
                        Matchers.hasProperty("name", Matchers.equalTo("a_new_n"))
                )
        );
    }

    @Test
    public void testOverwriteAuthor() throws Exception {
        String url = "a3u";
        String newName = "a_new_n";
        Assert.assertThat(inMemoryAuthorStorage.getAuthorByUrl(url),
                Matchers.allOf(
                        Matchers.hasProperty("url", Matchers.equalTo(url)),
                        Matchers.hasProperty("name", Matchers.equalTo("a3n"))
                )
        );
        Assert.assertTrue(5 == inMemoryAuthorStorage.getAllAuthors().size());
        inMemoryAuthorStorage.saveAuthor(TestHelper.getAuthor(url, newName));
        Assert.assertTrue(5 == inMemoryAuthorStorage.getAllAuthors().size());
        Assert.assertThat(inMemoryAuthorStorage.getAuthorByUrl(url),
                Matchers.allOf(
                        Matchers.hasProperty("url", Matchers.equalTo(url)),
                        Matchers.hasProperty("name", Matchers.equalTo(newName))
                )
        );
    }

    @Test
    public void testSaveAuthorUniqueUrlsLastValue() throws Exception {
        String url = "a3u";
        inMemoryAuthorStorage.saveAuthor(TestHelper.getAuthor(url, "a_new_n1"));
        inMemoryAuthorStorage.saveAuthor(TestHelper.getAuthor(url, "a_new_n2"));
        inMemoryAuthorStorage.saveAuthor(TestHelper.getAuthor(url, "a_new_n3"));

        int counter = 0;
        Author authorFound = null;
        for(Author author : inMemoryAuthorStorage.getAllAuthors()) {
            if(author.getUrl().equals(url)){
                counter++;
                authorFound = author;
            }
        }
        Assert.assertTrue(counter == 1);
        Assert.assertThat(authorFound,
                Matchers.allOf(
                        Matchers.hasProperty("url", Matchers.equalTo(url)),
                        Matchers.hasProperty("name", Matchers.equalTo("a_new_n3"))
                )
        );
    }

    @Test
    public void testRemoveAuthor() throws Exception {
        Assert.assertTrue(inMemoryAuthorStorage.getAllAuthors().contains(a3));
        inMemoryAuthorStorage.removeAuthor(a3);
        Assert.assertFalse(inMemoryAuthorStorage.getAllAuthors().contains(a3));
        Assert.assertNull(inMemoryAuthorStorage.getAuthorByUrl(a3.getUrl()));
    }

    @Test
    public void testRemoveAuthorWithSameUrl() throws Exception {
        String url = a3.getUrl();
        Assert.assertNotNull(inMemoryAuthorStorage.getAuthorByUrl(url));
        inMemoryAuthorStorage.removeAuthor(TestHelper.getAuthor(url, "some_name"));
        Assert.assertNull(inMemoryAuthorStorage.getAuthorByUrl(a3.getUrl()));
    }

}
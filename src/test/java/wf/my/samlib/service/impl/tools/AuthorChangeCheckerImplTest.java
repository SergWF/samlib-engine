package wf.my.samlib.service.impl.tools;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Writing;
import wf.my.samlib.service.helper.TestHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AuthorChangeCheckerImplTest {

    AuthorChangeCheckerImpl authorChangeChecker;

    Author author;
    Author authorWithUpdate;
    Author authorNoChanges;
    Author authorWithNew;
    Author authorWithDeleted;
    @Before
    public void setUp() throws Exception {
        authorChangeChecker = new AuthorChangeCheckerImpl();
        authorChangeChecker.setWritingChangesChecker(new WritingChangesCheckerImpl());
        author = TestHelper.getAuthor("http://a1", new Date(), new Date(),
                TestHelper.getWriting("http://a1/w1", "old name 1", "100k", "old description 1", new Date()),
                TestHelper.getWriting("http://a1/w2", "old name 2", "200k", "old description 2", new Date()),
                TestHelper.getWriting("http://a1/w3", "old name 3", "300k", "old description 3", new Date()),
                TestHelper.getWriting("http://a1/w4", "old name 4", "400k", "old description 4", new Date())
        );
        authorNoChanges = TestHelper.getAuthor("http://a1", new Date(), new Date(),
                TestHelper.getWriting("http://a1/w1", "old name 1", "100k", "old description 1", new Date()),
                TestHelper.getWriting("http://a1/w2", "old name 2", "200k", "old description 2", new Date()),
                TestHelper.getWriting("http://a1/w3", "old name 3", "300k", "old description 3", new Date()),
                TestHelper.getWriting("http://a1/w4", "old name 4", "400k", "old description 4", new Date())
        );
        authorWithUpdate = TestHelper.getAuthor("http://a1", new Date(), new Date(),
                TestHelper.getWriting("http://a1/w1", "new name 1", "100k", "old description 1", new Date()),
                TestHelper.getWriting("http://a1/w2", "old name 2", "222k", "old description 2", new Date()),
                TestHelper.getWriting("http://a1/w3", "old name 3", "333k", "new description 3", new Date()),
                TestHelper.getWriting("http://a1/w4", "old name 4", "400k", "old description 4", new Date())
        );
        authorWithNew = TestHelper.getAuthor("http://a1", new Date(), new Date(),
                TestHelper.getWriting("http://a1/w1", "new name 1", "100k", "old description 1", new Date()),
                TestHelper.getWriting("http://a1/w2", "old name 2", "222k", "old description 2", new Date()),
                TestHelper.getWriting("http://a1/w3", "old name 3", "333k", "new description 3", new Date()),
                TestHelper.getWriting("http://a1/w4", "old name 4", "400k", "old description 4", new Date()),
                TestHelper.getWriting("http://a1/w5", "new name 5", "555k", "new description 5", new Date())
        );
        authorWithDeleted = TestHelper.getAuthor("http://a1", new Date(), new Date(),
                TestHelper.getWriting("http://a1/w1", "old name 1", "100k", "old description 1", new Date()),
                TestHelper.getWriting("http://a1/w4", "old name 4", "400k", "old description 4", new Date())
        );
    }

    @Test
    public void testFindWritingByUrl() throws Exception {
        List<Writing> writings = Arrays.asList(
                TestHelper.getWriting("http://a1/w1", "old name 1", "100k", "old description 1", new Date()),
                TestHelper.getWriting("http://a1/w2", "old name 2", "200k", "old description 2", new Date()),
                TestHelper.getWriting("http://a1/w3", "old name 3", "300k", "old description 3", new Date()));
        Writing writing = authorChangeChecker.findWritingByUrl(writings, "http://a1/w2");
        Assert.assertThat(writing,
                Matchers.allOf(
                        Matchers.hasProperty("url", Matchers.equalTo("http://a1/w2")),
                        Matchers.hasProperty("name", Matchers.equalTo("old name 2"))
                )
        );
    }

    @Test
    public void testCheckNoChanges() throws Exception {
        Assert.assertNull(authorChangeChecker.check(authorNoChanges, author));
    }

    @Test
    public void testCheckDeleted() throws Exception {
        Assert.assertNull(authorChangeChecker.check(authorWithDeleted, author));
    }

    @Test
    public void testCheckWithUpdates() throws Exception {
        AuthorChanges actual = authorChangeChecker.check(authorWithUpdate, author);
        Assert.assertTrue(actual.hasChanges());
        Assert.assertThat(actual,
                Matchers.allOf(
                        Matchers.hasProperty("writingChangesList",Matchers.hasSize(3)),
                        Matchers.hasProperty("writingChangesList",
                                Matchers.hasItems(
                                        Matchers.hasProperty("writing", Matchers.hasProperty("url", Matchers.equalTo("http://a1/w1"))),
                                        Matchers.hasProperty("writing", Matchers.hasProperty("url", Matchers.equalTo("http://a1/w2"))),
                                        Matchers.hasProperty("writing", Matchers.hasProperty("url", Matchers.equalTo("http://a1/w3")))
                                )
                        )
                )
        );
    }
    @Test
    public void testCheckWithNew() throws Exception {
        AuthorChanges actual = authorChangeChecker.check(authorWithNew, author);
        Assert.assertTrue(actual.hasChanges());
        Assert.assertThat(actual,
                Matchers.allOf(
                        Matchers.hasProperty("writingChangesList",Matchers.hasSize(4)),
                        Matchers.hasProperty("writingChangesList",
                                Matchers.hasItems(
                                        Matchers.hasProperty("writing", Matchers.hasProperty("url", Matchers.equalTo("http://a1/w1"))),
                                        Matchers.hasProperty("writing", Matchers.hasProperty("url", Matchers.equalTo("http://a1/w2"))),
                                        Matchers.hasProperty("writing", Matchers.hasProperty("url", Matchers.equalTo("http://a1/w3"))),
                                        Matchers.hasProperty("writing", Matchers.hasProperty("url", Matchers.equalTo("http://a1/w5")))
                                )
                        )
                )
        );
    }

}
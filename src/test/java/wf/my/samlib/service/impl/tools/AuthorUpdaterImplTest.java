package wf.my.samlib.service.impl.tools;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import wf.my.samlib.entity.Author;
import wf.my.samlib.service.helper.TestHelper;
import wf.my.samlib.service.tools.AuthorPageParser;
import wf.my.samlib.service.tools.AuthorPageReader;
import wf.my.samlib.storage.AuthorStorage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthorUpdaterImplTest {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String AUTHOR_URL = "http://url";
    private AuthorUpdaterImpl authorUpdater;
    private AuthorPageReader pageReader = Mockito.mock(AuthorPageReader.class);
    private AuthorPageParser pageParser = Mockito.mock(AuthorPageParser.class);
    private AuthorStorage authorStorage = Mockito.mock(AuthorStorage.class);

    private AuthorChangeCheckerImpl changeChecker = new AuthorChangeCheckerImpl();
    private Author author;
    private Author parsed;
    private Author parsedNew;
    private Author parsedNoChanges;
    private String pageString = "";
    private Date checkDate;
    private Date writing1Date;
    private Date writing2Date;
    private Date writing3Date;
    private Date writing4Date;
    private Date lastChangedDate;



    @Before
    public void setUp() throws Exception {
        checkDate = sdf.parse("2015-12-20 10:00:00");
        Date updatedDate = sdf.parse("2015-12-21 12:00:00");
        lastChangedDate = sdf.parse("2015-12-20 10:00:00");
        writing1Date = sdf.parse("2015-12-18 10:00:00");
        writing2Date = sdf.parse("2015-12-19 10:00:00");
        writing3Date = sdf.parse("2015-12-20 10:00:00");
        writing4Date = sdf.parse("2015-12-20 10:00:00");

        changeChecker.setWritingChangesChecker(new WritingChangesCheckerImpl());
        author = TestHelper.getAuthor(AUTHOR_URL, updatedDate, lastChangedDate,
                TestHelper.getWriting("http://url/w1", "old name 1", "100k", "old description 1", writing1Date),
                TestHelper.getWriting("http://url/w2", "old name 2", "200k", "old description 2", writing2Date),
                TestHelper.getWriting("http://url/w3", "old name 3", "300k", "old description 3", writing3Date),
                TestHelper.getWriting("http://url/w4", "old name 4", "400k", "old description 4", writing4Date)
                );
        parsed = TestHelper.getAuthor(AUTHOR_URL, checkDate, checkDate,
                TestHelper.getWriting("http://url/w1", "old name 1", "100k", "old description 1", checkDate),
                TestHelper.getWriting("http://url/w2", "new name 2", "200k", "old description 2", checkDate),
                TestHelper.getWriting("http://url/w3", "old name 3", "333k", "old description 3", checkDate),
                TestHelper.getWriting("http://url/w4", "old name 4", "400k", "new description 4", checkDate),
                TestHelper.getWriting("http://url/w5", "new name 5", "555k", "new description 5", checkDate)
        );
        parsedNoChanges = TestHelper.getAuthor(AUTHOR_URL, checkDate, checkDate,
                TestHelper.getWriting("http://url/w1", "old name 1", "100k", "old description 1", checkDate),
                TestHelper.getWriting("http://url/w2", "old name 2", "200k", "old description 2", checkDate),
                TestHelper.getWriting("http://url/w3", "old name 3", "300k", "old description 3", checkDate),
                TestHelper.getWriting("http://url/w4", "old name 4", "400k", "old description 4", checkDate)
        );
        parsedNew = TestHelper.getAuthor(AUTHOR_URL, checkDate, checkDate,
                TestHelper.getWriting("http://url/w1", "new name 1", "100k", "new description 1", checkDate),
                TestHelper.getWriting("http://url/w2", "new name 2", "200k", "new description 2", checkDate)
        );
        authorUpdater = new AuthorUpdaterImpl();
        authorUpdater.setAuthorPageReader(pageReader);
        authorUpdater.setAuthorPageParser(pageParser);
        authorUpdater.setAuthorChangeChecker(changeChecker);
        authorUpdater.setAuthorStorage(authorStorage);
        Mockito.doReturn(pageString).when(pageReader).readPage(AUTHOR_URL);
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        Mockito.doReturn(parsed).when(pageParser).parsePage(pageString);
        AuthorChanges actual = authorUpdater.updateAuthor(author, checkDate);
        Assert.assertThat(actual,
                Matchers.allOf(
                        Matchers.hasProperty("url", Matchers.equalTo(author.getUrl())),
                        Matchers.hasProperty("updateDate", Matchers.equalTo(checkDate)),
                        Matchers.hasProperty("lastChangedDate", Matchers.equalTo(checkDate)),
                        Matchers.hasProperty("writings", Matchers.hasSize(5)),
                        Matchers.hasProperty("writings",
                                Matchers.hasItems(
                                        Matchers.allOf(
                                                Matchers.hasProperty("url", Matchers.equalTo("http://url/w1")),
                                                Matchers.hasProperty("description", Matchers.equalTo("old description 1")),
                                                Matchers.hasProperty("size", Matchers.equalTo("100k")),
                                                Matchers.hasProperty("name", Matchers.equalTo("old name 1")),
                                                Matchers.hasProperty("updateDate", Matchers.equalTo(writing1Date))
                                        ),
                                        Matchers.allOf(
                                                Matchers.hasProperty("url", Matchers.equalTo("http://url/w2")),
                                                Matchers.hasProperty("description", Matchers.equalTo("old description 2")),
                                                Matchers.hasProperty("size", Matchers.equalTo("200k")),
                                                Matchers.hasProperty("name", Matchers.equalTo("new name 2")),
                                                Matchers.hasProperty("updateDate", Matchers.equalTo(checkDate))
                                        ),
                                        Matchers.allOf(
                                                Matchers.hasProperty("url", Matchers.equalTo("http://url/w3")),
                                                Matchers.hasProperty("description", Matchers.equalTo("old description 3")),
                                                Matchers.hasProperty("size", Matchers.equalTo("333k")),
                                                Matchers.hasProperty("name", Matchers.equalTo("old name 3")),
                                                Matchers.hasProperty("updateDate", Matchers.equalTo(checkDate))
                                        ),
                                        Matchers.allOf(
                                                Matchers.hasProperty("url", Matchers.equalTo("http://url/w4")),
                                                Matchers.hasProperty("description", Matchers.equalTo("new description 4")),
                                                Matchers.hasProperty("size", Matchers.equalTo("400k")),
                                                Matchers.hasProperty("name", Matchers.equalTo("old name 4")),
                                                Matchers.hasProperty("updateDate", Matchers.equalTo(checkDate))
                                        ),
                                        Matchers.allOf(
                                                Matchers.hasProperty("url", Matchers.equalTo("http://url/w4")),
                                                Matchers.hasProperty("description", Matchers.equalTo("new description 4")),
                                                Matchers.hasProperty("size", Matchers.equalTo("555k")),
                                                Matchers.hasProperty("name", Matchers.equalTo("new name 5")),
                                                Matchers.hasProperty("updateDate", Matchers.equalTo(checkDate))
                                        )
                                )
                        )
                )
        );
    }

    @Test
    public void testNewAuthor() throws Exception {
        Author newAuthor = TestHelper.getAuthor(AUTHOR_URL, null, null);
        Mockito.doReturn(parsed).when(pageParser).parsePage(pageString);
        AuthorChanges actual = authorUpdater.updateAuthor(newAuthor, checkDate);
        Assert.assertThat(actual,
                Matchers.allOf(
                        Matchers.hasProperty("changed", Matchers.equalTo(true)),
                        Matchers.hasProperty("author", Matchers.hasProperty("url", Matchers.equalTo(AUTHOR_URL))),
                        Matchers.hasProperty("writingChangesList",
                                Matchers.hasItems(
                                        Matchers.allOf(
                                                Matchers.hasProperty("changed", Matchers.equalTo(true)),
                                                Matchers.hasProperty("writing", Matchers.hasProperty("url", Matchers.equalTo("http://url/w1")))
                                        ),
                                        Matchers.allOf(
                                                Matchers.hasProperty("changed", Matchers.equalTo(true)),
                                                Matchers.hasProperty("writing", Matchers.hasProperty("url", Matchers.equalTo("http://url/w2")))
                                        )
                                )
                        )

                )
        );
    }

    @Test
    public void testUpdateAuthorNoChanges() throws Exception {
        Mockito.doReturn(parsedNoChanges).when(pageParser).parsePage(pageString);
        AuthorChanges actual = authorUpdater.updateAuthor(author, checkDate);
        Assert.assertThat(actual,
                Matchers.allOf(
                        Matchers.hasProperty("url", Matchers.equalTo(AUTHOR_URL)),
                        Matchers.hasProperty("updateDate", Matchers.equalTo(checkDate)),
                        Matchers.hasProperty("lastChangedDate", Matchers.equalTo(lastChangedDate)),
                        Matchers.hasProperty("writings", Matchers.hasSize(4)),
                        Matchers.hasProperty("writings",
                                Matchers.hasItems(
                                        Matchers.allOf(
                                                Matchers.hasProperty("url", Matchers.equalTo("http://url/w1")),
                                                Matchers.hasProperty("description", Matchers.equalTo("old description 1")),
                                                Matchers.hasProperty("size", Matchers.equalTo("100k")),
                                                Matchers.hasProperty("name", Matchers.equalTo("old name 1")),
                                                Matchers.hasProperty("updateDate", Matchers.equalTo(writing1Date))
                                        ),
                                        Matchers.allOf(
                                                Matchers.hasProperty("url", Matchers.equalTo("http://url/w2")),
                                                Matchers.hasProperty("description", Matchers.equalTo("old description 2")),
                                                Matchers.hasProperty("size", Matchers.equalTo("200k")),
                                                Matchers.hasProperty("name", Matchers.equalTo("old name 2")),
                                                Matchers.hasProperty("updateDate", Matchers.equalTo(writing2Date))
                                        ),
                                        Matchers.allOf(
                                                Matchers.hasProperty("url", Matchers.equalTo("http://url/w3")),
                                                Matchers.hasProperty("description", Matchers.equalTo("old description 3")),
                                                Matchers.hasProperty("size", Matchers.equalTo("300k")),
                                                Matchers.hasProperty("name", Matchers.equalTo("old name 3")),
                                                Matchers.hasProperty("updateDate", Matchers.equalTo(writing3Date))
                                        ),
                                        Matchers.allOf(
                                                Matchers.hasProperty("url", Matchers.equalTo("http://url/w4")),
                                                Matchers.hasProperty("description", Matchers.equalTo("old description 4")),
                                                Matchers.hasProperty("size", Matchers.equalTo("400k")),
                                                Matchers.hasProperty("name", Matchers.equalTo("old name 4")),
                                                Matchers.hasProperty("updateDate", Matchers.equalTo(writing4Date))
                                        )
                                )
                        )
                )
        );
    }

}
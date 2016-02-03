package wf.my.samlib.service.components.impl;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import wf.my.samlib.TestHelper;
import wf.my.samlib.entity.Author;
import wf.my.samlib.entity.Writing;
import wf.my.samlib.service.components.AuthorPageParser;
import wf.my.samlib.service.components.AuthorPageReader;
import wf.my.samlib.storage.AuthorStorage;

import java.util.Date;

public class AuthorCheckerImplTest {

    public static final String URL = "w1u";
    public static final String NAME = "w1n";
    public static final String DESCRIPTION = "w1d";
    public static final Integer SIZE = 100;


    @Spy
    private AuthorCheckerImpl authorUpdater;
    private Date checkDate;
    @Mock
    private AuthorPageReader reader;
    @Mock
    private AuthorPageParser parser;
    @Mock
    private AuthorStorage storage;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        checkDate = TestHelper.SDF.parse("2015.10.25 14:00:00");
        authorUpdater.setWritingChangesChecker(new WritingChangesChecker());
        authorUpdater.setAuthorPageReader(reader);
        authorUpdater.setAuthorPageParser(parser);
        authorUpdater.setAuthorStorage(storage);
    }

    @Test
    public void testUpdateWritingHistoryNew() throws Exception {
        Writing writing = TestHelper.getWriting(URL, NAME, SIZE, DESCRIPTION, checkDate);
        Assert.assertTrue(authorUpdater.updateWritingHistory(writing, null, checkDate));
        Assert.assertTrue(writing.getHistory().isEmpty());
    }

    @Test
    public void testUpdateWritingHistoryNoChangesNoHistory() throws Exception {
        Writing writing = TestHelper.getWriting(URL, NAME, SIZE, DESCRIPTION, checkDate);
        Writing writingOld = TestHelper.getWriting(URL, NAME, SIZE, DESCRIPTION, TestHelper.SDF.parse("2015.09.20 14:00:00"));
        Assert.assertFalse(authorUpdater.updateWritingHistory(writing, writingOld, checkDate));
        Assert.assertTrue(writing.getHistory().isEmpty());
    }

    @Test
    public void testUpdateWritingHistoryNoChangesWithHistory() throws Exception {
        String oldName = "w1n_old";
        Writing writing = TestHelper.getWriting(URL, NAME, SIZE, DESCRIPTION, checkDate);
        Writing writingOld = TestHelper.getWriting(URL, NAME, SIZE, DESCRIPTION, TestHelper.SDF.parse("2015.09.20 14:00:00"));
        writingOld.getHistory().add(TestHelper.getHistoryItem(oldName, null, null, TestHelper.SDF.parse("2015.09.19 14:00:00")));
        Assert.assertFalse(authorUpdater.updateWritingHistory(writing, writingOld, checkDate));
        Assert.assertThat(writing.getHistory(), Matchers.hasSize(1));
        Assert.assertThat(writing.getHistory(),
                Matchers.contains(
                    Matchers.allOf(
                            Matchers.hasProperty("name", Matchers.equalTo(oldName)),
                            Matchers.hasProperty("description", Matchers.nullValue()),
                            Matchers.hasProperty("size", Matchers.nullValue())
                    )
                )
        );
    }

    @Test
    public void testUpdateWritingHistoryWithChangesNoHistory() throws Exception {
        String oldName = "w1n_old";
        String oldDescr = "w1d_old";
        Integer oldSize = 99;
        Writing writing = TestHelper.getWriting(URL, NAME, SIZE, DESCRIPTION, checkDate);
        Writing writingOld = TestHelper.getWriting(URL, oldName , oldSize, oldDescr, TestHelper.SDF.parse("2015.09.20 14:00:00"));
        Assert.assertTrue(authorUpdater.updateWritingHistory(writing, writingOld, checkDate));
        Assert.assertThat(writing.getHistory(), Matchers.hasSize(1));
        Assert.assertThat(writing.getHistory(),
                Matchers.contains(
                        Matchers.allOf(
                            Matchers.hasProperty("name", Matchers.equalTo(oldName)),
                            Matchers.hasProperty("description", Matchers.equalTo(oldDescr)),
                            Matchers.hasProperty("size", Matchers.equalTo(oldSize))
                        )
                )
        );
    }

    @Test
    public void testUpdateWritingHistoryWithChangesWithHistory() throws Exception {
        String oldName = "w1n_old";
        String oldDescr = "w1d_old";
        Integer oldSize = 99;
        Writing writing = TestHelper.getWriting(URL, NAME, SIZE, DESCRIPTION, checkDate);
        Writing writingOld = TestHelper.getWriting(URL, oldName , oldSize, oldDescr, TestHelper.SDF.parse("2015.09.20 14:00:00"));
        writingOld.getHistory().add(TestHelper.getHistoryItem(oldName, null, null, TestHelper.SDF.parse("2015.09.19 14:00:00")));
        Assert.assertTrue(authorUpdater.updateWritingHistory(writing, writingOld, checkDate));
        Assert.assertThat(writing.getHistory(), Matchers.hasSize(2));
        Assert.assertThat(writing.getHistory(),
                Matchers.containsInAnyOrder(
                        Matchers.allOf(
                                Matchers.hasProperty("name", Matchers.equalTo(oldName)),
                                Matchers.hasProperty("description", Matchers.equalTo(oldDescr)),
                                Matchers.hasProperty("size", Matchers.equalTo(oldSize))
                        ),
                        Matchers.allOf(
                                Matchers.hasProperty("name", Matchers.equalTo(oldName)),
                                Matchers.hasProperty("description", Matchers.nullValue()),
                                Matchers.hasProperty("size", Matchers.nullValue())
                        )
                )

        );
    }

    @Test
    public void testUpdateAuthorShouldUpdateHistoryForAllWritings() throws Exception {
        String url = "a1u";
        Date checkDate = TestHelper.SDF.parse("2015.09.20 14:00:00");
        Writing writing1 = TestHelper.getWriting("w1u", "w1n", 100, "w1d", checkDate);
        Writing writing2 = TestHelper.getWriting("w2u", "w2n", 200, "w2d", checkDate);
        Author parsedAuthor = TestHelper.getAuthor(url, "a1", writing1, writing2);
        Author old = TestHelper.getAuthor(url, "a1");
        Mockito.doReturn(parsedAuthor).when(parser).parsePage(Mockito.anyString());
        authorUpdater.updateAuthor(old, checkDate);
        Mockito.verify(authorUpdater).updateWritingHistory(Mockito.eq(writing1), Mockito.any(Writing.class), Mockito.eq(checkDate));
        Mockito.verify(authorUpdater).updateWritingHistory(Mockito.eq(writing2), Mockito.any(Writing.class), Mockito.eq(checkDate));
    }

}
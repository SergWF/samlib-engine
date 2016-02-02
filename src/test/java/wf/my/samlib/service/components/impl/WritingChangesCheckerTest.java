package wf.my.samlib.service.components.impl;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import wf.my.samlib.entity.Writing;
import wf.my.samlib.TestHelper;

import java.util.Date;

public class WritingChangesCheckerTest {


    private static final String URL = "w1u";
    private static final String NAME = "w1n";
    private static final String DESCR = "w1d";
    private static final Integer SIZE = 100;
    private Writing oldWriting;
    private Date checkDate;

    @Before
    public void setUp() throws Exception {
        checkDate = TestHelper.SDF.parse("2015.10.26 14:00:00");
        oldWriting = TestHelper.getWriting(URL, NAME, SIZE, DESCR, TestHelper.SDF.parse("2015.10.25 14:00:00"));

    }

    @Test
    public void testGetChangesNoChanges() throws Exception {
        Writing writing = TestHelper.getWriting(URL, NAME, SIZE, DESCR, checkDate);
        Assert.assertNull(new WritingChangesChecker().getChanges(writing, oldWriting, checkDate));
    }

    @Test
    public void testGetChangesNoOld() throws Exception {
        Writing writing = TestHelper.getWriting(URL, NAME, SIZE, DESCR, checkDate);
        Assert.assertNull(new WritingChangesChecker().getChanges(writing, null, checkDate));
    }

    @Test
    public void testGetChangesName() throws Exception {
        Writing writing = TestHelper.getWriting(URL, NAME + "1", SIZE, DESCR, checkDate);
        Assert.assertThat(new WritingChangesChecker().getChanges(writing, oldWriting, checkDate),
                Matchers.allOf(
                        Matchers.hasProperty("updateDate", Matchers.equalTo(checkDate)),
                        Matchers.hasProperty("name", Matchers.equalTo(NAME)),
                        Matchers.hasProperty("description", Matchers.nullValue()),
                        Matchers.hasProperty("size", Matchers.nullValue())
                )
        );
    }

    @Test
    public void testGetChangesSize() throws Exception {
        Writing writing = TestHelper.getWriting(URL, NAME, SIZE + 10, DESCR, checkDate);
        Assert.assertThat(new WritingChangesChecker().getChanges(writing, oldWriting, checkDate),
                Matchers.allOf(
                        Matchers.hasProperty("updateDate", Matchers.equalTo(checkDate)),
                        Matchers.hasProperty("name", Matchers.nullValue()),
                        Matchers.hasProperty("description", Matchers.nullValue()),
                        Matchers.hasProperty("size", Matchers.equalTo(SIZE))
                )
        );
    }

    @Test
    public void testGetChangesDescription() throws Exception {
        Writing writing = TestHelper.getWriting(URL, NAME, SIZE, DESCR + "1", checkDate);
        Assert.assertThat(new WritingChangesChecker().getChanges(writing, oldWriting, checkDate),
                Matchers.allOf(
                        Matchers.hasProperty("updateDate", Matchers.equalTo(checkDate)),
                        Matchers.hasProperty("name", Matchers.nullValue()),
                        Matchers.hasProperty("description", Matchers.equalTo(DESCR)),
                        Matchers.hasProperty("size", Matchers.nullValue())
                )
        );
    }

    @Test
    public void testGetChangesAll() throws Exception {
        Writing writing = TestHelper.getWriting(URL, NAME + "1", SIZE + 10, DESCR + "1", checkDate);
        Assert.assertThat(new WritingChangesChecker().getChanges(writing, oldWriting, checkDate),
                Matchers.allOf(
                        Matchers.hasProperty("updateDate", Matchers.equalTo(checkDate)),
                        Matchers.hasProperty("name", Matchers.equalTo(NAME )),
                        Matchers.hasProperty("description", Matchers.equalTo(DESCR)),
                        Matchers.hasProperty("size", Matchers.equalTo(SIZE))
                )
        );
    }
}
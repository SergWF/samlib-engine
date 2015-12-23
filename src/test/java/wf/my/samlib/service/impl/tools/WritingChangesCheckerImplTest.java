package wf.my.samlib.service.impl.tools;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import wf.my.samlib.entity.Writing;
import wf.my.samlib.service.helper.TestHelper;

import java.util.Date;

public class WritingChangesCheckerImplTest {

    private WritingChangesCheckerImpl writingChangesChecker;
    private Writing writing_old;
    private Writing writing111;
    private Writing writing211;
    private Writing writing222;
    private Writing writing112;
    private Writing writing121;


    @Before
    public void setUp() throws Exception {
        writingChangesChecker = new WritingChangesCheckerImpl();
        Date updateDate = new Date();
        writing_old = TestHelper.getWriting("http://w111", "old name", "100k", "old description", updateDate);
        writing111 = TestHelper.getWriting("http://w111", "old name", "100k", "old description", updateDate);
        writing211 = TestHelper.getWriting("http://w111", "new name", "100k", "old description", updateDate);
        writing112 = TestHelper.getWriting("http://w111", "old name", "100k", "new description", updateDate);
        writing121 = TestHelper.getWriting("http://w111", "old name", "200k", "old description", updateDate);
        writing222 = TestHelper.getWriting("http://w111", "new name", "200k", "new description", updateDate);
    }

    @Test
    public void testNoChanges() throws Exception {
        Assert.assertFalse(writingChangesChecker.check(writing_old, writing111, new Date()).isChanged());
    }

    @Test
    public void testChangedName() throws Exception {
        Date checkDate = new Date();
        WritingChanges check = writingChangesChecker.check(writing_old, writing211, checkDate);
        Assert.assertThat(check,
                Matchers.allOf(
                        Matchers.hasProperty("checkDate", Matchers.equalTo(checkDate)),
                        Matchers.hasProperty("writing", Matchers.equalTo(writing211)),
                        Matchers.hasProperty("changes", Matchers.hasSize(1)),
                        Matchers.hasProperty("changes",
                                Matchers.hasItem(
                                          Matchers.allOf(
                                            Matchers.hasProperty("paramName", Matchers.equalTo(WritingChanges.ChangeItem.NAME.name())),
                                                  Matchers.hasProperty("oldValue", Matchers.equalTo("old name")),
                                                  Matchers.hasProperty("newValue", Matchers.equalTo("new name"))
                                          )
                                )
                        )
                )
        );
    }
    @Test
    public void testChangedSize() throws Exception {
        Date checkDate = new Date();
        WritingChanges check = writingChangesChecker.check(writing_old, writing121, checkDate);
        Assert.assertThat(check,
                Matchers.allOf(
                        Matchers.hasProperty("checkDate", Matchers.equalTo(checkDate)),
                        Matchers.hasProperty("writing", Matchers.equalTo(writing121)),
                        Matchers.hasProperty("changes", Matchers.hasSize(1)),
                        Matchers.hasProperty("changes",
                                Matchers.hasItem(
                                        Matchers.allOf(
                                                Matchers.hasProperty("paramName", Matchers.equalTo(WritingChanges.ChangeItem.SIZE.name())),
                                                Matchers.hasProperty("oldValue", Matchers.equalTo("100k")),
                                                Matchers.hasProperty("newValue", Matchers.equalTo("200k"))
                                        )
                                )
                        )
                )
        );
    }
    @Test
    public void testChangedDescr() throws Exception {
        Date checkDate = new Date();
        WritingChanges check = writingChangesChecker.check(writing_old, writing112, checkDate);
        Assert.assertThat(check,
                Matchers.allOf(
                        Matchers.hasProperty("checkDate", Matchers.equalTo(checkDate)),
                        Matchers.hasProperty("writing", Matchers.equalTo(writing112)),
                        Matchers.hasProperty("changes", Matchers.hasSize(1)),
                        Matchers.hasProperty("changes",
                                Matchers.hasItem(
                                        Matchers.allOf(
                                                Matchers.hasProperty("paramName", Matchers.equalTo(WritingChanges.ChangeItem.DESCRIPTION.name())),
                                                Matchers.hasProperty("oldValue", Matchers.equalTo("old description")),
                                                Matchers.hasProperty("newValue", Matchers.equalTo("new description"))
                                        )
                                )
                        )
                )
        );
    }
    @Test
    public void testChangedAll() throws Exception {
        Date checkDate = new Date();
        WritingChanges check = writingChangesChecker.check(writing_old, writing222, checkDate);
        Assert.assertThat(check,
                Matchers.allOf(
                        Matchers.hasProperty("checkDate", Matchers.equalTo(checkDate)),
                        Matchers.hasProperty("writing", Matchers.equalTo(writing222)),
                        Matchers.hasProperty("changes", Matchers.hasSize(3)),
                        Matchers.hasProperty("changes",
                                Matchers.hasItems(
                                        Matchers.allOf(
                                                Matchers.hasProperty("paramName", Matchers.equalTo(WritingChanges.ChangeItem.NAME.name())),
                                                Matchers.hasProperty("oldValue", Matchers.equalTo("old name")),
                                                Matchers.hasProperty("newValue", Matchers.equalTo("new name"))
                                        ),
                                        Matchers.allOf(
                                                Matchers.hasProperty("paramName", Matchers.equalTo(WritingChanges.ChangeItem.DESCRIPTION.name())),
                                                Matchers.hasProperty("oldValue", Matchers.equalTo("old description")),
                                                Matchers.hasProperty("newValue", Matchers.equalTo("new description"))
                                        ),
                                        Matchers.allOf(
                                                Matchers.hasProperty("paramName", Matchers.equalTo(WritingChanges.ChangeItem.SIZE.name())),
                                                Matchers.hasProperty("oldValue", Matchers.equalTo("100k")),
                                                Matchers.hasProperty("newValue", Matchers.equalTo("200k"))
                                        )

                                )
                        )
                )
        );
    }
    @Test
    public void testNewWriting() throws Exception {
        Date checkDate = new Date();
        WritingChanges check = writingChangesChecker.check(null, writing222, checkDate);
        Assert.assertThat(check,
                Matchers.allOf(
                        Matchers.hasProperty("checkDate", Matchers.equalTo(checkDate)),
                        Matchers.hasProperty("writing", Matchers.equalTo(writing222)),
                        Matchers.hasProperty("changes", Matchers.hasSize(3)),
                        Matchers.hasProperty("changes",
                                Matchers.hasItems(
                                        Matchers.allOf(
                                                Matchers.hasProperty("paramName", Matchers.equalTo(WritingChanges.ChangeItem.NAME.name())),
                                                Matchers.hasProperty("oldValue", Matchers.nullValue()),
                                                Matchers.hasProperty("newValue", Matchers.equalTo("new name"))
                                        ),
                                        Matchers.allOf(
                                                Matchers.hasProperty("paramName", Matchers.equalTo(WritingChanges.ChangeItem.DESCRIPTION.name())),
                                                Matchers.hasProperty("oldValue", Matchers.nullValue()),
                                                Matchers.hasProperty("newValue", Matchers.equalTo("new description"))
                                        ),
                                        Matchers.allOf(
                                                Matchers.hasProperty("paramName", Matchers.equalTo(WritingChanges.ChangeItem.SIZE.name())),
                                                Matchers.hasProperty("oldValue", Matchers.nullValue()),
                                                Matchers.hasProperty("newValue", Matchers.equalTo("200k"))
                                        )

                                )
                        )
                )
        );
    }

}
package wf.my.samlib.tools;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import wf.my.samlib.entity.Author;
import wf.my.samlib.TestHelper;

public class AuthorToolsTest {


    private Author author;

    @Before
    public void setUp() throws Exception {
        author = TestHelper.getAuthor("url1", "author1",
                TestHelper.getWriting("w1u", "w1n", 100, "w1d", TestHelper.SDF.parse("2015.10.25 14:00:00")),
                TestHelper.getWriting("w2u", "w2n", 100, "w2d", TestHelper.SDF.parse("2015.10.20 14:00:00")),
                TestHelper.getWriting("w3u", "w3n", 100, "w3d", TestHelper.SDF.parse("2015.10.21 14:00:00")),
                TestHelper.getWriting("w4u", "w4n", 100, "w4d", TestHelper.SDF.parse("2015.10.27 14:00:00")),
                TestHelper.getWriting("w5u", "w5n", 100, "w5d", TestHelper.SDF.parse("2015.10.01 14:00:00"))
        );
    }

    @Test
    public void testFindUpdatedWritings() throws Exception {
        Assert.assertEquals(2, AuthorTools.findUpdatedWritings(author, TestHelper.SDF.parse("2015.10.25 14:00:00")).size());
        Assert.assertEquals(1, AuthorTools.findUpdatedWritings(author, TestHelper.SDF.parse("2015.10.27 14:00:00")).size());
        Assert.assertEquals(0, AuthorTools.findUpdatedWritings(author, TestHelper.SDF.parse("2016.01.01 14:00:00")).size());
    }

    @Test
    public void testIsAuthorUpdated() throws Exception {
        Assert.assertTrue(AuthorTools.isAuthorUpdated(author, TestHelper.SDF.parse("2015.10.24 14:00:00")));
        Assert.assertTrue(AuthorTools.isAuthorUpdated(author, TestHelper.SDF.parse("2015.10.27 14:00:00")));
        Assert.assertFalse(AuthorTools.isAuthorUpdated(author, TestHelper.SDF.parse("2016.01.01 14:00:00")));
    }
}
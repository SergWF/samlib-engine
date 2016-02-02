package wf.my.samlib.comparator;

import org.junit.Assert;
import org.junit.Test;
import wf.my.samlib.TestHelper;
import wf.my.samlib.entity.Writing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class UpdateDateComparatorTest {

    @Test
    public void testCompare() throws Exception {
        Writing w1 = new Writing();
        w1.setUpdateDate(TestHelper.SDF.parse("2016.01.12 14:00:00"));
        Writing w2 = new Writing();
        w2.setUpdateDate(TestHelper.SDF.parse("2016.01.12 15:00:00"));
        Writing w3 = new Writing();
        w3.setUpdateDate(TestHelper.SDF.parse("2016.01.12 16:00:00"));
        List<Writing> writings = new ArrayList<>(Arrays.asList(w3, w1, w2));
        Assert.assertEquals(w1, Collections.min(writings, new UpdateDateComparator()));
        Assert.assertEquals(w3, Collections.max(writings, new UpdateDateComparator()));
    }
}
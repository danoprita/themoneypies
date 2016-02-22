package com.themoneypies;

import com.themoneypies.domain.Entry;
import com.themoneypies.report.ReportFilterImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ReportFilterImplTest {
    private ReportFilterImpl reportFilterImpl;

    @Before
    public void setUp() {
        reportFilterImpl = new ReportFilterImpl();
    }

    @Test
    public void removeDuplicates() throws IOException {
        List<Entry> entries1 = TestUtils.getTestEntries("src/test/resources/bt24-credit-card.csv");
        List<Entry> entries2 = TestUtils.getTestEntries("src/test/resources/bt24-credit-card-subset.csv");

        entries1.addAll(entries2);
        List<Entry> unique = reportFilterImpl.removeDuplicates(entries1);

        assertNotNull(unique);
        assertEquals(13, unique.size());
    }

}

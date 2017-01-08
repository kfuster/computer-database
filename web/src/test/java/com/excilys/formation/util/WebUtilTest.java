package com.excilys.formation.util;

import com.excilys.formation.model.util.PageFilter;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Ookami on 08/01/2017.
 */
public class WebUtilTest {
    @Test
    public void toPageFilter() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("page","2");
        params.put("limit","10");
        params.put("search", "test");
        params.put("column", "computerName");
        params.put("order", "ASC");
        PageFilter pageFilter = WebUtil.toPageFilter(params);
        assertNotNull(pageFilter);
        assertTrue(pageFilter.getPageNum() == 2);
        assertTrue(pageFilter.getElementsByPage() == 10);
    }

    @Test
    public void stringToListIds() throws Exception {
        String ids = "1, 2, 3, 4";
        List<Long> listIds = WebUtil.toListIds(ids);
        assertNotNull(listIds);
        assertTrue(listIds.size() == 4);
    }

}
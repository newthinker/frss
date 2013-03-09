/**
 *
 */
package servlet;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import bean.NameUtils;
import bean.TitleUtils;

/**
 * @author Roger Xu
 * 
 */
public class QueryStoreServlet extends JSONServlet {

    private static final long serialVersionUID = -9206692451416013803L;

    private List list = new ArrayList();

    @Override
    public void init() throws ServletException {
        this.list = getParentItems(0, 3000, null);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String parentParam = getParameter(req, "parent");

        if (StringUtils.isNotEmpty(parentParam)) {
            // load child items
            String parentId = parentParam;
            final int childrenCount = 5;
            String sort = (String) session.getAttribute("sort");

            List list = getChildItems(parentId, childrenCount, sort);
            JSONArray children = toJSONArray(list);
            writeResponseAsJSON(children, resp);

        } else {
            // load parent items
            String sortParam = getParameter(req, "sort");

            session.setAttribute("sort", sortParam);

            String startParam = getParameter(req, "start");
            String countParam = getParameter(req, "count");

            int start = NumberUtils.toInt(startParam);
            int count = NumberUtils.toInt(countParam);

            int totalCount = this.list.size();
            sortList(this.list, sortParam);
            List list = subList(this.list, start, count);

            JSONObject json = toJSONDataStoreFormat(list, totalCount);
            writeResponseAsJSON(json, resp);
        }

        return;
    }

    private List getParentItems(int start, int count, String sort) {
        List list = new ArrayList();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int index = start + i;
            String identity = String.format("id_%d", index);
            Map<String, Object> item = getItem(random, identity, "PARENT");
            item.put("position", "Manager");
            int children = random.nextInt(2);
            if (children > 0) {
                item.put("children", true);
            }
            list.add(item);
        }

        sortList(list, sort);

        return list;
    }

    private List getChildItems(String parentId, int count, String sort) {
        List list = new ArrayList();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int index = i;
            String identity = String.format("%s-%d", parentId, index);
            Map<String, Object> item = getItem(random, identity, "CHILD");
            item.put("position", "Staff");
            list.add(item);
        }

        sortList(list, sort);

        return list;
    }

    private void sortList(List list, String sort) {
        if (StringUtils.isEmpty(sort)) {
            return;
        }

        boolean sortDescending = false;
        String sortFieldValue = null;
        if (StringUtils.startsWith(sort, "-")) {
            sortDescending = true;
            sortFieldValue = StringUtils.substring(sort, 1);
        } else {
            sortFieldValue = sort;
        }

        // comparator for Map
        final String sortField = sortFieldValue;
        Comparator comparator = new Comparator() {

            public int compare(final Object o1, final Object o2) {
                Locale locale = Locale.getDefault();

                final Map m1 = (Map) o1;
                final Map m2 = (Map) o2;

                int compare = 0;

                Object v1 = m1.get(sortField);
                Object v2 = m2.get(sortField);

                if (v1 instanceof String && v2 instanceof String) {
                    String value1 = (String) v1;
                    String value2 = (String) v2;
                    Comparator comparator = Collator.getInstance(locale);
                    return comparator.compare(value1.toLowerCase(locale), value2.toLowerCase(locale));
                }

                if (v1 instanceof Number && v2 instanceof Number) {
                    Long value1 = ((Number) v1).longValue();
                    Long value2 = ((Number) v2).longValue();
                    return value1.compareTo(value2);
                }

                if (v1 instanceof Date && v2 instanceof Date) {
                    Date value1 = (Date) v1;
                    Date value2 = (Date) v2;

                    return value1.compareTo(value2);
                }

                Comparable value1 = (Comparable) v1;
                Comparable value2 = (Comparable) v2;

                return value1.compareTo(value2);
            }
        };

        // sort
        if (sortDescending) {
            Collections.sort(list, Collections.reverseOrder(comparator));
        } else {
            Collections.sort(list, comparator);
        }
    }

    private List subList(List list, int start, int count) {
        List result = list.subList(start, start + count);
        return result;
    }

    private Map getItem(Random random, String identity, String type) {
        String name = NameUtils.getRandomName(random);
        String title = TitleUtils.getRandomTitle(random);

        Map<String, Object> item = new HashMap<String, Object>();
        item.put("id", identity);
        item.put("name", name);
        item.put("age", 20 + random.nextInt(40));
        item.put("type", type);
        item.put("title", title);
        String telephone = String.format("690%05d", random.nextInt(99999));
        item.put("telephone", telephone);

        return item;
    }
}
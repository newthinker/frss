/**
 *
 */
package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import bean.NameUtils;
import bean.TitleUtils;

/**
 * @author Roger Xu
 * 
 */
public class ItemFileStoreServlet extends JSONServlet {

    private static final long serialVersionUID = 2553207754058783195L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int num1stLevelItems = 1000;
        int max2ndLevelItems = 10;

        List list = getHierarchicalItems(num1stLevelItems, max2ndLevelItems);
        int totalCount = list.size();

        JSONObject json = toJSONDataStoreFormat(list, totalCount);
        writeResponseAsJSON(json, resp);

        return;
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

    @SuppressWarnings("unchecked")
    private List getHierarchicalItems(int num1stLevelItems, int max2ndLevelItems) {
        List list = new ArrayList();
        Random random = new Random();

        for (int i = 0; i < num1stLevelItems; i++) {
            String identity = String.format("id_%d", i);

            Map level1Item = getItem(random, identity, "PARENT");
            level1Item.put("position", "Manager");
            list.add(level1Item);

            int num2ndLevelItems = random.nextInt(max2ndLevelItems);
            if (num2ndLevelItems > 0) {
                List childrenOfLevel1 = new ArrayList();
                for (int j = 0; j < num2ndLevelItems; j++) {
                    String level2Identity = String.format("%s-%d", identity, j);
                    Map level2Item = getItem(random, level2Identity, "CHILD");
                    level2Item.put("position", "Staff");
                    list.add(level2Item);

                    // update children for level1Item
                    Map _referenceLevel2 = new HashMap();
                    _referenceLevel2.put("_reference", level2Identity);
                    childrenOfLevel1.add(_referenceLevel2);
                }

                level1Item.put("children", childrenOfLevel1);
            }
        }

        return list;
    }

}
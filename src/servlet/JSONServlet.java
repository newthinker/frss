package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Roger Xu
 * 
 */
public class JSONServlet extends HttpServlet {

    private static final long serialVersionUID = 6245372783806720522L;

    protected void writeResponseAsJSON(JSONObject json, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(json.toString());
        out.flush();
        return;
    }

    protected void writeResponseAsJSON(JSONArray json, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(json.toString());
        out.flush();
        return;
    }

    /**
     * @param list
     * @return
     */
    protected JSONObject toJSONDataStoreFormat(List list, int totalCount) {
        JSONArray items = toJSONArray(list);

        JSONObject data = new JSONObject();
        data.put("identifier", "id");
        data.put("label", "name");
        data.put("items", items);
        data.put("numRows", totalCount);

        return data;
    }

    /**
     * @param obj
     * @return
     */
    protected Object toJSON(Object obj) {
        if (obj instanceof Map) {
            return toJSONObject((Map) obj);
        }

        if (obj instanceof List) {
            return toJSONArray((List) obj);
        }

        if (obj instanceof Number || obj instanceof Boolean || obj instanceof String) {
            return obj;
        }

        return null;
    }

    /**
     * @param list
     * @return
     */
    protected JSONArray toJSONArray(List list) {
        JSONArray items = new JSONArray();
        for (Object obj : list) {
            Object item = toJSON(obj);
            if (item != null) {
                items.add(item);
            }
        }

        return items;
    }

    /**
     * @param map
     * @return
     */
    protected JSONObject toJSONObject(Map map) {
        JSONObject json = new JSONObject();
        for (Object key : map.keySet()) {
            Object value = toJSON(map.get(key));
            if (value != null) {
                json.put(key, value);
            }
        }

        return json;
    }

    protected String getParameter(HttpServletRequest req, String name) {
        String param = StringUtils.trimToEmpty(req.getParameter(name));
        return param;
    }
}

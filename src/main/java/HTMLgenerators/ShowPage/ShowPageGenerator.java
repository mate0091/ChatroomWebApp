package HTMLgenerators.ShowPage;

import HTMLgenerators.HTMLGenerator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class ShowPageGenerator<T> extends HTMLGenerator
{
    protected List<T> list;
    private Class<T> type;
    public ShowPageGenerator(List<T> list)
    {
        this.list = list;
        type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public String generate() {
        return body();
    }

    @Override
    protected String title() {
        return "Show " + this.type.getSimpleName();
    }

    @Override
    protected String body() {
        return this.table() +
                "<br>\n" +
                "<br>\n" +
                newRecordBtn();
    }

    protected String table()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("<table class=\"table table-striped table-bordered table-sm\">");
        sb.append("<thead class=\"thead-dark\">");
        sb.append("<tr>");

        Field[] fields = type.getDeclaredFields();
        for (Field f : fields)
        {
            sb.append("<th>");
            sb.append(f.getName());
            sb.append("</th>");
        }

        sb.append("<th>Actions</th>");

        sb.append("</tr>");
        sb.append("</thead>");
        sb.append(this.generateTableBody());
        sb.append("</table>");

        return sb.toString();
    }

    protected String generateTableRow(T obj) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();

        Field[] fields = type.getDeclaredFields();

        fields[0].setAccessible(true);
        int id = (int) fields[0].get(obj);

        for (Field f : fields)
        {
            f.setAccessible(true);
            Object value = f.get(obj);
            sb.append("<td>");
            sb.append(value.toString());
            sb.append("</td>");
        }

        sb.append("<td>\n" +
                "<a href=\"/admin/CRUD?action=edit&table=" + type.getSimpleName() + "&id=" + id + "\"><span class=\"glyphicon glyphicon-pencil\" >Edit</span></a>\n" +
                "<a href=\"/admin/CRUD?action=delete&table=" + type.getSimpleName() + "&id=" + id + "\"><span class=\"glyphicon glyphicon-pencil\" >Delete</span></a>" +
                "</td>\n");

        return sb.toString();
    }

    protected String generateTableBody()
    {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("<tbody>\n");

            for (T obj : this.list) {
                sb.append("<tr>\n");
                sb.append(generateTableRow(obj));
                sb.append("</tr>\n");
            }

            sb.append("</tbody>\n");

            return sb.toString();
        }

        catch (IllegalAccessException e)
        {
            return "VERY HUGE ERROR";
        }
    }

    protected String newRecordBtn()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("<center>");
        sb.append("<a href=\"/admin/CRUD?action=insert&table=");
        sb.append(type.getSimpleName());
        sb.append("\" class=\"btn btn-primary\">Add New Record</a></center>");

        return sb.toString();
    }
}

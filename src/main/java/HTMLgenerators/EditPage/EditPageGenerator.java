package HTMLgenerators.EditPage;

import HTMLgenerators.HTMLGenerator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public class EditPageGenerator<T> extends HTMLGenerator
{
    protected T obj;
    private Class<T> type;
    protected boolean edit;

    public EditPageGenerator(T obj, boolean edit)
    {
        if(obj != null) this.obj = obj;
        this.edit = edit;
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    protected String title() {
        return "Edit " + type.getSimpleName();
    }

    @Override
    protected String body()
    {
        return "<body>\n" +
                this.form() +
                "<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>\n" +
                "</body>";
    }

    private String form()
    {

        StringBuilder sb = new StringBuilder();

        sb.append("<form method=\"POST\" class=\"post-form\" action=\"/admin/CRUD/");
        sb.append("?action=");
        sb.append((edit)? "edit" : "insert");
        sb.append("&table=");
        sb.append(type.getSimpleName());
        sb.append("\">");
        sb.append("<div class=\"container\"><br>");
        sb.append("<div class=\"form-group row\">\n" +
                "                    <label class=\"col-sm-1 col-form-label\"></label>\n" +
                "                    <div class=\"col-sm-4\">\n" +
                "                        <h3>Details</h3>\n" +
                "                    </div>\n" +
                "                </div>");

        sb.append(generateInputFields());

        sb.append("<div class=\"form-group row\">\n" +
                "                    <label class=\"col-sm-1 col-form-label\"></label>\n" +
                "                    <div class=\"col-sm-4\">\n" +
                "                        <button type=\"submit\" class=\"btn btn-success\">Update</button>\n" +
                "                    </div>\n" +
                "                </div>");
        sb.append("</div></form>");

        return sb.toString();
    }

    private String generateInputFields()
    {
        try {
            Field[] fields = type.getDeclaredFields();
            StringBuilder sb = new StringBuilder();

            for (Field f : fields) {

                Object value = null;
                if(edit)
                {
                    f.setAccessible(true);
                    value = f.get(obj);
                }

                sb.append("<div class=\"form-group row\">");
                sb.append("<label class=\"col-sm-2 col-form-label\">");
                sb.append(f.getName());
                sb.append("</label>");
                sb.append("<div class=\"col-sm-4\">");
                sb.append("<input type=\"text\" name=\"");
                sb.append(f.getName());
                sb.append("\" id=\"");
                sb.append("id_");
                sb.append(f.getName());
                sb.append("\" required maxlength=\"20\" value=\"");
                if(edit) sb.append(value.toString());
                sb.append("\"/>");
                sb.append("</div></div>");
            }

            return sb.toString();
        }
        catch (IllegalAccessException e)
        {
            return "ERROR FUCKHEAD";
        }
    }
}

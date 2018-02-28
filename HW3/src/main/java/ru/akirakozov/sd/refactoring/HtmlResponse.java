package ru.akirakozov.sd.refactoring;

import ru.akirakozov.sd.refactoring.checked.CheckedSupplier;

import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

public class HtmlResponse {

    public static void formatHtmlWithResultSet(String template,
                                               HttpServletResponse response,
                                               ResultSet rs,
                                               CheckedSupplier<String> rsExt) {
        formatHtml(template, response, () -> {
            try {
                StringBuilder sb = new StringBuilder();
                while (rs.next()) {
                    sb.append(rsExt.get()).append("<br>");
                }
                return sb.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    public static void formatHtml(String template, HttpServletResponse response, CheckedSupplier<String> content) {
        try {
            response.getWriter().print(String.format(template, content.get()));
            response.setContentType("text/html");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DB;
import ru.akirakozov.sd.refactoring.HtmlResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    private static final String responseTemplate = "<html><body>%s</body></html>";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DB.executeQuery("SELECT * FROM PRODUCT", (ResultSet rs) ->
            HtmlResponse.formatHtmlWithResultSet(responseTemplate, response, rs, () -> {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    return name + "\t" + price;
            })
        );
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

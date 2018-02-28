package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DB;
import ru.akirakozov.sd.refactoring.HtmlResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private static final String responseTemplate = "<html><body>%s</body></html>";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            DB.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", (ResultSet rs) ->
                HtmlResponse.formatHtmlWithResultSet(responseTemplate, response, rs, () -> {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    return "<h1>Product with max price: </h1>" + name + "\t" + price;
                })
            );

        } else if ("min".equals(command)) {
            DB.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", (ResultSet rs) ->
                HtmlResponse.formatHtmlWithResultSet(responseTemplate, response, rs, () -> {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    return "<h1>Product with min price: </h1>" + name + "\t" + price;
                })
            );
        } else if ("sum".equals(command)) {
            DB.executeQuery("SELECT SUM(price) FROM PRODUCT", (ResultSet rs) ->
                HtmlResponse.formatHtmlWithResultSet(responseTemplate, response, rs, () ->
                    "Summary price: " + rs.getInt(1)
                )
            );
        } else if ("count".equals(command)) {
            DB.executeQuery("SELECT COUNT(*) FROM PRODUCT", (ResultSet rs) ->
                HtmlResponse.formatHtmlWithResultSet(responseTemplate, response, rs, () ->
                        "Number of products: " + rs.getInt(1)
                )
            );
        } else {
            response.getWriter().println("Unknown command: " + command);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }

}

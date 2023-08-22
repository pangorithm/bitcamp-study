package bitcamp.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/error")
public class ErrorServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    if (request.getAttribute("refresh") != null) {
      response.setHeader("Refresh", (String) request.getAttribute("refresh"));
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    try {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<title>게시글</title>");
      out.println("</head>");
      out.println("<body>");

      request.getRequestDispatcher("/header").include(request, response);

      out.println("<h1>실행 오류</h1>");

      if (request.getAttribute("message") != null) {
        out.printf("<p>%s</p>\n", request.getAttribute("message"));
      }

      request.getRequestDispatcher("/footer").include(request, response);

      out.println("</body>");
      out.println("</html>");
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

}



import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class SearchVehicleServlet extends HttpServlet {
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    String number = req.getParameter("number");

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/vehicledb", "root", "yourpassword");

      PreparedStatement ps = con.prepareStatement("SELECT * FROM vehicles WHERE vehicle_number = ?");
      ps.setString(1, number);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        out.println("<h2>Vehicle Found:</h2>");
        out.println("<p>Owner: " + rs.getString("owner_name") + "</p>");
        out.println("<p>Email: " + rs.getString("email") + "</p>");
        out.println("<p>Phone: " + rs.getString("phone") + "</p>");
        out.println("<p>Model: " + rs.getString("model") + "</p>");
        out.println("<p>Type: " + rs.getString("vehicle_type") + "</p>");
      } else {
        out.println("<h2>No record found</h2>");
      }
      con.close();
    } catch (Exception e) {
      out.println("<h2>Error: " + e.getMessage() + "</h2>");
    }
  }
}

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class RegisterVehicleServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String owner = req.getParameter("owner");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String number = req.getParameter("number");
        String type = req.getParameter("type");
        String model = req.getParameter("model");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/vehicledb", "root", "yourpassword");

            PreparedStatement check = con.prepareStatement("SELECT * FROM vehicles WHERE vehicle_number = ?");
            check.setString(1, number);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                out.println("<h2>Vehicle already registered!</h2>");
            } else {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO vehicles(owner_name, email, phone, vehicle_number, vehicle_type, model) VALUES(?,?,?,?,?,?)");
                ps.setString(1, owner);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setString(4, number);
                ps.setString(5, type);
                ps.setString(6, model);
                ps.executeUpdate();

                out.println("<h2>Vehicle Registered Successfully!</h2>");
            }
            con.close();
        } catch(Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}

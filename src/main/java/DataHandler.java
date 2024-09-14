import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// No package declaration (default package)

@WebServlet("/DataHandler")
public class DataHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            Connection con = DatabaseConnection.initializeDatabase();
            if ("insert".equalsIgnoreCase(action)) {
                PreparedStatement st = con.prepareStatement("INSERT INTO demo (accountId, accountName) VALUES (?, ?)");
                st.setInt(1, Integer.valueOf(request.getParameter("id")));
                st.setString(2, request.getParameter("name"));
                st.executeUpdate();
                st.close();
                PrintWriter out = response.getWriter();
                out.println("<html><body><b>Successfully Inserted</b></body></html>");
            } 
            else if ("update".equalsIgnoreCase(action)) {
                PreparedStatement st = con.prepareStatement("UPDATE demo SET accountName = ? WHERE accountId = ?");
                st.setString(1, request.getParameter("newName"));
                st.setInt(2, Integer.valueOf(request.getParameter("id")));
                st.executeUpdate();
                st.close();
                PrintWriter out = response.getWriter();
                out.println("<html><body><b>Successfully Updated</b></body></html>");
            } 
            else if ("delete".equalsIgnoreCase(action)) {
                PreparedStatement st = con.prepareStatement("DELETE FROM demo WHERE accountId = ?");
                st.setInt(1, Integer.valueOf(request.getParameter("id")));
                st.executeUpdate();
                st.close();
                PrintWriter out = response.getWriter();
                out.println("<html><body><b>Successfully Deleted</b></body></html>");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            PrintWriter out = response.getWriter();
            out.println("<html><body><b>Error: " + e.getMessage() + "</b></body></html>");
        }
    }
}


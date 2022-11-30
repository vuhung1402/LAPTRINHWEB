package registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		String Repwd = request.getParameter("re_pass");
		String umobile = request.getParameter("contact");
		RequestDispatcher dispatcher = null;
		
		Connection con =null;
		
		if(!(upwd.equals(Repwd))) {
			request.setAttribute("status","invalidConfirmPassword" );
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
			
		}else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://b060c3fa72ae89:0e6af735@us-cdbr-east-06.cleardb.net/heroku_9921352427430fd?reconnect=true","b060c3fa72ae89","0e6af735");
				PreparedStatement pst = con.prepareStatement("insert into users(uname,upwd,uemail,umobile) values(?,?,?,?) ");
				pst.setString(1, uname);
				pst.setString(2, upwd);
				pst.setString(3, uemail);
				pst.setString(4, umobile);
				User userSession =new User(uname,uemail,umobile,""); 
				HttpSession ses = request.getSession();
				ses.setAttribute("userSession",userSession );
				int rowCount = pst.executeUpdate();
				dispatcher = request.getRequestDispatcher("registration.jsp");
				if(rowCount > 0) {
					request.setAttribute("status", "success");
					
				}else {
					request.setAttribute("status", "failed");
				}
				dispatcher.forward(request, response);
				
		}
			catch (Exception e){
				e.printStackTrace();
			}finally {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}//end do get
		
		

}

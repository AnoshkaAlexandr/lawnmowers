package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@SuppressWarnings("serial")
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String login = req.getParameter("login");
		String password = req.getParameter("password");

		if (login.trim() != "" && login.equals("admin") && password.trim() != "" && password.equals("admin")) {
		
			
			HttpSession session = req.getSession();
			session.setAttribute("user", "admin");
			session.setMaxInactiveInterval(60*5);

			
			RequestDispatcher dispatcher = req.getRequestDispatcher("/admin");
			dispatcher.forward(req, resp);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/main");
		dispatcher.forward(req, resp);
	}
}

package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mailSender.Sender;

@SuppressWarnings("serial")
@WebServlet("/mail")
public class SendMail extends HttpServlet {
	private static final String EMAIL_ADRESS = "nosha.bobruysk@gmail.com";
	private static final String EMAIL_PASSWORD = "qqq7603833";
	private static final String EMAIL_TITLE = "Письмо с сайта газонокосилок";

	private static final String ADRESS_OUTBOX = "nosha.bobruysk@gmail.com";
	private static final String ADRESS_INBOX = "nosha_bobruysk@mail.ru";

	private Sender sslSender;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String phone = req.getParameter("phone");

		if (phone != null && phone.trim() != "") {
			sslSender = new Sender(EMAIL_ADRESS, EMAIL_PASSWORD);
			sslSender.send(EMAIL_TITLE, phone, ADRESS_OUTBOX, ADRESS_INBOX);
		}

	}
}

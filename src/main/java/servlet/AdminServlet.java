package servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dao.impl.mysql.ProductDaoMysql;
import domain.Product;
import exception.ServiceException;
import reader.HtmlReader;
import service.IProductService;
import service.productServiceImpl.ProductService;

@SuppressWarnings("serial")
@WebServlet({ "/admin", "/admin/*" })
public class AdminServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(AdminServlet.class);
	IProductService productController = new ProductService(ProductDaoMysql.getImpl());

	List<Product> allProduct;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name = req.getParameter("name");
		String link = req.getParameter("link");
		String description = req.getParameter("description");
		String cost = req.getParameter("cost");

		if (checkValue(name) && checkValue(link) && checkValue(description) && checkValue(cost)
				&& isAdmin(req.getSession())) {
			int costInt = 0;
			try {
				costInt = Integer.parseInt(cost);
			} catch (Exception e) {

			}

			try {
				productController.saveOrUpdate(new Product(name, description, link, costInt));
			} catch (ServiceException e) {
				logger.error("Can't get products " + e.getMessage(), e);
				e.printStackTrace();

			}
		}
		try {
			allProduct = productController.loadAll();
		} catch (ServiceException e) {
			logger.error("Can't get products " + e.getMessage(), e);
		}

		if (isAdmin(req.getSession())) {
			Document doc = Jsoup.parse(HtmlReader.getHeader());
			Element elem = doc.getElementById("forRemove");
			elem.remove();
			Element newelem = doc.getElementById("before");
			newelem.append("<a id=\"forRemove\"class=\"btn btn-primary\" href=\"admin?exit=true\">Выйти</a>");
			HtmlReader.setHeder(doc.html());
		}

		resp.getWriter().println(HtmlReader.header);
		resp.getWriter().println();
		resp.getWriter()
				.append("<div class=\"container\"><h2>Добавление товара</h2><form action=\"admin\" method=\"post\">\n"
						+ "  <div class=\"form-group\">\n"
						+ "    <label for=\"exampleFormControlInput1\">Название товара</label>\n"
						+ "    <input type=\"text\" class=\"form-control\" name=\"name\" placeholder=\"Введите название\">\n"
						+ "  </div>\n" + "  <div class=\"form-group\">\n"
						+ "    <label for=\"exampleFormControlInput1\">Ссылка на картинку</label>\n"
						+ "    <input type=\"text\" class=\"form-control\" name=\"link\" id=\"exampleFormControlInput1\" placeholder=\"Введите URI картинки\">\n"
						+ "  </div>\n" + "  <div class=\"form-group\">\n"
						+ "    <label for=\"exampleFormControlInput1\">Цена</label>\n"
						+ "    <input type=\"text\" class=\"form-control\" name=\"cost\" id=\"exampleFormControlInput1\" placeholder=\"Введите цену\">\n"
						+ "  </div>\n" + "  <div class=\"form-group\">\n"
						+ "    <label for=\"exampleFormControlTextarea1\">Описание товара</label>\n"
						+ "    <textarea class=\"form-control\" name=\"description\" id=\"exampleFormControlTextarea1\" rows=\"3\"></textarea>\n"
						+ "  </div>\n" + "<button type=\"submit\" class=\"btn btn-primary\">Добавить товар</button>\n"
						+ "" + "</form></div>");
		resp.getWriter()
				.append("<div class=\"container\"><h2>Список всех товаров</h2><table class=\"table table-striped\">\n"
						+ "  <thead>\n" + "    <tr>\n" + "      <th scope=\"col\">Название тавара</th>\n"
						+ "      <th scope=\"col\">Описание</th>\n" + "      <th scope=\"col\">Картинка</th>\n"
						+ "      <th scope=\"col\">Действие</th>\n" + "    </tr>\n" + "  </thead>\n" + "  <tbody>\n");
		for (int i = 0; i < allProduct.size(); i++) {
			resp.getWriter().append("    <tr>\n" + "      <th scope=\"row\">" + allProduct.get(i).getName() + "</th>\n"
					+ "      <td><img src=\"" + allProduct.get(i).getImgLink()
					+ "\" width=\"200\" height=\"150\" ></td>\n" + "      <td>" + allProduct.get(i).getDescription()
					+ "</td>\n"
					+ "      <td><button type=\"button\" href=\"#\" class=\"btn btn-danger\"  onclick=\" var xhr = new XMLHttpRequest();xhr.open('DELETE', 'admin/"
					+ allProduct.get(i).getId()
					+ "', false);xhr.send();window.location.href='admin'\">Удалить</button></td></tr>");

		}
		resp.getWriter().append("  </tbody>\n" + "</table></div>");
		resp.getWriter().println(HtmlReader.getFooter());

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (isAdmin(req.getSession())) {

			int id = Integer.parseInt(req.getPathInfo().substring(1));
			List<Product> productForRemuve = allProduct.stream().filter(product -> product.getId() == id)
					.collect(Collectors.toList());
			try {
				productController.remove(productForRemuve.get(0));
			} catch (ServiceException e) {
				logger.error("Can't get products " + e.getMessage(), e);
				e.printStackTrace();

			}

		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String exit = req.getParameter("exit");

		if (checkValue(exit) && exit.equals("true") || !isAdmin(req.getSession())) {

			req.getSession().setAttribute("user", "user");
			req.getSession().setMaxInactiveInterval(1);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/main");
			dispatcher.forward(req, resp);
		} else {
			doPost(req, resp);
		}
	}

	private boolean isAdmin(HttpSession session) {

		try {
			return session.getAttribute("user") != null && session.getAttribute("user").equals("admin");
		} catch (IllegalStateException e) {
			return false;
		}
	}

	private boolean checkValue(String value) {
		return value != null && value.trim() != "";
	}
}

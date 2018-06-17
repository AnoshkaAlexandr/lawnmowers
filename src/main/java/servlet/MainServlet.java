package servlet;

import java.io.IOException;
import java.util.List;

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
@WebServlet("/main")
public class MainServlet  extends HttpServlet{
	private static final Logger logger = Logger.getLogger(MainServlet.class);
	IProductService productController = new ProductService(ProductDaoMysql.getImpl());
	
	 List<Product> allProduct ;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		try {
			allProduct = productController.loadAll();
		} catch (ServiceException e) {
			logger.error("Can't get products " + e.getMessage(), e);
			e.printStackTrace();
		}
		
		if(session.getAttribute("user")!= null && session.getAttribute("user").equals("admin")) {
			Document doc = Jsoup.parse(HtmlReader.getHeader());
			Element elem = doc.getElementById("forRemove");
			elem.remove();
			Element newelem = doc.getElementById("before");
			newelem.append("<a id=\"forRemove\"class=\"btn btn-primary\" href=\"admin\">Welcom Admin</a>");
			HtmlReader.setHeder(doc.html());
		} else {
			// if admin exit, button not changed
			HtmlReader.header = null;

		}

		
		resp.getWriter().println(HtmlReader.getHeader());
		resp.getWriter().append("\n" + 
				"    <!-- Image Showcases -->\n" + 
				"      <div class=\"container-fluid p-0\">\n"+
				"    <section class=\"showcase\">\n" 
				);
				
				for(int i = 0; i < allProduct.size(); i++) {
				
					resp.getWriter().append(	"        <div class=\"row no-gutters\">\n" + 
				"          <div class=\"" + isStileForImage(i) + "\" style=\"background-image: url('"+ allProduct.get(i).getImgLink() +"');\"></div>\n" + 
				"          <div class=\"" + isStileForText(i) + "\">\n" + 
				"            <h2>"+ allProduct.get(i).getName() +"</h2>\n" + 
				"            <p class=\"lead mb-0\">"+ allProduct.get(i).getDescription() +"</p>\n" + 
				"             </br> <p><strong>Цена: </strong>" + allProduct.get(i).getCost() + " BYN</p>  "+
				"          </div>\n" + 
				"        </div>\n" );
				
				}
				
				
				resp.getWriter().append("    </section>\n" + 
						"      </div>\n" + 
				
				"");
		resp.getWriter().append(HtmlReader.getFooter());

	}
	
	private String isStileForImage(int i) {
		return i%2 == 0?"col-lg-6 order-lg-2 text-white showcase-img":"col-lg-6 text-white showcase-img";
	}
	
	private String isStileForText(int i) {
		return i%2 == 0?"col-lg-6 order-lg-1 my-auto showcase-text":"col-lg-6 my-auto showcase-text";
	}

}

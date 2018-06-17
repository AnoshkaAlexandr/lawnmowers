package dao.impl.List;

import java.util.ArrayList;
import java.util.List;

import dao.IProductDao;
import domain.Product;

public class ProductDaoInList implements IProductDao {

	private List<Product> allProduct;
	private static ProductDaoInList pci;

	private ProductDaoInList() {
		allProduct = new ArrayList<>();
		allProduct.add(new Product("Аккумуляторные и электрические газонокосилки", "Легкость и маневренность\n"
				+ "Бесшумность и редкое техобслуживание\n" + "Соответствие стандартам техники безопасности",
				"https://www.obi.ru/sovety/garden-and-leisure/landscape-gardening/lawn-mower-advisor/images/akku_elektrorasenmaeher.jpg",
				300));
		allProduct.add(new Product("Шпиндельная газонокосилка Fiskars Stay Sharp TM",
				"Инновационный прибор класса Premium\n" + "Невыступающие колеса для стрижки вблизи краев",
						
				"https://www.obi.ru/sovety/garden-and-leisure/landscape-gardening/lawn-mower-advisor/images/fiskars_pindelmaeher.jpg",
				200));
		allProduct.add(new Product("Бензиновые газонокосилки", "Мощные бензиновые двигатели от лучших производителей\n"
				+ "Высокая плавность хода\n" + "Соответствие стандартам техники безопасности\n" + "Легкий запуск\n"
				,
				"https://www.obi.ru/sovety/garden-and-leisure/landscape-gardening/lawn-mower-advisor/images/benzinrasenmaeher.jpg",
				500));
		allProduct.add(new Product("Газонокосилка тракторного типа LUX RT 155-92 HW с полным зимним оснащением",
				"Включает оснащение для зимнего использования.",
				"https://www.obi.ru/sovety/garden-and-leisure/landscape-gardening/lawn-mower-advisor/images/rasentraktor_mit-kompletter_winterausruestung.jpg",
				800));

	}

	public static ProductDaoInList getImplementation() {
		if (pci == null) {
			pci = new ProductDaoInList();
		}
		return pci;
	}

	@Override
	public List<Product> loadAll() {
		return allProduct;
	}

	@Override
	public boolean add(Product product) {

		try {
			allProduct.add(product);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean remove(Product product) {
		try {
			allProduct.remove(product);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}

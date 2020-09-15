import ru.erasko.service.ProductService;
import ru.erasko.service.ProductServiceWs;

import ru.erasko.service.interf.CategoryRepr;
import ru.erasko.service.interf.ProductRepr;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws MalformedURLException {

        URL url = new URL("http://localhost:8080/first-jsf-app/ProductService/ProductServiceImpl?wsdl");

        ProductService productService = new ProductService(url);

        ProductServiceWs port = productService.getProductServiceImplPort();

        System.out.println("All product: ");
        port.findAll().forEach(todo -> System.out.println(todo.getId() + ": " + todo.getName()));

        System.out.println("\nProduct by id = 3:");
        ProductRepr prById = port.getProductById(3L);
        System.out.println(prById.getId() + ": " + prById.getName() +
                ", " + prById.getPrice() + ", " + prById.getCategoryName());

        System.out.println("\nProduct by name = Apple iPad:");
        ProductRepr prByName =port.getProductByName("Apple iPad");
        System.out.println(prByName.getId() + ": " + prByName.getName() +
                ", " + prByName.getPrice() + ", " + prByName.getCategoryName());

        System.out.println("\nProduct by category id = 2:");
        port.getProductsByCategoryId(2L)
                .forEach(prod -> System.out.println(prod.getId() + ": " + prod.getName()));

        System.out.println("\nInsert product:");
        ProductRepr productRepr = new ProductRepr();
        productRepr.setId(null);
        productRepr.setName("Samsung-7");
        productRepr.setDescription("Description");
        productRepr.setCategoryId(2L);
        productRepr.setCategoryName("Tablet");
        port.insert(productRepr);

        System.out.println("\nAll product: ");
        port.findAll().forEach(todo -> System.out.println(todo.getId() + ": " + todo.getName()));

        System.out.println("\nDelete product:");
        port.delete(7L);

        System.out.println("\nAll product: ");
        port.findAll().forEach(todo -> System.out.println(todo.getId() + ": " + todo.getName()));

        System.out.println("\nInsert category: ");
        CategoryRepr categoryRepr = new CategoryRepr();
        categoryRepr.setId(null);
        categoryRepr.setName("Phone");
        port.insertCategory(categoryRepr);

    }
}

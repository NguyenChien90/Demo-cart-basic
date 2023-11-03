package ra.view.acount;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Cart;
import ra.model.Catalogs;
import ra.model.Products;
import ra.model.Users;
import ra.sevice.cart.CartServiceIMPL;
import ra.sevice.cart.ICartService;
import ra.sevice.catalog.CatalogIMPL;
import ra.sevice.catalog.ICatalogService;
import ra.sevice.product.IProductService;
import ra.sevice.product.ProductServiceIMPL;
import ra.view.Home;

import java.util.HashMap;

import static ra.config.Color.*;

public class UserManager {
    IProductService productService = new ProductServiceIMPL();
    ICatalogService catalogService = new CatalogIMPL();
    ICartService cartService = new CartServiceIMPL();


    public void menuUser() {
        do {
//                    System.out.println("\nXin chao: "+ Home.userLogin.getName());
            System.out.println("\nXin chao: " + new Config<Users>().readFile(Config.URL_USER_LOGIN).getName());
            System.out.println(YELLOW_BOLD_BRIGHT + "\n  .--------------------------------------------------------.");
            System.out.println("  |                       TRANG NGUOI DUNG                 |");
            System.out.println("  |--------------------------------------------------------|");
            System.out.println("  |                     1. DANH SACH SAN PHAM              |");
            System.out.println("  |                     2. TIM KIEM SAN PHAM               |");
            System.out.println("  |                     3. THONG TIN NGUOI DUNG            |");
            System.out.println("  |                     4. DOI MAT KHAU                    |");
            System.out.println("  |                     5. DAT HANG                        |");
            System.out.println("  |                     6. GIO HANG                        |");
            System.out.println("  |                     0. THOAT TAI KHOAN        (OK)     |");
            System.out.println("  '--------------------------------------------------------'\n" + RESET);
            System.out.print("Lựa chọn (0/1/2/3/4/5/6): ");
            switch (Validate.validateInt()) {
                case 1:
                    System.out.println("DANH SACH DANH MUC");
                    for (Catalogs catalogs : catalogService.findAll()) {
                        System.out.println(catalogs);
                    }
                    System.out.println("DANH SACH SAN PHAM");
                    for (Products products : productService.findAll()) {
                        System.out.println(products);
                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    orderProducts();
                    break;
                case 6:
                    myCart();
                    break;
                case 0:
//                            Home.userLogin = null;
                    new Config<Users>().writeFile(Config.URL_USER_LOGIN, null);
                    new Home().menuHome();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void myCart() {
        // Hiển thị danh sách các sản phẩm đang có trong giỏ hàng
        System.out.println(".------------------------------------------------.");
        System.out.printf( "|       %20s's CART              |\n",new Config<Users>().readFile(Config.URL_USER_LOGIN).getName());
        System.out.println("|------------------------------------------------|");
        System.out.printf( "| %s |   %s   |  %s  |     %s      |\n", "Mã SP", "Tên SP", "SỐ LƯỢNG", "GIÁ");
        System.out.println("|-------|------------|------------|--------------|");
        Cart cart = cartService.findCartByUserLogin();
        if (cart == null){
            System.out.println("|Giỏ hàng trống                                  |");
            System.out.println("'------------------------------------------------'");

            return;
        }
        for (int idPro : cart.getProductCart().keySet()) {
            Products products = productService.findById(idPro);
            System.out.printf("|%4d   |     %-7s|     %-4d   |%12.2f  |\n",
                    idPro,products.getProductName(),cart.getProductCart().get(idPro),products.getPrice());
        }
        System.out.println("'------------------------------------------------'");
        System.out.println("Tổng tiền: ");
        System.out.println(".------------------------------------------------.");
        System.out.println("|   1.Đặt hàng  |  2.Thay đổi SP  | 3.Quay lại   |");
        System.out.println("'------------------------------------------------'");
        System.out.println("Tự làm đê =))");

    }

    private void orderProducts() {
        System.out.println("------ DANH SACH SAN PHAM DANG BAN -------");
        System.out.println(".------------------------------------------------.");
        System.out.printf("| %s |   %s   |  %s  |     %s      |\n", "Mã SP", "Tên SP", "SỐ LƯỢNG", "GIÁ");
        for (Products products : productService.findAll()) {
            if (products.isStatus()) {
                System.out.printf("|%4d   |  %-10s|     %-4d   |%12.2f  |\n", products.getProductId(),
                        products.getProductName(), products.getStock(), products.getPrice());
            }
        }
        System.out.println("'------------------------------------------------'");
        System.out.println(".------------------------------------------------.");
        System.out.println("| Chọn ID của sản phẩm đặt hàng | Chọn 0 : Thoát |");
        System.out.println("'------------------------------------------------'");
        System.out.print("  Nhập ID: ");
        int idBuy = Validate.validateInt();
        Products productBuy = productService.findById(idBuy);
        if (idBuy == 0){
            return;
        }
        if (productBuy == null){
            System.out.println("Không tồn tại sản phẩm theo Id vừa nhập");
        }else {
            // khi đặt hàng trừ luôn số lượng trong danh sách sản phẩm
//            int newStock = productBuy.getStock() - 1;
//            productBuy.setStock(newStock);
//            productService.save(productBuy);
            Cart cart = cartService.findCartByUserLogin();
            Users userLogin = new Config<Users>().readFile(Config.URL_USER_LOGIN);
            if (cart == null){
                cart = new Cart(cartService.getNewId(),userLogin.getId(),new HashMap<>(),false);
            }
            if (cart.getProductCart().containsKey(idBuy)){
                cart.getProductCart().put(idBuy,cart.getProductCart().get(idBuy) +1 );
            }else {
                cart.getProductCart().put(idBuy,1);
            }
            cartService.save(cart);
            System.out.println("Đã thêm SP vào giỏ hàng thành công");

        }
        // In ra danh cart để test
        for (Cart cart: cartService.findAll()) {
            System.out.println(cart);
        }
        orderProducts();
    }
}

package ra.view.acount;

import ra.config.Config;
import ra.config.Validate;
import ra.model.Users;
import ra.view.Home;

import static ra.config.Color.RESET;
import static ra.config.Color.YELLOW_BOLD_BRIGHT;

public class AdminManager {
    public void menuAmin() {
        do {

//            System.out.println("\nXin chao: "+ Home.userLogin.getName());
            System.out.println("\nXin chao: "+ new Config<Users>().readFile(Config.URL_USER_LOGIN).getName());
            System.out.println(YELLOW_BOLD_BRIGHT + "\n                                   .--------------------------------------------------------.");
            System.out.println("                                   |                         TRANG QUAN LY                  |");
            System.out.println("                                   |--------------------------------------------------------|");
            System.out.println("                                   |                     1. QUAN LY NGUOI DUNG              |");
            System.out.println("                                   |                     2. QUAN LY DANH MUC                |");
            System.out.println("                                   |                     3. QUAN LY SAN PHAM                |");
            System.out.println("                                   |                     4. QUAN LY DON HANG                |");
            System.out.println("                                   |                     0. THOAT TAI KHOAN      (OK)       |");
            System.out.println("                                   '--------------------------------------------------------'\n" + RESET);

            System.out.print("Lựa chọn (0/1/2/3/4): ");

            switch (Validate.validateInt()) {
                case 0:
                    new Config<Users>().writeFile(Config.URL_USER_LOGIN,null);
                    new Home().menuHome();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

}

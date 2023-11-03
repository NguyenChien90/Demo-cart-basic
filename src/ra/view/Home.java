package ra.view;

import ra.config.Config;
import ra.config.Validate;
import ra.model.RoleName;
import ra.model.Users;
import ra.sevice.user.IUserService;
import ra.sevice.user.UserServiceIMPL;
import ra.view.acount.AdminManager;
import ra.view.acount.UserManager;

import static ra.config.Color.*;

public class Home {
    public static void main(String[] args) {
        Users userLogin = new Config<Users>().readFile(Config.URL_USER_LOGIN);
        if (userLogin != null) {
            new Home().checkRoleLogin(userLogin);
        } else {
            new Home().menuHome();
        }
    }

    IUserService userService = new UserServiceIMPL();
    public static Users userLogin;
    public static Config<Users> config = new Config<>();

    public void menuHome() {
        //
        do {
            // hiển thị danh sách người dùng để test
            System.out.println("DANH SACH NGUOI DUNG (test)");
            for (Users users : userService.findAll()) {
                System.out.println(users);
            }
            System.out.println(YELLOW_BOLD_BRIGHT + "\n                                   .--------------------------------------------------------.");
            System.out.println("                                   |                         TRANG CHU                      |");
            System.out.println("                                   |--------------------------------------------------------|");
            System.out.println("                                   |                       1. DANG NHAP        (OK          |");
            System.out.println("                                   |                       2. DANG KY          (OK)         |");
            System.out.println("                                   |                       0. BACK             (OK)         |");
            System.out.println("                                   '--------------------------------------------------------'\n" + RESET);

            System.out.print("Lựa chọn (0/1/2): ");
            switch (Validate.validateInt()) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    private void login() {
        System.out.println("*** FORM LOGIN ***");
        System.out.println("Nhap ten tai khoan: ");
        String username = Validate.validateString();
        System.out.println("Nhap mat khau: ");
        String pass = Validate.validateString();
        Users users = userService.checkLogin(username, pass);

        //kiem tra
        if (users == null) {
            System.out.println("Sai ten tai khoan hoac mat khau");
        } else {
            //dung ten tk voi mk
            checkRoleLogin(users);
            if (!users.isStatus()) {
                System.out.println("Dang nhap thanh cong");
            }
        }
    }

    public void checkRoleLogin(Users users) {
        if (users.getRole().equals(RoleName.AMIN)) {
//                userLogin = users;
            config.writeFile(Config.URL_USER_LOGIN, users);// ghi doi tuong Users đang đăng nhập vào file
            // chuyen den trang quanr ly ADMIN
            new AdminManager().menuAmin();
        } else {
            if (users.isStatus()) {
//                    userLogin = users;
                config.writeFile(Config.URL_USER_LOGIN, users);// ghi doi tuong Users đang đăng nhập vào file
                // chuyen den trang user
                new UserManager().menuUser();
            } else {
                System.out.println("Tai khoan cua ban dang bi khoa");
            }
        }
    }


    private void register() {
        System.out.println("*** FORM REGISTER ***");
        Users users = new Users();
        users.setId(userService.getNewId());
        System.out.println("Id: " + users.getId());
        System.out.println("Nhap ho ten: ");
        users.setName(Validate.validateString());
        System.out.println("Nhap ten tai khoan");
        while (true) {
            String username = Validate.validateString();
            if (userService.existUsername(username)) {
                System.out.println("Ten dang nhap da ton tai moi nhap lai");
            } else {
                users.setUsername(username);
                break;
            }
        }
        System.out.println("Nhap mat khau");
        users.setPassword(Validate.validateString());
        System.out.println("Nhap lai mat khau");
        while (true) {
            String repeatPass = Validate.validateString();
            if (users.getPassword().equals(repeatPass)) {
                break;
            } else {
                System.out.println("Mat khau khong khop moi nhai lai");
            }
        }
        System.out.println("Nhap email");
        while (true) {
            String email = Validate.validateEmail();
            if (userService.existEmail(email)) {
                System.out.println("Email da ton tai moi nhap lai");
            } else {
                users.setEmail(email);
                break;
            }
        }
        userService.save(users);
        System.out.println("Tao tai khoan thanh cong");

    }

}

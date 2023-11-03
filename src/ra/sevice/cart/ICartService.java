package ra.sevice.cart;

import ra.model.Cart;
import ra.sevice.IGenericService;

public interface ICartService extends IGenericService<Cart> {
    Cart findCartByUserLogin();
}

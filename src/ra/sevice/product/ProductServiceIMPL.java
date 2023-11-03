package ra.sevice.product;

import ra.config.Config;
import ra.model.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceIMPL implements IProductService{
    static Config<List<Products>> config = new Config<>();
    static List<Products> productsList;
    static {
        productsList = config.readFile(Config.URL_PRODUCT);
        if (productsList == null){
            productsList = new ArrayList<>();
//            productsList.add(new Products(1, "SP1", 1, "SP1",10000, 10, true));
//            productsList.add(new Products(2, "SP2", 1, "SP2",50000, 10, true));
//            productsList.add(new Products(3, "SP3", 3, "SP3",30000, 10, true));
//            productsList.add(new Products(4, "SP4", 3, "SP4",40000, 10, true));
//            productsList.add(new Products(5, "SP5", 2, "SP5",1000, 10, true));
//            productsList.add(new Products(6, "SP6", 2, "SP6",20000, 10, true));
//            config.writeFile(Config.URL_PRODUCT, productsList);
        }
    }

    @Override
    public List<Products> findAll() {
        return productsList;
    }

    @Override
    public void save(Products products) {
        if (findById(products.getProductId()) == null){
            productsList.add(products);
            updateData();
        }else {
            productsList.set(productsList.indexOf(products),products);
            updateData();
        }

    }

    @Override
    public void delete(int id) {
        productsList.remove(findById(id));
        updateData();
    }

    @Override
    public Products findById(int id) {
        for (Products products : productsList){
            if (products.getProductId() == id){
                return products;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Products product : productsList) {
            if (product.getProductId() > idMax){
                idMax = product.getProductId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_PRODUCT,productsList);
    }
}

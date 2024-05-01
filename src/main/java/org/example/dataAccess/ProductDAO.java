package org.example.dataAccess;

import org.example.model.Product;

public class ProductDAO extends AbstractDAO<Product>{
    public void updateQuantity(int productId, int soldQuantity) {
        Product product = findById(productId);
        if(product != null) {
            product.setCantitate(product.getCantitate() - soldQuantity);
            update(product);
        }
    }
}

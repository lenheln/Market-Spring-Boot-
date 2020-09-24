package com.geekbrains.geekmarket.services;

import com.geekbrains.geekmarket.entities.Items;
import com.geekbrains.geekmarket.entities.Orders;
import com.geekbrains.geekmarket.entities.Orders;
import com.geekbrains.geekmarket.entities.Product;
import com.geekbrains.geekmarket.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class CartService {

    private ProductService productService;
    private ItemService itemService;
    private OrderService orderService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }


    public ShoppingCart getCurrentCart(HttpSession httpSession) {
        ShoppingCart shoppingCart = (ShoppingCart)httpSession.getAttribute("cart");
        if(shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            httpSession.setAttribute("cart", shoppingCart);
        }
        return shoppingCart;
    }

    public void resetCart(HttpSession httpSession) {
        httpSession.removeAttribute("cart");
    }

    public void addToCart(HttpSession httpSession, Long productId) {
        Product product = productService.getProductById(productId);
        addToCart(httpSession, product);
    }

    public void addToCart(HttpSession httpSession, Product product) {
        ShoppingCart shoppingCart = getCurrentCart(httpSession);
        shoppingCart.addProduct(product);
    }

    public void removeFromCart(HttpSession httpSession, Long productId){
        Product product = productService.getProductById(productId);
        removeFromCart(httpSession, product);
    }

    public void removeFromCart(HttpSession httpSession, Product product){
        ShoppingCart shoppingCart = getCurrentCart(httpSession);
        shoppingCart.remove(product);
    }

    public void setProductCount(HttpSession httpSession, Long productId, Long quantity){
        Product product = productService.getProductById(productId);
        setProductCount(httpSession, product, quantity);
    }

    public void setProductCount(HttpSession httpSession, Product product, Long quantity){
        ShoppingCart shoppingCart = getCurrentCart(httpSession);
        shoppingCart.setQuantity(product,quantity);
    }

    public double getTotalCost(HttpSession httpSession) {
        ShoppingCart shoppingCart = getCurrentCart(httpSession);
        return shoppingCart.getTotalCost();
    }

    public void makeOrder(HttpSession httpSession){
        ShoppingCart shoppingCart = getCurrentCart(httpSession);
        List<Items> items = shoppingCart.getItems();
        Orders newOrder = new Orders();

//        newOrder.setItemsList(items);
        orderService.saveOrder(newOrder);

        for (Items o: items) {
            o.setOrder(newOrder);
            itemService.saveItem(o);
        }
    }
}

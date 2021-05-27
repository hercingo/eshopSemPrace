package upce.semprace.eshop.services;

import org.springframework.stereotype.Service;
import upce.semprace.eshop.entity.NakoupenaPolozka;
import upce.semprace.eshop.entity.Nakup;
import upce.semprace.eshop.entity.Produkt;
import upce.semprace.eshop.repository.NakoupenaPolozkaRepository;
import upce.semprace.eshop.repository.NakupRepository;
import upce.semprace.eshop.repository.ProduktRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {


    private Map<Produkt, Integer> cart;

    private final ProduktRepository productRepository;
    private final NakupRepository orderRepository;
    private final NakoupenaPolozkaRepository orderHasProductRepository;

    public ShoppingCartServiceImpl(ProduktRepository productRepository, NakupRepository orderRepository, NakoupenaPolozkaRepository orderHasProductRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderHasProductRepository = orderHasProductRepository;
        cart = new HashMap<>();
    }

    @Override
    public void add(Long id) {
        Produkt product = productRepository.findById(id).orElseThrow(NoSuchElementException::new);
        if (cart.containsKey(product)) {
            cart.replace(product, cart.get(product) + 1);
        } else {
            cart.put(product, 1);
        }
    }

    @Override
    public void remove(Long id) {
        Produkt product = productRepository.findById(id).orElseThrow(NoSuchElementException::new);
        if (cart.containsKey(product)) {
            if (cart.get(product) > 1) {
                cart.replace(product, cart.get(product) - 1);
            } else {
                cart.remove(product);
            }
        }
    }

    @Override
    public Map<Produkt, Integer> getCart() {
        return cart;
    }

    @Override
    public void checkout() {
        Nakup order = new Nakup();
        orderRepository.save(order);

        for (Map.Entry<Produkt, Integer> entry : cart.entrySet()) {
            NakoupenaPolozka orderHasProduct = new NakoupenaPolozka();
            //orderHasProduct.setOrder(order);
            //orderHasProduct.setProduct(entry.getKey());
            orderHasProduct.getMnozstvi(entry.getValue());
            orderHasProductRepository.save(orderHasProduct);
        }

        cart.clear();

    }
}
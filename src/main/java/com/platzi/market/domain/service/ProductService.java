package com.platzi.market.domain.service;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.getAll();
    }

    public Optional<Product> getProduct(int productId){
        return productRepository.getProduct(productId);
    }

    public Optional<List<Product>> getCategory(int categoryId){
        return productRepository.getByCategory(categoryId);
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    //Buscamos un producto. Si existe elproducto entonces eliminalo y retorna true, de lo contario retorna false
    public boolean delete(int productId){
        //llamamos o buscamos primero el producto a eliminar
        return getProduct(productId).map(product ->{
            productRepository.delete(productId);
            return true;
        }).orElse(false);

        //2da forma
//        if(getProduct(productId).isPresent()){
//            productRepository.delete(productId);
//            return true;
//        }
//        else{
//            return false;
//        }
    }
}

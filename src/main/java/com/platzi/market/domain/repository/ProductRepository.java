package com.platzi.market.domain.repository;

import com.platzi.market.domain.Product;

import java.util.List;
import java.util.Optional;

/*Esta interface permite indicarle a todos los repositorios como debe de comportarse cuando se hable en terminos
* de productos
* Especificamos los metodos que cualquier repositorio que quiera trabajar con productos tendra que implementar
* */
public interface ProductRepository {
    /*Definimos las reglas que tendrá el repositorio de nuestro dominio al momento que cualquier repository
    * quiera acceder a productos en la BD, esto nos permite no acoplar nuestra solucion a una BD especifica
    *  sino que siempre estemos hablando en terminos de dominio, en este caso de Product*/
    List<Product> getAll();
    Optional<List<Product>> getByCategory(int categoryId);
    Optional<List<Product>> getProductsWithLessStock(int quantity);
    Optional<Product> getProduct(int productId);
    Product save(Product product);
    void delete(int productId);
}

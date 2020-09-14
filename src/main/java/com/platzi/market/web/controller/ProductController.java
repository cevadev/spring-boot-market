package com.platzi.market.web.controller;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    /*En lugar de que getAll() responda con una lista de prodcutos haremos que el metodo responsa con la clase
    * ResponseEntity*/
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll(){
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") int productId){
        /*¿Cuándo no se ejecuta el map() -> cuando dentro del Optional<> de getProducto() no existe o no hay ningun producto
        * es decir el producto no existe
        * orElse() -> se ejecuta cuando el map() no ocurre
        * de esta forma cuando no exista un producto el api no  va a responder null o undefined sino que responder not found */
        return productService.getProduct(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("categoryId") int categoryId){
        return productService.getCategory(categoryId)
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<Product> save(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") int productId){
        //validamos si se puedo eliminar el producto
        if(productService.delete(productId)){
            return new ResponseEntity(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    //Llamamos a los metodos de ProductService
//    @GetMapping("/all")
//    public List<Product> getAll(){
//        return productService.getAll();
//    }

//    @GetMapping("/{id}")
//    public Optional<Product> getProduct(@PathVariable("id") int productId){
//        return productService.getProduct(productId);
//    }

    //adicionamos una modificacion en el path para que spring sepa a que metodo llamar ya que se parece al metodo getProduct
//    @GetMapping("/category/{categoryId}")
//    public Optional<List<Product>> getByCategory(@PathVariable("categoryId") int categoryId){
//        return productService.getCategory(categoryId);
//    }

//    @PostMapping("/save")
//    public Product save(@RequestBody Product product){
//        return productService.save(product);
//    }

//    @DeleteMapping("/delete/{id}")
//    public boolean delete(@PathVariable("id") int productId){
//        return productService.delete(productId);
//    }

}

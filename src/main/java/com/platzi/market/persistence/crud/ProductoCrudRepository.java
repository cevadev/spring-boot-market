package com.platzi.market.persistence.crud;

import com.platzi.market.persistence.entities.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {
    /*Query methods*/
    //Obtener toda la lista de productos que pertenecen a una categoria en especifico y ordenamos los productos ASC por el nombre
    //Se debe respetar el nombre del metodo IdCategoria representa el atributo por el cual se filtraran los productos
    //recibe como parametro el idCategoria que se debe escribir tal como fue definido dentro de la clase Producto.

    List<Producto> findByIdCategoriaOrderByNombreAsc(int idCategoria);

    //obtenemos una lista de productos cuyo stock sea menor a (debemos utilizar las palabras claves de spring data)
    //recordemos que los parametros se deben llamar igual que los campos en la tabla
    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidadStock, boolean estado);

    /*Otra forma valida*/
    //@Query(value = "SELECT * FROM productos WHERE id_categoria = ?", nativeQuery = true)
    //List<Producto> getProductByCategoria(int idCategoria);
}

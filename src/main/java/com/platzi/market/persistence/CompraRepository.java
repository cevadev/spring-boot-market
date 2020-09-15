package com.platzi.market.persistence;

import com.platzi.market.domain.Purchase;
import com.platzi.market.domain.repository.PurchaseRepository;
import com.platzi.market.persistence.crud.CompraCrudRepository;
import com.platzi.market.persistence.entities.Compra;
import com.platzi.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {

    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        //map() nos ayuda para operar con lo que sea que venga dentro del Optional (si viene algo)
        //si no hay nada en el Optional pues no se ejecuta
        return compraCrudRepository.findByIdCliente(clientId)
                .map(compras -> mapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        //1. Convertimos un Purchase a Compra
        Compra compra = mapper.toCompra(purchase);
        //2. Debemos garantizar que toda la información se va a guardar en cascada
        //2.1 Para guardarse en cascada debemos aseguraros de que compra conoce los productos y los
        //    producto saben a que compra pertenecen
        //2.2 A cada producto que recorremos con el foreach le asignamos la compra con la finalidad de que
        //compras ya conoce sus productos ahora le decimos a productos a que compra pertenecen
        compra.getProductos().forEach(producto -> producto.setCompra(compra));
        return mapper.toPurchase(compraCrudRepository.save(compra));
    }
}

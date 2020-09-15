package com.platzi.market.domain.repository;

import com.platzi.market.domain.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository {
    List<Purchase> getAll();

    //usamos optional ya que puede ser que el cliente consultado no hay realizado una compra
    Optional<List<Purchase>> getByClient(String clientId);

    Purchase save(Purchase purchase);
}

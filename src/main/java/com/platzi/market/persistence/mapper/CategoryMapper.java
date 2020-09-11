package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.Category;
import com.platzi.market.persistence.entities.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    //obtenemos un objeto Category del mapper, es decir convertimos una Categoria a Category
    @Mappings({
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "descripcion", target = "category"),
            @Mapping(source = "estado", target = "active"),
    })
    Category toCategory(Categoria categoria);

    //indicamo que la conversion que vamos a realizar es la inversa a la conversion de arriba
    @InheritInverseConfiguration
    @Mapping(target = "productos", ignore = true) //la Clase Category no tiene el atributo products por ello lo ignoramos
    Categoria toCategoria(Category category);
}

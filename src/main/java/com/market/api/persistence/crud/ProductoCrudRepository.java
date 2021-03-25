package com.market.api.persistence.crud;

import com.market.api.persistence.entities.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {
    /*@Query(value = "select * from productos where id_categoria = ?", nativeQuery = true)
    List<Producto> getProductosByCategoria(int idCategoria);*/

    List<Producto> findByIdCategoriaOrderByNombreAsc(int idCategoria);

    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidadStock, boolean estado);
}

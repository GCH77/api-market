package com.market.api.persistence;

import com.market.api.domain.Product;
import com.market.api.domain.repositories.ProductRepository;
import com.market.api.persistence.crud.ProductoCrudRepository;
import com.market.api.persistence.entities.Producto;
import com.market.api.persistence.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {
//    Si dejamos tal cual el codigo, nos arrojara el error NullPointerException por que en ningun momento instanciamos
//    productoCrudRepository y productMapper, por tanto no podemos hacer uso de sus metodos
//    Autowired: inyecta una dependencia unicamente del tipo Component o Bean de Spring
//    y el realiza internamente la creacion del objeto para poder usar sus metodos sin problemas
    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getAll() {
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return productMapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(productMapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarseProduct(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        return productos.map(productos1 -> productMapper.toProducts(productos1));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId).map(producto -> productMapper.toProduct(producto));
    }

    @Override
    public Product save(Product product) {
        Producto producto = productMapper.toProducto(product);
        System.out.println(producto.toString());
        return productMapper.toProduct(productoCrudRepository.save(producto));
    }

    @Override
    public void delete(int productId) {
        productoCrudRepository.deleteById(productId);
    }
}

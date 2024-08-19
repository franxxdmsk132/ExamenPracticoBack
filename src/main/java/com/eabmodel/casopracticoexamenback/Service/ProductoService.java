package com.eabmodel.casopracticoexamenback.Service;

import com.eabmodel.casopracticoexamenback.Model.Producto;
import com.eabmodel.casopracticoexamenback.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }
    public Optional<Producto> findById(Integer id) {
        return productoRepository.findById(id);
    }

    public void save(Producto producto) {
        productoRepository.save(producto);
    }
    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }
    public boolean existsById(Integer id) {
        return productoRepository.existsById(id);
    }
}

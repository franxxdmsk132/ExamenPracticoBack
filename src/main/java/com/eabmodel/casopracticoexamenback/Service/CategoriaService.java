package com.eabmodel.casopracticoexamenback.Service;

import com.eabmodel.casopracticoexamenback.Model.Categoria;
import com.eabmodel.casopracticoexamenback.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> findById(int id) {
        return categoriaRepository.findById(id);
    }
    public void save(Categoria categoria) {
        categoriaRepository.save(categoria);
    }
    public void delete(Integer id) {
        categoriaRepository.deleteById(id);

    }
    public boolean existsById(int id) {
        return categoriaRepository.existsById(id);
    }
}

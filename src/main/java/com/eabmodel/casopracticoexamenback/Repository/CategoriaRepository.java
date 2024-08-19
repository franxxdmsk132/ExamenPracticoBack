package com.eabmodel.casopracticoexamenback.Repository;

import com.eabmodel.casopracticoexamenback.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}

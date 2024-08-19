package com.eabmodel.casopracticoexamenback.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    private String descripcion;
    private Integer stock;
    private String ubicacion;
    private String fecha_caducidad;

    public Producto( String nombre, Categoria categoria, String descripcion, Integer stock, String ubicacion, String fecha_caducidad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.stock = stock;
        this.ubicacion = ubicacion;
        this.fecha_caducidad = fecha_caducidad;
    }
}

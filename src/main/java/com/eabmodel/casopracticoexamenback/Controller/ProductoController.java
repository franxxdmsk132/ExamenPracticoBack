package com.eabmodel.casopracticoexamenback.Controller;

import com.eabmodel.casopracticoexamenback.Model.Categoria;
import com.eabmodel.casopracticoexamenback.Model.Producto;
import com.eabmodel.casopracticoexamenback.Reporte.Reporte;
import com.eabmodel.casopracticoexamenback.Service.CategoriaService;
import com.eabmodel.casopracticoexamenback.Service.ProductoService;
import com.lowagie.text.DocumentException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {
    @Autowired
    ProductoService productoService;
    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> list() {
        List<Producto> list = productoService.findAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Producto> findById(@PathVariable("id") Integer id) {
        if (!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.findById(id).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> create(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("categoriaId") Integer categoriaId,  // Se cambia de String a Integer
            @RequestParam("stock") Integer stock,
            @RequestParam("ubicacion") String ubicacion,
            @RequestParam("fecha_caducidad") String fecha) {

        // Validación de campos
        if (StringUtils.isBlank(nombre)) {
            return new ResponseEntity<>(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (categoriaId == null || categoriaId < 0) {
            return new ResponseEntity<>(new Mensaje("La Categoria es obligatoria"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(descripcion)) {
            return new ResponseEntity<>(new Mensaje("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);
        }
        if (stock == null || stock < 0) {
            return new ResponseEntity<>(new Mensaje("El número de talla debe ser mayor a 0"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(ubicacion)) {
            return new ResponseEntity<>(new Mensaje("La ubicacion es obligatoria"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(fecha)) {
            return new ResponseEntity<>(new Mensaje("La fecha es obligatoria"), HttpStatus.BAD_REQUEST);
        }
        // Buscar la categoría en la base de datos
        Optional<Categoria> categoriaOptional = categoriaService.findById(categoriaId);
        if (!categoriaOptional.isPresent()) {
            return new ResponseEntity<>(new Mensaje("La categoría no existe"), HttpStatus.NOT_FOUND);
        }
        Categoria categoria = categoriaOptional.get();

        // Guardar la imagen

        // Crear y guardar el producto
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setStock(stock);
        producto.setUbicacion(ubicacion);
        producto.setFecha_caducidad(fecha);
        producto.setCategoria(categoria);  // Establecer la relación con la categoría

        productoService.save(producto);

        return new ResponseEntity<>(new Mensaje("Producto creado"), HttpStatus.OK);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Integer id,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("categoriaId") Integer categoriaId,
            @RequestParam("stock") Integer stock,
            @RequestParam("ubicacion") String ubicacion,
            @RequestParam("fecha_caducidad") String fecha) {

        // Buscar el producto en la base de datos
        Optional<Producto> productoOptional = productoService.findById(id);
        if (!productoOptional.isPresent()) {
            return new ResponseEntity<>(new Mensaje("El producto no existe"), HttpStatus.NOT_FOUND);
        }
        Producto producto = productoOptional.get();

        // Validación de campos
        if (StringUtils.isBlank(nombre)) {
            return new ResponseEntity<>(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (categoriaId == null || categoriaId < 0) {
            return new ResponseEntity<>(new Mensaje("La categoría es obligatoria"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(descripcion)) {
            return new ResponseEntity<>(new Mensaje("La descripción es obligatoria"), HttpStatus.BAD_REQUEST);
        }
        if (stock == null || stock < 0) {
            return new ResponseEntity<>(new Mensaje("El stock debe ser mayor a 0"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(ubicacion)) {
            return new ResponseEntity<>(new Mensaje("La ubicación es obligatoria"), HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(fecha)) {
            return new ResponseEntity<>(new Mensaje("La fecha es obligatoria"), HttpStatus.BAD_REQUEST);
        }

        // Buscar la categoría en la base de datos
        Optional<Categoria> categoriaOptional = categoriaService.findById(categoriaId);
        if (!categoriaOptional.isPresent()) {
            return new ResponseEntity<>(new Mensaje("La categoría no existe"), HttpStatus.NOT_FOUND);
        }
        Categoria categoria = categoriaOptional.get();

        // Actualizar los campos del producto
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setStock(stock);
        producto.setUbicacion(ubicacion);
        producto.setFecha_caducidad(fecha);
        producto.setCategoria(categoria);  // Establecer la relación con la nueva categoría

        // Guardar los cambios
        productoService.save(producto);

        return new ResponseEntity<>(new Mensaje("Producto actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        if (!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        productoService.delete(id);
        return new ResponseEntity(new Mensaje("producto eliminado"), HttpStatus.OK);
    }
    @GetMapping("/exportarPDF")
    public void exportarEnPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Reporte_Prodcutos" + fechaActual + ".pdf";

        response.setHeader(cabecera, valor);

        List<Producto> productoList = productoService.findAll();

        Reporte exporter = new Reporte(productoList);
        exporter.exportarReporte(response);
    }
}

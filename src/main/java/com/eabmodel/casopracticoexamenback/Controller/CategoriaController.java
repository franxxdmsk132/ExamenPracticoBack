package com.eabmodel.casopracticoexamenback.Controller;

import com.eabmodel.casopracticoexamenback.Model.Categoria;
import com.eabmodel.casopracticoexamenback.Service.CategoriaService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/listar")
    public ResponseEntity<List<Categoria>> listar() {
        List<Categoria> categoriaList = categoriaService.findAll();
        return ResponseEntity.ok(categoriaList);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Categoria> listarById(@PathVariable("id") Integer id) {
        if (!categoriaService.existsById(id))
            return new ResponseEntity(new Mensaje("no Existe"), HttpStatus.NOT_FOUND);
        Categoria categoria = categoriaService.findById(id).get();
        return new ResponseEntity(categoria, HttpStatus.OK);
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> create(
            @RequestParam("nombre_categoria") String nombre_categoria) {

        if (StringUtils.isBlank(nombre_categoria)) {
            return new ResponseEntity<>(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }
        Categoria categoria = new Categoria(nombre_categoria);
        categoriaService.save(categoria);
        return new ResponseEntity<>(new Mensaje("Categoria Create"), HttpStatus.OK);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id,
                                    @RequestParam("nombre_categoria") String nombre_categoria) {
        if (!categoriaService.existsById(id)) {
            return new ResponseEntity<>(new Mensaje("No existe la categoria con ese ID"), HttpStatus.NOT_FOUND);
        }
        if (StringUtils.isBlank(nombre_categoria)) {
            return new ResponseEntity<>(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        Categoria categoria = categoriaService.findById(id).get();
        categoria.setNombre_categoria(nombre_categoria);

        categoriaService.save(categoria);
        return new ResponseEntity<>(new Mensaje("Categoria actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        if (!categoriaService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        categoriaService.delete(id);
        return new ResponseEntity(new Mensaje("categoria eliminado"), HttpStatus.OK);
    }
//    @GetMapping("/exportarPDF")
//    public void exportarListadoDeEmpleadosEnPDF(HttpServletResponse response) throws DocumentException, IOException {
//        response.setContentType("application/pdf");
//
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String fechaActual = dateFormatter.format(new Date());
//
//        String cabecera = "Content-Disposition";
//        String valor = "attachment; filename=INGRESOS_" + fechaActual + ".pdf";
//
//        response.setHeader(cabecera, valor);
//
//        List<Zapato> productoList = zapatoService.findAll();
//
//        ReporteZapatos exporter = new ReporteZapatos(productoList);
//        exporter.exportarReporte(response);
//    }
}

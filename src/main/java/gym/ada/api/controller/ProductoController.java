package gym.ada.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import gym.ada.api.dto.ProductCreateDto;
import gym.ada.api.dto.ProductData;
import gym.ada.api.dto.ProductUpdateDto;
import gym.ada.api.service.IProductoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;



@RestController
@RequestMapping("/api/productos" )
@SecurityRequirement(name = "Bearer Authentication")
public class ProductoController {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }


    // =========================================================
    // LISTAR TODOS
    // =========================================================

    @GetMapping(value = "/listarProductoData", produces = MediaType.APPLICATION_JSON_VALUE
    		)
    public ResponseEntity<List<ProductData>> listarTodos() {

        List<ProductData> productos =
                productoService.obtenerTodosLosProductos();

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }


    // =========================================================
    // LISTAR PRODUCTOS ACTIVOS
    // =========================================================

    @GetMapping(value ="/listarActivos",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductData>> listarActivos() {

        List<ProductData> productos =
                productoService.listarProductosActivos();

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }


    // =========================================================
    // CREAR PRODUCTO
    // =========================================================

    @PostMapping(value = "/crear",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductData> crearProducto(
            @RequestBody ProductCreateDto dto) {

        ProductData producto =
                productoService.crearProducto(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(producto);
    }


    // =========================================================
    // ACTUALIZAR PARCIALMENTE
    // =========================================================

    @PatchMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductData> actualizarProducto(
            @PathVariable Long id,
            @RequestBody ProductUpdateDto dto) {

        ProductData producto =
                productoService.actualizarProductoParcial(id, dto);

        return ResponseEntity.ok(producto);
    }


    // =========================================================
    // DESACTIVAR PRODUCTO
    // =========================================================

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> desactivarProducto(
            @PathVariable Long id) {

        productoService.desactivarProducto(id);

        return ResponseEntity.noContent().build();
    }
 // =========================================================
 // ACTIVAR PRODUCTO
 // =========================================================

 @PatchMapping(value = "/{id}/activar" , produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<ProductData> activarProducto(
         @PathVariable Long id) {

     ProductData producto =
             productoService.activarProducto(id);

     return ResponseEntity.ok(producto);
 }
}
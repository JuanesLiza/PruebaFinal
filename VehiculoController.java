package com.ejemplo.lavado_autos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ejemplo.lavado_autos.model.Vehiculo;
import com.ejemplo.lavado_autos.service.VehiculoService;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/vehiculos") // Define la ruta base para los endpoints del controlador
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService; // Inyección del servicio de vehículos

    @Autowired
    private MessageSource messageSource; // Inyección de MessageSource para internacionalización (i18n)

    // Endpoint para listar todos los vehículos
    @GetMapping
    public Mono<ResponseEntity<List<Vehiculo>>> getAllVehiculos() {
        return vehiculoService.findAll()
                .collectList() // Convierte Flux<Vehiculo> en una lista (Mono<List<Vehiculo>>)
                .map(vehiculos -> ResponseEntity.ok(vehiculos)) // Devuelve la lista en la respuesta HTTP 200 OK
                .defaultIfEmpty(ResponseEntity.noContent().build()); // Si está vacío, devuelve 204 No Content
    }

    // Obtener un vehículo por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Vehiculo>> getVehiculoById(@PathVariable Long id) {
        return vehiculoService.findById(id)
                .map(vehiculo -> ResponseEntity.ok(vehiculo))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Crear un vehículo y devolver un mensaje de éxito en el header
    @PostMapping
    public Mono<ResponseEntity<String>> createVehiculo(@RequestBody Vehiculo vehiculo, Locale locale) {
        return vehiculoService.createVehiculo(vehiculo)
                .map(created -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(messageSource.getMessage("success.create", null, locale))) // Mensaje de éxito con i18n
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest()
                        .body(messageSource.getMessage("error.notfound", null, locale))));  // Mensaje de error con i18n
    }

    // Eliminar un vehículo y devolver un mensaje de éxito en el header
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteVehiculo(@PathVariable Long id, Locale locale) {
        return vehiculoService.deleteVehiculo(id)
                .then(Mono.just(ResponseEntity.ok(
                        messageSource.getMessage("success.delete", null, locale)
                )))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(messageSource.getMessage("error.notfound", null, locale))));
    }

    // Endpoint para obtener un mensaje de error (internacionalizado)
    @GetMapping("/mensaje-error")
    public Mono<String> getErrorMessage(Locale locale) {
        String message = messageSource.getMessage("error.notfound", null, locale);
        return Mono.just(message);
    }
}
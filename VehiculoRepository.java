package com.ejemplo.lavado_autos.repository;
import com.ejemplo.lavado_autos.model.Vehiculo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface VehiculoRepository extends ReactiveCrudRepository<Vehiculo, Long> {  // Extiende ReactiveCrudRepository, lo que permite realizar operaciones CRUD de manera reactiva.
    Mono<Vehiculo> findById(Long id);
    // Declara un método para buscar un vehículo por su ID de forma reactiva.
    // Retorna un Mono<Vehiculo>, lo que significa que puede devolver un solo resultado o estar vacío.
}
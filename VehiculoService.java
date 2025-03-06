package com.ejemplo.lavado_autos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ejemplo.lavado_autos.model.Vehiculo;
import com.ejemplo.lavado_autos.repository.VehiculoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service // Indica que esta clase es un servicio gestionado por Spring.
public class VehiculoService {

    @Autowired // Inyecta automáticamente el repositorio para interactuar con la base de datos
    private VehiculoRepository vehiculoRepository;

    // Método para buscar un vehículo por su ID de forma reactiva
    public Mono<Vehiculo> findById(Long id) {
        return vehiculoRepository.findById(id); // Devuelve un Mono con el vehículo encontrado o vacío si no existe
    }

    // Método para obtener todos los vehículos de la base de datos de forma reactiva
    public Flux<Vehiculo> findAll() {
        return vehiculoRepository.findAll(); // Retorna un Flux con todos los vehículos almacenados
    }

    // Método para guardar un nuevo vehículo en la base de datos
    public Mono<Vehiculo> createVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo); // Guarda el vehículo y devuelve un Mono con el objeto guardado
    }

    // Método para eliminar un vehículo por su ID.
    public Mono<Void> deleteVehiculo(Long id) {
        return vehiculoRepository.deleteById(id);
    }
}

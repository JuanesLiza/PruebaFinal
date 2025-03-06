package com.ejemplo.lavado_autos;

import com.ejemplo.lavado_autos.model.Vehiculo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Ejecuta la prueba en un puerto aleatorio
@AutoConfigureWebTestClient  // Configura WebTestClient automáticamente para pruebas reactivas
public class VehiculoControllerTest {

    @Autowired
    private WebTestClient webTestClient; // Cliente de pruebas para realizar solicitudes HTTP a la API

    @Test
    public void testCreateVehiculo() {
        // aquí creé un objeto Vehiculo con datos de prueba
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Ford");
        vehiculo.setPlaca("ABC123");
        vehiculo.setServicio("Lavado completo");
        vehiculo.setPrecio(20000);

        // Se envía una solicitud POST a /api/vehiculos con el vehículo como cuerpo
        webTestClient.post().uri("/api/vehiculos")
                .bodyValue(vehiculo)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .value(message -> {
                    System.out.println("Mensaje de creación: " + message);
                });
    }

    @Test
    public void testDeleteVehiculo() {
        // Para esta prueba se asume que ya existe un registro con ID 1
        webTestClient.delete().uri("/api/vehiculos/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(message -> {
                    System.out.println("Mensaje de eliminación: " + message);
                });
    }
}
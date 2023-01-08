package com.api.rest.pruebasunitariassprintboot.repository;

import com.api.rest.pruebasunitariassprintboot.model.Empleado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository empleadoRepository;


     @DisplayName("Test para guardar a un empleado")
    @Test
    void testGuardarEmpleado(){
        // given
        Empleado empleado1 = Empleado.builder() //Lombok
                .nombre("Juan")
                .apellido("Guevara")
                .email("j1@gmail.com")
                .build();
        // when
        Empleado empleadoGuardado = empleadoRepository.save(empleado1);

        //then
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isGreaterThan(0);
    }



}

package com.api.rest.pruebasunitariassprintboot.service;

import com.api.rest.pruebasunitariassprintboot.Service.Implementation.EmpleadoServiceImplementation;
import com.api.rest.pruebasunitariassprintboot.exception.ResourceNotFoundException;
import com.api.rest.pruebasunitariassprintboot.model.Empleado;
import com.api.rest.pruebasunitariassprintboot.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTests {

    @Mock // Clase independiente
    private EmpleadoRepository empleadoRepository;

    @InjectMocks // Clase dependiente
    private EmpleadoServiceImplementation empleadoServiceImplementation;

    private Empleado empleado;

    @BeforeEach
    void setup(){
        empleado = Empleado.builder()
                .id(1L)
                .nombre("Kelvin")
                .apellido("Ojeda")
                .email("kelvin.ojeda@gmail.com")
                .build();
    }


    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarEmpleado(){
        //given
        given(empleadoRepository.findByEmail(empleado.getEmail())).willReturn(Optional.empty());
        given(empleadoRepository.save(empleado)).willReturn(empleado);
        //when
        Empleado empleadoGuardado = empleadoServiceImplementation.saveEmpleado(empleado);
        //then
        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test para guardar un empleado con Throw Exception")
    @Test
    void testGuardarEmpleadoConThrowException(){
        //given
        given(empleadoRepository.findByEmail(empleado.getEmail())).willReturn(Optional.of(empleado));

        //when
        assertThrows(ResourceNotFoundException.class, () ->{
            empleadoServiceImplementation.saveEmpleado(empleado);
        });
        //then
        verify(empleadoRepository, never()).save(any(Empleado.class));
    }

    @DisplayName("Test para listar a los empleados")
    @Test
    void testListarEmpleados(){
        //given
        Empleado empleado01 = Empleado.builder()
                .id(2L)
                .nombre("Juan")
                .apellido("Espinoza")
                .email("juan.espinza@gmail.com")
                .build();

        given(empleadoRepository.findAll()).willReturn(List.of(empleado, empleado01));

        //when

        List<Empleado> empleados = empleadoServiceImplementation.getAllEmpleados();


        //then
        assertThat(empleados).isNotNull();
        assertThat(empleados.size()).isEqualTo(2);
    }
    @DisplayName("Test para retornar una lista vacia")
    @Test
    void testListarColeccionEmpleadosVacia(){
        //given

        given(empleadoRepository.findAll()).willReturn(Collections.emptyList());
        //when
        List<Empleado> listaEmpleados = empleadoServiceImplementation.getAllEmpleados();
        //then
        assertThat(listaEmpleados).isEmpty();
        assertThat(listaEmpleados.size()).isEqualTo(0);
    }

    @DisplayName("Test para obtener un empleado por ID")
    @Test
    void TestObtenerEmpleadoPorId(){
        //given
        given(empleadoRepository.findById(1L)).willReturn(Optional.of(empleado));
        //when
        Empleado empleadoGuardado = empleadoServiceImplementation.getEmpleadoById(empleado.getId()).get();

        //then
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isEqualTo(1L);
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado(){
        //given
        given(empleadoRepository.save(empleado)).willReturn(empleado);
        empleado.setEmail("jose.quiroz@gmail.com");
        empleado.setNombre("Jose");
        //when
        Empleado empleadoActualizado = empleadoServiceImplementation.updateEmpleado(empleado);
        //then

        assertThat(empleadoActualizado.getEmail()).isEqualTo("jose.quiroz@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Jose");


    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado(){
        //given
        long empleadoId = 1L;
        willDoNothing().given(empleadoRepository).deleteById(empleadoId);
        //when
        empleadoServiceImplementation.deleteEmpleado(empleadoId);
        //then
        verify(empleadoRepository, times(1)).deleteById(empleadoId);
    }


}

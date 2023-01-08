package com.api.rest.pruebasunitariassprintboot.repository;

import com.api.rest.pruebasunitariassprintboot.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByEmail(String email);


}

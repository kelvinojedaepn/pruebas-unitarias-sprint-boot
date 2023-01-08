package com.api.rest.pruebasunitariassprintboot.Service.Implementation;

import com.api.rest.pruebasunitariassprintboot.Service.EmpleadoService;
import com.api.rest.pruebasunitariassprintboot.exception.ResourceNotFoundException;
import com.api.rest.pruebasunitariassprintboot.model.Empleado;
import com.api.rest.pruebasunitariassprintboot.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class EmpleadoServiceImplementation implements EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;


    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        Optional<Empleado> empleadoGuardado = empleadoRepository.findByEmail(empleado.getEmail());
        if(empleadoGuardado.isPresent()){
            throw new ResourceNotFoundException("El empleado con ese email ya existe: "+empleado.getEmail());
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Optional<Empleado> getEmpleadoById(long id) {
        return empleadoRepository.findById(id);
    }

    @Override
    public Empleado updateEmpleado(Empleado empleadoActualizado) {
        return empleadoRepository.save(empleadoActualizado);
    }

    @Override
    public void deleteEmpleado(long id) {
        empleadoRepository.deleteById(id);
    }
}

package com.proyecto.miconsultorio.Service;

import com.proyecto.miconsultorio.Models.Solicitudes;
import com.proyecto.miconsultorio.Dao.SolicitudesDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class SolicitudesService {
    
    @Autowired
    private SolicitudesDao solicitudesDao;

    // Operación para guardar el nuevo objeto "Solicitudes"
    @Transactional(readOnly=false)
    public Solicitudes save(Solicitudes solicitudes) {
        return solicitudesDao.save(solicitudes);
    }

    @Transactional(readOnly=false)
    public void delete(Integer id) {
        solicitudesDao.deleteById(id);
    }

    @Transactional(readOnly=true)
    public Solicitudes findById(Integer id) {
        return solicitudesDao.findById(id).orElse(null);
    }

    @Transactional(readOnly=true)
    public List<Solicitudes> findByAll() {
        return (List<Solicitudes>) solicitudesDao.findAll();
    }

    // Operación para ver todos los objetos "Solicitudes"
    @Transactional(readOnly=true)
    public List<Solicitudes> consulta_solicitud(String idmed) {
        return (List<Solicitudes>) solicitudesDao.consulta_solicitud(idmed);
    }

    // Operación para borrar el obejeto "Solicitud" cuanda se envía el registro a la tabla "citas"
    @Transactional(readOnly=true)
    public void borrar_solicitud(Integer idsol) {
        solicitudesDao.borrar_solicitud(idsol);
    }
}

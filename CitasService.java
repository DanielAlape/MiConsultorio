package com.proyecto.miconsultorio.Service;

import com.proyecto.miconsultorio.Models.Citas;
import com.proyecto.miconsultorio.Dao.CitasDao;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitasService {
    
    @Autowired
    private CitasDao citasDao;

    // Operación para guardar el nuevo objeto "Citas"
    @Transactional(readOnly=false)
    public Citas save(Citas citas) {
        return citasDao.save(citas);
    }

    @Transactional(readOnly=false)
    public void delete(Integer id) {
        citasDao.deleteById(id);
    }

    @Transactional(readOnly=true)
    public Citas findById(Integer id) {
        return citasDao.findById(id).orElse(null);
    }

    @Transactional(readOnly=true)
    public List<Citas> findByAll() {
        return (List<Citas>) citasDao.findAll();
    }

    // Operación para ver todos los objetos "Citas"
    @Transactional(readOnly=true)
    public List<Citas> consulta_cita(String idmed) {
        return (List<Citas>) citasDao.consulta_cita(idmed);
    }

    // Operación para insertar nueva "cita" enviada desde el componente "solicitud"
    @Transactional(readOnly=false)
    public void crear_cita(String nombre_ct, Date fecha_ct, String identificacion_ct, Date atencion_ct, Time hora_ct, String correo_ct, String idmed) { // Recibe estos parámetros
        citasDao.nueva_cita(nombre_ct, fecha_ct, identificacion_ct, atencion_ct, hora_ct, correo_ct, idmed); // Y luego los envía a su repositorio
    }

}

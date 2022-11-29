package com.proyecto.miconsultorio.Dao;

import com.proyecto.miconsultorio.Models.Citas;

import java.util.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CitasDao extends CrudRepository<Citas, Integer>{
    // Operación para seleccionar las citas del medico
    @Transactional(readOnly=true) // No afecta integridad base de datos
    @Query(value="SELECT * FROM citas WHERE id_medico= :idmed",nativeQuery=true)
    public List<Citas> consulta_cita(@Param("idmed")String idmed); // Me retorna una lista de objetos cita del medico
    //Operación Agregar nueva cita enviando desde solicitudes
    @Transactional(readOnly=false) // Afecta integridad base de datos
    @Modifying
    @Query(value="INSERT INTO citas(nombre_cita,fechaNac_cita,identificacion_cita,fechaAt_cita,horaAt_cita,correo_cita,id_medico) VALUES (:nombre_ct, :fecha_ct, :identificacion_ct, :atencion_ct, :hora_ct, :correo_ct, :idmed)", nativeQuery=true)
    public void nueva_cita(@Param("nombre_ct") String nombre_ct,@Param("fecha_ct") Date fecha_ct,@Param("identificacion_ct") String identificacion_ct, @Param("atencion_ct") Date atencion_ct, @Param("hora_ct") Time hora_ct, @Param("correo_ct") String correo_ct, @Param("idmed") String idmed);

    
}

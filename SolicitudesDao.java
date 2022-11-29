package com.proyecto.miconsultorio.Dao;

import com.proyecto.miconsultorio.Models.Solicitudes;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SolicitudesDao extends CrudRepository<Solicitudes, Integer>{
    // Operación para seleccionar las solicitudes del medico
    @Transactional(readOnly=true) // No afecta integridad base de datos
    @Query(value="SELECT * FROM solicitudes WHERE id_medico= :idmed",nativeQuery=true)
    public List<Solicitudes> consulta_solicitud(@Param("idmed")String idmed); // Me retorna una lista de objetos cita del medico
    //Operación para Eliminar solicitud a través del ID una vez se envía a la tabla "citas"
    @Transactional(readOnly=false) // Afecta integridad base de datos
    @Modifying
    @Query(value="DELETE * FROM solicitudes WHERE id_solicitud= :idsol",nativeQuery=true)
    public void borrar_solicitud(@Param("idsol") Integer idsol);
}  


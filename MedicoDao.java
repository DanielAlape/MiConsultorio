package com.proyecto.miconsultorio.Dao;

import com.proyecto.miconsultorio.Models.Medico;
import org.springframework.data.repository.CrudRepository; 
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param;

public interface MedicoDao extends CrudRepository<Medico, String>{
    // Operación Login
    @Transactional(readOnly=true) // No afecta la integridad de la BD
    // Definimos sentencia SQL "select" para reconocer la clave y el usuario
    @Query(value="SELECT * FROM medico WHERE id_medico= :usuario AND clave_medico= :clave",nativeQuery=true)                
    public Medico login(@Param("usuario")String usuario, @Param("clave")String clave);
}

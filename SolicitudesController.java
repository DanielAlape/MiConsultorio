package com.proyecto.miconsultorio.Controller;
import com.proyecto.miconsultorio.Models.Solicitudes;
import com.proyecto.miconsultorio.Models.Medico;
import com.proyecto.miconsultorio.Dao.SolicitudesDao;
import com.proyecto.miconsultorio.Dao.MedicoDao;
import com.proyecto.miconsultorio.Service.SolicitudesService;
import com.proyecto.miconsultorio.Security.Hash;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController  
@CrossOrigin("*") 
@RequestMapping("/solicitudes")
public class SolicitudesController {
    
    @Autowired
    private SolicitudesDao solicitudesDao;
    
    @Autowired
    private MedicoDao medicoDao;

    @Autowired
    private SolicitudesService solicitudesService;

    // Operación crear solicitud
    @PostMapping(value="/")
    public ResponseEntity<Solicitudes> agregar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario,@Valid@RequestBody Solicitudes solicitudes){   
        // Para crear una nueva cita, el "medico" previamente requiere ingresar su usuario y clave
        Medico med=new Medico();
        med=medicoDao.login(usuario, Hash.sha1(clave));
        if (med!=null) {
            return new ResponseEntity<>(solicitudesService.save(solicitudes), HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
        }
          
    }

    // Operación borrar solicitud
    @DeleteMapping(value="/{id}") 
    public ResponseEntity<Solicitudes> eliminar(@PathVariable Integer id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Medico med=new Medico();
        med=medicoDao.login(usuario, Hash.sha1(clave));
       if (med!=null) {
        Solicitudes obj = solicitudesService.findById(id); 
            if(obj!=null) 
            solicitudesService.delete(id);
            else 
                return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR); 
            return new ResponseEntity<>(obj, HttpStatus.OK); 
      
       } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       }
        
    }

    // Operación editar solicitud (Por si acaso)
    @PutMapping(value="/") 
    @ResponseBody
    public ResponseEntity<Solicitudes> editar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario,@Valid @RequestBody Solicitudes solicitudes){ 
        Medico med=new Medico();
        med=medicoDao.login(usuario, Hash.sha1(clave));
        if (med!=null) {
            Solicitudes obj = solicitudesService.findById(solicitudes.getId_solicitud()); 
            if(obj!=null) { 
                obj.setNombre_solicitud(solicitudes.getNombre_solicitud());
                obj.setFechaNac_solicitud(solicitudes.getFechaNac_solicitud());
                obj.setIdentificacion_solicitud(solicitudes.getIdentificacion_solicitud());
                obj.setFechaAt_solicitud(solicitudes.getFechaAt_solicitud());
                obj.setHoraAt_solicitud(solicitudes.getHoraAt_solicitud());
                obj.setCorreo_solicitud(solicitudes.getCorreo_solicitud());
                obj.setMedico(solicitudes.getMedico());
                solicitudesService.save(solicitudes); 
            } 
            else 
                return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR); 
            return new ResponseEntity<>(obj, HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    }

    // Operación para consultar todos los registros de la tabla "solicitudes"
    @GetMapping("/list") 
    @ResponseBody
    public ResponseEntity<List<Solicitudes>> consultarTodo(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){
        Medico med=new Medico();
        med=medicoDao.login(usuario, Hash.sha1(clave));
        if (med!=null) {
            return new ResponseEntity<>(solicitudesService.findByAll(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }  
          
    }

    // Operación para consultar una solicitud en particular 
    @GetMapping("/list/{id}") 
    @ResponseBody
    public ResponseEntity<Solicitudes> consultaPorId(@PathVariable Integer id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Medico med=new Medico();
        med=medicoDao.login(usuario, Hash.sha1(clave));
        if (med!=null) {
            return new ResponseEntity<>(solicitudesService.findById(id),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }   
    }

    // Operación para consultar una solicitud para el medico
    @GetMapping("/consulta_solicitud")
    @ResponseBody
    public ResponseEntity<List<Solicitudes>> consulta_solicitud(@RequestParam ("idmed") String idmed,@RequestHeader ("usuario") String usuario,@RequestHeader ("clave") String clave) { 
        Medico medico=new Medico();
        medico=medicoDao.login(usuario, Hash.sha1(clave));
        if (medico!=null) {
            return new ResponseEntity<>(solicitudesService.consulta_solicitud(idmed),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    }

    // Operación para borrar objeto "Solicitudes" cuando se le indique que el registro se creará en la tabla "citas"
    @GetMapping("/borrar_solicitud")
    @ResponseBody
    public void borrar_solicitud(@Param("idsol") Integer idsol){
    Solicitudes obj = solicitudesService.findById(idsol); 
        if(obj!=null) 
        solicitudesService.delete(idsol); // Si es diferente de "vacio", eliminar el objeto a través de id
    }
}

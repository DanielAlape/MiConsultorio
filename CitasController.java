package com.proyecto.miconsultorio.Controller;
import com.proyecto.miconsultorio.Models.Citas;
import com.proyecto.miconsultorio.Models.Medico;
import com.proyecto.miconsultorio.Dao.CitasDao;
import com.proyecto.miconsultorio.Dao.MedicoDao;
import com.proyecto.miconsultorio.Service.CitasService;
import com.proyecto.miconsultorio.Security.Hash;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/citas")
public class CitasController {
    
    @Autowired
    private CitasDao citasDao;
    
    @Autowired
    private MedicoDao medicoDao;

    @Autowired
    private CitasService citasService;

    // Operación crear cita
    @PostMapping(value="/")
    public ResponseEntity<Citas> agregar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario,@Valid@RequestBody Citas citas){   
        // Para crear una nueva cita, el "medico" previamente requiere ingresar su usuario y clave
        Medico med=new Medico();
        med=medicoDao.login(usuario, Hash.sha1(clave));
        if (med!=null) {
            return new ResponseEntity<>(citasService.save(citas), HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
        }
          
    }

    // Operación borrar cita
    @DeleteMapping(value="/{id}") 
    public ResponseEntity<Citas> eliminar(@PathVariable Integer id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Medico med=new Medico();
        med=medicoDao.login(usuario, Hash.sha1(clave));
       if (med!=null) {
        Citas obj = citasService.findById(id); 
            if(obj!=null) 
            citasService.delete(id);
            else 
                return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR); 
            return new ResponseEntity<>(obj, HttpStatus.OK); 
      
       } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       }
       
        
    }

    // Operación editar cita
    @PutMapping(value="/") 
    @ResponseBody
    public ResponseEntity<Citas> editar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario,@Valid @RequestBody Citas citas){ 
        Medico med=new Medico();
        med=medicoDao.login(usuario, Hash.sha1(clave));
        if (med!=null) {
            Citas obj = citasService.findById(citas.getId_cita()); 
            if(obj!=null) { 
                obj.setNombre_cita(citas.getNombre_cita());
                obj.setFechaNac_cita(citas.getFechaNac_cita());
                obj.setIdentificacion_cita(citas.getIdentificacion_cita());
                obj.setFechaAt_cita(citas.getFechaAt_cita());
                obj.setHoraAt_cita(citas.getHoraAt_cita());
                obj.setCorreo_cita(citas.getCorreo_cita());
                obj.setMedico(citas.getMedico());
                citasService.save(citas); 
            } 
            else 
                return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR); 
            return new ResponseEntity<>(obj, HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    }

    // Operación para consultar todos los registros de la tabla "citas"
    @GetMapping("/list") 
    @ResponseBody
    public ResponseEntity<List<Citas>> consultarTodo(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){
        Medico med=new Medico();
        med=medicoDao.login(usuario, Hash.sha1(clave));
        if (med!=null) {
            return new ResponseEntity<>(citasService.findByAll(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }  
          
    }

    // Operación para consultar una cita en particular 
    @GetMapping("/list/{id}") 
    @ResponseBody
    public ResponseEntity<Citas> consultaPorId(@PathVariable Integer id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Medico med=new Medico();
        med=medicoDao.login(usuario, Hash.sha1(clave));
        if (med!=null) {
            return new ResponseEntity<>(citasService.findById(id),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }   
    }

    // Operación para consultar una cita para el medico
    @GetMapping("/consulta_cita")
    @ResponseBody
    public ResponseEntity<List<Citas>> consulta_cita(@RequestParam ("idmed") String idmed,@RequestHeader ("usuario") String usuario,@RequestHeader ("clave") String clave) { 
        Medico medico=new Medico();
        medico=medicoDao.login(usuario, Hash.sha1(clave));
        if (medico!=null) {
            return new ResponseEntity<>(citasService.consulta_cita(idmed),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    }

    // Operación para crear un nuevo objeto "Citas" cuando se envie un registro de la tabla "solicitudes"
    @PostMapping(value="/nueva_cita") 
    public void nueva_cita(@RequestParam ("nombre_ct") String nombre_ct, @RequestParam ("fecha_ct") Date fecha_ct,
        @RequestParam ("identificacion_ct") String identificacion_ct, @RequestParam ("atencion_ct") Date atencion_ct,
        @RequestParam ("hora_ct") Time hora_ct, @RequestParam ("correo_ct") String correo_ct,
        @RequestParam ("idmed") String idmed ,@RequestHeader("clave")String clave,
        @RequestHeader("usuario")String usuario){ 
        Medico medico1=new Medico(); // Debe acceder con usuario y clave el cliente
        medico1=medicoDao.login(usuario, Hash.sha1(clave)); // Se encripta la clave
        if (medico1!=null) {
            citasService.crear_cita(nombre_ct, fecha_ct, identificacion_ct, atencion_ct, hora_ct, correo_ct, idmed); // Llama a la función "crear_cita" de la clase "citasService" enviando esos parámetros
        }
          
    }
}

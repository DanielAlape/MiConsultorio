package com.proyecto.miconsultorio.Controller;

import com.proyecto.miconsultorio.Models.Medico;
import com.proyecto.miconsultorio.Dao.MedicoDao;
import com.proyecto.miconsultorio.Service.MedicoService;
import com.proyecto.miconsultorio.Security.Hash;

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
@RequestMapping("/medico")
public class MedicoController {
    
    @Autowired
    private MedicoDao medicoDao;
    @Autowired
    private MedicoService medicoService;

    // Operación agregar "Medico"
    @PostMapping(value="/")
    @ResponseBody
    public ResponseEntity<Medico> agregar (@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario, @Valid @RequestBody Medico medico){ 
        Medico medico1=new Medico(); 
        medico1=medicoDao.login(usuario, Hash.sha1(clave)); 
        if (medico1!=null) { 
            return new ResponseEntity<>(medicoService.save(medico), HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
        }
    }

    // Operación eliminar "Medico" por id
    @DeleteMapping(value="/{id}") 
    public ResponseEntity<Medico> eliminar(@PathVariable String id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Medico objmed=new Medico();
        objmed=medicoDao.login(usuario, Hash.sha1(clave));
       if (objmed!=null) {
        Medico obj = medicoService.findById(id); 
            if(obj!=null) 
            medicoService.delete(id);
            else 
                return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR); // No se puede borrar lo que no existe, por eso el mensaje de Error
            return new ResponseEntity<>(obj, HttpStatus.OK); 
      
       } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
       }
        
    }

    // Operación editar "Medico"
    @PutMapping(value="/") 
    @ResponseBody
    public ResponseEntity<Medico> editar(@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario,@Valid @RequestBody Medico medico){ 
        Medico med1=new Medico();
        med1=medicoDao.login(usuario, Hash.sha1(clave));
        if (med1!=null) {
            medico.setClave_medico(Hash.sha1(medico.getClave_medico()));
            Medico obj = medicoService.findById(medico.getId_medico()); 
            if(obj!=null) { 
                obj.setIdentificacion_medico(medico.getIdentificacion_medico());
                obj.setNombre_medico(medico.getNombre_medico());
                obj.setRm_medico(medico.getRm_medico());
                obj.setMovil_medico(medico.getMovil_medico());
                obj.setCorreo_medico(medico.getCorreo_medico());
                obj.setClave_medico(medico.getClave_medico());
                medicoService.save(medico); 
            } 
            else 
                return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR); 
            return new ResponseEntity<>(obj, HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    }

    // Operación consultar todo
    @GetMapping("/list")
    @ResponseBody
    public ResponseEntity<List<Medico>> consultaTodo(@RequestHeader("clave")String clave, @RequestHeader("usuario")String usuario){ // Estos parámetros quedan almacenados en las variables de sesión "Header"
    Medico medico = new Medico();
    medico=medicoDao.login(usuario, Hash.sha1(clave));  
        if (medico!=null){ 
            return new ResponseEntity<>(medicoService.findAll(),HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }           
    }

    // Operación consultar "Medico" por id
    @GetMapping("/list/{id}") 
    @ResponseBody
    public ResponseEntity<Medico> consultaPorId(@PathVariable String id,@RequestHeader("clave")String clave,@RequestHeader("usuario")String usuario){ 
        Medico medico=new Medico();
        medico=medicoDao.login(usuario, Hash.sha1(clave));
        if (medico!=null) {
            return new ResponseEntity<>(medicoService.findById(id),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }   
    }

    // Operación Login
    @GetMapping("/login")
    @ResponseBody
    public Medico ingresar(@RequestParam ("usuario") String usuario,@RequestParam ("clave") String clave) {
        clave=Hash.sha1(clave); // Se encripta la clave previamente
        return medicoService.login(usuario, clave); 
    }

}

package com.proyecto.miconsultorio.Service;

import com.proyecto.miconsultorio.Models.Medico;
import com.proyecto.miconsultorio.Dao.MedicoDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MedicoService {
    @Autowired
    private MedicoDao medicoDao;

    // Operación para guardar el nuevo objeto "Medico"
    @Transactional(readOnly=false)
    public Medico save(Medico medico) {
        return medicoDao.save(medico);
    }

    @Transactional(readOnly=false)
    public void delete(String id) {
        medicoDao.deleteById(id);
    }

    @Transactional(readOnly=true)
    public Medico findById(String id) {
        return medicoDao.findById(id).orElse(null);
    }

    @Transactional(readOnly=true)
    public List<Medico> findAll() {
        return (List<Medico>) medicoDao.findAll();
    }

    // Operación para loguearse
    @Transactional(readOnly=true)
    public Medico login(String usuario, String clave) {
        return medicoDao.login(usuario, clave);
    }
}

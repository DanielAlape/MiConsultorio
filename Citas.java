package com.proyecto.miconsultorio.Models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Size;


@Getter
@Setter
@Entity
@Table(name="citas")
public class Citas implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cita ")
    private int id_cita;
    @NotEmpty(message="El nombre del paciente no puede ser vacío")
    @Size(min=5, max=80,message="El nombre del paciente debe tener mínimo 5 caracteres y máximo 80")
    @Column(name="nombre_cita")
    private String nombre_cita;
    @Column(name="fechaNac_cita")
    private Date fechaNac_cita;
    @NotEmpty(message="La identificación del paciente no puede ser vacío")
    @Column(name="identificacion_cita") 
    private String identificacion_cita ;
    @Column(name="fechaAt_cita")
    private Date fechaAt_cita;
    @Column(name="horaAt_cita")
    private Time horaAt_cita;
    @NotEmpty(message="El correo del paciente no puede ser vacío")
    @Column(name="correo_cita")
    private String correo_cita;
    @ManyToOne // De un médico a muchas citas
    @JoinColumn(name="id_medico ") // Se relaciona con la tabla "medico" a través del "id_medico"
    private Medico medico ;

    @Override
    public String toString() {
        return "Citas [id_cita=" + id_cita + ", nombre_cita=" + nombre_cita + ", fechaNac_cita="
        + fechaNac_cita + ", identificacion_cita="+ identificacion_cita + ", fechaAt_cita=" 
        + fechaAt_cita + ", horaAt_cita=" + horaAt_cita + ", correo_cita=" + correo_cita 
        + ", medico=" + medico + "]";
    }
}

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
@Table(name="solicitudes")
public class Solicitudes implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // para que se genere el autoincremento
    @Column(name="id_solicitud ")
    private int id_solicitud;
    @NotEmpty(message="El nombre del paciente no puede ser vacío")
    @Size(min=5, max=80,message="El nombre del paciente debe tener mínimo 5 caracteres y máximo 80")
    @Column(name="nombre_solicitud")
    private String nombre_solicitud;
    @Column(name="fechaNac_solicitud")
    private Date fechaNac_solicitud;
    @NotEmpty(message="La identificación del paciente no puede ser vacío")
    @Column(name="identificacion_solicitud") 
    private String identificacion_solicitud ;
    @Column(name="fechaAt_solicitud")
    private Date fechaAt_solicitud;
    @Column(name="horaAt_solicitud")
    private Time horaAt_solicitud;
    @NotEmpty(message="El correo del paciente no puede ser vacío")
    @Column(name="correo_solicitud")
    private String correo_solicitud;
    @ManyToOne // De un médico a muchas solicitudes
    @JoinColumn(name="id_medico ") // Se relaciona con la tabla "medico" a través del "id_medico"
    private Medico medico ;

    @Override
    public String toString() {
        return "Citas [id_solicitud=" + id_solicitud + ", nombre_solicitud=" + nombre_solicitud + ", fechaNac_solicitud="
        + fechaNac_solicitud + ", identificacion_solicitud="+ identificacion_solicitud + ", fechaAt_solicitud=" 
        + fechaAt_solicitud + ", horaAt_solicitud=" + horaAt_solicitud + ", correo_solicitud=" + correo_solicitud 
        + ", medico=" + medico + "]";
    }
}
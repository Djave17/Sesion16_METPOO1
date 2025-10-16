package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;

import java.util.Date;

@Entity
@Getter
@Setter

public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Nombre
    @Column(nullable = false)
    String nombre;


    //Nacionalidad
    @Column(nullable = false)
    String nacionalidad;


    //Fecha de nacimiento
    @Column(nullable = false)
    Date fechaNacimiento;

    public Autor() {

    }

    public  Autor(String nombre, String nacionalidad, Date fechaNacimiento) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
    }




}

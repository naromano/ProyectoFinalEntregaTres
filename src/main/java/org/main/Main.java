package org.main;

import org.dao.DomicilioDAOImpl;
import org.dao.PersonaDAOImpl;
import org.entities.Domicilio;
import org.entities.Persona;
import org.services.DomicilioService;
import org.services.PersonaService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            DomicilioDAOImpl domicilioDAO = new DomicilioDAOImpl();
            DomicilioService domicilioService = new DomicilioService(domicilioDAO);


            PersonaDAOImpl personaDAO = new PersonaDAOImpl();
            PersonaService personaService = new PersonaService(personaDAO);

            Domicilio domicilio = Domicilio.builder()
                    .calle("Olascoaga")
                    .numero(123)
                    .localidad("Ciudad")
                    .provincia("Mendoza")
                    .pais("Argentina")
                    .build();

            domicilioService.guardar(domicilio);
            System.out.println("Domicilio guardado con ID: " + domicilio.getId());

            Persona persona = Persona.builder()
                    .nombre("Hector Alejandro")
                    .apellido("Romano")
                    .dni("123412312")
                    .domicilio(domicilio)
                    .build();

            personaService.guardar(persona);

            System.out.println("Persona guardada con ID: " + persona.getId());

            Domicilio domicilioEncontrado = domicilioService.buscarPorId(domicilio.getId());
            if (domicilioEncontrado != null) {
                System.out.println("Domicilio encontrado: " + domicilioEncontrado.getCalle() + " " + domicilioEncontrado.getNumero());
            } else {
                System.out.println("Domicilio no encontrado");
            }

            Persona personaEncontrada = personaService.buscarPorId(persona.getId());
            if (personaEncontrada != null) {
                System.out.println("Persona encontrada: " + personaEncontrada.getNombre() + " " + personaEncontrada.getApellido());
            } else {
                System.out.println("Persona no encontrada");
            }

            System.out.println("Lista de Personas:");
            List<Persona> personas = personaService.buscarTodos();

            for (Persona p : personas) {
                System.out.println(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

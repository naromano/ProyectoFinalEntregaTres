package org.main;

import org.dao.DomicilioDAOImpl;
import org.dao.PersonaDAOImpl;
import org.entities.Domicilio;
import org.entities.Persona;
import org.services.DomicilioService;
import org.services.PersonaService;

public class Main {
    public static void main(String[] args) {
        try {
            DomicilioDAOImpl domicilioDAO = new DomicilioDAOImpl();
            PersonaDAOImpl personaDAO = new PersonaDAOImpl();

            DomicilioService domicilioService = new DomicilioService(domicilioDAO);
            PersonaService personaService = new PersonaService(personaDAO);

            Domicilio domicilio = Domicilio.builder()
                    .calle("Augusto T. Vandor")
                    .numero(1542)
                    .localidad("Godoy Cruz")
                    .provincia("Mendoza")
                    .pais("ARgentina")
                    .build();

            domicilioService.guardar(domicilio);
            System.out.println("Domicilio guardado con ID: " + domicilio.getId());

            Persona persona = Persona.builder()
                    .nombre("Nicolas")
                    .apellido("Romano")
                    .dni("38759391")
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package servicio;

import dao.DAOGenerico;
import modelo.Persona;
import java.util.List;
import java.util.Optional;

public class ServicioPersona {
    private final DAOGenerico<Persona> dao;

    public ServicioPersona() {
        this.dao = new DAOGenerico<>("personas.dat");
    }

    public void crearPersona(Persona persona) {
        dao.crear(persona);
        System.out.println("✓ Persona creada exitosamente con ID: " + persona.getId());
    }

    public void mostrarPersona(Long id) {
        Optional<Persona> persona = dao.leer(id);
        if (persona.isPresent()) {
            System.out.println("\n" + persona.get());
        } else {
            System.out.println("✗ No se encontró ninguna persona con ID: " + id);
        }
    }

    // NUEVO: Método para obtener una persona sin imprimir
    public Optional<Persona> obtenerPersona(Long id) {
        return dao.leer(id);
    }

    // NUEVO: Método para obtener todas las personas como lista
    public List<Persona> obtenerTodasLasPersonas() {
        return dao.leerTodos();
    }

    public void mostrarTodasLasPersonas() {
        List<Persona> personas = dao.leerTodos();
        if (personas.isEmpty()) {
            System.out.println("\n✗ No hay personas registradas.");
        } else {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                          LISTADO DE PERSONAS                                  ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
            personas.forEach(System.out::println);
            System.out.println("\nTotal de personas: " + personas.size());
        }
    }

    public void actualizarPersona(Persona persona) {
        Optional<Persona> existe = dao.leer(persona.getId());
        if (existe.isPresent()) {
            dao.actualizar(persona);
            System.out.println("✓ Persona actualizada exitosamente.");
        } else {
            System.out.println("✗ No se encontró ninguna persona con ID: " + persona.getId());
        }
    }

    public void eliminarPersona(Long id) {
        if (dao.eliminar(id)) {
            System.out.println("✓ Persona eliminada exitosamente.");
        } else {
            System.out.println("✗ No se encontró ninguna persona con ID: " + id);
        }
    }

    public void buscarPorNombre(String nombre) {
        List<Persona> resultados = dao.buscarPorCriterio(
                p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase())
        );

        if (resultados.isEmpty()) {
            System.out.println("\n✗ No se encontraron personas con ese nombre.");
        } else {
            System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                       RESULTADOS DE BÚSQUEDA                                  ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
            resultados.forEach(System.out::println);
            System.out.println("\nResultados encontrados: " + resultados.size());
        }
    }

    // NUEVO: Método para buscar y retornar lista (para JavaFX)
    public List<Persona> buscarPorNombreLista(String nombre) {
        return dao.buscarPorCriterio(
                p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase())
        );
    }

    public Long obtenerNuevoId() {
        return dao.obtenerNuevoId();
    }
}
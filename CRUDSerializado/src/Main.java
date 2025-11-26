import modelo.Persona;
import servicio.ServicioPersona;
import util.Validador;

public class Main {
    private static final ServicioPersona servicioPersona = new ServicioPersona();
    private static final boolean DEBUG = true; // Cambiar a false para desactivar debug

    public static void main(String[] args) {
        log("=== INICIANDO APLICACIÓN ===");
        log("Modo DEBUG: " + (DEBUG ? "ACTIVADO" : "DESACTIVADO"));

        mostrarBienvenida();
        boolean continuar = true;

        while (continuar) {
            try {
                mostrarMenu();
                log("Esperando entrada del usuario...");
                int opcion = Validador.leerEnteroEnRango("Seleccione una opción: ", 0, 7);
                log("Opción seleccionada: " + opcion);
                System.out.println();

                switch (opcion) {
                    case 1:
                        log(">>> Ejecutando: CREAR PERSONA");
                        crearPersona();
                        break;
                    case 2:
                        log(">>> Ejecutando: CONSULTAR PERSONA");
                        consultarPersona();
                        break;
                    case 3:
                        log(">>> Ejecutando: LISTAR PERSONAS");
                        listarPersonas();
                        break;
                    case 4:
                        log(">>> Ejecutando: ACTUALIZAR PERSONA");
                        actualizarPersona();
                        break;
                    case 5:
                        log(">>> Ejecutando: ELIMINAR PERSONA");
                        eliminarPersona();
                        break;
                    case 6:
                        log(">>> Ejecutando: BUSCAR POR NOMBRE");
                        buscarPersonaPorNombre();
                        break;
                    case 7:
                        log(">>> Ejecutando: ESTADÍSTICAS");
                        mostrarEstadisticas();
                        break;
                    case 0:
                        log(">>> CERRANDO APLICACIÓN");
                        continuar = false;
                        mostrarDespedida();
                        break;
                }

                if (continuar) {
                    pausar();
                }
            } catch (Exception e) {
                System.err.println("\n✗✗✗ ERROR CRÍTICO EN MAIN ✗✗✗");
                System.err.println("Mensaje: " + e.getMessage());
                System.err.println("Tipo: " + e.getClass().getName());
                System.err.println("\nStack Trace:");
                e.printStackTrace();
                pausar();
            }
        }

        log("=== FINALIZANDO APLICACIÓN ===");
        Validador.cerrarScanner();
    }

    // Método auxiliar para logs de debug
    private static void log(String mensaje) {
        if (DEBUG) {
            System.out.println("[DEBUG] " + mensaje);
        }
    }

    private static void mostrarBienvenida() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                               ║");
        System.out.println("║                   SISTEMA CRUD DE GESTIÓN DE PERSONAS                         ║");
        System.out.println("║                      Con Serialización en Java                                ║");
        System.out.println("║                                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝\n");
    }

    private static void mostrarMenu() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              MENÚ PRINCIPAL                                   ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Crear Persona                                                             ║");
        System.out.println("║  2. Consultar Persona por ID                                                  ║");
        System.out.println("║  3. Listar Todas las Personas                                                 ║");
        System.out.println("║  4. Actualizar Persona                                                        ║");
        System.out.println("║  5. Eliminar Persona                                                          ║");
        System.out.println("║  6. Buscar por Nombre                                                         ║");
        System.out.println("║  7. Estadísticas                                                              ║");
        System.out.println("║  0. Salir                                                                     ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
    }

    private static void crearPersona() {
        try {
            log("Entrando a crearPersona()");
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                           CREAR NUEVA PERSONA                                 ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝\n");

            log("Solicitando nombre...");
            String nombre = Validador.leerTextoNoVacio("Nombre: ");
            log("Nombre capturado: " + nombre);

            log("Solicitando apellido...");
            String apellido = Validador.leerTextoNoVacio("Apellido: ");
            log("Apellido capturado: " + apellido);

            log("Solicitando edad...");
            int edad = Validador.leerEnteroEnRango("Edad: ", 0, 150);
            log("Edad capturada: " + edad);

            log("Solicitando email...");
            String email = Validador.leerTextoNoVacio("Email: ");
            log("Email capturado: " + email);

            log("Creando objeto Persona...");
            Persona persona = new Persona(null, nombre, apellido, edad, email);
            log("Objeto creado: " + persona);

            log("Llamando a servicioPersona.crearPersona()...");
            servicioPersona.crearPersona(persona);
            log("Persona creada exitosamente con ID: " + persona.getId());
        } catch (Exception e) {
            System.err.println("\n✗ ERROR al crear persona:");
            System.err.println("  Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void consultarPersona() {
        try {
            log("Entrando a consultarPersona()");
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                         CONSULTAR PERSONA POR ID                              ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝\n");

            log("Solicitando ID...");
            Long id = Validador.leerLong("Ingrese el ID de la persona: ");
            log("ID capturado: " + id);

            log("Buscando persona con ID: " + id);
            servicioPersona.mostrarPersona(id);
            log("Consulta completada");
        } catch (Exception e) {
            System.err.println("\n✗ ERROR al consultar persona:");
            System.err.println("  Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void listarPersonas() {
        try {
            log("Entrando a listarPersonas()");
            servicioPersona.mostrarTodasLasPersonas();
            log("Listado completado");
        } catch (Exception e) {
            System.err.println("\n✗ ERROR al listar personas:");
            System.err.println("  Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void actualizarPersona() {
        try {
            log("Entrando a actualizarPersona()");
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                          ACTUALIZAR PERSONA                                   ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝\n");

            log("Solicitando ID...");
            Long id = Validador.leerLong("Ingrese el ID de la persona a actualizar: ");
            log("ID capturado: " + id);

            servicioPersona.mostrarPersona(id);

            log("Solicitando confirmación...");
            if (Validador.confirmar("\n¿Desea continuar con la actualización?")) {
                log("Usuario confirmó actualización");

                String nombre = Validador.leerTextoNoVacio("Nuevo nombre: ");
                log("Nuevo nombre: " + nombre);

                String apellido = Validador.leerTextoNoVacio("Nuevo apellido: ");
                log("Nuevo apellido: " + apellido);

                int edad = Validador.leerEnteroEnRango("Nueva edad: ", 0, 150);
                log("Nueva edad: " + edad);

                String email = Validador.leerTextoNoVacio("Nuevo email: ");
                log("Nuevo email: " + email);

                Persona persona = new Persona(id, nombre, apellido, edad, email);
                log("Actualizando persona...");
                servicioPersona.actualizarPersona(persona);
                log("Actualización completada");
            } else {
                log("Usuario canceló actualización");
                System.out.println("Actualización cancelada.");
            }
        } catch (Exception e) {
            System.err.println("\n✗ ERROR al actualizar persona:");
            System.err.println("  Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void eliminarPersona() {
        try {
            log("Entrando a eliminarPersona()");
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                           ELIMINAR PERSONA                                    ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝\n");

            log("Solicitando ID...");
            Long id = Validador.leerLong("Ingrese el ID de la persona a eliminar: ");
            log("ID capturado: " + id);

            servicioPersona.mostrarPersona(id);

            log("Solicitando confirmación...");
            if (Validador.confirmar("\n¿Está seguro de eliminar esta persona?")) {
                log("Usuario confirmó eliminación");
                servicioPersona.eliminarPersona(id);
                log("Eliminación completada");
            } else {
                log("Usuario canceló eliminación");
                System.out.println("Eliminación cancelada.");
            }
        } catch (Exception e) {
            System.err.println("\n✗ ERROR al eliminar persona:");
            System.err.println("  Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void buscarPersonaPorNombre() {
        try {
            log("Entrando a buscarPersonaPorNombre()");
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                         BUSCAR POR NOMBRE                                     ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝\n");

            log("Solicitando nombre...");
            String nombre = Validador.leerTextoNoVacio("Ingrese el nombre a buscar: ");
            log("Nombre a buscar: " + nombre);

            servicioPersona.buscarPorNombre(nombre);
            log("Búsqueda completada");
        } catch (Exception e) {
            System.err.println("\n✗ ERROR al buscar persona:");
            System.err.println("  Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mostrarEstadisticas() {
        try {
            log("Entrando a mostrarEstadisticas()");
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                            ESTADÍSTICAS                                       ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝\n");

            Long proximoId = servicioPersona.obtenerNuevoId();
            log("Próximo ID disponible: " + proximoId);
            System.out.println("Próximo ID disponible: " + proximoId);

            servicioPersona.mostrarTodasLasPersonas();
            log("Estadísticas mostradas");
        } catch (Exception e) {
            System.err.println("\n✗ ERROR al mostrar estadísticas:");
            System.err.println("  Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mostrarDespedida() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                               ║");
        System.out.println("║              ¡Gracias por usar el Sistema de Gestión de Personas!             ║");
        System.out.println("║                            ¡Hasta pronto!                                     ║");
        System.out.println("║                                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝\n");
    }

    private static void pausar() {
        System.out.println("\nPresione ENTER para continuar...");
        Validador.leerTexto("");
    }
}
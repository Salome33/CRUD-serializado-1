package dao;

import modelo.Entidad;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DAOGenerico<T extends Entidad> implements IDAO<T> {
    private final String archivo;
    private List<T> registros;

    public DAOGenerico(String nombreArchivo) {
        this.archivo = "data/" + nombreArchivo;
        this.registros = new ArrayList<>();
        crearDirectorioSiNoExiste();
        cargarDatos();
    }

    private void crearDirectorioSiNoExiste() {
        File directorio = new File("data");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        File file = new File(archivo);
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                registros = (List<T>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar datos: " + e.getMessage());
                registros = new ArrayList<>();
            }
        }
    }

    private void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(registros);
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }

    @Override
    public void crear(T entidad) {
        if (entidad.getId() == null) {
            entidad.setId(obtenerNuevoId());
        }
        registros.add(entidad);
        guardarDatos();
    }

    @Override
    public Optional<T> leer(Long id) {
        return registros.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<T> leerTodos() {
        return new ArrayList<>(registros);
    }

    @Override
    public void actualizar(T entidad) {
        for (int i = 0; i < registros.size(); i++) {
            if (registros.get(i).getId().equals(entidad.getId())) {
                registros.set(i, entidad);
                guardarDatos();
                return;
            }
        }
    }

    @Override
    public boolean eliminar(Long id) {
        boolean eliminado = registros.removeIf(e -> e.getId().equals(id));
        if (eliminado) {
            guardarDatos();
        }
        return eliminado;
    }

    @Override
    public Long obtenerNuevoId() {
        return registros.stream()
                .mapToLong(Entidad::getId)
                .max()
                .orElse(0L) + 1;
    }

    public List<T> buscarPorCriterio(java.util.function.Predicate<T> criterio) {
        return registros.stream()
                .filter(criterio)
                .collect(Collectors.toList());
    }
}
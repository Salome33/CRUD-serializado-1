package dao;

import modelo.Entidad;
import java.util.List;
import java.util.Optional;

public interface IDAO<T extends Entidad> {
    void crear(T entidad);
    Optional<T> leer(Long id);
    List<T> leerTodos();
    void actualizar(T entidad);
    boolean eliminar(Long id);
    Long obtenerNuevoId();
}
package modelo;

import java.io.Serializable;

public interface Entidad extends Serializable {
    Long getId();
    void setId(Long id);
}
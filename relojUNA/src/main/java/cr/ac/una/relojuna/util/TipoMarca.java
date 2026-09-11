package cr.ac.una.relojuna.util;

public enum TipoMarca {
    ENTRADA("E", "Entrada"),
    SALIDA("S", "Salida");

    private final String codigo;
    private final String descripcion;

    TipoMarca(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }

    public static TipoMarca fromCodigo(String codigo) {
        for (TipoMarca t : values()) {
            if (t.codigo.equalsIgnoreCase(codigo)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Código de tipo de marca inválido: " + codigo);
    }
}

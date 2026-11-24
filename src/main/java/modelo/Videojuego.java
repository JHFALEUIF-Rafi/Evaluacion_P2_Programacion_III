package modelo;

import java.time.Year;


public class Videojuego {
    
    public static final String[] CATEGORIAS = {
            "Acción", "Estrategia", "RPG", "Plataformas", "Simulación"
    };

    public static final int ANIO_MINIMO = 1980;
    public static final int VOTOS_INICIALES_NUEVO = 0;

    private final int codigoID;
    private String nombre;
    private String estudio;
    private String categoria;
    private int anioLanzamiento;
    private int votos;


    public Videojuego(int codigoID, String nombre, String estudio,
                      String categoria, int anioLanzamiento) {
        this(codigoID, nombre, estudio, categoria, anioLanzamiento, VOTOS_INICIALES_NUEVO);
    }

    public Videojuego(int codigoID, String nombre, String estudio,
                      String categoria, int anioLanzamiento, int votos) {
        validarCodigo(codigoID);
        validarDatos(nombre, estudio, categoria, anioLanzamiento);
        if (votos < 0) {
            throw new IllegalArgumentException("Los votos no pueden ser negativos.");
        }
        this.codigoID = codigoID;
        this.nombre = nombre.trim();
        this.estudio = estudio.trim();
        this.categoria = categoria;
        this.anioLanzamiento = anioLanzamiento;
        this.votos = votos;
    }

    public static void validarCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("El código debe ser un número positivo.");
        }
    }

    public static void validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
    }

    public static void validarCategoria(String categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Debe seleccionar una categoría.");
        }
        for (String c : CATEGORIAS) {
            if (c.equals(categoria)) {
                return;
            }
        }
        throw new IllegalArgumentException("Categoría no válida: " + categoria);
    }

    public static void validarAnio(int anio) {
        int anioActual = Year.now().getValue();
        if (anio < ANIO_MINIMO || anio > anioActual) {
            throw new IllegalArgumentException(
                    "El año debe estar entre " + ANIO_MINIMO + " y " + anioActual + ".");
        }
    }

    public static void validarDatos(String nombre, String estudio,
                                    String categoria, int anio) {
        validarTexto(nombre, "nombre");
        validarTexto(estudio, "estudio");
        validarCategoria(categoria);
        validarAnio(anio);
    }


    public void disminuirVotos(int cantidad) {
        if (cantidad < 1 || cantidad > 3) {
            throw new IllegalArgumentException("La cantidad debe estar entre 1 y 3.");
        }
        votos = Math.max(0, votos - cantidad);
    }


    public int getCodigoID() { return codigoID; }
    public String getNombre() { return nombre; }
    public String getEstudio() { return estudio; }
    public String getCategoria() { return categoria; }
    public int getAnioLanzamiento() { return anioLanzamiento; }
    public int getVotos() { return votos; }


    public void setNombre(String nombre) {
        validarTexto(nombre, "nombre");
        this.nombre = nombre.trim();
    }

    public void setEstudio(String estudio) {
        validarTexto(estudio, "estudio");
        this.estudio = estudio.trim();
    }

    public void setCategoria(String categoria) {
        validarCategoria(categoria);
        this.categoria = categoria;
    }

    public void setAnioLanzamiento(int anio) {
        validarAnio(anio);
        this.anioLanzamiento = anio;
    }

    @Override
    public String toString() {
        return codigoID + " | " + nombre + " | " + estudio + " | "
                + categoria + " | " + anioLanzamiento + " | Votos: " + votos;
    }

    public String toStringRanking() {
        return codigoID + " - " + nombre + " - " + votos + " votos";
    }
}

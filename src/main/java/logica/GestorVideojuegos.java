package logica;

import java.util.ArrayList;
import modelo.Videojuego;

public class GestorVideojuegos {

    private final ArrayList<Videojuego> videojuegos;

    public GestorVideojuegos() {
        this.videojuegos = new ArrayList<>();
        cargarDatosIniciales();
    }

    public GestorVideojuegos(boolean cargarIniciales) {
        this.videojuegos = new ArrayList<>();
        if (cargarIniciales) {
            cargarDatosIniciales();
        }
    }

    private void cargarDatosIniciales() {
        for (Videojuego v : DatosIniciales.cargar()) {
            registrar(v);
        }
    }

    public ArrayList<Videojuego> getVideojuegos() {
        return videojuegos;
    }

    public void ordenarPorCodigoDescendente() {
        int n = videojuegos.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (videojuegos.get(j).getCodigoID() < videojuegos.get(j + 1).getCodigoID()) {
                    Videojuego temp = videojuegos.get(j);
                    videojuegos.set(j, videojuegos.get(j + 1));
                    videojuegos.set(j + 1, temp);
                }
            }
        }
    }

    public int buscarPorCodigo(int codigo) {
        if (codigo <= 0) {
            return -1;
        }

        int inicio = 0;
        int fin = videojuegos.size() - 1;

        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            int codigoMedio = videojuegos.get(medio).getCodigoID();

            if (codigoMedio == codigo) {
                return medio;
            }

            if (codigo > codigoMedio) {
                fin = medio - 1;
            } else {
                inicio = medio + 1;
            }
        }

        return -1;
    }

    public boolean registrar(Videojuego v) {
        if (v == null) {
            return false;
        }
        Videojuego.validarCodigo(v.getCodigoID());

        if (buscarPorCodigo(v.getCodigoID()) != -1) {
            return false;
        }

        videojuegos.add(v);
        ordenarPorCodigoDescendente();
        return true;
    }

    public boolean eliminar(int codigo) {
        if (codigo <= 0) {
            return false;
        }
        int indice = buscarPorCodigo(codigo);
        if (indice != -1) {
            videojuegos.remove(indice);
            return true;
        }
        return false;
    }

    public ArrayList<Videojuego> buscarPorTexto(String texto) {
        ArrayList<Videojuego> resultados = new ArrayList<>();
        if (texto == null || texto.trim().isEmpty()) {
            return resultados;
        }

        for (Videojuego v : videojuegos) {
            if (v.getNombre().contains(texto) || v.getEstudio().contains(texto)) {
                resultados.add(v);
            }
        }
        return resultados;
    }

    public boolean restarVotos(int codigo, int cantidad) {
        int indice = buscarPorCodigo(codigo);
        if (indice == -1) {
            return false;
        }

        videojuegos.get(indice).disminuirVotos(cantidad);
        return true;
    }

    public boolean editar(int codigo, String nuevoNombre, String nuevoEstudio, String nuevaCategoria, int nuevoAnio) {
        int indice = buscarPorCodigo(codigo);
        if (indice == -1) {
            return false;
        }

        Videojuego.validarDatos(nuevoNombre, nuevoEstudio, nuevaCategoria, nuevoAnio);

        Videojuego v = videojuegos.get(indice);
        v.setNombre(nuevoNombre);
        v.setEstudio(nuevoEstudio);
        v.setCategoria(nuevaCategoria);
        v.setAnioLanzamiento(nuevoAnio);
        return true;
    }

    public ArrayList<Videojuego> obtenerRanking() {
        ArrayList<Videojuego> copia = new ArrayList<>(videojuegos);
        int n = copia.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (copia.get(j).getVotos() < copia.get(j + 1).getVotos()) {
                    Videojuego temp = copia.get(j);
                    copia.set(j, copia.get(j + 1));
                    copia.set(j + 1, temp);
                }
            }
        }
        return copia;
    }

}

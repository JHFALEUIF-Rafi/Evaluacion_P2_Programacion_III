package main.java.logica;

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
        for (Videojuego v : logica.DatosIniciales.cargar()) {
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

}

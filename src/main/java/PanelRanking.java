import logica.GestorVideojuegos;
import modelo.Videojuego;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.List;

public class PanelRanking extends JPanel {

    private final GestorVideojuegos gestor;
    private final DefaultListModel<String> modeloLista;
    private final JList<String> listaRanking;

    public PanelRanking(GestorVideojuegos gestor) {
        this.gestor = gestor;
        this.modeloLista = new DefaultListModel<>();
        this.listaRanking = new JList<>(modeloLista);

        setLayout(new BorderLayout());
        add(new JScrollPane(listaRanking), BorderLayout.CENTER);

        refrescar();
    }

    public void refrescar() {
        modeloLista.clear();
        if (gestor == null) {
            return;
        }
        List<Videojuego> ranking = gestor.obtenerRanking();
        for (Videojuego v : ranking) {
            modeloLista.addElement(v.toStringRanking());
        }
    }
}
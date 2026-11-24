
import javax.swing.*;
import logica.GestorVideojuegos;
import modelo.Videojuego;

import java.text.ParseException;
import java.time.Year;


public class Ventana {
    // Según la tabla del taller, los videojuegos nuevos empiezan con 20 votos
    private static final int VOTOS_INICIALES = 20;

    private JPanel Principal;
    private JTabbedPane tabbedPane1;
    private JList<Videojuego> listaCatalogo;   // muestra cada juego con su toString()
    private JLabel lblCantidad;
    private JButton btnRecargar;
    private JComboBox<String> cboCategoria;
    private JTextField txtEstudio;
    private JTextField txtNombre;
    private JSpinner spinAnio;
    private JButton btnRegistrar;
    private JTextField txtID;
    private GestorVideojuegos gestor;
    private final DefaultListModel<Videojuego> modeloCatalogo = new DefaultListModel<>();

    public Ventana() {
        gestor = new GestorVideojuegos();   // un solo gestor; los datos se cargan una sola vez

        // Tab 1: catálogo y formulario de registro
        listaCatalogo.setModel(modeloCatalogo);
        cboCategoria.setModel(new DefaultComboBoxModel<>(Videojuego.CATEGORIAS));
        int anioActual = Year.now().getValue();
        spinAnio.setModel(new SpinnerNumberModel(anioActual, Videojuego.ANIO_MINIMO, anioActual, 1));
        spinAnio.setEditor(new JSpinner.NumberEditor(spinAnio, "#")); // sin separador de miles

        // Tab 2: búsqueda, edición, eliminar y votar
        PanelBusqueda panelBusqueda = new PanelBusqueda(gestor);
        tabbedPane1.setComponentAt(1, panelBusqueda);          // reemplaza el tab "Untitled" vacío
        tabbedPane1.setTitleAt(1, "Búsqueda y edición");

        // Tab 3: ranking
        PanelRanking panelRanking = new PanelRanking(gestor);
        tabbedPane1.setComponentAt(2, panelRanking);           // reemplaza el segundo tab "Untitled"
        tabbedPane1.setTitleAt(2, "Ranking");
        tabbedPane1.addChangeListener(e -> {                   // al entrar al Tab 3, ranking actualizado
            if (tabbedPane1.getSelectedComponent() == panelRanking) {
                panelRanking.refrescar();
            }
        });

        // al guardar, eliminar o restar votos: refresca el Tab 1 y el ranking
        panelBusqueda.setAlGuardar(() -> {
            refrescarCatalogo();
            panelRanking.refrescar();
        });

        refrescarCatalogo();
        btnRegistrar.addActionListener(e -> registrarVideojuego());
        btnRecargar.addActionListener(e -> refrescarCatalogo());
    }

    private void registrarVideojuego() {
        String idTexto = txtID.getText().trim();
        String nombre = txtNombre.getText().trim();
        String estudio = txtEstudio.getText().trim();

        if (idTexto.isEmpty() || nombre.isEmpty() || estudio.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cboCategoria.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Selecciona una categoría.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String categoria = cboCategoria.getSelectedItem().toString();

        int id;
        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El código debe ser un número entero válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            spinAnio.commitEdit();   // toma el valor escrito a mano en el spinner
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null,
                    "El año debe estar entre " + Videojuego.ANIO_MINIMO + " y " + Year.now().getValue() + ".",
                    "Año inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int anio = (Integer) spinAnio.getValue();

        try {
            // El constructor valida código positivo, campos obligatorios, categoría y año
            Videojuego vd = new Videojuego(id, nombre, estudio, categoria, anio, VOTOS_INICIALES);
            if (!gestor.registrar(vd)) {
                JOptionPane.showMessageDialog(null, "Ya existe un videojuego con el código " + id + ".", "Código duplicado", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Datos no válidos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        refrescarCatalogo();
        txtID.setText("");
        txtNombre.setText("");
        txtEstudio.setText("");
        JOptionPane.showMessageDialog(null, "Videojuego registrado con éxito.");
    }

    public void refrescarCatalogo() {
        modeloCatalogo.clear();
        for (Videojuego v : gestor.getVideojuegos()) {
            modeloCatalogo.addElement(v);
        }
        lblCantidad.setText("Cantidad de videojuegos: " + gestor.getVideojuegos().size());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GameVotes");
        frame.setContentPane(new Ventana().Principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 650);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

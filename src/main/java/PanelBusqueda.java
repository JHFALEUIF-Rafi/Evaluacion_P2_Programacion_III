import main.java.logica.GestorVideojuegos;
import modelo.Videojuego;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PanelBusqueda extends JPanel {
    private final GestorVideojuegos gestor;
    // Búsqueda por texto
    private JTextField txtTexto;
    private JButton btnBuscarTexto;
    private JTextArea areaResultados;
    // Búsqueda por código
    private JTextField txtCodigoBuscar;
    private JButton btnBuscarCodigo;
    // Campos del videojuego encontrado
    private JTextField txtCodigo;      // bloqueado
    private JTextField txtNombre;
    private JTextField txtEstudio;
    private JComboBox<String> cmbCategoria;
    private JTextField txtAnio;
    private JTextField txtVotos;       // bloqueado
    private JButton btnGuardar;
    // Videojuego actualmente encontrado (null si no hay ninguno)
    private Videojuego seleccionado;
    // Acción que se ejecuta después de guardar (la Persona 4 la usa para refrescar el Tab 1)
    private Runnable alGuardar;
    public PanelBusqueda(GestorVideojuegos gestor) {
        this.gestor = gestor;
        setLayout(new BorderLayout(5, 5));
        add(crearPanelTexto(), BorderLayout.NORTH);
        add(crearPanelCodigo(), BorderLayout.CENTER);
        limpiarSeleccion();
    }

    public void setAlGuardar(Runnable alGuardar) {
        this.alGuardar = alGuardar;
    }

    private JPanel crearPanelTexto() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Buscar por nombre o estudio"));
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila.add(new JLabel("Texto:"));
        txtTexto = new JTextField(20);
        btnBuscarTexto = new JButton("Buscar");
        fila.add(txtTexto);
        fila.add(btnBuscarTexto);
        areaResultados = new JTextArea(8, 40);
        areaResultados.setEditable(false);
        panel.add(fila, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaResultados), BorderLayout.CENTER);
        btnBuscarTexto.addActionListener(e -> buscarPorTexto());
        return panel;
    }
    private void buscarPorTexto() {
        String texto = txtTexto.getText();
        if (texto == null || texto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Escribe un texto para buscar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<Videojuego> resultados = gestor.buscarPorTexto(texto);
        areaResultados.setText("");
        if (resultados == null || resultados.isEmpty()) {
            areaResultados.setText("No se encontraron resultados.");
            return;
        }
        for (Videojuego v : resultados) {
            areaResultados.append(v.toString() + "\n");
        }
    }

    public void refrescarBusquedaTexto() {
        if (!txtTexto.getText().trim().isEmpty()) {
            buscarPorTexto();
        }
    }

    private JPanel crearPanelCodigo() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Buscar por código y editar"));
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila.add(new JLabel("Código:"));
        txtCodigoBuscar = new JTextField(10);
        btnBuscarCodigo = new JButton("Buscar");
        fila.add(txtCodigoBuscar);
        fila.add(btnBuscarCodigo);
        JPanel formulario = new JPanel(new GridLayout(6, 2, 5, 5));
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtEstudio = new JTextField();
        cmbCategoria = new JComboBox<>(Videojuego.CATEGORIAS);
        txtAnio = new JTextField();
        txtVotos = new JTextField();
        // El código y los votos no se pueden editar
        txtCodigo.setEditable(false);
        txtVotos.setEditable(false);
        formulario.add(new JLabel("Código:"));
        formulario.add(txtCodigo);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Estudio:"));
        formulario.add(txtEstudio);
        formulario.add(new JLabel("Categoría:"));
        formulario.add(cmbCategoria);
        formulario.add(new JLabel("Año de lanzamiento:"));
        formulario.add(txtAnio);
        formulario.add(new JLabel("Votos:"));
        formulario.add(txtVotos);
        btnGuardar = new JButton("Guardar cambios");
        JPanel filaGuardar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filaGuardar.add(btnGuardar);
        panel.add(fila, BorderLayout.NORTH);
        panel.add(formulario, BorderLayout.CENTER);
        panel.add(filaGuardar, BorderLayout.SOUTH);
        btnBuscarCodigo.addActionListener(e -> buscarPorCodigo());
        btnGuardar.addActionListener(e -> guardarCambios());
        return panel;
    }
    private void buscarPorCodigo() {
        int codigo;
        try {
            codigo = Integer.parseInt(txtCodigoBuscar.getText().trim());
        } catch (NumberFormatException ex) {
            limpiarSeleccion();
            JOptionPane.showMessageDialog(this,
                    "El código debe ser un número entero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (codigo <= 0) {
            limpiarSeleccion();
            JOptionPane.showMessageDialog(this,
                    "El código debe ser positivo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int indice = gestor.buscarPorCodigo(codigo);
        if (indice == -1) {
            limpiarSeleccion();
            JOptionPane.showMessageDialog(this,
                    "No existe un videojuego con el código " + codigo + ".",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        seleccionado = gestor.getVideojuegos().get(indice);
        cargarCampos(seleccionado);
    }
    private void cargarCampos(Videojuego v) {
        txtCodigo.setText(String.valueOf(v.getCodigoID()));
        txtNombre.setText(v.getNombre());
        txtEstudio.setText(v.getEstudio());
        cmbCategoria.setSelectedItem(v.getCategoria());
        txtAnio.setText(String.valueOf(v.getAnioLanzamiento()));
        txtVotos.setText(String.valueOf(v.getVotos()));
    }

    public void limpiarSeleccion() {
        seleccionado = null;
        txtCodigo.setText("");
        txtNombre.setText("");
        txtEstudio.setText("");
        cmbCategoria.setSelectedIndex(0);
        txtAnio.setText("");
        txtVotos.setText("");
    }

    public Videojuego getSeleccionado() {
        return seleccionado;
    }

    private void guardarCambios() {
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Primero busca un videojuego por código.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int anio;
        try {
            anio = Integer.parseInt(txtAnio.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El año debe ser un número entero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String categoria = (String) cmbCategoria.getSelectedItem();
        try {
            // editar() valida todo antes de cambiar algo: si falla, no se modifica nada
            boolean editado = gestor.editar(seleccionado.getCodigoID(),
                    txtNombre.getText(), txtEstudio.getText(), categoria, anio);
            if (!editado) {
                limpiarSeleccion();
                JOptionPane.showMessageDialog(this,
                        "El videojuego ya no existe.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Datos no válidos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        cargarCampos(seleccionado);
        refrescarBusquedaTexto();
        if (alGuardar != null) {
            alGuardar.run();
        }
        JOptionPane.showMessageDialog(this,
                "Videojuego actualizado correctamente.");
    }
}
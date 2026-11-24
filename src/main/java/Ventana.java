
import javax.swing.*;
import main.java.logica.GestorVideojuegos;
import modelo.Videojuego;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Ventana {
    private JPanel Principal;
    private JTabbedPane tabbedPane1;
    private JTextArea textArea1;
    private JButton btnRecargar;
    private JComboBox cboCategoria;
    private JTextField txtEstudio;
    private JTextField txtNombre;
    private JSpinner spinAnio;
    private JButton btnRegistrar;
    private JTextField txtID;
    private GestorVideojuegos gestor;

    public Ventana() {
        gestor = new GestorVideojuegos();
        StringBuilder sb = new StringBuilder();
        for (modelo.Videojuego v : gestor.getVideojuegos()) {
            sb.append(v.toString()).append("\n");
        }

        textArea1.setText(sb.toString());
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
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

                    int id = Integer.parseInt(idTexto);
                    int anio = Integer.parseInt(spinAnio.getValue().toString());

                    if (id <= 0) {
                        JOptionPane.showMessageDialog(null, "El ID debe ser un número positivo.", "ID inválido", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (anio < 1950 || anio > 2030) {
                        JOptionPane.showMessageDialog(null, "Ingresa un año válido (1950 - 2030).", "Año inválido", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Videojuego vd = new Videojuego(id, nombre, estudio, categoria, anio, 20);
                    gestor.registrar(vd);

                    StringBuilder sb = new StringBuilder();
                    for (Videojuego v : gestor.getVideojuegos()) {
                        sb.append(v.toString()).append("\n");
                    }
                    textArea1.setText(sb.toString());

                    txtID.setText("");
                    txtNombre.setText("");
                    txtEstudio.setText("");

                    JOptionPane.showMessageDialog(null, "Videojuego registrado con éxito.");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El ID debe ser un número entero válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRecargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                for (Videojuego v : gestor.getVideojuegos()) {
                    sb.append(v.toString()).append("\n");
                }
                textArea1.setText(sb.toString());
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().Principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600,425);
    }
}

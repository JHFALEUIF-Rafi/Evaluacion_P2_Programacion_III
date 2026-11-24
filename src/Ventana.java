import main.java.logica.GestorVideojuegos;

import javax.swing.*;


public class Ventana {
    private JPanel Principal;
    private JTabbedPane tabbedPane1;
    private JTextArea textArea1;
    private JButton btnRecargar;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTextField textField2;
    private JSpinner spinner1;
    private JButton btnRegistrar;
    private JTextField textField3;
    private GestorVideojuegos gestor;

    public Ventana() {
        gestor = new GestorVideojuegos();
        StringBuilder sb = new StringBuilder();
        for (modelo.Videojuego v : gestor.getVideojuegos()) {
            sb.append(v.toString()).append("\n");
        }

        textArea1.setText(sb.toString());
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

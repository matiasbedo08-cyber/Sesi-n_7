package Ejer_3;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.File;

public class SelectorArchivo {
    private final JFileChooser chooser;

    public SelectorArchivo() {
        this.chooser = new JFileChooser();
        this.chooser.setDialogTitle("Selecciona un archivo de texto");
    }

    public File seleccionar() {
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    public void mostrarError(String mensaje) {
        System.out.println(mensaje);
        try {
            JOptionPane.showMessageDialog(null, mensaje + "\nSelecciona otro archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (java.awt.HeadlessException e) {
        }
    }
}
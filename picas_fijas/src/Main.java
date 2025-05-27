import ui.GameWindow;
import java.awt.GraphicsEnvironment;

public class Main {
    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("No se puede iniciar la interfaz gráfica: el entorno no soporta GUI (headless).");
            System.out.println("Ejecuta este programa en un entorno con soporte gráfico.");
            return;
        }
        javax.swing.SwingUtilities.invokeLater(GameWindow::new);
    }
}

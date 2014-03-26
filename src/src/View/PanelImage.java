package View;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import java.awt.image.ImageObserver;
import java.util.Observer;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * Panel en el que se visualizar�n las im�genes de los iconos que se usar�n
 * en la aplicaci�n generada.
 * @author Digna Rodr�guez
 * @version 1.0
 */
public class PanelImage
    extends JPanel {
  private Image imagen;
  private String ruta;

  /**
   * Constructor en el que se crea un nuevo panel con una imagen
   * @param imagenInicial Image que se carga.
   * @param r String ruta en la que se encuentra la imagen.
   */
  public PanelImage(Image imagenInicial, String r) {
    if (imagenInicial != null) {
      imagen = imagenInicial;
      ruta = r;
    }
  }

  /**
   * M�todo que se encarga de dibujar la imagen.
   * @param g Graphics gr�fico que se crea con la im�gen.
   */
  public void paint(Graphics g) {
    BufferedImage b = null;
    try {
      b = ImageIO.read(new File(ruta));
    }
    catch (IOException ex) {}

    int nHeight = b.getHeight();
    int nWidth = b.getWidth();

     g.drawImage(imagen, (726 - nWidth) / 2, (506 - nHeight) / 2, this);
    setOpaque(false);
    super.paint(g);
  }

  /**
   * M�todo que sirve para cambiar la imagen de un panel.
   * @param nuevaImagen Image nueva imagen por la que se cambia.
   */
  public void setImagen(Image nuevaImagen) {
    imagen = nuevaImagen;
    repaint();
  }

}

package View;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import com.borland.jbcl.layout.*;
import javax.swing.JTextPane;
import org.jgraph.JGraph;
import org.jgraph.graph.*;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphCell;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import javax.swing.JTabbedPane;
import javax.swing.border.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.lang.reflect.Method;
import Model.*;
import Model.Controller;

/**
 * Clase principal de la aplicaci�n que implementa la interfaz de la herramienta
 * atrav�s de la cual se pueden realizar todas las operaciones que puede
 * realizar el usuario.
 * @version 1.0
 * @author Digna Rodr�guez Cudeiro
 */
public class MainWindow
    extends JFrame {

  public static String pesta�aSeleccionada = "";
  private static String xmiPath = "";
  public static String javaPath = "";
  boolean DesignView = true;
  boolean treeClassesLoaded = false;
  boolean xmiImported = false;
  boolean xmlLoaded = false;
  boolean newProyectCreated = false;
  boolean guardadocomo = false;
  boolean mainClass = false;
  boolean proyectomodificado = false;
  boolean proyectoGuardado = false;
  boolean menuOculto = false;
  boolean proyectoCompilado= false;
  public static String nombreProyecto = "";
  String directorioProyecto = "";
  int contPest = 0;
  boolean paneljavamodificado = false;

  //diagrama de clases
  GraphModel model = null;
  GraphLayoutCache view = null;
  JGraph graph = null;

  //Para el �rbol de clases usamos un dynamic tree
  DynamicTree dtree = new DynamicTree();

  //Creamos el �rbol de Archivos java
  public JTree treeJava;
  DefaultMutableTreeNode topJava = new DefaultMutableTreeNode(
      "Contenedor de Archivos");

  //Men� Superior
  JMenuBar menuSuperior = new JMenuBar();
  JMenu menuArchivo = new JMenu();
  JMenu menuVista = new JMenu();
  JMenu menuEditar = new JMenu();
  JMenu menuHerramientas = new JMenu();
  JMenu menuAyuda = new JMenu();

  //Submen�s barra superior
  JMenuItem menuNuevo = new JMenuItem();
  JMenuItem menuAbrir = new JMenuItem();
  JMenuItem menuGuardar = new JMenuItem();
  JMenuItem menuGuardarComo = new JMenuItem();
  JMenuItem menuSalir = new JMenuItem();
  JMenuItem menuDise�o = new JMenuItem();
  JMenuItem menuIDEJava = new JMenuItem();
  JMenuItem menuRefresh = new JMenuItem();
  JMenuItem menuGenerarCodigo = new JMenuItem();
  JMenuItem menuGuardarArchivoActual = new JMenuItem();
  JMenuItem menuPregunta = new JMenuItem();
  JMenuItem menuAcerca = new JMenuItem();
  JMenuItem menuImportar = new JMenuItem();
  JMenuItem menuCopiar = new JMenuItem();
  JMenuItem menuCortar = new JMenuItem();
  JMenuItem menuPegar = new JMenuItem();
  JMenuItem menuImprimir = new JMenuItem();
  JMenuItem menuCompilar = new JMenuItem();
  JMenuItem menuClasePrin = new JMenuItem();
  JMenuItem menuCompilarArchivoActual = new JMenuItem();
  JMenuItem menuPreferencias = new JMenuItem();
  JMenuItem menuEjecutar = new JMenuItem();
  JMenuItem menuAbrirCarpeta = new JMenuItem();
  JMenuItem menuGuardarArchivosJava = new JMenuItem();
  JMenuItem menuOcultarMenu = new JMenuItem();
  JMenuItem menuJavadoc = new JMenuItem();
  JMenuItem menuFijarDiagrama = new JMenuItem();
  JMenuItem menuNuevoJava = new JMenuItem();
  JMenuItem menuBorrarJava = new JMenuItem();
  JMenuItem menuOpenJava = new JMenuItem();

  //Paneles
  JPanel panelFondo = new JPanel();
  JPanel jPanelArboles = new JPanel();
  JPanel panelArbolSup = new JPanel();
  JPanel panelbuttonsArbol = new JPanel();

  //buttones
  JButton buttonNuevo = new JButton();
  JButton buttonOpen = new JButton();
  JButton buttonImportar = new JButton();
  JButton buttonPreferencias = new JButton();
  JButton buttonGuardar = new JButton();
  JButton buttonGuardarComo = new JButton();
  JButton buttonGenerar = new JButton();
  JButton buttonAbrirCarpeta = new JButton();
  JButton buttonCompilar = new JButton();
  JButton buttonAyuda = new JButton();
  JButton buttonJavadoc = new JButton();
  JButton buttonImprimir = new JButton();
  JButton buttonRefresh = new JButton();
  JButton buttonAdd = new JButton();
  JButton buttonDelete = new JButton();
  JButton buttonDeleteAll = new JButton();
  JButton buttonConsultar = new JButton();
  JButton buttonCambiarVista = new JButton();
  JButton buttonEjecutar = new JButton();
  JButton buttonGuardarJava = new JButton();
  JButton buttonAyudaDise�o = new JButton();
  JButton buttonCompilarPestana = new JButton();
  JButton buttonPrefe = new JButton();
  JButton buttonCloseTab = new JButton();
  JButton buttonGuardarTodo = new JButton();
  JButton buttonNuevoFichero = new JButton();
  JButton buttonBorrarFichero = new JButton();
  JButton buttonHideMenu = new JButton();
  JButton buttonSelectMainClass = new JButton();
  JButton buttonOpenJava = new JButton();

  //Iconos
  ImageIcon iNuevo;
  ImageIcon iAbrir;
  ImageIcon iImportar;
  ImageIcon iCambiarVista;
  ImageIcon iPrefe;
  ImageIcon iGuardar;
  ImageIcon iGuardarJava;
  ImageIcon iGuardarTodo;
  ImageIcon iRefresh;
  ImageIcon iGuardarComo;
  ImageIcon iAyuda;
  ImageIcon iGenerar;
  ImageIcon iClose;
  ImageIcon iAbout;
  ImageIcon iInterfaz;
  ImageIcon iCompile;
  ImageIcon iAbrirCarpeta;
  ImageIcon iImprimirCodigo;
  ImageIcon iTab;
  ImageIcon iClase;
  ImageIcon iDelete;
  ImageIcon iAdd;
  ImageIcon iDeleteAll;
  ImageIcon iConsult;
  ImageIcon iAtb;
  ImageIcon iExec;
  ImageIcon iMet;
  ImageIcon iJava;
  ImageIcon iXML;
  ImageIcon iSelected;
  ImageIcon iUnSelected;
  ImageIcon icompileSave;
  ImageIcon iNewFile;
  ImageIcon iDeleteFile;
  ImageIcon iArrow;
  ImageIcon iSelectMain;
  ImageIcon iOpenJava;

  //labels
  JLabel jLabel1 = new JLabel();
  JLabel jLabelTituloArbol = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel labelx = new JLabel();
  JLabel labelEstado = new JLabel();

  //Barras de herramientas
  JToolBar jToolBarIDE = new JToolBar();
  JToolBar jToolBarDise�o = new JToolBar();

  //layouts
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout11 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();
  XYLayout xYLayout6 = new XYLayout();
  XYLayout xYLayout7 = new XYLayout();
  XYLayout xYLayout9 = new XYLayout();
  XYLayout xYLayout10 = new XYLayout();
  GridLayout gridLayout1 = new GridLayout();
  GridLayout gridLayout2 = new GridLayout();

  //paneles
  JPanel jPanelDcha = new JPanel();
  JPanel paneIcons = new JPanel();
  JPanel panelbuttonCerrar = new JPanel();
  JPanel jPanelArbolXML = new JPanel();
  JPanel jPanelJav = new JPanel();
  JPanel panelDiagrama = new JPanel();
  JTextPane panelTexto = new JTextPane();
  JTextPane panelTextoJava = new JTextPane();
  JTabbedPane panelCodigo = new JTabbedPane();
  JSplitPane splitPaneJava = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
  JScrollPane jScroll = new JScrollPane();
  JPanel panelConsola = new JPanel();
  JPanel panelEstado = new JPanel();
  JTextPane textConsola = new JTextPane();

  Border border1;
  JTextArea text = new JTextArea();


  public MainWindow() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * El m�todo jbInit se encarga de la inicializaci�n de componentes
   * de la interfaz.
   * @throws en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepci�n.
   * @throws Exception lanza una excepci�n si no consigue pintar la ventana.
   */
  private void jbInit() throws Exception {

    //tama�o y t�tulo de la ventana principal
    border1 = BorderFactory.createEmptyBorder();

    this.setForeground(Color.black);
    this.setSize(new Dimension(1030, 714));
    this.setTitle("  Generador Integral de Aplicaciones");
    this.setExtendedState(Frame.MAXIMIZED_BOTH);
    panelFondo = (JPanel)this.getContentPane();

    /************************Inicializaci�n �rboles**********************/
    JScrollPane treeView = new JScrollPane(dtree.tree);
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    splitPane.setTopComponent(treeView);
    splitPane.setBorder(border1);
    splitPane.setPreferredSize(new Dimension(200, 400));

    treeJava = new JTree(topJava);
    treeJava.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);

    MouseListener mll = new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          TreePath path = dtree.tree.getPathForLocation(e.getX(), e.getY());
          if (path == null
              || path.getPathCount() == 0) {
            return;
          }
          modifyNode();
        }
      }
    };

    MouseListener ml2 = new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          TreePath path = treeJava.getPathForLocation(e.getX(), e.getY());
          /*    System.out.println("Java:doble clic: " +
                                 path.getLastPathComponent());*/
          if (path == null
              || path.getPathCount() == 0) {
            return;
          }
          String nodoseleccionado = path.getLastPathComponent().toString();
          int i = 0;
          while (i < nodoseleccionado.length() &&
                 (nodoseleccionado.charAt(i) != '.')) {
            i++;
          }
          if (i == nodoseleccionado.length()) {
            // si hacemos doble clic en una carpeta no hacemos nada
          }
          else if (nodoseleccionado.charAt(i) == '.') {
            String extension = nodoseleccionado.substring(i,
                nodoseleccionado.length());
            if (extension.compareTo(".java") == 0) {
              MainWindow.pesta�aSeleccionada = path.getLastPathComponent().
                  toString();
              openFileInTab(path.getLastPathComponent().toString());
            }
            else {
              openImageInTab(path.getLastPathComponent().toString());
            }
          }
        }
      }
    };

    //Escucha doble click
    dtree.tree.addMouseListener(mll);
    treeJava.addMouseListener(ml2);
    JScrollPane treeView2 = new JScrollPane(treeJava);
    Dimension minimumSize2 = new Dimension(100, 50);
    treeView2.setMinimumSize(minimumSize2);
    jPanelArboles.setLayout(xYLayout2);
    panelArbolSup.setLayout(xYLayout4);
    jLabelTituloArbol.setText("                  Clases del proyecto");
    jLabelTituloArbol.setBorder(BorderFactory.createEtchedBorder());
    jLabelTituloArbol.setBackground(SystemColor.menu);
    jLabelTituloArbol.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 10));
    panelbuttonsArbol.setBackground(SystemColor.control);
    panelbuttonsArbol.setBorder(BorderFactory.createEtchedBorder());
    panelbuttonsArbol.setDoubleBuffered(true);
    panelbuttonsArbol.setLayout(gridLayout1);
    buttonDelete.setBorder(null);
    buttonDelete.setBorderPainted(true);
    buttonDelete.setContentAreaFilled(false);
    buttonDelete.setText("");
    buttonAdd.setBorder(null);
    buttonAdd.setContentAreaFilled(false);
    buttonDeleteAll.setBorder(null);
    buttonDeleteAll.setContentAreaFilled(false);
    buttonConsultar.setToolTipText("Modificar el elemento");
    buttonConsultar.setBorderPainted(true);
    buttonConsultar.setContentAreaFilled(false);
    buttonConsultar.setText("");
    jPanelArboles.setBorder(BorderFactory.createLoweredBevelBorder());
    buttonCloseTab.addActionListener(new Main_closeTab_ActionAdapter(this));
    buttonImportar.setBorderPainted(true);
    buttonImportar.setContentAreaFilled(true);

    paneIcons.setLayout(xYLayout7);
    paneIcons.setBorder(BorderFactory.createEtchedBorder());
    buttonCloseTab.setEnabled(false);
    buttonCloseTab.setAlignmentX( (float) 0.5);
    buttonCloseTab.setAlignmentY( (float) 0.5);
    buttonCloseTab.setPreferredSize(new Dimension(121, 20));

    buttonCloseTab.setBorderPainted(true);
    buttonCloseTab.setFocusPainted(true);
    buttonCloseTab.setIcon(iClose);
    buttonCloseTab.setText(" Cerrar Pesta�a");
    panelbuttonCerrar.setDebugGraphicsOptions(0);
    panelbuttonCerrar.setLayout(gridLayout2);
    jPanelDcha.setBorder(BorderFactory.createEtchedBorder());
    jPanelDcha.setLayout(xYLayout9);
    splitPaneJava.setTopComponent(treeView2);
    splitPaneJava.setPreferredSize(new Dimension(200, 400));
    jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 10));
    jLabel1.setBorder(BorderFactory.createEtchedBorder());
    jLabel1.setText("             Archivos Java Generados");
    jPanelArbolXML.setLayout(xYLayout10);
    jLabel3.setText("             Archivos Generados");
    jLabel3.setBorder(BorderFactory.createEtchedBorder());
    jLabel3.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 10));
    splitPaneJava.setPreferredSize(new Dimension(200, 400));
    splitPaneJava.setTopComponent(treeView2);
    jPanelJav.setLayout(xYLayout11);

    /****************************buttons**************************************/
    //Carga de las im�genes de los iconos
    iNuevo = new ImageIcon(MainWindow.class.getResource("Images/new.png"));
  iAbrir = new ImageIcon(MainWindow.class.getResource("Images/openZip.png"));
  iImportar = new ImageIcon(MainWindow.class.getResource("Images/import.png"));
  iCambiarVista = new ImageIcon(MainWindow.class.getResource(
      "Images/changeView.png"));
  iClase = new ImageIcon(MainWindow.class.getResource("Images/clase.png"));
  iPrefe = new ImageIcon(MainWindow.class.getResource("Images/prefe.png"));
  iGuardar = new ImageIcon(MainWindow.class.getResource("Images/save.png"));
  iGuardarTodo = new ImageIcon(MainWindow.class.getResource(
      "Images/saveall.png"));
  iRefresh = new ImageIcon(MainWindow.class.getResource("Images/refresh.png"));
  iGuardarComo = new ImageIcon(MainWindow.class.getResource(
      "Images/saveas.png"));
  icompileSave = new ImageIcon(MainWindow.class.getResource(
      "Images/save&compile.png"));
  iXML = new ImageIcon(MainWindow.class.getResource("Images/ixml.png"));
  iTab = new ImageIcon(MainWindow.class.getResource("Images/document.png"));
  iAyuda = new ImageIcon(MainWindow.class.getResource("Images/help.png"));
  iExec = new ImageIcon(MainWindow.class.getResource("Images/exec.png"));
  iGenerar = new ImageIcon(MainWindow.class.getResource("Images/generate.png"));
  iAbout = new ImageIcon(MainWindow.class.getResource("Images/info.png"));
  iInterfaz = new ImageIcon(MainWindow.class.getResource(
      "Images/interfaz.png"));
  iCompile = new ImageIcon(MainWindow.class.getResource(
      "Images/compile.png"));
  iAbrirCarpeta = new ImageIcon(MainWindow.class.getResource(
      "Images/openfolder.png"));
  iImprimirCodigo = new ImageIcon(MainWindow.class.getResource(
      "Images/print.png"));
  iAdd = new ImageIcon(MainWindow.class.getResource(
      "Images/window_add.png"));
  iDelete = new ImageIcon(MainWindow.class.getResource(
      "Images/window_sub.png"));
  iDeleteAll = new ImageIcon(MainWindow.class.getResource(
      "Images/window_remove.png"));
  iConsult = new ImageIcon(MainWindow.class.getResource(
      "Images/window_edit.png"));
  iClose = new ImageIcon(MainWindow.class.getResource(
      "Images/close_mini.png"));
  iNewFile = new ImageIcon(MainWindow.class.getResource(
      "Images/page_add.png"));
  iDeleteFile = new ImageIcon(MainWindow.class.getResource(
      "Images/page_remove.png"));
  iArrow = new ImageIcon(MainWindow.class.getResource(
      "Images/arrow.png"));
  iSelectMain = new ImageIcon(MainWindow.class.getResource(
      "Images/selectMain.png"));
  iOpenJava = new ImageIcon(MainWindow.class.getResource(
      "Images/openJava.png"));

    //Asignamos a cada bot�n su icono
    buttonNuevo.setIcon(iNuevo);
    buttonOpen.setIcon(iAbrir);
    buttonImportar.setIcon(iImportar);
    buttonNuevoFichero.setIcon(iNewFile);
    buttonBorrarFichero.setIcon(iDeleteFile);
    buttonPrefe.setIcon(iPrefe);
    buttonGuardar.setIcon(iGuardar);
    buttonGuardarJava.setIcon(iGuardar);
    buttonGuardarTodo.setIcon(iGuardarTodo);
    buttonRefresh.setIcon(iRefresh);
    buttonCloseTab.setIcon(iClose);
    buttonGuardarComo.setIcon(iGuardarComo);
    buttonAyuda.setIcon(iAyuda);
    buttonGenerar.setIcon(iGenerar);
    buttonCompilar.setIcon(iCompile);
    buttonAbrirCarpeta.setIcon(iAbrirCarpeta);
    buttonImprimir.setIcon(iImprimirCodigo);
    buttonAdd.setIcon(iAdd);
    buttonDelete.setIcon(iDelete);
    buttonDeleteAll.setIcon(iDeleteAll);
    buttonConsultar.setIcon(iConsult);
    buttonNuevo.setIcon(iNuevo);
    buttonEjecutar.setIcon(iExec);
    buttonAyudaDise�o.setIcon(iAyuda);
    buttonJavadoc.setIcon(iAbout);
    buttonCambiarVista.setIcon(iCambiarVista);
    buttonCompilarPestana.setIcon(icompileSave);
    buttonHideMenu.setIcon(iArrow);
    buttonSelectMainClass.setIcon(iSelectMain);
    buttonOpenJava.setIcon(iOpenJava);

    buttonCloseTab.setText("cerrar");
    buttonCloseTab.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

    panelDiagrama.setLayout(xYLayout5);
    panelDiagrama.setBorder(BorderFactory.createEtchedBorder());
    panelDiagrama.setDebugGraphicsOptions(0);

    jToolBarDise�o.setBorderPainted(false);

    buttonEjecutar.setToolTipText("Ejecutar el Proyecto Generado");
    buttonGuardarTodo.setToolTipText("Guardar Todos los archivos");
    buttonNuevoFichero.setToolTipText("Crear Nuevo Archivo java");
    buttonOpenJava.setToolTipText("A�adir Archivo java existente");
    buttonBorrarFichero.setToolTipText("Borrar fichero seleccionado");
    panelEstado.setBorder(BorderFactory.createEtchedBorder());
    panelEstado.setLayout(xYLayout3);

    labelEstado.setText("");
    panelConsola.setBorder(BorderFactory.createEtchedBorder());
    panelConsola.setLayout(xYLayout6);
    buttonHideMenu.setFont(new java.awt.Font("Dialog", 0, 8));
    buttonHideMenu.setBorder(null);
    buttonHideMenu.setToolTipText("Oculta Menu principal");
    buttonHideMenu.setBorderPainted(false);
    buttonHideMenu.setContentAreaFilled(false);
    buttonHideMenu.addActionListener(new Main_menuHide_ActionAdapter(this));

    textConsola.setText("");
    textConsola.setBackground(Color.white);
    textConsola.setToolTipText("");
    textConsola.setEditable(false);
    textConsola.setSelectedTextColor(Color.white);
    textConsola.setSelectionEnd(0);
    panelTextoJava.setSelectedTextColor(Color.green);
    getContentPane().add(splitPane);

    //Agregamos etiquetas a los buttones
    buttonNuevo.setToolTipText("Crear Nuevo Proyecto");
    buttonOpen.setToolTipText("Abrir Proyecto");
    buttonImportar.setToolTipText("Importar Archivo XMI");
    buttonPrefe.setToolTipText("Preferencias");
    buttonCompilarPestana.setToolTipText("Guardar y Compilar pesta�a actual");
    buttonSelectMainClass.setToolTipText(
        "Seleccionar clase principal del sistema");
    buttonCloseTab.setToolTipText("Cerrar Pesta�a");
    buttonGuardar.setToolTipText("Guardar");
    buttonCambiarVista.setToolTipText("Cambiar vista");
    buttonRefresh.setToolTipText("Refrescar Contenidos");
    buttonGuardarComo.setToolTipText("Guardar como...");
    buttonAyuda.setToolTipText("Ayuda");
    buttonGenerar.setToolTipText("Generar c�digo");
    buttonCompilar.setToolTipText("Compilar Proyecto Generado");
    buttonAbrirCarpeta.setToolTipText(
        "Abrir carpeta contenedora de archivos *.java");
    buttonImprimir.setToolTipText("Imprimir c�digo");
    buttonAdd.setToolTipText("A�adir un nuevo elemento al �rbol");
    buttonDelete.setToolTipText("Borrar del �rbol el elemento seleccionado");
    buttonDeleteAll.setToolTipText("Borrar todos los elementos del �rbol");
    buttonGuardarJava.setToolTipText("Guardar pesta�a actual");
    buttonAyudaDise�o.setToolTipText("Ayuda");
    buttonJavadoc.setToolTipText("Generar Documentaci�n Javadoc");

    //Escucha de accciones de cada bot�n
    buttonNuevo.addActionListener(new Main_menuNew_ActionAdapter(this));
    buttonOpen.addActionListener(new Main_menuOpen_ActionAdapter(this));
    buttonImportar.addActionListener(new Main_menuImport_ActionAdapter(this));
    buttonBorrarFichero.addActionListener(new Main_menuDeleteJava_ActionAdapter(this));
    buttonNuevoFichero.addActionListener(new
                                         Main_menuNewJava_ActionAdapter(this));
    buttonOpenJava.addActionListener(new
                                     Main_menuOpenJava_ActionAdapter(this));
    buttonGuardarComo.addActionListener(new
                                        Main_menuSaveAs_ActionAdapter(this));
    buttonGuardar.addActionListener(new
                                    Main_menuSave_ActionAdapter(this));
    buttonGuardarJava.addActionListener(new
                                        Main_menuSaveJava_ActionAdapter(this));
    buttonGuardarTodo.addActionListener(new
                                        Main_menuSaveAllJava_ActionAdapter(this));
    buttonGenerar.addActionListener(new
                                    Main_menuGenerate_ActionAdapter(this));
    buttonEjecutar.addActionListener(new Main_menuExec_ActionAdapter(this));
    buttonAbrirCarpeta.addActionListener(new
                                         Main_menuOpenFolder_ActionAdapter(this));
    buttonImprimir.addActionListener(new Main_menuPrint_ActionAdapter(this));
    buttonCompilar.addActionListener(new Main_menuCompile_ActionAdapter(this));

    buttonPrefe.addActionListener(new Main_menuPreferences_ActionAdapter(this));
    buttonRefresh.addActionListener(new Main_menuRefresh_ActionAdapter(this));
    buttonCompilarPestana.addActionListener(new
                                            Main_menuCompileJava_ActionAdapter(this));
    buttonCambiarVista.addActionListener(new
                                         Main_menuChangeToDesingView_ActionAdapter(this));
    buttonAyudaDise�o.addActionListener(new
                                        Main_menuHelp_ActionAdapter(this));
    buttonAyuda.addActionListener(new
                                  Main_menuHelp_ActionAdapter(this));
    buttonJavadoc.addActionListener(new
                                    Main_menuJavaDoc_ActionAdapter(this));
    buttonSelectMainClass.addActionListener(new
                                            Main_menuMainClass_ActionAdapter(this));

    //buttones del Arbol
    buttonAdd.addActionListener(new Main_treeAdd_ActionAdapter(this));
    buttonDelete.addActionListener(new Main_treeRemoveNode_ActionAdapter(this));
    buttonDeleteAll.addActionListener(new Main_treeRemoveAll_ActionAdapter(this));
    buttonConsultar.addActionListener(new Main_treeModifyNode_ActionAdapter(this));

    panelFondo.setLayout(xYLayout1);
    panelCodigo.setBackground(Color.lightGray);
    jPanelArboles.setBackground(Color.lightGray);

    //***************************Men� barra superior**************************/
    //Inicializaci�n del menu Archivo del menu principal y sus componentes
    menuArchivo.setText("Archivo");
    menuNuevo.setText("Nuevo Proyecto");
    menuAbrir.setText("Abrir Proyecto...");
    menuImportar.setText("Importar XMI...");
    menuGuardar.setText("Guardar Proyecto");
    menuGuardarComo.setText("Guardar Proyecto Como");
    menuNuevoJava.setText("Nuevo Archivo Java");
    menuOpenJava.setText("Abrir Archivo Java");
    menuBorrarJava.setText("Borrar Archivo Java");
    menuImprimir.setText("Imprimir Archivo");
    menuGuardarArchivoActual.setText("Guardar Archivo actual");
    menuGuardarArchivosJava.setText("Guardar Todo");
    menuSalir.setText("Salir");

    menuArchivo.add(menuNuevo);
    menuArchivo.add(menuAbrir);
    menuArchivo.add(menuImportar);
    menuArchivo.add(menuGuardar);
    menuArchivo.add(menuGuardarComo);
    menuArchivo.addSeparator();
    menuArchivo.add(menuImprimir);
    menuArchivo.add(menuNuevoJava);
    menuArchivo.add(menuOpenJava);
    menuArchivo.add(menuBorrarJava);
    menuArchivo.add(menuGuardarArchivoActual);
    menuArchivo.add(menuGuardarArchivosJava);
    menuArchivo.addSeparator();
    menuArchivo.add(menuSalir);
    menuSalir.addActionListener(new Main_menuExit_ActionAdapter(this));
    menuNuevo.addActionListener(new Main_menuNew_ActionAdapter(this));
    menuAbrir.addActionListener(new Main_menuOpen_ActionAdapter(this));
    menuImportar.addActionListener(new Main_menuImport_ActionAdapter(this));
    menuGuardar.addActionListener(new Main_menuSave_ActionAdapter(this));
    menuGuardarComo.addActionListener(new Main_menuSaveAs_ActionAdapter(this));
    menuImprimir.addActionListener(new Main_menuPrint_ActionAdapter(this));
    menuNuevoJava.addActionListener(new Main_menuNewJava_ActionAdapter(this));
    menuOpenJava.addActionListener(new Main_menuOpenJava_ActionAdapter(this));
    menuBorrarJava.addActionListener(new Main_menuDeleteJava_ActionAdapter(this));
    menuGuardarArchivosJava.addActionListener(new
                                              Main_menuSaveAllJava_ActionAdapter(this));
    menuGuardarArchivoActual.addActionListener(new
                                               Main_menuSaveJava_ActionAdapter(this));
    menuEditar.setText("Editar");
    menuCopiar.setText("Copiar (Ctrl + c)");
    menuCortar.setText("Cortar (Ctrl + x)");
    menuPegar.setText("Pegar (Ctrl + v)");
    menuFijarDiagrama.setText("Fijar diagrama");
    menuEditar.add(menuFijarDiagrama);
    menuEditar.addSeparator();
    menuEditar.add(menuCopiar);
    menuEditar.add(menuCortar);
    menuEditar.add(menuPegar);

    menuCopiar.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menuCopy(e);
      }
    }
    );
    menuCortar.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menuCut(e);
      }
    }
    );
    menuPegar.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menuPaste(e);
      }
    }
    );
    menuFijarDiagrama.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menuSetEditableDiagram(e);
      }

    }
    );

    //Inicializaci�n del men� Vista y sus componentes
    menuVista.setText("Vista");
    menuDise�o.setText("Dise�o");
    menuIDEJava.setText("IDE Java");
    menuVista.add(menuDise�o);
    menuVista.add(menuIDEJava);
    menuIDEJava.addActionListener(new Main_menuGenerate_ActionAdapter(this));
    menuDise�o.addActionListener(new
                                 Main_menuChangeToDesingView_ActionAdapter(this));

    //Inicializaci�n del men� Herramientas y sus componentes
    menuHerramientas.setText("Herramientas");
    menuRefresh.setText("Refrescar Contenidos");
    menuGenerarCodigo.setText("Generar C�digo");
    menuArchivo.addSeparator();
    menuAbrirCarpeta.setText("Abrir Contenedor de Archivos");
    menuCompilarArchivoActual.setText("Compilar Pesta�a Actual");
    menuCompilar.setText("Compilar Archivos Generados ");
    menuEjecutar.setText("Ejecutar proyecto Generado");
    menuJavadoc.setText("Generar documentaci�n JavaDoc");
    menuOcultarMenu.setText("Ocultar Menu Principal");
    menuClasePrin.setText("Seleccionar Clase Principal");
    menuPreferencias.setText("Preferencias");
    menuHerramientas.add(menuRefresh);
    menuHerramientas.add(menuGenerarCodigo);
    menuHerramientas.addSeparator();
    menuHerramientas.add(menuAbrirCarpeta);
    menuHerramientas.add(menuCompilarArchivoActual);
    menuHerramientas.add(menuCompilar);
    menuHerramientas.add(menuEjecutar);
    menuHerramientas.add(menuJavadoc);
    menuHerramientas.addSeparator();
    menuHerramientas.add(menuOcultarMenu);
    menuHerramientas.add(menuClasePrin);
    menuHerramientas.add(menuPreferencias);
    menuGenerarCodigo.addActionListener(new
                                        Main_menuGenerate_ActionAdapter(this));
    menuRefresh.addActionListener(new Main_menuRefresh_ActionAdapter(this));
    menuAbrirCarpeta.addActionListener(new Main_menuOpenFolder_ActionAdapter(this));
    menuCompilar.addActionListener(new Main_menuCompile_ActionAdapter(this));
    menuCompilarArchivoActual.addActionListener(new
                                                Main_menuCompileJava_ActionAdapter(this));
    menuEjecutar.addActionListener(new Main_menuExec_ActionAdapter(this));
    menuJavadoc.addActionListener(new Main_menuJavaDoc_ActionAdapter(this));
    menuOcultarMenu.addActionListener(new Main_menuHide_ActionAdapter(this));
    menuClasePrin.addActionListener(new Main_menuMainClass_ActionAdapter(this));
    menuPreferencias.addActionListener(new Main_menuPreferences_ActionAdapter(this));

    //Inicializaci�n del men� Ayuda y sus componentes
    menuAyuda.setText("Ayuda");
    menuPregunta.setText("Ayuda Dise�o");
    menuAcerca.setText("Acerca de...  ");
    menuAcerca.addActionListener(new Main_menuAbout_ActionAdapter(this));
    menuPregunta.addActionListener(new Main_menuHelp_ActionAdapter(this));
    menuAyuda.add(menuPregunta);
    menuAyuda.add(menuAcerca);

    //A�adimos los men�s completos al elemento menuSuperior
    menuSuperior.add(menuArchivo);
    menuSuperior.add(menuVista);
    menuSuperior.add(menuEditar);
    menuSuperior.add(menuHerramientas);
    menuSuperior.add(menuAyuda);

    //A�adimos los buttones a las barras de herramientas izquierda y dcha
    jToolBarDise�o.add(buttonNuevo, null);
    jToolBarDise�o.add(buttonOpen, null);
    jToolBarDise�o.add(buttonImportar, null);
    jToolBarDise�o.add(buttonRefresh, null);
    jToolBarDise�o.add(buttonPrefe, null);
    jToolBarDise�o.add(buttonGuardar, null);
    jToolBarDise�o.add(buttonGuardarComo, null);
    jToolBarDise�o.add(buttonGenerar, null);
    jToolBarDise�o.add(buttonAyudaDise�o, null);
    paneIcons.add(buttonHideMenu, new XYConstraints(720, 0, 17, 16));
    panelFondo.add(panelConsola, new XYConstraints(271, 554, 740, 112));
    panelFondo.add(panelEstado, new XYConstraints(13, 670, 999, 27));
    panelEstado.add(labelEstado, new XYConstraints(3, 3, 989, 17));

    paneIcons.add(jToolBarIDE, new XYConstraints( -4, 2, 454, 36));
    panelFondo.add(panelbuttonCerrar, new XYConstraints(913, 58, 81, 22));
    panelFondo.add(panelCodigo, new XYConstraints(270, 69, 740, 481));
    paneIcons.add(jToolBarDise�o, new XYConstraints(0, 0, 397, 32));
    panelFondo.add(panelDiagrama, new XYConstraints(270, 59, 742, 606));
    panelFondo.add(jPanelArboles, new XYConstraints(13, 15, -1, 651));
    jPanelArboles.add(panelArbolSup, new XYConstraints( -1, 0, 238, 646));
    panelFondo.add(jPanelDcha, new XYConstraints(13, 14, 243, 652));
    panelbuttonCerrar.add(buttonCloseTab, null);
    jPanelJav.add(splitPaneJava, new XYConstraints( -1, 34, 229, 608));
    jPanelJav.add(jLabel1, new XYConstraints( -5, 0, 238, 34));
    jPanelDcha.add(jPanelJav, new XYConstraints(3, 3, 229, 641));

    panelbuttonCerrar.add(buttonCloseTab, null);
    jToolBarIDE.add(buttonCambiarVista, null);
    jToolBarIDE.add(buttonNuevoFichero, null);
    jToolBarIDE.add(buttonBorrarFichero, null);
    jToolBarIDE.add(buttonOpenJava, null);
    jToolBarIDE.add(buttonGuardarJava, null);
    jToolBarIDE.add(buttonGuardarTodo, null);
    jToolBarIDE.add(buttonImprimir, null);
    jToolBarIDE.add(buttonAbrirCarpeta, null);
    jToolBarIDE.add(buttonCompilarPestana, null);
    jToolBarIDE.add(buttonCompilar, null);
    jToolBarIDE.add(buttonEjecutar, null);
    jToolBarIDE.add(buttonAyuda, null);
    jToolBarIDE.add(buttonJavadoc, null);

    panelArbolSup.add(jLabelTituloArbol, new XYConstraints(0, 0, 239, 23));
    panelArbolSup.add(panelbuttonsArbol, new XYConstraints( -1, 21, 240, -1));

    panelbuttonsArbol.add(buttonConsultar, null);
    panelbuttonsArbol.add(buttonDelete, null);
    panelbuttonsArbol.add(buttonAdd, null);
    panelbuttonsArbol.add(buttonDeleteAll, null);
    panelArbolSup.add(splitPane, new XYConstraints(0, 62, 238, 591));
    panelFondo.add(paneIcons, new XYConstraints(270, 14, 742, -1));
    jScroll.getViewport().add(textConsola, null);

    this.panelCodigo.setVisible(true);
    //colocamos la barra de men�s superior en el panel principal
    this.setJMenuBar(menuSuperior);

   activeDesingView();
   //  activeIDEView();

  }

  /**
   * El m�todo getRutaXMI devuelve la ruta del archivo XMI en
   * disco.
   * @return String ruta del archivo.
   */
  public static String getXmiPath() {
    return xmiPath;
  }

  /**
   * El m�todo fileIsOpenInTab comprueba si el fichero ya est� abierto
   * en una pesta�a del panel donde se muestra el c�digo.
   * @param nom String que contiene el t�tulo de la pesta�a.
   * @return boolean valor verdadero si ya est� abierto falso en caso contrario.
   */
  public boolean fileIsOpenInTab(String nom) {
    for (int i = 0; i < panelCodigo.getTabCount(); i++) {
      if (nom.compareTo(panelCodigo.getTitleAt(i)) == 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * El m�todo openImageInTab abre un archivo imagen en una nueva
   * pesta�a del explorador de archivos de la aplicaci�n.
   * @param nFile String que contiene el nombre del fichero de la imagen.
   */
  public void openImageInTab(String nFile) {
    if (nFile.compareTo("Thumbs.db") != 0) {
      if (fileIsOpenInTab(nFile) == false) {
        String ruta = "Images" + File.separator + nFile;
        Image imagen = new ImageIcon(MainWindow.class.getResource( "Images" +
            File.separator +nFile)).getImage();

        PanelImage panelImagen = new PanelImage(imagen, ruta);
        panelCodigo.addTab(nFile, iTab, panelImagen, "Imagen");
        this.contPest = this.contPest + 1;
        buttonCloseTab.setEnabled(true);
        panelCodigo.setSelectedComponent(panelImagen);
      }
      else {
        //si ya existe activamos la pesta�a
        for (int i = 0; i < panelCodigo.getTabCount(); i++) {
          if (nFile.compareTo(panelCodigo.getTitleAt(i)) == 0) {
            panelCodigo.setSelectedComponent(panelCodigo.getComponent(i));
          }
        }
      }
    }
  }

  /**
   * El m�todo exportGraphtoImage exporta una imagen del grafo situado
   * en el panel principal a un archivo de imagen png. Lo almacena en la ruta
   * elegida para guardar el proyecto.
   * @param ruta String que contiene la ruta en la que se almacenar� la imagen.
   */
  public void exportGraphtoImage(String ruta) {
    try {
      BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(
          ruta + File.separator + this.nombreProyecto+".png"));
      BufferedImage image = graph.getImage(graph.getBackground(),
                                           10);
      ImageIO.write(image, "png", out);
    }
    catch (Exception e) {
      // handle exception
    }
  }

  /**
   * El m�todo openFileInTab abre un nuevo archivo previamente
   * generado en una nueva pesta�a del panel central.
   * @param  nFile String del fichero a cargar en una nueva pesta�a
   */
  public void openFileInTab(String nFile) {
    String error = "";
    if (this.javaPath != "") {
      JTextPane panelTextoJava = new JTextPane();
      String fileName = this.javaPath + File.separator + nFile;
      if (Controller.validateFile(fileName)) {
        if (fileIsOpenInTab(nFile) == false) {
          try {
            File a = new File(fileName);
            BufferedReader entrada = new BufferedReader(new FileReader(a.
                getCanonicalPath()));
            panelTextoJava.read(entrada, null);
            this.contPest = this.contPest + 1;
            buttonCloseTab.setEnabled(true);

            PanelJavaCode panelScrol = new PanelJavaCode(panelTextoJava);
            panelCodigo.addTab(nFile, iTab, panelScrol, "C�digo Generado");
            panelCodigo.setSelectedComponent(panelScrol);
            //activamos los men�s de guardar y compilar pesta�a
            this.menuCompilarArchivoActual.setEnabled(true);
            this.buttonCompilarPestana.setEnabled(true);
            this.menuGuardarArchivoActual.setEnabled(true);
            this.buttonGuardarJava.setEnabled(true);
          }
          catch (Exception e) {}
        }
        else {
          //si ya existe activamos la pesta�a
          for (int i = 0; i < panelCodigo.getTabCount(); i++) {
            if (nFile.compareTo(panelCodigo.getTitleAt(i)) == 0) {
              panelCodigo.setSelectedComponent(panelCodigo.getComponent(i));
            }
          }
        }
      }
      else {
        error = "Error en la apertura de fichero.";
      }
    }
  }

  /**
   * M�todo que hace que el diagrama no se pueda editar.
   * @param e ActionEvent evento al que responde.
   */
  public void menuSetEditableDiagram(ActionEvent e) {
    if (this.xmiImported || this.xmlLoaded || this.newProyectCreated) {
      if (this.menuFijarDiagrama.getText().compareTo("Fijar diagrama") == 0) {
        this.graph.setEditable(false);
        this.graph.setMoveable(false);
        this.menuFijarDiagrama.setText("Diagrama editable");
      }
      else {
        this.graph.setEditable(true);
        this.graph.setMoveable(true);
        this.menuFijarDiagrama.setText("Fijar diagrama");
      }
    }
    else {
      JOptionPane.showMessageDialog(this,
                                    "Debe de existir un proyecto para poder fijar su diagrama de clases.",
                                    "Aviso",
                                    JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * M�todo que copia una selecci�n de texto
   * @param e ActionEvent evento al que responde
   */
  public void menuCopy(ActionEvent e) {
    PanelJavaCode pscrollCode = (PanelJavaCode) panelCodigo.
        getSelectedComponent();
    if (pscrollCode != null)
      pscrollCode.pCode.copy();
  }

  /**
   * M�todo que corta una selecci�n de texto
   * @param e ActionEvent evento al que responde
   */
  public void menuCut(ActionEvent e) {
    PanelJavaCode pscrollCode = (PanelJavaCode) panelCodigo.
        getSelectedComponent();
    if (pscrollCode != null)
      pscrollCode.pCode.cut();
  }

  /**
   * M�todo que pega una selecci�n de texto, cortada o copiada con anterioridad.
   * @param e ActionEvent evento al que responde
   */
  public void menuPaste(ActionEvent e) {
    PanelJavaCode pscrollCode = (PanelJavaCode) panelCodigo.
        getSelectedComponent();
    if (pscrollCode != null)
      pscrollCode.pCode.paste();
  }

  /**
   * M�todo que inicializa todas las variables del sistema.
   */
  public void resetVariables() {
    pesta�aSeleccionada = "";
    xmiPath = "";
    javaPath = "";
    DesignView = true;
    treeClassesLoaded = false;
    xmiImported = false;
    xmlLoaded = false;
    newProyectCreated = false;
    mainClass = false;
    proyectomodificado = false;
    proyectoGuardado = false;
    menuOculto = false;
    nombreProyecto = "";
    directorioProyecto = "";
    contPest = 0;
    paneljavamodificado = false;
    this.buttonCloseTab.setEnabled(false);

    //borramos el arbol
    dtree.clear();
    //borramos el clascontainer
    Controller.contenido.deleteAllClases();
    Controller.asoc.deleteAllAsoc();
    Controller.gener.deleteAllGen();
    //cerramos todas las pesta�as
    closeAllTabs();
    this.validate();
    reloadGraph();
  }

  /**
   * M�todo que oculta elementos de la ventana de IDE de Java y activa los de la
   * pesta�a de dise�o.
   */
  public void activeDesingView() {

    jToolBarDise�o.add(buttonNuevo, null);
    jToolBarDise�o.add(buttonOpen, null);
    jToolBarDise�o.add(buttonImportar, null);
    jToolBarDise�o.add(buttonRefresh, null);
    jToolBarDise�o.add(buttonSelectMainClass, null);
    jToolBarDise�o.add(buttonPrefe, null);
    jToolBarDise�o.add(buttonGuardar, null);
    jToolBarDise�o.add(buttonGuardarComo, null);
    jToolBarDise�o.add(buttonGenerar, null);
    jToolBarDise�o.add(buttonAyudaDise�o, null);
    this.buttonGuardar.setEnabled(false);
    this.menuGuardar.setEnabled(false);
    this.jToolBarDise�o.setVisible(true);
    panelbuttonCerrar.add(buttonCloseTab, null);

    //habilitamos las opciones propias del dise�o
    this.menuIDEJava.setEnabled(true);
    this.menuRefresh.setEnabled(true);
    this.menuGenerarCodigo.setEnabled(true);
    this.menuNuevo.setEnabled(true);
    this.menuAbrir.setEnabled(true);
    this.menuImportar.setEnabled(true);
    if (guardadocomo)
      this.menuGuardar.setEnabled(true);
    else
      this.menuGuardar.setEnabled(false);

    this.menuGuardarComo.setEnabled(true);
    this.menuClasePrin.setEnabled(true);

    this.jPanelArboles.setVisible(true);
    this.panelCodigo.setVisible(false);
    this.panelbuttonCerrar.setVisible(false);
    this.jPanelDcha.setVisible(false);
    this.jToolBarIDE.setVisible(false);
    this.panelConsola.setVisible(false);
    this.panelDiagrama.setVisible(true);

    //deshabilitamos las opciones del IDE
    this.menuImprimir.setEnabled(false);
    this.menuNuevoJava.setEnabled(false);
    this.menuOpenJava.setEnabled(false);
    this.menuBorrarJava.setEnabled(false);
    this.menuFijarDiagrama.setEnabled(true);
    this.menuGuardarArchivoActual.setEnabled(false);
    this.menuGuardarArchivosJava.setEnabled(false);
    this.menuAbrirCarpeta.setEnabled(false);
    this.menuDise�o.setEnabled(false);
    this.menuCopiar.setEnabled(false);
    this.menuCortar.setEnabled(false);
    this.menuPegar.setEnabled(false);
    this.menuCompilar.setEnabled(false);
    this.menuCompilarArchivoActual.setEnabled(false);
    this.menuJavadoc.setEnabled(false);
    this.menuEjecutar.setEnabled(false);
    menuPregunta.setText("Ayuda Dise�o");
    this.textConsola.setText("");
    if (guardadocomo)
      this.buttonGuardar.setEnabled(true);
    else
      this.buttonGuardar.setEnabled(false);

    //habilitamos las opciones de menu
    this.menuIDEJava.setEnabled(true);
  }

  /**
   * M�todo que oculta elementos de la ventana de Dise�o de Java y activa los
   * de la pesta�a de IDE de Java.
   */
  public void activeIDEView() {
    //Refrescamos el arbol de archivos java
    treeJava.repaint();
    treeJava.updateUI();
    //Expandimos el arbol
    DynamicTree dt = new DynamicTree();
    dt.setTree(treeJava, true);
    this.panelConsola.setVisible(true);
    this.jToolBarDise�o.setVisible(false);
    this.jToolBarIDE.setVisible(true);
    this.panelCodigo.setVisible(true);
    this.jPanelDcha.setVisible(true);
    this.panelbuttonCerrar.setVisible(true);
    this.panelDiagrama.setVisible(false);
    this.jPanelArboles.setVisible(false);

    //habilitamos las opciones del menu de IDE
    this.menuImprimir.setEnabled(true);
    this.menuNuevoJava.setEnabled(true);
    this.menuBorrarJava.setEnabled(true);
    this.menuOpenJava.setEnabled(true);
    this.menuGuardarArchivoActual.setEnabled(true);
    this.menuGuardarArchivosJava.setEnabled(true);
    this.menuDise�o.setEnabled(true);
    this.menuAbrirCarpeta.setEnabled(true);
    this.menuDise�o.setEnabled(true);
    this.menuCopiar.setEnabled(true);
    this.menuCortar.setEnabled(true);
    this.menuPegar.setEnabled(true);
    this.menuCompilar.setEnabled(true);
    this.menuJavadoc.setEnabled(true);
    this.menuEjecutar.setEnabled(true);
    menuPregunta.setText("Ayuda IDE de Java");

    //deshabilitamos las opciones del menu de Dise�o
    this.menuFijarDiagrama.setEnabled(false);
    this.menuCompilarArchivoActual.setEnabled(false);
    this.buttonGuardarJava.setEnabled(false);
    this.menuIDEJava.setEnabled(false);
    this.menuRefresh.setEnabled(false);
    this.menuGenerarCodigo.setEnabled(false);
    this.menuNuevo.setEnabled(false);
    this.menuAbrir.setEnabled(false);
    this.menuImportar.setEnabled(false);
    this.menuGuardar.setEnabled(false);
    this.menuGuardarComo.setEnabled(false);
    this.menuClasePrin.setEnabled(false);
    panelConsola.add(jScroll, new XYConstraints( -1, 0, 737, 108));
    this.menuCompilarArchivoActual.setEnabled(false);
    this.buttonCompilarPestana.setEnabled(false);
    this.menuGuardarArchivoActual.setEnabled(false);
    this.buttonGuardarJava.setEnabled(false);
    this.proyectoCompilado=false;
    this.buttonCloseTab.setEnabled(false);

    System.gc();
  }

  /**
   * El m�todo createNodesTreeClass crea los nodos y subnodos del
   * arbol que contiene las clases del proyecto.
   * @param top es un DefaultMutableTreeNode que representa al nodo ra�z del
   * �rbol.
   */
  private void createNodesTreeClass(DefaultMutableTreeNode top) {

    DefaultMutableTreeNode clase = null;
    DefaultMutableTreeNode miembro = null;

    for (int i = 0; i < Controller.contenido.vClasses.size(); i++) {

      UMLClass tempClass = ( (UMLClass) Controller.contenido.vClasses.get(i));
      String nameC = tempClass.getName();
      clase = new DefaultMutableTreeNode(nameC);
      top.add(clase);
      //A�adimos los atributos
      for (int j = 0; j < tempClass.vAttributes.size(); j++) {
        UMLAttribute tempAtb = (UMLAttribute) tempClass.vAttributes.get(j);
        String nameA = tempAtb.getName();
        miembro = new DefaultMutableTreeNode(nameA);
        clase.add(miembro);
      }
      //A�adimos los m�todos
      for (int j = 0; j < tempClass.vOperations.size(); j++) {
        String par = "(";
        UMLMethod tempOper = (UMLMethod) tempClass.vOperations.get(j);
        for (int k = 0; k < tempOper.parameters.size(); k++) {
          UMLParameter param = (UMLParameter) tempOper.parameters.get(k);
          par += param.getName() + ":" + param.getType();
          if (k + 1 != tempOper.parameters.size()) {
            par += ",";
          }
        }
        par += ")";
        String nameA = tempOper.getName();
        miembro = new DefaultMutableTreeNode(nameA + par);
        clase.add(miembro);
      }
    }
    dtree.tree.setCellRenderer(new MTreeCellRender());
    //Refrescamos el arbol
    dtree.tree.repaint();
    //Expandimos el arbol
    dtree.setTree(dtree.tree, true);
    treeClassesLoaded = true;
  }

  /**
   * El m�todo createNodesTreeFiles crea los nodos del �rbol que
   * contiene los archivos *.java generados.
   * @param top es un DefaultMutableTreeNode que representa al nodo ra�z del
   * �rbol.
   */
  private void createNodesTreeFiles(DefaultMutableTreeNode top) {
    DefaultMutableTreeNode clase = null;
    DefaultMutableTreeNode carpetaModelo = new DefaultMutableTreeNode("Modelo");

    top.add(carpetaModelo);
    try {
      CodeGenerator.copy(new File("." + File.separator + "templates" +
                                  File.separator + "Raiz.java"),
                         new File(this.javaPath + File.separator + "Raiz.java"));
      CodeGenerator.copy(new File("." + File.separator + "templates" +
                                  File.separator + "Gestion.java"),
                         new File(this.javaPath + File.separator +
                                  "Gestion.java"));
    }
    catch (IOException ex) {
    }
    clase = new DefaultMutableTreeNode("Raiz.java");
    carpetaModelo.add(clase);
    clase = new DefaultMutableTreeNode("Gestion.java");
    carpetaModelo.add(clase);
    for (int i = 0; i < Controller.contenido.vClasses.size(); i++) {
      UMLClass tempClass = ( (UMLClass) Controller.contenido.vClasses.get(i));
      String nameC = tempClass.getName() + ".java";
      clase = new DefaultMutableTreeNode(nameC);
      carpetaModelo.add(clase);
    }
    //cambiamos los iconos del �rbol
    treeJava.setCellRenderer(new JavaTreeCellRender());
    //Refrescamos el arbol
    treeJava.repaint();
    //Expandimos el arbol
    DynamicTree dt = new DynamicTree();
    dt.setTree(treeJava, true);
  }

  /**
   * El m�todo removeNodesTreeJava elimina los nodos del �rbol
   * que contiene los archivos java del proyecto actual.
   * @param top es un DefaultMutableTreeNode que representa al nodo ra�z del
   * �rbol.
   */
  private void removeNodesTreeJava(DefaultMutableTreeNode top) {
    top.removeAllChildren();
    treeJava.repaint();
    treeJava.revalidate();
    treeJava.updateUI();
    //Expandimos el arbol
    DynamicTree dt = new DynamicTree();
    dt.setTree(treeJava, true);
  }

  /**
   * M�todo que elimina el archivo seleccionado del conjunto de archivos
   * Java generados que se muestran en el �rbol.
   * @param e ActionEvent evento al que responde.
   */
  public void menuDeleteJava(ActionEvent e) {

    TreePath parentPath = treeJava.getSelectionPath();
    if (parentPath == null) {
      String aviso =
          "No se ha seleccionado ning�n elemento";
      JOptionPane.showMessageDialog(this, aviso, "Error",
                                    JOptionPane.WARNING_MESSAGE);
    }
    else {
      DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
          (treeJava.getSelectionPath().getLastPathComponent());
      MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
      if (parent != null) {
        DefaultTreeModel treeModel = new DefaultTreeModel(topJava);
        String nodoborrado = currentNode.getUserObject().toString();
        String s =
            "�Est� seguro de eliminar el archivo " + nodoborrado + " ?";
        int value = JOptionPane.showConfirmDialog(
            this, s,
            "Mensaje de Confirmaci�n",
            JOptionPane.YES_NO_OPTION);

        if (value == JOptionPane.YES_OPTION) {
          treeModel.removeNodeFromParent(currentNode);
          // System.out.println("Borrando......" + nodoborrado);
          File f = new File(this.javaPath + File.separator + nodoborrado);
          f.delete();
          //cambiamos los iconos del �rbol
          treeJava.setCellRenderer(new JavaTreeCellRender());
          //Refrescamos el arbol
          treeJava.repaint();
          treeJava.updateUI();
          //Expandimos el arbol
          DynamicTree dt = new DynamicTree();
          dt.setTree(treeJava, true);
          this.labelEstado.setText("Se ha borrado el archivo " + nodoborrado);
          //comprobamos si el fichero est� abierto en una pesta�a y lo cerramos
          for (int i = 0; i < panelCodigo.getTabCount(); i++) {
            String nompest = panelCodigo.getTitleAt(i);
            if(nompest.compareTo(nodoborrado)==0){
              panelCodigo.remove(i);
            }
          }
       }
      }

    }
  }

  /**
   * El m�todo treeRemoveNode elimina el nodo selecionado
   * del �rbol de clases del proyecto
   * @param e ActionEvent evento al que responde.
   */
  public void treeRemoveNode(ActionEvent e) {
    if (treeClassesLoaded) {
      TreePath parentPath = dtree.tree.getSelectionPath();
      if (parentPath == null) {
        String aviso =
            "No se ha seleccionado ning�n elemento";
        JOptionPane.showMessageDialog(this, aviso, "Error",
                                      JOptionPane.WARNING_MESSAGE);
      }
      else {
        if ( (parentPath.getPathCount() == 1) &&
            ( (parentPath.getLastPathComponent().toString()).compareTo(
            "Contenedor de clases") == 0)) {
          String aviso2 =
              "No se puede eliminar la ra�z del �rbol";
          JOptionPane.showMessageDialog(this, aviso2, "Error",
                                        JOptionPane.WARNING_MESSAGE);
        }
        else {
          TreePath[] paths = dtree.tree.getSelectionPaths();
          if (paths.length == 1) {
            WindowConfirmationDeleteOne vm;
            vm = new WindowConfirmationDeleteOne(this, "uno");
            vm.setSize(600, 300);
            Dimension dlgSize3 = vm.getPreferredSize();
            Dimension frmSize3 = getSize();
            Point loc3 = getLocation();
            vm.setLocation( (frmSize3.width - dlgSize3.width) / 2 + loc3.x,
                           (frmSize3.height - dlgSize3.height) / 2 + loc3.y);
            vm.setModal(true);
            vm.pack();
            vm.show();
            if (vm.deleteOk) {
              this.proyectomodificado = true;
              //borramos el nodo del �rbol
              dtree.removeCurrentNode();
              dtree.clear();
              createNodesTreeClass(dtree.rootNode);
              reloadGraph();
            }
          }
          else {
            WindowConfirmationDeleteOne vm;
            vm = new WindowConfirmationDeleteOne(this, "varios");
            vm.setSize(600, 300);
            Dimension dlgSize3 = vm.getPreferredSize();
            Dimension frmSize3 = getSize();
            Point loc3 = getLocation();
            vm.setLocation( (frmSize3.width - dlgSize3.width) / 2 + loc3.x,
                           (frmSize3.height - dlgSize3.height) / 2 + loc3.y);
            vm.setModal(true);
            vm.pack();
            vm.show();
            if (vm.deleteOk) {
              this.proyectomodificado = true;
              for (int i = 0; i < paths.length; i++) {
                if ( (paths[i].getPathCount()) == 3) {
                  TreeNode raiz = (TreeNode) dtree.tree.getModel().getRoot();
                  dtree.removeNode(paths[i], raiz);
                }
              }
              reloadGraph();
            }
          }
        }
      }
    }
    else {
      String aviso =
          "No se ha creado ning�n proyecto sobre el que trabajar.";
      JOptionPane.showMessageDialog(this, aviso, "Error",
                                    JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * M�todo que comprueba si el string que se le pasa como par�metro es
   * el nombre de una clase del sistema.
   * @param nom String nombre de la clase.
   * @return boolean valor true si ya es el nombre de una clase, falso en caso
   * contrario.
   */
  public static boolean typeIsClass(String nom) {
    boolean toret = false;
    for (int i = 0; i < Controller.contenido.vClasses.size(); i++) {
      UMLClass classco = ( (UMLClass) Controller.contenido.vClasses.get(i));
      if ( ( (String) classco.getName()).compareTo(nom) == 0) {
        toret = true;
      }
    }
    return toret;
  }

  /**
   * El m�todo treeAdd a�ade un nuevo nodo al �rbol de clases
   * del proyecto creado,este nuevo nodo puede ser una clase, un m�todo o un
   * atributo de una clase.
   * @param e ActionEvent evento al que responde.
   */
  public void treeAdd(ActionEvent e) {
    if (treeClassesLoaded) {
      //Comprobamos si el nivel esta bien
      TreePath parentPath = dtree.tree.getSelectionPath();
      //EL nivel tiene que ser o 1= clase o 2=atb/met
      if ( (parentPath == null) || parentPath.getPathCount() == 1) { //es clase
        //a�adimos una clase gen�rica
        Vector v = new Vector();
        v.add("nomTemp");
        v.add("public");
        v.add("false");
        Controller.contenido.addClass(v);
        WindowModifyClass vent;
        vent = new WindowModifyClass(this, "nomTemp", "A�adir clase");
        v.set(0, "");
        vent.fillFields(v);
        vent.setSize(600, 300);
        Dimension dlgSize = vent.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        vent.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                         (frmSize.height - dlgSize.height) / 2 + loc.y);
        vent.setModal(true);
        vent.pack();
        vent.show();

        if (vent.botonModificarActivo && vent.completRecord()) {
          //A�adimos la clase al contenedor de clases y al �rbol
          Controller.contenido.modifyClass("nomTemp", vent.vClase);
          vent.vClase.toString();
          Controller.addTypes();
          Controller.addTypesWithVoid();
          if (vent.vClase.size() > 0) {
            dtree.addObject( (String) vent.vClase.get(0));
            this.proyectomodificado = true;
          }
          Controller.contenido.printAllClasses();
          reloadGraph();
        }
        else{//si al final el usuario decide cancelar
          Controller.contenido.deleteMember("nomTemp");
        }
      }
      else if (parentPath.getPathCount() == 2) { //es atb o m�todo
        //Mostramos la ventana seleccionadora
        WindowMemberSelector v;
        v = new WindowMemberSelector(this);
        v.setSize(600, 300);
        Dimension dlgSize = v.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                      (frmSize.height - dlgSize.height) / 2 + loc.y);
        v.setModal(true);
        v.pack();
        v.show();

        if (v.botonSiguientePulsado) {
          //Si es un atributo
          if (v.radioAtributo.isSelected()) {

            Vector vat = new Vector();
            vat.add("nomAtbTemp");
            vat.add("int");
            vat.add("public");
            vat.add("false");
            vat.add("");
            String padre = (String) parentPath.getLastPathComponent().toString();

            Controller.contenido.addAtb(vat, padre);

            WindowModifyAttribute vent;
            vent = new WindowModifyAttribute(this, "nomAtbTemp",
                                             "A�adir atributo");
            vent.nombreClase = padre;
            //cargamos los datos en pantalla
            vat.set(0, "");
            vent.fillFields(vat);
            vent.setSize(600, 300);
            Dimension dlgSize2 = v.getPreferredSize();
            Dimension frmSize2 = getSize();
            Point loc2 = getLocation();
            vent.setLocation( (frmSize2.width - dlgSize2.width) / 2 + loc2.x,
                             (frmSize2.height - dlgSize2.height) / 2 + loc2.y);
            vent.setModal(true);
            vent.pack();
            vent.show();

            if (vent.botonModificarActivo && vent.completRecord()) {
              //Modificamos la clase en el Controlador
              Controller.contenido.modifyAttribute("nomAtbTemp", padre,
                  vent.vAtb);

              boolean esClase = this.typeIsClass( (String) vent.vAtb.get(1));
              if (esClase) {
                //A�adimos una asociaci�n 1 a 1
                Controller.asoc.addAsoc1a1( (String) parentPath.
                                           getLastPathComponent().
                                           toString(), (String) vent.vAtb.get(1));
              }
              else {
                if ( ( (String) vent.vAtb.get(1)).compareTo("Vector") == 0) {
                  esClase = this.typeIsClass( (String) vent.vAtb.get(4));
                  if (esClase) {
                    Controller.asoc.addAsoc1an( (String) parentPath.
                                               getLastPathComponent().
                                               toString(),
                                               (String) vent.vAtb.get(4));
                  }
                }
              }

              this.proyectomodificado = true;
              if (vent.vAtb.size() > 0) {
                dtree.addObject( (String) vent.vAtb.get(0));
              }
              Controller.contenido.printAllClasses();
            }
            else {//si al final el usuario le da a cancelar.
              Controller.contenido.deleteMember("nomTemp");
            }

          }
          else {
            //Si es un m�todo
            if (v.radioMetodo.isSelected()) {
              WindowAddMethod vm;
              vm = new WindowAddMethod(this);
              vm.setSize(600, 300);
              Dimension dlgSize3 = vm.getPreferredSize();
              Dimension frmSize3 = getSize();
              Point loc3 = getLocation();
              vm.setLocation( (frmSize3.width - dlgSize3.width) / 2 + loc3.x,
                             (frmSize3.height - dlgSize3.height) / 2 + loc3.y);
              vm.setModal(true);
              vm.pack();
              vm.show();
              if (vm.botonAceptarActivo && vm.completRecord()) {
                //A�adimos el metodo a la clase y al �rbol
                System.out.println(vm.vMethod.toString());
                Controller.contenido.addMethod(vm.vMethod, vm.vPar,
                                               (String) parentPath.
                                               getLastPathComponent().
                                               toString());
                this.proyectomodificado = true;
                if (vm.vMethod.size() == 5 || vm.vMethod.size() == 6) {
                  if (vm.vMethod.size() == 6) { //tiene par�metros
                    //cont=numero de  parametros
                    int cont = Integer.parseInt(vm.vMethod.get(5).toString());
                    //  System.out.println("num parametros" + cont);
                    String nombremet = (String) vm.vMethod.get(0) + "(";
                    for (int i = 0; i < cont; i++) {

                      nombremet += ( (UMLParameter) vm.vPar.get(i)).getName().
                          toString();
                      nombremet += ":" +
                          ( (UMLParameter) vm.vPar.get(i)).getType().
                          toString();
                      if (i + 1 != cont) {
                        nombremet += ",";
                      }
                    }
                    dtree.addObject(nombremet + ")");
                  }
                  else {
                    dtree.addObject( (String) vm.vMethod.get(0) + "( )");
                  }
                }
                Controller.contenido.printAllClasses();
                Controller.addTypes();
              }
            }
          }
        }
      }
      else {
        String error =
            "No se puede a�adir un elemento que no sea un atributo o una clase.";
        JOptionPane.showMessageDialog(this, error, "Aviso",
                                      JOptionPane.WARNING_MESSAGE);
      }
    }
    else {
      //No se pueden a�adir nodos a un �rbol que no existe
      String aviso =
          "No se ha creado ning�n proyecto sobre el que trabajar.";
      JOptionPane.showMessageDialog(this, aviso, "Aviso",
                                    JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * El m�todo treeModifyNode modifica el nodo selecionado
   * del �rbol de clases del proyecto
   * @param e ActionEvent evento al que responde.
   */
  public void treeModifyNode(ActionEvent e) {
    modifyNode();
    Controller.addTypes();
  }

  /**
   * El m�todo modifyNode cambia el nodo seleccionado.
   */
  public void modifyNode() {
    if (treeClassesLoaded) {
      TreePath parentPath = dtree.tree.getSelectionPath();
      if (parentPath == null) {
        String aviso =
            "No se ha seleccionado ning�n elemento";
        JOptionPane.showMessageDialog(this, aviso, "Error",
                                      JOptionPane.WARNING_MESSAGE);
      }
      else {
        // Get paths of all selected nodes
        TreePath[] paths = dtree.tree.getSelectionPaths();
        if (paths.length == 1) {
          //Comprobamos si el nivel esta bien
          TreePath currentSelection = dtree.tree.getSelectionPath();
          if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                (currentSelection.getLastPathComponent());

            if ( (currentSelection == null) ||
                currentSelection.getPathCount() == 2) { //es clase
              String nombreant = currentNode.getUserObject().toString();
              //Buscamos la clase a modificar
              Vector vClase = new Vector();

              //Cargamos el contenido de la clase a modificar/consultar en un vector
              vClase = Controller.contenido.consultClass(nombreant);
              WindowModifyClass vent;
              vent = new WindowModifyClass(this, nombreant,
                                           "Modificar datos de una clase");
              //cargamos los datos en pantalla
              vent.fillFields(vClase);
              vent.setSize(600, 300);
              Dimension dlgSize = vent.getPreferredSize();
              Dimension frmSize = getSize();
              Point loc = getLocation();
              vent.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                               (frmSize.height - dlgSize.height) / 2 + loc.y);
              vent.setModal(true);
              vent.pack();
              vent.show();

              if (vent.botonModificarActivo && vent.completRecord()) {
                //Modificamos la clase en el Controlador
                Controller.contenido.modifyClass(nombreant, vent.vClase);
                //Modificamos el nombre para todas las referencias a esa clase
                Controller.modifyClassContainer(nombreant,
                                                (String) vent.vClase.get(0));
                //Modificamos el nombre de la clase de la asociaci�n.
                Controller.modifyAsociationTable(nombreant,
                                                 vent.fieldClassName.getText());
                //Modificamos el nombre en la generalizationTable
                Controller.modifyGeneralizationTable(nombreant,vent.fieldClassName.getText());
                this.proyectomodificado = true;
                //Si el nombre de la clase cambia modificamos el �rbol
                if (vent.vClase.get(0) != "" && vent.vClase.get(0) != nombreant) {
                  dtree.modifyObject( (String) vent.vClase.get(0));
                }
                Controller.contenido.printAllClasses();
                reloadGraph();
              }
            }
            else if (currentSelection.getPathCount() == 3) { //es atb
              String nombreant = currentNode.getUserObject().toString();
              if (!Controller.isMethod( (String) nombreant)) {

                //Buscamos la clase a modificar
                String padre = currentNode.getParent().toString();
                Vector vAtributo = new Vector();

                //Cargamos el contenido del atributo a modificar/consultar en un vector
                vAtributo = Controller.contenido.consultAttribute(nombreant,
                    padre);

                WindowModifyAttribute vent;
                vent = new WindowModifyAttribute(this, nombreant,
                                                 "Modificar atributo");
                vent.nombreClase = padre;
                //cargamos los datos en pantalla
                vent.fillFields(vAtributo);
                vent.setSize(600, 300);
                Dimension dlgSize = vent.getPreferredSize();
                Dimension frmSize = getSize();
                Point loc = getLocation();
                vent.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                                 (frmSize.height - dlgSize.height) / 2 + loc.y);
                vent.setModal(true);
                vent.pack();
                vent.show();

                if (vent.botonModificarActivo && vent.completRecord()) {
                  //Modificamos la clase en el Controlador
                  Controller.contenido.modifyAttribute(nombreant, padre,
                      vent.vAtb);
                  /*si cambia el tipo de los objetos y antes era un vector o
                   un objeto y ahora no, hay que eliminar las asociaciones*/
                  Controller.delAssoc(padre, vAtributo, vent.vAtb);

                  this.proyectomodificado = true;
                  //Si el nombre de la clase cambia modificamos el �rbol
                  if (vent.vAtb.get(0) != "" && vent.vAtb.get(0) != nombreant) {
                    dtree.modifyObject( (String) vent.vAtb.get(0));
                  }
                  Controller.contenido.printAllClasses();
                }
              }
              else { // es m�todo
                //Buscamos la clase a modificar
                String padre = currentNode.getParent().toString();
                Vector vOperation = new Vector();
                //le quitamos los par�metros y nos quedamos solo con el nombre del m�todo
                nombreant = Controller.getOnlyName(nombreant);
                //Cargamos el contenido de la clase a modificar/consultar en un vector
                vOperation = Controller.contenido.consultOperation(nombreant,
                    padre);

                WindowModifyMethod vent;
                vent = new WindowModifyMethod(this);
                //cargamos los datos en pantalla
                vent.fillFields(vOperation);
                //guardamos los parametros en un vector por si los queremos modificar
                vent.vparametros = Controller.contenido.consultParameters(
                    nombreant,
                    padre);
                vent.setSize(600, 300);
                Dimension dlgSize = vent.getPreferredSize();
                Dimension frmSize = getSize();
                Point loc = getLocation();
                vent.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                                 (frmSize.height - dlgSize.height) / 2 + loc.y);
                vent.setModal(true);
                vent.pack();
                vent.show();
                if (vent.botonModificarActivo && vent.completRecord()) {
                  String operNuevo = "";
                  if (vent.vparametros.size() > 0) {
                    for (int k = 0; k < vent.vparametros.size(); k++) {
                      UMLParameter param = (UMLParameter) vent.vparametros.get(
                          k);
                      operNuevo += param.getName() + ":" + param.getType();
                      if (k + 1 != vent.vparametros.size()) {
                        operNuevo += ",";
                      }
                    }
                  }

                  //Modificamos el m�todo en el Controlador
                  Controller.contenido.modifyOperation(nombreant, padre,
                      vent.vOper, vent.vparametros);
                  this.proyectomodificado = true;
                  //Si el nombre del m�todo, o sus parametros cambia lo modificamos en el �rbol
                  if (vent.vOper.get(0) != "" && vent.vOper.get(0) != nombreant) {
                    dtree.modifyObject( (String) vent.vOper.get(0) + "(" +
                                       operNuevo + ")");
                  }
                  Controller.contenido.printAllClasses();
                }
              }
            }
          }
          //Refrescamos el arbol
          dtree.tree.repaint();
          //Expandimos el arbol
          dtree.setTreeState(dtree.tree, currentSelection, false);
          dtree.setTreeState(dtree.tree, currentSelection, true);
          dtree.treeModel.reload();
          dtree.setTree(dtree.tree, true);
        }
        else {
          String aviso =
              "Para esta acci�n no se permite selecci�n m�ltiple de elementos";
          JOptionPane.showMessageDialog(this, aviso, "Error",
                                        JOptionPane.WARNING_MESSAGE);
        }
      }
    }
    else {
      String aviso =
          "No se ha creado ning�n proyecto sobre el que trabajar.";
      JOptionPane.showMessageDialog(this, aviso, "Error",
                                    JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * El m�todo treeRemoveAll elimina todos los nodos
   * del �rbol de clases del proyecto
   * @param e ActionEvent evento al que responde.
   */
  public void treeRemoveAll(ActionEvent e) {
    if (treeClassesLoaded) {
      WindowConfirmationDeleteAll vm;
      vm = new WindowConfirmationDeleteAll(this);
      vm.tipoBorrarTodo = true;
      vm.setSize(600, 300);
      Dimension dlgSize3 = vm.getPreferredSize();
      Dimension frmSize3 = getSize();
      Point loc3 = getLocation();
      vm.setLocation( (frmSize3.width - dlgSize3.width) / 2 + loc3.x,
                     (frmSize3.height - dlgSize3.height) / 2 + loc3.y);
      vm.setModal(true);
      vm.pack();
      vm.show();

      if (vm.deleteAll) {
        //borramos todo el �rbol
        dtree.clear();
        //borramos el clascontainer y la associationTable
        Controller.contenido.deleteAllClases();
        this.proyectomodificado = true;
        Controller.asoc.deleteAllAsoc();
        reloadGraph();
      }
    }
    else {
      String aviso =
          "No se ha creado ning�n proyecto sobre el que trabajar.";
      JOptionPane.showMessageDialog(this, aviso, "Error",
                                    JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Combrueba si alguno de los tabs est� modificado.
   * @return boolean true si alguno de los tabs se ha modificado false en caso
   * contrario.
   */
  public boolean tabsModified() {
    /*no todos los tabs se pueden modificar, hacemos la comprobaci�n si el
         nombre del tab acaba en java*/
    for (int i = 0; i < this.panelCodigo.getTabCount(); i++) {
      String nompest = panelCodigo.getTitleAt(i);
      String extension = "";
      int j = 0;
      while (j < nompest.length() && (nompest.charAt(j) != '.'))
        j++;

      if (nompest.charAt(j) == '.')
        extension = nompest.substring(j, nompest.length());

      if (extension.compareTo(".java") == 0) {
        if ( ( (PanelJavaCode) panelCodigo.getComponent(i)).javamodificado == true) {
          return true;
        }
      }
    }
    return false;
  }

  //M�TODOS que se ejecutan cuando el usuario pulsa un bot�n
  /**
   * El m�todo menuExit se ejecuta al llamar al men�
   * Archivo-Salir del Proyecto.Sale por completo de la aplicaci�n.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuExit(ActionEvent e) {
    //si estamos en la vista de dise�o y se ha modificado algo
    if (this.DesignView && !this.proyectomodificado)
      System.exit(0);
    else {
      if (this.DesignView && this.proyectomodificado) {

        String s =
            "�Desea guardar los cambios realizados, antes de salir?";
        int value = JOptionPane.showConfirmDialog(
            this, s,
            "Mensaje de Confirmaci�n",
            JOptionPane.YES_NO_OPTION);

        if (value == JOptionPane.YES_OPTION) {
          if (!proyectoGuardado)
            this.menuSaveAs(e);
          else
            this.menuSave(e);
          System.exit(0);
        }
        else
          System.exit(0);
      }
      else {
        if (!this.DesignView) {
          if (panelCodigo.getTabCount() > 0) {

            if (tabsModified()) {
              String s =
                  "�Desea guardar los cambios realizados en las pesta�as abiertas, antes de salir?";
              int value = JOptionPane.showConfirmDialog(
                  this, s,
                  "Mensaje de Confirmaci�n",
                  JOptionPane.YES_NO_OPTION);

              if (value == JOptionPane.YES_OPTION) {
                this.menuSaveAllJava(e);
                System.exit(0);
              }
              else
                System.exit(0);
            }
          }
          else
            System.exit(0);
        }
      }
    }
  }

  /**
   * M�todo que a�ade un nuevo archivo en blanco al conjunto de archivos
   * generados.
   * @param e ActionEvent evento al que responde.
   */
  public void menuNewJava(ActionEvent e) {

    if (this.javaPath != "") {
      WindowNewFile v = new WindowNewFile(this);
      v.setSize(600, 300);
      Dimension dlgSize = v.getPreferredSize();
      Dimension frmSize = getSize();
      Point loc = getLocation();
      v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                    (frmSize.height - dlgSize.height) / 2 + loc.y);
      v.setModal(true);
      v.pack();
      v.show();

      if (v.botonOk && v.datosCompletados) {
        String nuevo = v.fieldName.getText();
        String error = Controller.newJavaFile(this.javaPath, nuevo);

        if (error != "")
          JOptionPane.showMessageDialog(this, error, "Mensaje de error",
                                        JOptionPane.WARNING_MESSAGE);
        else {
          DefaultMutableTreeNode nodoArchivoNuevo =
              new DefaultMutableTreeNode(v.fieldName.getText() +
                                         ".java");
          if (v.modelo)
            topJava.getFirstLeaf().getPreviousNode().add(nodoArchivoNuevo);
          else
            topJava.getNextNode().getNextSibling().add(nodoArchivoNuevo);

          openFileInTab(v.fieldName.getText() + ".java");

          //cambiamos los iconos del �rbol
          treeJava.setCellRenderer(new JavaTreeCellRender());
          //Refrescamos el arbol
          treeJava.repaint();
          treeJava.updateUI();
          DynamicTree dt = new DynamicTree();
          dt.setTree(treeJava, true);
        }
      }
    }
    else {
      String error =
          "Antes de a�adir un nuevo archivo debe de generar los archivos de c�digo *.java";
      JOptionPane.showMessageDialog(this, error, "Aviso",
                                    JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * M�todo que muestra la ayuda tanto en la parte de dise�o como en la del
   * IDE de Java.
   * @param e ActionEvent
   */
  public void menuHelp(ActionEvent e) {
    WindowHelp v;
    if (DesignView == true)
      v = new WindowHelp(this,
                         "." + File.separator + "help" + File.separator + "Ayuda_Dise�o.html");
    else
      v = new WindowHelp(this,
                         "." + File.separator + "help" + File.separator + "Ayuda_IDE.html");

    v.setSize(600, 300);
    Dimension dlgSize = v.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                  (frmSize.height - dlgSize.height) / 2 + loc.y);
    v.setModal(true);
    v.pack();
    v.show();
  }

  /**
   * M�todo que realiza la importaci�n de un archivo  XMI para que la herramienta
   * lo lea.
   * @param e ActionEvent evento al que responde.
   */
  public void menuImport(ActionEvent e) {
    if (!this.xmiImported && !this.xmlLoaded && !this.newProyectCreated)
      openXMIFile();
    else {
      String s =
          "En estos momentos ya ha sido cargado un proyecto, \n�Desea eliminarlo y cargar uno nuevo?";
      int value = JOptionPane.showConfirmDialog(
          this, s,
          "Mensaje de Confirmaci�n",
          JOptionPane.YES_NO_OPTION);

      if (value == JOptionPane.YES_OPTION) {
        this.resetVariables();
        openXMIFile();
      }
      this.menuFijarDiagrama.setEnabled(true);
    }
  }

  /**
   * M�todo que vuelve a pintar el diagrama de clases.
   */
  public void reloadGraph() {
    Vector vClases = new Vector();
    String s = "";
    //Almacenamos los nombres de las clases del ClassContainer en un vector
    for (int j = 0; j < Controller.contenido.vClasses.size(); j++) {
      s = ( (UMLClass) (Controller.contenido.vClasses.get(j))).getName().
          toString();
      vClases.add(s);
    }

    Color colorRect = Color.decode("#99CCFF");
    Dimension defaultSize = new Dimension(735, 599);
    model = new DefaultGraphModel();
    view = new GraphLayoutCache(model,
                                new DefaultCellViewFactory());

    graph = new JGraph(model, view);
    graph.setEditable(false);
    graph.setPreferredSize(defaultSize);
    DefaultGraphCell[] cells = new DefaultGraphCell[vClases.size() +
        Controller.asoc.vAssoc.size() + Controller.gener.vGen.size()];

    //Dibujo de las clases
    int x = 30;
    int y = 30;
    //r y z son dos n�meros aleatorios
    int z = 0;
    int r = 0;
    int contador = 0;
    for (int i = 0; i < vClases.size(); i++) {
      cells[i] = new DefaultGraphCell(new String(vClases.get(i).toString()));
      GraphConstants.setBounds(cells[i].getAttributes(),
                               new Rectangle2D.Double(x, y, 80, 50));
      GraphConstants.setGradientColor(cells[i].getAttributes(), colorRect);
      GraphConstants.setOpaque(cells[i].getAttributes(), true);
      GraphConstants.setIcon(cells[i].getAttributes(), iClase);
      GraphConstants.setBorderColor(cells[i].getAttributes(), Color.black);
      DefaultPort port = new DefaultPort();
      cells[i].add(port);
      contador = contador + 1;
      z = (int) (Math.random() * 300);
      r = (int) (Math.random() * 300);
      y = y + z;
      x = z + r;
      while ( (x > 900) || (y > 500)) {
        y = 0;
        x = 0;
        z = (int) (Math.random() * 200);
        r = (int) (Math.random() * 200);
        y = y + z;
        x = z + r;
      }
    }

    //Dibujo de las asociaciones
    for (int k = 0; k < Controller.asoc.vAssoc.size(); k++) {
      DefaultEdge edge = new DefaultEdge();
      UMLAssociation aso = ( (UMLAssociation) Controller.asoc.vAssoc.get(k));

      //Recorremos los DefaultGraphCell creados para el origen
      for (int i = 0; i < contador; i++) {
        DefaultMutableTreeNode n = (DefaultMutableTreeNode) cells[i];
        if (n.toString() != null) {
          if ( (aso.vBeginEnd.get(0)).toString().compareTo(n.toString()) == 0) {
            edge.setSource(cells[i].getChildAt(0));
          }
        }
      }
      //Recorremos los DefaultGraphCell creados para el destino
      for (int i = 0; i < contador; i++) {
        DefaultMutableTreeNode n = (DefaultMutableTreeNode) cells[i];
        if (n.toString() != null) {
          if ( (aso.vBeginEnd.get(1)).toString().compareTo(n.toString()) == 0) {
            edge.setTarget(cells[i].getChildAt(0));
          }
        }
      }

      //A�adimos multiplicidad a las aristas
      String ori = "";
      String des = "";
      if ( ( ( (String) (aso.multiplicity.get(0))).compareTo("1") == 0) &&
          ( ( (String) (aso.multiplicity.get(1))).compareTo("1") == 0)) {
        ori = new String("1");
        des = new String("1");
      }
      else if ( ( ( (String) (aso.multiplicity.get(0))).compareTo("1") == 0) &&
               ( ( (String) (aso.multiplicity.get(1))).compareTo("-1") == 0)) {
        ori = new String("1");
        des = new String("0..n");
      }

      Object[] labels = {
          des, ori};
      Point2D[] labelPositions = {
          new Point2D.Double(GraphConstants.PERMILLE * 7 / 8, -20),
          new Point2D.Double(GraphConstants.PERMILLE / 8, -20)};

      GraphConstants.setExtraLabelPositions(edge.getAttributes(),
                                            labelPositions);
      GraphConstants.setExtraLabels(edge.getAttributes(), labels);
      cells[contador] = edge;
      contador = contador + 1;
      int arrow = GraphConstants.ARROW_CLASSIC;
      GraphConstants.setLineEnd(edge.getAttributes(), arrow);
      GraphConstants.setEndFill(edge.getAttributes(), true);
    }

    //Dibujo de las relaciones de herencia
    for (int k = 0; k < Controller.gener.vGen.size(); k++) {
      DefaultEdge edge = new DefaultEdge();
      UMLGeneralization gen = ( (UMLGeneralization) Controller.gener.vGen.get(k));

      //Recorremos los DefaultGraphCell creados para el origen
      for (int i = 0; i < contador; i++) {
        DefaultMutableTreeNode n = (DefaultMutableTreeNode) cells[i];
        if (n.toString() != null) {
          if ( (gen.vBeginEnd.get(0)).toString().compareTo(n.toString()) == 0) {
            edge.setSource(cells[i].getChildAt(0));
          }
        }
      }
      //Recorremos los DefaultGraphCell creados para el destino
      for (int i = 0; i < contador; i++) {
        DefaultMutableTreeNode n = (DefaultMutableTreeNode) cells[i];
        if (n.toString() != null) {
          if ( (gen.vBeginEnd.get(1)).toString().compareTo(n.toString()) == 0) {
            edge.setTarget(cells[i].getChildAt(0));
          }
        }
      }

      Point2D[] labelPositions = {
          new Point2D.Double(GraphConstants.PERMILLE * 7 / 8, -20),
          new Point2D.Double(GraphConstants.PERMILLE / 8, -20)};

      GraphConstants.setExtraLabelPositions(edge.getAttributes(),
                                            labelPositions);
      cells[contador] = edge;
      contador = contador + 1;
      int arrow = GraphConstants.ARROW_TECHNICAL;
      GraphConstants.setLineEnd(edge.getAttributes(), arrow);
      GraphConstants.setEndFill(edge.getAttributes(), false);
    }

    //Editamos los atributos del Graph
    graph.getGraphLayoutCache().insert(cells);
    graph.setDisconnectable(false);
    graph.setEdgeLabelsMovable(false);
    graph.setSizeable(false);
    this.menuFijarDiagrama.setText("Fijar diagrama");
    this.panelDiagrama.removeAll();
    this.panelDiagrama.add(new JScrollPane(graph));
    this.panelDiagrama.revalidate();
  }

  /**
   * M�todo que abre la ventana en la se selecciona la clase principal.
   */
  public void selectMainClass() {
    if (Controller.contenido.vClasses.size() == 0) {
      JOptionPane.showMessageDialog(
          this, "En estos momentos no hay ninguna clase en el sistema,\n antes de continuar debe de crear el modelo del proyecto.",
          "Mensaje de Error",
          JOptionPane.WARNING_MESSAGE);
    }
    else {
      WindowSelectMainClass v;
      v = new WindowSelectMainClass(this);
      v.setSize(600, 300);
      Dimension dlgSize = v.getPreferredSize();
      Dimension frmSize = getSize();
      Point loc = getLocation();
      v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                    (frmSize.height - dlgSize.height) / 2 + loc.y);
      v.setModal(true);
      v.pack();
      v.show();
      if (v.botonOkPulsado) {
        this.mainClass = true;
        this.labelEstado.setText(
            "Se ha marcado con �xito la clase principal: " +
            Controller.mainClass);
      }
    }

  }

  /**
   * M�todo que abre la ventana que permite elegir la clase principal del
   * sistema.
   * @param e ActionEvent evento al que responde.
   */
  public void menuMainClass(ActionEvent e) {

    if (this.xmiImported || this.xmlLoaded || this.newProyectCreated)
      selectMainClass();

    else {
      JOptionPane.showMessageDialog(
          this, "En estos momentos no hay ning�n proyecto abierto,\n debe de abrir o crear un nuevo proyecto para seleccionar su clase Principal ",
          "Mensaje de Error",
          JOptionPane.WARNING_MESSAGE);
    }

  }

  /**
   * M�todo que abre la ventana desde la que se pueden editar las opciones de
   * la aplicaci�n.
   * @param e ActionEvent evento al que responde.
   */
  public void menuOptions(ActionEvent e) {
    WindowPreferences v;
    v = new WindowPreferences(this);
    v.setSize(600, 300);
    Dimension dlgSize = v.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                  (frmSize.height - dlgSize.height) / 2 + loc.y);
    v.setModal(true);
    v.pack();
    v.show();
    if(v.botonOk)
      this.labelEstado.setText("Se han almacenado correctamente los directorios por defecto.");
  }

  /**
   * El m�todo menuOpenJava muestra una ventana que contiene un formulario para
   * introducir los datos de la ruta del fichero que deseamos abrir.
   * @param e ActionEvent evento al que responde.
   */
  public void menuOpenJava(ActionEvent e) {

    WindowOpenJavaFile v;
    v = new WindowOpenJavaFile(this);
    v.setSize(600, 300);
    Dimension dlgSize = v.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                  (frmSize.height - dlgSize.height) / 2 + loc.y);
    v.setModal(true);
    v.pack();
    v.show();
    if (v.datosAccesibles && v.botonFinalizarPulsado) {
      File src = new File(v.fieldName.getText());
      String nameFile = src.getName();
      File dst = new File(this.javaPath + File.separator +
                          nameFile);

      try {
        if (!dst.exists()) {
          dst.createNewFile();
          CodeGenerator.copy(src, dst);
          DefaultMutableTreeNode nodoArchivoNuevo = new DefaultMutableTreeNode(
              nameFile);
          if (v.modelo)
            topJava.getFirstLeaf().getPreviousNode().add(nodoArchivoNuevo);
          else
            topJava.getNextNode().getNextSibling().add(nodoArchivoNuevo);

          openFileInTab(nameFile);
          //cambiamos los iconos del �rbol
          treeJava.setCellRenderer(new JavaTreeCellRender());
          //Refrescamos el arbol
          treeJava.repaint();
          treeJava.updateUI();
          DynamicTree dt = new DynamicTree();
          dt.setTree(treeJava, true);
        }
        else {
          JOptionPane.showMessageDialog(
              this, "Ya existe un fichero con el mismo nombre en el proyecto.",
              "Mensaje de Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * El m�todo openXMIFile muestra una ventana que contiene un
   * formulario para introducir los datos de la ruta del fichero que deseamos
   * abrir.
   */
  public void openXMIFile() {

    WindowOpenXMIFile archivoVent;
    archivoVent = new WindowOpenXMIFile(this);
    archivoVent.setSize(600, 300);
    Dimension dlgSize = archivoVent.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    archivoVent.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                            (frmSize.height - dlgSize.height) / 2 + loc.y);
    archivoVent.setModal(true);
    archivoVent.pack();
    archivoVent.show();

    if (archivoVent.datosAccesibles && archivoVent.botonFinalizarPulsado) {
      if (xmiImported || xmlLoaded || newProyectCreated) {
        //borramos el arbol
        dtree.clear();
        //borramos el clascontainer
        Controller.contenido.deleteAllClases();
        //cerramos todas las pesta�as
        closeAllTabs();
        this.validate();
      }
      int pos = archivoVent.nombreXMI.indexOf(".");
      if (pos != -1)
        this.nombreProyecto = archivoVent.nombreXMI.substring(0, pos);
      else
        this.nombreProyecto = archivoVent.nombreXMI;

      // File archivo = new File(archivoVent.DirTrabajoCasilla.getText());
      this.xmiPath = archivoVent.fieldDirXMI.getText();

      //Cargamos los datos en el ClassContainer
      try {
        Controller.loadData();
      }
      catch (IOException ex1) {
      }
      createNodesTreeClass(dtree.rootNode);
      Controller.addTypes();
      reloadGraph();
      this.xmiImported = true;
      this.buttonGuardar.setEnabled(false);
      this.menuGuardar.setEnabled(false);

    }
    panelTexto.revalidate();
  }

  /**
   * M�todo que crea un nuevo proyecto en blanco sobre el que trabajar.
   */
  public void newProyect() {

    WindowChooseSaveFolder v;
    v = new WindowChooseSaveFolder(this, "Nuevo Proyecto...", true);
    v.setSize(600, 300);
    Dimension dlgSize = v.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                  (frmSize.height - dlgSize.height) / 2 + loc.y);
    v.setModal(true);
    v.pack();
    v.show();

    if (v.datosCompletados && v.botonAceptarPulsado) {
      Controller.contenido = new ClassContainer();
      Controller.asoc = new AssociationTable();
      reloadGraph();
      createNodesTreeClass(dtree.rootNode);
      this.newProyectCreated = true;

      this.nombreProyecto = v.fieldName.getText();
      this.directorioProyecto = v.fieldDirectorio.getText();

      Controller.contenido.save(this.directorioProyecto, this.nombreProyecto);
      String s =
          "Se han creado con �xito el proyecto en el directorio: " +
          this.directorioProyecto;
      this.buttonGuardar.setEnabled(true);
      this.menuGuardar.setEnabled(true);
      Controller.defaultSavePath = this.directorioProyecto;
      this.labelEstado.setText(s);
    }
    this.buttonGuardar.setEnabled(true);
  }

  /**
   * El m�todo openZipFile muestra una ventana que contiene un
   * formulario para introducir los datos de la ruta del fichero que deseamos
   * abrir.
   */
  public void openZipFile() {
    File temp = new File("." + File.separator + "temp");
    WindowOpenZipFile archivoVent;
    archivoVent = new WindowOpenZipFile(this);
    archivoVent.setSize(600, 300);
    Dimension dlgSize = archivoVent.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    archivoVent.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                            (frmSize.height - dlgSize.height) / 2 + loc.y);
    archivoVent.setModal(true);
    archivoVent.pack();
    archivoVent.show();

    if (archivoVent.datosAccesibles && archivoVent.botonFinalizarPulsado) {
      if (xmiImported || xmlLoaded || newProyectCreated) {
        //borramos el arbol
        dtree.clear();
        //borramos el clascontainer
        Controller.contenido.deleteAllClases();
        this.validate();
      }
      else {
        Controller.contenido = new ClassContainer();
      }

      File f= new File(archivoVent.rutaProyecto);
      this.nombreProyecto = archivoVent.nombreRaiz;
      this.directorioProyecto=  f.getParentFile().getAbsolutePath();
      this.newProyectCreated= true;
      this.buttonGuardar.setEnabled(true);
      this.menuGuardar.setEnabled(true);


      if (!temp.exists())
        temp.mkdir();

      String rutaTemporal = "." + File.separator + "temp" + File.separator +
          archivoVent.nombreRaiz +
          ".xml";
      /*leemos el contenido del xml y lo cargamos en el classcontainer
       y lo mostramos en los dos �rboles*/
      Vector vficherosXML = new Vector();
      vficherosXML = Controller.readBasicXML(rutaTemporal);
      String nomFich = "";
      for (int i = 0; i < vficherosXML.size(); i++) {
        UMLClass nueva = new UMLClass();
        nomFich = vficherosXML.get(i).toString();
        System.out.println("Cargando datos de:...." + nomFich);
        nueva = Controller.readCompletXML("." + File.separator + "temp" +
                                          File.separator + nomFich);
        Controller.contenido.vClasses.add(nueva);
      }
      createNodesTreeClass(dtree.rootNode);
      /*antes de cargar el diagrama debemos de rellenar el contenido de la
             AssociationTable*/
      Controller.createAssociationTable(Controller.contenido);
      Controller.addTypes();
      Controller.addTypesWithVoid();
      Controller.asoc.printAssociations();
      reloadGraph();
      this.xmlLoaded = true;
    }

    //borramos los ficheros descomprimidos
   if (temp.isDirectory()) {
     File[] ficheros = temp.listFiles();
     for (int i = 0; i < ficheros.length; i++) {
       // System.out.println("borrando..." + ( (File) ficheros[i]).getName());
       ( (File) ficheros[i]).delete();
     }
   }

  }

  /**
   * M�todo que comprueba si ya exite un proyecto activo y carga uno nuevo
   * vac�o.
   * @param e ActionEvent evento al que responde.
   */
  public void menuNewProject(ActionEvent e) {
    if (this.xmiImported || this.xmlLoaded || this.newProyectCreated) {
      String s =
          "En estos momentos ya ha sido cargado un proyecto, \n�Desea eliminarlo y crear uno nuevo?";
      int value = JOptionPane.showConfirmDialog(
          this, s,
          "Mensaje de Confirmaci�n",
          JOptionPane.YES_NO_OPTION);

      if (value == JOptionPane.YES_OPTION) {
        this.resetVariables();
        newProyect();
        this.menuFijarDiagrama.setEnabled(true);
      }
      else if (value == JOptionPane.NO_OPTION) {
        System.out.println("Has elegido no");
      }
    }
    else {
      newProyect();
      this.menuFijarDiagrama.setEnabled(true);
    }
  }

  /**
   * El m�todo menuOpenProject se ejecuta al llamar al men�
   * Archivo-Abrir Proyecto. Carga el contenido de un proyecto ya creado
   * desde la herramienta.Carga el contenido en el �rbol de clases y tambi�n
   * en el arbol contenedor de archivos *.xml
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuOpenProject(ActionEvent e) {
    if (this.xmiImported || this.xmlLoaded || this.newProyectCreated) {
      String s =
          "En estos momentos ya ha sido cargado un proyecto, \n�Desea eliminarlo y cargar uno nuevo?";
      int value = JOptionPane.showConfirmDialog(
          this, s,
          "Mensaje de Confirmaci�n",
          JOptionPane.YES_NO_OPTION);

      if (value == JOptionPane.YES_OPTION) {
        this.resetVariables();
        this.openZipFile();
        this.menuFijarDiagrama.setEnabled(true);
      }
    }
    else {
      openZipFile();
      this.menuFijarDiagrama.setEnabled(true);
    }

  }

  /**
   * M�todo que comprueba si existe c�digo relativo a ese proyecto en un
   * directorio.
   * @param cc ClassContainer Contenedor de clases.
   * @param ruta String pathn en el que se busca si existe el archivo.
   * @return boolean true si existe c�digo false en caso contrario.
   */
  public boolean existsCode(ClassContainer cc, String ruta) {
    File fGestion = new File(ruta + File.separator, "Gestion.java");
    File fRaiz = new File(ruta + File.separator, "Raiz.java");
    File fcargarVentanaPrincipal = new File(ruta + File.separator,
                                            "CargarVentanaPrincipal.java");
    File fVentanaPrincipal = new File(ruta + File.separator,
                                      "VentanaPrincipal.java");
    File fVentanaAgregar = new File(ruta + File.separator,
                                    "VentanaAgregar.java");
    File fVentanaGestion = new File(ruta + File.separator,
                                    "VentanaGestion.java");
    File fVentanaModificar = new File(ruta + File.separator,
                                      "VentanaModificar.java");
    if (!fGestion.exists() || !fRaiz.exists() ||
        !fcargarVentanaPrincipal.exists()
        || !fVentanaPrincipal.exists() || !fVentanaAgregar.exists() ||
        !fVentanaGestion.exists()
        || !fVentanaModificar.exists()) {
      return false;
    }
    for (int i = 0; i < cc.vClasses.size(); i++) {
      String nomClase = ( (UMLClass) cc.vClasses.get(i)).getName();
      File fich = new File(ruta + File.separator + nomClase + ".java");
      if (!fich.exists()) {
        return false;
      }
    }
    return true;
  }

  /**
   * M�todo que pregunta si se desea guardar el proyecto antes de generar el c�digo.
   */
  public void saveGen() {
    String s =
        "Antes de pasar a la vista 'IDE de Java'\n �Desea guardar el proyecto actual?";
    int value = JOptionPane.showConfirmDialog(
        this, s,
        "Mensaje de Confirmaci�n",
        JOptionPane.YES_NO_OPTION);

    if (value == JOptionPane.YES_OPTION) {
      if (this.proyectoGuardado)
        save();
      else
        openWindowSaveAs();
    }
  }

  /**
   * El m�todo menuGenerat se ejecuta al llamar al men�
   * Herramientas - Generar C�digo. Genera el c�digo relativo a las clases
   * del proyecto.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuGenerate(ActionEvent e) {
    String error =
        "Debe de cargar primero el proyecto, antes de generar el c�digo";
    String aviso =
        "Antes de generar el c�digo debe de marcar la clase principal del sistema.";
    if (!xmiImported && !xmlLoaded && !newProyectCreated) {
      JOptionPane.showMessageDialog(this, error, "Aviso",
                                    JOptionPane.WARNING_MESSAGE);
    }
    else {
      if (this.mainClass == false) {
        JOptionPane.showMessageDialog(this, aviso, "Error",
                                      JOptionPane.WARNING_MESSAGE);
        selectMainClass();
        saveGen();
      }
      else
        saveGen();
      openChooseDirectory();
    }
    //llamamos al recolector de basura
    System.gc();
  }

  /**
   * M�todo que abre la ventana en la que se selecciona el directorio en el que
   * se almacenar�n los archivos generados.
   */
  public void openChooseDirectory() {
    View.WindowChooseDirectory vent;
    vent = new WindowChooseDirectory(this, this.nombreProyecto);
    vent.setSize(600, 300);
    Dimension dlgSize = vent.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    vent.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                     (frmSize.height - dlgSize.height) / 2 + loc.y);
    vent.setModal(true);
    vent.pack();
    vent.show();

    if (vent.datosAccesibles && vent.botonFinalizarPulsado) {
      this.javaPath = vent.DirTrabajoCasilla.getText()+File.separator;

      if (existsCode(Controller.contenido, this.javaPath)) {
        String s1 =
            "Ya existe una versi�n de c�digo java que ha sido creada previamente \n por la aplicaci�n." +
            " �Desea generar el c�digo del proyecto nuevamente? ";

        int val = JOptionPane.showConfirmDialog(
            this, s1,
            "Mensaje de Confirmaci�n",
            JOptionPane.YES_NO_OPTION);

        if (val == JOptionPane.YES_OPTION) {
          Controller.sort();
          File fborrar = new File(this.javaPath);
          File[] ficheros = fborrar.listFiles();
          for (int x = 0; x < ficheros.length; x++)
            ficheros[x].delete();

          File fcrear = new File(this.javaPath);
          try {
            fcrear.createNewFile();
          }
          catch (IOException ex) {
          }
          createNodesTreeFiles(topJava);
          genInterface();
          activeIDEView();
          this.DesignView = false;
        }
      }
      else { //no existe alguno de los ficheros, o ninguno ->borramos todos
        File fborrar = new File(this.javaPath);
        File[] ficheros = fborrar.listFiles();
        for (int x = 0; x < ficheros.length; x++)
          ficheros[x].delete();
        File fcrear = new File(this.javaPath);
        try {
          fcrear.createNewFile();
        }
        catch (IOException ex) {
        }
        createNodesTreeFiles(topJava);
        genInterface();
        activeIDEView();
        this.DesignView = false;
      }
    }
  }

  /**
   * El m�todo menuPrint se ejecuta al llamar al men�
   * imprimir Archivo, y se encarga de imprimir el contenido de una
   * de las pesta�as del panel central.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuPrint(ActionEvent e) {

    if (panelCodigo.getTabCount() > 0) {
      JTextPane t = new JTextPane();
      int pest = panelCodigo.getSelectedIndex();
      String nompest = panelCodigo.getTitleAt(pest);

      File a = new File(javaPath, nompest);
      BufferedReader entrada = null;
      try {
        entrada = new BufferedReader(new FileReader(a.
            getCanonicalPath()));
        t.read(entrada, null);
      }
      catch (FileNotFoundException ex) {
      }
      catch (IOException ex) {
      }
      Controller.printFile(t);
    }
    else {
      String s = "Debe de abrir un fichero a imprimir";
      JOptionPane.showMessageDialog(this, s, "Aviso",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * El m�todo menuRefresh se ejecuta al seleccionar el
   * bot�n de refrescar el contenido del �rbol de clases.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuRefresh(ActionEvent e) {
    String error =
        "Debe de crear o importar un nuevo proyecto, antes de refrescar el diagrama y el �rbol.";
    if (!xmiImported && !xmlLoaded && !newProyectCreated) {
      JOptionPane.showMessageDialog(this, error, "Error",
                                    JOptionPane.WARNING_MESSAGE);
    }
    else {
      //Refrescamos el arbol
      dtree.tree.repaint();
      //Expandimos el arbol
      dtree.setTree(dtree.tree, true);
      reloadGraph();
    }
  }

  /**
   * Guarda los cambios del proyecto.
   */
  public void save() {
    String nomArchivo = this.nombreProyecto;
    String dir = this.directorioProyecto;
    File f = new File(this.directorioProyecto + File.separator +
                      this.nombreProyecto + ".zip");
    if (f.exists())
      f.delete();

    Controller.contenido.save(dir, nomArchivo);
    String s =
        "Se ha guardado correctamente el proyecto en el directorio: " +
        dir + File.separator + nomArchivo;
    this.labelEstado.setText(s);
    this.proyectomodificado = false;
    this.exportGraphtoImage(dir);
  }

  /**
   * El m�todo menuSave se ejecuta al llamar al men�
   * Archivo-Guardar. Salva el proyecto credo en el directorio en el que
   * previamente se ha guardado mediante el men� Archivo-Guardar como
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuSave(ActionEvent e) {
    if (this.newProyectCreated || this.proyectoGuardado) {
      save();
    }
  }

  /**
   * M�todo que abre una nueva ventana Guardar Como
   */
  public void openWindowSaveAs() {

    WindowChooseSaveFolder v;
    v = new WindowChooseSaveFolder(this, "Guardar Como...", false);
    v.setSize(600, 300);
    Dimension dlgSize = v.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                  (frmSize.height - dlgSize.height) / 2 + loc.y);
    v.setModal(true);
    v.pack();
    v.show();

    if (v.datosCompletados && v.botonAceptarPulsado) {
      String nomArchivo = v.fieldName.getText();
      String dir = v.fieldDirectorio.getText();
      this.directorioProyecto = dir;
      this.nombreProyecto = nomArchivo;
      Controller.contenido.save(dir, nomArchivo);
      String s =
          "Se ha guardado correctamente el proyecto en el directorio:" +
          dir;
      this.labelEstado.setText(s);
      this.proyectomodificado = false;
      this.proyectoGuardado = true;
      this.buttonGuardar.setEnabled(true);
      this.menuGuardar.setEnabled(true);
      this.exportGraphtoImage(dir);
      guardadocomo = true;
    }
  }

  /**
   * El m�todo menuSaveAs se ejecuta al llamar al men�
   * Archivo-Guardar Como. Guarda el proyecto en formato XML, en una ruta por
   * defecto, o en una ruta seleccionada por el usuario.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuSaveAs(ActionEvent e) {
    if (this.xmiImported || this.xmlLoaded || this.newProyectCreated) {
      openWindowSaveAs();
    }
    else {
      String s = "No existe un proyecto que guardar";
      JOptionPane.showMessageDialog(this, s, "Aviso", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * El m�todo menuJavaDoc se ejecuta al llamar al men�
   * "Generar documentaci�n javadoc". Se encarga de la generaci�n documental
   * de las clases de la aplicaci�n generada.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuJavaDoc(ActionEvent e) {
    if (this.proyectoCompilado) {
      this.textConsola.setText(
          "Generando documentaci�n javadoc ....\nEste proceso puede tardar unos segundos.");
      String entrada = "file:" + File.separator + File.separator +
          this.javaPath +
          File.separator + "doc" + File.separator + "index.html";
      abrirURL(entrada);
    }
    else {
      JOptionPane.showMessageDialog(this,
          "Debe de compilar el proyecto antes de generar la documentaci�n.",
                                    "Error",
                                    JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * El m�todo menuCompile se ejecuta al llamar al men�
   * compilar.Se encarga de compilar el c�digo generado previamente.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuCompile(ActionEvent e) {

    String nombreSO = System.getProperty("os.name");


      if (nombreSO.startsWith("Mac OS")||(nombreSO.startsWith("Windows"))) {
        try {

          Runtime obj = Runtime.getRuntime();
          File javaP = new File(this.javaPath);

          String comando = "javac " + javaP.getCanonicalPath() + File.separator +
              "*.java";

          Process p = obj.exec(comando);
          InputStream stderr = p.getErrorStream();
          InputStreamReader isr = new InputStreamReader(stderr);
          BufferedReader br = new BufferedReader(isr);
          String line = null;

          File f = new File("salida.txt");
          BufferedWriter bw = new BufferedWriter(new FileWriter(f));
          bw.write("Compilando el proyecto....\n");
          while ( (line = br.readLine()) != null) {
            bw.write(line + "\n");
          }
          bw.write("Proyecto compilado.");
          bw.close();
          BufferedReader bre = new BufferedReader(new FileReader(f.
              getCanonicalPath()));
          this.textConsola.read(bre, null);
          bre.close();
          f.delete();
          this.proyectoCompilado = true;
          //generamos la documentaci�n javadoc
          Runtime obj1 = Runtime.getRuntime();
          String comando1 = "javadoc -d " + javaP.getAbsolutePath() +
              File.separator +
              "doc -private -version -author " + javaP.getAbsolutePath() +
              File.separator + "*.java";
          obj1.exec(comando1);
        }
        catch (IOException ex) {
        }
      }else
      {
        JOptionPane.showMessageDialog(this, "Debe de compilar el proyecto desde el terminal de linux", "Error",
                                 JOptionPane.WARNING_MESSAGE);

      }
  }

  /**
   * El m�todo menuExecute se ejecuta al llamar al men�
   * ejecutar proyeto.Se encarga de ejecutar el c�digo compilado previamente.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuExecute(ActionEvent e) {
    if (proyectoCompilado) {
      try {
        this.textConsola.setText("");
        Runtime obj = Runtime.getRuntime();
        File javaP = new File(this.javaPath);
        String comando = "java -cp " + javaP.getCanonicalPath() +
            File.separator +
            " VentanaPrincipal";

        Process p = obj.exec(comando);
        /*   InputStream stderr = p.getErrorStream();
           InputStreamReader isr = new InputStreamReader(stderr);
           BufferedReader br = new BufferedReader(isr);
           String line = null;

         File f = new File("salida.txt");
           BufferedWriter bw = new BufferedWriter(new FileWriter(f));
           bw.write(comando + "\n");
           bw.write("Ejecutando el proyecto....\n");
           while ( (line = br.readLine()) != null) {
             bw.write(line + "\n");
           }

           bw.close();
           BufferedReader bre = new BufferedReader(new FileReader(f.
               getCanonicalPath()));
           this.textConsola.read(bre, null);
           bre.close();
           f.delete();*/
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }
      System.gc();
    }
    else{
      JOptionPane.showMessageDialog(this, "Debe de compilar el proyecto antes de su ejecuci�n", "Error",
                                    JOptionPane.WARNING_MESSAGE);
    }
  }

  /**
   * Comprueba si el archivo abierto y activo del panel de pesta�as es un
   * fichero java o es una imagen.
   * @return boolean true si es un fichero de c�digo java.
   */
  public boolean isJavaFile() {
    String extension = "";
    int pest = panelCodigo.getSelectedIndex();
    String nompest = panelCodigo.getTitleAt(pest);
    int i = 0;
    while (i < nompest.length() && (nompest.charAt(i) != '.'))
      i++;
    if (nompest.charAt(i) == '.')
      extension = nompest.substring(i, nompest.length());
    if (extension.compareTo(".java") == 0)
      return true;
    else
      return false;
  }

  /**
   * Oculta el menu principal.
   * @param e ActionEvent evento al que responde.
   */
  public void menuHide(ActionEvent e) {
    if (this.menuOculto) {
      menuSuperior.setVisible(true);
      this.menuOculto = false;
    }
    else {
      menuSuperior.setVisible(false);
      this.menuOculto = true;
    }
  }

  /**
   * El m�todo menuSaveAllJava se ejecuta al llamar al men�
   * guardar todo.Se encarga guardar todos los .java editados y abiertos.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuSaveAllJava(ActionEvent e) {
    if (panelCodigo.getTabCount() > 0) {
      String extension = "";
      for (int i = 0; i < panelCodigo.getTabCount(); i++) {
        String nompest = panelCodigo.getTitleAt(i);

        int k = 0;
        while (k < nompest.length() && (nompest.charAt(k) != '.'))
          k++;
        if (nompest.charAt(k) == '.')
          extension = nompest.substring(k, nompest.length());
        if (extension.compareTo(".java") == 0) {
          File f = new File(javaPath, panelCodigo.getTitleAt(i));
          // String rutaCompleta = getJavaPath(panelCodigo.getTitleAt(i));
          if (isJavaFile()) {
            if (f.exists() && (f.isFile())) {
              f.delete();
            }
            File fnuevo = new File(javaPath, panelCodigo.getTitleAt(i));
            BufferedWriter salida = null;
            try {
              salida = new BufferedWriter(new FileWriter(fnuevo.
                  getCanonicalPath()));
              panelCodigo.setSelectedComponent(panelCodigo.getComponent(i));
              PanelJavaCode pscrollCode = (PanelJavaCode) panelCodigo.
                  getSelectedComponent();
              pscrollCode.javamodificado = false;
              salida.write(pscrollCode.pCode.getText());
              salida.close();
            }
            catch (FileNotFoundException ex) {
              ex.printStackTrace();
            }
            catch (IOException ex) {
              ex.printStackTrace();
            }
          }
        }
      }
      this.labelEstado.setText("Se han guardado con �xito todos los archivos");
    }
    else
      this.labelEstado.setText("No hay nig�n archivo que guardar.");
  }

  /**
   * El m�todo saveTab se encarga guardar el archivo .java de la pesta�a seleccionada.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void saveTab() {
    int pest = panelCodigo.getSelectedIndex();
    String nompest = panelCodigo.getTitleAt(pest);
    ( (PanelJavaCode) panelCodigo.getComponent(pest)).javamodificado = false;
    File f = new File(javaPath, nompest);
    //si el fichero existe -> que siempre deber�a de existir
    if (f.exists() && (f.isFile())) {
      f.delete();
    }
    File fnuevo = new File(javaPath, nompest);
    BufferedWriter salida = null;
    try {
      salida = new BufferedWriter(new FileWriter(fnuevo.
                                                 getCanonicalPath()));
      PanelJavaCode pscrollCode = (PanelJavaCode) panelCodigo.
          getSelectedComponent();
      salida.write(pscrollCode.pCode.getText());
      salida.close();
    }
    catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
    this.labelEstado.setText("Se ha guardado con �xito el archivo " +
                             nompest);
  }

  /**
   * El m�todo menuSaveJavaTab se ejecuta al llamar al men�
   * guardar .Se encarga guardar el archivo .java de la pesta�a seleccionada.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuSaveJavaTab(ActionEvent e) {
    if (panelCodigo.getTabCount() > 0) {
      saveTab();
    }
    else
      this.labelEstado.setText("No hay nig�n archivo que guardar.");
  }

  /**
   * El m�todo menuCompileTab se ejecuta al llamar al men�
   * compilar.Se encarga de compilar el c�digo de la pesta�a seleccionada.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuCompileTab(ActionEvent e) {
    if (panelCodigo.getTabCount() > 0) {
      saveTab();
      int pest = panelCodigo.getSelectedIndex();
      String nompest = panelCodigo.getTitleAt(pest);
      try {
        Runtime obj = Runtime.getRuntime();
        File javaP = new File(this.javaPath);
        String comando = "javac -classpath " + javaP.getCanonicalPath() +
            File.separator + " " + this.javaPath + File.separator + nompest;

        Process p = obj.exec(comando);
        InputStream stderr = p.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line = null;

        File fi = new File("salida.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(fi));
        bw.write("Compilando la pesta�a actual....\n");
        while ( (line = br.readLine()) != null) {
          bw.write(line + "\n");
        }
        bw.write("archivo " + nompest + ".java compilado.");

        bw.close();
        BufferedReader bre = new BufferedReader(new FileReader(fi.
            getCanonicalPath()));
        this.textConsola.read(bre, null);
        bre.close();
        fi.delete();
      }
      catch (IOException ex) {
      }
    }
    else
      this.labelEstado.setText("No hay nig�n archivo abierto que compilar.");
  }

  /**
   * Abre una direcci�n en el explorador
   * @param url String nombre de la url
   */
  public static void abrirURL(String url) {

    String mensajeError = "Error al intentar lanzar el navegador web";

    String nombreSO = System.getProperty("os.name");

    try {
      if (nombreSO.startsWith("Mac OS")) {
        Class manager = Class.forName("com.apple.eio.FileManager");
        Method openURL = manager.getDeclaredMethod("openURL",
            new Class[] {String.class});
        openURL.invoke(null, new Object[] {url});
      }
      if (nombreSO.startsWith("Windows"))
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
      else {
        //se asume  Unix or Linux
        String[] navegadores = {
            "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape","chromium-browser"};
        String navegador = null;
        for (int contador = 0; contador < navegadores.length && navegador == null;
             contador++) {
          if (Runtime.getRuntime().exec(new String[] {"which",
                                        navegadores[contador]}).waitFor() == 0)
            navegador = navegadores[contador];
        }
        if (navegador == null)throw new Exception(
            "No se encuentra navegador web");
        else
          Runtime.getRuntime().exec(new String[] {navegador, url});
      }
    }
    catch (Exception e) {
      JOptionPane.showMessageDialog(null,
                                    mensajeError + ":\n" + e.getLocalizedMessage());
    }
  }

  /**
   * El m�todo menuOpenFolder se ejecuta al llamar al men�
   * Abrir directorio.Abre el directorio contenedor de los archivos *.java
   * generados.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuOpenFolder(ActionEvent e) {

    abrirURL("file:" + File.separator + File.separator + this.javaPath +
             File.separator);
  }

  /**
   * El m�todo menuAbout se ejecuta al llamar al men�
   * About.Muestra informaci�n a cerca de la aplicaci�n.
   * @param e ActionEvent al que responde el m�todo.
   */
  public void menuAbout(ActionEvent e) {
    View.WindowAbout vent;
    vent = new WindowAbout(this);
    vent.setSize(600, 300);
    Dimension dlgSize = vent.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    vent.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                     (frmSize.height - dlgSize.height) / 2 + loc.y);
    vent.setModal(true);
    vent.pack();
    vent.show();
  }

  /**
   * Se encarga de crear las clases relativas a la interfaz de la aplicaci�n
   * generada.
   */
  public void genInterface() {
    try {
      CodeGenerator gc = new CodeGenerator(Controller.contenido,
                                           this.javaPath);
      gc.generateGUI();
      //a�adimos al arbol los java generados.
      if (topJava.getNextNode().getNextSibling() == null) {
        DefaultMutableTreeNode carpetaVista = new DefaultMutableTreeNode(
            "Vista");
        topJava.add(carpetaVista);
        DefaultMutableTreeNode nodoCargar = new DefaultMutableTreeNode(
            "CargarVentanaPrincipal.java");
        carpetaVista.add(nodoCargar);
        DefaultMutableTreeNode nodoElemento = new DefaultMutableTreeNode(
            "Elemento.java");
        carpetaVista.add(nodoElemento);
        DefaultMutableTreeNode nodoAgregar = new DefaultMutableTreeNode(
            "VentanaAgregar.java");
        carpetaVista.add(nodoAgregar);
        DefaultMutableTreeNode nodoGestion = new DefaultMutableTreeNode(
            "VentanaGestion.java");
        carpetaVista.add(nodoGestion);
        DefaultMutableTreeNode nodoPrincipal = new DefaultMutableTreeNode(
            "VentanaPrincipal.java");
        carpetaVista.add(nodoPrincipal);
        DefaultMutableTreeNode nodoModificar = new DefaultMutableTreeNode(
            "VentanaModificar.java");
        carpetaVista.add(nodoModificar);

        /*  File dirImagenes = new File(this.javaPath + File.separator + "Imagenes" +
                                      File.separator);
          if (dirImagenes.isDirectory()) {
            String[] children = dirImagenes.list();
         DefaultMutableTreeNode carpetaImagenes = new DefaultMutableTreeNode(
                "Imagenes");
            topJava.add(carpetaImagenes);
            for (int i = 0; i < children.length; i++) {
         DefaultMutableTreeNode archgenerado = new DefaultMutableTreeNode( (
                  String) children[i]);
              carpetaImagenes.add(archgenerado);
            }
          }
               }
               else {
          DefaultMutableTreeNode carpetaVista = topJava.getNextNode().
              getNextSibling();

          DefaultMutableTreeNode nodoCargar = new DefaultMutableTreeNode(
              "CargarVentanaPrincipal.java");
          carpetaVista.add(nodoCargar);
          DefaultMutableTreeNode nodoPrincipal = new DefaultMutableTreeNode(
              "VentanaPrincipal.java");
          carpetaVista.add(nodoPrincipal);
          DefaultMutableTreeNode nodoElemento = new DefaultMutableTreeNode(
              "Elemento.java");
          carpetaVista.add(nodoElemento);
          DefaultMutableTreeNode nodoAgregar = new DefaultMutableTreeNode(
              "VentanaAgregar.java");
          carpetaVista.add(nodoAgregar);
          DefaultMutableTreeNode nodoGestion = new DefaultMutableTreeNode(
              "VentanaGestion.java");
          carpetaVista.add(nodoGestion);
          DefaultMutableTreeNode nodoModificar = new DefaultMutableTreeNode(
              "VentanaModificar.java");
          carpetaVista.add(nodoModificar);

         File dirImagenes = new File(this.javaPath + File.separator + "Imagenes" +
                                      File.separator);
          if (dirImagenes.isDirectory()) {
            String[] children = dirImagenes.list();
         DefaultMutableTreeNode carpetaImagenes = new DefaultMutableTreeNode(
                "Imagenes");
            topJava.add(carpetaImagenes);
            for (int i = 0; i < children.length; i++) {
         DefaultMutableTreeNode archgenerado = new DefaultMutableTreeNode( (
                  String) children[i]);
              carpetaImagenes.add(archgenerado);
            }
          }
        }*/
      }
    }
    catch (IOException ex) {
    }
    //cambiamos los iconos del �rbol
    treeJava.setCellRenderer(new JavaTreeCellRender());
    //Refrescamos el arbol
    treeJava.repaint();
    treeJava.updateUI();
    DynamicTree dt = new DynamicTree();
    dt.setTree(treeJava, true);
  }

  /**
   * M�todo que permite al usuario volver a la vista de dise�o para continuar
   * con las modificaciones en el dise�o de la aplicaci�n.
   * @param e ActionEvent evento al que responde.
   */
  public void menuChangeView(ActionEvent e) {
    String s =
        "�Desea volver a la vista de 'Dise�o'?\n Si pulsa la opci�n S�, " +
        "volver� al proyecto previo a la generaci�n de c�digo.";

    int value = JOptionPane.showConfirmDialog(
        this, s,
        "Mensaje de Confirmaci�n",
        JOptionPane.YES_NO_OPTION);

    if (value == JOptionPane.YES_OPTION) {
      this.closeAllTabs();
      removeNodesTreeJava(this.topJava);
      this.activeDesingView();
      if (panelCodigo.getTabCount() == 1)
        panelCodigo.remove(0);
    }
  }

  /**
   * El m�todo closeAllTabs cierra todas las pesta�as del panel
   * central que esten abiertas.
   */
  public void closeAllTabs() {
    panelCodigo.removeAll();
    //desactivamos los men�s de guardar y compilar pesta�a
    this.menuCompilarArchivoActual.setEnabled(false);
    this.buttonCompilarPestana.setEnabled(false);
    this.menuGuardarArchivoActual.setEnabled(false);
    this.buttonGuardarJava.setEnabled(false);
  }

  /**
   * El m�todo closeTab cierra la pesta�a seleccionada
   */
  public void closeOneTab() {
    int i = panelCodigo.getSelectedIndex();
    if (i != -1) {
      this.contPest = this.contPest - 1;
      //borramos la pesta�a
      panelCodigo.remove(i);
      if (this.contPest == 0) {
        buttonCloseTab.setEnabled(false);
      }
    }
  }

  /**
   * El m�todo closeTab se ejecuta al hacer clic en el
   * bot�n cerrar pesta�a. Cierra la pesta�a seleccionada.
   * @param e ActionEvent evento al que responde el m�todo.
   */
  public void closeTab(ActionEvent e) {

    String extension = "";
   if(panelCodigo.getTabCount()>0){
     int pest = panelCodigo.getSelectedIndex();
     String nompest = panelCodigo.getTitleAt(pest);
     int i = 0;
     while (i < nompest.length() && (nompest.charAt(i) != '.'))
       i++;

     if (nompest.charAt(i) == '.')
       extension = nompest.substring(i, nompest.length());

     if (extension.compareTo(".java") == 0) {
       if ( ( (PanelJavaCode) panelCodigo.getComponent(pest)).javamodificado == true) {
         String s =
             "�Desea guardar los cambios realizados, antes de salir?";
         int value = JOptionPane.showConfirmDialog(
             this, s,
             "Mensaje de Confirmaci�n",
             JOptionPane.YES_NO_OPTION);
         if (value == JOptionPane.YES_OPTION)
           this.menuSaveJavaTab(e);
       }
     }
     closeOneTab();
     if (this.panelCodigo.getTabCount() == 0) {
       //desactivamos los men�s de guardar y compilar pesta�a
       this.menuCompilarArchivoActual.setEnabled(false);
       this.buttonCompilarPestana.setEnabled(false);
       this.menuGuardarArchivoActual.setEnabled(false);
       this.buttonGuardarJava.setEnabled(false);
     }
   }
  }

  /**
   * El m�todo processWindowEvent se ejecuta al hacer clic en el
   * bot�n de salir de la ventana actual.
   * @param e WindowEvent al que responde el m�todo.
   */
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      menuExit(null);
    }
  }

}

//*******CLASES MANEJADORAS DE EVENTOS: implementan una interfaz oyente
 /**
  * Clase que implementa las acciones del men� Archivo-Importar XMI
  * @author  Digna Rodr�guez
  * @version  1.0
  */
class Main_menuImport_ActionAdapter
    implements ActionListener {
  MainWindow vent;

  Main_menuImport_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuImport(e);
  }
}

/**
 * Clase que implementa las acciones del men� Archivo-Abrir Archivo
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuOpen_ActionAdapter
    implements ActionListener {
  MainWindow vent;

  Main_menuOpen_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuOpenProject(e);
  }
}

/**
 * Clase que implementa las acciones del men� Archivo-Abrir Archivo
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuNew_ActionAdapter
    implements ActionListener {
  MainWindow vent;

  Main_menuNew_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuNewProject(e);
  }
}

/**
 * Clase que implementa las acciones del men� Archivo-Salir Archivo
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuExit_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuExit_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuExit(e);
  }
}

/**
 * Clase que implementa las acciones del men� imprimir archivo
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuPrint_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuPrint_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuPrint(e);
  }
}

/**
 * Clase que implementa las acciones del men� Refrescar el �rbol que contiene
 * todas las clases.
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuRefresh_ActionAdapter
    implements ActionListener {
  MainWindow vent;

  Main_menuRefresh_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuRefresh(e);
  }
}

/**
 * Clase que implementa las acciones del men� Archivo-Guardar Archivo
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuSave_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuSave_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuSave(e);
  }
}

/**
 * Clase que implementa las acciones del men� Archivo-Guardar Como Archivo
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuSaveAs_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuSaveAs_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuSaveAs(e);
  }
}

/**
 * Clase que implementa las acciones del men� Herramientas-Generar C�digo
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuGenerate_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuGenerate_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuGenerate(e);
  }
}

/**
 * Clase que implementa las acciones del men� Abrir Directorio
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuOpenFolder_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuOpenFolder_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuOpenFolder(e);
  }
}

/**
 * Clase que implementa las acciones del menu Guardar Archivo Java actual
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuSaveJava_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuSaveJava_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuSaveJavaTab(e);
  }
}

/**
 * Clase que implementa las acciones del menu Guardar Archivo Java actual
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuSaveAllJava_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuSaveAllJava_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuSaveAllJava(e);
  }
}

/**
 * Clase que implementa las acciones del menu Compilar
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuCompile_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuCompile_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuCompile(e);
  }
}

/**
 * Clase que implementa las acciones del menu Ejecutar proyecto
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuExec_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuExec_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuExecute(e);
  }
}

/**
 * Clase que implementa las acciones del menu Compilar una sola pesta�a
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuCompileJava_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuCompileJava_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuCompileTab(e);
  }
}

/**
 * Clase que implementa las acciones del menu ocultar menu principal
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuHide_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuHide_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuHide(e);
  }
}

/**
 * Clase que implementa las acciones del menu Acerca de...
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuAbout_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuAbout_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuAbout(e);
  }
}

/************Clases que Modifican el �rbol contenedor de clases***********/
/**
 * Clase que implementa el evento de a�adir un nodo al DynamicTree
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_treeAdd_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_treeAdd_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.treeAdd(e);
  }
}

/**
 * Clase que implementa el evento de eliminar uno o varios nodos
 * del DynamicTree
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_treeRemoveNode_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_treeRemoveNode_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.treeRemoveNode(e);
  }
}

/**
 * Clase que implementa el evento de eliminar todos los nodos
 * del DynamicTree
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_treeRemoveAll_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_treeRemoveAll_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.treeRemoveAll(e);
  }
}

/**
 * Clase que implementa el evento de modificar un nodo del DynamicTree
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_treeModifyNode_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_treeModifyNode_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.treeModifyNode(e);
  }
}

/**
 * Clase qeu implementa la acci�n de cambiar a la vista de dise�o
 * @author Digna Rodr�guez
 * @version 1.0
 */
class Main_menuChangeToDesingView_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuChangeToDesingView_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuChangeView(e);
  }

}

/**
 * Clase que implementa el evento de eliminar una pesta�a del panel contenedor
 * de texto.
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_closeTab_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_closeTab_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.closeTab(e);
  }

}

/**
 * Clase que implementa las acciones del menu-Ayuda
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuHelp_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuHelp_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuHelp(e);
  }
}

/**
 * Clase que implementa las acciones del menu-Archivo-Borrar Archivo
 * seleccionado.
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuDeleteJava_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuDeleteJava_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuDeleteJava(e);
  }
}

/**
 * Clase que implementa las acciones del menu-Archivo-A�adir un nuevo archivo
 * al proyecto.
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuNewJava_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuNewJava_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuNewJava(e);
  }
}

/**
 * Clase que implementa las acciones del menu-Archivo-Abrir Archivo Java.
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuOpenJava_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuOpenJava_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuOpenJava(e);
  }
}

/**
 * Clase que implementa las acciones del menu-Modificar preferencias.
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuPreferences_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuPreferences_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuOptions(e);
  }
}

/**
 * Clase que implementa las acciones del menu-Seleccionar clase principal del
 * sistema.
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuMainClass_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuMainClass_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuMainClass(e);
  }
}

/**
 * Clase que implementa las acciones del menu-Generar documentaci�n Javadoc para
 * el proyecto.
 * @author  Digna Rodr�guez
 * @version  1.0
 */
class Main_menuJavaDoc_ActionAdapter
    implements ActionListener {
  MainWindow vent;
  Main_menuJavaDoc_ActionAdapter(MainWindow v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.menuJavaDoc(e);
  }
}

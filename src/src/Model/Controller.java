package Model;

//librerias basicas
import java.io.*;
import java.util.*;
import javax.swing.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.JDOMException;

/**
 * Clase que responde a eventos e invoca cambios en el modelo
 * @version 1.0
 * @author Digna Rodr�guez
 */
public class Controller {

  public static String defaultJavaPath = File.separator + "GiaJava";
  public static String defaultSavePath = File.separator + "Workspace";
  public static String mainClass = "";
  public static String[] vecTipo;
  public static String[] vecTipoReturn;
  public static ClassContainer contenido;
  public static AssociationTable asoc;
  public static GeneralizationTable gener = new GeneralizationTable();
  public static String errorBienFormado = "";

  public Controller() {
  }

  /**
   * El m�todo existsClass Comprueba si ya existe el nombre para esa clase.
   * @param nClass es un String que almacena el nombre de la clase.
   * @return boolean valor verdadero si ya existe la clase false
   * en caso contrario.
   */
  public static boolean existsClass(String nClass) {
    UMLClass clase = new UMLClass();
    for (int i = 0; i < contenido.vClasses.size(); i++) {
      clase = ( (UMLClass) contenido.vClasses.get(i));
      if (clase != null) {
        if (clase.getName().compareTo(nClass) == 0) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * M�todo que ordena en classContainer de manera que los atributos que son
   * vectores aparecen al final del vector de atributos.
   */
  public static void sort(){
    UMLClass clase = new UMLClass();
     for (int i = 0; i < contenido.vClasses.size(); i++) {
       clase = ( (UMLClass) contenido.vClasses.get(i));
       for(int k=0;k< clase.vAttributes.size();k++)
       {
         UMLAttribute at= ((UMLAttribute)clase.vAttributes.get(k));
         if((at.getName().substring(0,3).compareTo("$v_")==0) ||
         ((at.getName().substring(0,3).compareTo("$r_")==0))){
           //lo eliminamos de su posici�n y lo a�adimos al final.
           ((UMLClass) contenido.vClasses.get(i)).vAttributes.remove(k);
           ((UMLClass) contenido.vClasses.get(i)).vAttributes.add(at);
         }
       }
     }
 }

  /**
   * M�todo que a�ade los tipos de datos primitivos y las clases al vector de
   * tipos disponible para todas las clases de la aplicaci�n.
   */
  public static void addTypes() {
    //primero a�adimos los tipos primitivos
    String[] v = {
        "byte", "short", "int", "long", "boolean", "float", "double", "char",
        "String", "Vector", "Date"};
    vecTipo = new String[11 + Controller.contenido.vClasses.size()];
    int j = 0;
    for (int i = 0; i < v.length; i++) {
      vecTipo[j] = v[i];
      j++;
    }
    //a�adimos los tipos de las clases
    for (int k = 0; k < Controller.contenido.vClasses.size(); k++) {
      vecTipo[j] = ( (UMLClass) (Controller.contenido.vClasses.get(k))).name;
      j++;
    }
  }

  /**
   * M�todo que devuelve le plural de una clase
   * @param nombre String  nombre de la clase
   * @return String "s" o "es" para mostrar el plural de una clase.
   */
  public static String getPlural(String nombre) {
    int tam = nombre.length() - 1;
    if ( (nombre.charAt(tam) == 'a') || (nombre.charAt(tam) == 'e') ||
        (nombre.charAt(tam) == 'i')
        || (nombre.charAt(tam) == 'o') || (nombre.charAt(tam) == 'u'))
      return "s";
   else
      return "es";
  }

  /**
   * M�todo que a�ade los tipos de datos primitivos y las clases al vector de
   * tipos de retorno disponibles para todas las clases de la aplicaci�n.
   * A diferencia de addTypes() tambi�n a�ade el tipo void.
   */
  public static void addTypesWithVoid() {
    //primero a�adimos los tipo b�sicos
    String[] v = {
        "void",
        "byte", "short", "int", "long", "boolean", "float", "double", "char",
        "String", "Vector", "Date"};
    vecTipoReturn = new String[12 + Controller.contenido.vClasses.size()];
    int j = 0;
    for (int i = 0; i < v.length; i++) {
      vecTipoReturn[j] = v[i];
      j++;
    }
    //a�adimos los tipos de las clases.
    for (int k = 0; k < Controller.contenido.vClasses.size(); k++) {
      vecTipoReturn[j] = ( (UMLClass) (Controller.contenido.vClasses.get(k))).
          name;
      j++;
    }
  }

  /**
   * Crea un nuevo fichero java vac�o.
   * @param path String ruta en la que se crea el fichero.
   * @param nombre String del fichero java.
   * @return String mensaje de error en caso de el fichero ya exista.
   */
  public static String newJavaFile(String path, String nombre) {
    File fich = new File(path + File.separator + nombre + ".java");
    String error = "";
    if (fich.exists()) {
      error = "El fichero " + nombre + ".java ya existe";
    }
    else {
      // A partir del objeto File creamos el fichero f�sicamente
      try {
        if (!fich.createNewFile()) {
          System.out.println("No ha podido crearse el fichero");
        }
      }
      catch (IOException ex) {
      }
    }
    return error;
  }

  /**
     * Elimina los elementos que son referencias a la clase borrada.
     * @param claseborrada String nombre de la clase borrada.
     */
    public static void deleteAtbRel(String claseborrada)
  {

    if (Controller.existsClass(claseborrada)) {
      for (int i = 0; i < contenido.vClasses.size(); i++) {
        UMLClass c = (UMLClass) contenido.vClasses.get(i);
        System.out.println("Clase " + c.getName());
        for (int k = 0; k < c.vAttributes.size(); k++) {
          UMLAttribute at = (UMLAttribute) c.vAttributes.get(k);
          System.out.println("-"+ at.getName());
          System.out.println(claseborrada+"=="+at.getType());
          if(claseborrada.compareTo(at.getType())==0){

            ( (UMLClass) contenido.vClasses.get(i)).vAttributes.remove(
              k);
         }
         if(claseborrada.compareTo(at.getVectorType())==0){

           ( (UMLClass) contenido.vClasses.get(i)).vAttributes.remove(
             k);
        }
        }
      }
    }
  }

  /**
   * El m�todo createDirectory comprueba que existe un directorio en esa ruta, y
   * si existe genera los archivos .java y los almacena en ese directorio, en
   * caso contrario emite un mensaje de error
   *
   * @param dir es un String que contiene la ruta en la que se almacenar�n los
   *   ficheros
   * @return String mensaje de error o cadena vac�a en caso de que no exista
   *   error.
   * @throws IOException lanza una excepci�n sino se puede crear el fichero.
   */
  public static String createDirectory(String dir) throws IOException {
    String error = "";
    if (!Controller.validatePath(dir)) {
      File f = new File(dir);
      //creamos todos los subdirectorios
      f.mkdirs();
    }
    return error;
  }

  /**
   * M�todo que sirve para comprobar si el nombre de un archivo contiene
   * caracteres especiales.
   * @param nom String nombre del fichero a analizar
   * @return boolean devuelve verdadero si es v�lido falso en caso contrario.
   */
  public static boolean validateFileName(String nom) {
    if ( (nom.indexOf("\\") == -1) &&
        (nom.indexOf("/") == -1)
        && (nom.indexOf("*") == -1) &&
        (nom.indexOf("<") == -1)
        && (nom.indexOf(";") == -1) &&
        (nom.indexOf("?") == -1)
        && (nom.indexOf("|") == -1) &&
        (nom.indexOf("\"") == -1)) {
      return true;
    }
    else return false;
  }

  /**
   * Comprueba si el nombre que se le pasa por par�metro tiene una determinada
   * extensi�n
   * @param nombre String nombre del fichero .
   * @param ext String extensi�n a validar.
   * @return boolean true si es un fichero de la extensi�n ext false en caso
   * contrario.
   */
  public static boolean isFile(String nombre, String ext) {
    String extension = "";
    int i = 0;
    while (i < nombre.length() && (nombre.charAt(i) != '.'))
      i++;
    if (nombre.charAt(i) == '.')
      extension = nombre.substring(i, nombre.length());
    if (extension.compareTo(ext) == 0)
      return true;
    else
      return false;
  }

  /**
   * El m�todo openFile Comprueba si el directorio que le pasamos
   * por par�metro existe y es un fichero, en caso de que no lo sea emite un mensaje
   * de error.Adem�s comprueba que el archivo XMI est� bien formado.
   * @param dir Ruta que se desea validar.
   * @return boolean : Devuelve true si la ruta es v�lida, false en caso contrario
   */
  public static String openFile(String dir) {
    String error = "";
    if (!Model.Controller.validateFile(dir)) {
      error = "Debe introducir una ruta v�lida en el campo. \nEl archivo debe de tener extensi�n xmi";
    } //si la ruta es correcta miramos si el archivo est� bien formado
    else {
      boolean bienformado = true;
      File archivo = new File(dir);
      try {
        bienformado = Model.Controller.wellFormedXML(archivo);
      }
      catch (JDOMException ex) {
      }
      catch (IOException ex) {
      }
      if (bienformado == false) {
        error = "El archivo no est� bien formado.\n Compruebe que el archivo cumple la sint�xis del lenguaje XML ";
      }
    }
    return error;
  }

  /**
   * El m�todo validateFile Comprueba si la ruta que se le pasa como
   * par�metro es la de un fichero existente.
   * @param ruta String que almacena la ruta a validar.
   * @return boolean : Devuelve true si la ruta es v�lida, false en caso contrario
   */
  public static boolean validateFile(String ruta) {
    File f1 = new File(ruta);
    if (f1.exists() && f1.isFile()) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * El m�todo validatePath Comprueba si la ruta que se le pasa
   * como par�metro es correcta.
   * @param ruta String que almacena la ruta a validar.
   * @return boolean : Devuelve true si la ruta es v�lida, false en caso contrario
   */
  public static boolean validatePath(String ruta) {
    File f = new File(ruta);
    if (f.exists())
      return true;
    else return false;
  }

  /**
   * El m�todo printFile imprime a trav�s de la impresora el
   * texto del JTextPane que se le pasa como par�metro.
   * @param panel JTextPane que almacena el texto a imprimir por la impresora
   */
  public static void printFile(JTextPane panel) {
    DocumentRender dr = new DocumentRender();
    boolean ok = dr.pageDialogok();
    //dr.pageDialog();
    dr.setDocument(panel);
    dr.printDialog();

  }

  /**
   * El m�todo printClassContainer muestra por consola el contenido
   * de las clases, las relaciones, y los tipos de datos de la aplicaci�n.
   */
  public static void printAll() {
    System.out.println("*********************************************");
    asoc.printAssociations();
    System.out.println("*********************************************\n\n");
    System.out.println("*********************************************");
    gener.printGenralizations();
    System.out.println("*********************************************\n\n");
    System.out.println("*********************************************");
    contenido.printAllClasses();
    System.out.println("*********************************************\n\n");
    System.out.println("*********************************************");
    contenido.printAllClasses();
    System.out.println("*********************************************");
  }

  /**
   * El m�todo modifyAsociationTable
   *
   * @param nameClassA String nombre de la clase que est� en el inicio de la
   * relaci�n.
   * @param nameClassB String nombre de la clase que est� en el fin de la
   * relaci�n.
   */
  public static void modifyAsociationTable(String nameClassA, String nameClassB) {
    String nom;
    for (int k = 0; k < asoc.vAssoc.size(); k++) {
      for (int i = 0; i < ( (UMLAssociation) asoc.vAssoc.get(k)).vBeginEnd.size();
           i++) {
        nom = (String) ( ( (UMLAssociation) asoc.vAssoc.get(k)).vBeginEnd.get(i));
        if (nom.compareTo(nameClassA) == 0) {
          ( (UMLAssociation) asoc.vAssoc.get(k)).vBeginEnd.set(i,
              (String) nameClassB);
        }
      }
    }
  }

/**
 * El m�todo modifyGeneralizationTable
 * @param nameClassA String nombre de la clase que est� en el inicio de la
 * relaci�n.
 * @param nameClassB String nombre de la clase que est� en el fin de la
 * relaci�n.
 */
public static void modifyGeneralizationTable(String nameClassA, String nameClassB) {
  String nom;
  for (int k = 0; k < gener.vGen.size(); k++) {
    for (int i = 0; i < ( (UMLGeneralization) gener.vGen.get(k)).vBeginEnd.size();
         i++) {
      nom = (String) ( ( (UMLGeneralization) gener.vGen.get(k)).vBeginEnd.get(i));
      if (nom.compareTo(nameClassA) == 0) {
        ( (UMLGeneralization) gener.vGen.get(k)).vBeginEnd.set(i,
            (String) nameClassB);
      }
    }
  }
}




  /**
   * M�todo que modifica el nombre de los tipos de los atributos de una clase
   * cuando estos son referencias  a vectores de objetos o a objetos.
   * @param nombreant String
   * @param nuevoNom String
   */
  public static void modifyClassContainer(String nombreant,String nuevoNom ){
    String tipo = "";
    String vectortipo="";
   for (int k = 0; k < Controller.contenido.vClasses.size(); k++) {
     UMLClass c = ( (UMLClass) Controller.contenido.vClasses.get(k));
     for(int i=0;i<c.vAttributes.size();i++){
       UMLAttribute at = (UMLAttribute) c.vAttributes.get(i);
       tipo = at.getType();
       vectortipo= at.getVectorType();
       if(tipo.compareTo(nombreant)==0){
         at.setType(nuevoNom);
       }
       if(vectortipo.compareTo(nombreant)==0)
       {
         at.setVectorType(nuevoNom);
       }
     }
   }

 }
  /**
   * M�todo que elimina una relacion de asociaci�n de la AssociationTable
   * @param nameClassA String identificador de uno de los dos elementos de la
   * asociaci�n.
   */
  public static void deleteAsociation(String nameClassA) {
    String begin = "";
    String end = "";
    int k = 0;
    while (k < asoc.vAssoc.size()) {
      begin = (String) ( ( (UMLAssociation) asoc.vAssoc.get(k)).vBeginEnd.get(0));
      end = (String) ( ( (UMLAssociation) asoc.vAssoc.get(k)).vBeginEnd.get(1));
      if ( (begin.compareTo(nameClassA) == 0) ||
          (end.compareTo(nameClassA) == 0)) {
        asoc.vAssoc.remove(k);
        k--;
      }
      k++;
    }
  }
  /**
   * M�todo que en caso de que varie el tipo de un atributo de un vector que
   * antes era un Vector de objetos o una referencia a un objeto, eliminamos
   * la relaci�n.
   * @param padre String nombre de la clase de la que se modifica el atb.
   * @param vAtbAnterior Vector que contiene los datos del vector anterior
   * de caracter�sticas de un atributo.
   * @param vNuevo Vector que contine los datos del vector nuevo de
   * caracter�sticas de un atributo
   */
  public static void delAssoc(String padre, Vector vAtbAnterior, Vector vNuevo) {
    //comparamos si han cambiado los tipos.
    //si era de tipo Vector de objetos ->1 a n
    if ( ( ( (String) vAtbAnterior.get(1)).compareTo("Vector") == 0) &&
        (Controller.existsClass( (String) vAtbAnterior.get(4)))) {
      if ( ( ( (String) vAtbAnterior.get(1)).compareTo( (String) vNuevo.get(1)) ==
            0) &&
          ( (String) vAtbAnterior.get(4)).compareTo( (String) vNuevo.get(4)) ==
          0) {
        //no se ha modificado nada
      }
      else {
        if ( ( ( (String) vAtbAnterior.get(1)).compareTo( (String) vNuevo.get(1)) ==
              0) &&
            ( (String) vAtbAnterior.get(4)).compareTo( (String) vNuevo.get(4)) !=
            0){
         deleteAsociation(padre, (String) vAtbAnterior.get(4));
       }
       else {
         if( ( (String) vAtbAnterior.get(1)).compareTo( (String) vNuevo.get(1)) !=
              0) {
         deleteAsociation(padre, (String) vAtbAnterior.get(4));
       }

       }
      }
    }
    //si era una relaci�n de 1 a 1
    else {
      if (Controller.existsClass( (String) vAtbAnterior.get(1))) {
        if ( ( ( (String) vAtbAnterior.get(1)).compareTo( (String) vNuevo.get(1)) ==
              0)) {
          //no ha cambiado
        }
        else {
          if ( ( ( (String) vAtbAnterior.get(1)).compareTo( (String) vNuevo.get(
              1)) !=
                0)) {
            deleteAsociation(padre, (String) vAtbAnterior.get(1));
          }
        }
      }
    }
  }

  /**
   * M�todo que elimina una relacion de asociaci�n de la AssociationTable
   * @param claseini String identificador de la clase inicio de la relaci�n.
   * @param clasefin String identificador de la clase fin de la relaci�n.
   */
  public static void deleteAsociation(String claseini, String clasefin) {
    String begin = "";
    String end = "";
    int k = 0;
    while (k < asoc.vAssoc.size()) {
      begin = (String) ( ( (UMLAssociation) asoc.vAssoc.get(k)).vBeginEnd.get(0));
      end = (String) ( ( (UMLAssociation) asoc.vAssoc.get(k)).vBeginEnd.get(1));
      if ( (begin.compareTo(claseini) == 0) &&
          (end.compareTo(clasefin) == 0)) {
        asoc.vAssoc.remove(k);
        k--;
      }
      k++;
    }
  }

  /**
   * M�todo que crea la tabla de asociaciones de la aplicaci�n.
   * @param cc ClassContainer contenedor de clases de la aplicaci�n a partir
   * del cual se crea la AssociationTable.
   */
  public static void createAssociationTable(ClassContainer cc) {
    asoc = new AssociationTable();
    Vector vClass = new Vector();
    String nom = "";

    //Almacenamos el nombre de las clases en un vector de strings
    for (int k = 0; k < cc.vClasses.size(); k++) {
      nom = ( (UMLClass) cc.vClasses.get(k)).getName();
      vClass.add(nom);
    }
    //Recorremos todos los atributos de todas las clases
    for (int i = 0; i < cc.vClasses.size(); i++) {
      UMLClass clase = ( (UMLClass) cc.vClasses.get(i));
      for (int j = 0; j < clase.vAttributes.size(); j++) {
        UMLAttribute atb = (UMLAttribute) clase.vAttributes.get(j);

        //si el tipo es un vector
        if ( (atb.getType().compareTo("Vector") == 0) &&
            (atb.getVectorType().compareTo("") != 0)) {
          for (int r = 0; r < vClass.size(); r++) {
            if ( ( ( (String) vClass.get(r)).compareTo(atb.getVectorType())) ==
                0) {
              UMLAssociation as = new UMLAssociation();
              as.vBeginEnd.add(clase.getName()); //inicio Agenda
              as.vBeginEnd.add(atb.getVectorType()); //fin Contacto
              as.multiplicity.add("1");
              as.multiplicity.add("-1");
              asoc.vAssoc.add(as);
            }
          }
        }
        else {
          /*comparamos el nombre del atributo con los nombres de las clases
                  para saber si son referencias*/
          for (int r = 0; r < vClass.size(); r++) {
            if (atb.getType().compareTo(vClass.get(r)) == 0) {

              UMLAssociation as = new UMLAssociation();
              as.vBeginEnd.add(clase.getName()); //inicio
              as.vBeginEnd.add(atb.getType()); //fin
              as.multiplicity.add("1");
              as.multiplicity.add("1");
              asoc.vAssoc.add(as);
            }
          }
        }
      }
    }
  }

  /**
   * El m�todo loadData Extrae la informaci�n del XMI y crea:
   * - Un objeto ClassContainer que contendr� todas las clases del proyecto.
   * - Un objeto AssociationTable con todas las asociaciones entre clases del
   * sistema.
   * - Un objeto GeneralizationTable con todas las relaciones de herencia del
   * sistema.
   * - Y una tabla de rutas en la que se encuentran los tipos de datos del
   * sistema.
   * @throws IOException excepci�n de entrada salida.
   */
  public static void loadData() throws IOException {
    ParserXMI parser = new ParserXMI();
    contenido = new ClassContainer();

    DataTypeTable tdt = new DataTypeTable();
    asoc = new AssociationTable();
    gener = new GeneralizationTable();

    asoc = parser.readAssociations();
    gener = parser.readGeneralizations();
    tdt = parser.readTypes();
    contenido = parser.readXMI();

    //Modificaciones en el vector de asociaciones
    //cambio de id en begin y end por nombre de clase
    String nuevo = "";
    for (int i = 0; i < asoc.vAssoc.size(); i++) {
      String s1 = (String) ( (UMLAssociation) asoc.vAssoc.get(i)).vBeginEnd.get(
          0);
      String s2 = (String) ( (UMLAssociation) asoc.vAssoc.get(i)).vBeginEnd.get(
          1);
      nuevo = tdt.searchType(s1);
      ( (UMLAssociation) asoc.vAssoc.get(i)).vBeginEnd.setElementAt(nuevo, 0);
      nuevo = tdt.searchType(s2);
      ( (UMLAssociation) asoc.vAssoc.get(i)).vBeginEnd.setElementAt(nuevo, 1);
    }

    //Modificaciones en el vector de generalizaciones
    //cambio de id en padre y hijo por nombre de clase
    String nuevo2 = "";
    for (int i = 0; i < gener.vGen.size(); i++) {
      String s1 = (String) ( (UMLGeneralization) gener.vGen.get(i)).vBeginEnd.
          get(
          0);
      String s2 = (String) ( (UMLGeneralization) gener.vGen.get(i)).vBeginEnd.
          get(
          1);
      nuevo2 = tdt.searchType(s1);
      ( (UMLGeneralization) gener.vGen.get(i)).vBeginEnd.setElementAt(nuevo2, 0);
      nuevo2 = tdt.searchType(s2);
      ( (UMLGeneralization) gener.vGen.get(i)).vBeginEnd.setElementAt(nuevo2, 1);
    }

    //a�adir vector si multiplicidad(1,n) o (1,1)
    String n = ""; //Clase donde hay que a�adir un vector de tipo de la clase uno
    String uno = "";

    //a�adir vecto multiplicidad (1,1)
    String ini = ""; //Clase en la que a�adimos una ref a la clase fin
    String fin = "";
    boolean unoauno = false;
    boolean unoan = false;

    for (int i = 0; i < asoc.vAssoc.size(); i++) {
      unoauno = false;
      unoan = false;
      String mult1 = (String) ( (UMLAssociation) asoc.vAssoc.get(i)).
          multiplicity.get(0);
      String mult2 = (String) ( (UMLAssociation) asoc.vAssoc.get(i)).
          multiplicity.get(1);

      if ( (mult1.compareTo("-1") == 0) || (mult2.compareTo("-1") == 0)) {
        if (mult1.compareTo("-1") == 0) {
          n = (String) ( (UMLAssociation) asoc.vAssoc.get(i)).vBeginEnd.get(0);
          uno = (String) ( (UMLAssociation) asoc.vAssoc.get(i)).vBeginEnd.get(
              1);
        }
        else {
          //Agenda
          uno = (String) ( (UMLAssociation) asoc.vAssoc.get(i)).vBeginEnd.get(
              0);
          //Contacto
          n = (String) ( (UMLAssociation) asoc.vAssoc.get(i)).vBeginEnd.get(1);
          unoan = true;
        }
      }
      else {
        if ( (mult1.compareTo("1") == 0) && (mult2.compareTo("1") == 0)) {
          unoauno = true;
          ini = (String) ( (UMLAssociation) asoc.vAssoc.get(i)).vBeginEnd.get(
              0);
          fin = (String) ( (UMLAssociation) asoc.vAssoc.get(i)).vBeginEnd.get(
              1);
        }
      }

      /*En caso de relaciones 1 a 1 a�adimos una
           nueva referencia al end en la clase begin*/
      if (unoauno) {
        boolean paso2 = false;
        for (int k = 0; k < contenido.vClasses.size(); k++) {
          if ( ( (UMLClass) contenido.vClasses.get(k)).name.compareTo(ini) == 0) {
            if (!paso2) {
              UMLAttribute atb = new UMLAttribute();
              atb.name = "$r_" + fin;
              atb.setVisibility(3);
              atb.type = fin;
              atb.setVectorType(null);
              ( (UMLClass) contenido.vClasses.get(k)).vAttributes.addElement(
                  atb);
              paso2 = true;
            }
          }
        }
      }

      /*En caso de relaciones 1 a n
        a�adimos el nuevo atributo de tipo vector a la clase*/
      if (unoan) {
        boolean paso = false;
        for (int k = 0; k < contenido.vClasses.size(); k++) {
          if ( ( (UMLClass) contenido.vClasses.get(k)).name.compareTo(uno) == 0) {
            if (!paso) {
              UMLAttribute atb = new UMLAttribute();
              atb.name = "$v_" + n + Controller.getPlural(n);
              atb.setVisibility(3);
              atb.setVectorType(n);
              atb.type = "Vector";
              ( (UMLClass) contenido.vClasses.get(k)).vAttributes.addElement(
                  atb);
              paso = true;
            }
          }
        }
      }
    }
    printAll();
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (UnsupportedLookAndFeelException ex1) {
    }
    catch (IllegalAccessException ex1) {
    }
    catch (InstantiationException ex1) {
    }
    catch (ClassNotFoundException ex1) {
    }
    addTypes();
    addTypesWithVoid();
  }

  /**
   * El m�todo wellFormedXML comprueba que un archivo XML est� bien formado.
   * @param XMLFile fichero con formato XML.
   * @throws IOException
   * @throws JDOMException
   * @return boolean valor verdadero si est� bien formado, falso en caso contrario.
   */
  public static boolean wellFormedXML(File XMLFile) throws IOException,
      JDOMException {
    boolean esValido = false;
    SAXBuilder builder = new SAXBuilder();
    // El argumento debe ser una URI o el nombre de un fichero
    try {
      builder.build(XMLFile.getPath());
      // Si esta bien formado no se produce una exepcion
      esValido = true;
    }
    catch (JDOMException e) { // indica un error por no estar bien formado
      esValido = false;
      errorBienFormado = e.getMessage();
      System.out.println("ERROR:" + errorBienFormado);
    }
    return esValido;
  }

  /**
   * El m�todo isMethod m�todo que comprueba si un string es
   * un m�todo o no.
   * @param met es un String que almacena el nombre del m�todo.
   * @return boolean true si el par�metro es un m�todo o false en caso
   * contrario.
   */
  public static boolean isMethod(String met) {
    for (int i = 0; i < met.length(); i++) {
      if (met.charAt(i) == '(') {
        return true;
      }
    }
    return false;
  }

  /**
   * El m�todo getOnlyName obtiene el nombre del m�todo
   * @param nombrepar String que almacena el nombre del m�todo y sus par�metros.
   * @return String nombre del m�todo sin par�metros.
   */
  public static String getOnlyName(String nombrepar) {
    int i = 0;
    boolean encontrado = false;
    String salida = "";
    while (i < nombrepar.length() && !encontrado) {
      if (nombrepar.charAt(i) != '(')
        salida += nombrepar.charAt(i);
      else
        encontrado = true;
      i++;
    }
    return salida;
  }

  /**
   * El m�todo readBasicXML se encarga de cargar el contenido de
   * un proyecto ya existente y con formato xml en la herramienta.
   * @param ruta String que almacena la ruta del fichero xml ra�z del proyecto
   * a abrir.
   * @return Vector que contiene las rutas de los archivos xml que forman
   * parte del proyecto.
   */
  public static Vector readBasicXML(String ruta) {
    Vector vfic = new Vector();
    try {
      DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().
          newDocumentBuilder();
      Document doc = docBuilder.parse(new File(ruta));
      getAllNodes(vfic, doc);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    return vfic;

  }

  /**
   * El m�todo getAllNodes es un m�todo recursivo que se encarga de
   * leer el contenido del fichero xml ra�z del proyecto e introducirlo en un
   * vector que contiene el nombre de los archivos que forman parte del
   * proyecto.
   * @param res Vector en el que almacena la ruta de los archivos que forman
   * el proyecto.
   * @param nodo Node actual que contienen la informaci�n.
   */
  public static void getAllNodes(Vector res,
                                 Node nodo) {

    switch (nodo.getNodeType()) {
      //NODO PRINCIPAL DEL DOCUMENTO lista_peliculas
      case Node.DOCUMENT_NODE:
        Document doc = (Document) nodo;
        getAllNodes(res, doc.getDocumentElement());
        break;

        //NODO ELEMENTO
      case Node.ELEMENT_NODE:

        NodeList hijos = nodo.getChildNodes(); //lista de hijos del nodo

        if (nodo.getNodeName() == "project") {
          //Aqui recorremos todas los  file hijos de project
          for (int i = 0; i < hijos.getLength(); i++)
            getAllNodes(res, hijos.item(i));
        }
        else { //son los elementos file
          for (int i = 0; i < hijos.getLength(); i++)
            getAllNodes(res, hijos.item(i));
        }
        break;

        //NODO DE TEXTO
      case Node.TEXT_NODE:
        String texto = nodo.getNodeValue().trim();
        if (!texto.equals("")) {
          res.add(texto);
        }
        break;
    }
  }

  /**
   * El m�todo readCompletXML se encarga de cargar el contenido de
   * un proyecto ya existente y con formato xml en la herramienta.
   * @param rutaXML String que almacena la ruta del fichero xml a leer.
   * @return UMLClass que contiene los datos de la clase leida.
   */
  public static UMLClass readCompletXML(String rutaXML) {
    UMLClass clase = new UMLClass();

    Vector datosClase = new Vector();
    Vector datosAtb = new Vector();
    Vector datosMet = new Vector();
    Vector datosPar = new Vector();
    UMLMethod met = new UMLMethod();

    try {
      DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().
          newDocumentBuilder();
      Document doc = docBuilder.parse(new File(rutaXML));
      tratarNodoXML(clase, datosClase, datosAtb, met, datosMet, datosPar, doc);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    clase.printClass();
    return clase;
  }

  /**
   * El m�todo tratarNodoXML es un m�todo recursivo que se encarga de
   * leer el contenido del fichero xml de una clase del proyecto e introducirlo
   * en una estructura denominada UMLClass que contiene todos los datos relativos
   * a una clase.
   * @param clase UMLClass clase en la que se almacena el contenido de una clase uml.
   * @param datosClase Vector que contiene los datos relativos a una clase.
   * @param datosAtb Vector que contiene los datos de los atributos de la clase.
   * @param met UMLMethod clase que contiene todos los datos relativos a un m�todo.
   * @param datosMet Vector que contiene los datos relativos a los m�todos de la clase.
   * @param datosPar Vector que contiene los datos de un par�metro de un m�todo.
   * @param nodo Node actual que contienen la informaci�n.
   */
  public static void tratarNodoXML(UMLClass clase, Vector datosClase,
                                   Vector datosAtb, UMLMethod met,
                                   Vector datosMet, Vector datosPar, Node nodo) {

    switch (nodo.getNodeType()) {
      /*
       * Nodo de tipo Raiz del documento
       */
      case Node.DOCUMENT_NODE:
        Document doc = (Document) nodo;
        tratarNodoXML(clase, datosClase, datosAtb, met, datosMet, datosPar,
                      doc.getDocumentElement());
        break;

        /*
         * Nodo de tipo Elemento
         */
      case Node.ELEMENT_NODE:

        // System.out.println(nodo.getNodeName());
        NodeList hijos = nodo.getChildNodes();
        if (nodo.getNodeName() == "uml:class") {
          for (int i = 0; i < hijos.getLength(); i++)
            tratarNodoXML(clase, datosClase, datosAtb, met, datosMet, datosPar,
                          hijos.item(i));
        }
        else {
          if ( (nodo.getNodeName() == "nameclass") ||
              (nodo.getNodeName() == "visibility") ||
              (nodo.getNodeName() == "abstract")) {

            for (int i = 0; i < hijos.getLength(); i++)
              tratarNodoXML(clase, datosClase, datosAtb, met, datosMet,
                            datosPar,
                            hijos.item(i));

            if (datosClase.size() == 3) {
              System.out.println("Vector de clase" + datosClase.toString());
              clase.name = (String) datosClase.get(0);

              if ( ( (String) datosClase.get(1)).compareTo("1") == 0)
                clase.setVisibility(1);
              else {
                if ( ( (String) datosClase.get(1)).compareTo("2") == 0)
                  clase.setVisibility(2);
                else if ( ( (String) datosClase.get(1)).compareTo("3") == 0)
                  clase.setVisibility(3);
              }
              if ( ( (String) datosClase.get(1)).compareTo("no") == 0)
                clase.isAbstract = false;
              else
                clase.isAbstract = true;

            }
          }
          else { //es un atb o un metodo
            UMLAttribute auxAtb = new UMLAttribute();
            Vector datosA = new Vector();
            if (nodo.getNodeName() == "attribute") {
              for (int i = 0; i < hijos.getLength(); i++)
                tratarNodoXML(clase, datosClase, datosA, met, datosMet,
                              datosPar,
                              hijos.item(i));
            }
            else {
              if ( (nodo.getNodeName() == "name_atb") ||
                  (nodo.getNodeName() == "visib_atb") ||
                  (nodo.getNodeName() == "type_atb") ||
                  (nodo.getNodeName() == "type_vector") ||
                  (nodo.getNodeName() == "static_atb")) {
                for (int i = 0; i < hijos.getLength(); i++)
                  tratarNodoXML(clase, datosClase, datosAtb, met, datosMet,
                                datosPar,
                                hijos.item(i));

                  //Creamos un atributo con todos sus datos
                if (datosAtb.size() == 5) {
                  auxAtb.setName( (String) datosAtb.get(0));
                  if ( ( (String) datosAtb.get(1)).compareTo("1") == 0)
                    auxAtb.setVisibility(1);
                  else {
                    if ( ( (String) datosAtb.get(1)).compareTo("2") == 0)
                      auxAtb.setVisibility(2);
                    else if ( ( (String) datosAtb.get(1)).compareTo("3") == 0)
                      auxAtb.setVisibility(3);
                  }

                  auxAtb.setType( (String) datosAtb.get(2));
                  auxAtb.setVectorType( (String) datosAtb.get(3));
                  if ( ( (String) datosAtb.get(4)).compareTo("no") == 0)
                    auxAtb.setStatic(false);
                  else
                    auxAtb.setStatic(true);

                    //A�adimos el atributo a la clase
                  clase.vAttributes.add(auxAtb);
                }
              }
              else {
                UMLMethod auxmet = new UMLMethod();
                Vector datosM = new Vector();
                if (nodo.getNodeName() == "method") {
                  //cada vez que entra por aqu� le pasamos un vector datosM vac�o
                  for (int i = 0; i < hijos.getLength(); i++)
                    tratarNodoXML(clase, datosClase, datosA, auxmet, datosM,
                                  datosPar,
                                  hijos.item(i));
                }
                else {

                  if ( (nodo.getNodeName() == "name_met") ||
                      (nodo.getNodeName() == "visib_met") ||
                      (nodo.getNodeName() == "abstract_met") ||
                      (nodo.getNodeName() == "static_met") ||
                      (nodo.getNodeName() == "return")) {
                    for (int i = 0; i < hijos.getLength(); i++)
                      tratarNodoXML(clase, datosClase, datosAtb, met, datosMet,
                                    datosPar,
                                    hijos.item(i));

                      //Creamos un atributo con todos sus datos
                    if (datosMet.size() == 5) {
                      met.setName( (String) datosMet.get(0));
                      if ( ( (String) datosMet.get(1)).compareTo("1") == 0)
                        met.setVisibility(1);
                      else {
                        if ( ( (String) datosMet.get(1)).compareTo("2") == 0)
                          met.setVisibility(2);
                        else if ( ( (String) datosMet.get(1)).compareTo("3") ==
                                 0)
                          met.setVisibility(3);
                      }
                      if ( ( (String) datosMet.get(2)).compareTo("no") == 0)
                        met.setAbstract(false);
                      else
                        met.setAbstract(true);

                      if (datosMet.get(3) == "no")
                        met.setStatic(false);
                      else
                        met.setStatic(true);

                      met.setType( (String) datosMet.get(4));

                      //A�adimos el m�todo a la clase
                      clase.vOperations.add(met);
                    }
                  }
                  else {
                    UMLParameter auxPar = new UMLParameter();
                    Vector datosP = new Vector();
                    if (nodo.getNodeName() == "parameter") {
                      //cada vez que entra por aqu� le pasamos un vector datosP vac�o
                      for (int i = 0; i < hijos.getLength(); i++)
                        tratarNodoXML(clase, datosClase, datosA, met, datosMet,
                                      datosP,
                                      hijos.item(i));
                    }
                    else {
                      if ( (nodo.getNodeName() == "name_par") ||
                          (nodo.getNodeName() == "type_par")) {
                        for (int i = 0; i < hijos.getLength(); i++)
                          tratarNodoXML(clase, datosClase, datosAtb, met,
                                        datosMet,
                                        datosPar, hijos.item(i));
                        if (datosPar.size() == 2) {
                          auxPar.setName( (String) datosPar.get(0));
                          auxPar.setType( (String) datosPar.get(1));
                          //A�adimos el parametro al m�todo
                          met.parameters.add(auxPar);
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        break;
        /*
         * Nodo de tipo Texto
         */
      case Node.TEXT_NODE:
        String text = nodo.getNodeValue();

        if ( (nodo.getParentNode().getNodeName() == "nameclass") ||
            (nodo.getParentNode().getNodeName() == "visibility") ||
            (nodo.getParentNode().getNodeName() == "abstract"))
          datosClase.add(text);
        else {
          if ( (nodo.getParentNode().getNodeName() == "name_atb") ||
              (nodo.getParentNode().getNodeName() == "visib_atb") ||
              (nodo.getParentNode().getNodeName() == "type_atb") ||
              (nodo.getParentNode().getNodeName() == "type_vector") ||
              (nodo.getParentNode().getNodeName() == "static_atb"))
            datosAtb.add(text);

          else {
            if ( (nodo.getParentNode().getNodeName() == "name_met") ||
                (nodo.getParentNode().getNodeName() == "visib_met") ||
                (nodo.getParentNode().getNodeName() == "abstract_met") ||
                (nodo.getParentNode().getNodeName() == "static_met") ||
                (nodo.getParentNode().getNodeName() == "return"))
              datosMet.add(text);
            else if ( (nodo.getParentNode().getNodeName() == "name_par") ||
                     (nodo.getParentNode().getNodeName() == "type_par"))
              datosPar.add(text);
          }
        }
        break;
    } //switch
  }

  /**
   * El m�todo validateAllXML Comprueba si el directorio que le
   * pasamos por par�metro existe y es un fichero, en caso de que no lo sea
   * emite un mensaje de error.Adem�s comprueba que el archivo XML ra�z y
   * todos los que foman el proyecto est�n bien formados.
   * @param dir Ruta que se desea validar.
   * @return boolean devuelve true si la ruta es v�lida, false en caso contrario
   */
  public static String validateAllXML(String dir) {

    String error = "";
    //primero validamos el fichero raiz xml
    if (!Model.Controller.validateFile(dir)) {
      error = "El fichero " + dir + ", no existe";
    } //si la ruta es correcta miramos si el archivo est� bien formado
    else {
      boolean bienformado = true;
      File archivo = new File(dir);
      //System.out.println("RUTAS::" + archivo.getAbsolutePath());
      try {
        bienformado = Model.Controller.wellFormedXML(archivo);
      }
      catch (JDOMException ex) {
      }
      catch (IOException ex) {
      }
      if (bienformado == false) {
        error = "El archivo " + dir + " no est� bien formado.\n Compruebe que el archivo cumple la sint�xis del lenguaje XML ";
      }
    }

    /*leemos el contenido del xml raiz para comprobar si las rutas de los ficheros
            existen y los ficheros est�n bien formados*/
    Vector vficherosXML = readBasicXML(dir);
    System.out.println("vector :" + vficherosXML.toString());

    String nomFich = "";
    for (int i = 0; i < vficherosXML.size(); i++) {
      nomFich = vficherosXML.get(i).toString();

      if (!Model.Controller.validateFile("./temp/" + nomFich)) {
        error = "El archivo " + nomFich + " no existe";
      } //si la ruta es correcta miramos si el archivo est� bien formado
      else {

        boolean bienformado = true;
        File archivoClase = new File("./temp/" + nomFich);
        try {
          bienformado = Model.Controller.wellFormedXML(archivoClase);
        }
        catch (JDOMException ex) {
        }
        catch (IOException ex) {
        }
        if (bienformado == false) {
          error = "El archivo " + nomFich + " no est� bien formado.\n Compruebe que el archivo cumple la sint�xis del lenguaje XML ";
        }
      }
    }
    return error;
  }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceproyecto1;

import estructura.Atributo;
import estructura.DocumentoJson;
import estructura.JsonStore;
import estructura.ListaEnlazadaDoble;
import estructura.ObjetoJson;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

/**
 *
 * @author luisk
 */
public class FXMLDocumentController implements Initializable {
    
    /**
     * declaracion de la lista principal y la variable para cargar los datos
     */
    ObjectInputStream leer;
    ListaEnlazadaDoble lista;
//    ListaEnlazadaDoble lista = new ListaEnlazadaDoble();
    
    /**
     * En este metodo se cargarantodos los datos desde memoria y se incertaran en el arbol, 
     * ademas se da al arbol la propiedad del ContextMenu al precionar click derecho que
     * se deplegara uno de los dos que se definieron dependiendo de si lo que se preciona 
     * es un JsonStore o un DocumentoJson
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @FXML
    private void cargarDatos() throws FileNotFoundException, IOException, ClassNotFoundException{
        leer = new ObjectInputStream(new FileInputStream("Jsons.txt"));
        lista = (ListaEnlazadaDoble) leer.readObject();
        labelListaStores.setText(lista.obtenerLista());
        int size = lista.size();
        TreeItem<String> raiz = new TreeItem<>("Raiz");
        if(size != 0){
            JsonStore temp = lista.obtener(0);
            for(int i = 0;i < size;i++){
                TreeItem<String> nodo = new TreeItem<>(temp.obtenerNombre(),new ImageView(carpeta));
                nodo.setExpanded(true);
                raiz.getChildren().add(nodo);
                int tamaño = temp.obtenerLista().size();
                if(tamaño != 0){
                    DocumentoJson temporal = temp.obtenerLista().obtener(0);
                    for(int in = 0;in < tamaño;in++){
                        TreeItem<String> subNodo = new TreeItem<>(temporal.obtenerNombre(),new ImageView(archivo));
                        subNodo.setExpanded(true);
                        nodo.getChildren().add(subNodo);
                        System.out.println(nodo.getParent());
                        System.out.println(subNodo.getParent());
                        temporal = temporal.obtenerSiguiente();
                    }
                }
               temp = temp.obtenerSiguiente(); 
            }
        }
        arbol.setRoot(raiz);
        raiz.setExpanded(true);
        arbol.setShowRoot(false);
        arbol.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                TreeItem<String> item = arbol.getSelectionModel().getSelectedItem();
                if(t.getButton() == MouseButton.SECONDARY)
                    if(item.getParent().getValue().equals("Raiz")){
                        System.out.println("Es un Store");
                        buscarJsonNombre2(item.getValue());
                        arbol.setContextMenu(menuDerecho);
                    }else{
                        System.out.println("Es un Documento");
                        buscarJsonNombre2(item.getParent().getValue());
                        buscarDocumentoJson2(item.getValue());
                        arbol.setContextMenu(menuDerechoDoc);
                    }
            }
        });
        btninsertarJson.setDisable(false);
        btnBuscarJson.setDisable(false);
    }
    
    
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void goCrearJson(ActionEvent e){
        fondoCrearJson.setVisible(true);
        fondoPrincipal.setVisible(false);
        btninsertarJson.setDisable(false);
        btnBuscarJson.setDisable(false);
    }
    
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void ingresarJson(ActionEvent e){
        if(txtNombreJsonField.getText().equals("")){
            labelMensajeCrearJson.setText("No se ingreso ningun nombre, vuelve a intentar");
        }else{
            if(lista.add(txtNombreJsonField.getText()).equals("Store existente")){
                labelMensajeCrearJson.setText("Store Existente");
            }else{
                labelMensajeCrearJson.setText("Se creo el Store "+txtNombreJsonField.getText());
                TreeItem<String> raiz = arbol.getRoot();
                TreeItem<String> item = new TreeItem<>(txtNombreJsonField.getText(),new ImageView(carpeta));
                raiz.getChildren().add(item);
                labelListaStores.setText(lista.obtenerLista());
            }
        }
    }
    
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void cancelarCrearJson(ActionEvent e){
        fondoCrearJson.setVisible(false);
        fondoPrincipal.setVisible(true);
        labelMensajeCrearJson.setText("");
        txtNombreJsonField.setText("");
    }
    
    
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void goInsertarJson(ActionEvent e){
        fondoInsertarJson1.setVisible(true);
        fondoPrincipal.setVisible(false);
        btnVerificarInsertarJson.setDisable(false);
        btnInsertarJson2.setDisable(false);
    }
    
    /**
     * Verifica que el indice ingresado sea valido
     * @param e 
     */
    @FXML
    private void goInsertarJson2(){
        int ind = 0;
        String IND = txtIndInsertJsonField.getText();
        try{
            ind = Integer.parseInt(IND);
        }catch(NumberFormatException e){
            labelInsertarJson1.setText("No se introdujo un valor valido,vuelve a intentar!");
            return;
        }
        if(ind < lista.size()){
            fondoInsertarJson2.setVisible(true);
            txtIndInsertJsonField.setDisable(true);
            labelInsertarJson1.setText("Verificado");
            btnVerificarInsertarJson.setDisable(true);
        }else{
            labelInsertarJson1.setText("Indice no valido, vuelve a intentar!");
        }
    }
    
    /**
     * invoca los metodos para insetar un nuevo JsonStore creado en el indice indicado
     * @param e 
     */
    @FXML
    private void goInsertarJson3(){
        if(txtIndInsertNombreJsonField.getText().equals("")){
            labelInsertarJson2.setText("No se intrdujo ningun valor");
        }else{
            int ind = 0;
            String IND = txtIndInsertJsonField.getText();
            try{
                ind = Integer.parseInt(IND);
            }catch(NumberFormatException e){
                labelInsertarJson1.setText("No se introdujo un valor valido,vuelve a intentar!");
                return;
            }
            if(ind<(lista.size())){
                if(lista.insertar(ind,txtIndInsertNombreJsonField.getText()).equals("Store existente")){
                    labelInsertarJson2.setText("Store Existente");
                    return;
                }else{
                    TreeItem<String> raiz = arbol.getRoot();
                    TreeItem<String> item = new TreeItem<>(txtIndInsertNombreJsonField.getText(),new ImageView(carpeta));
                    raiz.getChildren().add(item);
                }
            }
            btnVerificarInsertarJson.setDisable(true);
            btnInsertarJson2.setDisable(true);
            btnContinuarInsertarJson.setDisable(false);
            labelListaStores.setText(lista.obtenerLista());
        }
    }
    
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void cancelarInsertarJson(ActionEvent e){
        txtIndInsertJsonField.setText("");
        labelInsertarJson2.setText("");
        txtIndInsertNombreJsonField.setText("");
        labelInsertarJson1.setText("");
        fondoInsertarJson1.setVisible(false);
        fondoInsertarJson2.setVisible(false);
        fondoPrincipal.setVisible(true);
    }
    
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void goBuscarJson(ActionEvent e){
        fondoBuscarJson.setVisible(true);
        fondoPrincipal.setVisible(false);
        btnBuscarJsonNombre.setDisable(false);
        btnBuscarJsonIndice.setDisable(false);
    }
    
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void goBuscarJsonIndice(ActionEvent e){
        btnBuscarJsonNombre.setDisable(true);
        btnBuscarJsonIndice.setDisable(true);
        fondoBuscarJsonIndice.setVisible(true);
    }
    
    /**
     * variable que va a guardar el JsonStore que estemos utilizando en ejecucion
     */
    JsonStore buscado;
    
    /**
     * Verifica que el indice ingresado sea valido
     * @param e 
     */
    @FXML
    private void buscarJsonIndice(){
        int ind;
        String IND = txtBuscarIndiceField.getText();
        try{
            ind = Integer.parseInt(IND);
        }catch(NumberFormatException e){
            labelBuscarJsonIndice.setText("No se introdujo un valor valido,vuelve a intentar!");
            return;
        }
        if(ind < lista.size()){
            buscado = lista.obtener(ind);
            labelBuscarJsonIndice.setText(buscado.obtenerNombre());
            btnIngresarEnJsonStore.setDisable(false);
            
        }else{
            labelBuscarJsonIndice.setText("Indice no valido, vuelve a intentar!");
        }
    }
    
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void goBuscarJsonNombre(ActionEvent e){
        btnBuscarJsonNombre.setDisable(true);
        btnBuscarJsonIndice.setDisable(true);
        fondoBuscarJsonNombre.setVisible(true);
        
    }
    
    /**
     * Invoca al metodo que obtiene por nombre y valida si existe o no.
     * @param e 
     */
    @FXML
    private void buscarJsonNombre(ActionEvent e){
        if(txtbucarJsonNombreField.getText().equals("")){
            labelBuscarJsonNombre.setText("No se introdujo un valor");
        }else{
            String nom = txtbucarJsonNombreField.getText();
            if(lista.obtener(nom) == null){
                labelBuscarJsonNombre.setText("No existe un JsonStore con ese nombre");
            }else{
                buscado = lista.obtener(nom);
                labelBuscarJsonNombre.setText(nom);
                btnIngresarJsonNombre.setDisable(false);
            }
        }
    }
    
    @FXML
    private void buscarJsonNombre2(String nom){
            buscado = lista.obtener(nom);
    }
    
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void goIngresarJsonStore(ActionEvent e){
        labelPricipalMenuJsonStore.setText("Menu de "+buscado.obtenerNombre());
        fondoBuscarJson.setVisible(false);
        fondoBuscarJsonIndice.setVisible(false);
        fondoBuscarJsonNombre.setVisible(false);
        txtBuscarIndiceField.setText("");
        txtbucarJsonNombreField.setText("");
        btnIngresarEnJsonStore.setDisable(true);
        btnIngresarJsonNombre.setDisable(true);
        labelBuscarJsonIndice.setText("");
        labelBuscarJsonNombre.setText("");
        fondoMenuJsonStore.setVisible(true);
        labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista());
    }
    
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void atrasmenuJsonStore(){
        fondoMenuJsonStore.setVisible(false);
        fondoPrincipal.setVisible(true);
    }
    
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void cancelarBusqueda(ActionEvent e){
        fondoBuscarJsonIndice.setVisible(false);
        fondoBuscarJsonNombre.setVisible(false);
        fondoBuscarJson.setVisible(false);
        txtBuscarIndiceField.setText("");
        txtbucarJsonNombreField.setText("");
        btnIngresarEnJsonStore.setDisable(true);
        btnIngresarJsonNombre.setDisable(true);
        labelBuscarJsonNombre.setText("");
        labelBuscarJsonIndice.setText("");
        fondoPrincipal.setVisible(true);
        
    }
    
    /**
     * Se introduce el nombre del Store que se dea eliminar
     * @param nom 
     */
    private void eliminarNombreJsonDef(String nom){
        lista.eliminar(nom);
        labelListaStores.setText(lista.obtenerLista());
    }
    
    
    
    
    
    
    
    
    
    //Aqui terminan los controles principales
    //--------------------------------------------------------------------------------
    //Aqui inician los controles para manipular DocumentosJson
    
    
    
    
    
    
    
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void goCrearDocumentoJson(){
        labelCrearDocumentoJson.setText("");
        txtCrearDocumentoJsonField.setText("");
        fondoPrincipal.setVisible(false);
        fondoInsertarJson1.setVisible(false);
        fondoInsertarJson2.setVisible(false);
        fondoCrearJson.setVisible(false);
        fondoMenuJsonStore.setVisible(false);
        fondoBuscarJson.setVisible(false);
        fondoBuscarJsonNombre.setVisible(false);
        fondoBuscarJsonIndice.setVisible(false);
        fondoBuscarDocumento1.setVisible(false);
        fondoBuscarIndiceDocumento.setVisible(false);
        fondoBuscarNombreDocumento.setVisible(false);
        fondoCrearObjeto.setVisible(false);
        fondoConsultaObjeto.setVisible(false);
        fondoBuscarObjeto.setVisible(false);
        fondoBuscarLlavePrim.setVisible(false);
        fondoEliminarObjeto.setVisible(false);
        fondoBuscarAtributo.setVisible(false);
        fondoCrearDocumentoJson.setVisible(true);
        btnDefinirAtributo.setDisable(true);
        btnCrearDocumento.setDisable(false);
        labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista());
    }
    
    /**
     * En esta variable se almacenara el DocumentoJson que estemos manipulando en ejecucion
     */
    private DocumentoJson buscadoDoc;
    
    /**
     * Se asigna a buscadoDoc el documentoJson con el nombre del item que se selecciono en el arbol
     * @param nom 
     */
    private void buscarDocumentoJson2(String nom){
        buscadoDoc = buscado.obtenerLista().obtener(nom);
    }
    
    /**
     * Invoca el metodo para crear un DocumentoJson y valida si existe
     * @param e 
     */
    @FXML
    private void crearDocumentoJson(ActionEvent e){
        if(txtCrearDocumentoJsonField.getText().equals("")){
            labelCrearDocumentoJson.setText("No se introdujo un nombre, vuelve a intentar");
        }else{
            labelCrearDocumentoJson.setText(buscado.obtenerLista().add(txtCrearDocumentoJsonField.getText()));
            labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista());
            buscadoDoc = buscado.obtenerLista().obtener(txtCrearDocumentoJsonField.getText());
            if(labelCrearDocumentoJson.getText().equals("Documento existente")){
            }else{
                btnDefinirAtributo.setDisable(false);
                btnCrearDocumento.setDisable(true);
                TreeItem<String> item = arbol.getSelectionModel().getSelectedItem();
                TreeItem<String> subItem = new TreeItem<>(txtCrearDocumentoJsonField.getText(),new ImageView(archivo));
                item.setExpanded(true);
                item.getChildren().add(subItem);
            
            }
        }
    }
    
    /**
     * variable que se aumentara cada vez que se ejecute el maetodo definirAtributo() 
     */
    int cont = 0;
    
    /**
     * Se controlara que dependiendo las opciones marcadas y los valores ingresados 
     * en la interface se defina corectamente el atributo con dichos valores
     * @param e 
     */
    @FXML
    private void definirAtributo(ActionEvent e){
        if(txtNombreAtributoField.getText().equals("")){
            labelDefAtributo.setText("No se introdujo ningun valor");
            return;
        }else{
        String nom = txtNombreAtributoField.getText();
        String valorDef = txtValorDefAtributoField.getText();
        String tipo;
        String tipoEsp = null;
        boolean requerido = true;
        String predeterminado = null;
        if(boxTipoAtributo.getValue()== null){
            tipo = "cadena";
        }else{
            tipo = boxTipoAtributo.getValue();
        }
        if(rbTipoPrimaria.isSelected()){
            tipoEsp = "llavePrimaria";
            requerido = true;
        }else{
            if(rbTipoNinguno.isSelected()){
                tipoEsp = "";
            }else if(rbTipoForanea.isSelected()){
                tipoEsp = "llaveForanea";
            }
            if(rbReqSi.isSelected()){
            requerido = true;
            predeterminado = "";
            }else if(rbReqNo.isSelected()){
            requerido = false;
            predeterminado = txtValorDefAtributoField.getText();
            }
        }
        buscadoDoc.obtenerLista().addAtributo(nom, tipo, tipoEsp, requerido, predeterminado);
        labelDefAtributo.setText("Se creo el atributo "+nom);
        buscadoDoc.obtenerLista().verAtributos();
        buscadoDoc.obtenerLista().obtenerAtributo(cont).verCaracteristicas();
        txtNombreAtributoField.setText("");
        cont++;
        }
    }
    
    /**
     * Cambia de escenario
     * @param e 
     */
    @FXML
    private void atrasCrearDocumento(ActionEvent e){
        fondoCrearDocumentoJson.setVisible(false);
        fondoMenuJsonStore.setVisible(true);
        txtCrearDocumentoJsonField.setText("");
        labelCrearDocumentoJson.setText("");
    }
    
    /**
     * Cambia de escenario
     * @param e 
     */
    @FXML
    private void goBuscarDocumentoJson(ActionEvent e){
        btnBuscarDocumentoIndice.setDisable(false);
        btnBuscarDocumentoNombre.setDisable(false);
        fondoBuscarDocumento1.setVisible(true);
        fondoMenuJsonStore.setVisible(false);
        txtBuscarIndiceDocumento.setText("");
        txtBuscarDocumentoNombreField.setText("");
    }
    
    /**
     * Cambia de escenario
     * @param e 
     */
    @FXML
    private void atrasBuscarDocumento(ActionEvent e){
        fondoBuscarDocumento1.setVisible(false);
        fondoBuscarIndiceDocumento.setVisible(false);
        fondoBuscarNombreDocumento.setVisible(false);
        btnIngresarDocumentoInd.setDisable(true);
        btnIngresarDocumentoNombre.setDisable(true);
        fondoMenuJsonStore.setVisible(true);
    }
    
    
    /**
     * Cambia de escenario
     * @param e 
     */
    @FXML
    private void buscarDocumentoIndice(ActionEvent e){
        fondoBuscarIndiceDocumento.setVisible(true);
        btnBuscarDocumentoIndice.setDisable(true);
        btnBuscarDocumentoNombre.setDisable(true);
        btnBuscarDocumentoIndice2.setDisable(false);
        labelBuscarDocumentoIndice.setText("");
    }
    
    /**
     * Va pidiendo uno a uno los valores que se deben ingresar en los atributos defininidos 
     * @param e 
     */
    @FXML
    private void goBuscarDocumentoIndice(ActionEvent e){
        int ind;
        String IND = txtBuscarIndiceDocumento.getText();
        ind = Integer.parseInt(IND);
        if(ind < (buscado.obtenerLista().size())){
            buscadoDoc = buscado.obtenerLista().obtener(ind);
            labelBuscarDocumentoIndice.setText(buscadoDoc.obtenerNombre());
            btnBuscarDocumentoIndice2.setDisable(true);
            btnIngresarDocumentoInd.setDisable(false);
        }else{
            labelBuscarDocumentoIndice.setText("Indice no valido, vuelve a intentar");
        }
    }
    
    /**
     * Cambia de escenario
     * @param e 
     */
    @FXML
    private void buscarDocumentoNombre(ActionEvent e){
        fondoBuscarNombreDocumento.setVisible(true);
        btnBuscarDocumentoNombre2.setDisable(false);
        btnBuscarDocumentoIndice.setDisable(true);
        btnBuscarDocumentoNombre.setDisable(true);
        labelBuscarDocumentoNombre.setText("");
    }
    
    /**
     * Verifica que el DocumentoJson que se busca exista
     * @param e 
     */
    @FXML
    private void goBuscarDocumentoNombre(ActionEvent e){
        String nom = txtBuscarDocumentoNombreField.getText();
        if(buscado.obtenerLista().obtener(nom) == null){
            labelBuscarDocumentoNombre.setText("No existe un DocumentoJson con ese nombre");
        }else{
            buscadoDoc = buscado.obtenerLista().obtener(nom);
            labelBuscarDocumentoNombre.setText(nom);
            btnIngresarDocumentoNombre.setDisable(false);
        }
    }
    
    /**
     * Se introduce el nombre del DocumentoJson a eliminar
     * @param nom 
     */
    private void eliminarDocNomDef(String nom){
        buscado.obtenerLista().eliminar(nom);
        labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista());
    }
    
    
    
    
    //Aqui terminan los controles para manipular DocumentosJso
//----------------------------------------------------------------------------------------
    //Aqui inician los controles para manipular ObjetosJson
    
    
    
    
    
    
    /**
     * En esta variable se guardara el atributo que se este manipulando
     */
    private Atributo atributo;
    
    /**
     * Cambia de escenario y 
     */
    @FXML
    private void crearObjeto(){
        fondoInsertarJson1.setVisible(false);
        fondoInsertarJson2.setVisible(false);
        fondoCrearJson.setVisible(false);
        fondoMenuJsonStore.setVisible(false);
        fondoBuscarJson.setVisible(false);
        fondoBuscarJsonNombre.setVisible(false);
        fondoBuscarJsonIndice.setVisible(false);
        fondoBuscarDocumento1.setVisible(false);
        fondoBuscarIndiceDocumento.setVisible(false);
        fondoBuscarNombreDocumento.setVisible(false);
        fondoBuscarObjeto.setVisible(false);
        fondoBuscarLlavePrim.setVisible(false);
        fondoBuscarAtributo.setVisible(false);
        fondoCrearDocumentoJson.setVisible(false);
        fondoEliminarObjeto.setVisible(false);
        fondoCrearObjeto.setVisible(true);
        fondoConsultaObjeto.setVisible(false);
        fondoPrincipal.setVisible(false);
        btnContinuarObjeto.setDisable(true);
        btnInsertarUnObjeto.setDisable(false);
        txtConsultaObjetosArea.setText("");
        atributo = buscadoDoc.obtenerLista().obtenerCabeza();
        labelCrearObjeto1.setText("Ingrese el valor de "+atributo.obtenerNombre());
    }
    
    /**
     * Se toma el valor ingresado en el campo de texto y se intorduce dentro de la lista de 
     * ObjetosJson que se encuentra dentro del atributo que se esta manipulando
     */
    @FXML
    private void crearObjetoDef(){
        if(txtCrearObjetoField.getText().equals("")){
            if(!atributo.requerido()){
                atributo.obtenerLista().add(atributo.valPorDefecto());
                System.out.println(atributo.obtenerLista().obtenerLista());
                atributo = atributo.obtenerSiguiente();
                if(atributo != null){
                    labelCrearObjeto1.setText("Ingrese el valor de "+atributo.obtenerNombre());
                    labelCrearObjeto.setText("");
                }else{
                    btnContinuarObjeto.setDisable(false);
                    btnInsertarUnObjeto.setDisable(true);
                }
                return;
            }else{
            labelCrearObjeto.setText("No se introdujo ningun valor");
            return;
            }
        }
        if(atributo.obtenerTipo().equals("entero")){
            int valor;
            try{
            valor = Integer.parseInt(txtCrearObjetoField.getText());
            }catch(NumberFormatException e){
                labelCrearObjeto.setText("Introdusca un valor numerico");
                return;
            }
        }
        if(atributo.obtenerTipoEspecial().equals("llavePrimaria")){
            if(atributo.obtenerLista().addComoLlave(txtCrearObjetoField.getText()).equals("existe")){
                labelCrearObjeto.setText("Llave primaria existente");
                return;
            }
        }else{
            atributo.obtenerLista().add(txtCrearObjetoField.getText());
        }
            System.out.println(atributo.obtenerLista().obtenerLista());
            atributo = atributo.obtenerSiguiente();
        if(atributo != null){
            labelCrearObjeto1.setText("Ingrese el valor de "+atributo.obtenerNombre());
            labelCrearObjeto.setText("");
        }else{
            btnContinuarObjeto.setDisable(false);
            btnInsertarUnObjeto.setDisable(true);
        }
        txtCrearObjetoField.setText("");
    }
    
    
    /**
     * En este metodo se crean las columnas con los Atributos pertenecientes al DocumentoJson 
     * que se esta manipulando en ese momento y se introducen los datos que esten registrados 
     * dentro de las celdas de la tabla y tambien se crea la JsonView que se observa en pantalla
     */
    @FXML
    private void goConsultaObjeto(){
        txtConsultaObjetosArea.setText("{\n\r");
        fondoPrincipal.setVisible(false);
        fondoInsertarJson1.setVisible(false);
        fondoInsertarJson2.setVisible(false);
        fondoCrearJson.setVisible(false);
        fondoMenuJsonStore.setVisible(false);
        fondoBuscarJson.setVisible(false);
        fondoBuscarJsonNombre.setVisible(false);
        fondoBuscarJsonIndice.setVisible(false);
        fondoBuscarDocumento1.setVisible(false);
        fondoBuscarIndiceDocumento.setVisible(false);
        fondoBuscarNombreDocumento.setVisible(false);
        fondoBuscarObjeto.setVisible(false);
        fondoBuscarLlavePrim.setVisible(false);
        fondoBuscarAtributo.setVisible(false);
        fondoCrearDocumentoJson.setVisible(false);
        fondoConsultaObjeto.setVisible(true);
        fondoEliminarObjeto.setVisible(false);
        fondoCrearObjeto.setVisible(false);
        btnEliminarObjeto.setDisable(true);
        Atributo actual;
        int contador = 0;
        int ind = buscadoDoc.obtenerLista().obtenerCabeza().obtenerLista().size();
        data.clear();
        tabla.getItems().clear();
        tabla.getColumns().clear();
        actual = buscadoDoc.obtenerLista().obtenerCabeza();
        int indice = 0;
        while(actual!= null){
            TableColumn<List<String>, String> columna = new TableColumn();
            if(actual.obtenerTipoEspecial().equals("llavePrimaria")){
                columna.setText(actual.obtenerNombre()+"(Llave Prim)");
            }else{
                columna.setText(actual.obtenerNombre());
            }
            int colIndex = indice ;
            columna.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().get(colIndex)));
            tabla.getColumns().add(columna);
            actual = actual.obtenerSiguiente();
            indice++;
        }
        for(int i = 0;i < ind;i++){
            actual = buscadoDoc.obtenerLista().obtenerCabeza();
            String registro = "";
            List<String> row = new ArrayList<String>();
            while(actual != null){
                ObjetoJson temp = actual.obtenerLista().obtener(contador);
                row.add(temp.obtenerValor()+"");
                registro += (actual.obtenerNombre()+":"+temp.obtenerValor()+",");
                actual = actual.obtenerSiguiente();
            }
            data.add(row);
            txtConsultaObjetosArea.appendText("{"+registro+"},"+"\n\r");
            contador++;
        }
        txtConsultaObjetosArea.appendText("}");
        ObservableList<List<String>> inpData = FXCollections.observableArrayList(data);
        tabla.setItems(inpData); 
    }
    
    /**
     * Variable tabla para la table view y variable data en donde se almacenaran los 
     * datos que se obcervaran en una fila de la tabla.
     */
    @FXML
    private TableView<List<String>> tabla = new TableView<>();
    List<List<String>> data = new ArrayList<List<String>>();
    
    /**
     * Cambia de la TableView a la JsonView
     */
    @FXML
    private void vistaTablaJson(){
        if(rbTableView.isSelected()){
            tabla.setVisible(true);
        }else{
            tabla.setVisible(false);
        }
    }
    
    /**
     * Cambia de escenario
     * @param e 
     */
    @FXML
    private void atrasConsultaObjeto(ActionEvent e){
        fondoConsultaObjeto.setVisible(false);
        fondoPrincipal.setVisible(true);
    }
    
    /**
     * Cambia de escenario
     */
    @FXML
    private void goBuscarObjeto(){
        fondoPrincipal.setVisible(false);
        fondoInsertarJson1.setVisible(false);
        fondoInsertarJson2.setVisible(false);
        fondoCrearJson.setVisible(false);
        fondoMenuJsonStore.setVisible(false);
        fondoBuscarJson.setVisible(false);
        fondoBuscarJsonNombre.setVisible(false);
        fondoBuscarJsonIndice.setVisible(false);
        fondoBuscarDocumento1.setVisible(false);
        fondoBuscarIndiceDocumento.setVisible(false);
        fondoBuscarNombreDocumento.setVisible(false);
        fondoBuscarLlavePrim.setVisible(false);
        fondoBuscarAtributo.setVisible(false);
        fondoCrearDocumentoJson.setVisible(false);
        fondoEliminarObjeto.setVisible(false);
        fondoCrearObjeto.setVisible(false);
        fondoBuscarObjeto.setVisible(true);
        fondoConsultaObjeto.setVisible(false);
        btnBuscarLLavePrim.setDisable(false);
        btnBuscarAtributo.setDisable(false);
    }
    
    /**
     * Cambia de escenario
     * @param e 
     */
    @FXML
    private void goBuscarLlavePrim(ActionEvent e){
        btnBuscarAtributo.setDisable(true);
        btnBuscarLLavePrim.setDisable(true);
        fondoBuscarLlavePrim.setVisible(true);
        txtBuscarLlavePrimField.setText("");
        txtBuscarLlavePrimArea.setText("");
    }
    
    /**
     * Se encarga de verificar que el valor que se ingeso se encuentre en la lista enlazada
     * y que ademas sea llave primaria para mostrarlo en pantalla
     * @param e 
     */
    @FXML
    private void buscarLlavePrim(ActionEvent e){
        if(txtBuscarLlavePrimField.getText().equals("")){
            txtBuscarLlavePrimArea.setText("No se itrodujo ningun valor");
        }else{
        Atributo actual = buscadoDoc.obtenerLista().obtenerCabeza();
        while(actual != null){
            if(actual.obtenerTipoEspecial().equals("llavePrimaria")){
                int indice = actual.obtenerLista().obtenerIndice(txtBuscarLlavePrimField.getText());
                if(indice != -1){
                    String registro = "";
                    actual = buscadoDoc.obtenerLista().obtenerCabeza();
                    while(actual != null){
                        ObjetoJson temp = actual.obtenerLista().obtener(indice);
                        registro += (actual.obtenerNombre()+":"+temp.obtenerValor()+",");
                        actual = actual.obtenerSiguiente();
                    }
                    txtBuscarLlavePrimArea.setText("{"+registro+"}");
                    return;
                }else{
                    txtBuscarLlavePrimArea.setText("No existe un ObjetoJson con esa llave primaria");
                    return;
                }
            }else{
                actual = actual.obtenerSiguiente();
            }
        }
        txtBuscarLlavePrimArea.setText("No existe una llave primaria en este Documento");
        }
    }
    
    /**
     * Cambio de escenario
     * @param e 
     */
    @FXML
    private void goBuscarAtributo(ActionEvent e){
        btnBuscarLLavePrim.setDisable(true);
        btnBuscarAtributo.setDisable(true);
        fondoBuscarAtributo.setVisible(true);
    }
    
    /**
     * Se toma el valor ingresado en el campo de texto y se procede a verificar que
     * ese valor se encuentre registrado en alguna de las listas que se encuentran
     * almacenadoas en los atributos pertenecientes al DocumentoJson que se esta consultando
     * y se muestran todos los registros que contengan este dato
     * @param e 
     */
    @FXML
    private void buscarAtributo(ActionEvent e){
        if(txtBuscarAtributoField.getText().equals("")){
            txtBuscarAtributoArea.setText("No se introdujo un valor");
        }else{
        txtBuscarAtributoArea.setText("");
        Atributo actual = buscadoDoc.obtenerLista().obtenerCabeza();
        Atributo temporal;
        int contador = 0;
        int indice = 0;
        while(actual != null){
            System.out.println(contador);
            indice = actual.obtenerLista().obtenerIndices(txtBuscarAtributoField.getText(),contador);
            System.out.println(contador);
            temporal = actual;
            if(indice != -1){
                String registro = "";
                actual = buscadoDoc.obtenerLista().obtenerCabeza();
                while(actual != null){
                    ObjetoJson temp = actual.obtenerLista().obtener(indice);
                    registro += (actual.obtenerNombre()+":"+temp.obtenerValor()+",");
                    actual = actual.obtenerSiguiente();
                }
                txtBuscarAtributoArea.appendText("{"+registro+"}"+"\n\r");
                System.out.println(registro);
                contador += (indice+1);
                System.out.println(contador);
                actual = temporal;
            }else{
                actual = actual.obtenerSiguiente();
                contador = 0;
            }
        }
        }
    }    
            
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void goEliminarObjeto(ActionEvent e){
        fondoConsultaObjeto.setVisible(false);
        fondoEliminarObjeto.setVisible(true);
        btnEliminarObjeto.setDisable(true);
        txtConsultaObjetosArea.setText("");
    }
    
    /**
     * Variable que 
     */
    int indi;
    
    /**
     * Este metodo se encarga de consultar si si existe un Atributo que cuya variable TipoEspecial
     * sea llave primaria y consulta si el valor ingresado existe y muestra los demas datos de este
     * registro en pantalla
     * @param e 
     */
    @FXML
    private void verificarEliminarObjeto(ActionEvent e){
        Atributo actual = buscadoDoc.obtenerLista().obtenerCabeza();
        while(actual != null){
            if(actual.obtenerTipoEspecial().equals("llavePrimaria")){
                indi = actual.obtenerLista().obtenerIndice(txtEliminarObjetoField.getText());
                if(indi != -1){
                    String registro = "";
                    actual = buscadoDoc.obtenerLista().obtenerCabeza();
                    while(actual != null){
                        ObjetoJson temp = actual.obtenerLista().obtener(indi);
                        registro += (actual.obtenerNombre()+":"+temp.obtenerValor()+",");
                        actual = actual.obtenerSiguiente();
                    }
                    txtEliminarObjetoArea.setText("{"+registro+"}");
                    btnEliminarObjeto.setDisable(false);
                    System.out.println(indi);
                    return;
                }else{
                    txtEliminarObjetoArea.setText("No existe un ObjetoJson con esa llave primaria");
                    return;
                }
            }else{
                actual = actual.obtenerSiguiente();
            }
        }
        txtEliminarObjetoArea.setText("No existe una llave primaria en este Documento");
    }
    
    /**
     * Se eliminara definitivamente el registro consultado del documento
     * @param e 
     */
    @FXML
    private void eliminarObjetoDef(ActionEvent e){
        Atributo actual = buscadoDoc.obtenerLista().obtenerCabeza();
        while(actual != null){
            actual.obtenerLista().eliminar(indi);
            actual = actual.obtenerSiguiente();
            System.out.println(indi);
        }
        txtEliminarObjetoArea.setText("Eliminado!!");
        btnEliminarObjeto.setDisable(true);
    }
    
    /**
     * En este metodo permitira eliminar todos los datos contenidos dentro
     * de cata atributo
     * @param e 
     */
    @FXML
    private void eliminarTodosObjetos(ActionEvent e){
        Atributo actual = buscadoDoc.obtenerLista().obtenerCabeza();
        while(actual != null){
            while(actual.obtenerLista().estaVacio() != true){
                actual.obtenerLista().eliminar(0);
            }
            actual = actual.obtenerSiguiente();
        }
        goConsultaObjeto();
    }
    
    
    /**
     * Este metodo permitira guardar los datos en un archivo de texto de manera
     * que se ingresara toda la estructura en este
     * @param e
     * @throws FileNotFoundException
     * @throws IOException 
     */
    @FXML
    private void comit(ActionEvent e) throws FileNotFoundException, IOException{
        FileOutputStream out = new FileOutputStream("Jsons.txt");
        ObjectOutputStream salida = new ObjectOutputStream(out);

        salida.writeObject(lista);
        salida.flush();
    }
    
    /**
     * TreeView
     */
    @FXML
    private TreeView<String> arbol;
    
    /**
     * Menu de JsonStore para clic derecho en arbol
     */
    private ContextMenu menuDerecho = new ContextMenu();
    private MenuItem itemJ1 = new MenuItem("Eliminar");
    private MenuItem itemJ3 = new MenuItem("Crear Documento");
    
    /**
     * Menu de DocumentoJson  para clic derecho en arbol
     */
    private ContextMenu menuDerechoDoc = new ContextMenu();
    private MenuItem itemD1 = new MenuItem("Eliminar");
    private MenuItem itemD3 = new MenuItem("Crear Objeto");
    private MenuItem itemD4 = new MenuItem("Ver Objetos");
    private MenuItem itemD5 = new MenuItem("Buscar Objeto");
    
    /**
     * Botones de la interface del sistema
     */
    //Botones pagina principal
    @FXML
    private Button btninsertarJson;
    
    @FXML
    private Button btnBuscarJson;
    
    @FXML
    private Button comitSave;
    
    //Botones para insertar json
    @FXML
    private Button btnInsertarJson2;
    
    @FXML
    private Button btnVerificarInsertarJson;
    
    @FXML
    private Button btnContinuarInsertarJson;
    
    //Botones buscar JsonStore
    @FXML
    private Button btnBuscarJsonNombre;
    
    @FXML
    private Button btnBuscarJsonIndice;
    
    @FXML
    private Button btnIngresarEnJsonStore;
    
    @FXML
    private Button btnIngresarJsonNombre;
    
    @FXML
    private Button btnEliminarIndiceJsonDefinitivo;
    
    //Botones DocumentoJson
    //Botones crear DocumentoJson
    @FXML
    private Button btnDefinirAtributo;
    @FXML
    private Button btnCrearDocumento;
    
    @FXML
    private Button btnVerificarEliminarNombreJson;
    
    //Botones para buscar DoumentoJson
    @FXML
    private Button btnBuscarDocumentoNombre;
    
    @FXML
    private Button btnBuscarDocumentoIndice;
    
    @FXML
    private Button btnBuscarDocumentoIndice2;
    
    @FXML
    private Button btnIngresarDocumentoInd;
    
    @FXML
    private Button btnBuscarDocumentoNombre2;
    
    @FXML
    private Button btnIngresarDocumentoNombre;
    
    //Botones para crear ObjetoJson
    @FXML
    private Button btnContinuarObjeto;
    
    @FXML
    private Button btnInsertarUnObjeto;
    
    //Botones para buscar ObjetoJson
    @FXML
    private Button btnBuscarLLavePrim;
    
    @FXML
    private Button btnBuscarAtributo;
    
    //Botones para eliminar ObjetosJson
    @FXML
    private Button btnEliminarObjeto;
    
    
    
    /**
     * Labels del sitema
     */
    @FXML
    private Label imprime;
    
    @FXML
    private Label labelMensajeCrearJson;
    
    @FXML
    private Label labelInsertarJson1;
    
    @FXML
    private Label labelInsertarJson2;
    
    @FXML
    public Label labelListaStores;
    
    @FXML
    public Label labelBuscarJsonIndice;

    @FXML
    public Label labelPricipalMenuJsonStore;
    
    @FXML
    public Label labelCrearDocumentoJson;
    
    @FXML
    public Label labelDefAtributo;
    
    @FXML
    public Label labelListaDocumentos;
    
    @FXML
    public Label labelBuscarJsonNombre;

    @FXML
    public Label labelBuscarDocumentoIndice;
    
    @FXML
    public Label labelBuscarDocumentoNombre;

    @FXML
    public Label labelCrearObjeto1;
    
    @FXML
    public Label labelCrearObjeto;
    
    
    
    
    /**
     * TextFields del Sistema
     */
    @FXML
    private TextField txtNombreJsonField;
    
    
    @FXML
    private TextField txtIndInsertJsonField;
    
    @FXML
    private TextField txtIndInsertNombreJsonField;
    
    
    @FXML
    private TextField txtBuscarIndiceField;
    
    
    @FXML
    private TextField txtCrearDocumentoJsonField;
    
    @FXML
    private TextField txtbucarJsonNombreField;
    
    
    @FXML
    private TextField txtBuscarIndiceDocumento;
    
    @FXML
    private TextField txtBuscarDocumentoNombreField;
    
    
    @FXML
    private TextField txtNombreAtributoField;
    
    @FXML
    private TextField txtValorDefAtributoField;
    
    
    @FXML
    private TextField txtCrearObjetoField;
    
    
    @FXML
    private TextField txtBuscarLlavePrimField;
    
    @FXML
    private TextField txtEliminarObjetoField;
    
    @FXML
    private TextField txtBuscarAtributoField;
    
    
    
    
    /**
     * Text areas del Sitema
     */
    @FXML
    private TextArea txtConsultaObjetosArea;
    
    @FXML
    private TextArea txtBuscarLlavePrimArea;
    
    @FXML
    private TextArea txtEliminarObjetoArea;
    
    @FXML
    private TextArea txtBuscarAtributoArea;
    
    
    
    /**
     * ComboBox para escojer el tipo del atributo y su lista observable
     */
    @FXML
    private ComboBox<String> boxTipoAtributo;
    
    ObservableList<String> comboLista = FXCollections.observableArrayList("entero","flotante","cadena","fechaHora");
    
    
    /**
     * Fondos de los diferentes escenarios
     */
    @FXML
    private Pane fondoPrincipal;
    
    @FXML
    private Pane fondoCrearJson;
    
    @FXML
    private Pane fondoInsertarJson1;
    
    @FXML
    private Pane fondoInsertarJson2;
    
    @FXML
    private Pane fondoBuscarJson;
    
    @FXML
    private Pane fondoBuscarJsonNombre;
    
    @FXML
    private Pane fondoBuscarJsonIndice;
    
    @FXML
    private Pane fondoMenuJsonStore;
    
    @FXML
    private Pane fondoCrearDocumentoJson;
    
    @FXML
    private Pane fondoBuscarDocumento1;
    
    @FXML
    private Pane fondoBuscarIndiceDocumento;
    
    @FXML
    private Pane fondoBuscarNombreDocumento;
    
    @FXML
    private Pane fondoCrearObjeto;
    
    @FXML
    private Pane fondoConsultaObjeto;
    
    @FXML
    private Pane fondoBuscarObjeto;
    
    @FXML
    private Pane fondoBuscarLlavePrim;
    
    @FXML
    private Pane fondoEliminarObjeto;
    
    @FXML
    private Pane fondoBuscarAtributo;
    
    
    /**
     * RadioButons del sistema
     */
    //Crear documento
    @FXML
    private RadioButton rbTipoForanea;
    
    @FXML
    private RadioButton rbTipoPrimaria;
    
    @FXML
    private RadioButton rbTipoNinguno;
    
    @FXML
    private RadioButton rbReqSi;
    
    @FXML
    private RadioButton rbReqNo;
    
    //Cambiar vista de los ObjetosJson
    @FXML
    private RadioButton rbTableView;
    
    @FXML
    private RadioButton  rbJsonView;
    
    
    /**
     * Imagenes de la interface
     */
    Image carpeta = new Image(getClass().getResourceAsStream("/Images/Carpeta.png"));
    
    Image archivo = new Image(getClass().getResourceAsStream("/Images/Archivo.png"));
    
    Image save = new Image(getClass().getResourceAsStream("/Images/save.png"));
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aqui es la primera ejecucion
        boxTipoAtributo.setItems(comboLista);
        System.out.println("Cargando");
        txtNombreJsonField.setText("");
        txtIndInsertJsonField.setText("");
        txtIndInsertNombreJsonField.setText("");
        comitSave.setGraphic(new ImageView(save));
        /**
         * dentro de este try catch se llama al metodo cargar datos que se encargara de traer 
         * todos los datos deste disco y llenar todas las estructuras con la informacion
         */
        try {
            cargarDatos();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /**
         * se agregan los items que se crearon a cada context menu
         */
        menuDerecho.getItems().addAll(itemJ1,new SeparatorMenuItem(),itemJ3);
        menuDerechoDoc.getItems().addAll(itemD1,new SeparatorMenuItem(),itemD3,itemD4,itemD5);
        
        /**
         * se le dan acciones a cada item de los context menu
         */
        itemJ1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TreeItem<String> item = arbol.getSelectionModel().getSelectedItem();
                eliminarNombreJsonDef(item.getValue());
                TreeItem c = (TreeItem)arbol.getSelectionModel().getSelectedItem();
                boolean remove = c.getParent().getChildren().remove(c);
                System.out.println("Remove");
            }
        });
        itemJ3.setOnAction(event ->{
            goCrearDocumentoJson();
        });
        itemD1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TreeItem<String> item = arbol.getSelectionModel().getSelectedItem();
                eliminarDocNomDef(item.getValue());
                TreeItem c = (TreeItem)arbol.getSelectionModel().getSelectedItem();
                boolean remove = c.getParent().getChildren().remove(c);
                System.out.println("Remove");
            }
        });
        itemD3.setOnAction(event ->{
            crearObjeto();
        });
        itemD4.setOnAction(event ->{
            goConsultaObjeto();
        });
        itemD5.setOnAction(event ->{
            goBuscarObjeto();
        });
    }
    
}

  
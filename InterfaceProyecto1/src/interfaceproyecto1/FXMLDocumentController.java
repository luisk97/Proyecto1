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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.TreeItem;
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
                TreeItem<String> nodo = new TreeItem<>(temp.obtenerNombre());
                nodo.setExpanded(true);
                raiz.getChildren().add(nodo);
                int tamaño = temp.obtenerLista().size();
                if(tamaño != 0){
                    DocumentoJson temporal = temp.obtenerLista().obtener(0);
                    for(int in = 0;in < tamaño;in++){
                        TreeItem<String> subNodo = new TreeItem<>(temporal.obtenerNombre());
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
    }
    
    /**
     * cambia de escenario
     * @param e 
     */
    @FXML
    private void ingresarJson(ActionEvent e){
        if(lista.add(txtNombreJsonField.getText()).equals("Store existente")){
            labelMensajeCrearJson.setText("Store Existente");
        }else{
            labelMensajeCrearJson.setText("Se creo el Store "+txtNombreJsonField.getText());
            TreeItem<String> raiz = arbol.getRoot();
            TreeItem<String> item = new TreeItem<>(txtNombreJsonField.getText());
            raiz.getChildren().add(item);
            labelListaStores.setText(lista.obtenerLista());
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
    private void goInsertarJson2(ActionEvent e){
        int ind;
        String IND = txtIndInsertJsonField.getText();
        ind = Integer.parseInt(IND);
        if(ind < lista.size()){
            fondoInsertarJson2.setVisible(true);
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
        int ind = 0;
        String IND = txtIndInsertJsonField.getText();
        try{
        ind = Integer.parseInt(IND);
        }catch(NumberFormatException e){
            labelInsertarJson2.setText("No se introdujo un un valor numerico");
            return;
        }
        if(ind<(lista.size())){
            labelInsertarJson2.setText(lista.insertar(ind,txtIndInsertNombreJsonField.getText()));
            TreeItem<String> raiz = arbol.getRoot();
            TreeItem<String> item = new TreeItem<>(txtIndInsertNombreJsonField.getText());
            raiz.getChildren().add(item);
        }
        btnVerificarInsertarJson.setDisable(true);
        btnInsertarJson2.setDisable(true);
        btnContinuarInsertarJson.setDisable(false);
        labelListaStores.setText(lista.obtenerLista());
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
    private void buscarJsonIndice(ActionEvent e){
        int ind;
        String IND = txtBuscarIndiceField.getText();
        ind = Integer.parseInt(IND);
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
        String nom = txtbucarJsonNombreField.getText();
        if(lista.obtener(nom) == null){
            labelBuscarJsonNombre.setText("No existe un JsonStore con ese nombre");
        }else{
            buscado = lista.obtener(nom);
            labelBuscarJsonNombre.setText(nom);
            btnIngresarJsonNombre.setDisable(false);
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
     * cambia de escenario
     * @param e 
     */
//    @FXML
//    private void goEliminarJson(ActionEvent e){
//        btneliminarIndiceJson.setDisable(false);
//        btnEliminarNombreJson.setDisable(false);
//        fondoEliminarStore.setVisible(true);
//        fondoPrincipal.setVisible(false);
//    }
    
    /**
     * cambia de escenario
     * @param e 
     */
//    @FXML
//    private void atrasEliminarJson(ActionEvent e){
//        fondoEliminarStore.setVisible(false);
//        fondoPrincipal.setVisible(true);
//        fondoEliminarIndiceJson.setVisible(false);
//        fondoEliminarNombreJson.setVisible(false);
//        labelEliminarIndiceJson.setText("");
//        labelEliminarJsonNombre.setText("");
//        btnVerificarEliminarIndJson.setDisable(false);
//        btnVerificarEliminarNombreJson.setDisable(false);
//        btnEliminarIndiceJsonDefinitivo.setDisable(true);
//        btnEliminarNombreJsonDefinitivo.setDisable(true);
//    }
    
    /**
     * cambia de escenario
     * @param e 
     */
//    @FXML
//    private void eliminarIndiceJson(ActionEvent e){
//        btneliminarIndiceJson.setDisable(true);
//        btnEliminarNombreJson.setDisable(true);
//        fondoEliminarIndiceJson.setVisible(true);   
//    }
    
    /**
     * Invoca el metodo de verificacion de indice de la ListaEnlazadaDoble y valida 
     * si existe o no el indice igresado
     * @param e 
     */
//    @FXML
//    private void verificarEliminarJson(ActionEvent e){
//        int ind;
//        String IND = txtEliminarIndiceField.getText();
//        ind = Integer.parseInt(IND);
//        if(ind < lista.size()){
//            labelEliminarIndiceJson.setText(lista.obtener(ind).obtenerNombre());
//            btnEliminarIndiceJsonDefinitivo.setDisable(false);
//            
//        }else{
//            labelEliminarIndiceJson.setText("Indice no valido, vuelve a intentar!");
//        }
//    }
    
    /**
     * invoca el metodo para eliminar un JsonStore
     * @param e 
     */
//    @FXML
//    private void eliminarJsonDefinitivo(ActionEvent e){
//        int ind;
//        String IND = txtEliminarIndiceField.getText();
//        ind = Integer.parseInt(IND);
//        lista.eliminar(ind);
//        labelEliminarIndiceJson.setText("Eliminado");
//        btnEliminarIndiceJsonDefinitivo.setDisable(true);
//        labelListaStores.setText(lista.obtenerLista());
//    }
    
    /**
     * cambia de escenario
     * @param e 
     */
//    @FXML
//    private void eliminarNombreJson(ActionEvent e){
//        btneliminarIndiceJson.setDisable(true);
//        btnEliminarNombreJson.setDisable(true);
//        fondoEliminarNombreJson.setVisible(true);
//    }
    
    /**
     * Invoca el metodo que devuel el JsonStore por nombre y valida si existe o no
     * @param e 
     */
//    @FXML
//    private void verificarEliminarNombreJson(ActionEvent e){
//        String nom = txtEliminarNombreJsonField.getText();
//        if(lista.obtener(nom) == null){
//            labelEliminarJsonNombre.setText("No existe un JsonStore con ese nombre");
//        }else{
//            labelEliminarJsonNombre.setText(nom);
//            btnEliminarNombreJsonDefinitivo.setDisable(false);
//        }
//    }
    
    /**
     * cambia de escenario
     * @param e 
     */
//    @FXML
//    private void eliminarNombreJsonDefinitivo(ActionEvent e){
//        String nom = txtEliminarNombreJsonField.getText();
//        labelEliminarJsonNombre.setText(lista.eliminar(nom));
//        labelListaStores.setText(lista.obtenerLista());
//        btnEliminarNombreJsonDefinitivo.setDisable(true);
//    }
    
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
     * 
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
        labelCrearDocumentoJson.setText(buscado.obtenerLista().add(txtCrearDocumentoJsonField.getText()));
        labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista());
        buscadoDoc = buscado.obtenerLista().obtener(txtCrearDocumentoJsonField.getText());
        if(labelCrearDocumentoJson.getText().equals("Documento existente")){
        }else{
            TreeItem<String> item = arbol.getSelectionModel().getSelectedItem();
            TreeItem<String> subItem = new TreeItem<>(txtCrearDocumentoJsonField.getText());
            item.setExpanded(true);
            item.getChildren().add(subItem);
            btnDefinirAtributo.setDisable(false);
            btnCrearDocumento.setDisable(true);
        }
    }
    
    /**
     * variable que se aumentara cada vez que se ejecute el maetodo definirAtributo() 
     */
    int cont = 0;
    
    /**
     * 
     * @param e 
     */
    @FXML
    private void definirAtributo(ActionEvent e){
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
    
    @FXML
    private void atrasCrearDocumento(ActionEvent e){
        fondoCrearDocumentoJson.setVisible(false);
        fondoMenuJsonStore.setVisible(true);
        txtCrearDocumentoJsonField.setText("");
        labelCrearDocumentoJson.setText("");
    }
    
    
    
//    @FXML
//    private void goInsertarDocumentoJson(ActionEvent e){
//        fondoInsertarDocumento1.setVisible(true);
//        fondoMenuJsonStore.setVisible(false);
//        btnVerificarInsertarDocumento.setDisable(false);
//    }
    
//    @FXML
//    private void verificarInsertarDocumento(){
//        int ind;
//        try{
//        ind = Integer.parseInt(txtIndInsertarDocumentoField.getText());
//        }catch(NumberFormatException e){
//            labelInsertarDocumento1.setText("No se introdujo un un valor numerico");
//            return;
//        }
//        if(ind > (buscado.obtenerLista().size()-1)){
//            labelInsertarDocumento1.setText("Indice no valido, vuelve a intentar");
//        }else{
//            labelInsertarDocumento1.setText("Verificado");
//            btnVerificarInsertarDocumento.setDisable(true);
//            btnInsertarDocumentoDefinitivo.setDisable(false);
//            fondoInsertarDocumento2.setVisible(true);
//        }    
//    }
    
//    @FXML
//    private void insertarDocumentoDefinitivo(ActionEvent e){
//        int ind;
//        String IND = txtIndInsertarDocumentoField.getText();
//        ind = Integer.parseInt(IND);
//        labelInsertarDocumento2.setText(buscado.obtenerLista().insertar(ind,txtInsertarDocumentoNombreField.getText()));
//        btnInsertarDocumentoDefinitivo.setDisable(true);
//        btnContinuarInsertarDocumento.setDisable(false);
//        labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista()); 
//    }
    
//    @FXML
//    private void atrasInsertarDocumento(ActionEvent e){
//        txtIndInsertarDocumentoField.setText("");
//        txtInsertarDocumentoNombreField.setText("");
//        labelInsertarDocumento1.setText("");
//        labelInsertarDocumento2.setText("");
//        fondoInsertarDocumento1.setVisible(false);
//        fondoInsertarDocumento2.setVisible(false);
//        btnContinuarInsertarDocumento.setDisable(true);
//        fondoMenuJsonStore.setVisible(true);
//    }
    
    
    
    @FXML
    private void goBuscarDocumentoJson(ActionEvent e){
        btnBuscarDocumentoIndice.setDisable(false);
        btnBuscarDocumentoNombre.setDisable(false);
        fondoBuscarDocumento1.setVisible(true);
        fondoMenuJsonStore.setVisible(false);
        txtBuscarIndiceDocumento.setText("");
        txtBuscarDocumentoNombreField.setText("");
    }
    
    @FXML
    private void atrasBuscarDocumento(ActionEvent e){
        fondoBuscarDocumento1.setVisible(false);
        fondoBuscarIndiceDocumento.setVisible(false);
        fondoBuscarNombreDocumento.setVisible(false);
        btnIngresarDocumentoInd.setDisable(true);
        btnIngresarDocumentoNombre.setDisable(true);
        fondoMenuJsonStore.setVisible(true);
    }
    
    @FXML
    private void buscarDocumentoIndice(ActionEvent e){
        fondoBuscarIndiceDocumento.setVisible(true);
        btnBuscarDocumentoIndice.setDisable(true);
        btnBuscarDocumentoNombre.setDisable(true);
        btnBuscarDocumentoIndice2.setDisable(false);
        labelBuscarDocumentoIndice.setText("");
    }
    
    
    
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
    
    
    @FXML
    private void buscarDocumentoNombre(ActionEvent e){
        fondoBuscarNombreDocumento.setVisible(true);
        btnBuscarDocumentoNombre2.setDisable(false);
        btnBuscarDocumentoIndice.setDisable(true);
        btnBuscarDocumentoNombre.setDisable(true);
        labelBuscarDocumentoNombre.setText("");
    }
    
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
    
//    @FXML
//    private void goIngresarEnDocumento(ActionEvent e){
//        fondoBuscarNombreDocumento.setVisible(false);
//        fondoBuscarIndiceDocumento.setVisible(false);
//        fondoBuscarDocumento1.setVisible(false);
//        fondoConsultaObjeto.setVisible(true);
//    }
    
    
//    @FXML
//    private void atrasMenuDocumento(ActionEvent e){
//        fondoMenuDocumento.setVisible(false);
//        fondoMenuJsonStore.setVisible(true);
//    }
    
    
    
    
//    @FXML
//    private void goEliminarDocumento(ActionEvent e){
//        fondoEliminarDocumento.setVisible(true);
//        btnEliminarNombreDocumento.setDisable(false);
//        btnEliminarIndiceDocumento.setDisable(false);
//        fondoMenuJsonStore.setVisible(false);
//    }
    
//    @FXML
//    private void atrasEliminarDocumento(ActionEvent e){
//        fondoEliminarDocumento.setVisible(false);
//        fondoMenuJsonStore.setVisible(true);
//        fondoEliminarDocumentoNombre.setVisible(false);
//        btnEliminarDocumentoNombreDef.setDisable(true);
//    }
    
//    @FXML
//    private void eliminarDocumentoNombre(ActionEvent e){
//        btnEliminarNombreDocumento.setDisable(true);
//        btnEliminarIndiceDocumento.setDisable(true);
//        fondoEliminarDocumentoNombre.setVisible(true);
//        labelEliminarDocumentoNombre.setText("");
//    }
    
//    @FXML
//    private void goEliminarDocumentoNombre(ActionEvent e){
//        String nom = txtEliminarDocumentoNombreField.getText();
//        if(buscado.obtenerLista().obtener(nom) == null){
//            labelEliminarDocumentoNombre.setText("No existe un DocumentoJson con ese nombre");
//        }else{
//            labelEliminarDocumentoNombre.setText(nom);
//            btnEliminarDocumentoNombreDef.setDisable(false);
//        }   
//    }
    
    
//    @FXML
//    private void eliminarDocumentoNombreDef(ActionEvent e){
//        String nom = txtEliminarDocumentoNombreField.getText();
//        labelEliminarDocumentoNombre.setText(buscado.obtenerLista().eliminar(nom));
//        labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista());
//        btnEliminarDocumentoNombreDef.setDisable(true);
//    }
    
    private void eliminarDocNomDef(String nom){
        buscado.obtenerLista().eliminar(nom);
        labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista());
    }
    
    
    
    
    //Aqui terminan los controles para manipular DocumentosJso
//----------------------------------------------------------------------------------------
    //Aqui inician los controles para manipular ObjetosJson
    
    
    
    
    
    
    
    private Atributo atributo;
//    private int contador;
    
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
    
    @FXML
    private void crearObjetoDef(){
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
                labelCrearObjeto.setText("Llave foranea existente");
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
    
    
    
    @FXML
    private void goConsultaObjeto(){
        txtConsultaObjetosArea.setText("");
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
        for(int i = 0;i < ind;i++){
            actual = buscadoDoc.obtenerLista().obtenerCabeza();
            String registro = "";
            while(actual != null){
                ObjetoJson temp = actual.obtenerLista().obtener(contador);
                registro += (actual.obtenerNombre()+":"+temp.obtenerValor()+",");
                actual = actual.obtenerSiguiente();
            }
            txtConsultaObjetosArea.appendText("{"+registro+"}"+"\n\r");
            contador++;
        }
    }
    
    
    @FXML
    private void atrasConsultaObjeto(ActionEvent e){
        fondoConsultaObjeto.setVisible(false);
        fondoPrincipal.setVisible(true);
    }
    
    
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
    
    
//    @FXML
//    private void atrasBuscarObjeto(ActionEvent e){
//        fondoBuscarObjeto.setVisible(false);
//        fondoBuscarLlavePrim.setVisible(false);
//        fondoBuscarAtributo.setVisible(false);
//        fondoConsultaObjeto.setVisible(true);
//    }
    
    
    @FXML
    private void goBuscarLlavePrim(ActionEvent e){
        btnBuscarLLavePrim.setDisable(true);
        fondoBuscarLlavePrim.setVisible(true);
        txtBuscarLlavePrimField.setText("");
        txtBuscarLlavePrimArea.setText("");
    }
    
    @FXML
    private void buscarLlavePrim(ActionEvent e){
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
    
    
    @FXML
    private void goBuscarAtributo(ActionEvent e){
        btnBuscarLLavePrim.setDisable(true);
        btnBuscarAtributo.setDisable(true);
        fondoBuscarAtributo.setVisible(true);
        
    }
    
    @FXML
    private void buscarAtributo(ActionEvent e){
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
            
    
    
    
    @FXML
    private void goEliminarObjeto(ActionEvent e){
        fondoConsultaObjeto.setVisible(false);
        fondoEliminarObjeto.setVisible(true);
        btnEliminarObjeto.setDisable(true);
        txtConsultaObjetosArea.setText("");
    }
    
    int indi;
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
    
//    @FXML
//    private void atrasEliminarObjeto(ActionEvent e){
//        fondoConsultaObjeto.setVisible(true);
//        fondoEliminarObjeto.setVisible(false);
//    }
    
    
    @FXML
    private void eliminarTodosObjetos(ActionEvent e){
        Atributo actual = buscadoDoc.obtenerLista().obtenerCabeza();
        while(actual != null){
            while(actual.obtenerLista().estaVacio() != true){
                actual.obtenerLista().eliminar(0);
            }
            actual = actual.obtenerSiguiente();
        }
        txtConsultaObjetosArea.setText("");
    }
    
    
    @FXML
    private void goModificarObjeto(ActionEvent e){
        
    }
    
    
    
    //Comit
    @FXML
    private void comit(ActionEvent e) throws FileNotFoundException, IOException{
        FileOutputStream out = new FileOutputStream("Jsons.txt");
        ObjectOutputStream salida = new ObjectOutputStream(out);

        salida.writeObject(lista);
        salida.flush();
    }
    
    
    
    
    
    
    @FXML
    private void imprimir(ActionEvent e){
        if(flag){
            imprime.setVisible(false);
            flag = false;
        }else{
            imprime.setVisible(true);
            flag = true;
        }
        
    } 
      
        
    //treeview
    @FXML
    private TreeView<String> arbol;
    
    /**
     * menu de JsonStore para clic derecho en arbol
     */
    private ContextMenu menuDerecho = new ContextMenu();
    private MenuItem itemJ1 = new MenuItem("Eliminar");
    private MenuItem itemJ2 = new MenuItem("Editar nombre");
    private MenuItem itemJ3 = new MenuItem("Crear Documento");
    /**
     * menu de DocumentoJson  para clic derecho en arbol
     */
    private ContextMenu menuDerechoDoc = new ContextMenu();
    private MenuItem itemD1 = new MenuItem("Eliminar");
    private MenuItem itemD2 = new MenuItem("Editar nombre");
    private MenuItem itemD3 = new MenuItem("Crear Objeto");
    private MenuItem itemD4 = new MenuItem("Ver Objetos");
    private MenuItem itemD5 = new MenuItem("Buscar Objeto");
    
    
    //Botones pagina principal
    @FXML
    private Button btninsertarJson;
    
    @FXML
    private Button btnBuscarJson;
    
    
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
    
    
    //Botones para eliminar JsonStore
//    @FXML
//    private Button btneliminarIndiceJson;
//    
//    @FXML
//    private Button btnVerificarEliminarIndJson;
    
    @FXML
    private Button btnEliminarIndiceJsonDefinitivo;
    
//    @FXML
//    private Button btnEliminarNombreJson;
    
//    @FXML
//    private Button btnEliminarNombreJsonDefinitivo;
    
    
    
    
    //Botones DocumentoJson
    //Botones crear DocumentoJson
    @FXML
    private Button btnDefinirAtributo;
    @FXML
    private Button btnCrearDocumento;
    
    //Botones insertar DocumentoJson
//    @FXML
//    private Button btnVerificarInsertarDocumento;
    
//    @FXML
//    private Button btnInsertarDocumentoDefinitivo;
    
//    @FXML
//    private Button btnContinuarInsertarDocumento;
    
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
    
    
    //Botones para eliminar DocumentoJson
//    @FXML
//    private Button btnEliminarNombreDocumento;
    
//    @FXML
//    private Button btnEliminarIndiceDocumento;
    
//    @FXML
//    private Button btnEliminarDocumentoNombreDef;
    
    
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
    
    
    
    //labels
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
    
//    @FXML
//    public Label labelEliminarIndiceJson;
    
//    @FXML
//    public Label labelEliminarJsonNombre;
    
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
    
//    @FXML
//    public Label labelInsertarDocumento1;
    
//    @FXML
//    public Label labelInsertarDocumento2;
    
    @FXML
    public Label labelBuscarDocumentoIndice;
    
    @FXML
    public Label labelBuscarDocumentoNombre;
    
//    @FXML
//    public Label labelEliminarDocumentoNombre;
    
//    @FXML
//    public Label labelMenuDocumento;
    
    @FXML
    public Label labelCrearObjeto1;
    
    @FXML
    public Label labelCrearObjeto;
    
    
    
    
    //text fields
    @FXML
    private TextField txtNombreJsonField;
    
    
    @FXML
    private TextField txtIndInsertJsonField;
    
    @FXML
    private TextField txtIndInsertNombreJsonField;
    
    
    @FXML
    private TextField txtBuscarIndiceField;
    
    
//    @FXML
//    private TextField txtEliminarIndiceField;
    
//    @FXML
//    private TextField txtEliminarNombreJsonField;
    
    
    @FXML
    private TextField txtCrearDocumentoJsonField;
    
    @FXML
    private TextField txtbucarJsonNombreField;
    
    
//    @FXML
//    private TextField txtIndInsertarDocumentoField;
    
//    @FXML
//    private TextField txtInsertarDocumentoNombreField;
    
    
    @FXML
    private TextField txtBuscarIndiceDocumento;
    
    @FXML
    private TextField txtBuscarDocumentoNombreField;
    
    
//    @FXML
//    private TextField txtEliminarDocumentoNombreField;
    
    
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
    
    
    
    
    //TextArea
    @FXML
    private TextArea txtConsultaObjetosArea;
    
    @FXML
    private TextArea txtBuscarLlavePrimArea;
    
    @FXML
    private TextArea txtEliminarObjetoArea;
    
    @FXML
    private TextArea txtBuscarAtributoArea;
    
    
    
    //ComboBox
    @FXML
    private ComboBox<String> boxTipoAtributo;
    
    ObservableList<String> comboLista = FXCollections.observableArrayList("entero","flotante","cadena","fechaHora");
    
    
    //Fondos
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
    
//    @FXML
//    private Pane fondoEliminarStore;
    
//    @FXML
//    private Pane fondoEliminarIndiceJson;
    
//    @FXML
//    private Pane fondoEliminarNombreJson;
    
    @FXML
    private Pane fondoCrearDocumentoJson;
    
//    @FXML
//    private Pane fondoInsertarDocumento1;
//    
//    @FXML
//    private Pane fondoInsertarDocumento2;
    
    @FXML
    private Pane fondoBuscarDocumento1;
    
    @FXML
    private Pane fondoBuscarIndiceDocumento;
    
    @FXML
    private Pane fondoBuscarNombreDocumento;
    
//    @FXML
//    private Pane fondoEliminarDocumento;
    
//    @FXML
//    private Pane fondoEliminarDocumentoNombre;
    
//    @FXML
//    private Pane fondoMenuDocumento;
    
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
    
    
    //Radio buttons
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
    
    
    
    
    private boolean flag = false;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aqui es la primera ejecucion
        boxTipoAtributo.setItems(comboLista);
        System.out.println("Cargando");
        txtNombreJsonField.setText("");
        txtIndInsertJsonField.setText("");
        txtIndInsertNombreJsonField.setText("");
        try {
            cargarDatos();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        menuDerecho.getItems().addAll(itemJ1,itemJ2,new SeparatorMenuItem(),itemJ3);
        menuDerechoDoc.getItems().addAll(itemD1,itemD2,new SeparatorMenuItem(),itemD3,itemD4,itemD5);
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

  
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.TreeItem;

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
    
    
    @FXML
    private void cargarDatos(ActionEvent e) throws FileNotFoundException, IOException, ClassNotFoundException{
        leer = new ObjectInputStream(new FileInputStream("Jsons.txt"));
        lista = (ListaEnlazadaDoble) leer.readObject();
        labelListaStores.setText(lista.obtenerLista());
        int size = lista.size();
        TreeItem<String> raiz = new TreeItem<>("Raiz");
        if(size != 0){
            JsonStore temp = lista.obtener(0);
            for(int i = 0;i < size;i++){
                TreeItem<String> nodo = new TreeItem<>(temp.obtenerNombre());
                raiz.getChildren().add(nodo);
                nodo.setExpanded(true);
                int tamaño = temp.obtenerLista().size();
                if(tamaño != 0){
                    DocumentoJson temporal = temp.obtenerLista().obtener(0);
                    for(int in = 0;in < tamaño;in++){
                        TreeItem<String> subNodo = new TreeItem<>(temporal.obtenerNombre());
                        nodo.getChildren().add(subNodo);
                        subNodo.setExpanded(true);
                        Atributo atrib = temporal.obtenerLista().obtenerCabeza();
                        while(atrib != null){
                            TreeItem<String> subSubNodo = new TreeItem<>(atrib.obtenerNombre());
                            subNodo.getChildren().add(subSubNodo);
                            atrib = atrib.obtenerSiguiente();
                        }
                        temporal = temporal.obtenerSiguiente();
                    }
                }
               temp = temp.obtenerSiguiente(); 
            }
        }
        arbol.setRoot(raiz);
        raiz.setExpanded(true);
        arbol.setShowRoot(false);
        btninsertarJson.setDisable(false);
        btnBuscarJson.setDisable(false);
        btnEliminarJson.setDisable(false);
        
    }
    
    
    @FXML
    private void goCrearJson(ActionEvent e){
        fondoCrearJson.setVisible(true);
        fondoPrincipal.setVisible(false);
    }
    
    
    @FXML
    private void ingresarJson(ActionEvent e){
        labelMensajeCrearJson.setText(lista.add(txtNombreJsonField.getText()));
        btninsertarJson.setDisable(false);
        btnBuscarJson.setDisable(false);
        btnEliminarJson.setDisable(false);
        labelListaStores.setText(lista.obtenerLista());
    }
    
    @FXML
    private void cancelarCrearJson(ActionEvent e){
        fondoCrearJson.setVisible(false);
        fondoPrincipal.setVisible(true);
        labelMensajeCrearJson.setText("");
        txtNombreJsonField.setText("");
    }
    
    
    
    @FXML
    private void goInsertarJson(ActionEvent e){
        fondoInsertarJson1.setVisible(true);
        fondoPrincipal.setVisible(false);
        btnVerificarInsertarJson.setDisable(false);
        btnInsertarJson2.setDisable(false);
    }
    
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
    
    @FXML
    private void goInsertarJson3(ActionEvent e){
        int ind;
        String IND = txtIndInsertJsonField.getText();
        ind = Integer.parseInt(IND);
        if(ind<(lista.size())){
            labelInsertarJson2.setText(lista.insertar(ind,txtIndInsertNombreJsonField.getText()));
        }
        btnVerificarInsertarJson.setDisable(true);
        btnInsertarJson2.setDisable(true);
        btnContinuarInsertarJson.setDisable(false);
        labelListaStores.setText(lista.obtenerLista());
    }
    
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
    
    
    @FXML
    private void goBuscarJson(ActionEvent e){
        fondoBuscarJson.setVisible(true);
        fondoPrincipal.setVisible(false);
        btnBuscarJsonNombre.setDisable(false);
        btnBuscarJsonIndice.setDisable(false);
    }
    
    @FXML
    private void goBuscarJsonIndice(ActionEvent e){
        btnBuscarJsonNombre.setDisable(true);
        btnBuscarJsonIndice.setDisable(true);
        fondoBuscarJsonIndice.setVisible(true);
        
    }
    
    JsonStore buscado;
    
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
    
    
    @FXML
    private void goBuscarJsonNombre(ActionEvent e){
        btnBuscarJsonNombre.setDisable(true);
        btnBuscarJsonIndice.setDisable(true);
        fondoBuscarJsonNombre.setVisible(true);
        
    }
    
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
    
    @FXML
    private void atrasmenuJsonStore(){
        fondoMenuJsonStore.setVisible(false);
        fondoPrincipal.setVisible(true);
    }
            
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
    
    
    
    @FXML
    private void goEliminarJson(ActionEvent e){
        btneliminarIndiceJson.setDisable(false);
        btnEliminarNombreJson.setDisable(false);
        fondoEliminarStore.setVisible(true);
        fondoPrincipal.setVisible(false);
    }
    
     @FXML
    private void atrasEliminarJson(ActionEvent e){
        fondoEliminarStore.setVisible(false);
        fondoPrincipal.setVisible(true);
        fondoEliminarIndiceJson.setVisible(false);
        fondoEliminarNombreJson.setVisible(false);
        labelEliminarIndiceJson.setText("");
        labelEliminarJsonNombre.setText("");
        btnVerificarEliminarIndJson.setDisable(false);
        btnVerificarEliminarNombreJson.setDisable(false);
        btnEliminarIndiceJsonDefinitivo.setDisable(true);
        btnEliminarNombreJsonDefinitivo.setDisable(true);
    }
    
    @FXML
    private void eliminarIndiceJson(ActionEvent e){
        btneliminarIndiceJson.setDisable(true);
        btnEliminarNombreJson.setDisable(true);
        fondoEliminarIndiceJson.setVisible(true);   
    }
    
    @FXML
    private void verificarEliminarJson(ActionEvent e){
        int ind;
        String IND = txtEliminarIndiceField.getText();
        ind = Integer.parseInt(IND);
        if(ind < lista.size()){
            labelEliminarIndiceJson.setText(lista.obtener(ind).obtenerNombre());
            btnEliminarIndiceJsonDefinitivo.setDisable(false);
            
        }else{
            labelEliminarIndiceJson.setText("Indice no valido, vuelve a intentar!");
        }
    }
    
    @FXML
    private void eliminarJsonDefinitivo(ActionEvent e){
        int ind;
        String IND = txtEliminarIndiceField.getText();
        ind = Integer.parseInt(IND);
        lista.eliminar(ind);
        labelEliminarIndiceJson.setText("Eliminado");
        btnEliminarIndiceJsonDefinitivo.setDisable(true);
        labelListaStores.setText(lista.obtenerLista());
    }
    
    @FXML
    private void eliminarNombreJson(ActionEvent e){
        btneliminarIndiceJson.setDisable(true);
        btnEliminarNombreJson.setDisable(true);
        fondoEliminarNombreJson.setVisible(true);
    }
    
    @FXML
    private void verificarEliminarNombreJson(ActionEvent e){
        String nom = txtEliminarNombreJsonField.getText();
        if(lista.obtener(nom) == null){
            labelEliminarJsonNombre.setText("No existe un JsonStore con ese nombre");
        }else{
            labelEliminarJsonNombre.setText(nom);
            btnEliminarNombreJsonDefinitivo.setDisable(false);
        }
    }
    
    @FXML
    private void eliminarNombreJsonDefinitivo(ActionEvent e){
        String nom = txtEliminarNombreJsonField.getText();
        labelEliminarJsonNombre.setText(lista.eliminar(nom));
        labelListaStores.setText(lista.obtenerLista());
        btnEliminarNombreJsonDefinitivo.setDisable(true);
    }
    
    
    
    
    
    
    
    
    
    //Aqui terminan los controles principales
    //--------------------------------------------------------------------------------
    //Aqui inician los controles para manipular DocumentosJson
    
    
    
    
    
    
    
    
    @FXML
    private void goCrearDocumentoJson(ActionEvent e){
        labelCrearDocumentoJson.setText("");
        txtCrearDocumentoJsonField.setText("");
        fondoCrearDocumentoJson.setVisible(true);
        fondoMenuJsonStore.setVisible(false);
        btnDefinirAtributo.setDisable(true);
        btnCrearDocumento.setDisable(false);
        labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista());
    }
    
    private DocumentoJson buscadoDoc;
    
    @FXML
    private void crearDocumentoJson(ActionEvent e){
        labelCrearDocumentoJson.setText(buscado.obtenerLista().add(txtCrearDocumentoJsonField.getText()));
        labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista());
        buscadoDoc = buscado.obtenerLista().obtener(txtCrearDocumentoJsonField.getText());
        if(labelCrearDocumentoJson.getText().equals("Documento existente")){
        }else{
            btnDefinirAtributo.setDisable(false);
            btnCrearDocumento.setDisable(true);
        }
    }
    int cont = 0;
    @FXML
    private void definirAtributo(ActionEvent e){
        String nom = txtNombreAtributoField.getText();
        String valorDef = txtValorDefAtributoField.getText();
        String tipo = boxTipoAtributo.getValue();
        String tipoEsp = null;
        boolean requerido = true;
        String predeterminado = null;
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
    
    
    
    @FXML
    private void goInsertarDocumentoJson(ActionEvent e){
        fondoInsertarDocumento1.setVisible(true);
        fondoMenuJsonStore.setVisible(false);
        btnVerificarInsertarDocumento.setDisable(false);
    }
    
    @FXML
    private void verificarInsertarDocumento(ActionEvent e){
        int ind;
        String IND = txtIndInsertarDocumentoField.getText();
        ind = Integer.parseInt(IND);
        if(ind > (buscado.obtenerLista().size()-1)){
            labelInsertarDocumento1.setText("Indice no valido, vuelve a intentar");
        }else{
            labelInsertarDocumento1.setText("Verificado");
            btnVerificarInsertarDocumento.setDisable(true);
            btnInsertarDocumentoDefinitivo.setDisable(false);
            fondoInsertarDocumento2.setVisible(true);
        }    
    }
    
    @FXML
    private void insertarDocumentoDefinitivo(ActionEvent e){
        int ind;
        String IND = txtIndInsertarDocumentoField.getText();
        ind = Integer.parseInt(IND);
        labelInsertarDocumento2.setText(buscado.obtenerLista().insertar(ind,txtInsertarDocumentoNombreField.getText()));
        btnInsertarDocumentoDefinitivo.setDisable(true);
        btnContinuarInsertarDocumento.setDisable(false);
        labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista()); 
    }
    
    @FXML
    private void atrasInsertarDocumento(ActionEvent e){
        txtIndInsertarDocumentoField.setText("");
        txtInsertarDocumentoNombreField.setText("");
        labelInsertarDocumento1.setText("");
        labelInsertarDocumento2.setText("");
        fondoInsertarDocumento1.setVisible(false);
        fondoInsertarDocumento2.setVisible(false);
        btnContinuarInsertarDocumento.setDisable(true);
        fondoMenuJsonStore.setVisible(true);
    }
    
    
    
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
    
    @FXML
    private void goIngresarEnDocumento(ActionEvent e){
        fondoBuscarNombreDocumento.setVisible(false);
        fondoBuscarIndiceDocumento.setVisible(false);
        fondoBuscarDocumento1.setVisible(false);
        fondoMenuDocumento.setVisible(true);
        labelMenuDocumento.setText("Menu de "+buscadoDoc.obtenerNombre());
    }
    
    
    @FXML
    private void atrasMenuDocumento(ActionEvent e){
        fondoMenuDocumento.setVisible(false);
        fondoMenuJsonStore.setVisible(true);
    }
    
    
    
    
    @FXML
    private void goEliminarDocumento(ActionEvent e){
        fondoEliminarDocumento.setVisible(true);
        btnEliminarNombreDocumento.setDisable(false);
        btnEliminarIndiceDocumento.setDisable(false);
        fondoMenuJsonStore.setVisible(false);
    }
    
    @FXML
    private void atrasEliminarDocumento(ActionEvent e){
        fondoEliminarDocumento.setVisible(false);
        fondoMenuJsonStore.setVisible(true);
        fondoEliminarDocumentoNombre.setVisible(false);
        btnEliminarDocumentoNombreDef.setDisable(true);
    }
    
    @FXML
    private void eliminarDocumentoNombre(ActionEvent e){
        btnEliminarNombreDocumento.setDisable(true);
        btnEliminarIndiceDocumento.setDisable(true);
        fondoEliminarDocumentoNombre.setVisible(true);
        labelEliminarDocumentoNombre.setText("");
    }
    
    @FXML
    private void goEliminarDocumentoNombre(ActionEvent e){
        String nom = txtEliminarDocumentoNombreField.getText();
        if(buscado.obtenerLista().obtener(nom) == null){
            labelEliminarDocumentoNombre.setText("No existe un DocumentoJson con ese nombre");
        }else{
            labelEliminarDocumentoNombre.setText(nom);
            btnEliminarDocumentoNombreDef.setDisable(false);
        }   
    }
    
    
    @FXML
    private void eliminarDocumentoNombreDef(ActionEvent e){
        String nom = txtEliminarDocumentoNombreField.getText();
        labelEliminarDocumentoNombre.setText(buscado.obtenerLista().eliminar(nom));
        labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista());
        btnEliminarDocumentoNombreDef.setDisable(true);
    }
    
    
    
    
    
    //Aqui terminan los controles para manipular DocumentosJson
    
    
    
    
    
    
    
    //Arbol
    public void mouseClick(MouseEvent e){
        TreeItem<String> item = arbol.getSelectionModel().getSelectedItem();
        System.out.println(item.getValue());
    }
    
    
    
    
    
    
    
    //Aqui inician los controles para manipular ObjetosJson
    
    
    
    
    
    
    
    private Atributo atributo;
//    private int contador;
    
    @FXML
    private void crearObjeto(ActionEvent e){
        fondoCrearObjeto.setVisible(true);
        fondoConsultaObjeto.setVisible(false);
        fondoMenuDocumento.setVisible(false);
        btnContinuarObjeto.setDisable(true);
        btnInsertarUnObjeto.setDisable(false);
        txtConsultaObjetosArea.setText("");
        atributo = buscadoDoc.obtenerLista().obtenerCabeza();
        labelCrearObjeto1.setText("Ingrese el valor de "+atributo.obtenerNombre());
    }
    
    @FXML
    private void crearObjetoDef(ActionEvent e){
        
        atributo.obtenerLista().add(txtCrearObjetoField.getText());
        System.out.println(atributo.obtenerLista().obtenerLista());
        atributo = atributo.obtenerSiguiente();
        if(atributo != null){
            labelCrearObjeto1.setText("Ingrese el valor de "+atributo.obtenerNombre());
            }if(atributo == null){
                btnContinuarObjeto.setDisable(false);
                btnInsertarUnObjeto.setDisable(true);
        }
        txtCrearObjetoField.setText("");
    }
    
    
    
    @FXML
    private void goConsultaObjeto(ActionEvent e){
        fondoConsultaObjeto.setVisible(true);
        fondoEliminarObjeto.setVisible(false);
        fondoCrearObjeto.setVisible(false);
        btnEliminarObjeto.setDisable(true);
        fondoMenuDocumento.setVisible(false);
        Atributo actual;
        int cont = 0;
        int ind = buscadoDoc.obtenerLista().obtenerCabeza().obtenerLista().size();
        for(int i = 0;i < ind;i++){
            actual = buscadoDoc.obtenerLista().obtenerCabeza();
            String registro = "";
            while(actual != null){
                ObjetoJson temp = actual.obtenerLista().obtener(cont);
                registro += (actual.obtenerNombre()+":"+temp.obtenerValor()+",");
                actual = actual.obtenerSiguiente();
            }
            txtConsultaObjetosArea.appendText("{"+registro+"}"+"\n\r");
            cont++;
        }
    }
    
    
    @FXML
    private void atrasConsultaObjeto(ActionEvent e){
        fondoConsultaObjeto.setVisible(false);
        fondoMenuDocumento.setVisible(true);
        txtConsultaObjetosArea.setText("");
    }
    
    
    @FXML
    private void goBuscarObjeto(ActionEvent e){
        fondoBuscarObjeto.setVisible(true);
        fondoConsultaObjeto.setVisible(false);
        btnBuscarLLavePrim.setDisable(false);
    }
    
    
    @FXML
    private void atrasBuscarObjeto(ActionEvent e){
        fondoBuscarObjeto.setVisible(false);
        fondoBuscarLlavePrim.setVisible(false);
        fondoConsultaObjeto.setVisible(true);
    }
    
    
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
    
    
    //Botones pagina principal
    @FXML
    private Button btninsertarJson;
    
    @FXML
    private Button btnBuscarJson;
    
    @FXML
    private Button btnEliminarJson;
    
    
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
    @FXML
    private Button btneliminarIndiceJson;
    
    @FXML
    private Button btnVerificarEliminarIndJson;
    
    @FXML
    private Button btnEliminarIndiceJsonDefinitivo;
    
    @FXML
    private Button btnEliminarNombreJson;
    
    @FXML
    private Button btnEliminarNombreJsonDefinitivo;
    
    
    
    
    //Botones DocumentoJson
    //Botones crear DocumentoJson
    @FXML
    private Button btnDefinirAtributo;
    @FXML
    private Button btnCrearDocumento;
    
    //Botones insertar DocumentoJson
    @FXML
    private Button btnVerificarInsertarDocumento;
    
    @FXML
    private Button btnInsertarDocumentoDefinitivo;
    
    @FXML
    private Button btnContinuarInsertarDocumento;
    
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
    @FXML
    private Button btnEliminarNombreDocumento;
    
    @FXML
    private Button btnEliminarIndiceDocumento;
    
    @FXML
    private Button btnEliminarDocumentoNombreDef;
    
    
    //Botones para crear ObjetoJson
    @FXML
    private Button btnContinuarObjeto;
    
    @FXML
    private Button btnInsertarUnObjeto;
    
    
    //Botones para buscar ObjetoJson
    @FXML
    private Button btnBuscarLLavePrim;
    
    
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
    
    @FXML
    public Label labelEliminarIndiceJson;
    
    @FXML
    public Label labelEliminarJsonNombre;
    
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
    public Label labelInsertarDocumento1;
    
    @FXML
    public Label labelInsertarDocumento2;
    
    @FXML
    public Label labelBuscarDocumentoIndice;
    
    @FXML
    public Label labelBuscarDocumentoNombre;
    
    @FXML
    public Label labelEliminarDocumentoNombre;
    
    @FXML
    public Label labelMenuDocumento;
    
    @FXML
    public Label labelCrearObjeto1;
    
    
    
    
    //text fields
    @FXML
    private TextField txtNombreJsonField;
    
    
    @FXML
    private TextField txtIndInsertJsonField;
    
    @FXML
    private TextField txtIndInsertNombreJsonField;
    
    
    @FXML
    private TextField txtBuscarIndiceField;
    
    
    @FXML
    private TextField txtEliminarIndiceField;
    
    @FXML
    private TextField txtEliminarNombreJsonField;
    
    
    @FXML
    private TextField txtCrearDocumentoJsonField;
    
    @FXML
    private TextField txtbucarJsonNombreField;
    
    
    @FXML
    private TextField txtIndInsertarDocumentoField;
    
    @FXML
    private TextField txtInsertarDocumentoNombreField;
    
    
    @FXML
    private TextField txtBuscarIndiceDocumento;
    
    @FXML
    private TextField txtBuscarDocumentoNombreField;
    
    
    @FXML
    private TextField txtEliminarDocumentoNombreField;
    
    
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
    
    
    
    
    //TextArea
    @FXML
    private TextArea txtConsultaObjetosArea;
    
    @FXML
    private TextArea txtBuscarLlavePrimArea;
    
    @FXML
    private TextArea txtEliminarObjetoArea;
    
    
    
    //ComboBox
    @FXML
    private ComboBox<String> boxTipoAtributo;
    
    ObservableList<String> comboLista = FXCollections.observableArrayList("entero","flotante","cadena","fechaHora");
    
    
    //Fondos
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
    private Pane fondoEliminarStore;
    
    @FXML
    private Pane fondoEliminarIndiceJson;
    
    @FXML
    private Pane fondoEliminarNombreJson;
    
    @FXML
    private Pane fondoCrearDocumentoJson;
    
    @FXML
    private Pane fondoInsertarDocumento1;
    
    @FXML
    private Pane fondoInsertarDocumento2;
    
    @FXML
    private Pane fondoBuscarDocumento1;
    
    @FXML
    private Pane fondoBuscarIndiceDocumento;
    
    @FXML
    private Pane fondoBuscarNombreDocumento;
    
    @FXML
    private Pane fondoEliminarDocumento;
    
    @FXML
    private Pane fondoEliminarDocumentoNombre;
    
    @FXML
    private Pane fondoPrincipal;
    
    @FXML
    private Pane fondoMenuDocumento;
    
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
    }
    
}

  
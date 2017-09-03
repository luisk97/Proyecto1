/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceproyecto1;

import estructura.DocumentoJson;
import estructura.JsonStore;
import estructura.ListaEnlazadaDoble;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author luisk
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private void goCrearJson(ActionEvent e){
        fondoCrearJson.setVisible(true);
        fondoPrincipal.setVisible(false);
    }
    
    @FXML
    private void ingresarJson(ActionEvent e){
        labelMensajeCrearJson.setText(lista.add(txtNombreJsonField.getText()));
        lista.obtenerLista();
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
        txtNombreJsonField.setText("Ingrese el nombre del JsonStore");
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
        lista.obtenerLista();
        btnVerificarInsertarJson.setDisable(true);
        btnInsertarJson2.setDisable(true);
        labelListaStores.setText(lista.obtenerLista());
    }
    
    @FXML
    private void continuarInsertarJson(ActionEvent e){
        txtIndInsertJsonField.setText("");
        labelInsertarJson2.setText("");
        txtIndInsertNombreJsonField.setText("");
        labelInsertarJson1.setText("");
        fondoInsertarJson1.setVisible(false);
        fondoInsertarJson2.setVisible(false);
        fondoPrincipal.setVisible(true);
    }
    
    
    
    @FXML
    private void cancelarInsertarJson(ActionEvent e){
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
    private void goBuscarJsonNombre(ActionEvent e){
        btnBuscarJsonNombre.setDisable(true);
        btnBuscarJsonIndice.setDisable(true);
        fondoBuscarJsonNombre.setVisible(true);
        
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
    private void goIngresarJsonStore(ActionEvent e){
        labelPricipalMenuJsonStore.setText("Menu de "+buscado.obtenerNombre());
        fondoBuscarJson.setVisible(false);
        fondoBuscarJsonIndice.setVisible(false);
        txtBuscarIndiceField.setText("");
        btnIngresarEnJsonStore.setDisable(true);
        labelBuscarJsonIndice.setText("");
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
        btnIngresarEnJsonStore.setDisable(true);
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
        labelEliminarIndiceJson.setText("");
        btnEliminarIndiceJsonDefinitivo.setDisable(true);
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
            JsonStore buscadoElim = lista.obtener(ind);
            labelEliminarIndiceJson.setText(buscadoElim.obtenerNombre());
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
    private void goCrearDocumentoJson(ActionEvent e){
        labelCrearDocumentoJson.setText("");
        txtCrearDocumentoJsonField.setText("");
        fondoCrearDocumentoJson.setVisible(true);
        fondoMenuJsonStore.setVisible(false);
        labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista());
    }
    
    @FXML
    private void crearDocumentoJson(ActionEvent e){
        buscado.obtenerLista().add(txtCrearDocumentoJsonField.getText());
        DocumentoJson doc = buscado.obtenerLista().obtenerUltimo();
        labelCrearDocumentoJson.setText("Se creo el Documento: "+doc.obtenerNombre());
        labelListaDocumentos.setText(buscado.obtenerLista().obtenerLista());
        
    }
    
    @FXML
    private void atrasCrearDocumento(ActionEvent e){
        fondoCrearDocumentoJson.setVisible(false);
        fondoMenuJsonStore.setVisible(true);
    }
    
    
    
    @FXML
    private void goInsertarDocumentoJson(ActionEvent e){
        fondoInsertarDocumento1.setVisible(true);
        fondoMenuJsonStore.setVisible(false);
    }
    
    @FXML
    private void verificarInsertarDocumento(ActionEvent e){
        fondoInsertarDocumento2.setVisible(true);
        
    }
    
    @FXML
    private void atrasInsertarDocumento(ActionEvent e){
        fondoInsertarDocumento1.setVisible(false);
        fondoInsertarDocumento2.setVisible(false);
        fondoMenuJsonStore.setVisible(true);
    }
    
    
    
    @FXML
    private void goBuscarDocumentoJson(ActionEvent e){
        fondoBuscarDocumento1.setVisible(true);
        fondoMenuJsonStore.setVisible(false);
    }
    
    @FXML
    private void atrasBuscarDocumento(ActionEvent e){
        fondoBuscarDocumento1.setVisible(false);
        fondoBuscarDocumento2.setVisible(false);
        fondoMenuJsonStore.setVisible(true);
    }
    
    @FXML
    private void buscarDocumentoIndice(ActionEvent e){
        fondoBuscarDocumento2.setVisible(true);
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
    
    
    //Botones buscar JsonStore
    @FXML
    private Button btnBuscarJsonNombre;
    
    @FXML
    private Button btnBuscarJsonIndice;
    
    @FXML
    private Button btnIngresarEnJsonStore;
    
    
    //Botones para eliminar JsonStore
    @FXML
    private Button btneliminarIndiceJson;
    
    @FXML
    private Button btnEliminarNombreJson;
    
    @FXML
    private Button btnEliminarIndiceJsonDefinitivo;
    
    
    //Botones DocumentoJson
    
    
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
    public Label labelPricipalMenuJsonStore;
    
    @FXML
    public Label labelCrearDocumentoJson;
    
    @FXML
    public Label labelListaDocumentos;
    
    
    
    
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
    private TextField txtCrearDocumentoJsonField;
    
    
    
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
    private Pane fondoCrearDocumentoJson;
    
    @FXML
    private Pane fondoInsertarDocumento1;
    
    @FXML
    private Pane fondoInsertarDocumento2;
    
    @FXML
    private Pane fondoBuscarDocumento1;
    
    @FXML
    private Pane fondoBuscarDocumento2;
    
    @FXML
    private Pane fondoPrincipal;
    
    private boolean flag = false;
    
    private ListaEnlazadaDoble lista = new ListaEnlazadaDoble();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aqui es la primera ejecucion
        System.out.println("Cargando");
        txtNombreJsonField.setText("Ingrese el nombre del JsonStore");
        txtIndInsertJsonField.setText("");
        txtIndInsertNombreJsonField.setText("");
    }    
    
}

  
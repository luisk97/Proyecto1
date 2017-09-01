/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceproyecto1;

import estructura.DocumentoJson;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author luisk
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private void escribe(ActionEvent e){
        System.out.println("Hello mi compa!!");
        saludo.setVisible(true);
    }
    @FXML
    private Button borrar;
    
    @FXML
    private void borra(ActionEvent e){
        saludo.setVisible(false);
        borrar.setCursor(Cursor.WAIT);
    }
    
    @FXML
    private void ingresarDoc(ActionEvent e){
        DocumentoJson nuevo = new DocumentoJson(txt.getText());
        mensaje.setText(nuevo.obtenerNombre());
    }
    
    @FXML
    private void mostrarMensaje(ActionEvent e){
        mensaje.setText(txt.getText());
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
    
    @FXML
    private Label saludo,mensaje,imprime;
    
    @FXML
    private TextField txt;
    
    @FXML
    private ComboBox cbox;
    
    private boolean flag = false;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aqui es la primera ejecucion
        System.out.println("Cargando");
        txt.setText("Escriba su mensaje");
        cbox.getItems().add("entero");
        cbox.getItems().add("flotante");
        cbox.getItems().add("cadena");
    }    
    
}

  
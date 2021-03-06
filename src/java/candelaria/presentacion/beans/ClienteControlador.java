/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package candelaria.presentacion.beans;

import candelaria.logica.clases.Cliente;
import candelaria.logica.funciones.FCliente;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.context.DefaultRequestContext;
import recursos.Util;
import recursos.utilBeans.ValidatorBean;

/**
 *
 * @author Pato
 */
@ManagedBean
@ViewScoped
public class ClienteControlador {

    private Cliente objCliente;
    private Cliente clienteSel;
    private ArrayList<Cliente> lstCliente;
    private boolean mostrarActualizar;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cliente getObjCliente() {
        return objCliente;
    }

    public void setObjCliente(Cliente objCliente) {
        this.objCliente = objCliente;
    }

    public Cliente getClienteSel() {
        return clienteSel;
    }

    public void setClienteSel(Cliente clienteSel) {
        this.clienteSel = clienteSel;
    }

    public ArrayList<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(ArrayList<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

    public boolean isMostrarActualizar() {
        return mostrarActualizar;
    }

    public void setMostrarActualizar(boolean mostrarActualizar) {
        this.mostrarActualizar = mostrarActualizar;
    }

    public ClienteControlador() {
        reinit();
    }

    private void reinit() {
        this.objCliente = new Cliente();
        this.clienteSel = new Cliente();
        this.lstCliente = new ArrayList<Cliente>();
//        this.LstPeriodos = new ArrayList<Periodos>();  
//        this.lstFacultades = new ArrayList<Facultad>();
//        this.lstEscuelas = new ArrayList<Escuela>();
//        this.lstNiveles = new ArrayList<Nivel>();
        //this.ProveedorSel = this.lstProveedors.get(0);
        this.cargarCliente();
        //this.cargarNiveles();
//        this.cargarPeriodos();
//        this.cargarFacultad();

    }

    public void cargarCliente() {
        try {
            this.lstCliente = FCliente.ObtenerClientes();
            this.clienteSel = lstCliente.get(0);
            System.out.println(lstCliente.get(0).getId_cliente());
        } catch (Exception e) {
            Util.addErrorMessage("private void cargarCliente dice: " + e.getMessage());
            System.out.println("private void cargarCliente dice: " + e.getMessage());
        }
    }

    public void insertarCliente() {

        try {
            if (FCliente.Insertar(objCliente)) {
                this.reinit();
                DefaultRequestContext.getCurrentInstance().execute("wdlgNuevoCliente.hide()");
                Util.addSuccessMessage("Información guardada con éxito");
                System.out.println("public void insertarCliente dice: Error al guardar la información");
            } else {
                Util.addSuccessMessage("Error al guardar la información");
                System.out.println("public void insertarCliente dice: Error al guardar la información");
            }
        } catch (Exception e) {
            Util.addErrorMessage("private void insertarCliente dice: " + e.getMessage());
            System.out.println("private void insertarCliente dice: " + e.getMessage());
        }
    }

    public void cambiarEstadoMostrarActualizar() {
        mostrarActualizar = true;
    }

    public void actualizarCliente() {
        try {

            if (FCliente.actualizar(clienteSel)) {
                clienteSel = new Cliente();
                mostrarActualizar = false;
                this.reinit();
                DefaultRequestContext.getCurrentInstance().execute("wdlgEditarCliente.hide()");
                Util.addSuccessMessage("Información guardada con éxito");
                System.out.println("public void actualizarCliente dice: Información guardada con éxito!!");
            } else {
                Util.addErrorMessage("Error al guardar la información");
                System.out.println("public void actualizarCliente dice: Error al guardar la información");
            }
        } catch (Exception e) {
            Util.addErrorMessage("private void actualizarCliente dice: " + e.getMessage());
            System.out.println("private void actualizarCliente dice: " + e.getMessage());
        }
    }

    public void eliminarCliente() {
        try {
            if (FCliente.eliminar((int) clienteSel.getId_cliente())) {
                this.reinit();
                DefaultRequestContext.getCurrentInstance().execute("wdlgEliminarCliente.hide()");
                Util.addSuccessMessage("Información eliminada.");
                System.out.println("public void eliminarCliente dice: Información eliminada.");
            } else {
                Util.addErrorMessage("Error al eliminar la información.");
                System.out.println("public void eliminarCliente dice: Error al eliminar la información");
            }
        } catch (Exception e) {
            Util.addErrorMessage("private void eliminarCliente dice: " + e.getMessage());
            System.out.println("private void eliminarCliente dice: " + e.getMessage());
        }


    }
    
     public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
 
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images" + File.separator + "reportes" + File.separator + "clientes.png";
         
        pdf.add(Image.getInstance(logo));
    }
}


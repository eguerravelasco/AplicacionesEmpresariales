package candelaria.presentacion.beans;


import candelaria.logica.clases.Categoria;
import candelaria.logica.clases.Producto;
import candelaria.logica.funciones.FCategoria;
import candelaria.logica.funciones.FProducto;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.DefaultRequestContext;
import recursos.Util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ROGES
 */
@ManagedBean
@ViewScoped
public class ProductoControlador {
    private Producto objProducto;
    private Producto productoSel;
    private ArrayList<Producto> lstProducto;
    private ArrayList<Categoria> lstCategoria;
    private boolean mostrarActualizar;
    private int valorCategoriaSeleccionado;
    private Date fecha_fa,fecha_cad;

    public Date getFecha_fa() {
        return fecha_fa;
    }

    public void setFecha_fa(Date fecha_fa) {
        this.fecha_fa = fecha_fa;
    }

    public Date getFecha_cad() {
        return fecha_cad;
    }

    public void setFecha_cad(Date fecha_cad) {
        this.fecha_cad = fecha_cad;
    }
    

    public ArrayList<Categoria> getLstCategoria() {
        return lstCategoria;
    }

    public void setLstCategoria(ArrayList<Categoria> lstCategoria) {
        this.lstCategoria = lstCategoria;
    }

    public boolean isMostrarActualizar() {
        return mostrarActualizar;
    }

    public void setMostrarActualizar(boolean mostrarActualizar) {
        this.mostrarActualizar = mostrarActualizar;
    }

    public int getValorCategoriaSeleccionado() {
        return valorCategoriaSeleccionado;
    }

    public void setValorCategoriaSeleccionado(int valorCategoriaSeleccionado) {
        this.valorCategoriaSeleccionado = valorCategoriaSeleccionado;
    }
    
    public Producto getObjProducto() {
        return objProducto;
    }

    public void setObjProducto(Producto objProducto) {
        this.objProducto = objProducto;
    }

    public Producto getProductoSel() {
        return productoSel;
    }

    public void setProductoSel(Producto productoSel) {
        this.productoSel = productoSel;
    }

    public ArrayList<Producto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(ArrayList<Producto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public ProductoControlador() {
        reinit();
    }
    private void reinit(){
        this.objProducto = new Producto();
        this.productoSel = new Producto();
        this.lstProducto = new ArrayList<Producto>();
                this.lstCategoria = new ArrayList<Categoria>();

//        this.LstPeriodos = new ArrayList<Periodos>();  
//        this.lstFacultades = new ArrayList<Facultad>();
//        this.lstEscuelas = new ArrayList<Escuela>();
//        this.lstNiveles = new ArrayList<Nivel>();
        //this.ProveedorSel = this.lstProveedors.get(0);
        this.cargarProducto();
        this.cargarCategoria();
        //this.cargarNiveles();
//        this.cargarPeriodos();
//        this.cargarFacultad();
        
    }
    
    public void cargarProducto() {
        try {
            this.lstProducto = FProducto.ObtenerProductos();
            this.productoSel = lstProducto.get(0);
            System.out.println(lstProducto.get(0).getId_producto());
        } catch (Exception e) {
            Util.addErrorMessage("private void cargarProducto dice: " + e.getMessage());
            System.out.println("private void cargarProducto dice: " + e.getMessage());
        }
     }
    
    public void cargarCategoria() {
        try {
            this.lstCategoria = FCategoria.ObtenerCategoria();
            
            System.out.println(lstCategoria.get(0).getId_categoria());
        } catch (Exception e) {
            Util.addErrorMessage("private void cargarCategoria dice: " + e.getMessage());
            System.out.println("private void cargarCategoria dice: " + e.getMessage());
        }
     }
            
        public void insertarProducto() {
        try {
            
            Categoria categoria= new Categoria();
            categoria.setId_categoria(valorCategoriaSeleccionado);
            objProducto.setId_categoria(categoria);
            
                        
            if (FProducto.Insertar(objProducto)) {
                this.reinit();
                DefaultRequestContext.getCurrentInstance().execute("wdlgNuevoProducto.hide()");
                Util.addSuccessMessage("Información guardada con éxito");
                System.out.println("public void insertarProducto dice: Error al guardar la información");
           } else { 
                Util.addSuccessMessage("Error al guardar la información");
                System.out.println("public void insertarProducto dice: Error al guardar la información");
           }
        } catch (Exception e) {
            Util.addErrorMessage("private void insertarProducto dice: " + e.getMessage());
            System.out.println("private void insertarProducto dice: " + e.getMessage());
                }
        }

    public void cambiarEstadoMostrarActualizar(){
        mostrarActualizar = true;
    }
        
     public void actualizarProducto() {
        try {
            
            productoSel.setId_categoria(FCategoria.ObtenerCategoriaDadoCodigo(productoSel.getId_categoria().getId_categoria()));
            if (FProducto.actualizar(productoSel)) {
                productoSel = new Producto();
                mostrarActualizar = false;
                this.reinit();
                DefaultRequestContext.getCurrentInstance().execute("wdlgEditarProducto.hide()");
                Util.addSuccessMessage("Información guardada con éxito");
                System.out.println("public void actualizarProducto dice: Información guardada con éxito!!");
            } else {
                Util.addErrorMessage("Error al guardar la información");
                System.out.println("public void actualizarProducto dice: Error al guardar la información");
            }
        } catch (Exception e) {
            Util.addErrorMessage("private void actualizarProducto dice: " + e.getMessage());
            System.out.println("private void actualizarProducto dice: " + e.getMessage());
        }
    }

    public void eliminarProducto() {
        try {
            if (FProducto.eliminar((int) productoSel.getId_producto())) {
                this.reinit();
                DefaultRequestContext.getCurrentInstance().execute("wdlgEliminarProducto.hide()");
                Util.addSuccessMessage("Información eliminada.");
                System.out.println("public void eliminarProducto dice: Información eliminada.");
            } else {
                Util.addErrorMessage("Error al eliminar la información.");
                System.out.println("public void eliminarProducto dice: Error al eliminar la información");
            }
        } catch (Exception e) {
            Util.addErrorMessage("private void eliminarProducto dice: " + e.getMessage());
            System.out.println("private void eliminarProducto dice: " + e.getMessage());
        }
        
    }
    
    
}

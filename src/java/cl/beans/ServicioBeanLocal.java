/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.beans;

import cl.entities.*;
import javax.ejb.Local;
import java.util.*;
/**
 *
 * @author roman
 */
@Local
public interface ServicioBeanLocal {
    Usuario iniciarSesion(String rut, String clave);
    void guardar(Object o);
    void sincronizar(Object o);
    
    Categoria buscarCategoria(int id);
    
    
    List<Categoria> getCategorias();
    List<Producto> getProductos();
}

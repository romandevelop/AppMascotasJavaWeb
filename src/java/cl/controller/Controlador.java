/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.controller;

import cl.beans.ServicioBeanLocal;
import cl.entities.*;
import directorio.Hash;
import java.io.IOException;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author roman
 */
@WebServlet(name = "Controlador", urlPatterns = {"/control.do"})
@MultipartConfig( location = "/tmp",
                  fileSizeThreshold = 1024*1024,
                  maxFileSize = 1024*1024*5,
                  maxRequestSize = 1024*1024*5*5
                )
public class Controlador extends HttpServlet {

   
    @EJB
    private ServicioBeanLocal servicio;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String boton = request.getParameter("boton");
        switch (boton) {
            case "login":
                login(request, response);
                break;
            case "nuevacategoria":
                nuevaCategoria(request, response);
                break;
            case "nuevoproducto":
                nuevoProducto(request, response);
                break;
            case "editardatos":
                editarDatos(request, response);
                break;    
                
                
        }
        
    }
    
    protected void editarDatos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String rut      = request.getParameter("rut");
        String mail     = request.getParameter("mail");
        String clave1   = request.getParameter("clave1");
        String clave2   = request.getParameter("clave2");
        
        String errores = "";
        if (mail.isEmpty()) {
            errores = errores.concat("- Falta Correo");
        }
        if (clave1.isEmpty()) {
            errores = errores.concat("- Falta Clave");
        }
        if (clave2.isEmpty()) {
            errores = errores.concat("- Falta Confirmar Clave");
        }
        
        if (!clave1.equals(clave2)) {
            errores = errores.concat("- Claves no coinciden");
        }
        
        if (errores.isEmpty()) {
            Usuario user = servicio.buscarUsuario(rut);
            user.setEmailUser(mail);
            user.setClave(Hash.md5(clave1));
            
            servicio.sincronizar(user);
            request.getSession().setAttribute("admin", user);
            request.setAttribute("msg", "Datos actualizados");
        }else{
            request.setAttribute("msg", errores);
        }
        request.getRequestDispatcher("misdatos.jsp").forward(request, response);
        
        
        
    }
    
    
    protected void nuevoProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre       = request.getParameter("nombre");
        String s_precio     = request.getParameter("precio");
        String s_unidad     = request.getParameter("unidad");
        String descripcion  = request.getParameter("descripcion");
        String s_id         = request.getParameter("idcategoria");
        
        InputStream stream=null;
        Part foto = request.getPart("foto");
        if(foto!=null){
            stream = foto.getInputStream();
        }
        
        
        String errores="";
        int precio=0, unidad=0, id=0;
        
        if (nombre.isEmpty()) {
            errores=errores.concat("- Falta Nombre<br>");
        }
        if (descripcion.isEmpty()) {
            errores=errores.concat("- Falta Descripcion<br>");
        }
        try {
            precio = Integer.parseInt(s_precio);
        } catch (Exception e) {
            errores=errores.concat("- Falta Precio<br>");
        }
        try {
            unidad = Integer.parseInt(s_unidad);
        } catch (Exception e) {
            errores=errores.concat("- Falta Unidad<br>");
        }
        
        
        if (errores.isEmpty()) {
            id = Integer.parseInt(s_id);
            Categoria cat = servicio.buscarCategoria(id);
            Producto nuevo = new Producto();
            nuevo.setNombreProducto(nombre);
            nuevo.setPrecioProducto(precio);
            nuevo.setUnidadesProducto(unidad);
            nuevo.setDescripcionProducto(descripcion);
            nuevo.setCategoria(cat);
            if (stream!=null) {
                nuevo.setFotoProducto(IOUtils.toByteArray(stream));
            }
            
            servicio.guardar(nuevo);
            
            cat.getProductoList().add(nuevo);
            servicio.sincronizar(cat);
            request.setAttribute("msg", "Producto creado con exito!");
            
        } else {
            request.setAttribute("msg", errores);
        }
        
        request.getRequestDispatcher("producto.jsp").forward(request, response);
    }
    
    protected void nuevaCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        
        if (nombre.isEmpty()) {
            request.setAttribute("msg", "completa el nombre");
        }else{
            Categoria nueva = new Categoria();
            nueva.setNombreCategoria(nombre);
            servicio.guardar(nueva);
            request.setAttribute("msg", "Categoria creada con exito!");
        }
        
        request.getRequestDispatcher("categoria.jsp").forward(request, response);
    }
    
    
    protected void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String rut = request.getParameter("rut");
        String clave = request.getParameter("clave");
        
        Usuario user = servicio.iniciarSesion(rut, Hash.md5(clave));
        
        if (user != null) {
            if (user.getPerfil().getNombrePerfil().equals("administrador")) {
                request.getSession().setAttribute("admin", user);
            } else { //es un cliente
                request.getSession().setAttribute("person", user);
            }
            response.sendRedirect("inicio.jsp");
        } else {
            request.setAttribute("msg", "usuario no encontrado :(");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

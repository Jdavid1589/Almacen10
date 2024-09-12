package Controlador;

import Modelo.Usuarios;
import Persistencia.DaoUsuarios;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorUsuarios extends HttpServlet {

    Usuarios user = new Usuarios();
    int ide;  // Variable de instancia para almacenar el ID

    //La variable serialVersionUID se utiliza en Java para asignar una versión única 
    //a una clase Serializable.
    //En este contexto, private static final long serialVersionUID = 1L; simplemente está estableciendo 
    //el serialVersionUID de la clase ControladorConsecutivo
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        String action = request.getParameter("accion");

        switch (action) {
          

            case "registrar":
                registrarUsuario(request, response);
                break;

            case "listar":
                listarUsuarios(request, response);
                break;

            case "editar":
                editarUsuarios(request, response);
                break;

            case "editarUsuarios":
                actualizarUsuario(request, response);
                break;

            case "buscar":
                buscarUsuarios(request, response);
                break;

            case "eliminar":
                eliminarUsuario(request, response);
                break;

            default:
                // Handle any other actions or provide a default behavior
                break;
        }
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
         
            Usuarios usuarios = new Usuarios();

            // . Se toma la inforacion  del for y se le pasa directamente al obj
            usuarios.setNombres(request.getParameter("nombres"));       
            usuarios.setUsuario(request.getParameter("usuario"));
            usuarios.setClave(request.getParameter("clave"));
            usuarios.setPerfilId(Integer.parseInt(request.getParameter("perfilId")));

            if (DaoUsuarios.grabar(usuarios)) {
                request.setAttribute("mensaje", "el usuario fue registrado");
            } else {
                request.setAttribute("mensaje", "el usuario no fue registrado, validar campos ingresados");
            }

            List<Usuarios> lt = DaoUsuarios.listar();
            request.setAttribute("listaUsuarios", lt);
            request.getRequestDispatcher("Vistas/ListaUsuarios.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar el Consecutivo");
            listarUsuarios(request, response);
        }
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Usuarios> lt = DaoUsuarios.listar();
            request.setAttribute("listaUsuarios", lt);
            request.getRequestDispatcher("Vistas/ListaUsuarios.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al listar los Consecutivos");
            request.getRequestDispatcher("Vistas/ListaUsuarios.jsp").forward(request, response);
        }
    }

    private void editarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el ID del parámetro de solicitud y almacenarlo en la variable de instancia
            ide = Integer.parseInt(request.getParameter("id"));
            Usuarios u = DaoUsuarios.obtenerUsuarioPorId(ide);
            request.setAttribute("User", u);

            listarUsuarios(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al editar el Consecutivo");
            listarUsuarios(request, response);
        }
    }

    private void buscarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String texto = request.getParameter("txtbuscar");
            List<Usuarios> lt = DaoUsuarios.buscarUsuarios(texto);
            request.setAttribute("listaUsuarios", lt);
            request.getRequestDispatcher("Vistas/ListaUsuarios.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al buscar los Consecutivos");
            request.getRequestDispatcher("Vistas/ListaUsuarios.jsp").forward(request, response);
        }
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Utilizar la variable de instancia para obtener el ID
            Usuarios u = DaoUsuarios.obtenerUsuarioPorId(ide);
            request.setAttribute("User", u);

          
            Usuarios usuarios = new Usuarios();

            usuarios.setNombres(request.getParameter("nombres"));         
            usuarios.setUsuario(request.getParameter("usuario"));
            usuarios.setClave(request.getParameter("clave"));
            usuarios.setPerfilId(Integer.parseInt(request.getParameter("perfilId")));
            usuarios.setIdUsuarios(ide);

            boolean actualizacionExitosa = DaoUsuarios.editar(usuarios);

            if (actualizacionExitosa) {
                request.setAttribute("mensaje", "Consecutivo actualizado correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo actualizar el Consecutivo");
            }

            List<Usuarios> lt = DaoUsuarios.listar();
            request.setAttribute("listaUsuarios", lt);
            request.getRequestDispatcher("Vistas/ListaUsuarios.jsp").forward(request, response);

        } catch (IOException | NumberFormatException | ServletException ex) {
            request.setAttribute("mensaje", "Error al actualizar el Consecutivo: " + ex.getMessage());
            listarUsuarios(request, response);
        }
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idUsuarios = Integer.parseInt(request.getParameter("id"));

            if (DaoUsuarios.eliminar(idUsuarios)) {
                request.setAttribute("mensaje", "¿Estás seguro de que deseas eliminar este usuario?");
            } else {
                request.setAttribute("mensaje", "No se pudo eliminar el usuario");
            }

            List<Usuarios> lt = DaoUsuarios.listar();
            request.setAttribute("listaUsuarios", lt);
            request.getRequestDispatcher("Vistas/ListaUsuarios.jsp").forward(request, response);

        } catch (IOException | NumberFormatException | ServletException ex) {
            request.setAttribute("mensaje", "Error al eliminar el Consecutivo");
            listarUsuarios(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

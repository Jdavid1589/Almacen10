package Controlador;

import Modelo.Categorias;
import Persistencia.DaoCategorias;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorCategorias extends HttpServlet {

    Categorias user = new Categorias();
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
            //   case "add":
            //  request.getRequestDispatcher("Vistas/RegistrarUsuarios.jsp").forward(request, response);//
            //  break;

            case "registrar":
                registrarCategorias(request, response);
                break;

            case "listar":
                listarCategorias(request, response);
                break;

            case "editar":
                editarCategorias(request, response);
                break;

            case "editarCategorias":
                actualizarCategorias(request, response);
                break;

            case "buscar":
                buscarCategorias(request, response);
                break;

            case "eliminar":
                eliminarCategorias(request, response);
                break;

            default:
                // Handle any other actions or provide a default behavior
                break;
        }
    }

    private void registrarCategorias(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            Categorias categorias = new Categorias();

            categorias.setTipos(request.getParameter("tipos"));
            categorias.setDescripcion(request.getParameter("descripcion"));

            if (DaoCategorias.grabar(categorias)) {

                request.setAttribute("mensaje", "la categorias fue registrada");
            } else {
                request.setAttribute("mensaje", "la categoria no fue registrada, validar campos ingresados");
            }

            List<Categorias> lt = DaoCategorias.listar();
            request.setAttribute("listaCategorias", lt);
            request.getRequestDispatcher("Vistas/ListaCategorias.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar el Consecutivo");
            listarCategorias(request, response);
        }
    }

    private void listarCategorias(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Categorias> lt = DaoCategorias.listar();
            request.setAttribute("listaCategorias", lt);
            request.getRequestDispatcher("Vistas/ListaCategorias.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al listar los Consecutivos");
            request.getRequestDispatcher("Vistas/ListaCategorias.jsp").forward(request, response);
        }
    }

    private void editarCategorias(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el ID del parámetro de solicitud y almacenarlo en la variable de instancia
            ide = Integer.parseInt(request.getParameter("id"));
            Categorias cat = DaoCategorias.obtenerCategoriasPorId(ide);
            request.setAttribute("User", cat);

            listarCategorias(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al editar el Consecutivo");
            listarCategorias(request, response);
        }
    }

    private void actualizarCategorias(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Utilizar la variable de instancia para obtener el ID
            Categorias cat = DaoCategorias.obtenerCategoriasPorId(ide);
            request.setAttribute("User", cat);

            Categorias categorias = new Categorias();

            categorias.setTipos(request.getParameter("tipos"));
            categorias.setDescripcion(request.getParameter("descripcion"));
            categorias.setIdCategorias(ide);

            boolean actualizacionExitosa = DaoCategorias.editar(categorias);

            if (actualizacionExitosa) {
                request.setAttribute("mensaje", "Consecutivo actualizado correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo actualizar el Consecutivo");
            }

            listarCategorias(request, response);

        } catch (IOException | NumberFormatException | ServletException ex) {
            request.setAttribute("mensaje", "Error al actualizar el Consecutivo: " + ex.getMessage());
            listarCategorias(request, response);
        }
    }

    private void eliminarCategorias(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idCategorias = Integer.parseInt(request.getParameter("id"));

            if (DaoCategorias.eliminar(idCategorias)) {
                request.setAttribute("mensaje", "El Catalogo fue Eliminado Correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo eliminar el catalogo");
            }

            List<Categorias> lt = DaoCategorias.listar();
            request.setAttribute("listaCategorias", lt);
            request.getRequestDispatcher("Vistas/ListaCategorias.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminar el Consecutivo");
            listarCategorias(request, response);
        }
    }

    private void buscarCategorias(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String texto = request.getParameter("txtbuscar");
            List<Categorias> lt = DaoCategorias.buscarCategorias(texto);
            request.setAttribute("listaCategorias", lt);
            request.getRequestDispatcher("Vistas/ListaCategorias.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al buscar los Consecutivos");
            request.getRequestDispatcher("Vistas/ListaCategorias.jsp").forward(request, response);
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

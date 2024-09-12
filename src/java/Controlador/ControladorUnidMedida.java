package Controlador;

import Modelo.Productos;
import Modelo.UnidadMedida;
import Persistencia.DaoProductos;
import Persistencia.DaoUnidMedida;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorUnidMedida extends HttpServlet {

    UnidadMedida unidadMedida = new UnidadMedida();
    int ide;  // Variable de instancia para almacenar el ID

    //La variable serialVersionUID se utiliza en Java para asignar una versión única 
    //a una clase Serializable.
    //En este contexto, private static final long serialVersionUID = 1L; simplemente está estableciendo 
    //el serialVersionUID de la clase ControladorConsecutivo
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set the character encoding for the request
        request.setCharacterEncoding("UTF-8");
        // Set the content type and character encoding for the response
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ensure UTF-8 encoding
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        processRequest(request, response);

        String action = request.getParameter("accion");

        switch (action) {
            //   case "add":
            //  request.getRequestDispatcher("Vistas/RegistrarUsuarios.jsp").forward(request, response);//
            //  break;

            case "registrar":
                registrarUnidMedida(request, response);
                break;

            case "listar":
                listarUnidad(request, response);
                break;

            case "editar":
                editarProductos(request, response);
                break;

            case "editar2":
                request.setAttribute("EditarProd", request.getParameter("id"));
                request.getRequestDispatcher("Vistas/EditarProducto.jsp").forward(request, response);
                break;

            case "actualizar":
                actualizarProductos(request, response);
                break;

            case "eliminar":
                eliminarUnidad(request, response);
                break;

            default:
                // Handle any other actions or provide a default behavior
                break;
        }
    }

    private void registrarUnidMedida(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            // UnidadMedida unidadMedida= new UnidadMedida();
            unidadMedida.setUnidadMedida(request.getParameter("unidadMedida"));

            if (DaoUnidMedida.grabar(unidadMedida)) {
                request.setAttribute("mensaje", "el registro fue exitoso");
            } else {
                request.setAttribute("mensaje", "error en el registro, validar campos ingresados");
            }

            listarUnidad(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar el Consecutivo");
            listarUnidad(request, response);
        }
    }

    private void listarUnidad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<UnidadMedida> lt = DaoUnidMedida.listar();
            request.setAttribute("listaUnidad", lt);
            request.getRequestDispatcher("Vistas/ListaUnidadMedida.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al listar los Consecutivos");
            request.getRequestDispatcher("Vistas/ListaUnidadMedida.jsp").forward(request, response);
        }
    }

    private void editarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el ID del parámetro de solicitud y almacenarlo en la variable de instancia
            ide = Integer.parseInt(request.getParameter("id"));
            UnidadMedida UN = DaoUnidMedida.obtenerUnidadPorId(ide);
            request.setAttribute("Unid", UN);
            request.setAttribute("updated", true);
            listarUnidad(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al editar el Consecutivo");
            listarUnidad(request, response);
        }
    }

    private void actualizarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            // Utilizar la variable de instancia para obtener el ID
            UnidadMedida UN = DaoUnidMedida.obtenerUnidadPorId(ide);
            request.setAttribute("Unid", UN);

            // UnidadMedida unidadM = new UnidadMedida();
            unidadMedida.setUnidadMedida(request.getParameter("unidadMedida"));
            unidadMedida.setIdUnidadMedida(ide);

            boolean actualizacionExitosa = DaoUnidMedida.editar(unidadMedida);

            String mensaje = actualizacionExitosa ? "Consecutivo actualizado correctamente" : "No se pudo actualizar el Consecutivo";
            request.setAttribute("mensaje", mensaje);

            listarUnidad(request, response);

        } catch (IOException | NumberFormatException | ServletException ex) {
            request.setAttribute("mensaje", "Error al actualizar el Consecutivo: " + ex.getMessage());
            listarUnidad(request, response);
        }
    }

    private void eliminarUnidad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            if (DaoUnidMedida.eliminar(id)) {
                request.setAttribute("mensaje", "El Registro fue Eliminado Correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo eliminar el Registro");
            }

            listarUnidad(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminar el registro");
            listarUnidad(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
        // Ensure UTF-8 encoding
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

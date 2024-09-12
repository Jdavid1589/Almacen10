package Controlador;

import Modelo.Perfil;
import Modelo.Usuarios;
import Persistencia.DaoPerfil;
import Persistencia.DaoUsuarios;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorPerfil extends HttpServlet {

    Perfil user = new Perfil();
    int ide;
    DaoPerfil per = new DaoPerfil();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        String action = request.getParameter("accion");
        //  String acceso = "";

        switch (action) {
            //   case "add":
            //  request.getRequestDispatcher("Vistas/RegistrarUsuarios.jsp").forward(request, response);//
            //  break;

            case "registrar":
                registrarPerfil(request, response);
                break;

            case "listar":
                listarPerfil(request, response);
                break;

            case "editar":
                editarPerfil(request, response);
                break;
            case "buscar":
                buscarPerfil(request, response);
                break;

            case "editarPerfil":
                actualizarPerfil(request, response);
                break;

            case "buscarPerfil":
                buscarPerfil(request, response);
                break;

            case "eliminar":
                eliminarPerfil(request, response);
                break;

            default:
                // Handle any other actions or provide a default behavior
                break;
        }
    }

    private void registrarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            Perfil perfil = new Perfil();

            // . Se toma la inforacion  del for y se le pasa directamente al obj
            perfil.setTipoperfil(request.getParameter("tipoperfil"));

            if (DaoPerfil.grabar(perfil)) {
                request.setAttribute("mensaje", "el perfil  fue registrado");
            } else {
                request.setAttribute("mensaje", "el perfil no fue registrado, validar campos ingresados");
            }

            List<Perfil> lt = DaoPerfil.listar();
            request.setAttribute("listaPerfil", lt);
            request.getRequestDispatcher("Vistas/ListaPerfil.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar el Consecutivo");
            listarPerfil(request, response);
        }
    }

    private void listarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Perfil> lt = DaoPerfil.listar();
            request.setAttribute("listaPerfil", lt);
            request.getRequestDispatcher("Vistas/ListaPerfil.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al listar los Consecutivos");
            request.getRequestDispatcher("Vistas/ListaPerfil.jsp").forward(request, response);
        }
    }

    private void editarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el ID del parámetro de solicitud y almacenarlo en la variable de instancia
            ide = Integer.parseInt(request.getParameter("id"));
            Perfil p = DaoPerfil.obtenerPerfilPorId(ide);
            request.setAttribute("User", p);

            listarPerfil(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al editar el Consecutivo");
            listarPerfil(request, response);
        }
    }

    private void actualizarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Utilizar la variable de instancia para obtener el ID
            Perfil pe = DaoPerfil.obtenerPerfilPorId(ide);
            request.setAttribute("User", pe);

            Perfil perfil1 = new Perfil();

            perfil1.setTipoperfil(request.getParameter("tipoperfil"));
            perfil1.setIdPerfil(ide);

            boolean actualizacionExitosa = DaoPerfil.editar(perfil1);

            if (actualizacionExitosa) {
                request.setAttribute("mensaje", "Consecutivo actualizado correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo actualizar el Consecutivo");
            }

            List<Perfil> lt = DaoPerfil.listar();
            request.setAttribute("listaPerfil", lt);
            request.getRequestDispatcher("Vistas/ListaPerfil.jsp").forward(request, response);

        } catch (IOException | NumberFormatException | ServletException ex) {
            request.setAttribute("mensaje", "Error al actualizar el Consecutivo: " + ex.getMessage());
            listarPerfil(request, response);
        }
    }

    private void eliminarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idPerfil = Integer.parseInt(request.getParameter("id"));

            if (DaoPerfil.eliminar(idPerfil)) {
                request.setAttribute("mensaje", "El Perfil fue Eliminado Correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo eliminar el Perfil");
            }

            List<Perfil> lt = DaoPerfil.listar();
            request.setAttribute("listaPerfil", lt);
            request.getRequestDispatcher("Vistas/ListarPerfil.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminar el Consecutivo");
            listarPerfil(request, response);
        }
    }

    private void buscarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String texto = request.getParameter("txtbuscar");
            List<Perfil> lt = DaoPerfil.buscarPerfil(texto);
            request.setAttribute("listaPerfil", lt);
            request.getRequestDispatcher("Vistas/ListaPerfil.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al buscar los Consecutivos");
            request.getRequestDispatcher("Vistas/ListaPerfil.jsp").forward(request, response);
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

package Controlador;

import Modelo.Documento;
import Persistencia.DaoDocumento;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorDocumento extends HttpServlet {

    Documento doc = new Documento();
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
                registrarDocumento(request, response);
                break;

            case "listar":
                listarDocumento(request, response);
                break;

            case "editar":
                editarDocumento(request, response);
                break;

            case "editarDocumento":
                actualizarDocumento(request, response);
                break;
                
                    case "buscar":
                buscarDocumento(request, response);
                break;
             

            case "eliminar":
                eliminarDocumento(request, response);
                break;

            default:
                // Handle any other actions or provide a default behavior
                break;
        }
    }

    private void registrarDocumento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            int idDocumento = Integer.parseInt(request.getParameter("id_Documento"));
            String tipoDocumento = request.getParameter("tipoDocumento");
            int usuarios_idUsuarios = Integer.parseInt(request.getParameter("usuarios_idUsuarios"));

            Documento documento = new Documento();

            documento.setIdDocumento(idDocumento);
            documento.setTipoDocumento(tipoDocumento);
            documento.setUsuarios_idUsuarios(usuarios_idUsuarios);

            if (DaoDocumento.grabar(documento)) {
                request.setAttribute("mensaje", "el documento fue registrado");
            } else {
                request.setAttribute("mensaje", "el documento no fue registrado, validar campos ingresados");
            }

            List<Documento> lt = DaoDocumento.listar();
            request.setAttribute("listaDocumento", lt);
            request.getRequestDispatcher("Vistas/ListaDocumento.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar el Consecutivo");
            listarDocumento(request, response);
        }
    }

    private void listarDocumento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Documento> lt = DaoDocumento.listar();
            request.setAttribute("listaDocumento", lt);
            request.getRequestDispatcher("Vistas/ListaDocumento.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al listar los Consecutivos");
            request.getRequestDispatcher("Vistas/ListaDocumento.jsp").forward(request, response);
        }
    }

    private void editarDocumento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el ID del parámetro de solicitud y almacenarlo en la variable de instancia
            ide = Integer.parseInt(request.getParameter("id"));
            Documento doc = DaoDocumento.obtenerDocumentoPorId(ide);
            request.setAttribute("User", doc);

            List<Documento> lt = DaoDocumento.listar();
            request.setAttribute("listaDocumento", lt);
            request.getRequestDispatcher("Vistas/ListaDocumento.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al editar el Consecutivo");
            listarDocumento(request, response);
        }
    }

    private void actualizarDocumento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Utilizar la variable de instancia para obtener el ID
            Documento doc = DaoDocumento.obtenerDocumentoPorId(ide);
            request.setAttribute("User", doc);

            // Resto del código para actualizar el Consecutivo
            int idDocumento = Integer.parseInt(request.getParameter("id_Documento"));
            String tipoDocumento = request.getParameter("tipoDocumento");
            int usuarios_idUsuarios = Integer.parseInt(request.getParameter("usuarios_idUsuarios"));
            //int txtid = Integer.parseInt(request.getParameter("txtid"));

            Documento documento = new Documento();

            documento.setIdDocumento(idDocumento);
            documento.setTipoDocumento(tipoDocumento);
            documento.setUsuarios_idUsuarios(usuarios_idUsuarios);
            documento.setIdDocumento(ide);

            boolean actualizacionExitosa = DaoDocumento.editar(documento);

            if (actualizacionExitosa) {
                request.setAttribute("mensaje", "Consecutivo actualizado correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo actualizar el Consecutivo");
            }

            List<Documento> lt = DaoDocumento.listar();
            request.setAttribute("listaDocumento", lt);
            request.getRequestDispatcher("Vistas/ListaDocumento.jsp").forward(request, response);

        } catch (IOException | NumberFormatException | ServletException ex) {
            request.setAttribute("mensaje", "Error al actualizar el Consecutivo: " + ex.getMessage());
            listarDocumento(request, response);
        }
    }

    private void eliminarDocumento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idDocumento = Integer.parseInt(request.getParameter("id"));

            if (DaoDocumento.eliminar(idDocumento)) {
                request.setAttribute("mensaje", "El documento fue Eliminado Correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo eliminar el documento");
            }

            List<Documento> lt = DaoDocumento.listar();
            request.setAttribute("listaDocumento", lt);
            request.getRequestDispatcher("Vistas/ListarDocumento.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminar el Consecutivo");
            listarDocumento(request, response);
        }
    }
       private void buscarDocumento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String texto = request.getParameter("txtbuscar");
            List<Documento> lt = DaoDocumento.buscarDocumento(texto);
            request.setAttribute("listaDocumento", lt);
         request.getRequestDispatcher("Vistas/ListaDocumento.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al buscar los Consecutivos");
  request.getRequestDispatcher("Vistas/ListaDocumento.jsp").forward(request, response);
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

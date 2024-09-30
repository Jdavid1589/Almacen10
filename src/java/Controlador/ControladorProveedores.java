package Controlador;

import Modelo.Proveedores;
import Persistencia.DaoProveedores;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorProveedores extends HttpServlet {

    Proveedores user = new Proveedores();
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
        String action = request.getParameter("accion");

        switch (action) {
            case "registrar":
                registrarProveedores(request, response);
                break;

            case "listar":
                listarProveedores(request, response);
                break;
            case "listar2":
                listarProveedores2(request, response);
                break;

            case "editar":
                editarProveedores(request, response);
                break;

            case "editarProveedores":
                actualizarProveedores(request, response);
                break;

            case "buscar":
                buscarProveedores(request, response);
                break;

            case "eliminar":
                eliminarProveedores(request, response);
                break;

            default:
                // Handle any other actions or provide a default behavior
                break;
        }
    }

    //metodos
    private void registrarProveedores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Proveedores proveedor = new Proveedores();

            proveedor.setProveedor(request.getParameter("proveedor"));
            proveedor.setTelefono(request.getParameter("telefono"));
            proveedor.setAsesor(request.getParameter("asesor"));

            if (DaoProveedores.grabar(proveedor)) {

                request.setAttribute("mensaje", "el Proveedor fue registrado");
            } else {
                request.setAttribute("mensaje", "el Proveedor no fue registrado, validar campos ingresados");
            }

            listarProveedores(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar el Consecutivo");
            listarProveedores(request, response);
        }
    }

    private void listarProveedores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Proveedores> lt = DaoProveedores.listar();
            request.setAttribute("listaProveedores", lt);
            request.getRequestDispatcher("Vistas/ListaProveedores.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al listar los proveedores");
            request.getRequestDispatcher("Vistas/ListaProveedores.jsp").forward(request, response);
        }
    }

    private void listarProveedores2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper(); // Para convertir objetos Java a JSON    

        try {
            List<Proveedores> listaProveedores = DaoProveedores.listar();  // Obtenemos la lista de la base de datos

            PrintWriter out = response.getWriter();
            if (listaProveedores != null && !listaProveedores.isEmpty()) {
                objectMapper.writeValue(out, listaProveedores);  // Enviar la lista como JSON
            } else {
                // Si no hay proveedores, enviar un mensaje en formato JSON
                Map<String, String> responseMessage = new HashMap<>();
                responseMessage.put("mensaje", "No se encontraron proveedores.");
                objectMapper.writeValue(out, responseMessage);
            }
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();  // Depuración

            // Enviar mensaje de error en caso de excepción
            Map<String, String> responseMessage = new HashMap<>();
            responseMessage.put("mensaje", "Error al listar los proveedores.");
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, responseMessage);
            out.flush();
        }
    }

// Clase para la respuesta Json
    public class ResponseMessage {

        private String mensaje;

        public ResponseMessage() {
        }

        public ResponseMessage(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }

    private void editarProveedores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el ID del parámetro de solicitud y almacenarlo en la variable de instancia
            ide = Integer.parseInt(request.getParameter("id"));
            Proveedores pr = DaoProveedores.obtenerProveedoresPorId(ide);

            // Establecer la categoría a editar y una bandera para indicar que estamos editando
            request.setAttribute("ProvLista", pr);
            request.setAttribute("isEditing", true); // Indicador de edición

            listarProveedores(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al editar el consecutivo");
            listarProveedores(request, response);
        }
    }

    private void actualizarProveedores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Utilizar la variable de instancia para obtener el ID
            Proveedores pr = DaoProveedores.obtenerProveedoresPorId(ide);
            request.setAttribute("ProvLista", pr);

            Proveedores prov = new Proveedores();

            prov.setProveedor(request.getParameter("proveedor"));
            prov.setTelefono(request.getParameter("telefono"));
            prov.setAsesor(request.getParameter("asesor"));

            prov.setIdProveedor(ide);

            boolean actualizacionExitosa = DaoProveedores.editar(prov);

            if (actualizacionExitosa) {
                request.setAttribute("mensaje", "Proveedor actualizado correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo actualizar el proveedor");
            }

            // Limpia los campos
            request.setAttribute("ProvLista", new Proveedores());

            listarProveedores(request, response);

        } catch (IOException | NumberFormatException | ServletException ex) {
            request.setAttribute("mensaje", "Error al actualizar el proveedor: " + ex.getMessage());

            listarProveedores(request, response);
        }
    }

    private void eliminarProveedores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idProveedores = Integer.parseInt(request.getParameter("id"));

            if (DaoProveedores.eliminar(idProveedores)) {
                request.setAttribute("mensaje", "El proveedor fue eliminado correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo eliminar el proveedor");
            }

            listarProveedores(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminar el proveedor");
            listarProveedores(request, response);
        }

    }

    private void buscarProveedores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String texto = request.getParameter("txtbuscar");
            List<Proveedores> lt = DaoProveedores.buscarProveedores(texto);
            request.setAttribute("listaProveedores", lt);
            request.getRequestDispatcher("Vistas/ListaProveedores.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al buscar los proveedores");
            request.getRequestDispatcher("Vistas/ListaProveedores.jsp").forward(request, response);
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
    }
}

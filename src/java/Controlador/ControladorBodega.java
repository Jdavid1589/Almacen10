package Controlador;
//import config conexion;//

import Modelo.Bodega;
import Persistencia.DaoBodega;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorBodega extends HttpServlet {

    Bodega bode = new Bodega();
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
            //case "add"://
            // request.getRequestDispatcher("Vistas/RegistrarBodega.jsp").forward(request, response);//
            // break;

            case "registrar":
                registrarBodega(request, response);
                break;

            case "listar":
                listarBodega(request, response);
                break;

            case "editar":
                editarBodega(request, response);
                break;

            case "editarBodega":
                actualizarBodega(request, response);
                break;

            case "buscarBodega":
                buscarBodega(request, response);
                break;

            case "eliminar":
                eliminarBodega(request, response);
                break;

            default:
                // Handle any other actions or provide a default behavior
                break;
        }
    }

    private void registrarBodega(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Bodega bodega1 = new Bodega();

            bodega1.setCantidadInventario(Double.valueOf(request.getParameter("cantidadInventario")));
            bodega1.setUnidadMedida(request.getParameter("unidadMedida"));
            bodega1.setCosto(Double.valueOf(request.getParameter("costo")));
            bodega1.setProductos_idProductos(Integer.parseInt(request.getParameter("productos_idProductos")));
            bodega1.setMovimientos_Id(Integer.parseInt(request.getParameter("movimientos_Id")));

            if (DaoBodega.grabar(bodega1)) {
                request.setAttribute("mensaje", " la bodega fue registrada");
            } else {
                request.setAttribute("mensaje", "la bodega no fue registrada, validar campos ingresados");
            }

          listarBodega(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar el Consecutivo");
            listarBodega(request, response);
        }
    }

    private void listarBodega(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Bodega> lt = DaoBodega.listar();
            request.setAttribute("listaBodega", lt);
            request.getRequestDispatcher("Vistas/ListaBodega.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al listar los Consecutivos");
            request.getRequestDispatcher("Vistas/ListaBodega.jsp").forward(request, response);
        }
    }

    private void editarBodega(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el ID del parámetro de solicitud y almacenarlo en la variable de instancia
            ide = Integer.parseInt(request.getParameter("id"));
            Bodega b = DaoBodega.obtenerBodegaPorId(ide);
            request.setAttribute("User", b);

            listarBodega(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al editar el Consecutivo");
            listarBodega(request, response);
        }
    }

    private void actualizarBodega(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Utilizar la variable de instancia para obtener el ID
            Bodega b = DaoBodega.obtenerBodegaPorId(ide);
            request.setAttribute("User", b);

            // Resto del código para actualizar el Consecutivo
            double cantidadInventario = Double.parseDouble(request.getParameter("cantidadInventario"));
            String unidadMedida = request.getParameter("unidadMedida");
            double costo = Double.parseDouble(request.getParameter("costo"));
            int productos_idproductos = Integer.parseInt(request.getParameter("productos_idproductos"));
            int recibo_idRecibo = Integer.parseInt(request.getParameter("recibo_idrecibo"));
            int movimientos_Id = Integer.parseInt(request.getParameter("movimientosId"));
            //int txtid = Integer.parseInt(request.getParameter("txtid"));

            Bodega bodega2 = new Bodega();

            bodega2.setProductos_idProductos(productos_idproductos);
            bodega2.setCantidadInventario(cantidadInventario);
            bodega2.setUnidadMedida(unidadMedida);
            bodega2.setCosto(costo);
            bodega2.setMovimientos_Id(movimientos_Id);
            bodega2.setIdBodega(ide);

            boolean actualizacionExitosa = DaoBodega.editar(bodega2);

            if (actualizacionExitosa) {
                request.setAttribute("mensaje", "Consecutivo actualizado correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo actualizar el Consecutivo");
            }

            List<Bodega> lt = DaoBodega.listar();
            request.setAttribute("listaBodega", lt);
            request.getRequestDispatcher("Vistas/ListaBodega.jsp").forward(request, response);

        } catch (IOException | NumberFormatException | ServletException ex) {
            request.setAttribute("mensaje", "Error al actualizar el Consecutivo: " + ex.getMessage());
            listarBodega(request, response);
        }
    }

    private void eliminarBodega(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idBodega = Integer.parseInt(request.getParameter("id"));

            if (DaoBodega.eliminar(idBodega)) {
                request.setAttribute("mensaje", "la bodega fue Eliminado Correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo eliminar la bodega");
            }

           listarBodega(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminar el Consecutivo");
            listarBodega(request, response);
        }
    }

    private void buscarBodega(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String texto = request.getParameter("txtbuscar");
            List<Bodega> lt = DaoBodega.buscarBodega(texto);
            request.setAttribute("listaBodega", lt);
            request.getRequestDispatcher("Vistas/ListaBodega.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al buscar los Consecutivos");
            request.getRequestDispatcher("Vistas/ListaBodega.jsp").forward(request, response);
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

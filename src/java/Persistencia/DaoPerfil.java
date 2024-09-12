package Persistencia;

import Config.Conexion;
import Modelo.Perfil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*crear metodos*/

 /*adicionar*/
public class DaoPerfil {

      static Conexion cn = Conexion.getInstance();
    static Connection con;
    static PreparedStatement ps;
    static ResultSet rs;
    static Perfil perfil = new Perfil();

    public static boolean grabar(Perfil perfil) {

        try {
            con = cn.getConnection();
            String sql = "INSERT INTO perfil(tipoperfil)"
                    + "VALUES(?);";
            ps = con.prepareStatement(sql);
            ps.setString(1, perfil.getTipoperfil());

            if (ps.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
        } finally {
            cerrarRecursos();
        }
        return false;
    }

    public static List<Perfil> listar() {
        List<Perfil> lista = new ArrayList<>();
        try {
          con = cn.getConnection();
            String sql = "SELECT * FROM perfil;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Perfil perfil1 = new Perfil();

                perfil1.setIdPerfil(rs.getInt("idPerfil"));
                perfil1.setTipoperfil(rs.getString("tipoperfil"));

                lista.add(perfil1);

           
            }
        } catch (Exception e) {
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    //Se debe asegurar el mismo orden
    public static boolean editar(Perfil perfil) {

        try {
      con = cn.getConnection();
            String sql = "UPDATE perfil SET tipoperfil = ?, "
                    + "WHERE idPerfil =?";

            ps = con.prepareStatement(sql);

            ps.setString(1, perfil.getTipoperfil());
            ps.setInt(2, perfil.getIdPerfil());
            
            ps.executeUpdate();

            // Ejecutar la consulta de actualización
            int rowsAffected = ps.executeUpdate();

            // Verificar si se actualizó algún registro
            if (rowsAffected > 0) {
                return true; // La actualización fue exitosa

            }

        } catch (SQLException ex) {

        } finally {
            // Cerrar recursos (conexión, declaración, etc.) en el bloque "finally"
            // para garantizar que se cierren incluso si ocurre una excepción.
            cerrarRecursos();
        }
        return false;
    }

    public static boolean eliminar(int idPerfil) {
        try {
          con = cn.getConnection();
            String sql = "DELETE FROM perfil WHERE idPerfil=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idPerfil);

            if (ps.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Maneja las excepciones de mejor manera, por ejemplo, lanzando una excepción personalizada.
        } finally {
            cerrarRecursos();
        }
        return false;
    }


    public static Perfil obtenerPerfilPorId(int id) {
        Perfil perfil = null;

        String sql = "SELECT * FROM Perfil WHERE idPerfil=?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    perfil = new Perfil();
                    perfil.setIdPerfil(rs.getInt("idPerfil"));
                    perfil.setTipoperfil(rs.getString("tipoperfil"));

                }
            }
        } catch (SQLException ex) {
            // Registra el error sin mostrar detalles al usuario
            Logger.getLogger(DaoPerfil.class
                    .getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);

        }

        return perfil;
    }
    
     public static String obtenerNombrePerfil(int id) {
        try {
          con = cn.getConnection();
            String sql = "SELECT tipoperfil FROM perfil WHERE idPerfil=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("tipoperfil");
            } else {
                return "--";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "--";
    }
     
     public static List<Perfil> buscarPerfil(String texto) {
        List<Perfil> listaPerfil = new ArrayList<>();

        try {
     con = cn.getConnection();

            String sql = "SELECT * FROM perfil "
                    + "WHERE nombres LIKE ?"
                    + " OR perfil LIKE ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                 Perfil perfil1 = new Perfil();

                perfil1.setIdPerfil(rs.getInt("idPerfil"));
                perfil1.setTipoperfil(rs.getString("tipoperfil"));

                listaPerfil.add(perfil1);
            }
        } catch (SQLException e) {
            // Manejo adecuado de la excepción (log, imprimir, etc.)
            e.printStackTrace();
        } finally {
            // Asegúrate de cerrar los recursos (ResultSet, PreparedStatement, Connection)
            cerrarRecursos();
        }

        return listaPerfil;
    }
     
     

    //**********************************************
    //metodo jasypt para encriptar contraseña
    //Nota:Se debe manejar el mismo orden tanto en l asentencia Sql como en el ps
    // Agrega este método para cerrar las conexiones y recursos
    private static void cerrarRecursos() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

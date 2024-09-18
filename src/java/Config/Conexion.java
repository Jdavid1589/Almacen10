package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
// Almacen
public class Conexion {
      private static final String DATABASE_NAME = "almacen13";
   // private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + "?useUnicode=true&characterEncoding=UTF-8";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "jdproot123456";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static Conexion instancia;
    private Connection connection;
    private static final Logger logger = Logger.getLogger(Conexion.class.getName());

    private Conexion() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            logger.info("Conexión Exitosa a la BD: " + DATABASE_NAME);
        } catch (ClassNotFoundException | SQLException ex) {
            logger.log(Level.SEVERE, "Error al conectar a la base de datos", ex);
        }
    }

    public static synchronized Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al obtener la conexión", ex);
        }
        return connection;
    }

    public synchronized void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Conexión cerrada");
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error al cerrar la conexión", ex);
        }
    }
}

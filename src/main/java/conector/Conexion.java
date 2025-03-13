package conector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String DB_NAME = "gestion_eventos";  
    private static final String USER = "postgres";  
    private static final String PASSWORD = "admin";  
    private static final String URL = "jdbc:postgresql://localhost:5432/" + DB_NAME;

    private Connection connection;

    public Conexion() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión a la base de datos '" + DB_NAME + "' correcta.");
        } catch (SQLException e) {
            System.err.println("Error de conexión a la base de datos.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver de PostgreSQL no encontrado.");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión.");
            e.printStackTrace();
        }
    }
}

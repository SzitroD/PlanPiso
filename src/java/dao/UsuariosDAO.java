package dao;

import control.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariosDAO {
    
    //Validar que el usuario este registrado dentro de la base de datos
   public Boolean validarUsuario(String usuario, String contraseña) {
        Boolean status = false;
        Connection con = new ConexionMySQL().conectarPP();
        PreparedStatement ps = null;
        ResultSet rs = null;
        if (con != null) {
            String sql = "SELECT id_usuario, usuario , contraseña \n" +
                "FROM usuario \n" +
                "WHERE usuario = ? AND contraseña = ? ";
            try {
                ps = con.prepareStatement(sql);
                ps.setString(1, usuario);
                ps.setString(2, contraseña);
                // System.out.println("Query: \n" + ps.toString());
                rs = ps.executeQuery();
                if (rs.next()) {
                    status = true;
                }
            } catch (SQLException e) {
                System.out.println("ERROR validarUsuario: " + e.getSQLState() + ": " + e.getMessage());
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                    ps = null;
                    rs = null;
                    con.close();
                } catch (SQLException ee) {
                    System.out.println("ERROR SQL: " + ee.getSQLState() + ee.getMessage());
                }
            }
        } else {
            System.out.println("ERROR SQL: Conexión nula");
        }
        return status;
    }
    
}

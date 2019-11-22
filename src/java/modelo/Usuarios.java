package modelo;

public class Usuarios {
    private int idUsuario;
    private String nombre;
    private String usuario;
    private String contrasena;
    private int status;

    public Usuarios() {
        this.idUsuario = 0;
        this.nombre = "";
        this.usuario = "";
        this.contrasena = "";
        this.status = 0;
    }
    
    public Usuarios(int idUsuario, String nombre, String usuario, String contrasena, int status) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.status = status;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
}

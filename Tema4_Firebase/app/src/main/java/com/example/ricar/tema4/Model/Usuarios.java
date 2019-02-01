package com.example.ricar.tema4.Model;

public class Usuarios {
    private String Email;
   private String NombreUsuario;
   private String Nombre;
   private String Apellidos;
   private String Direccion;


    public Usuarios(String Email, String NombreUsuario, String Nombre, String Apellidos, String Direccion) {
        this.Email = Email;
        this.NombreUsuario = NombreUsuario;
        this.Nombre =  Nombre;
        this.Apellidos = Apellidos;
        this.Direccion = Direccion;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "Email='" + Email + '\'' +
                ", NombreUsuario='" + NombreUsuario + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Apellidos='" + Apellidos + '\'' +
                ", Direccion='" + Direccion + '\'' +
                '}';
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }
}

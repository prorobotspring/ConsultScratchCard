package ve.com.digitel.scratch.to;

import java.util.ArrayList;

/**
 * Usuario es un JavaBeans que se encarga de representar
 * los registros de las tablas cls_user, cls_user_rol.
 */
public class Usuario {

	private String login;
	private String clave;
	private String rol;
	private String nombre;
	private ArrayList modulos;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public ArrayList getModulos() {
		return modulos;
	}

	public void setModulos(ArrayList modulos) {
		this.modulos = modulos;
	}
	
}

package ve.com.digitel.bssint.util;

/**
 * Interface para la creacion de objetos del pool
 * 
 * @author Diego Leal
 * <br>	   BSS - CC Areas Tecnicas - Integracion
 *
 */
public interface CreatorIF {

	/**
	 * Realiza la creacion de un objeto del pool
	 * 
	 * @return Objeto creado
	 * @throws Exception En caso de error
	 */
	public Object create() throws Exception;
}

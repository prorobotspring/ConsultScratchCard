/* 
 * Fecha.java
 * Created on 16/02/2005
 *
 */

package ve.com.digitel.bssint.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Clase con m�todos est�ticos para manipular fechas
 * 
 * @author Grupo Integracion tecnica Kenan
 */

public class Fecha {
	public static final int ENTRE_30_ANTES_Y_3_DESPUES = 1;
	public static final int OTRO_RANGO = 2;

	/**
	 * Metodo que convierte un string que representa una fecha en un objeto
	 * <code>Date</code>.
	 * 
	 * @param fecha
	 *            <code>String</code> con la fecha a ser convertida a
	 *            <code>Date</code>
	 * @param formato
	 *            <code>String</code> con el formato que se acepta la fecha .
	 *            Los valores permitidos son:
	 *            <p>
	 *            <blockout>
	 * 
	 * <pre>
	 * YYYY - MM - DD
	 * </pre>
	 * 
	 * <blockout>
	 * @return varfecha Objeto Date con la fecha suministrada como par�metro,
	 *         de recivir un formato no reconocido, retorna <code>null</code>.
	 * @throws Exception
	 *             "Se envio una fecha cuya longitud mo coincide con la del
	 *             formato"
	 */

	public static Date StringToDate(String fecha, String formato) throws Exception {

		// Declaraciones de variables de trabajo
		int varano, varmes, vardia;
		int varhh, varmm, varss;
		String varpto;

		Calendar ObjFecha = Calendar.getInstance(TimeZone.getTimeZone("GMT-4:30"));
		ObjFecha.clear();
		ObjFecha.setLenient(false);
		Date varfecha = null;

		if (fecha.length() != formato.length()) {
			throw new Exception("Se  envio  una  fecha " + "cuya longitud mo coincide con la del formato");
		} // fin del IF

		try {
			if (formato.equals("YYYY-MM-DD")) {
				varano = Integer.parseInt(fecha.substring(0, 4));
				varmes = Integer.parseInt(fecha.substring(5, 7));
				vardia = Integer.parseInt(fecha.substring(8, 10));

				ObjFecha.set(varano, varmes - 1, vardia);

			} // Fin del IF
			else if (formato.equals("YYYYMMDD.HHMMSS")) {
				varano = Integer.parseInt(fecha.substring(0, 4));
				varmes = Integer.parseInt(fecha.substring(4, 6));
				vardia = Integer.parseInt(fecha.substring(6, 8));

				varpto = fecha.substring(8, 9);

				if (!varpto.equals("."))
					throw new Exception("El separador entre la fecha y la hora es Incorrecto");

				varhh = Integer.parseInt(fecha.substring(9, 11));
				varmm = Integer.parseInt(fecha.substring(11, 13));
				varss = Integer.parseInt(fecha.substring(13, 15));

				ObjFecha.set(varano, varmes - 1, vardia, varhh, varmm, varss);

			} // Fin del IF
			else if (formato.equals("YYYYMMDDHHMMSS")) {
				varano = Integer.parseInt(fecha.substring(0, 4));
				varmes = Integer.parseInt(fecha.substring(4, 6));
				vardia = Integer.parseInt(fecha.substring(6, 8));

				varhh = Integer.parseInt(fecha.substring(8, 10));
				varmm = Integer.parseInt(fecha.substring(10, 12));
				varss = Integer.parseInt(fecha.substring(12, 14));

				ObjFecha.set(varano, varmes - 1, vardia, varhh, varmm, varss);

			} // Fin del IF
			else if (formato.equals("YYYYMMDD")) {
				varano = Integer.parseInt(fecha.substring(0, 4));
				varmes = Integer.parseInt(fecha.substring(4, 6));
				vardia = Integer.parseInt(fecha.substring(6, 8));

				ObjFecha.set(varano, varmes - 1, vardia);

			} // Fin del IF
			else if (formato.equals("DD-MM-YYYY HH:MM:SS")) {

				varano = Integer.parseInt(fecha.substring(0, 2));
				varmes = Integer.parseInt(fecha.substring(3, 5));
				vardia = Integer.parseInt(fecha.substring(6, 10));

				varpto = fecha.substring(10, 11);

				if (!varpto.equals(" "))
					throw new Exception("El separador entre la fecha y la hora es Incorrecto");

				varhh = Integer.parseInt(fecha.substring(11, 13));
				varmm = Integer.parseInt(fecha.substring(14, 16));
				varss = Integer.parseInt(fecha.substring(17, 18));

				ObjFecha.set(varano, varmes - 1, vardia, varhh, varmm, varss);
			} else if (formato.equals("yyyy-MM-ddTHH:mm:ss.0000000-04:30")) {

				varano = Integer.parseInt(fecha.substring(0, 4));
				varmes = Integer.parseInt(fecha.substring(5, 7));
				vardia = Integer.parseInt(fecha.substring(8, 10));

				varpto = fecha.substring(10, 11);

				if (!varpto.equals("T"))
					throw new Exception("El separador entre la fecha y la hora es Incorrecto");

				varhh = Integer.parseInt(fecha.substring(11, 13));
				varmm = Integer.parseInt(fecha.substring(14, 16));
				varss = Integer.parseInt(fecha.substring(17, 18));

				ObjFecha.set(varano, varmes - 1, vardia, varhh, varmm, varss);
			} else if (formato.equals("DDMMYYYY")) {
				varano = Integer.parseInt(fecha.substring(4, 8));
				varmes = Integer.parseInt(fecha.substring(2, 4));
				vardia = Integer.parseInt(fecha.substring(0, 2));

				ObjFecha.set(varano, varmes - 1, vardia);

			} // Fin del IF
			else {
				throw new Exception("Se  envio  un  formato  de  texto  no  " + "reconocido");
			} // Fin del ELSE
		} // Fin del TRY
		catch (NumberFormatException e) {
			throw new Exception("Error por tipo de Dato. " + "Verifique que la informaci�n sea num�rica");
		} // Fin del CATCH

		try {
			varfecha = ObjFecha.getTime();
		} // Fin del TRY
		catch (IllegalArgumentException iae) {
			throw new Exception("Valores de la fecha Incorrectos. " + "Verifique el rango del d�a y mes");
		} // Fin del CATCH

		return varfecha;
	} // Fin de convertir

	/**
	 * Metodo que transforma un objeto <code>Date</code> en un
	 * <code>String</code> que representa a esa fecha en un formato
	 * especificado.
	 * 
	 * @param fecha
	 *            <code>Date</code> a ser codificado en un <code>String</code>.
	 * @param formato
	 *            <code>String</code> con un formato predeterminado. Los
	 *            valores aceptados son:
	 *            <p>
	 *            <blockout>
	 * 
	 * <pre>
	 * .YYYYMMDD-HHMMSS
	 * </pre>
	 * 
	 * </blockout>
	 * <p>
	 * <blockout>
	 * 
	 * <pre>
	 * YYYYMMDD_HHMMSS
	 * </pre>
	 * 
	 * </blockout>
	 * @return salida <code>String</code> con una representacion de la fecha
	 *         indicada como par�metro segun el formato especificado, de no
	 *         reconocerse el formato retorna <code>null</code>.
	 */
	public static String dateToString(Date fecha, String formato) {
		String salida = null;

		Calendar calendario = Calendar.getInstance(TimeZone.getTimeZone("GMT-4:30"));
		calendario.setTime(fecha);

		if (formato.equals("yyyy-MM-ddTHH:mm:ssZ")) {
			salida = "" + Fecha.intToString(calendario.get(Calendar.YEAR)) + "-"
					+ Fecha.intToString(calendario.get(Calendar.MONTH) + 1) + "-"
					+ Fecha.intToString(calendario.get(Calendar.DATE)) + "T"
					//+ Fecha.intToString(calendario.get(Calendar.HOUR_OF_DAY)) + ":"
					+ Fecha.intToString(calendario.get(Calendar.HOUR)) + ":"
					+ Fecha.intToString(calendario.get(Calendar.MINUTE)) + ":"
					+ Fecha.intToString(calendario.get(Calendar.SECOND)) + ".000Z";
		}
		if (formato.equals("YYYY-MM-DD HH:MM:SS")) {
			salida = Fecha.intToString(calendario.get(Calendar.YEAR)).concat("-").concat(
					Fecha.intToString(calendario.get(Calendar.MONTH) + 1)).concat("-").concat(
					Fecha.intToString(calendario.get(Calendar.DATE))).concat(" ").concat(
					Fecha.intToString(calendario.get(Calendar.HOUR_OF_DAY))).concat(":").concat(
					Fecha.intToString(calendario.get(Calendar.MINUTE))).concat(":").concat(
					Fecha.intToString(calendario.get(Calendar.SECOND)));
		}
		
		if (formato.equals("DD-MM-YYYY HH:MM:SS")) {
			salida = Fecha.intToString(calendario.get(Calendar.DATE)).concat("-").concat(
					Fecha.intToString(calendario.get(Calendar.MONTH) + 1)).concat("-").concat(
					Fecha.intToString(calendario.get(Calendar.YEAR))).concat(" ").concat(
					Fecha.intToString(calendario.get(Calendar.HOUR))).concat(":").concat(
					Fecha.intToString(calendario.get(Calendar.MINUTE))).concat(":").concat(
					Fecha.intToString(calendario.get(Calendar.SECOND)));
		}

		if (formato.equals("YYYY-MM-DD")) {
			salida = Fecha.intToString(calendario.get(Calendar.YEAR)) + "-"
					+ Fecha.intToString(calendario.get(Calendar.MONTH) + 1) + "-"
					+ Fecha.intToString(calendario.get(Calendar.DATE) + 1);
		}
		if (formato.equals("DD/MM/YYYY")) {
			salida = Fecha.intToString(calendario.get(Calendar.DATE)) + "/"
					+ Fecha.intToString(calendario.get(Calendar.MONTH) + 1) + "/"
					+ Fecha.intToString(calendario.get(Calendar.YEAR));
		}

		return salida;
	} // Fin metodo convertir()

	/**
	 * M�todo que a�ade a un n�mero entero que representa a un d�a del
	 * mes o a un mes del a�o un "0" en caso de que sean menores a 10.
	 * 
	 * @param num
	 *            <code>int</code> con el dia o mes que se desea completar.
	 * @return El mismo <code>int</code> en caso de que sea 10 o mayor, "0"
	 *         concatenado al n�mero en caso contrario.
	 */
	private static String intToString(int num) {
		return num < 10 ? "0" + num : "" + num;
	} // fin de m�todo intToString.

	/**
	 * Metodo que retorna un <code>java.util.Date</code> que representa a la
	 * fecha pasa como parametro
	 * 
	 * @param strFecha
	 *            String que posee la fecha
	 * @return fecha Fecha en formato date
	 */

	public static Date getFecha(String strFecha, String formato, boolean isUpperDate) {
		Date fecha = null;

		int varano, varmes, vardia;

		Calendar ObjFecha = Calendar.getInstance();
		ObjFecha.clear();
		ObjFecha.setLenient(false);
		if (formato.toUpperCase().equals("DDMMYYYY")) {
			vardia = Integer.parseInt(strFecha.substring(0, 2));
			varmes = Integer.parseInt(strFecha.substring(2, 4));
			varano = Integer.parseInt(strFecha.substring(4, 8));
			if (isUpperDate) {
				ObjFecha.set(varano, varmes - 1, vardia, 23, 59, 59);
			} else
				ObjFecha.set(varano, varmes - 1, vardia);
			fecha = ObjFecha.getTime();
		} // formato DDMMYYYY

		return fecha;
	} // fin de m�todo getFecha(String, String, boolean)

	/**
	 * Metodo que retorna un objeto java.sql.Date (fecha en formato SQL) (el
	 * cual es el formato de fecha con el que se pasa una fecha a una API) a
	 * partir de un objeto java.util.Date (fecha estandar)
	 * 
	 * @param Fecha
	 *            es un Date que posee la fecha a buscar
	 * @return fechaSQL Objeto java.sql.Date que es la fecha con formato SQL
	 */

	public static Date getFechaSQL(Date Fecha) {
		long milisegundos = Fecha.getTime();
		java.sql.Date FechaSQL = new java.sql.Date(milisegundos);
		return FechaSQL;
	}

	/**
	 * Metodo que convierte un numero entero en un String, siguiendo un formato
	 * de fecha especificado
	 * 
	 * @param num1
	 *            integer que posee la fecha sin ceros
	 * @param num2
	 *            Se refire al numero de ceros necesarios. Vendra dado por num1
	 * @return aux String que posee el formato establecido de fecha
	 */

	public static String fixWidthNumber(int num1, int num2) {
		String aux = "0";
		for (int i = 0; i < num2 - String.valueOf(num1).length(); i++) {
			aux = aux + "0";
		}
		;
		aux = aux + String.valueOf(num1);
		return (aux);
	} // fin fixWidthNumber(int, int)

	/**
	 * M�todo que valida el rango de fecha para los archivos de Entrada de las
	 * interfaces Batch
	 * 
	 * @param strFecha
	 *            String con el formato de fecha especifico
	 * @param rango
	 *            Integer que toma de una lista donde estan las variables
	 *            estaticas de la clase
	 * @return salida boolean que retorna true si la fehca pertenece al rango y
	 *         false en caso contrario
	 * @throws Exception
	 */

	public static boolean validarRango(String strFecha, int rango) throws Exception {
		boolean salida = false;

		switch (rango) {
		case Fecha.ENTRE_30_ANTES_Y_3_DESPUES:
			// Declaraci�n de Variables de Trabajo
			Calendar fechaMaquina = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+4"));
			Date fecha = Fecha.StringToDate(strFecha, "YYYYMMDD");
			fechaMaquina.add(Calendar.MONTH, -1);
			Date low = fechaMaquina.getTime();
			fechaMaquina.add(Calendar.MONTH, 1);
			fechaMaquina.add(Calendar.DATE, 3);
			Date high = fechaMaquina.getTime();
			fechaMaquina.add(Calendar.DATE, -3);

			if (fecha.after(low) && fecha.before(high))
				salida = true;
			else
				salida = false;
			break;
		} // Fin del switch

		return salida;
	} // Fin de validarRango(String,int)

} // Fin de class Fecha()

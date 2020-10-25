/**
 * Creado el 06/05/2008
 */
package ve.com.digitel.bssint;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase para la realizacion de SoftCast entre tipos
 * 
 * @author Diego Leal
 * <br>	   BSS - CC Areas Tecnicas - Integracion
 *
 */
public class SoftCast {
	
    /** Sets the named field to the passed value, converting the value from a String to the corrent type using <code>Type.valueOf()</code>
     * @param name The field name to set
     * @param value The String value to convert and set
     * @throws ParseException 
     */
    public static Object setString(Object objectValue, String fieldType, String format) throws ParseException{
       
    	String value = objectValue.toString();

        // if the string is all spaces ignore
        boolean nonSpace = false;

        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) != ' ') {
                nonSpace = true;
                break;
            }
        }
        
        if (!nonSpace)
            return null;

        // first the custom types that need to be parsed
        if (fieldType.equals("CustomTimestamp")) {
            // this custom type will take a string a parse according to date formatting
            // string then put the result in a java.sql.Timestamp
            // a common timestamp format for flat files is with no separators: yyyyMMddHHmmss
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            java.util.Date tempDate = sdf.parse(value);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(tempDate.getTime());

            return timestamp;
            
        } else if (fieldType.equals("CustomDate")) {
            // a common date only format for flat files is with no separators: yyyyMMdd or MMddyyyy
            /*SimpleDateFormat sdf = new SimpleDateFormat(format);
            java.util.Date tempDate = sdf.parse(value);
            java.sql.Date date = new java.sql.Date(tempDate.getTime());*/
            
            java.text.SimpleDateFormat antes = new java.text.SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
	        java.text.SimpleDateFormat despues = new java.text.SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" );

	        java.util.Date fechaParaCambiar = antes.parse(value);
	        String fechaFormateadaCorrectamente = despues.format(fechaParaCambiar);

            return fechaFormateadaCorrectamente;
            
        } else if (fieldType.equals("CustomTime")) {
            // a common time only format for flat files is with no separators: HHmmss
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            java.util.Date tempDate = sdf.parse(value);
            java.sql.Time time = new java.sql.Time(tempDate.getTime());

            return time;
            
        }else if (fieldType.equals("StrDate")) {
        	 
        	java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat( format );

		    return formato.format((Date)objectValue);
            
        }        else if (fieldType.equals("FixedPointDouble")) {
            // this custom type will parse a fixed point number according to the number
            // of decimal places in the formatting string then place it in a Double
            NumberFormat nf = NumberFormat.getNumberInstance();
            Number tempNum = nf.parse(value);
            double number = tempNum.doubleValue();
            double decimalPlaces = Double.parseDouble(format);
            double divisor = Math.pow(10.0, decimalPlaces);

            number = number / divisor;
            
            return new Double(number);
            
        } // standard types
        else if (fieldType.equals("java.lang.String") || fieldType.equals("String"))
        	
            return value;
        
        else if (fieldType.equals("NullTerminatedString")) {
            int terminate = value.indexOf(0x0);
            return terminate>0?value.substring(0,terminate):value;
            
        } else if (fieldType.equals("java.sql.Timestamp") || fieldType.equals("Timestamp"))
            return java.sql.Timestamp.valueOf(value);
        
        else if (fieldType.equals("java.sql.Time") || fieldType.equals("Time"))
        	return java.sql.Time.valueOf(value);
        
        else if (fieldType.equals("java.sql.Date") || fieldType.equals("Date"))
            return java.sql.Date.valueOf(value);
        
        else if (fieldType.equals("java.lang.Integer") || fieldType.equals("Integer"))
            return Integer.valueOf(value);
        else if (fieldType.equals("java.lang.Long") || fieldType.equals("Long"))
            return Long.valueOf(value);
        else if (fieldType.equals("java.lang.Float") || fieldType.equals("Float"))
            return Float.valueOf(value);
        else if (fieldType.equals("java.lang.Double") || fieldType.equals("Double"))
            return Double.valueOf(value);
        else if (fieldType.equals("java.lang.Boolean") || fieldType.equals("Double"))
            return Boolean.valueOf(value);
       /* else if (fieldType.equals("LEShort"))
            return new Short(readLEShort(value.getBytes()));
        else if (fieldType.equals("LEInteger"))
            return new Integer(readLEInt(value.getBytes()));
        else if (fieldType.equals("LELong"))
            return new Long(readLELong(value.getBytes()));*/
       else {
            throw new IllegalArgumentException("Field type " + fieldType + " not currently supported. Sorry.");
        }
    }

}

package ve.com.digitel.bssint.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import ve.com.digitel.bssint.log.BSSIntLogger;
import ve.com.digitel.bssint.log.LogWrapper;
import ve.com.digitel.bssint.messages.ConstantMessageCode;
import ve.com.digitel.bssint.messages.ErrorMessageHandler;

/**
 * Object Pool para el manejo de conexiones y disponibilidad de recursos
 * 
 * @author Diego Leal
 * <br>	   BSS - CC Areas Tecnicas - Integracion
 *
 */
public class ObjectPool {

	private static Logger logger = BSSIntLogger.getBSSIntLogger(ObjectPool.class);
	/**
	 * Esta coleccion contiene los objetos que estan esperando para ser usados
	 */
	private ArrayList pool;
	/**
	 * Objeto responsable de la creacion de los objetos a ser administrados
	 */
	private CreatorIF creator;
	/**
	 * Numero de objetos manejados en el pool actual
	 */
	private int instanceCount;
	/**
	 * Numero maximo de objetos manejados en el pool
	 */
	private int maxInstances;
	/**
	 * Clase que representa el tipo de objeto manejado en el pool
	 */
	private Class poolClass;

	/**
	 * 
	 * @param creator
	 * @param maxInstances Si maxInstances = -1 no tiene limite maximo de objetos
	 * @param className
	 */
	public ObjectPool(CreatorIF creator, int maxInstances, String className) {
		super();
		pool = new ArrayList();

		try {

			//poolClass = Class.forName("ve.com.digitel.nmo.NetworkAgent");

			poolClass = Class.forName(className, true, Thread.currentThread().getContextClassLoader());

		} catch (ClassNotFoundException e) {
			logger.error(LogWrapper.formatMessage(ConstantMessageCode.CLASS_NOT_FOUND_EXCEPTION, ErrorMessageHandler
					.getMsg(ConstantMessageCode.CLASS_NOT_FOUND_EXCEPTION)));
		}

		this.creator = creator;

		this.maxInstances = maxInstances;
	}

	/**
	 * Retorna el numero de objetos que estan esperando para ser reusados
	 * 
	 * @return Tamanio del pool
	 */
	public int getSize() {
		synchronized (pool) {
			return pool.size();
		}
	}

	/**
	 * Retorna el numero de objetos que actualmente estan siendo manejados
	 * 
	 * @return instanceCount
	 */
	public int getInstanceCount() {
		return instanceCount;
	}

	/**
	 * Retorna el numero maximo de objetos que este pool podra manejar
	 * 
	 * @return maxInstances
	 */
	public int getMaxInstances() {
		return maxInstances;
	}

	/**
	 * Fija el numero maximo de objetos que este pool podra manejar
	 * 
	 * @param Nuevo Valor
	 */
	public void setMaxInstances(int maxInstances) {
		this.maxInstances = maxInstances;
	}

	/**
	 * Retorna un objeto del pool, si no existe un objeto para ser reusado,
	 * se creara uno nuevo, siempre que se cumplan las condiciones del pool
	 * 
	 * @return Object
	 * @throws Exception 
	 */
	public Object getObject() throws Exception {

		synchronized (pool) {
			Object thisObject = removeObject();
			if (thisObject != null) {
				return thisObject;
			}
			if (getInstanceCount() < getMaxInstances() || getMaxInstances() == -1) {
				return createObject();
			} else {
				return null;
			}
		}
	}

	/**
	 * Retorna un objeto del pool, si no hay un objeto para reusar, espera por uno
	 * 
	 * @return
	 * @throws Exception 
	 */
	public Object waitForObject() throws Exception {

		synchronized (pool) {
			Object thisObject = removeObject();
			if (thisObject != null) {
				logger.debug("Objetp disponible en el Pool DNMO");
				return thisObject;
			}
			if (getInstanceCount() < getMaxInstances() || getMaxInstances() == -1) {
				logger.debug("Sin disponibilidad en Pool. Creando Objeto");
				return createObject();
			} else {
				try {
					do {
						logger.debug("Esperando por liberacion de objeto del Pool");
						pool.wait();
						thisObject = removeObject();
					} while (thisObject == null);
				} catch (InterruptedException e) {
					Integer code = new Integer(ConstantMessageCode.INTERRUPTED_EXCEPTION);

					// Imprimiendo codigo de error asociado
					logger.error("Codigo de Error: " + code + "\n\tMensaje de Error: "
							+ ErrorMessageHandler.getMsg(code.toString()) + "\n\tReason: " + e);
				}
				return thisObject;
			}
		}
	}

	/**
	 * Remueve un objeto del pool y lo retorna
	 * 
	 * @return
	 */
	private Object removeObject() {
		logger.debug("Verificando la existencia de objeto reutilizable");

		while (pool.size() > 0) {

			Object thisObject = pool.remove(pool.size() - 1);

			if (thisObject != null) {
				logger.debug("Objeto de conexion obtenido del POOL");
				return thisObject;
			}
			instanceCount--;
		}
		return null;
	}

	/**
	 * Crea un nuevo recurso de conexion
	 * 
	 * @return Objeto del Pool
	 * @throws Exception En caso de error
	 */
	private Object createObject() throws Exception {

		Object newObject = null;

		newObject = creator.create();

		if (newObject != null)
			instanceCount++;

		return newObject;
	}

	/**
	 * Libera un objeto del pool para su reutilizacion
	 * 
	 * @param obj
	 */
	public void release(Object obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		if (!poolClass.isInstance(obj)) {
			String actualClassName = obj.getClass().getName();
			throw new ArrayStoreException(actualClassName);
		}
		synchronized (pool) {
			pool.add(obj);

			pool.notify();
		}
	}

}

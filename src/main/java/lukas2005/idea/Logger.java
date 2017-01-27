package lukas2005.idea;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Logger {
	public static final Marker MOD_MARKER = MarkerManager.getMarker(Reference.MODID.toUpperCase());

	public static final Marker STACKTRACE_MARKER = MarkerManager.getMarker("Stack Trace Marker");
	
	private static org.apache.logging.log4j.Logger logger;
	
	public static void log(Level level, String format, Object... data) {
		logger.printf(level, format, data);
	}

	public static void log(Level level, Throwable throwable, String format, Object... data) {
		logger.log(level, String.format(format, data), throwable);
	}

	public static void log(Level level, Marker marker, String format, Object... data) {
		logger.printf(level, marker, format, data);
	}

	public static void log(Level level, Marker marker, Throwable throwable, String format, Object... data) {
		logger.log(level, marker, String.format(format, data), throwable);
	}

	public static void log(Level level, Object format) {
		log(level, format.toString(), new Object());
	}
	
	public static void fatal(String format, Object... data) {
		log(Level.FATAL, format, data);
	}

	public static void fatal(Marker marker, String format, Object... data) {
		log(Level.FATAL, marker, format, data);
	}

	public static void fatal(Throwable throwable, String format,
							 Object... data) {
		log(Level.FATAL, throwable, format, data);
	}

	public static void fatal(Marker marker, Throwable throwable, String format,
							 Object... data) {
		log(Level.FATAL, marker, throwable, format, data);
	}

	public static void error(String format, Object... data) {
		log(Level.ERROR, format, data);
	}

	public static void error(Marker marker, String format, Object... data) {
		log(Level.ERROR, marker, format, data);
	}

	public static void error(Throwable throwable, String format,
							 Object... data) {
		log(Level.ERROR, throwable, format, data);
	}

	public static void error(Marker marker, Throwable throwable, String format,
							 Object... data) {
		log(Level.ERROR, marker, throwable, format, data);
	}

	public static void warn(String format, Object... data) {
		log(Level.WARN, format, data);
	}

	public static void warn(Marker marker, String format, Object... data) {
		log(Level.WARN, marker, format, data);
	}

	public static void info(Object format) {
		log(Level.INFO, format.toString());
	}
	
	public static void info(String format, Object... data) {
		log(Level.INFO, format, data);
	}

	public static void info(Marker marker, String format, Object... data) {
		log(Level.INFO, marker, format, data);
	}

	public static void info(Throwable throwable, String format,
							Object... data) {
		log(Level.INFO, throwable, format, data);
	}

	public static void info(Marker marker, Throwable throwable, String format,
							Object... data) {
		log(Level.INFO, marker, throwable, format, data);
	}

	public static void debug(String format, Object... data) {
		log(Level.DEBUG, format, data);
	}

	public static void debug(Marker marker, String format, Object... data) {
		log(Level.DEBUG, marker, format, data);
	}

	public static void debug(Marker marker, Throwable throwable, String format,
							 Object... data) {
		log(Level.DEBUG, marker, throwable, format, data);
	}
	
	public static void printStackTrace(Throwable t, Level level) {
			log(level, STACKTRACE_MARKER, t.getClass().getName() + ": " + t.getMessage());
            StackTraceElement[] trace = t.getStackTrace();
            for (StackTraceElement traceElement : trace)
            	log(level, STACKTRACE_MARKER, "\tat " + traceElement);

            // Print suppressed exceptions, if any
            for (Throwable se : t.getSuppressed()) {
            	
            	log(level, STACKTRACE_MARKER, se, se.getMessage(), trace);
                
            }
               
            // Print cause, if any
            Throwable ourCause = t.getCause();
            if (ourCause != null) {
            	
            	log(level, STACKTRACE_MARKER, ourCause, ourCause.getMessage(), trace);
                
            }
    }
	
	public static void printStackTrace(Throwable t) {
		
		printStackTrace(t, Level.ERROR);
		
	}

	public static void setLogger(org.apache.logging.log4j.Logger logger) {
		if (Logger.logger != null) {
			throw new IllegalStateException("Attempt to replace logger");
		}

		Logger.logger = logger;
	}
	
	public static org.apache.logging.log4j.Logger getLogger() {
		return Logger.logger;
	}


}

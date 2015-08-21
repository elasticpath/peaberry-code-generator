package org.eclipse.sisu.contrib.peaberry;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * A Velocity utility class.
 */
class VelocityCodeGenerationUtility {

	/**
	 * This prevents install(Peaberry.osgiModule()); from being declared more than once per bundle.
	 */
	private AtomicBoolean isPeaberryInitializationRequired = new AtomicBoolean(true);

	private static VelocityCodeGenerationUtility velocityCodeGenerationUtility = new VelocityCodeGenerationUtility();

	private VelocityCodeGenerationUtility(){
		//Singleton Class, but hopefully per jar....if I've read the interwebs correctly.
	}

	/* Static 'instance' method */
	static VelocityCodeGenerationUtility getInstance( ) {
		return velocityCodeGenerationUtility;
	}

	VelocityEngine getVelocityEngine() throws IOException {
		Properties props = new Properties();
		URL url = this.getClass().getClassLoader().getResource("velocity.properties");
		assert url != null;
		props.load(url.openStream());

		VelocityEngine ve = new VelocityEngine(props);
		ve.init();
		return ve;
	}

	VelocityContext populateVelocityContext(final PeaberryData peaberryData) {
		VelocityContext vc = new VelocityContext();
		vc.put("packageName", peaberryData.packageName);
		vc.put("serviceImplementation", peaberryData.serviceImplementation);
		vc.put("serviceImplementationSimpleName", peaberryData.serviceImplementationSimpleName);
		vc.put("serviceInterface", peaberryData.serviceInterface);
		vc.put("serviceInterfaceSimpleName", peaberryData.serviceInterfaceSimpleName);
		if (isPeaberryInitializationRequired.compareAndSet(true, false)) {
			vc.put("peaberryInitRequired", true);
		}
		vc.put("generatePid", peaberryData.generatePid);
		vc.put("multipleServices", peaberryData.multipleServices);
		vc.put("fileNamePrefix", peaberryData.fileNamePrefix);
		return vc;
	}

	void writeSourceFile(final VelocityContext context, final Template template, final JavaFileObject javaFileObject,
			final ProcessingEnvironment processingEnv) throws IOException {
		processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "creating source file: " + javaFileObject.toUri());
		Writer writer = javaFileObject.openWriter();
		processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "applying velocity template: " + template.getName());
		template.merge(context, writer);
		writer.close();
	}
}

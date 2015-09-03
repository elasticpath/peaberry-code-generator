package org.eclipse.sisu.contrib.peaberry;


import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.eclipse.sisu.contrib.peaberry.annotations.ServiceExport;

@SupportedAnnotationTypes("org.eclipse.sisu.contrib.peaberry.annotations.ServiceExport")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ServiceExportAnnotationProcessor extends AbstractProcessor {

	private final VelocityCodeGenerationUtility velocityUtility = VelocityCodeGenerationUtility.getInstance();

	/**
	 * Default Constructor.
	 */
	public ServiceExportAnnotationProcessor() {
		super();
	}

	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
		for (Element componentClazz : roundEnv.getElementsAnnotatedWith(ServiceExport.class)) {
			if (componentClazz.getKind() != ElementKind.CLASS) {
				continue;
			}
			try {
				PeaberryData peaberryData = populatePeaberryData(componentClazz);
				if (peaberryData.isExportDataInitialialized()) {
					generatePeaberryCode(peaberryData);
				}
			} catch (IOException e) {
				processingEnv.getMessager()
						.printMessage(Diagnostic.Kind.ERROR, "Error creating Peaberry classes for: " + componentClazz.getSimpleName());
			}
		}
		return true;
	}

	private void generatePeaberryCode(final PeaberryData peaberryData) throws IOException {
		VelocityEngine engine = velocityUtility.getVelocityEngine();
		VelocityContext velocityContext = velocityUtility.populateVelocityContext(peaberryData);

		Template moduleTemplate = engine.getTemplate("PeaberryServiceExportModule.vm");
		JavaFileObject moduleFileObject = processingEnv.getFiler()
				.createSourceFile(peaberryData.serviceInterfaceSimpleName + "PeaberryExportModule");
		velocityUtility.writeSourceFile(velocityContext, moduleTemplate, moduleFileObject, processingEnv);

		Template ServiceExportTemplate = engine.getTemplate("PeaberryServiceExport.vm");
		JavaFileObject serviceExportFileObject = processingEnv.getFiler()
				.createSourceFile(peaberryData.serviceInterfaceSimpleName + "PeaberryServiceExport");
		velocityUtility.writeSourceFile(velocityContext, ServiceExportTemplate, serviceExportFileObject, processingEnv);
	}

	private PeaberryData populatePeaberryData(final Element componentClazz) {
		PeaberryData peaberryData = new PeaberryData();
		ServiceExport annotation = componentClazz.getAnnotation(ServiceExport.class);
		peaberryData.generatePid = annotation.generatePid();
		//Do not be too alarmed by this code. We trigger this exception to get access the type mirrors. This is currently the cleanest way.
		try {
			Class[] serviceClasses = annotation.interfaces();
			Class throwsException = serviceClasses[0];
		}
		catch( MirroredTypesException mte ) {
			List<? extends TypeMirror> typeMirrors = mte.getTypeMirrors();
			// todo - Figure out what the Peaberry code to export a service under multiple interfaces looks like and update the velocity templates
			for (TypeMirror typeMirror : typeMirrors) {
				Element serviceInterface = ((DeclaredType)typeMirror).asElement();
				peaberryData.serviceInterface = serviceInterface.toString();
				peaberryData.serviceInterfaceSimpleName = serviceInterface.getSimpleName().toString();
			}
		}

		TypeElement classElement = (TypeElement) componentClazz;
		PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

		processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "annotated class: " + classElement.getQualifiedName(), componentClazz);

		peaberryData.serviceImplementation = classElement.getQualifiedName().toString();
		peaberryData.serviceImplementationSimpleName = classElement.getSimpleName().toString();
		peaberryData.packageName = packageElement.getQualifiedName().toString();

		return peaberryData;
	}
}
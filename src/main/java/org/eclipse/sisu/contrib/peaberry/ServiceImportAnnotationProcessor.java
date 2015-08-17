package org.eclipse.sisu.contrib.peaberry;


import java.io.IOException;
import java.util.ArrayList;
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
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.eclipse.sisu.contrib.peaberry.annotations.ServiceImport;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

@SupportedAnnotationTypes("org.eclipse.sisu.contrib.peaberry.annotations.ServiceImport")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ServiceImportAnnotationProcessor extends AbstractProcessor {

	private final List<String> processed = new ArrayList<>();
	private final VelocityCodeGenerationUtility velocityUtility = VelocityCodeGenerationUtility.getInstance();

	/**
	 * Default Constructor.
	 */
	public ServiceImportAnnotationProcessor() {
		super();
	}


	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
		for (Element componentClazz : roundEnv.getElementsAnnotatedWith(ServiceImport.class)) {
			if (componentClazz.getKind() != ElementKind.PARAMETER
					&& componentClazz.getKind() != ElementKind.FIELD) {
				continue;
			}
			try {
				PeaberryData peaberryData = populatePeaberryData(componentClazz);
				if (isPeaberryCodeGenerationRequired(peaberryData)) {
					generatePeaberryCode(peaberryData);
					processed.add(peaberryData.serviceInterface);
				}
			} catch (IOException e) {
				processingEnv.getMessager()
						.printMessage(Diagnostic.Kind.ERROR, "Error creating Peaberry classes for: " + componentClazz.getSimpleName());
			}
		}
		return true;
	}

	private boolean isPeaberryCodeGenerationRequired(final PeaberryData peaberryData) {
		return !processed.contains(peaberryData.serviceInterface)
				&& peaberryData.isImportDataInitialized();
	}

	private void generatePeaberryCode(final PeaberryData peaberryData) throws IOException {
		VelocityEngine engine = velocityUtility.getVelocityEngine();
		VelocityContext velocityContext = velocityUtility.populateVelocityContext(peaberryData);

		Template moduleTemplate = engine.getTemplate("PeaberryServiceImportModule.vm");
		JavaFileObject moduleFileObject = processingEnv.getFiler()
				.createSourceFile(peaberryData.serviceInterfaceSimpleName + "PeaberryImportModule");

		velocityUtility.writeSourceFile(velocityContext, moduleTemplate, moduleFileObject, processingEnv);
	}

	private PeaberryData populatePeaberryData(final Element element) {
		PeaberryData peaberryData = new PeaberryData();
		TypeMirror fieldType = element.asType();
		String serviceInterface = fieldType.toString();
		String serviceSimpleName = serviceInterface.substring(serviceInterface.lastIndexOf(".") + 1);
		peaberryData.serviceInterface = serviceInterface;
		peaberryData.serviceInterfaceSimpleName = serviceSimpleName;
		processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "annotated type: " + serviceSimpleName);
		PackageElement typeElement = getEnclosingPackageElement(element);
		peaberryData.packageName = typeElement.getQualifiedName().toString();
		return peaberryData;
	}

	private PackageElement getEnclosingPackageElement(final Element element) {
		if (element.getKind() == ElementKind.PACKAGE) {
			return (PackageElement) element;
		}
		return getEnclosingPackageElement(element.getEnclosingElement());
	}
}
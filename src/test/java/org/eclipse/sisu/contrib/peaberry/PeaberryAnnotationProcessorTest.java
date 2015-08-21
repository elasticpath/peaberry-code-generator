package org.eclipse.sisu.contrib.peaberry;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import org.junit.Test;

import com.google.testing.compile.JavaFileObjects;

/**
 * Test {@link ServiceExportAnnotationProcessor} and
 * {@link ServiceImportAnnotationProcessor}.
 */
public class PeaberryAnnotationProcessorTest {


	/**
	 * Tests need to be grouped together like this because the Peaberry install declaration can only be present once per bundle:
	 * install(Peaberry.osgiModule());
	 */
	@Test
	public void runAllTests() {
		testPeaberryExportClassesGenerateCorrectly();

		testPeaberryExportClassesGenerateCorrectlyWhenNoPid();

		testPeaberryImportClassGeneratesCorrectly();

		testPeaberryImportClassesGeneratesOnceWhenAnnotationsDeclaredMultipleTimes();

		testPeaberryImportClassGeneratesCorrectlyForServiceLists();
	}

	private void testPeaberryExportClassesGenerateCorrectly() {
		assert_().about(javaSource())
				.that(JavaFileObjects.forResource("ServiceOneImpl.java"))
				.processedWith(
						new ServiceExportAnnotationProcessor(),
						new ServiceImportAnnotationProcessor()
				)
				.compilesWithoutError()
				.and()
				.generatesSources(
						JavaFileObjects.forResource("ServiceOnePeaberryExportModule.java"),
						JavaFileObjects.forResource("ServiceOnePeaberryServiceExport.java")
				);
	}

	private void testPeaberryExportClassesGenerateCorrectlyWhenNoPid() {
		assert_().about(javaSource())
				.that(JavaFileObjects.forResource("ServiceTwoImpl.java"))
				.processedWith(
						new ServiceExportAnnotationProcessor(),
						new ServiceImportAnnotationProcessor()
				)
				.compilesWithoutError()
				.and()
				.generatesSources(
						JavaFileObjects.forResource("ServiceTwoPeaberryExportModule.java")
				);
	}

	private void testPeaberryImportClassGeneratesCorrectly() {
		assert_().about(javaSource())
				.that(JavaFileObjects.forResource("MultiServiceConsumer.java"))
				.processedWith(
						new ServiceExportAnnotationProcessor(),
						new ServiceImportAnnotationProcessor()
				)
				.compilesWithoutError()
				.and()
				.generatesSources(
						JavaFileObjects.forResource("ServiceOnePeaberryImportModule.java"),
						JavaFileObjects.forResource("ServiceTwoPeaberryImportModule.java")
				);
	}

	private void testPeaberryImportClassesGeneratesOnceWhenAnnotationsDeclaredMultipleTimes() {
		assert_().about(javaSource())
				.that(JavaFileObjects.forResource("MultipleAnnotationDeclarationsServiceConsumer.java"))
				.processedWith(
						new ServiceExportAnnotationProcessor(),
						new ServiceImportAnnotationProcessor()
				)
				.compilesWithoutError()
				.and()
				.generatesSources(
						JavaFileObjects.forResource("ServiceOnePeaberryImportModule.java"),
						JavaFileObjects.forResource("ServiceTwoPeaberryImportModule.java")
				);
	}

	private void testPeaberryImportClassGeneratesCorrectlyForServiceLists() {
		assert_().about(javaSource())
				.that(JavaFileObjects.forResource("ServiceListConsumer.java"))
				.processedWith(
						new ServiceExportAnnotationProcessor(),
						new ServiceImportAnnotationProcessor()
				)
				.compilesWithoutError()
				.and()
				.generatesSources(
						JavaFileObjects.forResource("ServiceOneListPeaberryImportModule.java")
				);
	}

}

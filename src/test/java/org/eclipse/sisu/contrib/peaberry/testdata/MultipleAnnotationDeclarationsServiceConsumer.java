package org.eclipse.sisu.contrib.peaberry.testdata;

import javax.inject.Inject;

import org.eclipse.sisu.contrib.peaberry.annotations.ServiceImport;

/**
 * Example Service.
 */
public class MultipleAnnotationDeclarationsServiceConsumer {

	@ServiceImport
	private final ServiceOne testServiceOne;
	@ServiceImport
	private final ServiceTwo testServiceTwo;

	@Inject
	public MultipleAnnotationDeclarationsServiceConsumer(
			@ServiceImport
			final ServiceOne testServiceOne,
			@ServiceImport
			final ServiceTwo testServiceTwo) {
		this.testServiceOne = testServiceOne;
		this.testServiceTwo = testServiceTwo;
	}
}
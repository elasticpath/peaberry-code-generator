package org.eclipse.sisu.contrib.peaberry.testdata;

import javax.inject.Inject;

import org.eclipse.sisu.contrib.peaberry.annotations.ServiceImport;

/**
 * Example Service.
 */
public class MultiServiceConsumer {

	private final ServiceOne testServiceOne;
	private final ServiceTwo testServiceTwo;

	@Inject
	public MultiServiceConsumer(
			@ServiceImport
			final ServiceOne testServiceOne,
			@ServiceImport
			final ServiceTwo testServiceTwo) {
		this.testServiceOne = testServiceOne;
		this.testServiceTwo = testServiceTwo;
	}
}
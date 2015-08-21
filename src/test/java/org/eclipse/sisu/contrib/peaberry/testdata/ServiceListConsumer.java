package org.eclipse.sisu.contrib.peaberry.testdata;

import javax.inject.Inject;

import org.eclipse.sisu.contrib.peaberry.annotations.ServiceImport;

/**
 * Consumes a Dynamic List of Services.
 */
public class ServiceListConsumer {

	private final Iterable<ServiceOne> list;

	@Inject
	public ServiceListConsumer(
			@ServiceImport
			final Iterable<ServiceOne> list) {
		this.list = list;
	}
}
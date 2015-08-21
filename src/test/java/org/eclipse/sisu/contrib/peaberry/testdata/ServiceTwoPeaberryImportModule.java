package org.eclipse.sisu.contrib.peaberry.testdata;

import static org.ops4j.peaberry.Peaberry.service;

import javax.inject.Named;

import org.eclipse.sisu.contrib.peaberry.testdata.ServiceTwo;
import com.google.inject.AbstractModule;

@Named
public class ServiceTwoPeaberryImportModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(ServiceTwo.class).toProvider(service(ServiceTwo.class).single());
	}
}
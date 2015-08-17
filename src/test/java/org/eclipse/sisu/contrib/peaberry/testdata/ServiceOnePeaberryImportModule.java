package org.eclipse.sisu.contrib.peaberry.testdata;

import static org.ops4j.peaberry.Peaberry.service;

import javax.inject.Named;

import org.eclipse.sisu.contrib.peaberry.testdata.ServiceOne;
import com.google.inject.AbstractModule;

@Named
public class ServiceOnePeaberryImportModule extends AbstractModule {

	@Override
	protected void configure() {
				bind(ServiceOne.class).toProvider(service(ServiceOne.class).single());
	}
}
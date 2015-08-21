package org.eclipse.sisu.contrib.peaberry.testdata;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.iterable;

import javax.inject.Named;

import org.eclipse.sisu.contrib.peaberry.testdata.ServiceOne;
import com.google.inject.AbstractModule;

@Named
public class ServiceOneListPeaberryImportModule extends AbstractModule {

	@Override
	protected void configure() {

				bind(iterable(ServiceOne.class)).toProvider(service(ServiceOne.class).multiple());
	}
}
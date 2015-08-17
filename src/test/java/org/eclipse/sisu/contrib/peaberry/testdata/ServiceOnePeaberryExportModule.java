package org.eclipse.sisu.contrib.peaberry.testdata;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

import javax.inject.Named;

import org.eclipse.sisu.contrib.peaberry.testdata.ServiceOne;
import org.eclipse.sisu.contrib.peaberry.testdata.ServiceOneImpl;
import com.google.inject.AbstractModule;

import org.ops4j.peaberry.Peaberry;

@Named
public class ServiceOnePeaberryExportModule extends AbstractModule {

	@Override
	protected void configure() {
				install(Peaberry.osgiModule());
				bind(export(ServiceOne.class)).toProvider(service(ServiceOneImpl.class).export());
	}
}
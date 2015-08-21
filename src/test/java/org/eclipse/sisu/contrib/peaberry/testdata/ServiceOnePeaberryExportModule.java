package org.eclipse.sisu.contrib.peaberry.testdata;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

import java.util.HashMap;
import java.util.Map;

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

				Map<String, Object> attributes = new HashMap<>();
				attributes.put("service.pid", ServiceOneImpl.class.getName());

				bind(export(ServiceOne.class)).toProvider(service(ServiceOneImpl.class)
						.attributes(attributes)
						.export());
	}
}
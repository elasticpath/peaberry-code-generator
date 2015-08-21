package org.eclipse.sisu.contrib.peaberry.testdata;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.eclipse.sisu.contrib.peaberry.testdata.ServiceTwo;
import org.eclipse.sisu.contrib.peaberry.testdata.ServiceTwoImpl;
import com.google.inject.AbstractModule;

import org.ops4j.peaberry.Peaberry;

@Named
public class ServiceTwoPeaberryExportModule extends AbstractModule {

	@Override
	protected void configure() {

		Map<String, Object> attributes = new HashMap<>();

		bind(export(ServiceTwo.class)).toProvider(service(ServiceTwoImpl.class)
				.attributes(attributes)
				.export());
	}
}
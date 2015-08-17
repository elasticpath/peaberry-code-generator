package org.eclipse.sisu.contrib.peaberry.testdata;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.sisu.contrib.peaberry.testdata.ServiceOne;

import org.eclipse.sisu.EagerSingleton;
import org.ops4j.peaberry.Export;

@Named
@EagerSingleton
public class ServiceOnePeaberryServiceExport {

	@Inject
	Export<ServiceOne> service;

}

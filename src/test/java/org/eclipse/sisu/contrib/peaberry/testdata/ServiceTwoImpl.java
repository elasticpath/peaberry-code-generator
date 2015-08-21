package org.eclipse.sisu.contrib.peaberry.testdata;

import org.eclipse.sisu.contrib.peaberry.annotations.ServiceExport;

/**
 * Example Service.
 */
@ServiceExport(services = ServiceTwo.class,
		generatePid = false)
public class ServiceTwoImpl implements ServiceTwo {
}

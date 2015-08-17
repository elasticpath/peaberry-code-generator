package org.eclipse.sisu.contrib.peaberry.testdata;

import org.eclipse.sisu.contrib.peaberry.annotations.ServiceExport;

/**
 * Example Service.
 */
@ServiceExport( service = ServiceOne.class)
public class ServiceOneImpl implements ServiceOne {
}

package org.eclipse.sisu.contrib.peaberry.testdata;

import org.eclipse.sisu.contrib.peaberry.annotations.ServiceExport;

/**
 * Example Service to be exported under multiple interfaces.
 */
@ServiceExport( interfaces = {ServiceOne.class, ServiceTwo.class})
public class ServiceOneAndTwoImpl implements ServiceOne, ServiceTwo {
}

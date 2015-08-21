package org.eclipse.sisu.contrib.peaberry;

/**
 * Holds Peaberry code generation values.
 */
class PeaberryData {
	boolean generatePid = true;
	boolean multipleServices = false;
	String fileNamePrefix = null;
	String packageName = null;
	String serviceImplementation = null;
	String serviceImplementationSimpleName = null;
	String serviceInterface = null;
	String serviceInterfaceSimpleName = null;

	boolean isExportDataInitialialized() {
		return isImportDataInitialized()
				&& serviceImplementation != null
				&& serviceImplementationSimpleName != null;
	}
	boolean isImportDataInitialized() {
		return packageName != null
				&& serviceInterface != null
				&& serviceInterfaceSimpleName != null;
	}
}

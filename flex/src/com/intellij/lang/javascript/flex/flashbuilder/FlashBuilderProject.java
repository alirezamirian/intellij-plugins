package com.intellij.lang.javascript.flex.flashbuilder;

import com.intellij.lang.javascript.flex.FlexBundle;
import com.intellij.lang.javascript.flex.projectStructure.model.OutputType;
import com.intellij.lang.javascript.flex.projectStructure.model.TargetPlatform;
import com.intellij.openapi.util.Pair;
import gnu.trove.THashMap;
import gnu.trove.THashSet;

import java.util.*;

public class FlashBuilderProject {

  private String myName = FlexBundle.message("unnamed");
  private Map<String, String> myLinkedResources = new THashMap<String, String>();
  private Set<String> myUsedPathVariables = new THashSet<String>();
  private String myProjectRootPath = "";
  private Collection<String> mySourcePaths = new ArrayList<String>();
  private String myOutputFolderPath = "";
  private boolean mySdkUsed;
  private String mySdkName = FlashBuilderSdkFinder.DEFAULT_SDK_NAME;
  private TargetPlatform myTargetPlatform;
  private boolean myPureActionScript = false;
  private OutputType myOutputType = OutputType.Application;
  private boolean myAndroidSupported = false;
  private boolean myIosSupported = false;
  private String myMainAppClassName = "";
  private Collection<String> myApplicationClassNames = new ArrayList<String>();
  private String myTargetPlayerVersion;
  private String myAdditionalCompilerOptions = "";
  private boolean myUseHtmlWrapper = false;
  private Map<String, Collection<String>> myLibraryPathsAndSources = new LinkedHashMap<String, Collection<String>>();
  private Collection<Pair<String, String>> myNamespacesAndManifestPaths = new ArrayList<Pair<String, String>>(1);
  private Collection<Pair<String, String>> myModules = new ArrayList<Pair<String, String>>();
  private Collection<String> myCssFilesToCompile = new ArrayList<String>();

  FlashBuilderProject() {
  }

  public String getName() {
    return myName;
  }

  public void setName(final String name) {
    myName = name;
  }

  public void addLinkedResource(final String linkName, final String linkLocation) {
    myLinkedResources.put(linkName, linkLocation);
  }

  public Map<String, String> getLinkedResources() {
    return myLinkedResources;
  }

  public String getProjectRootPath() {
    return myProjectRootPath;
  }

  public void setProjectRootPath(final String projectRootPath) {
    myProjectRootPath = projectRootPath;
  }

  public Collection<String> getSourcePaths() {
    return mySourcePaths;
  }

  public void addSourcePath(final String sourcePath) {
    checkIfPathMacroUsed(sourcePath);
    mySourcePaths.add(sourcePath);
  }

  public String getOutputFolderPath() {
    return myOutputFolderPath;
  }

  public void setOutputFolderPath(final String outputFolderPath) {
    checkIfPathMacroUsed(outputFolderPath);
    myOutputFolderPath = outputFolderPath;
  }

  public boolean isSdkUsed() {
    return mySdkUsed;
  }

  public void setSdkUsed(boolean sdkUsed) {
    mySdkUsed = sdkUsed;
  }

  public String getSdkName() {
    return mySdkName;
  }

  public void setSdkName(final String sdkName) {
    mySdkName = sdkName;
  }

  public TargetPlatform getTargetPlatform() {
    return myTargetPlatform;
  }

  public void setTargetPlatform(final TargetPlatform targetPlatform) {
    myTargetPlatform = targetPlatform;
  }

  public boolean isPureActionScript() {
    return myPureActionScript;
  }

  public void setPureActionScript(final boolean pureActionScript) {
    myPureActionScript = pureActionScript;
  }

  public OutputType getOutputType() {
    return myOutputType;
  }

  public void setOutputType(final OutputType outputType) {
    myOutputType = outputType;
  }

  public boolean isAndroidSupported() {
    return myAndroidSupported;
  }

  public void setAndroidSupported(final boolean androidSupported) {
    myAndroidSupported = androidSupported;
  }

  public boolean isIosSupported() {
    return myIosSupported;
  }

  public void setIosSupported(final boolean iosSupported) {
    myIosSupported = iosSupported;
  }

  public String getMainAppClassName() {
    return myMainAppClassName;
  }

  public void addApplicationClassName(final String className) {
    if (!className.equals(myMainAppClassName)) {
      myApplicationClassNames.add(className);
    }
  }

  public Collection<String> getApplicationClassNames() {
    return myApplicationClassNames;
  }

  public void setMainAppClassName(final String mainClassName) {
    myMainAppClassName = mainClassName;
  }

  public String getTargetPlayerVersion() {
    return myTargetPlayerVersion;
  }

  public void setTargetPlayerVersion(final String targetPlayerVersion) {
    myTargetPlayerVersion = targetPlayerVersion;
  }

  public String getAdditionalCompilerOptions() {
    return myAdditionalCompilerOptions;
  }

  public void setAdditionalCompilerOptions(final String additionalCompilerOptions) {
    myAdditionalCompilerOptions = additionalCompilerOptions;
  }

  public boolean isUseHtmlWrapper() {
    return myUseHtmlWrapper;
  }

  public void setUseHtmlWrapper(final boolean useHtmlWrapper) {
    myUseHtmlWrapper = useHtmlWrapper;
  }

  public Collection<String> getLibraryPaths() {
    return myLibraryPathsAndSources.keySet();
  }

  public Collection<String> getLibrarySourcePaths(final String libraryPath) {
    return myLibraryPathsAndSources.get(libraryPath);
  }

  public void addLibraryPathAndSources(final String libraryPath, final Collection<String> sourcePathsForLibrary) {
    checkIfPathMacroUsed(libraryPath);
    for (final String path : sourcePathsForLibrary) {
      checkIfPathMacroUsed(path);
    }
    myLibraryPathsAndSources.put(libraryPath, sourcePathsForLibrary);
  }

  public Set<String> getUsedPathVariables() {
    return myUsedPathVariables;
  }

  public void addNamespaceAndManifestPath(final String namespace, final String manifestPath) {
    checkIfPathMacroUsed(manifestPath);
    myNamespacesAndManifestPaths.add(Pair.create(namespace, manifestPath));
  }

  public Collection<Pair<String, String>> getNamespacesAndManifestPaths() {
    return myNamespacesAndManifestPaths;
  }

  public void addModule(final String sourcePath, final String destPath) {
    checkIfPathMacroUsed(sourcePath);
    myModules.add(Pair.create(sourcePath, destPath));
  }

  public Collection<Pair<String, String>> getModules() {
    return myModules;
  }

  public void addCssFileToCompile(final String cssFilePath) {
    checkIfPathMacroUsed(cssFilePath);
    myCssFilesToCompile.add(cssFilePath);
  }

  public Collection<String> getCssFilesToCompile() {
    return myCssFilesToCompile;
  }

  private void checkIfPathMacroUsed(final String path) {
    final int slashIndex = path.indexOf('/');
    final String potentialLink = slashIndex >= 0 ? path.substring(0, slashIndex) : path;

    if (potentialLink.startsWith("${") && potentialLink.endsWith("}")) {
      myUsedPathVariables.add(potentialLink.substring(2, potentialLink.length() - 1));
    }
  }
}
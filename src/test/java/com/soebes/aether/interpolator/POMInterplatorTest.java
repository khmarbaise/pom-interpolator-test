package com.soebes.aether.interpolator;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.soebes.aether.interpolator.POMInterpolator;

/**
 *
 * @author Karl Heinz Marbaise
 *
 */
public class POMInterplatorTest extends UnitTestBase {
    private static Logger LOGGER = Logger.getLogger(POMInterplatorTest.class);

    private POMInterpolator pomInterpolator;

    @BeforeClass
    public void beforeClass() {
        pomInterpolator = new POMInterpolator();
    }

    public Model readPom(File pomFile) throws FileNotFoundException, IOException, XmlPullParserException {
        MavenXpp3Reader mavenReader = new MavenXpp3Reader();
        Model pom = mavenReader.read(new FileReader(pomFile));
        return pom;
    }

    @Test
    public void readModelTest() throws FileNotFoundException, IOException, XmlPullParserException {
        File pomFile = new File (getTestResourcesDirectory(), "test-pom.xml");
        Model modelPom = readPom(pomFile);
        System.out.println("--------- Model -------------");
        for (Dependency dependency : modelPom.getDependencies()) {
            System.out.println("Model Dependency:" + dependency);
        }
    }

    @Test
    public void checkInterpolatedPom() throws ProjectBuildingException {
        File pomFile = new File (getTestResourcesDirectory(), "test-pom.xml");
        MavenProject mavenProject = pomInterpolator.getInterpolatedPom(pomFile);
        System.out.println("--------- Interpolated POM (resolveDependencies=false) -------------");
        System.out.println("Artifact: " + mavenProject.getArtifact());

        assertThat(mavenProject.getArtifacts()).isEmpty();
        assertThat(mavenProject.getDependencies()).hasSize(16);
        assertThat(mavenProject.getActiveProfiles()).isEmpty();

        for (Artifact artifact : mavenProject.getArtifacts()) {
            System.out.println("Artifacts:" + artifact);
        }
        for (Entry<String, Artifact> artifactMapEntry : mavenProject.getArtifactMap().entrySet()) {
            System.out.println("E:" + artifactMapEntry.getKey() + " v:" + artifactMapEntry.getValue());
        }

        System.out.println("Dependency: " + dependencyLog4j);
        for (Dependency dependency : mavenProject.getDependencies()) {
            System.out.println("Dependency:" + dependency);
        }
        for (Profile profile : mavenProject.getActiveProfiles()) {
            System.out.println("Profile: " + profile);
        }
    }

    @Test
    public void checkInterpolatedPomWithResolvedDependencies() throws ProjectBuildingException {
        File pomFile = new File (getTestResourcesDirectory(), "test-pom.xml");
        MavenProject mavenProject = pomInterpolator.getInterpolatedPomWithResolvedDependencies(pomFile);
        System.out.println("--------- Interpolated POM (resolveDependencies=true) -------------");
        System.out.println("Artifact: " + mavenProject.getArtifact());

        if (contains(dependencyLog4j, mavenProject.getDependencies())) {
            System.out.println("contains(): Ok");
        } else {
            System.out.println("contains(): Fail!");

        }

        assertThat(mavenProject.getArtifacts()).isNotEmpty().hasSize(35);
        assertThat(mavenProject.getDependencies()).hasSize(16);
        assertThat(mavenProject.getActiveProfiles()).isEmpty();

        for (Artifact artifact : mavenProject.getArtifacts()) {
            System.out.println("Artifacts:" + artifact);
        }
        for (Dependency dependency : mavenProject.getDependencies()) {
            System.out.println("Dependency:" + dependency);
        }
        for (Profile profile : mavenProject.getActiveProfiles()) {
            System.out.println("Profile: " + profile);
        }
    }

    private Dependency create(String groupId, String artifactId, String version, String type) {
        Dependency dep = new Dependency();
        dep.setGroupId(groupId);
        dep.setArtifactId(artifactId);
        dep.setVersion(version);
        dep.setType(type);
        return dep;
    }

    /**
     * We have to do it that way, cause the Dependency class does not implement
     * it's own equals() method.
     * @param containingDependency
     * @param dependencies
     * @return true if found based on groupId, artifactId, Version and type.
     */
    private boolean contains(Dependency containingDependency, List<Dependency> dependencies) {
        boolean result = false;
        for (Dependency dependency : dependencies) {
            if (	containingDependency.getGroupId().equals(dependency.getGroupId())
                &&	containingDependency.getArtifactId().equals(dependency.getArtifactId())
                &&	containingDependency.getVersion().equals(dependency.getVersion())
                &&	containingDependency.getType().equals(dependency.getType())
                ) {
                result = true;
            }
        }
        return result;
    }

    private final Dependency dependencyAetherAPI = create("org.sonatype.aether", "aether-api", "1.11", "jar");
    private final Dependency dependencyLog4j = create("log4j", "log4j", "1.2.14", "jar");


}

package org.aksw.rdfunit.model.implementations;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import org.aksw.rdfunit.enums.RLOGLevel;
import org.aksw.rdfunit.exceptions.TestCaseInstantiationException;
import org.aksw.rdfunit.model.interfaces.ResultAnnotation;
import org.aksw.rdfunit.model.interfaces.TestCase;
import org.aksw.rdfunit.services.PrefixNSService;
import org.aksw.rdfunit.tests.TestCaseAnnotation;

import java.util.Collection;

/**
 * <p>Abstract TestCase class.</p>
 *
 * @author Dimitris Kontokostas
 *         Description
 * @since 9/23/13 6:31 AM
 * @version $Id: $Id
 */
public abstract class AbstractTestCaseImpl implements TestCase, Comparable<AbstractTestCaseImpl> {

    protected final String testURI;
    protected final TestCaseAnnotation annotation;

    /**
     * <p>Constructor for TestCase.</p>
     *
     * @param testURI a {@link java.lang.String} object.
     * @param annotation a {@link org.aksw.rdfunit.tests.TestCaseAnnotation} object.
     * @throws org.aksw.rdfunit.exceptions.TestCaseInstantiationException if any.
     */
    public AbstractTestCaseImpl(String testURI, TestCaseAnnotation annotation) throws TestCaseInstantiationException {
        this.testURI = testURI;
        this.annotation = annotation;
        // Validate on subclasses
    }

    /**
     * <p>getUnitTestModel.</p>
     *
     * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
     */
    public Model getUnitTestModel() {
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, ModelFactory.createDefaultModel());
        serialize(model);
        return model;
    }

    /**
     * <p>getSparqlWhere.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public abstract String getSparqlWhere();

    /**
     * <p>getSparqlPrevalence.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public abstract String getSparqlPrevalence();

    /**
     * {@inheritDoc}
     *
     * <p>serialize.</p>
     */
    public Resource serialize(Model model) {

        Resource resource = model.createResource(testURI);
        annotation.serialize(resource, model);
        return resource;

    }

    /**
     * <p>getResultMessage.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getResultMessage() {
        return annotation.getDescription();
    }

    /**
     * <p>getLogLevel.</p>
     *
     * @return a {@link org.aksw.rdfunit.enums.RLOGLevel} object.
     */
    public RLOGLevel getLogLevel() {
        return annotation.getTestCaseLogLevel();
    }

    /**
     * <p>getResultAnnotations.</p>
     *
     * @return a {@link java.util.Collection} object.
     */
    public Collection<ResultAnnotation> getResultAnnotations() {
        return annotation.getResultAnnotations();
    }

    /**
     * <p>getVariableAnnotations.</p>
     *
     * @return a {@link java.util.Collection} object.
     */
    public Collection<ResultAnnotation> getVariableAnnotations() {
        return annotation.getVariableAnnotations();
    }

    /**
     * <p>getSparqlPrevalenceQuery.</p>
     *
     * @return a {@link com.hp.hpl.jena.query.Query} object.
     */
    public Query getSparqlPrevalenceQuery() {
        if (getSparqlPrevalence().trim().isEmpty())
            return null;
        return QueryFactory.create(PrefixNSService.getSparqlPrefixDecl() + getSparqlPrevalence());
    }

    /**
     * <p>Getter for the field <code>testURI</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getTestURI() {
        return testURI;
    }

    /**
     * <p>getAbrTestURI.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getAbrTestURI() {
        return testURI.replace(PrefixNSService.getNSFromPrefix("rutt"), "rutt:");
    }



    /** {@inheritDoc} */
    @Override
    public int compareTo(AbstractTestCaseImpl o) {
        if (o == null) {
            return -1;
        }

        return this.getTestURI().compareTo(o.getTestURI());
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractTestCaseImpl)) return false;

        AbstractTestCaseImpl testCase = (AbstractTestCaseImpl) o;

        return testURI.equals(testCase.testURI);

    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return testURI.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return this.getTestURI();
    }

}

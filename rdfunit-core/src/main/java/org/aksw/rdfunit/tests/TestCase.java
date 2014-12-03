package org.aksw.rdfunit.tests;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QueryParseException;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import org.aksw.rdfunit.enums.RLOGLevel;
import org.aksw.rdfunit.exceptions.TestCaseInstantiationException;
import org.aksw.rdfunit.services.PrefixNSService;
import org.aksw.rdfunit.tests.results.ResultAnnotation;

import java.util.Collection;

/**
 * <p>Abstract TestCase class.</p>
 *
 * @author Dimitris Kontokostas
 *         Description
 * @since 9/23/13 6:31 AM
 * @version $Id: $Id
 */
public abstract class TestCase implements Comparable<TestCase> {

    private final String testURI;
    private final TestCaseAnnotation annotation;

    /**
     * <p>Constructor for TestCase.</p>
     *
     * @param testURI a {@link java.lang.String} object.
     * @param annotation a {@link org.aksw.rdfunit.tests.TestCaseAnnotation} object.
     * @throws org.aksw.rdfunit.exceptions.TestCaseInstantiationException if any.
     */
    public TestCase(String testURI, TestCaseAnnotation annotation) throws TestCaseInstantiationException {
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
     * <p>serialize.</p>
     *
     * @param model a {@link com.hp.hpl.jena.rdf.model.Model} object.
     * @return a {@link com.hp.hpl.jena.rdf.model.Resource} object.
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

    /**
     * <p>validateQueries.</p>
     *
     * @throws org.aksw.rdfunit.exceptions.TestCaseInstantiationException if any.
     */
    public void validateQueries() throws TestCaseInstantiationException {
        // TODO move this in a separate class

        validateSPARQL(new QueryGenerationSelectFactory().getSparqlQueryAsString(this), "SPARQL");
        validateSPARQL(new QueryGenerationExtendedSelectFactory().getSparqlQueryAsString(this), "SPARQL Extended");
        validateSPARQL(new QueryGenerationCountFactory().getSparqlQueryAsString(this), "SPARQL Count");
        validateSPARQL(new QueryGenerationAskFactory().getSparqlQueryAsString(this), "ASK");
        if (!getSparqlPrevalence().trim().equals("")) { // Prevalence in not always defined
            validateSPARQL(PrefixNSService.getSparqlPrefixDecl() + getSparqlPrevalence(), "prevalence");
        }

        Collection<String> vars = new QueryGenerationSelectFactory().getSparqlQuery(this).getResultVars();
        // check for Resource & message
        boolean hasResource = false;
        for (String v : vars) {
            if (v.equals("resource")) {
                hasResource = true;
            }

        }
        if (!hasResource) {
            throw new TestCaseInstantiationException("?resource is not included in SELECT for Test: " + testURI);
        }

        // Message is allowed to exist either in SELECT or as a result annotation
        if (annotation.getDescription().equals("")) {
            throw new TestCaseInstantiationException("No test case dcterms:description message included in TestCase: " + testURI);
        }

        if (getLogLevel() == null) {
            throw new TestCaseInstantiationException("No (or malformed) log level included for Test: " + testURI);
        }
    }

    private void validateSPARQL(String sparql, String type) throws TestCaseInstantiationException {
        try {
            QueryFactory.create(sparql);
        } catch (QueryParseException e) {
            String message = "QueryParseException in " + type + " query (line " + e.getLine() + ", column " + e.getColumn() + " for Test: " + testURI + "\n" + PrefixNSService.getSparqlPrefixDecl() + sparql;
            throw new TestCaseInstantiationException(message, e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public int compareTo(TestCase o) {
        if (o == null) {
            return -1;
        }

        return this.getTestURI().compareTo(o.getTestURI());
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestCase)) return false;

        TestCase testCase = (TestCase) o;

        if (!testURI.equals(testCase.testURI)) return false;

        return true;
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

package org.aksw.rdfunit.vocabulary;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

/**
 * Core RDFUnit Vocabulary
 *
 * @author Dimitris Kontokostas
 * @since 6/17/15 6:43 PM
 * @version $Id: $Id
 */
public final class RDFUNITv {

    //namespace
    /** Constant <code>namespace="http://rdfunit.aksw.org/ns/core#"</code> */
    public static final String namespace = "http://rdfunit.aksw.org/ns/core#";

    //Classes
    /** Constant <code>Binding</code> */
    public static final Resource Binding = resource("Binding");
    /** Constant <code>Pattern</code> */
    public static final Resource Pattern = resource("Pattern");
    /** Constant <code>ResultAnnotation</code> */
    public static final Resource ResultAnnotation = resource("ResultAnnotation");
    /** Constant <code>RLOGTestCaseResult</code> */
    public static final Resource RLOGTestCaseResult = resource("RLOGTestCaseResult");
    /** Constant <code>TestGenerator</code> */
    public static final Resource TestGenerator = resource("TestGenerator");
    /** Constant <code>Parameter</code> */
    public static final Resource Parameter = resource("Parameter");
    /** Constant <code>PatternBasedTestCase</code> */
    public static final Resource PatternBasedTestCase = resource("PatternBasedTestCase");
    /** Constant <code>ManualTestCase</code> */
    public static final Resource ManualTestCase = resource("ManualTestCase");



    //properties
    /** Constant <code>binding</code> */
    public static final Property binding = property("binding");
    /** Constant <code>bindingValue</code> */
    public static final Property bindingValue = property("bindingValue");
    /** Constant <code>parameter</code> */
    public static final Property parameter = property("parameter");
    /** Constant <code>annotationProperty</code> */
    public static final Property annotationProperty = property("annotationProperty");
    /** Constant <code>annotationValue</code> */
    public static final Property annotationValue = property("annotationValue");
    /** Constant <code>resultAnnotation</code> */
    public static final Property resultAnnotation = property("resultAnnotation");
    /** Constant <code>sparqlGenerator</code> */
    public static final Property sparqlGenerator = property("sparqlGenerator");
    /** Constant <code>basedOnPattern</code> */
    public static final Property basedOnPattern = property("basedOnPattern");
    /** Constant <code>parameterConstraint</code> */
    public static final Property parameterConstraint = property("parameterConstraint");
    /** Constant <code>constraintPattern</code> */
    public static final Property constraintPattern = property("constraintPattern");
    /** Constant <code>sparqlWherePattern</code> */
    public static final Property sparqlWherePattern = property("sparqlWherePattern");
    /** Constant <code>sparqlPrevalencePattern</code> */
    public static final Property sparqlPrevalencePattern = property("sparqlPrevalencePattern");
    /** Constant <code>sparqlWhere</code> */
    public static final Property sparqlWhere = property("sparqlWhere");
    /** Constant <code>sparqlPrevalence</code> */
    public static final Property sparqlPrevalence = property("sparqlPrevalence");



    private RDFUNITv() {

    }


    private static Resource resource(String local) {
        return ResourceFactory.createResource(namespace + local);
    }

    private static Property property(String local) {
        return ResourceFactory.createProperty(namespace, local);
    }
}

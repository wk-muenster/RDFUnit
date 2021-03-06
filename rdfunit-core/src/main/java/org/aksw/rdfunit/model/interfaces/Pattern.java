package org.aksw.rdfunit.model.interfaces;

import com.google.common.base.Optional;
import org.aksw.rdfunit.tests.Binding;

import java.util.Collection;

/**
 * Defines an RDFUnitL Pattern
 *
 * @author Dimitris Kontokostas
 * @since 9/16/13 1:14 PM
 * @version $Id: $Id
 */
public interface Pattern extends Element{

    /**
     * <p>isValid.</p>
     * TODO:Move to another class
     *
     * @return a boolean.
     */
    boolean isValid();

    /*
    * Checks if all given arguments exist in the patters and the opposite
    *
    private boolean validateArguments() {
        //TODO implement this method
        return true;
    } */


    /**
     * Goes through all external annotations and if it finds a literal value with %%XX%%
     * it replaces it with the binding value
     *
     * @param bindings a {@link java.util.Collection} object.
     * @return a {@link java.util.Collection} object.
     */
    Collection<ResultAnnotation> getBindedAnnotations(Collection<Binding> bindings);

    /**
     * <p>Getter for the field <code>iri</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getIRI();

    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getId();

    /**
     * <p>Getter for the field <code>description</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getDescription();

    /**
     * <p>Getter for the field <code>sparqlWherePattern</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getSparqlWherePattern();

    /**
     * <p>Getter for the field <code>sparqlPatternPrevalence</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    Optional<String> getSparqlPatternPrevalence();

    /**
     * Returns the Pattern Parameters as an immutable Collection
     *
     * @return the pattern parameters as an Collections.unmodifiableCollection()
     */
    Collection<PatternParameter> getParameters();

    /**
     * Returns a parameter object from a parameter URI
     *
     * @param parameterURI the parameter uRI
     * @return the parameter object
     */
    Optional<PatternParameter> getParameter(String parameterURI);


}

package org.aksw.rdfunit.model.interfaces;

import org.aksw.rdfunit.sources.SchemaSource;

import java.util.Collection;

/**
 * Interface to a function according to SHACL
 *
 * @author Dimitris Kontokostas
 * @since 6/17/15 3:10 PM
 * @version $Id: $Id
 */
public interface TestGenerator extends Element {

    /**
     * <p>Getter for the field <code>uri</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getTAGUri();

    /**
     * <p>Getter for the field <code>description</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getTAGDescription();

    /**
     * <p>Getter for the field <code>query</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getTAGQuery();

    /**
     * <p>Getter for the field <code>pattern</code>.</p>
     *
     * @return a {@link org.aksw.rdfunit.model.interfaces.Pattern} object.
     */
    Pattern getTAGPattern();

    /**
     * <p>Generate TestCases based on a Source.</p>
     * TODO:move this to another class
     *
     * @param source a {@link org.aksw.rdfunit.sources.Source} object.
     * @return a {@link java.util.Collection} object.
     */
    Collection<TestCase> generate(SchemaSource source);

    /**
     * Checks if the the generator is valid (provides correct parameters)
     * TODO: move this function to a standalone class
     *
     * @return a boolean.
     */
    boolean isValid();

}

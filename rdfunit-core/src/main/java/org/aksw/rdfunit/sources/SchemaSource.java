package org.aksw.rdfunit.sources;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import org.aksw.rdfunit.enums.TestAppliesTo;
import org.aksw.rdfunit.io.reader.RDFReader;
import org.aksw.rdfunit.io.reader.RDFReaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>SchemaSource class.</p>
 *
 * @author Dimitris Kontokostas
 * @since 9/16/13 1:51 PM
 * @version $Id: $Id
 */
public class SchemaSource implements Source {
    /** Constant <code>log</code> */
    protected static final Logger log = LoggerFactory.getLogger(SchemaSource.class);


    protected final SourceConfig sourceConfig;
    protected final String schema;
    protected final RDFReader schemaReader;

    protected final OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, ModelFactory.createDefaultModel());

    SchemaSource(SourceConfig sourceConfig, RDFReader schemaReader) {
        this(sourceConfig, sourceConfig.getUri(), schemaReader);
    }


    SchemaSource(SourceConfig sourceConfig, String schema, RDFReader schemaReader) {
        this.sourceConfig = sourceConfig;
        this.schema = schema;
        this.schemaReader = schemaReader;
    }

    SchemaSource(SchemaSource source) {
        this.sourceConfig = source.sourceConfig;
        this.schema = source.getSchema();
        this.schemaReader = source.schemaReader;
    }

    /** {@inheritDoc} */
    @Override
    public String getPrefix() {
        return sourceConfig.getPrefix();
    }

    /** {@inheritDoc} */
    @Override
    public String getUri() {
        return sourceConfig.getUri();
    }

    /** {@inheritDoc} */
    @Override
    public TestAppliesTo getSourceType() {
        return TestAppliesTo.Schema;
    }

    /**
     * <p>Getter for the field <code>model</code>.</p>
     *
     * @return a {@link com.hp.hpl.jena.rdf.model.Model} object.
     * @since 0.7.6
     */
    public Model getModel() {
        if (model.isEmpty())
        try {
            schemaReader.read(model);
        } catch (RDFReaderException e) {
            log.error("Cannot load ontology: {} ", getSchema(), e);
        }
        return model;
    }

    /**
     * <p>Getter for the field <code>schema</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSchema() {
        return schema;
    }

}

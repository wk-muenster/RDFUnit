package org.aksw.rdfunit.model.writers;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import org.aksw.rdfunit.enums.ValueKind;
import org.aksw.rdfunit.model.interfaces.Argument;
import org.aksw.rdfunit.vocabulary.SHACL;

/**
 * Description
 *
 * @author Dimitris Kontokostas
 * @since 6/17/15 5:57 PM
 * @version $Id: $Id
 */
public final class ArgumentWriter implements ElementWriter {

    private final Argument argument;

    private ArgumentWriter(Argument argument) {
        this.argument = argument;
    }

    /**
     * <p>createArgumentWriter.</p>
     *
     * @param argument a {@link org.aksw.rdfunit.model.interfaces.Argument} object.
     * @return a {@link org.aksw.rdfunit.model.writers.ArgumentWriter} object.
     */
    public static ArgumentWriter createArgumentWriter(Argument argument) {return new ArgumentWriter(argument);}

    /** {@inheritDoc} */
    @Override
    public Resource write(Model model) {
        Resource resource = ElementWriterUtils.copyElementResourceInModel(argument, model);

        // rdf:type sh:Argument
        resource.addProperty(RDF.type, SHACL.Argument);

        // sh:predicate sh:argX
        resource.addProperty(SHACL.predicate, argument.getPredicate()) ;

        // rdfs:comment
        if (!argument.getComment().isEmpty())
            resource.addProperty(RDFS.comment, argument.getComment());

        // default value
        if (argument.getDefaultValue().isPresent()) {
            resource.addProperty(SHACL.defaultValue, argument.getDefaultValue().get()) ;
        }

        // get  valueType / Datatype
        if (argument.getValueType().isPresent() && argument.getValueKind().isPresent()) {
            Property property = (argument.getValueKind().get().equals(ValueKind.DATATYPE)) ? SHACL.datatype : SHACL.valueType ;
            resource.addProperty(property, argument.getValueType().get()) ;
        }

        //Optional
        if (argument.isOptional()) {
            resource.addProperty(SHACL.optional, ResourceFactory.createTypedLiteral("true", XSDDatatype.XSDboolean)) ;
        }
        return resource;
    }
}

package org.aksw.rdfunit.services;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import org.aksw.rdfunit.Resources;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

public class PrefixNSServiceTest {

    /* tests if the prefix map is setup correctly */
    @Test
    public void testGetPrefix() throws Exception {
        Model prefixModel = ModelFactory.createDefaultModel();
        try (InputStream is = org.aksw.rdfunit.services.PrefixNSService.class.getResourceAsStream(Resources.PREFIXES)) {
            prefixModel.read(is, null, "TURTLE");
        }
        // Update Prefix Service
        Map<String, String> prefixes = prefixModel.getNsPrefixMap();
        for (Map.Entry<String, String> entry : prefixes.entrySet()) {
            // All entries should match
            String uri = org.aksw.rdfunit.services.PrefixNSService.getNSFromPrefix(entry.getKey());
            Assert.assertEquals("All prefixed should be initialized", uri, entry.getValue());
        }
    }
}
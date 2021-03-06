package org.aksw.rdfunit.tests.executors;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;
import org.aksw.rdfunit.enums.RLOGLevel;
import org.aksw.rdfunit.exceptions.TestCaseExecutionException;
import org.aksw.rdfunit.model.interfaces.ResultAnnotation;
import org.aksw.rdfunit.model.interfaces.TestCase;
import org.aksw.rdfunit.sources.TestSource;
import org.aksw.rdfunit.tests.query_generation.QueryGenerationFactory;
import org.aksw.rdfunit.tests.results.ExtendedTestCaseResult;
import org.aksw.rdfunit.tests.results.TestCaseResult;
import org.aksw.rdfunit.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Extended Test Executor extends RLOG Executor but provides richer error metadata
 * TODO: At the moment this is partially
 *
 * @author Dimitris Kontokostas
 * @since 2 /2/14 6:13 PM
 * @version $Id: $Id
 */
public class ExtendedTestExecutor extends RLOGTestExecutor {

    /**
     * Instantiates a new ExtendedTestExecutor
     *
     * @param queryGenerationFactory a QueryGenerationFactory
     */
    public ExtendedTestExecutor(QueryGenerationFactory queryGenerationFactory) {
        super(queryGenerationFactory);
    }

    /** {@inheritDoc} */
    @Override
    protected Collection<TestCaseResult> executeSingleTest(TestSource testSource, TestCase testCase) throws TestCaseExecutionException {

        Collection<TestCaseResult> testCaseResults = new ArrayList<>();

        QueryExecution qe = null;
        try {
            qe = testSource.getExecutionFactory().createQueryExecution(queryGenerationFactory.getSparqlQuery(testCase));
            ResultSet results = qe.execSelect();

            ExtendedTestCaseResult result = null;
            String prevResource = "";

            while (results.hasNext()) {

                QuerySolution qs = results.next();

                String resource = qs.get("resource").toString();
                if (qs.get("resource").isLiteral()) {
                    resource = StringUtils.getHashFromString(resource);
                }
                String message = testCase.getResultMessage();
                if (qs.contains("message")) {
                    message = qs.get("message").toString();
                }
                RLOGLevel logLevel = testCase.getLogLevel();

                // If resource != before
                // we add the previous result in the list
                if (!prevResource.equals(resource)) {
                    // The very first time we enter, result = null and we don't add any result
                    if (result != null) {
                        testCaseResults.add(result);
                    }

                    result = new ExtendedTestCaseResult(testCase, resource, message, logLevel);
                }

                // result must be initialized by now
                checkNotNull(result);

                for (Map.Entry<ResultAnnotation, Set<RDFNode>> vaEntry : result.getVariableAnnotationsMap().entrySet()) {
                    // Get the variable name
                    String variable = vaEntry.getKey().getAnnotationVarName().get().trim();
                    //If it exists, add it in the Set
                    if (qs.contains(variable)) {
                        vaEntry.getValue().add(qs.get(variable));
                    }
                }
            }
            // Add last result (if query return any)
            if (result != null) {
                testCaseResults.add(result);
            }
        } catch (QueryExceptionHTTP e) {
            checkQueryResultStatus(e);
        } finally {
            if (qe != null) {
                qe.close();
            }
        }

        return testCaseResults;

    }
}

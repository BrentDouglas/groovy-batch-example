package example

import javax.batch.api.Batchlet;

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 * @since 1.0
 */
public class GroovyBatchlet implements Batchlet {
    @Override
    String process() throws Exception {
        return "This worked fine"
    }

    @Override
    void stop() throws Exception {
        // no op
    }
}
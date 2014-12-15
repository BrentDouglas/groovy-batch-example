package example

import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

import javax.batch.operations.JobOperator
import javax.batch.runtime.BatchRuntime
import javax.batch.runtime.JobExecution
import javax.batch.runtime.StepExecution

import static javax.batch.runtime.BatchStatus.ABANDONED
import static javax.batch.runtime.BatchStatus.COMPLETED
import static javax.batch.runtime.BatchStatus.FAILED
import static javax.batch.runtime.BatchStatus.STOPPED

/**
 * @author <a href="mailto:brent.n.douglas@gmail.com">Brent Douglas</a>
 * @since 1.0
 */
class GroovyBatchletTest extends Assert {

    private static JobOperator operator;

    @BeforeClass
    public static void beforeClass() {
        //ServiceTypes.serviceImplClassNames.put ServiceTypes.Name.JAVA_EDITION_IS_SE_DUMMY_SERVICE, "true"
        operator = BatchRuntime.getJobOperator();
    }

    @Test
    public void testGroovyBatchlet() throws InterruptedException {
        final JobExecution job = awaitJob "groovyTest"
        assertEquals COMPLETED, job.batchStatus

        final StepExecution step = operator.getStepExecutions(job.executionId).get(0)
        assertEquals COMPLETED, step.batchStatus
        assertEquals "This worked fine", step.exitStatus
    }

    private static JobExecution awaitJob(final String job) {
        final long id = operator.start job, null
        for (;;) {
            final JobExecution execution = operator.getJobExecution id
            switch (execution.batchStatus) {
                case ABANDONED:
                case STOPPED:
                case FAILED:
                case COMPLETED:
                    return execution;
            }
            try {
                Thread.sleep 100
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

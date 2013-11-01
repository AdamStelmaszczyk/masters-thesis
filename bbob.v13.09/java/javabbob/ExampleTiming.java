package javabbob;

import java.util.Random;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/** Wrapper class running an entire BBOB timing experiment.
 * Measure the CPU-time of an experiment using an algorithm (in the example
 * MY_OPTIMIZER) on the function f8 of the BBOB testbeds.
 *
 * Information provided by running the timing experiment using your algorithm
 * are required in your BBOB workshop paper.
 */
public class ExampleTiming {

    /** Example optimiser.
     * In the following, the pure random search optimization method is
     * implemented as an example.
     * Please include/insert any code as suitable.<p>
     * 
     * This optimiser takes as argument an instance of JNIfgeneric
     * which have all the information on the problem to solve.
     * Only the methods getFtarget() and evaluate(x) of the class JNIfgeneric
     * are used.<p>
     *
     * This method also takes as argument an instance of Random since one
     * might want to set the seed of the random search.<p>
     *
     * The optimiser generates random vectors evaluated on fgeneric until
     * the number of function evaluations is greater than maxfunevals or
     * a function value smaller than the target given by fgeneric.getFtarget()
     * is attained.
     * @param fgeneric an instance JNIfgeneric object
     * @param dim an integer giving the dimension of the problem
     * @param maxfunevals an integer giving the maximum number of function evaluations
     * @param rand an instance of Random
     */
    public static void MY_OPTIMIZER(JNIfgeneric fgeneric, int dim, long maxfunevals, Random rand) {

        double[] x = new double[dim];

        /* Obtain the target function value, which only use is termination */
        double ftarget = fgeneric.getFtarget();
        double f;

        if (maxfunevals > 1000000000 * (long)dim) {
             maxfunevals = 1000000000 * (long)dim;
        }

        for (int iter = 0; iter < maxfunevals; iter++) {
            /* Generate individual */
            for (int i = 0; i < dim; i++) {
                x[i] = 10. * rand.nextDouble() - 5.;
            }

            /* evaluate X on the objective function */
            f = fgeneric.evaluate(x);

            if (f < ftarget) {
                break;
            }
        }
    }

    /** Main method for running the whole BBOB timing experiment.
     *  It will run an optimizer (in this case MY_OPTIMIZER) on the function
     *  8 of the BBOB testbeds in dimensions from 2 to 40. The run will last
     *  at least 30 seconds for each dimension (if the algorithm stopped,
     *  it is restarted). The method will display (in the standard output)
     *  information such as the number of runs and the time per function
     *  evaluations in seconds for each dimension.
     */
    public static void main(String[] args) {

        /* your own internal stuff */
        /* External initialization of MY_OPTIMIZER */
        Random rand = new Random(System.currentTimeMillis());

        final int dim[] = {2, 3, 5, 10, 20, 40};
        double timings[] = new double[dim.length];
        int runs[] = new int[dim.length];
        int dims[] = new int[dim.length];
        long t0;
        int idx_dim, nbrun;
        JNIfgeneric fgeneric = new JNIfgeneric();

        JNIfgeneric.Params params = new JNIfgeneric.Params();
        params.algName = "PUT ALGORITHM NAME";
        params.comments = "PUT MORE DETAILED INFORMATION, PARAMETER SETTINGS ETC";
        String outputPath = "tmp";

        if ( JNIfgeneric.makeBBOBdirs(outputPath, false) ) {
            System.out.println("BBOB data directories at " + outputPath
                    + " created.");
        } else {
            System.out.println("Error! BBOB data directories at " + outputPath
                    + " NOT created, stopping.");
            return; 
        };

        for (idx_dim = 0; idx_dim < 6; idx_dim++) {
            nbrun = 0;
            fgeneric.initBBOB(8, 1, dim[idx_dim], outputPath, params);
            t0 = System.currentTimeMillis();
            while (System.currentTimeMillis() - t0 < 30000) {
                MY_OPTIMIZER(fgeneric, dim[idx_dim], 100000, rand); /*adjust maxfunevals*/
                nbrun ++;
            }

            timings[idx_dim] = (double)(System.currentTimeMillis() - t0)/ 3600000. / (double)fgeneric.getEvaluations();
            dims[idx_dim] = dim[idx_dim];
            runs[idx_dim] = nbrun;
            fgeneric.exitBBOB();
            System.out.print("Dimensions:");
            for (int i = 0; i <= idx_dim; i++) {
                System.out.printf(" %11d ", dims[i]);
            }
            System.out.print("\n      runs:");
            for (int i = 0; i <= idx_dim; i++) {
                System.out.printf(" %11d ", runs[i]);
            }
            System.out.print("\n times [s]:");
            for (int i = 0; i <= idx_dim; i++) {
                System.out.printf(" %11.1e ", timings[i]);
            }
            System.out.println();
        }
    }
}

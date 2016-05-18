import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.print.attribute.standard.RequestingUserName;

import org.HdrHistogram.*;
import org.HdrHistogram.AbstractHistogram.RecordedValues;
import org.apache.commons.math3.primes.Primes;

public class Main {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		temp0();
		temp1();
		temp2();
		temp3();
		temp4();
	}

	private static void temp0() {
		Histogram histogram;
		Recorder recorder = new Recorder(10, 2);
		recorder.recordValue(3);
		System.out.println("Recorder count = " + recorder.getIntervalHistogram().getTotalCount());
		recorder.recordValue(5);
		System.out.println("Recorder count = " + recorder.getIntervalHistogram().getTotalCount());
		try {
			recorder.recordValue(150);
		} catch (Exception ex) {
			System.err.println("Exception occured");
		}
		System.out.println("Recorder count = " + recorder.getIntervalHistogram().getTotalCount());
		
	}

	private static void temp4() {
		System.out.println("67 is a prime number: " + Primes.isPrime(64));
		System.out.println(Long.MAX_VALUE % 3);
		
	}

	static int i =0;
	private static void temp3() throws InterruptedException {
		Executor executor = Executors.newSingleThreadExecutor();
		for ( i = 0;i<5; i++) {
			executor.execute(() -> {
				try {
					Thread.sleep(1000 * 3);
					System.out.println("Executor used " + i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			Thread.sleep(2000);
			System.out.println(i);
		}
	}

	private static void temp2() {
		ConcurrentHistogram histogram = new ConcurrentHistogram(5);
		histogram.recordValue(100);
		histogram.recordValue(101);
		histogram.recordValue(102);
		histogram.recordValue(1000);
		histogram.recordValue(1001);
		histogram.recordValue(1002);
		histogram.recordValue(10000);
		histogram.recordValue(10001);
		histogram.recordValue(10002);
		histogram.recordValue(100000);
		histogram.recordValue(100001);
		histogram.recordValue(100002);
		histogram.recordValue(1000000);
		histogram.recordValue(1000001);
		histogram.recordValue(1000002);
		histogram.recordValue(10000000);
		histogram.recordValue(10000001);
		histogram.recordValue(10000002);
		histogram.recordValue(100000000);
		histogram.recordValue(100000001);
		histogram.recordValue(100000002);
		histogram.recordValue(1000000000);
		histogram.recordValue(1000000001);
		histogram.recordValue(1000000002);
		RecordedValues recordedValues = histogram.recordedValues();
		for(HistogramIterationValue values : recordedValues)
			System.out.println(values);
		
		System.out.println("\n\n\n");
		
		ConcurrentHistogram histogram2 = new ConcurrentHistogram(5);
		histogram2.add(histogram);
		recordedValues = histogram.recordedValues();
		for(HistogramIterationValue values : recordedValues)
			System.out.println(values);
	}

	public static void temp1() throws FileNotFoundException {
		String fileName = "/Users/jai1/Documents/workspace/Temp/src/main/resources/input.txt";
		HistogramLogWriter writer = new HistogramLogWriter(fileName);
		Histogram histogram = new Histogram(5);
		ExecutorService thread = Executors.newSingleThreadExecutor();
		thread.submit(() -> {
			for (int i = 1; ; i++)
				histogram.recordValue(i);
		});
		System.err.println(histogram.getPercentileAtOrBelowValue(30000));
		writer.outputIntervalHistogram(histogram);
		System.err.println(histogram.getPercentileAtOrBelowValue(30000));
		
		HistogramLogReader reader = new HistogramLogReader(fileName);
		Histogram histogram2 = (Histogram) reader.nextIntervalHistogram();

		System.err.println(histogram2.getPercentileAtOrBelowValue(30000));
	}

}
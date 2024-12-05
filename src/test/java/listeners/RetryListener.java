package listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class RetryListener implements IAnnotationTransformer {
	public void transform(ITestAnnotation testAnnotation, Class testClass, Constructor testConstructor, Method testMthod) {
		System.out.println(">>> Unser RetryListener -> Transform method");
		
		// Check if retry analyzer is already set, if not, set it
		
		if(testAnnotation.getRetryAnalyzerClass()==null) {
			//Set the retry analyzer class to FailRetry
			System.out.println("Under RetryListener -> condition");
			testAnnotation.setRetryAnalyzer(FailRetry.class);
		}else {
			testAnnotation.setRetryAnalyzer(FailRetry.class);
		}
	}
}

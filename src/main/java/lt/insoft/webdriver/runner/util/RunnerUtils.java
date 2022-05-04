package lt.insoft.webdriver.runner.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MemberUsageScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterNamesScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class RunnerUtils {

	public static List<String> getTestMethods(String packageName) {
//		Reflections reflections = new Reflections(new ConfigurationBuilder()
//				.setUrls(ClasspathHelper.forPackage(packageName)).setScanners(new MethodAnnotationsScanner()));
		Reflections reflections = new Reflections(packageName, new MethodAnnotationsScanner());
		Collection<Method> methods = reflections.getMethodsAnnotatedWith(Test.class);

		List<String> result = new ArrayList<String>();
		for (Method method : methods) {
			result.add(method.getDeclaringClass().getName() + ":" + method.getName());
		}
		Collections.sort(result);

		return result;
	}

	public static Set<Method> getMethodsAnnotatedWith(Class<?> type, Class<? extends Annotation> annotation)
			throws ClassNotFoundException {
		Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forClass(type))
				.setScanners(new MethodAnnotationsScanner()));
		return reflections.getMethodsAnnotatedWith(annotation);
	}

	public static void invokeMultiple(Collection<Method> methods) throws Exception {
		invokeMultiple(null, methods);
	}

	public static void invokeMultiple(Object instance, Collection<Method> methods) throws Exception {
		for (Method method : methods) {
			method.invoke(instance, new Object[] {});
		}
	}
	
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public static List<String> getPackagesUnderPackage(String packageToLoadClassesFrom, String packageStartToFilterBy) { 
        Reflections reflections = new Reflections();
        FilterBuilder TestModelFilter = new FilterBuilder().include(packageToLoadClassesFrom);
        reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(Arrays.asList(ClasspathHelper.forClass(RunnerUtils.class))).filterInputsBy(TestModelFilter)
                .setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner(), new FieldAnnotationsScanner(),
                        new MethodAnnotationsScanner(), new MethodParameterScanner(), new MethodParameterNamesScanner(),
                        new MemberUsageScanner()));

        Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);
        for (Iterator it = allClasses.iterator(); it.hasNext();) {
            Class<? extends Object> clazz = (Class<? extends Object>) it.next();
        }
        List<String> list =  findPackageNamesStartingWith("com.test.scripts");
        Collections.sort(list);
		return list;
	}
	
	private static List<String> findPackageNamesStartingWith(String prefix) {
	    return Stream.of(Package.getPackages())
	        .map(Package::getName)
	        .filter(n -> n.startsWith(prefix))
	        .collect(Collectors.toList());
	}


}
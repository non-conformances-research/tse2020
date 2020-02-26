package executors;

import mining.NonConformancesStudy;
import util.PropertiesManager;
import com.google.common.collect.Lists;
import util.Binary;
import util.FileUtil;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.Permission;
import java.security.ProtectionDomain;
import java.util.*;

public class ReflectionAPIExecutor {

    private static Map<Class, String> methods = new HashMap<>();
    private static Map<String, String> files = new HashMap<>();
    private static List<Class> reflectionClasses = new ArrayList<>();
    private static List<String> javaTypes = new ArrayList<>();
    private static List<String> skippedKeys = new ArrayList<>();
    private static Set<Method> publicMethods = new HashSet<>();
    private static Set<Class> exercisedClasses = new HashSet<>();
    private static Set<String> keys = new HashSet<>();

	static {
		methods.put(Constructor.class, "getName");
		methods.put(TypeVariable.class, "getTypeName");
		methods.put(Type.class, "getTypeName");
		methods.put(Field.class, "getName");
		methods.put(Class.class, "getName");
		methods.put(AnnotatedType.class, "getType");
		methods.put(Annotation.class, "annotationType");
		reflectionClasses.add(AccessibleObject.class);
		reflectionClasses.add(Array.class);
		reflectionClasses.add(Constructor.class);
		reflectionClasses.add(Executable.class);
		reflectionClasses.add(Field.class);
		reflectionClasses.add(Method.class);
		reflectionClasses.add(Modifier.class);
		reflectionClasses.add(Parameter.class);
		reflectionClasses.add(ReflectPermission.class);
		reflectionClasses.add(Proxy.class);
		reflectionClasses.add(Package.class);
		reflectionClasses.add(Type.class);
		reflectionClasses.add(AnnotatedType.class);
		reflectionClasses.add(TypeVariable.class);
		reflectionClasses.add(Field[].class);
		reflectionClasses.add(Method[].class);
		reflectionClasses.add(Constructor[].class);
		reflectionClasses.add(AnnotatedType[].class);
		reflectionClasses.add(Package[].class);
		reflectionClasses.add(Parameter[].class);
		reflectionClasses.add(Type[].class);
		reflectionClasses.add(TypeVariable[].class);
		reflectionClasses.add(Class[].class);
		reflectionClasses.add(Class.class);
		reflectionClasses.add(ClassLoader.class);
		reflectionClasses.add(ClassLoader[].class);
		javaTypes.add(Object.class.toString());
		javaTypes.add(String.class.toString());
		skippedKeys.add("class org.mot.common.mq.ActiveMQFactory#Class#newInstance#[void]:");
		skippedKeys.add("class org.mot.common.util.TickPriceReplayer#Class#newInstance#[void]:");
		skippedKeys.add("public static void com.baidu.unbiz.multitask.spring.integration.TaskBeanContainer.initFetcherContainer()#Method#invoke#[Object->null,Object[]->null]:");
	}

	public static Object[] getParameterSamples(Class type) {
		Object[] result;
		switch (type.toString()) {
			case "int":
				result = new Integer[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE};
				break;
			case "long":
				result = new Long[]{Long.MIN_VALUE, -1l, 0l, 1l, Long.MAX_VALUE};
				break;
			case "double":
				result = new Double[]{Double.MIN_VALUE, -1.0, 0.0, 1.0, Double.MAX_VALUE};
				break;
			case "boolean":
				result = new Boolean[]{false, true};
				break;
			case "char":
				result = new Character[]{' ', 'a'};
				break;
			case "float":
				result = new Float[]{Float.MIN_VALUE, -1.0f, 0.0f, 1.0f, Float.MAX_VALUE};
				break;
			case "short":
				result = new Short[]{Short.MIN_VALUE, -1, 0, 1, Short.MAX_VALUE};
				break;
			case "byte":
				result = new Byte[]{Byte.MIN_VALUE, -1, 0, 1, Byte.MAX_VALUE};
				break;
			case "class java.lang.String":
				result = new String[]{"", " ", null, "#A1", "gEuOVmBvn1"};
				break;
			case "class java.lang.Class":
				result = new Class[]{Class.class, null};
				break;
			case "class [Ljava.lang.Class;":
				result = new Class[][]{new Class[]{int.class}, new Class[]{long.class}, new Class[]{double.class}, new Class[]{boolean.class}, new Class[]{char.class}, new Class[]{float.class},
						new Class[]{short.class}, new Class[]{String.class}, new Class[]{Class.class}, new Class[]{Class[].class}, new Class[]{Object.class}};
				break;
			default:
				result = new Object[]{new Object(), null};
		}
		return result;
	}

	private static void dumpResultsToFile(String projectPath, String content) {
		String projectName = FileUtil.getProjectName(projectPath);
		content = projectName + "#" + content + "\n";
		String resultsFolder = new PropertiesManager().getProperty("results_path");
		String targetFilePath = resultsFolder + NonConformancesStudy.getInstance().getJvm() + ".txt";
		FileUtil.writeToFile(content, targetFilePath, true);
	}

	/**
	 * Combines several collections of elements and create permutations of all of them, taking one element from each
	 * collection, and keeping the same order in resultant lists as the one in original list of collections.
	 *
	 * <ul>Example
	 * <li>Input  = { {a,b,c} , {1,2,3,4} }</li>
	 * <li>Output = { {a,1} , {a,2} , {a,3} , {a,4} , {b,1} , {b,2} , {b,3} , {b,4} , {c,1} , {c,2} , {c,3} , {c,4} }</li>
	 * </ul>
	 *
	 * @param collections Original list of collections which elements have to be combined.
	 * @return Resultant collection of lists with all permutations of original list.
	 */
	public static <T> Collection<List<T>> permutations(List<Collection<T>> collections) {
		if (collections == null || collections.isEmpty()) {
			return Collections.emptyList();
		} else {
			Collection<List<T>> res = Lists.newLinkedList();
			permutationsImpl(collections, res, 0, new LinkedList<T>());
			return res;
		}
	}

	/** Recursive implementation for {@link (List, Collection)} */
	private static <T> void permutationsImpl(List<Collection<T>> ori, Collection<List<T>> res, int d, List<T> current) {
		// if depth equals number of original collections, final reached, add and return
		if (d == ori.size()) {
			res.add(current);
			return;
		}

		// iterate from current collection and copy 'current' element N times, one for each element
		Collection<T> currentCollection = ori.get(d);
		for (T element : currentCollection) {
			List<T> copy = Lists.newLinkedList(current);
			copy.add(element);
			permutationsImpl(ori, res, d + 1, copy);
		}
	}

	public static void invokeMethods(Class type, Object instance, String projectPath) {
		exercisedClasses.add(type);
		if (instance != null && !reflectionClasses.contains(instance)) {
			String key;
			for (Method method : type.getMethods()) {
				if (Modifier.isPublic(method.getModifiers()) /*&& method.getName().equals("getResource") || method.getName().equals("getResourceAsStream")*/) {
					publicMethods.add(method);
					if (instance instanceof AccessibleObject){
						//Handling some false positives
						((AccessibleObject)instance).setAccessible(true);
					}
					if (instance instanceof Parameter) {
						Parameter p = ((Parameter) instance);
						key = p.getType() + " " + p.getName() + "#" + type.getSimpleName() + "#" + method.getName();
					} else {
						key = instance + "#" + type.getSimpleName() + "#" + method.getName();
					}
					Class<?>[] parameterTypes = method.getParameterTypes();
					Map<String, Object> methodInvocationResults = new HashMap<>();
					int parameterTypesLength = parameterTypes.length;
					if (parameterTypesLength > 0) {
						List allParametersSamples = new ArrayList<>();
						for (Class parameterType : parameterTypes) {
							allParametersSamples.add(Arrays.asList(getParameterSamples(parameterType)));
						}
						Collection<List<Object>> parametersSamples = permutations(allParametersSamples);
						for (List pList : parametersSamples) {
							Object[] parameters = pList.toArray();
							String elements = getParametersString(parameterTypes, parameters);
							String subKey = key + "#" + elements + ":";
							if (!methodInvocationResults.containsKey(subKey) && !skippedKeys.contains(subKey)) {
								methodInvocationResults.put(subKey, invokeMethod(instance, method, parameters));
							}

							//Wrong number of arguments
							Object[] wrongArguments = new Object[parameterTypesLength - 1];
							for (int i = 0; i < parameters.length - 1; i++) {
								wrongArguments[i] = parameters[i];
							}
							subKey = key + "#[wrongArguments]:";
							if (!methodInvocationResults.containsKey(subKey) && !skippedKeys.contains(subKey)) {
								methodInvocationResults.put(subKey, invokeMethod(instance, method, wrongArguments));
							}
							//Argument type mismatch
							Object[] mismatchedArgumentTypes = new Object[parameterTypesLength];
							int j = parameterTypesLength;
							for (int i = 0; i < parameters.length; i++) {
								mismatchedArgumentTypes[i] = parameters[j - 1];
								j--;
							}
							elements = getParametersString(parameterTypes, mismatchedArgumentTypes);
							subKey = key + "#" + elements + ":";
							if (!methodInvocationResults.containsKey(subKey) && !skippedKeys.contains(subKey)) {
								methodInvocationResults.put(subKey, invokeMethod(instance, method, mismatchedArgumentTypes));
							}
						}
					} else {
						String subKey = key + "#[void]:";
						if (!methodInvocationResults.containsKey(subKey) && !skippedKeys.contains(subKey)) {
							Object result = invokeMethod(instance, method, null);
							methodInvocationResults.put(subKey, result);
							if (result != null && result.getClass().isArray()) {
								try {
									if (result instanceof Method[]) {
										Method[] methods = (Method[]) result;
										Method getMethod = Class.class.getMethod("getMethod", String.class, Class[].class);
										publicMethods.add(getMethod);
										Method getDeclaredMethod = Class.class.getMethod("getDeclaredMethod", String.class, Class[].class);
										publicMethods.add(getDeclaredMethod);
										for (Method m : methods) {
											subKey = instance + "#" + type.getSimpleName() + "#" + getMethod.getName() + "#[String -> " + m.getName() + "]:";
											if (!methodInvocationResults.containsKey(subKey) && !skippedKeys.contains(subKey)) {
												methodInvocationResults.put(subKey, invokeMethod(instance, getMethod, new Object[]{m.getName(), null}));
											}
											subKey = instance + "#" + type.getSimpleName() + "#" + getDeclaredMethod.getName() + "#[String -> " + m.getName() + "]:";
											if (!methodInvocationResults.containsKey(subKey) && !skippedKeys.contains(subKey)) {
												methodInvocationResults.put(subKey, invokeMethod(instance, getDeclaredMethod, new Object[]{m.getName(), null}));
											}
										}
									}
									if (result instanceof Field[]) {
										Field[] fields = (Field[]) result;
										Method getField = Class.class.getMethod("getField", String.class);
										publicMethods.add(getField);
										Method getDeclaredField = Class.class.getMethod("getDeclaredField", String.class);
										publicMethods.add(getDeclaredField);
										for (Field f : fields) {
											subKey = instance + "#" + type.getSimpleName() + "#" + getField.getName() + "#[String -> " + f.getName() + "]:";
											if (!methodInvocationResults.containsKey(subKey) && !skippedKeys.contains(subKey)) {
												methodInvocationResults.put(subKey, invokeMethod(instance, getField, f.getName()));
											}
											subKey = instance + "#" + type.getSimpleName() + "#" + getDeclaredField.getName() + "#[String -> " + f.getName() + "]:";
											if (!methodInvocationResults.containsKey(subKey) && !skippedKeys.contains(subKey)) {
												methodInvocationResults.put(subKey, invokeMethod(instance, getDeclaredField, f.getName()));
											}
										}
									}
									if (result instanceof Constructor[]) {
										Constructor[] constructors = (Constructor[]) result;
										Method getConstructor = Class.class.getMethod("getConstructor", Class[].class);
										publicMethods.add(getConstructor);
										Method getDeclaredConstructor = Class.class.getMethod("getDeclaredConstructor", Class[].class);
										publicMethods.add(getDeclaredConstructor);
										for (Constructor c : constructors) {
											String elements = getParametersString(c.getParameterTypes(), c.getParameters());
											subKey = instance + "#" + type.getSimpleName() + "#" + getConstructor.getName() + "#" + elements + ":";
											if (!methodInvocationResults.containsKey(subKey) && !skippedKeys.contains(subKey)) {
												methodInvocationResults.put(subKey, invokeMethod(instance, getConstructor, c.getParameterTypes()));
											}
											subKey = instance + "#" + type.getSimpleName() + "#" + getDeclaredConstructor.getName() + "#" + elements + ":";
											if (!methodInvocationResults.containsKey(subKey) && !skippedKeys.contains(subKey)) {
												methodInvocationResults.put(subKey, invokeMethod(instance, getDeclaredConstructor, c.getParameterTypes()));
											}
										}
									}
								} catch (NoSuchMethodException e) {
								}
							}
						}
						//Wrong number of arguments
						subKey = key + "#[void -> \"\"]:";
						if (!methodInvocationResults.containsKey(key) && !skippedKeys.contains(key)) {
							methodInvocationResults.put(subKey, invokeMethod(instance, method, new Object[]{""}));
						}
					}

					for (String resultsKey : methodInvocationResults.keySet()) {
						String textResult = resultsKey;
						Object methodInvocationResult = methodInvocationResults.get(resultsKey);
						if (!keys.contains(resultsKey)) {
							if (methodInvocationResult != null) {
								if (methodInvocationResult instanceof AnnotatedType) {
									methodInvocationResult = ((AnnotatedType) methodInvocationResult).getType().getTypeName();
								} else if (methodInvocationResult instanceof URLClassLoader) {
									URL[] urls = ((URLClassLoader) methodInvocationResult).getURLs();
									String urlsResult = "";
									for (URL url : urls) {
										urlsResult += getFileName(url) + ";";
									}
									methodInvocationResult = urlsResult;
								} else if (methodInvocationResult instanceof String) {
									if (methodInvocationResult.toString().contains("@")) {
										methodInvocationResult = methodInvocationResult.toString().split("@")[0];
									}
								} else if (methodInvocationResult instanceof ProtectionDomain) {
									Enumeration<Permission> elements = ((ProtectionDomain) methodInvocationResult).getPermissions().elements();
									methodInvocationResult = "";
									while (elements.hasMoreElements()) {
										methodInvocationResult += elements.nextElement().getActions() + ";";
									}
								} else if (methodInvocationResult instanceof AnnotatedType[]) {
									AnnotatedType[] methodInvocationResultArray = (AnnotatedType[]) methodInvocationResult;
									if (methodInvocationResultArray.length > 0) {
										methodInvocationResult = "";
										for (AnnotatedType at : methodInvocationResultArray) {
											methodInvocationResult += at.getType().getTypeName() + ";";
										}
									}
								} else if (methodInvocationResult instanceof Annotation[]) {
									Annotation[] methodInvocationResultArray = (Annotation[]) methodInvocationResult;
									if (methodInvocationResultArray.length > 0) {
										methodInvocationResult = "";
										for (Annotation a : methodInvocationResultArray) {
											methodInvocationResult += a.toString() + ";";
										}
									}
								} else if (!(methodInvocationResult instanceof AbstractCollection) && methodInvocationResult.toString().contains("@") && !reflectionClasses.contains(methodInvocationResult.getClass())) {
									methodInvocationResult = methodInvocationResult.toString().split("@")[0];
								} else if (method.getName().equalsIgnoreCase("getresource") || methodInvocationResult instanceof URL) {
									URL url = (URL) methodInvocationResult;
									methodInvocationResult = getFileName(url);
								}
								if (methodInvocationResult.getClass().isArray()) {
									Object[] results = (Object[]) methodInvocationResult;
									for (Object r : results) {
										if (reflectionClasses.contains(method.getReturnType())
												&& !method.getReturnType().isPrimitive()
												&& !javaTypes.contains(method.getReturnType().toString())
												&& !keys.contains(resultsKey)) {
											keys.add(resultsKey);
											invokeMethods(method.getReturnType().getComponentType(), r, projectPath);
										}
										try {
											textResult += r.toString() + ";";
										} catch (Exception e) {
											textResult += e.getMessage() + ";";
										}
									}
								} else {
									textResult += methodInvocationResult;
									if (reflectionClasses.contains(method.getReturnType())
											&& !method.getReturnType().isPrimitive()
											&& !javaTypes.contains(methodInvocationResult.toString())
											&& !keys.contains(resultsKey)) {
										keys.add(resultsKey);
										invokeMethods(method.getReturnType(), methodInvocationResult, projectPath);
									}
								}
							} else {
								textResult += methodInvocationResult;
							}
							keys.add(resultsKey);
							dumpResultsToFile(projectPath, textResult);
						}
					}
				}
			}
		}
	}

	private static String getParametersString(TypeVariable[] parameterTypes) {
		String elements = "[";
		for (TypeVariable tv : parameterTypes) {
			elements += tv.getTypeName() + ",";
		}
		if (elements.length() == 1) {
			elements += "void";
		} else {
			elements = elements.substring(0, elements.length() - 1);
		}
		elements += "]";
		return elements;
	}

	public static String getParametersString(Class<?>[] parameterTypes, Object[] parameters) {
		String elements = "[";
		for (int i = 0; i < parameters.length; i++) {
            Object parameterSample = parameters[i];
            String parameterSampleString = "null";
            if (parameterSample != null) {
                parameterSampleString = parameterSample.toString();
            }
            if (parameterSampleString.contains("@")) {
                parameterSampleString = parameterSampleString.split("@")[0];
            }
            elements += parameterTypes[i].getSimpleName() + "->" + parameterSampleString + ",";
        }
		if (elements.length() == 1) {
			elements += "void";
		} else {
			elements = elements.substring(0, elements.length() - 1);
		}
		elements += "]";
		return elements;
	}

	private static Object invokeMethod(Object instance, Method method, Object parameters) {
		Object methodInvocationResult = null;
		try {
			if (parameters != null) {
				methodInvocationResult = method.invoke(instance, parameters);
			} else {
				methodInvocationResult = method.invoke(instance);
			}
		} catch (Exception e) {
			methodInvocationResult = "<exception><type>" + e.toString() + "</type><message>" + e.getMessage() + "</message></exception>";
		} finally {
			return methodInvocationResult;
		}
	}

	private static String getFileName(URL url) {
		String fileName;
		String fileString = url.getFile();
		if (!files.containsKey(fileString)) {
			File file = new File(fileString);
			fileName = file.getName();
			files.put(url.getFile(), fileName);
		} else {
			fileName = files.get(fileString);
		}
		return fileName;
	}

	public static class ExitTrappedException extends SecurityException {
	}

	public static void execute(String projectPath, Binary binary, boolean initialize) {

		try {
			Class<?> loadedBinary = GeneralExecutor.loadClass(projectPath, binary, initialize);
			invokeMethods(Class.class, loadedBinary, projectPath);
		} catch (ExitTrappedException e) {
			System.out.println("Forbidding call to System.exit");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

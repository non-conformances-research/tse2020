# Testing the Java Reflection API

### Test Cases

 * Parameters
 

 | Type 	 | Values |
 |:----------|:---------------:|
 |*int*      | -2147483648, -1, 0, 1, and 2147483647 |
 |*long*     | 2<sup>63</sup>, -1, 0, 1, and (2<sup>63</sup>)-1 |
 |*double*   | 4.9x10<sup>-324</sup>, -1.0, 0, 1.0, and 1.7x10<sup>308</sup> |
 |*boolean*  | false and true |
 |*char*     | '' and 'a' |                   
 |*float*    | 1.4x10<sup>-45</sup>, -1.0f, 0.0f, 1.0f, 3.4028235x10<sup>38</sup> |  
 |*short*    | -32768, -1, 0, 1, 32767|
 |*byte*     | -128, -1, 0, 1, 127 |
 |*String*   | "", " ", "gEuOVmBvn1", "#A1", and null | 
 |*Class*    | Class.class and null |
 |*Class[]*  | new Class[][]{new Class[]{int.class}, new Class[]{long.class}, new Class[]{double.class}, new Class[]{boolean.class}, new Class[]{char.class}, new Class[]{float.class}, new Class[]{short.class}, new Class[]{String.class}, new Class[]{Class.class}, new Class[]{Class[].class}, new Class[]{Object.class}} |
 |Other      | new Object() and null |     

**Table I. Values for Java primitive and non-primitive types used to create test cases.**

 * [Input Programs](subjects.xlsx)

### Results

 * Test cases added to Eclipse OpenJ9 test suite (https://github.com/eclipse/openj9/pull/1792)
   * [Class.getConstructor](test-cases/org/openj9/test/reflect/GetConstructorTests.java)
   * [Class.getConstructors](test-cases/org/openj9/test/reflect/GetConstructorsTests.java)
   * [Class.getDeclaredConstructor](test-cases/org/openj9/test/reflect/GetDeclaredConstructorTests.java)
   * [Class.getDeclaredConstructors](test-cases/org/openj9/test/reflect/GetDeclaredConstructorsTests.java)
   * [Class.getDeclaredField](test-cases/org/openj9/test/reflect/GetDeclaredFieldTests.java)
   * [Class.getDeclaredFields](test-cases/org/openj9/test/reflect/GetDeclaredFieldsTests.java)
   * [Class.getDeclaredMethod](test-cases/org/openj9/test/reflect/GetDeclaredMethodTests.java)
   * [Class.getDeclaredMethods](test-cases/org/openj9/test/reflect/GetDeclaredMethodsTests.java)
   * [Class.getField](test-cases/org/openj9/test/reflect/GetFieldTests.java)
   * [Class.getFields](test-cases/org/openj9/test/reflect/GetFieldsTests.java)
   * [Class.getMethod](test-cases/org/openj9/test/reflect/GetMethodTests.java)
   * [Class.getMethods](test-cases/org/openj9/test/reflect/GetMethodsTests.java)
 
 * Our technique detects false positives for the following methods of Java Reflection API:
   * Class.hashCode
   * Class.newInstance
   * Constructor.isAccessible
   * Constructor.newInstance
   * Field.getInt
   * Field.getLong
   * Field.isAccessible
   * Method.isAccessible
   * Parameter.getDeclaringExecutable
   * Parameter.hashCode
 * Reported Candidates ([see all](all-reported-candidates.md))

 
 | Method 				| Target | Report ID/Bug Tracker URL | Status |
 |:----------:|:---------------|---------------:|---------------:|
 |Class.getAnnotations 			| Specification | 9053675 								|Open |
 |Class.getDeclaredAnnotations 		| Specification | https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8202652	|Duplicated |
 |Executable.getAnnotations 		| Specification | 9053679 								|Open|
 |Executable.getDeclaredAnnotations 	| Specification | 9053680 								|Open|
 |Executable.getParameterAnnotations 	| Specification | 9053682  								|Open|
 |Field.getAnnotations 			| Specification | 9053683  								|Open|
 |Field.getDeclaredAnnotations 		| Specification | 9053684  								|Open|
 |Method.getAnnotations 		| Specification | 9053677  								|Open|
 |Method.getDeclaredAnnotations 	| Specification | 9053678  								|Open|
 |Method.getParameterAnnotations 	| Specification | 9053681  								|Open|
 |Class.getResource 			| Specification | https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8202687 	|Accepted |
 |Class.getResourceAsStream 		| Specification | https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8202687 	|Accepted |
 |Class.getConstructor 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1837				|Fixed |
 |Class.getConstructors 		| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1838				|Fixed |
 |Class.getDeclaredConstructor 		| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1839 			|Fixed |
 |Class.getDeclaredConstructors 	| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1840 			|Fixed |
 |Class.getDeclaredField 		| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1841 			|Fixed |
 |Class.getDeclaredFields 		| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1627 			|Fixed |
 |Class.getDeclaredMethod 		| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1842 			|Fixed |
 |Class.getDeclaredMethods 		| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1843 			|Fixed |
 |Class.getField 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1844 			|Fixed |
 |Class.getFields 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1845 			|Fixed |
 |Class.getMethod 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1846 			|Fixed |
 |Class.getMethods 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1847 			|Fixed |
 |Constructor.getAnnotatedParameterTypes| Oracle	| https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8225394	|Fixed |
 |Constructor.getAnnotatedParameterTypes| Oracle	| https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8225395 	|Fixed |
 |Constructor.getAnnotatedParameterTypes| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1851 			|Fixed |
 |Constructor.getAnnotatedParameterTypes| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/5994 			|Fixed |
 |Constructor.getAnnotatedParameterTypes| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/5995 			|Fixed |
 |Executable.getAnnotatedParameterTypes | Oracle	| https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8225396	|Fixed |
 |Executable.getAnnotatedParameterTypes | Oracle	| https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8225398	|Fixed |
 |Executable.getAnnotatedParameterTypes | Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1852 			|Fixed |
 |Executable.getAnnotatedParameterTypes | Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/5997			|Fixed |
 |Executable.getAnnotatedParameterTypes | Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/5998			|Fixed |
 |Method.invoke 			| Oracle	| https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8202689 	|Duplicated |
 |Method.invoke 			| Oracle	| 9053687 	|Open |
 |Method.invoke 			| Oracle	| 9053686 	|Open |
 |Method.invoke 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1834 		|Rejected |
 |Method.invoke 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1836 		|Rejected |
 |Method.invoke 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1835 		|Rejected |
 |Parameter.getAnnotatedType 		| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1850 		|Fixed |
 |Parameter.getParameterizedType 	| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1854			|Fixed |
 |Parameter.toString 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/1853			|Fixed |

**Table II. Reported candidates.**

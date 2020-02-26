# Testing the Java Collections API

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
 | Collection | new ArrayList(), new ArrayList("", " ", "gEuOVmBvn1", "#A1"), null |
 |Other      | new Object() and null |     

**Table I. Values for Java primitive and non-primitive types used to create test cases.**

### Results
 
 * Our technique detects false positives for the following methods of Java Collections API:
   * ArrayDeque
   * ArrayList.ensureCapacity
   * ArrayList.remove
   * Arrays.copyOfRange
   * ConcurrentSkipListSet.add
   * ConcurrentSkipListMap.put
   * ConcurrentSkipListMap.putIfAbsent
   * Hashtable
   * IdentityHashMap
   * WeakHashMap

 * Reported Candidates ([see all](all-reported-candidates.md))

 
 | Method 				| Target | Report ID/Bug Tracker URL | Status |
 |:----------:|:---------------|---------------:|---------------:|
 |ArrayDeque 			| Specification | 9063252	| Open |
 |ArrayDeque 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/8221 | Rejected |
 |ArrayList.ensureCapacity 	| Specification | https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8227674								|Rejected |
 |ArrayList.ensureCapacity	| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/6449 | Open |
 |ArrayList.remove 		| Specification | 9063256	| Open |
 |Arrays.copyOfRange 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/6682 | Open |
 |Arrays.copyOfRange		| Oracle	| https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8229268	| Rejected |
 |ConcurrentSkipListMap.put	| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/6595 | Open |
 |ConcurrentSkipListMap.put	| Oracle	| https://bugs.java.com/bugdatabase/view_bug.do?bug_id=8228864	| Fixed |
 |ConcurrentSkipListMap.putIfAbsent	| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/6596 | Open |
 |ConcurrentSkipListMap.putIfAbsent| Oracle	| https://bugs.java.com/bugdatabase/view_bug.do?bug_id=8228865	| Fixed |
 |ConcurrentSkipListSet.add		| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/6597 | Open |
 |ConcurrentSkipListSet.add	| Oracle	| https://bugs.java.com/bugdatabase/view_bug.do?bug_id=8228866	| Fixed |
 |Hashtable 			| Specification | 9063253	| Open |
 |Hashtable 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/8222 | Rejected |
 |IdentityHashMap 		| Specification | 9063254	| Open |
 |IdentityHashMap 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/8223 | Rejected |
 |WeakHashMap 			| Specification | 9063255	| Open |
 |WeakHashMap 			| Eclipse OpenJ9| https://github.com/eclipse/openj9/issues/8224 | Rejected |

**Table II. Reported candidates.**

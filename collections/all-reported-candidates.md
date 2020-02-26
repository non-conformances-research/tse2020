# Specification

## ArrayList.ensureCapacity throws OutOfMemoryError

Javadoc of method ArrayList.ensureCapacity does not restrict the capacity of the ArrayList. Sample program:

```
public class A {
   public static void main(String args[]) {
      ArrayList al = new ArrayList();
      al.ensureCapacity(Integer.MAX_VALUE);
   }
}
```

**Affected versions:**

```
Picked up _JAVA_OPTIONS:   -Dawt.useSystemAAFontSettings=gasp
java version "1.8.0_151"
Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using Oracle HotSpot java

**Current result:**

```
Exception in thread "main" java.lang.OutOfMemoryError: Requested array size exceeds VM limit
	at java.util.Arrays.copyOf(Arrays.java:3210)
	at java.util.Arrays.copyOf(Arrays.java:3181)
	at java.util.ArrayList.grow(ArrayList.java:265)
	at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:239)
	at java.util.ArrayList.ensureCapacity(ArrayList.java:219)
	at A.main(A.java:6)
```

**Expected result:**

The ArrayList capacity increased by Integer.MAX_VALUE.

## ArrayDeque documentation does not specify the maximum value for numElements

Documentation should specify a maximum value for numElements parameter. ArrayDeque constructor can throw OutOfMemoryError when executing the following program in other JVMs (e.g. Eclipse OpenJ9):

```
import java.util.ArrayDeque;
public class A {
    public static void main(String[] args) {
        new ArrayDeque(Integer.MAX_VALUE);
    }
}
```

## Hashtable documentation does not specify the maximum value for initialCapacity

Hashtable constructor throws OutOfMemoryError when executing the following program:

```
import java.util.Hashtable;
public class A {
    public static void main(String[] args) {
        new Hashtable(Integer.MAX_VALUE);
    }
}
```

**Affected versions:**

```
Picked up _JAVA_OPTIONS:   -Dawt.useSystemAAFontSettings=gasp
java version "1.8.0_151"
Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using Oracle HotSpot java

**Current result:**
```
Caused by: java.lang.OutOfMemoryError: Requested array size exceeds VM limit
    at java.util.Hashtable.<init> (Hashtable.java:191)
    at java.util.Hashtable.<init> (Hashtable.java:204)
    at A.main (A.java:4)
```
**Expected result:**

Hashtable documentation specifying a maximum value for initialCapacity parameter.

## IdentityHashMap documentation does not specify the maximum value for expectedMaxSize

Documentation should specify a maximum value for expectedMaxSize parameter. 

IdentityHashMap constructor can throw OutOfMemoryError when executing the following program in other JVMs (e.g. Eclipse OpenJ9):

```
import java.util.IdentityHashMap;
public class A {
    public static void main(String[] args) {
        new IdentityHashMap(Integer.MAX_VALUE);
    }
}
```

## WeakHashMap documentation does not specify the maximum value for initialCapacity

Documentation should specify a maximum value for initialCapacity parameter. 

WeakHashMap constructor can throw OutOfMemoryError when executing the following program in other JVMs (e.g. Eclipse OpenJ9):

```
import java.util.WeakHashMap;
public class A {
    public static void main(String[] args) {
        new WeakHashMap(Integer.MAX_VALUE, 1.0f);
    }
}
```

## ArrayList.remove documentation does not specify the message when an exception is thrown

Documentation should specify an exception message. The following program shows “-1” as exception message:

```
import java.util.ArrayList;
public class A {
    public static void main(String[] args) {
        new ArrayList().remove(-1);
    }
}
```

# Eclipse OpenJ9

## ArrayList.ensureCapacity crashes the JVM

ArrayList.ensureCapacity throws OutOfMemoryError when executing the following program:

```
public class A {
   public static void main(String args[]) {
      ArrayList al = new ArrayList();
      al.ensureCapacity(Integer.MAX_VALUE/10);
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9   - e24e8aa9
OMR      - 3e8296b4
JCL      - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

	1. Compile above program
	2. Run it using Eclipse OpenJ9 java

**Current result:**

```
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3210)
	at java.util.Arrays.copyOf(Arrays.java:3181)
	at java.util.ArrayList.grow(ArrayList.java:265)
	at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:239)
	at java.util.ArrayList.ensureCapacity(ArrayList.java:219)
	at A.main(A.java:6)
```

**Expected result:**

The same result as Oracle JVM (ArrayList capacity increased by Integer.MAX_VALUE/10).

## ConcurrentSkipListMap.put presents nondeterministic results

The following sample program presents different results when executed multiple times.

```
public class A {
   public static void main(String args[]) {
      ConcurrentSkipListMap concurrentSkipListMap = new ConcurrentSkipListMap();
      System.out.println(concurrentSkipListMap.put(new Object(), new Object()));
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9   - e24e8aa9
OMR      - 3e8296b4
JCL      - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using Eclipse OpenJ9 java

**Current result:**

Sometimes null, sometimes ClassCastException.

**Expected result:**

null

## ConcurrentSkipListMap.putIfAbsent presents nondeterministic results

The following sample program presents different results when executed multiple times.

```
public class A {
   public static void main(String args[]) {
      ConcurrentSkipListMap concurrentSkipListMap = new ConcurrentSkipListMap();
      System.out.println(concurrentSkipListMap.putIfAbsent(new Object(), new Object()));
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9   - e24e8aa9
OMR      - 3e8296b4
JCL      - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using Eclipse OpenJ9 java

**Current result:**

Sometimes null, sometimes ClassCastException.

**Expected result:**

null

## ConcurrentSkipListSet.add presents nondeterministic results

The following sample program presents different results when executed multiple times.

```
public class A {
   public static void main(String args[]) {
      ConcurrentSkipListSet concurrentSkipListSet = new ConcurrentSkipListSet();
      System.out.println(concurrentSkipListSet.add(new Object()));
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9   - e24e8aa9
OMR      - 3e8296b4
JCL      - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using Eclipse OpenJ9 java

**Current result:**

Sometimes true, sometimes ClassCastException.

**Expected result:**

true

## Arrays.copyOfRange method crashes the JVM

Arrays.copyOfRange specification defines that a NullPointerException should be throw if ```original``` parameter is ```null``` (https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#copyOfRange-byte:A-int-int-). However, it throws OutOfMemoryError when executing the following program:

```
import java.util.Arrays;
public class A {
   public static void main(String args[]) {
      Arrays.copyOfRange((byte[])null, 0, 2147483647);
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9   - e24e8aa9
OMR      - 3e8296b4
JCL      - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

	1. Compile above program
	2. Run it using Eclipse OpenJ9 java

**Current result:**

```
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOfRange(Arrays.java:3520)
	at A.main(A.java:4)
```

**Expected result:**

NullPointerException.

## ArrayDeque constructor crashes the JVM

ArrayDeque throws OutOfMemoryError when executing the following program:

```
import java.util.ArrayDeque;
public class A {
    public static void main(String[] args) {
        new ArrayDeque(Integer.MAX_VALUE);
    }
}
```

**Affected versions:**
```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9   - e24e8aa9
OMR      - 3e8296b4
JCL      - ee1e77df1d based on jdk8u162-b12)
```
**Steps to reproduce:**

	1. Compile above program
	2. Run it using Eclipse OpenJ9 java

**Current result:**

```
Caused by: java.lang.OutOfMemoryError: Java heap space
    at java.util.ArrayDeque.allocateElements (ArrayDeque.java:147)
    at java.util.ArrayDeque.<init> (ArrayDeque.java:203)
    at A.main (Main.java:4)
```

**Expected result:**

An ArrayDeque object.

## Hashtable constructor crashes the JVM

Hashtable constructor throws OutOfMemoryError when executing the following program:

```
import java.util.Hashtable;
public class A {
    public static void main(String[] args) {
        new Hashtable(Integer.MAX_VALUE);
    }
}
```

**Affected versions:**
```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9   - e24e8aa9
OMR      - 3e8296b4
JCL      - ee1e77df1d based on jdk8u162-b12)
```
**Steps to reproduce:**

	1. Compile above program
	2. Run it using Eclipse OpenJ9 java

**Current result:**

```
Caused by: java.lang.OutOfMemoryError: Java heap space
    at java.util.Hashtable.<init> (Hashtable.java:191)
    at java.util.Hashtable.<init> (Hashtable.java:204)
    at A.main (A.java:4)
```

**Expected result:**

A Hashtable object.

## IdentityHashMap constructor crashes the JVM

IdentityHashMap constructor throws OutOfMemoryError when executing the following program:

```
import java.util.IdentityHashMap;
public class A {
    public static void main(String[] args) {
        new IdentityHashMap(Integer.MAX_VALUE);
    }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9   - e24e8aa9
OMR      - 3e8296b4
JCL      - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

	1. Compile above program
	2. Run it using Eclipse OpenJ9 java

**Current result:**

```
Caused by: java.lang.OutOfMemoryError: Java heap space
    at java.util.IdentityHashMap.init (IdentityHashMap.java:255)
    at java.util.IdentityHashMap.<init> (IdentityHashMap.java:227)
    at A.main (A.java:4)
```

**Expected result:**

An IdentityHashMap object.

## WeakHashMap constructor crashes the JVM

WeakHashMap constructor throws OutOfMemoryError when executing the following program:

```
import java.util.WeakHashMap;
public class A {
    public static void main(String[] args) {
        new WeakHashMap(Integer.MAX_VALUE, 1.0f);
    }
}
```

**Affected versions:**
```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9   - e24e8aa9
OMR      - 3e8296b4
JCL      - ee1e77df1d based on jdk8u162-b12)
```
**Steps to reproduce:**

	1. Compile above program
	2. Run it using Eclipse OpenJ9 java

**Current result:**

```
Caused by: java.lang.OutOfMemoryError: Java heap space
    at java.util.WeakHashMap.newTable (WeakHashMap.java:195)
    at java.util.WeakHashMap.<init> (WeakHashMap.java:220)
    at A.main (A.java:4)
```

**Expected result:**

A WeakHashMap object.

# Oracle

## ConcurrentSkipListMap.put presents nondeterministic results

The following sample program presents different results when executed multiple times.

```
public class A {
   public static void main(String args[]) {
      ConcurrentSkipListMap concurrentSkipListMap = new ConcurrentSkipListMap();
      System.out.println(concurrentSkipListMap.put(new Object(), new Object()));
   }
}
```

**Affected versions:**
```
Picked up _JAVA_OPTIONS:   -Dawt.useSystemAAFontSettings=gasp
java version "1.8.0_151"
Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)
```
**Steps to reproduce:**

1. Compile above program
2. Run it using Oracle HotSpot java

**Current result:**

Sometimes null, sometimes ClassCastException.

**Expected result:**

null

## ConcurrentSkipListMap.putIfAbsent presents nondeterministic results

The following sample program presents different results when executed multiple times.

```
public class A {
   public static void main(String args[]) {
      ConcurrentSkipListMap concurrentSkipListMap = new ConcurrentSkipListMap();
      System.out.println(concurrentSkipListMap.putIfAbsent(new Object(), new Object()));
   }
}
```

**Affected versions:**
```
Picked up _JAVA_OPTIONS:   -Dawt.useSystemAAFontSettings=gasp
java version "1.8.0_151"
Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using Oracle HotSpot java

**Current result:**

Sometimes null, sometimes ClassCastException.

**Expected result:**

null

## ConcurrentSkipListSet.add presents nondeterministic results

The following sample program presents different results when executed multiple times.

```
public class A {
   public static void main(String args[]) {
      ConcurrentSkipListSet concurrentSkipListSet = new ConcurrentSkipListSet();
      System.out.println(concurrentSkipListSet.add(new Object()));
   }
}
```

**Affected versions:**
```
Picked up _JAVA_OPTIONS:   -Dawt.useSystemAAFontSettings=gasp
java version "1.8.0_151"
Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)
```
**Steps to reproduce:**

1. Compile above program
2. Run it using Oracle HotSpot java

**Current result:**

Sometimes true, sometimes ClassCastException.

**Expected result:**

true

## Arrays.copyOfRange method throws OutOfMemoryError

Arrays.copyOfRange specification defines that a NullPointerException should be throw if original parameter is null (https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#copyOfRange-byte:A-int-int-). However, it throws OutOfMemoryError when executing the following program:

```
import java.util.Arrays;
public class A {
   public static void main(String args[]) {
      Arrays.copyOfRange((byte[])null, 0, 2147483647);
   }
}
```

**Affected versions:**
```
Picked up _JAVA_OPTIONS:   -Dawt.useSystemAAFontSettings=gasp
java version "1.8.0_151"
Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)
```
**Steps to reproduce:**

	1. Compile above program
	2. Run it using Oracle HotSpot java 

**Current result:**
```
Exception in thread "main" java.lang.OutOfMemoryError: Requested array size exceeds VM limit
	at java.util.Arrays.copyOfRange(Arrays.java:3520)
	at A.main(A.java:4)
```
**Expected result:**

NullPointerException.

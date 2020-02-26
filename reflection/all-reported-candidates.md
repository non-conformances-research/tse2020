# Specification

## Class.getResource

### Class.getResource method presents wrong result

Accordingly to Javadoc (https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getResource-java.lang.String-) Class.getResource method returns "A URL object or null if no resource with this name is found". However, a different result is returned when executing the following program:

```
package p;
public enum A {
    X, Y
}

package p;
public class B {
    public static void main(String[] args) {
        System.out.println(A[].class.getResource(""));
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

file:.../target/classes/

**Expected result:**

Underdetermined.

## Class.getResourceAsStream

### Class.getResourceAsStream method presents wrong result

Accordingly to Javadoc (https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getResourceAsStream-java.lang.String-) Class.getResourceAsStream method returns "A InputStream object or null if no resource with this name is found". However, a different result is returned when executing the following program:

```
package p;
public enum A {
    X, Y
}

package p;
public class B {
    public static void main(String[] args) {
        System.out.println(A[].class.getResourceAsStream(""));
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

java.io.ByteArrayInputStream@...

**Expected result:**

Underdetermined.

## Class.getAnnotations

### Class.getAnnotations presents annotations parameters in random order

Class.getAnnotations sometimes presents annotations parameters in different order when executing the following program:

```
@Retention(RUNTIME)
public @interface A {
    String name() default "A";
    String namespace() default "B" ;
}

@A()
public class B {
    public static void main(String[] args) {
	for(Annotation a : B.class.getAnnotations()) {
	    System.out.println(a);
	}
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

Sometimes A(name=A, namespace=B), sometimes A(namespace=B, name=A).

**Expected result:**

Underdetermined.

## Class.getDeclaredAnnotations

### Class.getDeclaredAnnotations presents annotations parameters in random order

Class.getDeclaredAnnotations sometimes presents annotations parameters in different order when executing the following program:

```
@Retention(RUNTIME)
public @interface A {
    String name() default "A";
    String namespace() default "B" ;
}

@A()
public class B {
    public static void main(String[] args) {
	for(Annotation a : B.class.getDeclaredAnnotations()) {
	    System.out.println(a);
	}
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

Sometimes A(name=A, namespace=B), sometimes A(namespace=B, name=A).

**Expected result:**

Underdetermined.

## Executable.getAnnotations

### Executable.getAnnotations presents annotations parameters in random order

Executable.getAnnotations sometimes presents annotations parameters in different order when executing the following program:

```
@Retention(RUNTIME)
public @interface A {
    String name() default "A";
    String namespace() default "B" ;
}

public class B {
    @A()
    public void m(int x) {
    }
    public static void main(String[] args) {
	for (Method m : B.class.getMethods()) {
	    for (Parameter p : m.getParameters()) {
                for (Annotation a : p.getDeclaringExecutable().getAnnotations()) {
                    System.out.println(a);
                }
            }
        }
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

Sometimes A(name=A, namespace=B), sometimes A(namespace=B, name=A).

**Expected result:**

Underdetermined.

## Executable.getDeclaredAnnotations

### Executable.getDeclaredAnnotations presents annotations parameters in random order

Executable.getDeclaredAnnotations sometimes presents annotations parameters in different order when executing the following program:

```
@Retention(RUNTIME)
public @interface A {
    String name() default "A";
    String namespace() default "B" ;
}

public class B {
    @A()
    public void m() {
    }
    public static void main(String[] args) {
	for (Method m : B.class.getMethods()) {
	    for (Parameter p : m.getParameters()) {
                for (Annotation a : p.getDeclaringExecutable().getDeclaredAnnotations()) {
                    System.out.println(a);
                }
            }
        }
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

Sometimes A(name=A, namespace=B), sometimes A(namespace=B, name=A).

**Expected result:**

Underdetermined.

## Executable.getParameterAnnotations

### Executable.getParameterAnnotations presents annotations parameters in random order

Executable.getParameterAnnotations sometimes presents annotations parameters in different order when executing the following program:

```
@Retention(RUNTIME)
public @interface A {
    String name() default "A";
    String namespace() default "B" ;
}

public class B {
    public void m(@A() String x) {
    }
    public static void main(String[] args) {
	for (Method m : B.class.getMethods()) {
	    for (Parameter p : m.getParameters()) {
                for (Annotation[] annotations : p.getDeclaringExecutable().getParameterAnnotations()) {
                    for (Annotation a : annotations) {
                        System.out.println(a);
                    }
                }
            }
        }
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

Sometimes A(name=A, namespace=B), sometimes A(namespace=B, name=A).

**Expected result:**

Underdetermined.

## Field.getAnnotations

### Field.getAnnotations presents annotations parameters in random order

Field.getAnnotations sometimes presents annotations parameters in different order when executing the following program:

```
@Retention(RUNTIME)
public @interface A {
    String name() default "A";
    String namespace() default "B" ;
}

public class B {
    @A()
    public String f;
    public static void main(String[] args) {
	for (Field f : B.class.getFields()) {
            for (Annotation a : f.getAnnotations()) {
                System.out.println(a);
            }
        }
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

Sometimes A(name=A, namespace=B), sometimes A(namespace=B, name=A).

**Expected result:**

Underdetermined.

## Field.getDeclaredAnnotations

### Field.getDeclaredAnnotations presents annotations parameters in random order

Field.getDeclaredAnnotations sometimes presents annotations parameters in different order when executing the following program:

```
@Retention(RUNTIME)
public @interface A {
    String name() default "A";
    String namespace() default "B" ;
}

public class B {
    @A()
    public String f;
    public static void main(String[] args) {
	for (Field f : B.class.getFields()) {
            for (Annotation a : f.getDeclaredAnnotations()) {
                System.out.println(a);
            }
        }
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

Sometimes A(name=A, namespace=B), sometimes A(namespace=B, name=A).

**Expected result:**

Underdetermined.

## Method.getAnnotations

### Method.getAnnotations presents annotations parameters in random order

Method.getAnnotations sometimes presents annotations parameters in different order when executing the following program:

```
@Retention(RUNTIME)
public @interface A {
    String name() default "A";
    String namespace() default "B" ;
}

public class B {
    @A()
    public void m() {
    }
    public static void main(String[] args) {
	for (Method m : B.class.getMethods()) {
            for(Annotation a : m.getAnnotations()) {
                System.out.println(a);
            }
        }
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

Sometimes A(name=A, namespace=B), sometimes A(namespace=B, name=A).

**Expected result:**

Underdetermined.

## Method.getDeclaredAnnotations

### Method.getDeclaredAnnotations presents annotations parameters in random order

Method.getDeclaredAnnotations sometimes presents annotations parameters in different order when executing the following program:

```
@Retention(RUNTIME)
public @interface A {
    String name() default "A";
    String namespace() default "B" ;
}

public class B {
    @A()
    public void m() {
    }
    public static void main(String[] args) {
	for (Method m : B.class.getMethods()) {
            for(Annotation a : m.getDeclaredAnnotations()) {
                System.out.println(a);
            }
        }
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

Sometimes A(name=A, namespace=B), sometimes A(namespace=B, name=A).

**Expected result:**

Underdetermined.

## Method.getParameterAnnotations

### Method.getParameterAnnotations presents annotations parameters in random order

Method.getParameterAnnotations sometimes presents annotations parameters in different order when executing the following program:

```
@Retention(RUNTIME)
public @interface A {
    String name() default "A";
    String namespace() default "B" ;
}

public class B {
    public void m(@A() String x) {
    }
    public static void main(String[] args) {
	for (Method m : B.class.getMethods()) {
	    for (Annotation[] annotations : m.getParameterAnnotations()) {
                for (Annotation a : annotations) {
                    System.out.println(a);
                }
            }
        }
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

Sometimes A(name=A, namespace=B), sometimes A(namespace=B, name=A).

**Expected result:**

Underdetermined.

# Eclipse OpenJ9

## Class.getConstructor

### Class.getConstructor throws exception after invoking Class.newInstance

Accordingly to Javadoc of Class.getConstructor (https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getConstructor-java.lang.Class...-), "...a Constructor object that reflects the specified public constructor of the class represented by this Class object" must be returned. But using the following program the constructor of class B can not be retrieved:

```
public class A {
   static {
      String x = null;
      x.getClass();
   }
}

public class B extends A {
}

public class C {
   public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
      try {
         B.class.newInstance();
      } catch (ExceptionInInitializerError e) {
      } catch (Exception e) {
      } finally {
	System.out.println(B.class.getConstructor(null));
      }
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.NoClassDefFoundError: B (initialization failure)
	at java.lang.J9VMInternals.initializationAlreadyFailed(J9VMInternals.java:96)
	at java.lang.J9VMInternals.prepareClassImpl(Native Method)
	at java.lang.J9VMInternals.prepare(J9VMInternals.java:300)
	at java.lang.Class.getConstructor(Class.java:554)
	at C.main(C.java:8)
Caused by: java.lang.NullPointerException
	at A.<clinit>(A.java:4)
	at java.lang.J9VMInternals.newInstanceImpl(Native Method)
	at java.lang.Class.newInstance(Class.java:1773)
	at C.main(C.java:2)
```

**Expected result:**

public B()

## Class.getConstructors

### Class.getConstructors throws exception after invoking Class.newInstance

Accordingly to Javadoc of Class.getConstructors (https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getConstructors--), "...an array containing Constructor objects reflecting all the public constructors of the class represented by this Class object" must be returned. But using the following program constructors of class B can not be retrieved:

```
public class A {
   static {
      String x = null;
      x.getClass();
   }
}

public class B extends A {
}

public class C {
   public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
      try {
         B.class.newInstance();
      } catch (ExceptionInInitializerError e) {
      } catch (Exception e) {
      } finally {
         for (Constructor c : B.class.getConstructors()) {
            System.out.println(c);
         }
      }
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.NoClassDefFoundError: B (initialization failure)
	at java.lang.J9VMInternals.initializationAlreadyFailed(J9VMInternals.java:96)
	at java.lang.J9VMInternals.prepareClassImpl(Native Method)
	at java.lang.J9VMInternals.prepare(J9VMInternals.java:300)
	at java.lang.Class.getConstructors(Class.java:607)
	at C.main(C.java:8)
Caused by: java.lang.NullPointerException
	at A.<clinit>(A.java:4)
	at java.lang.J9VMInternals.newInstanceImpl(Native Method)
	at java.lang.Class.newInstance(Class.java:1773)
	at C.main(C.java:2)
```

**Expected result:**

public B()

## Class.getDeclaredConstructor

### Class.getDeclaredConstructor throws exception after invoking Class.newInstance

Accordingly to Javadoc of Class.getDeclaredConstructor (https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredConstructor-java.lang.Class...-), "...a Constructor object that reflects the specified constructor of the class or interface represented by this Class object" must be returned. But using the following program declared constructor of class B can not be retrieved:

```
public class A {
   static {
      String x = null;
      x.getClass();
   }
}

public class B extends A {
}

public class C {
   public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
      try {
         B.class.newInstance();
      } catch (ExceptionInInitializerError e) {
      } catch (Exception e) {
      } finally {
	System.out.println(B.class.getDeclaredConstructor(null));
      }
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.NoClassDefFoundError: B (initialization failure)
	at java.lang.J9VMInternals.initializationAlreadyFailed(J9VMInternals.java:96)
	at java.lang.J9VMInternals.prepareClassImpl(Native Method)
	at java.lang.J9VMInternals.prepare(J9VMInternals.java:300)
	at java.lang.Class.getDeclaredConstructor(Class.java:684)
	at C.main(C.java:8)
Caused by: java.lang.NullPointerException
	at A.<clinit>(A.java:4)
	at java.lang.J9VMInternals.newInstanceImpl(Native Method)
	at java.lang.Class.newInstance(Class.java:1773)
	at C.main(C.java:2)
```

**Expected result:**

public B()

## Class.getDeclaredConstructors

### Class.getDeclaredConstructors throws exception after invoking Class.newInstance

Accordingly to Javadoc of Class.getDeclaredConstructors (https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredConstructors--), "...an array of Constructor objects reflecting all the constructors declared by the class represented by this Class object" must be returned. But using the following program declared constructors of class B can not be retrieved:

```
public class A {
   static {
      String x = null;
      x.getClass();
   }
}

public class B extends A {
}

public class C {
   public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
      try {
         B.class.newInstance();
      } catch (ExceptionInInitializerError e) {
      } catch (Exception e) {
      } finally {
         for (Constructor c : B.class.getDeclaredConstructors()) {
            System.out.println(c);
         }
      }
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.NoClassDefFoundError: B (initialization failure)
	at java.lang.J9VMInternals.initializationAlreadyFailed(J9VMInternals.java:96)
	at java.lang.J9VMInternals.prepareClassImpl(Native Method)
	at java.lang.J9VMInternals.prepare(J9VMInternals.java:300)
	at java.lang.Class.getDeclaredConstructors(Class.java:738)
	at C.main(C.java:8)
Caused by: java.lang.NullPointerException
	at A.<clinit>(A.java:4)
	at java.lang.J9VMInternals.newInstanceImpl(Native Method)
	at java.lang.Class.newInstance(Class.java:1773)
	at C.main(C.java:2)
```

**Expected result:**

public B()

## Class.getDeclaredField

### Class.getDeclaredField throws exception after invoking Class.newInstance

Accordingly to Javadoc of Class.getDeclaredField (https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredField-java.lang.String-), "...a Field object that reflects the specified declared field of the class or interface represented by this Class object" must be returned. But using the following program declared field of class B can not be retrieved:

```
public class A {
   static {
      String x = null;
      x.getClass();
   }
}

public class B extends A {
   public int x;
}

public class C {
   public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
      try {
         B.class.newInstance();
      } catch (ExceptionInInitializerError e) {
      } catch (Exception e) {
      } finally {
	System.out.println(B.class.getDeclaredField("x"));
      }
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.NoClassDefFoundError: B (initialization failure)
	at java.lang.J9VMInternals.initializationAlreadyFailed(J9VMInternals.java:96)
	at java.lang.J9VMInternals.prepareClassImpl(Native Method)
	at java.lang.J9VMInternals.prepare(J9VMInternals.java:300)
	at java.lang.Class.getDeclaredField(Class.java:782)
	at C.main(C.java:8)
Caused by: java.lang.NullPointerException
	at A.<clinit>(A.java:4)
	at java.lang.J9VMInternals.newInstanceImpl(Native Method)
	at java.lang.Class.newInstance(Class.java:1773)
	at C.main(C.java:2)
```

**Expected result:**

public int B.x

## Class.getDeclaredFields

### Class.getDeclaredFields throws exception after invoking Class.newInstance

Accordingly to Javadoc of Class.getDeclaredFields, "...an array of Field objects reflecting all the fields declared by the class or interface represented by this Class object" must be returned. But using the following program declared fields of class B can not be retrieved:

```
public class A {
   static {
      String x = null;
      x.getClass();
   }
}

public class B extends A {
   public int x;
}

public class C {
   public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
      try {
         B.class.newInstance();
      } catch (ExceptionInInitializerError e) {
      } catch (Exception e) {
      } finally {
         for (Field f : B.class.getDeclaredFields()) {
            System.out.println(f);
         }
      }
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
2. Run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.NoClassDefFoundError: samples.newInstance.B (initialization failure)
	at java.lang.J9VMInternals.initializationAlreadyFailed(J9VMInternals.java:96)
	at java.lang.J9VMInternals.prepareClassImpl(Native Method)
	at java.lang.J9VMInternals.prepare(J9VMInternals.java:300)
	at java.lang.Class.getDeclaredFields(Class.java:830)
	at samples.newInstance.C.main(C.java:27)
Caused by: java.lang.NullPointerException
	at samples.newInstance.A.<clinit>(A.java:7)
	at java.lang.J9VMInternals.newInstanceImpl(Native Method)
	at java.lang.Class.newInstance(Class.java:1773)
	at samples.newInstance.C.main(C.java:11)
```

**Expected result:**

public int x

## Class.getDeclaredMethod

### Class.getDeclaredMethod throws exception after invoking Class.newInstance

Accordingly to Javadoc of Class.getDeclaredMethod (https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredMethod-java.lang.String-java.lang.Class...-), "...a Method object that reflects the specified declared method of the class or interface represented by this Class object" must be returned. But using the following program declared method of class B can not be retrieved:

```
public class A {
   static {
      String x = null;
      x.getClass();
   }
}

public class B extends A {
   public void m() {}
}

public class C {
   public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
      try {
         B.class.newInstance();
      } catch (ExceptionInInitializerError e) {
      } catch (Exception e) {
      } finally {
	System.out.println(B.class.getDeclaredMethod("m"));
      }
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.NoClassDefFoundError: B (initialization failure)
	at java.lang.J9VMInternals.initializationAlreadyFailed(J9VMInternals.java:96)
	at java.lang.J9VMInternals.prepareClassImpl(Native Method)
	at java.lang.J9VMInternals.prepare(J9VMInternals.java:300)
	at java.lang.Class.getMethodHelper(Class.java:1184)
	at java.lang.Class.getDeclaredMethod(Class.java:895)
	at C.main(C.java:8)
Caused by: java.lang.NullPointerException
	at A.<clinit>(A.java:4)
	at java.lang.J9VMInternals.newInstanceImpl(Native Method)
	at java.lang.Class.newInstance(Class.java:1773)
	at C.main(C.java:2)
```

**Expected result:**

public void B.m()

## Class.getDeclaredMethods

### Class.getDeclaredMethods throws exception after invoking Class.newInstance

Accordingly to Javadoc of Class.getDeclaredMethods (https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getDeclaredMethods--), "...an array containing Method objects reflecting all the declared methods of the class or interface represented by this Class object" must be returned. But using the following program declared methods of class B can not be retrieved:

```
public class A {
   static {
      String x = null;
      x.getClass();
   }
}

public class B extends A {
   public void m() {}
}

public class C {
   public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
      try {
         B.class.newInstance();
      } catch (ExceptionInInitializerError e) {
      } catch (Exception e) {
      } finally {
         for (Method m : B.class.getDeclaredMethods()) {
            System.out.println(m);
         }
      }
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.NoClassDefFoundError: B (initialization failure)
	at java.lang.J9VMInternals.initializationAlreadyFailed(J9VMInternals.java:96)
	at java.lang.J9VMInternals.prepareClassImpl(Native Method)
	at java.lang.J9VMInternals.prepare(J9VMInternals.java:300)
	at java.lang.Class.getDeclaredMethods(Class.java:940)
	at C.main(C.java:8)
Caused by: java.lang.NullPointerException
	at A.<clinit>(A.java:4)
	at java.lang.J9VMInternals.newInstanceImpl(Native Method)
	at java.lang.Class.newInstance(Class.java:1773)
	at C.main(C.java:2)
```

**Expected result:**

public void B.m()

## Class.getField

### Class.getField throws exception after invoking Class.newInstance

Accordingly to Javadoc of Class.getField (https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getField-java.lang.String-), "...a Field object that reflects the specified public member field of the class or interface represented by this Class object" must be returned. But using the following program field of class B can not be retrieved:

```
public class A {
   static {
      String x = null;
      x.getClass();
   }
}

public class B extends A {
   public int x;
}

public class C {
   public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
      try {
         B.class.newInstance();
      } catch (ExceptionInInitializerError e) {
      } catch (Exception e) {
      } finally {
	System.out.println(B.class.getField("x"));
      }
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.NoClassDefFoundError: B (initialization failure)
	at java.lang.J9VMInternals.initializationAlreadyFailed(J9VMInternals.java:96)
	at java.lang.J9VMInternals.prepareClassImpl(Native Method)
	at java.lang.J9VMInternals.prepare(J9VMInternals.java:300)
	at java.lang.Class.getField(Class.java:1028)
	at C.main(C.java:8)
Caused by: java.lang.NullPointerException
	at A.<clinit>(A.java:4)
	at java.lang.J9VMInternals.newInstanceImpl(Native Method)
	at java.lang.Class.newInstance(Class.java:1773)
	at C.main(C.java:2)
```

**Expected result:**

public int B.x

## Class.getFields

### Class.getFields throws exception after invoking Class.newInstance

Accordingly to Javadoc of Class.getFields (https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getFields--), "...an array containing Field objects reflecting all the accessible public fields of the class or interface represented by this Class object" must be returned. But using the following program fields of class B can not be retrieved:

```
public class A {
   static {
      String x = null;
      x.getClass();
   }
}

public class B extends A {
   public int x;
}

public class C {
   public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
      try {
         B.class.newInstance();
      } catch (ExceptionInInitializerError e) {
      } catch (Exception e) {
      } finally {
         for (Field f : B.class.getFields()) {
            System.out.println(f);
         }
      }
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.NoClassDefFoundError: B (initialization failure)
	at java.lang.J9VMInternals.initializationAlreadyFailed(J9VMInternals.java:96)
	at java.lang.J9VMInternals.prepareClassImpl(Native Method)
	at java.lang.J9VMInternals.prepare(J9VMInternals.java:300)
	at java.lang.Class.getFields(Class.java:1075)
	at C.main(C.java:8)
Caused by: java.lang.NullPointerException
	at A.<clinit>(A.java:4)
	at java.lang.J9VMInternals.newInstanceImpl(Native Method)
	at java.lang.Class.newInstance(Class.java:1773)
	at C.main(C.java:2)
```

**Expected result:**

public int B.x

## Class.getMethod

### Class.getMethod throws exception after invoking Class.newInstance

Accordingly to Javadoc of Class.getMethod (https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getMethod-java.lang.String-java.lang.Class...-), "...a Method object that reflects the specified public member method of the class or interface represented by this Class object" must be returned. But using the following program method of class B can not be retrieved:

```
public class A {
   static {
      String x = null;
      x.getClass();
   }
}

public class B extends A {
   public void m() {}
}

public class C {
   public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
      try {
         B.class.newInstance();
      } catch (ExceptionInInitializerError e) {
      } catch (Exception e) {
      } finally {
	System.out.println(B.class.getMethod("m"));
      }
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.NoClassDefFoundError: B (initialization failure)
	at java.lang.J9VMInternals.initializationAlreadyFailed(J9VMInternals.java:96)
	at java.lang.J9VMInternals.prepareClassImpl(Native Method)
	at java.lang.J9VMInternals.prepare(J9VMInternals.java:300)
	at java.lang.Class.getMethodHelper(Class.java:1184)
	at java.lang.Class.getMethod(Class.java:1130)
	at C.main(C.java:8)
Caused by: java.lang.NullPointerException
	at A.<clinit>(A.java:4)
	at java.lang.J9VMInternals.newInstanceImpl(Native Method)
	at java.lang.Class.newInstance(Class.java:1773)
	at C.main(C.java:2)
```

**Expected result:**

public void B.m()

## Class.getMethods

### Class.getMethods throws exception after invoking Class.newInstance

Accordingly to Javadoc of Class.getMethods (https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html#getMethods--), "...an array containing Method objects reflecting all the public methods of the class or interface represented by this Class object" must be returned. But using the following program methods of class B can not be retrieved:

```
public class A {
   static {
      String x = null;
      x.getClass();
   }
}

public class B extends A {
   public void m() {}
}

public class C {
   public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
      try {
         B.class.newInstance();
      } catch (ExceptionInInitializerError e) {
      } catch (Exception e) {
      } finally {
         for (Method m : B.class.getMethods()) {
            System.out.println(m);
         }
      }
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. Compile above program
2. Run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.NoClassDefFoundError: B (initialization failure)
	at java.lang.J9VMInternals.initializationAlreadyFailed(J9VMInternals.java:96)
	at java.lang.J9VMInternals.prepareClassImpl(Native Method)
	at java.lang.J9VMInternals.prepare(J9VMInternals.java:300)
	at java.lang.Class.getMethods(Class.java:1313)
	at C.main(C.java:8)
Caused by: java.lang.NullPointerException
	at A.<clinit>(A.java:4)
	at java.lang.J9VMInternals.newInstanceImpl(Native Method)
	at java.lang.Class.newInstance(Class.java:1773)
	at C.main(C.java:2)
```

**Expected result:**

public void B.m()

## Constructor.getAnnotatedParameterTypes

### Constructor.getAnnotatedParameterTypes throws exception when trying to get annotated parameter type in an enum

An exception is thrown when executing the following program:

```
public enum A {}

public class B {
   public static void main(String[] args) {
      for (Constructor c : A.class.getDeclaredConstructors()) {
         for (AnnotatedType at : c.getAnnotatedParameterTypes()) {
            System.out.println(at.getType());
         }
      }
   }
}
```


**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. compile above program
2. run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Array index out of range: 0
	at java.lang.reflect.Executable.getAllGenericParameterTypes(Executable.java:318)
	at java.lang.reflect.Executable.getAnnotatedParameterTypes(Executable.java:693)
	at B.main(B.java:4)
```

**expected result:**

class java.lang.String

int

### Constructor.getAnnotatedParameterTypes returns wrong type to annotated parameter related to a static inner class


**Sample program:**

```
import java.lang.reflect.AnnotatedType;

public class A<T> {

    A(B<T> b) {
    }

    static class B<T> {

    }

    public static void main(String[] args) {
        AnnotatedType[] annotatedParameterTypes = A.class.getDeclaredConstructors()[0].getAnnotatedParameterTypes();
        System.out.println(annotatedParameterTypes[0].getType());
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

A.A$B&lt;T>

**Expected result:**

A$B&lt;T>

### Constructor.getAnnotatedParameterTypes returns wrong type to annotated parameter related to an inner class

**Sample program:**

```
import java.lang.reflect.AnnotatedType;

public class A<T> {

    A(B<T> b) {
    }

    class B<T> {
    }

    public static void main(String[] args) {
        AnnotatedType[] annotatedParameterTypes = A.class.getDeclaredConstructors()[0].getAnnotatedParameterTypes();
        System.out.println(annotatedParameterTypes[0].getType());
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

A&lt;T>.B&lt;T>

**Expected result:**

A&lt;T>$B&lt;T>

## Executable.getAnnotatedParameterTypes

### Executable.getAnnotatedParameterTypes throws exception when trying to get annotated parameter type in an inner class

An exception is thrown when executing the following program:

```
public class A {
    class Inner {
        Inner (Iterator<String> delegate) {
        }
    }
    public static void main(String[] args) {
        Class<?>[] classes = A.class.getDeclaredClasses();
	System.out.println(classes[0].getDeclaredConstructors()[0].getParameters()[0].getDeclaringExecutable().getAnnotatedParameterTypes()[0].getType());
    }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. compile above program
2. run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Array index out of range: 1
	at java.lang.reflect.Executable.getAllGenericParameterTypes(Executable.java:318)
	at java.lang.reflect.Executable.getAnnotatedParameterTypes(Executable.java:693)
	at A.main(A.java:8)
```

**expected result:**

class A

### Executable.getAnnotatedParameterTypes returns wrong type to annotated parameter related to a static inner class

**Sample program:**

```
import java.lang.reflect.AnnotatedType;

public class A<T> {

    A(B<T> b) {
    }

    static class B<T> {

    }

    public static void main(String[] args) {
        AnnotatedType[] annotatedParameterTypes = A.class.getDeclaredConstructors()[0].getParameters()[0].getDeclaringExecutable().getAnnotatedParameterTypes();
        System.out.println(annotatedParameterTypes[0].getType());
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

A.A$B&lt;T>

**Expected result:**

A$B&lt;T>

### Executable.getAnnotatedParameterTypes returns wrong type to annotated parameter related to an inner class

**Sample program:**

```
import java.lang.reflect.AnnotatedType;

public class A<T> {

    A(B<T> b) {
    }

    class B<T> {
    }

    public static void main(String[] args) {
        AnnotatedType[] annotatedParameterTypes = A.class.getDeclaredConstructors()[0].getParameters()[0].getDeclaringExecutable().getAnnotatedParameterTypes();
        System.out.println(annotatedParameterTypes[0].getType());
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

A&lt;T>.B&lt;T>

**Expected result:**

A&lt;T>$B&lt;T>

## Method.invoke

### Method.invoke presents different exception messages when invoking multiple times a method with wrong number of arguments

The following program presents a null exception message after invoking 17 times a method with wrong number of arguments.

```
import java.lang.reflect.Method;
public class A {
    public static void main(String[] args) throws NoSuchMethodException {
        A a = new A();
        for (int i = 0; i < 17; i++) {
            Method method = A.class.getMethod("wait", long.class);
            try {
                method.invoke(a, 0, 1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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
2. Run it using openj9 java

**Current result:**

wrong number of arguments (x16) <br>
null

**Expected result:**

Always print "wrong number of arguments" message.

### Method.invoke presents different exception messages when invoking multiple times a method with wrong argument type

The following program presents a null exception message after invoking 17 times a method with wrong argument type.

```
import java.lang.reflect.Method;
public class A {
    public static void main(String[] args) throws NoSuchMethodException {
        A a = new A();
        for (int i = 0; i < 17; i++) {
            Method method = A.class.getMethod("wait", long.class);
            try {
                method.invoke(a, "0");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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
2. Run it using openj9 java

**Current result:**

argument type mismatch (x16)<br>
null

**Expected result:**

Always print "argument type mismatch" message.

### Method.invoke presents different exception messages when invoking multiple times a method with wrong instance type

The following program presents a ClassCastException message after invoking 17 times a method with wrong instance type.

```
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
public class A {
    public static void main(String[] args) throws NoSuchMethodException {
        Method method = AnnotatedType.class.getDeclaredMethod("getType");
        for (int i = 0; i < 17; i++) {
            try {
                method.invoke("");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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
2. Run it using openj9 java

**Current result:**

object is not an instance of declaring class (x16) <br>
java.lang.ClassCastException@...

**Expected result:**

Always print "object is not an instance of declaring class" message.

## Parameter.getAnnotatedType

### Parameter.getAnnotatedType throws exception when trying to get parameter type in an inner class

An exception is thrown when executing the following program:

```
public class A {
    class Inner {
        Inner (Iterator<String> delegate) {
        }
    }
    public static void main(String[] args) {
        Class<?>[] classes = A.class.getDeclaredClasses();
        System.out.println(classes[0].getDeclaredConstructors()[0].getParameters()[0].getAnnotatedType().getType());
    }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. compile above program
2. run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Array index out of range: 1
	at java.lang.reflect.Executable.getAllGenericParameterTypes(Executable.java:318)
	at java.lang.reflect.Executable.getAnnotatedParameterTypes(Executable.java:693)
	at java.lang.reflect.Parameter.getAnnotatedType(Parameter.java:237)
	at A.main(A.java:8)
```

**expected result:**

class A

## Parameter.getParameterizedType

### Parameter.getParameterizedType throws exception when trying to get parameter parameterized type in an enum

An exception is thrown when executing the following program:

```
public enum A {}

public class B {
   public static void main(String[] args) {
      for (Constructor c : A.class.getDeclaredConstructors()) {
         for (Parameter p : c.getParameters()) {
            System.out.println(p.getParameterizedType());
         }
      }
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

	1. compile above program
	2. run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Array index out of range: 0
	at java.lang.reflect.Executable.getAllGenericParameterTypes(Executable.java:318)
	at java.lang.reflect.Parameter.getParameterizedType(Parameter.java:201)
	at B.main(B.java:5)
```

**Expected result:**

class java.lang.String

int

## Parameter.toString

### Parameter.toString throws exception when trying to get parameter description in an enum

An exception is thrown when executing the following program:

```
public enum A {}

public class B {
   public static void main(String[] args) {
      for (Constructor c : A.class.getDeclaredConstructors()) {
         for (Parameter p : c.getParameters()) {
            System.out.println(p.toString());
         }
      }
   }
}
```

**Affected versions:**

```
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-b12)
Eclipse OpenJ9 VM (build openj9-0.8.0, JRE 1.8.0 Linux amd64-64 Compressed References 20180315_120 (JIT enabled, AOT enabled)
OpenJ9 - e24e8aa
OMR - 3e8296b4
JCL - ee1e77df1d based on jdk8u162-b12)
```

**Steps to reproduce:**

1. compile above program
2. run it using openj9 java

**Current result:**

```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Array index out of range: 0
	at java.lang.reflect.Executable.getAllGenericParameterTypes(Executable.java:318)
	at java.lang.reflect.Parameter.getParameterizedType(Parameter.java:201)
	at java.lang.reflect.Parameter.toString(Parameter.java:125)
	at B.main(B.java:5)
```

**expected result:**

java.lang.String arg0

int arg1


# Oracle

## Constructor.getAnnotatedParameterTypes

### Constructor.getAnnotatedParameterTypes returns wrong type to static inner class

A wrong value is returned to an annotated type parameter related to a inner class.

**Sample program:**

```
import java.lang.reflect.AnnotatedType;

public class A<T> {

    A(B<T> b) {
    }

    static class B<T> {

    }

    public static void main(String[] args) {
        AnnotatedType[] annotatedParameterTypes = A.class.getDeclaredConstructors()[0].getAnnotatedParameterTypes();
        System.out.println(annotatedParameterTypes[0].getType());
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
2. Run it using Oracle java

**Current result:**

A.A$B&lt;T>

**Expected result:**

A$B&lt;T>

### Constructor.getAnnotatedParameterTypes returns wrong type related to inner class

A wrong value is returned to an annotated type parameter related to a static inner class.

**Sample program:**

```
import java.lang.reflect.AnnotatedType;

public class A<T> {

    A(B<T> b) {
    }

    class B<T> {
    }

    public static void main(String[] args) {
        AnnotatedType[] annotatedParameterTypes = A.class.getDeclaredConstructors()[0].getAnnotatedParameterTypes();
        System.out.println(annotatedParameterTypes[0].getType());
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
2. Run it using Oracle java

**Current result:**

A&lt;T>.B&lt;T>

**Expected result:**

A&lt;T>$B&lt;T>

## Executable.getAnnotatedParameterTypes

### Executable.getAnnotatedParameterTypes returns wrong type to static inner class

A wrong value is returned to an annotated type parameter related to a inner class.

**Sample program:**

```
import java.lang.reflect.AnnotatedType;

public class A<T> {

    A(B<T> b) {
    }

    static class B<T> {

    }

    public static void main(String[] args) {
        AnnotatedType[] annotatedParameterTypes = A.class.getDeclaredConstructors()[0].getParameters()[0].getDeclaringExecutable().getAnnotatedParameterTypes();
        System.out.println(annotatedParameterTypes[0].getType());
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
2. Run it using Oracle java

**Current result:**

A.A$B&lt;T>

**Expected result:**

A$B&lt;T>

### Executable.getAnnotatedParameterTypes returns wrong type related to inner class

A wrong value is returned to an annotated type parameter related to a static inner class.

**Sample program:**

```
import java.lang.reflect.AnnotatedType;

public class A<T> {

    A(B<T> b) {
    }

    class B<T> {
    }

    public static void main(String[] args) {
        AnnotatedType[] annotatedParameterTypes = A.class.getDeclaredConstructors()[0].getParameters()[0].getDeclaringExecutable().getAnnotatedParameterTypes();
        System.out.println(annotatedParameterTypes[0].getType());
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
2. Run it using Oracle java

**Current result:**

A&lt;T>.B&lt;T>

**Expected result:**

A&lt;T>$B&lt;T>

## Method.invoke

### Issue on Method.invoke when using wrong number of arguments

The following program presents a null exception message after invoking 17 times a method with wrong number of arguments.

```
import java.lang.reflect.Method;
public class A {
    public static void main(String[] args) throws NoSuchMethodException {
        A a = new A();
        for (int i = 0; i < 17; i++) {
            Method method = A.class.getMethod("wait", long.class);
            try {
                method.invoke(a, 0, 1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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

wrong number of arguments (x16)<br>
null

**Expected result:**

Always print "wrong number of arguments" message.

### Issue on Method.invoke when using wrong argument type

The following program presents a null exception message after invoking 17 times a method with wrong argument type.

```
import java.lang.reflect.Method;
public class A {
    public static void main(String[] args) throws NoSuchMethodException {
        A a = new A();
        for (int i = 0; i < 17; i++) {
            Method method = A.class.getMethod("wait", long.class);
            try {
                method.invoke(a, "0");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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

argument type mismatch (x16)<br>
null

**Expected result:**

Always print "argument type mismatch" message.

### Issue on Method.invoke when using wrong instance type

The following program presents a ClassCastException message after invoking 17 times a method with wrong instance type.

```
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
public class A {
    public static void main(String[] args) throws NoSuchMethodException {
        Method method = AnnotatedType.class.getDeclaredMethod("getType");
        for (int i = 0; i < 17; i++) {
            try {
                method.invoke("");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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

object is not an instance of declaring class (x16) <br>
java.lang.ClassCastException@...

**Expected result:**

Always print "object is not an instance of declaring class" message.

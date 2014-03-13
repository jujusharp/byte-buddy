package com.blogspot.mydailyjava.bytebuddy.instrumentation.method.bytecode.stack;

import com.blogspot.mydailyjava.bytebuddy.instrumentation.Instrumentation;
import com.blogspot.mydailyjava.bytebuddy.instrumentation.type.TypeDescription;
import com.blogspot.mydailyjava.bytebuddy.utility.MockitoRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.mockito.Mockito.*;

public class TypeCreationTest {

    private static final String FOO = "foo";

    @Rule
    public TestRule mockitoRule = new MockitoRule(this);

    @Mock
    private TypeDescription typeDescription;
    @Mock
    private MethodVisitor methodVisitor;
    @Mock
    private Instrumentation.Context instrumentationContext;

    @Before
    public void setUp() throws Exception {
        when(typeDescription.getInternalName()).thenReturn(FOO);
    }

    @Test
    public void testTypeCreation() throws Exception {
        TypeCreation.forType(typeDescription).apply(methodVisitor, instrumentationContext);
        verify(methodVisitor).visitTypeInsn(Opcodes.NEW, FOO);
        verifyNoMoreInteractions(methodVisitor);
        verifyZeroInteractions(instrumentationContext);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTypeCreationArray() throws Exception {
        when(typeDescription.isArray()).thenReturn(true);
        TypeCreation.forType(typeDescription);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTypeCreationPrimitive() throws Exception {
        when(typeDescription.isPrimitive()).thenReturn(true);
        TypeCreation.forType(typeDescription);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTypeCreationAbstract() throws Exception {
        when(typeDescription.isAbstract()).thenReturn(true);
        TypeCreation.forType(typeDescription);
    }
}

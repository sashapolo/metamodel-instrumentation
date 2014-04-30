/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.types;

/**
 *
 * @author alexander
 */
public class PrimitiveType extends Type {
    public final static PrimitiveType VOID = new VoidType();
    public final static PrimitiveType BOOL = new BoolType();
    public final static PrimitiveType BYTE = new ByteType();
    public final static PrimitiveType CHAR = new CharType();
    public final static PrimitiveType SHORT = new ShortType();
    public final static PrimitiveType INT = new IntType();
    public final static PrimitiveType LONG = new LongType();
    public final static PrimitiveType FLOAT = new FloatType();
    public final static PrimitiveType DOUBLE = new DoubleType();
}

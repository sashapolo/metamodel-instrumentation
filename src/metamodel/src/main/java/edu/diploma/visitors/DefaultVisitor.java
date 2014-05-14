/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.visitors;

import edu.diploma.metamodel.Annotation;
import edu.diploma.metamodel.Import;
import edu.diploma.metamodel.TranslationUnit;
import edu.diploma.metamodel.declarations.AnnotationDecl;
import edu.diploma.metamodel.declarations.ClassDecl;
import edu.diploma.metamodel.declarations.DeclBody;
import edu.diploma.metamodel.declarations.Declaration;
import edu.diploma.metamodel.declarations.EnumDecl;
import edu.diploma.metamodel.declarations.EnumValue;
import edu.diploma.metamodel.declarations.FunctionDecl;
import edu.diploma.metamodel.declarations.ParameterDecl;
import edu.diploma.metamodel.declarations.TemplateDecl;
import edu.diploma.metamodel.declarations.VariableDecl;
import edu.diploma.metamodel.expressions.ArrayAccessExpression;
import edu.diploma.metamodel.expressions.ArrayConstructorCall;
import edu.diploma.metamodel.expressions.ArrayInitializer;
import edu.diploma.metamodel.expressions.AssignmentExpression;
import edu.diploma.metamodel.expressions.AttributeAccess;
import edu.diploma.metamodel.expressions.BinaryExpression;
import edu.diploma.metamodel.expressions.CastExpression;
import edu.diploma.metamodel.expressions.ConstructorCall;
import edu.diploma.metamodel.expressions.Expression;
import edu.diploma.metamodel.expressions.ExpressionList;
import edu.diploma.metamodel.expressions.FunctionCall;
import edu.diploma.metamodel.expressions.StaticAttributeAccess;
import edu.diploma.metamodel.expressions.TernaryExpression;
import edu.diploma.metamodel.expressions.TypeExpression;
import edu.diploma.metamodel.expressions.UnaryExpression;
import edu.diploma.metamodel.expressions.VariableReference;
import edu.diploma.metamodel.literals.BooleanLiteral;
import edu.diploma.metamodel.literals.CharLiteral;
import edu.diploma.metamodel.literals.FloatLiteral;
import edu.diploma.metamodel.literals.IntegerLiteral;
import edu.diploma.metamodel.literals.Literal;
import edu.diploma.metamodel.literals.SpecialLiteral;
import edu.diploma.metamodel.literals.StringLiteral;
import edu.diploma.metamodel.statements.BreakStatement;
import edu.diploma.metamodel.statements.CatchStatement;
import edu.diploma.metamodel.statements.ContinueStatement;
import edu.diploma.metamodel.statements.DoWhileStatement;
import edu.diploma.metamodel.statements.EmptyStatement;
import edu.diploma.metamodel.statements.ForEachStatement;
import edu.diploma.metamodel.statements.ForStatement;
import edu.diploma.metamodel.statements.IfStatement;
import edu.diploma.metamodel.statements.LabelStatement;
import edu.diploma.metamodel.statements.ReturnStatement;
import edu.diploma.metamodel.statements.Statement;
import edu.diploma.metamodel.statements.StatementBlock;
import edu.diploma.metamodel.statements.StatementList;
import edu.diploma.metamodel.statements.SwitchStatement;
import edu.diploma.metamodel.statements.ThrowStatement;
import edu.diploma.metamodel.statements.TryStatement;
import edu.diploma.metamodel.statements.TryWithResourcesStatement;
import edu.diploma.metamodel.statements.VariableDeclStatement;
import edu.diploma.metamodel.statements.WhileStatement;
import edu.diploma.metamodel.types.ArrayType;
import edu.diploma.metamodel.types.ClassType;
import edu.diploma.metamodel.types.PrimitiveType;
import edu.diploma.metamodel.types.TemplateParameter;
import edu.diploma.metamodel.types.Type;

/**
 *
 * @author alexander
 */
public abstract class DefaultVisitor extends VisitorAdapter {
    public abstract void visit(final Annotation entity);
    public abstract void visit(final Import entity);
    public abstract void visit(final TranslationUnit entity);
    
    // Declarations
    public abstract void visit(final AnnotationDecl entity);
    public abstract void visit(final ClassDecl entity);
    public abstract void visit(final DeclBody entity);
    public abstract void visit(final Declaration entity);
    public abstract void visit(final EnumDecl entity);
    public abstract void visit(final EnumValue entity);
    public abstract void visit(final FunctionDecl entity);
    public abstract void visit(final ParameterDecl entity);
    public abstract void visit(final TemplateDecl entity);
    public abstract void visit(final VariableDecl entity);
    
    // Expressions
    public abstract void visit(final ArrayAccessExpression entity);
    public abstract void visit(final ArrayConstructorCall entity);
    public abstract void visit(final ArrayInitializer entity);
    public abstract void visit(final AssignmentExpression entity);
    public abstract void visit(final AttributeAccess entity);
    public abstract void visit(final BinaryExpression entity);
    public abstract void visit(final CastExpression entity);
    public abstract void visit(final ConstructorCall entity);
    public abstract void visit(final Expression entity);
    public abstract void visit(final ExpressionList entity);
    public abstract void visit(final FunctionCall entity);
    public abstract void visit(final StaticAttributeAccess entity);
    public abstract void visit(final TernaryExpression entity);
    public abstract void visit(final TypeExpression entity);
    public abstract void visit(final UnaryExpression entity);
    public abstract void visit(final VariableReference entity);
    
    // Literals
    public abstract void visit(final BooleanLiteral entity);
    public abstract void visit(final CharLiteral entity);
    public abstract void visit(final FloatLiteral entity);
    public abstract void visit(final IntegerLiteral entity);
    public abstract void visit(final Literal entity);
    public abstract void visit(final SpecialLiteral entity);
    public abstract void visit(final StringLiteral entity);
    
    // Statements
    public abstract void visit(final BreakStatement entity);
    public abstract void visit(final CatchStatement entity);
    public abstract void visit(final ContinueStatement entity);
    public abstract void visit(final DoWhileStatement entity);
    public abstract void visit(final EmptyStatement entity);
    public abstract void visit(final ForEachStatement entity);
    public abstract void visit(final ForStatement entity);
    public abstract void visit(final IfStatement entity);
    public abstract void visit(final LabelStatement entity);
    public abstract void visit(final ReturnStatement entity);
    public abstract void visit(final Statement entity);
    public abstract void visit(final StatementBlock entity);
    public abstract void visit(final StatementList entity);
    public abstract void visit(final SwitchStatement entity);
    public abstract void visit(final ThrowStatement entity);
    public abstract void visit(final TryStatement entity);
    public abstract void visit(final TryWithResourcesStatement entity);
    public abstract void visit(final VariableDeclStatement entity);
    public abstract void visit(final WhileStatement entity);
    
    // Types
    public abstract void visit(final ArrayType entity);
    public abstract void visit(final ClassType entity);
    public abstract void visit(final PrimitiveType entity);
    public abstract void visit(final TemplateParameter entity);
    public abstract void visit(final Type entity);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.visitors;

import edu.diploma.metamodel.Annotation;
import edu.diploma.metamodel.Import;
import edu.diploma.metamodel.Metamodel;
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
public class TestPrintVisitor extends DefaultVisitor {

    @Override
    public void visit(Annotation entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(Import entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(TranslationUnit entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(AnnotationDecl entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(ClassDecl entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(DeclBody entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(Declaration entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(EnumDecl entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(EnumValue entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(FunctionDecl entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(ParameterDecl entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(TemplateDecl entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(VariableDecl entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(ArrayAccessExpression entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(ArrayConstructorCall entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(ArrayInitializer entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(AssignmentExpression entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(AttributeAccess entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(BinaryExpression entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(CastExpression entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(ConstructorCall entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(Expression entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(ExpressionList entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(FunctionCall entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(StaticAttributeAccess entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(TernaryExpression entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(TypeExpression entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(UnaryExpression entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(VariableReference entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(BooleanLiteral entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(CharLiteral entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(FloatLiteral entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(IntegerLiteral entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(Literal entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(SpecialLiteral entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(StringLiteral entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(BreakStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(CatchStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(ContinueStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(DoWhileStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(EmptyStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(ForEachStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(ForStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(IfStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(LabelStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(ReturnStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(Statement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(StatementBlock entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(StatementList entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(SwitchStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(ThrowStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(TryStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(TryWithResourcesStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(VariableDeclStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(WhileStatement entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(ArrayType entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(ClassType entity) {
        System.out.println(entity.getClass().getName() + ": " + entity.getName());
    }

    @Override
    public void visit(PrimitiveType entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(TemplateParameter entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(Type entity) {
        System.out.println(entity.getClass().getName());
    }

    @Override
    public void visit(Metamodel entity) {
        System.out.println(entity.getClass().getName());
    }

}

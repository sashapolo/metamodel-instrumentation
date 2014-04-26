/*
 [The "BSD licence"]
 Copyright (c) 2013 Terence Parr, Sam Harwell
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

grammar JavaGrammar;

@header {  
    import java.util.Map;
    import java.util.LinkedList;
    import java.util.HashMap;
    import java.util.Collections;
         
    import main.java.edu.diploma.metamodel.*;
    import main.java.edu.diploma.metamodel.types.*;
    import main.java.edu.diploma.metamodel.expressions.*;
    import main.java.edu.diploma.metamodel.typedelem.*;
    import edu.diploma.parser.util.TypeFactory;
}

// starting point for parsing a java file
compilationUnit
returns [TranslationUnit result]
locals [PackageDecl packageDecl = null,
        List<Import> imports = new LinkedList<>(),
        List<Type> types = new LinkedList<>()]
    :   ( packageDeclaration { $packageDecl = $packageDeclaration.result; })? 
        ( importDeclaration { $imports.add($importDeclaration.result); })* 
        ( typeDeclaration 
          { 
              if ($typeDeclaration.result != null) {
                  $types.add($typeDeclaration.result); 
              }
          }
        )* 
        EOF
    ;

packageDeclaration
returns [PackageDecl result]
locals [List <Annotation> annotations = new LinkedList<>()]
    :   ( annotation { $annotations.add($annotation.result); } )* 
        'package' qualifiedName ';'
        { 
            $result = new PackageDecl($qualifiedName.result);
            $result.addAnnotations($annotations);
        }
    ;

importDeclaration
returns [Import result]
locals [boolean isStatic = false, boolean isWildcard = false]
    :   'import' ('static' {$isStatic = true;})? qualifiedName ('.' '*' {$isWildcard = true;})? ';'
        { $result = new Import($qualifiedName.result, $isStatic, $isWildcard); }
    ;

typeDeclaration
returns [Declaration result]
locals [List<Annotation> annos = new LinkedList<>(),
        List<String> mods = new LinkedList<>()]
    :   ( classOrInterfaceModifier 
          { 
              if ($classOrInterfaceModifier.mod != null) {
                  mods.add($classOrInterfaceModifier.mod);
              }
              if ($classOrInterfaceModifier.anno != null) {
                  annos.add($classOrInterfaceModifier.anno);
              }
          } 
        )* 
        ( classDeclaration          { $result = $classDeclaration.result; }
        | enumDeclaration           { $result = $classDeclaration.result; }
        | interfaceDeclaration      { $result = $interfaceDeclaration.result; }
        | annotationTypeDeclaration { $result = $annotationTypeDeclaration.result; }
        )
        {
            $result.addAnnotations($annos);
            $result.addModifiers($mods);
        }
    |   ';' { $result = null; }
    ;

modifier
returns [String mod, Annotation anno]
@init {
    $mod = null;
    $anno = null;
}    
    :   classOrInterfaceModifier
        {
            $mod = $classOrInterfaceModifier.mod;
            $anno = $classOrInterfaceModifier.anno;
        }   
    |   (   t='native'       { $mod = $t.text; }
        |   t='synchronized' { $mod = $t.text; }
        |   t='transient'    { $mod = $t.text; }
        |   t='volatile'     { $mod = $t.text; }
        )
    ;

classOrInterfaceModifier
returns [String mod, Annotation anno]
@init {
    $mod = null;
    $anno = null;
}
    :   annotation { $anno = $annotation.result; } // class or interface
    |   (   t='public'     { $mod = $t.text; } // class or interface 
        |   t='protected'  { $mod = $t.text; } // class or interface
        |   t='private'    { $mod = $t.text; } // class or interface
        |   t='static'     { $mod = $t.text; } // class or interface
        |   t='abstract'   { $mod = $t.text; } // class or interface
        |   t='final'      { $mod = $t.text; } // class only -- does not apply to interfaces
        |   t='strictfp'   { $mod = $t.text; } // class or interface
        )
    ;

variableModifier
returns [String mod, Annotation anno]
@init {
    $mod = null;
    $anno = null;
}
    :   'final' { $mod = "final"; }
    |   annotation { $anno = $annotation.result; }
    ;

classDeclaration
returns [ClassDecl result]
locals [List<TemplateDecl> templates = Collections.emptyList(),
        List<Type> inherits = new LinkedList<>()]
    :   'class' Identifier (typeParameters { $templates = $typeParameters.result; })?
        ('extends' type { $inherits.add($type.result); })?
        ('implements' typeList { $inherits.addAll($typeList.result); })?
        classBody
        {
            $result = new ClassDecl($Identifier.text, $templates, $inherits, $classBody.result);
        }
    ;

typeParameters
returns [List<TemplateDecl> result]
@init {
    $result = new LinkedList<>();
}       
    :   '<' typeParameter { $result.add($typeParameter.result); } 
        (',' typeParameter { $result.add($typeParameter.result); })* '>'
    ;

typeParameter
returns [TemplateDecl result]
    :   Identifier ('extends' typeBound)?
        {
            $result = new TemplateDecl($Identifier.text, 
                $typeBound.ctx == null ? Collections.emptyList() : $typeBound.result); 
        }
    ;

typeBound
returns [List<Type> result]
@init {
    $result = new LinkedList<>();
}    
    :   type { $result.add($type.result); } ('&' type { $result.add($type.result); })*
    ;

enumDeclaration
returns [EnumDecl result]
locals [List<Type> inherits = Collections.emptyList(),
        List<EnumValue> enums = Collections.emptyList(),
        DeclBody body = null]
    :   ENUM Identifier ('implements' typeList { $inherits = $typeList.result; })?
        '{' (enumConstants { $enums = $enumConstants.result; })? ','? 
        (enumBodyDeclarations { $body = $enumBodyDeclarations.result; })? '}'
        {
            $result = new EnumDecl($Identifier.text, $inherits, $enums, $body);
        } 
    ;

enumConstants
returns [List<EnumValue> result]
@init {
    $result = new LinkedList<>();
}
    :   enumConstant { $result.add($enumConstant.result); } 
        (',' enumConstant { $result.add($enumConstant.result); })*
    ;

enumConstant
returns [EnumValue result]
locals [List<Annotation> annos = new LinkedList<>(),
        List<Expression> values = Collections.emptyList(),
        DeclBody body = DeclBody.EMPTY]
    :   ( annotation { $annos.add($annotation.result); })* Identifier 
        ( arguments { $values = $arguments.result; })? 
        ( classBody { $body = $classBody.result; })?
        {
            $result = new EnumValue($Identifier.text, $values, $body);
            $result.addAnnotations($annos);
        }
    ;

enumBodyDeclarations
returns [DeclBody result]
@init {
    $result = new DeclBody();       
}
    :   ';' ( classBodyDeclaration 
        {
            if ($classBodyDeclaration.result != null) {
                $result.addDeclaration($classBodyDeclaration.result)
            }
        })*
    ;

interfaceDeclaration
returns [ClassDecl result]
locals [List<TemplateDecl> templates = Collections.emptyList(),
        List<Type> inherits = new LinkedList<>()]
    :   'interface' Identifier (typeParameters { $templates = $typeParameters.result; })? 
        ('extends' typeList { $inherits.addAll($typeList.result); })? interfaceBody
        {
            $result = new ClassDecl($Identifier.text, $templates, $inherits, $interfaceBody.result);
        }
    ;

typeList
returns [List<Type> result]
@init {
    $result = new LinkedList<>();
}
    :   type { $result.add($type.result); } (',' type { $result.add($type.result); })*
    ;

classBody
returns [DeclBody result]
@init {
    $result = new DeclBody();       
}
    :   '{' ( classBodyDeclaration 
              { 
                  if ($classBodyDeclaration != null) {
                      $result.add($classBodyDeclaration.result);
                  }
              }
            )* '}'
    ;

interfaceBody
returns [DeclBody result]
@init {
    $result = new DeclBody();
}
    :   '{' ( interfaceBodyDeclaration 
              { 
                  if ($interfaceBodyDeclaration != null) {
                      $result.add($interfaceBodyDeclaration.result);
                  }
              }
            )*  '}'
    ;

classBodyDeclaration
returns [Declaration result]
locals [List<String> mods = new LinkedList<>(), List<Annotation> annos = new LinkedList<>()]
    :   ';' { $result = null; }
    |   'static'? block 
        {
            $result = $block.result;
            $result.addModifier("static");
        }
    |   ( modifier
          {
              if ($modifier.mod != null) {
                  $mods.add($modifier.mod);
              }
              if ($modifier.anno != null) {
                  $annos.add($modifier.anno);
              }
          }
        )* memberDeclaration
        {
            $result = $memberDeclaration.result;
            $result.addModifiers($mods);
            $result.addAnnotations($annos);
        }
    ;

memberDeclaration
returns [Declaration result]
    :   methodDeclaration
    |   genericMethodDeclaration
    |   fieldDeclaration
    |   constructorDeclaration
    |   genericConstructorDeclaration
    |   interfaceDeclaration { $result = $interfaceDeclaration.result; }
    |   annotationTypeDeclaration { $result = $annotationTypeDeclaration.result; }
    |   classDeclaration { $result = $classDeclaration.result; }
    |   enumDeclaration { $result = $enumDeclaration.result; }
    ;

/* We use rule this even for void methods which cannot have [] after parameters.
   This simplifies grammar and we can consider void to be a type, which
   renders the [] matching as a context-sensitive issue or a semantic check
   for invalid return type after parsing.
 */
methodDeclaration
//returns [FunctionDecl result]
locals [Type retType]  
    :   (type {$retType = $type.result;} | 'void' {$retType = PrimitiveType.VOID;}) 
        Identifier formalParameters ('[' ']' { $retType = new ArrayType($retType); })*
        ('throws' qualifiedNameList)?
        (   methodBody
        |   ';'
        )
    ;

genericMethodDeclaration
    :   typeParameters methodDeclaration
    ;

constructorDeclaration
    :   Identifier formalParameters ('throws' qualifiedNameList)?
        constructorBody
    ;

genericConstructorDeclaration
    :   typeParameters constructorDeclaration
    ;

fieldDeclaration
    :   type variableDeclarators ';'
    ;

interfaceBodyDeclaration
returns [Declaration result]
locals [List<String> mods = new LinkedList<>(), List<Annotation> annos = new LinkedList<>()]
    :   ( modifier
          {
              if ($modifier.mod != null) {
                  $mods.add($modifier.mod);
              }
              if ($modifier.anno != null) {
                  $annos.add($modifier.anno);
              }
          }
        )* interfaceMemberDeclaration
        {
            $result = $interfaceMemberDeclaration.result;
            $result.addModifiers($mods);
            $result.addAnnotations($annos);
        }
    |   ';'
    ;

interfaceMemberDeclaration
    :   constDeclaration
    |   interfaceMethodDeclaration
    |   genericInterfaceMethodDeclaration
    |   interfaceDeclaration
    |   annotationTypeDeclaration
    |   classDeclaration
    |   enumDeclaration
    ;

constDeclaration
    :   type constantDeclarator (',' constantDeclarator)* ';'
    ;

constantDeclarator
    :   Identifier ('[' ']')* '=' variableInitializer
    ;

// see matching of [] comment in methodDeclaratorRest
interfaceMethodDeclaration
    :   (type|'void') Identifier formalParameters ('[' ']')*
        ('throws' qualifiedNameList)?
        ';'
    ;

genericInterfaceMethodDeclaration
    :   typeParameters interfaceMethodDeclaration
    ;

variableDeclarators
returns [List<UntypedVariable> result]
@init {
    $result = new LinkedList<>();
}
    :   variableDeclarator { $result.add($variableDeclarator.result); }
        (',' variableDeclarator { $result.add($variableDeclarator.result); })*
    ;

variableDeclarator
returns [UntypedVariable result]
    :   variableDeclaratorId { $result = $variableDeclaratorId.result; }
        ('=' variableInitializer { $result.setValue($variableInitializer.result); })?
    ;

variableDeclaratorId
returns [UntypedVariable result]
    :   Identifier { $result = new UntypedVariable($Identifier.text); }
        ('[' ']' { $result = new UntypedVariable($result); })*
    ;

variableInitializer
returns [Expression result]
    :   arrayInitializer { $result = $arrayInitializer.result; }
    |   expression { $result = $expression.result; }
    ;

arrayInitializer
returns [ArrayInitializer result]
locals [List<Expression> values = new LinkedList<>()]
    :   '{' (variableInitializer { $values.add($variableInitializer.result); } 
        (',' variableInitializer { $values.add($variableInitializer.result); })* (',')? )? '}'
        {
            $result = new ArrayInitializer($values);
        }
    ;

enumConstantName
returns [String result]
    :   Identifier { $result = $Identifier.text; }
    ;

type
returns [Type result]
    :   classOrInterfaceType { $result = $classOrInterfaceType.result; }
        ('[' ']' { $result = new ArrayType($result); })*
    |   primitiveType { $result = $primitiveType.result; } 
        ('[' ']' { $result = new ArrayType($result); })*
    ;

classOrInterfaceType
returns [ClassType result]
locals [List<TemplateParameter> templates = Collections.emptyList()]
    :   Identifier ( typeArguments { $templates = $typeArguments.result; })?
        { $result = new ClassType($Identifier.text, $templates); }
        ( '.' Identifier ( typeArguments { $templates = $typeArguments.result; })?
        {
            $result = new ClassType($Identifier.text, $templates, $result);
        })*
    ;

primitiveType
returns [PrimitiveType result]
    :   'boolean' { $result = PrimitiveType.BOOL; }
    |   'char'    { $result = PrimitiveType.CHAR; }
    |   'byte'    { $result = PrimitiveType.BYTE; }
    |   'short'   { $result = PrimitiveType.SHORT; }
    |   'int'     { $result = PrimitiveType.INT; }
    |   'long'    { $result = PrimitiveType.LONG; }
    |   'float'   { $result = PrimitiveType.FLOAT; }
    |   'double'  { $result = PrimitiveType.DOUBLE; }
    ;

typeArguments
returns [List<TemplateParameter> result]
@init {
    $result = new LinkedList<>();
}
    :   '<' typeArgument { $result.add($typeArgument.result); } 
        (',' typeArgument { $result.add($typeArgument.result); })* '>'
    ;

typeArgument
returns [TemplateParameter result]
    :   type { $result = new TemplateParameter($type.result); }
    |   '?' { $result = new TemplateParameter(null); }
    |   '?' 'extends' type 
        { $result = TemplateParameter.createWildcardTemplate($type.result, false); }
    |   '?' 'super' type
        { $result = TemplateParameter.createWildcardTemplate($type.result, true); }
    ;

qualifiedNameList
returns [List<String> result]
@init {
    $result = new LinkedList<>();
}
    :   qualifiedName { $result.add($qualifiedName.result); } 
        (',' qualifiedName { $result.add($qualifiedName.result); })*
    ;

formalParameters
returns [List<VariableDecl> result]
@init {
    $result = Collections.emptyList();
}
    :   '(' ( formalParameterList { $result = $formalParameterList.result; })? ')'
    ;

formalParameterList
returns [List<VariableDecl> result]
@init {
    $result = new LinkedList<>();
}
    :   formalParameter { $result.add($formalParameter.result) } 
        (',' formalParameter { $result.add($formalParameter.result) })* 
        (',' lastFormalParameter { $result.add($lastFormalParameter.result) })?
    |   lastFormalParameter { $result.add($lastFormalParameter.result) }
    ;

formalParameter
returns [VariableDecl result]
    :   variableModifier* type variableDeclaratorId
        {
            $result = $variableDeclaratorId.createVariableDecl($type.result);
            if ($variableModifier.ctx != null) {
                if ($variableModifier.mod != null) {
                    $result.addModifier($variableModifier.mod)                                    
                }
                if ($variableModifier.anno != null) {
                    $result.addAnnotation($variableModifier.anno)                                    
                }   
            }                                     
        } 
    ;

lastFormalParameter
    :   variableModifier* type '...' variableDeclaratorId
        {
            $result = $variableDeclaratorId.createVariableDecl($type.result, true);
            if ($variableModifier.ctx != null) {
                if ($variableModifier.mod != null) {
                    $result.addModifier($variableModifier.mod)                                    
                }
                if ($variableModifier.anno != null) {
                    $result.addAnnotation($variableModifier.anno)                                    
                }   
            }                                     
        } 
    ;

methodBody
    :   block
    ;

constructorBody
    :   block
    ;

qualifiedName
returns [String result]
locals [StringBuilder res]
    :   Identifier
        { $res = new StringBuilder($Identifier.text); }
        ( '.' Identifier { $res.append($Identifier.text); } )*
        { $result = $res.toString(); }
    ;

literal
    :   IntegerLiteral
    |   FloatingPointLiteral
    |   CharacterLiteral
    |   StringLiteral
    |   BooleanLiteral
    |   'null'
    ;

// ANNOTATIONS

annotation 
returns [Annotation result]
    :   '@' annotationName ( '(' ( elementValuePairs | elementValue )? ')' )?
        {
            if ($elementValuePairs.ctx != null) {
                $result = new Annotation($annotationName.result, $elementValuePairs.result);                                     
            } else if ($elementValue.ctx != null) {
                $result = new Annotation($annotationName.result, $elementValue.result);                                    
            } else {
                $result = new Annotation($annotationName.result); 
            }
        }
    ;

annotationName
returns [String result]
    : qualifiedName { $result = $qualifiedName.result; };

elementValuePairs
returns [Map<String, Entity> result]
@init {
    $result = new HashMap<>();
}
    :   elementValuePair { $result.put($elementValuePair.name, $elementValuePair.value); }
        (',' elementValuePair { $result.put($elementValuePair.name, $elementValuePair.value); })*
    ;

elementValuePair
returns [String name, Entity value]
    :   Identifier '=' elementValue
        {
            $name = $Identifier.text;
            $value = $elementValue.result;
        }
    ;

elementValue
returns [Entity result]
    :   expression { $result = $expression.result; }
    |   annotation { $result = $annotation.result; }
    |   elementValueArrayInitializer { $result = $elementValueArrayInitializer.result; }
    ;

elementValueArrayInitializer
returns [ArrayInitializer result]
locals [List<Entity> values = new LinkedList<>()]
@after {
    $result = new ArrayInitializer($values);
}
    :   '{' (elementValue {$values.add($elementValue.result);} 
            (',' elementValue {$values.add($elementValue.result);})*)? (',')? '}'
    ;

annotationTypeDeclaration
returns [AnnotationDecl result]
    :   '@' 'interface' Identifier annotationTypeBody
        {
            $result = new AnnotationDecl($Identifier.text, $annotationTypeBody.result);
        } 
    ;

annotationTypeBody
returns [DeclBody result]
@init {
    $result = new DeclBody();
}
    :   '{' 
        (
            ( annotationTypeElementDeclaration 
              {
                  if ($annotationTypeElementDeclaration.result != null) {
                      $result.addDeclaration($annotationTypeElementDeclaration.result);
                  }
              }
            )*
        |   ( annotationConstantsDeclaration
              {
                  $result.addDeclarations($annotationConstantsDeclaration.result);
              }
            )+
        )
        '}'
    ;

annotationTypeElementDeclaration
returns [Declaration result]
locals [List<String> mods = new LinkedList<>(), List<Annotation> annos = new LinkedList<>()]
    :   ( modifier
          {
              if ($modifier.mod != null) {
                  $mods.add($modifier.mod);
              }
              if ($modifier.anno != null) {
                  $annos.add($modifier.anno);
              }
          }
        )* annotationTypeElementRest
        {
            $result = $memberDeclaration.result;
            $result.addModifiers($mods);
            $result.addAnnotations($annos);
        }
    |   ';' { $result = null; } // this is not allowed by the grammar, but apparently allowed by the actual compiler
    ;

annotationTypeElementRest
returns [Declaration result]
    :   annotationMethod ';' { $result = $annotationMethod.result; }
    |   classDeclaration ';'? { $result = $classDeclaration.result; }
    |   interfaceDeclaration ';'? { $result = $interfaceDeclaration.result; }
    |   enumDeclaration ';'? { $result = $enumDeclaration.result; }
    |   annotationTypeDeclaration ';'? { $result = $annotationTypeDeclaration.result; }
    ;


annotationConstantsDeclaration
returns [List<Declaration> result]
locals [List<String> mods = new LinkedList<>(), List<Annotation> annos = new LinkedList<>()]
    :   ( modifier
          {
              if ($modifier.mod != null) {
                  $mods.add($modifier.mod);
              }
              if ($modifier.anno != null) {
                  $annos.add($modifier.anno);
              }
          }
        )* annotationConstants
        {
            $result = $annotationConstants.result;
            for (final Declaration decl : $result) {
                decl.addModifiers($mods);
                decl.addAnnotations($annos);
            }   
        }
    ;

annotationConstants
returns [List<Declaration> result]
@init {
    $result = new LinkedList<>();       
}
    :   type annotationConstantRest
        {
            for (final UntypedVariable var : $annotationConstantRest.result) {
                $result.add(var.createVariableDecl($type.result));                                                              
            }   
        }
    ;

annotationMethod
returns [MethodDeclaration result]
    :   type annotationMethodRest
        {
            $result = new MethodDeclaration($type.result, $annotationMethodRest.result);
        } 
    ;

annotationMethodRest
returns [String result]
    :   Identifier '(' ')' defaultValue?
        { $result = $Identifier.text; }
    ;

annotationConstantRest
returns [List<UntypedVariable> result]
    :   variableDeclarators { $result = $variableDeclarators.result; }
    ;

defaultValue
returns [Entity result]
    :   'default' elementValue { $result = $elementValue.result; }
    ;

// STATEMENTS / BLOCKS

block
    :   '{' blockStatement* '}'
    ;

blockStatement
    :   localVariableDeclarationStatement
    |   statement
    |   typeDeclaration
    ;

localVariableDeclarationStatement
    :    localVariableDeclaration ';'
    ;

localVariableDeclaration
    :   variableModifier* type variableDeclarators
    ;

statement
    :   block
    |   ASSERT expression (':' expression)? ';'
    |   'if' parExpression statement ('else' statement)?
    |   'for' '(' forControl ')' statement
    |   'while' parExpression statement
    |   'do' statement 'while' parExpression ';'
    |   'try' block (catchClause+ finallyBlock? | finallyBlock)
    |   'try' resourceSpecification block catchClause* finallyBlock?
    |   'switch' parExpression '{' switchBlockStatementGroup* switchLabel* '}'
    |   'synchronized' parExpression block
    |   'return' expression? ';'
    |   'throw' expression ';'
    |   'break' Identifier? ';'
    |   'continue' Identifier? ';'
    |   ';'
    |   statementExpression ';'
    |   Identifier ':' statement
    ;

catchClause
    :   'catch' '(' variableModifier* catchType Identifier ')' block
    ;

catchType
    :   qualifiedName ('|' qualifiedName)*
    ;

finallyBlock
    :   'finally' block
    ;

resourceSpecification
    :   '(' resources ';'? ')'
    ;

resources
    :   resource (';' resource)*
    ;

resource
    :   variableModifier* classOrInterfaceType variableDeclaratorId '=' expression
    ;

/** Matches cases then statements, both of which are mandatory.
 *  To handle empty cases at the end, we add switchLabel* to statement.
 */
switchBlockStatementGroup
    :   switchLabel+ blockStatement+
    ;

switchLabel
    :   'case' constantExpression ':'
    |   'case' enumConstantName ':'
    |   'default' ':'
    ;

forControl
    :   enhancedForControl
    |   forInit? ';' expression? ';' forUpdate?
    ;

forInit
    :   localVariableDeclaration
    |   expressionList
    ;

enhancedForControl
    :   variableModifier* type Identifier ':' expression
    ;

forUpdate
    :   expressionList
    ;

// EXPRESSIONS

parExpression
    :   '(' expression ')'
    ;

expressionList
    :   expression (',' expression)*
    ;

statementExpression
    :   expression
    ;

constantExpression
    :   expression
    ;

expression
//returns [Expression result]
    :   primary
    |   expression '.' Identifier
    |   expression '.' 'this'
    |   expression '.' 'new' nonWildcardTypeArguments? innerCreator
    |   expression '.' 'super' superSuffix
    |   expression '.' explicitGenericInvocation
    |   expression '[' expression ']'
    |   expression '(' expressionList? ')'
    |   'new' creator
    |   '(' type ')' expression
    |   expression ('++' | '--')
    |   ('+'|'-'|'++'|'--') expression
    |   ('~'|'!') expression
    |   expression ('*'|'/'|'%') expression
    |   expression ('+'|'-') expression
    |   expression ('<' '<' | '>' '>' '>' | '>' '>') expression
    |   expression ('<=' | '>=' | '>' | '<') expression
    |   expression 'instanceof' type
    |   expression ('==' | '!=') expression
    |   expression '&' expression
    |   expression '^' expression
    |   expression '|' expression
    |   expression '&&' expression
    |   expression '||' expression
    |   expression '?' expression ':' expression
    |   <assoc=right> expression
        (   '='
        |   '+='
        |   '-='
        |   '*='
        |   '/='
        |   '&='
        |   '|='
        |   '^='
        |   '>>='
        |   '>>>='
        |   '<<='
        |   '%='
        )
        expression
    ;

primary
returns [Expression result]
    :   '(' expression ')'
        { $result = $expression.result; }
    |   ( 'this' { $result = new VariableReference("this"); }
        | 'super' { $result = new VariableReference("super"); })
    |   literal { $result = TypeFactory.parseLiteral($literal.text); }
    |   Identifier { $result = new VariableReference($Identifier.text); }    
    |   type '.' 'class'
        {
            Type type = TypeFactory.createJavaClassType($type.result);
            Variable var = new Variable($type.result, "class"); 
            $result = new StaticAttributeAccess($type.result, var);
        }
    |   'void' '.' 'class'
        {
            Type type = TypeFactory.createJavaClassType(PrimitiveType.VOID);
            Variable var = new Variable(type, "class"); 
            $result = new StaticAttributeAccess(PrimitiveType.VOID, var);
        }
    |   nonWildcardTypeArguments (explicitGenericInvocationSuffix | 'this' arguments)
    ;

creator
    :   nonWildcardTypeArguments createdName classCreatorRest
    |   createdName (arrayCreatorRest | classCreatorRest)
    ;

createdName
    :   Identifier typeArgumentsOrDiamond? ('.' Identifier typeArgumentsOrDiamond?)*
    |   primitiveType
    ;

innerCreator
    :   Identifier nonWildcardTypeArgumentsOrDiamond? classCreatorRest
    ;

arrayCreatorRest
    :   '['
        (   ']' ('[' ']')* arrayInitializer
        |   expression ']' ('[' expression ']')* ('[' ']')*
        )
    ;

classCreatorRest
    :   arguments classBody?
    ;

explicitGenericInvocation
    :   nonWildcardTypeArguments explicitGenericInvocationSuffix
    ;

nonWildcardTypeArguments
    :   '<' typeList '>'
    ;

typeArgumentsOrDiamond
    :   '<' '>'
    |   typeArguments
    ;

nonWildcardTypeArgumentsOrDiamond
    :   '<' '>'
    |   nonWildcardTypeArguments
    ;

superSuffix
    :   arguments
    |   '.' Identifier arguments?
    ;

explicitGenericInvocationSuffix
    :   'super' superSuffix
    |   Identifier arguments
    ;

arguments
    :   '(' expressionList? ')'
    ;

// LEXER

// §3.9 Keywords

ABSTRACT      : 'abstract';
ASSERT        : 'assert';
BOOLEAN       : 'boolean';
BREAK         : 'break';
BYTE          : 'byte';
CASE          : 'case';
CATCH         : 'catch';
CHAR          : 'char';
CLASS         : 'class';
CONST         : 'const';
CONTINUE      : 'continue';
DEFAULT       : 'default';
DO            : 'do';
DOUBLE        : 'double';
ELSE          : 'else';
ENUM          : 'enum';
EXTENDS       : 'extends';
FINAL         : 'final';
FINALLY       : 'finally';
FLOAT         : 'float';
FOR           : 'for';
IF            : 'if';
GOTO          : 'goto';
IMPLEMENTS    : 'implements';
IMPORT        : 'import';
INSTANCEOF    : 'instanceof';
INT           : 'int';
INTERFACE     : 'interface';
LONG          : 'long';
NATIVE        : 'native';
NEW           : 'new';
PACKAGE       : 'package';
PRIVATE       : 'private';
PROTECTED     : 'protected';
PUBLIC        : 'public';
RETURN        : 'return';
SHORT         : 'short';
STATIC        : 'static';
STRICTFP      : 'strictfp';
SUPER         : 'super';
SWITCH        : 'switch';
SYNCHRONIZED  : 'synchronized';
THIS          : 'this';
THROW         : 'throw';
THROWS        : 'throws';
TRANSIENT     : 'transient';
TRY           : 'try';
VOID          : 'void';
VOLATILE      : 'volatile';
WHILE         : 'while';

// §3.10.1 Integer Literals

IntegerLiteral
    :   DecimalIntegerLiteral
    |   HexIntegerLiteral
    |   OctalIntegerLiteral
    |   BinaryIntegerLiteral
    ;

fragment
DecimalIntegerLiteral
    :   DecimalNumeral IntegerTypeSuffix?
    ;

fragment
HexIntegerLiteral
    :   HexNumeral IntegerTypeSuffix?
    ;

fragment
OctalIntegerLiteral
    :   OctalNumeral IntegerTypeSuffix?
    ;

fragment
BinaryIntegerLiteral
    :   BinaryNumeral IntegerTypeSuffix?
    ;

fragment
IntegerTypeSuffix
    :   [lL]
    ;

fragment
DecimalNumeral
    :   '0'
    |   NonZeroDigit (Digits? | Underscores Digits)
    ;

fragment
Digits
    :   Digit (DigitOrUnderscore* Digit)?
    ;

fragment
Digit
    :   '0'
    |   NonZeroDigit
    ;

fragment
NonZeroDigit
    :   [1-9]
    ;

fragment
DigitOrUnderscore
    :   Digit
    |   '_'
    ;

fragment
Underscores
    :   '_'+
    ;

fragment
HexNumeral
    :   '0' [xX] HexDigits
    ;

fragment
HexDigits
    :   HexDigit (HexDigitOrUnderscore* HexDigit)?
    ;

fragment
HexDigit
    :   [0-9a-fA-F]
    ;

fragment
HexDigitOrUnderscore
    :   HexDigit
    |   '_'
    ;

fragment
OctalNumeral
    :   '0' Underscores? OctalDigits
    ;

fragment
OctalDigits
    :   OctalDigit (OctalDigitOrUnderscore* OctalDigit)?
    ;

fragment
OctalDigit
    :   [0-7]
    ;

fragment
OctalDigitOrUnderscore
    :   OctalDigit
    |   '_'
    ;

fragment
BinaryNumeral
    :   '0' [bB] BinaryDigits
    ;

fragment
BinaryDigits
    :   BinaryDigit (BinaryDigitOrUnderscore* BinaryDigit)?
    ;

fragment
BinaryDigit
    :   [01]
    ;

fragment
BinaryDigitOrUnderscore
    :   BinaryDigit
    |   '_'
    ;

// §3.10.2 Floating-Point Literals

FloatingPointLiteral
    :   DecimalFloatingPointLiteral
    |   HexadecimalFloatingPointLiteral
    ;

fragment
DecimalFloatingPointLiteral
    :   Digits '.' Digits? ExponentPart? FloatTypeSuffix?
    |   '.' Digits ExponentPart? FloatTypeSuffix?
    |   Digits ExponentPart FloatTypeSuffix?
    |   Digits FloatTypeSuffix
    ;

fragment
ExponentPart
    :   ExponentIndicator SignedInteger
    ;

fragment
ExponentIndicator
    :   [eE]
    ;

fragment
SignedInteger
    :   Sign? Digits
    ;

fragment
Sign
    :   [+-]
    ;

fragment
FloatTypeSuffix
    :   [fFdD]
    ;

fragment
HexadecimalFloatingPointLiteral
    :   HexSignificand BinaryExponent FloatTypeSuffix?
    ;

fragment
HexSignificand
    :   HexNumeral '.'?
    |   '0' [xX] HexDigits? '.' HexDigits
    ;

fragment
BinaryExponent
    :   BinaryExponentIndicator SignedInteger
    ;

fragment
BinaryExponentIndicator
    :   [pP]
    ;

// §3.10.3 Boolean Literals

BooleanLiteral
    :   'true'
    |   'false'
    ;

// §3.10.4 Character Literals

CharacterLiteral
    :   '\'' SingleCharacter '\''
    |   '\'' EscapeSequence '\''
    ;

fragment
SingleCharacter
    :   ~['\\]
    ;

// §3.10.5 String Literals

StringLiteral
    :   '"' StringCharacters? '"'
    ;

fragment
StringCharacters
    :   StringCharacter+
    ;

fragment
StringCharacter
    :   ~["\\]
    |   EscapeSequence
    ;

// §3.10.6 Escape Sequences for Character and String Literals

fragment
EscapeSequence
    :   '\\' [btnfr"'\\]
    |   OctalEscape
    |   UnicodeEscape
    ;

fragment
OctalEscape
    :   '\\' OctalDigit
    |   '\\' OctalDigit OctalDigit
    |   '\\' ZeroToThree OctalDigit OctalDigit
    ;

fragment
UnicodeEscape
    :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
    ;

fragment
ZeroToThree
    :   [0-3]
    ;

// §3.10.7 The Null Literal

NullLiteral
    :   'null'
    ;

// §3.11 Separators

LPAREN          : '(';
RPAREN          : ')';
LBRACE          : '{';
RBRACE          : '}';
LBRACK          : '[';
RBRACK          : ']';
SEMI            : ';';
COMMA           : ',';
DOT             : '.';

// §3.12 Operators

ASSIGN          : '=';
GT              : '>';
LT              : '<';
BANG            : '!';
TILDE           : '~';
QUESTION        : '?';
COLON           : ':';
EQUAL           : '==';
LE              : '<=';
GE              : '>=';
NOTEQUAL        : '!=';
AND             : '&&';
OR              : '||';
INC             : '++';
DEC             : '--';
ADD             : '+';
SUB             : '-';
MUL             : '*';
DIV             : '/';
BITAND          : '&';
BITOR           : '|';
CARET           : '^';
MOD             : '%';

ADD_ASSIGN      : '+=';
SUB_ASSIGN      : '-=';
MUL_ASSIGN      : '*=';
DIV_ASSIGN      : '/=';
AND_ASSIGN      : '&=';
OR_ASSIGN       : '|=';
XOR_ASSIGN      : '^=';
MOD_ASSIGN      : '%=';
LSHIFT_ASSIGN   : '<<=';
RSHIFT_ASSIGN   : '>>=';
URSHIFT_ASSIGN  : '>>>=';

// §3.8 Identifiers (must appear after all keywords in the grammar)

Identifier
    :   JavaLetter JavaLetterOrDigit*
    ;

fragment
JavaLetter
    :   [a-zA-Z$_] // these are the "java letters" below 0xFF
    |   // covers all characters above 0xFF which are not a surrogate
        ~[\u0000-\u00FF\uD800-\uDBFF]
        {Character.isJavaIdentifierStart(_input.LA(-1))}?
    |   // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
        [\uD800-\uDBFF] [\uDC00-\uDFFF]
        {Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
    ;

fragment
JavaLetterOrDigit
    :   [a-zA-Z0-9$_] // these are the "java letters or digits" below 0xFF
    |   // covers all characters above 0xFF which are not a surrogate
        ~[\u0000-\u00FF\uD800-\uDBFF]
        {Character.isJavaIdentifierPart(_input.LA(-1))}?
    |   // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
        [\uD800-\uDBFF] [\uDC00-\uDFFF]
        {Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
    ;

//
// Additional symbols not defined in the lexical specification
//

AT : '@';
ELLIPSIS : '...';

//
// Whitespace and comments
//

WS  :  [ \t\r\n\u000C]+ -> skip
    ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;
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
    package edu.diploma.parser;
 
    import java.util.Map;
    import java.util.LinkedList;
    import java.util.HashMap;
    import java.util.Collections;
         
    import edu.diploma.metamodel.*;
    import edu.diploma.metamodel.types.*;
    import edu.diploma.metamodel.expressions.*;
    import edu.diploma.metamodel.statements.*;
    import edu.diploma.metamodel.declarations.*;
    import edu.diploma.metamodel.literals.*;
    import edu.diploma.parser.util.TypeFactory;
    import edu.diploma.parser.UntypedVariable;
}

// starting point for parsing a java file
compilationUnit
returns [TranslationUnit result]
locals [List<Import> imports = new LinkedList<>(),
        List<Declaration> types = new LinkedList<>()]
    :   packageDeclaration? 
        ( importDeclaration  { $imports.add($importDeclaration.result); })* 
        ( typeDeclaration 
          { 
              if ($typeDeclaration.result != null) {
                  $types.add($typeDeclaration.result); 
              }
          }
        )* 
        EOF
        {
            $result = new TranslationUnit($imports, $types);
        }
    ;

packageDeclaration
    :   annotation*  'package' qualifiedName ';'
    ;

importDeclaration
returns [Import result]
locals [boolean isStatic = false, boolean isWildcard = false]
    :   'import' 
        ('static' {$isStatic = true;})? 
        qualifiedName 
        ('.' '*' {$isWildcard = true;})? ';'
        { 
            $result = new Import($qualifiedName.result, $isStatic, $isWildcard); 
        }
    ;

typeDeclaration
returns [Declaration result]
locals [List<Annotation> annos = new LinkedList<>(),
        List<String> mods = new LinkedList<>()]
    :   ( classOrInterfaceModifier 
          { 
              if ($classOrInterfaceModifier.mod != null) {
                  $mods.add($classOrInterfaceModifier.mod);
              }
              if ($classOrInterfaceModifier.anno != null) {
                  $annos.add($classOrInterfaceModifier.anno);
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
                $typeBound.ctx == null ? Collections.<Type>emptyList() : $typeBound.result); 
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
    $result = new DeclBody(new LinkedList<Declaration>());       
}
    :   ';' ( classBodyDeclaration { $result.add($classBodyDeclaration.result);})*
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
    :   '{' ( classBodyDeclaration { $result.add($classBodyDeclaration.result); })* '}'
    ;

interfaceBody
returns [DeclBody result]
@init {
    $result = new DeclBody(new LinkedList<Declaration>()); ;
}
    :   '{' ( interfaceBodyDeclaration 
              { 
                  if ($interfaceBodyDeclaration.result != null) {
                      $result.add($interfaceBodyDeclaration.result);
                  }
              }
            )*  '}'
    ;

classBodyDeclaration
returns [Declaration result]
locals [List<String> mods = new LinkedList<>(), 
        List<Annotation> annos = new LinkedList<>()]
    :   ';'             { $result = DeclBody.EMPTY; }
    |   'static'? block { $result = DeclBody.EMPTY; }
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
    :   methodDeclaration             { $result = $methodDeclaration.result; }
    |   genericMethodDeclaration      { $result = $genericMethodDeclaration.result; }
    |   fieldDeclaration              { $result = $fieldDeclaration.result; }
    |   constructorDeclaration        { $result = $constructorDeclaration.result; }
    |   genericConstructorDeclaration { $result = $genericConstructorDeclaration.result; }
    |   interfaceDeclaration          { $result = $interfaceDeclaration.result; }
    |   annotationTypeDeclaration     { $result = $annotationTypeDeclaration.result; }
    |   classDeclaration              { $result = $classDeclaration.result; }
    |   enumDeclaration               { $result = $enumDeclaration.result; }
    ;

/* We use rule this even for void methods which cannot have [] after parameters.
   This simplifies grammar and we can consider void to be a type, which
   renders the [] matching as a context-sensitive issue or a semantic check
   for invalid return type after parsing.
 */
methodDeclaration
returns [FunctionDecl result]
locals [Type retType, 
        List<String> exceptions = Collections.emptyList(),
        StatementBlock body = StatementBlock.EMPTY_BLOCK]  
    :   (type {$retType = $type.result;} | 'void' {$retType = new PrimitiveType("void");}) 
        Identifier formalParameters ('[' ']' { $retType = new ArrayType($retType); })*
        ('throws' qualifiedNameList { $exceptions = $qualifiedNameList.result; })?
        (   methodBody { $body = $methodBody.result; }
        |   ';'
        )
        {
            $result = new FunctionDecl.Builder($retType, $Identifier.text)
                      .params($formalParameters.result)
                      .body($body)
                      .exceptions($exceptions)
                      .build();
        }   
    ;

genericMethodDeclaration
returns [FunctionDecl result]
    :   typeParameters methodDeclaration
        {
            FunctionDecl t = $methodDeclaration.result;
            $result = new FunctionDecl.Builder(t.getRetType(), t.getName())
                      .params(t.getParams())
                      .body(t.getBody())
                      .exceptions(t.getExceptions())
                      .templates($typeParameters.result)
                      .build(); 
        } 
    ;

constructorDeclaration
returns [FunctionDecl result]
    :   Identifier formalParameters ('throws' qualifiedNameList)?
        constructorBody
        {
            FunctionDecl.Builder builder = 
                new FunctionDecl.Builder(null, $Identifier.text)
                                .params($formalParameters.result)
                                .body($constructorBody.result);
            if ($qualifiedNameList.ctx != null) {
                builder.exceptions($qualifiedNameList.result);
            }                                     
            $result = builder.build();
        } 
    ;

genericConstructorDeclaration
returns [FunctionDecl result]
    :   typeParameters constructorDeclaration
        {
            FunctionDecl t = $constructorDeclaration.result;
            $result = new FunctionDecl.Builder(t.getRetType(), t.getName())
                      .params(t.getParams())
                      .body(t.getBody())
                      .exceptions(t.getExceptions())
                      .templates($typeParameters.result)
                      .build(); 
        } 
    ;

fieldDeclaration
returns [DeclBody result]
@init {
    $result = new DeclBody();
}       
    :   type variableDeclarators ';'
        {
            for (final UntypedVariable decl : $variableDeclarators.result) {
                $result.add(decl.createVariableDecl($type.result));                                                                
            }                                                                 
        } 
    ;

interfaceBodyDeclaration
returns [Declaration result]
locals [List<String> mods = new LinkedList<>(), 
        List<Annotation> annos = new LinkedList<>()]
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
            $result = new DeclBody($interfaceMemberDeclaration.result);
            $result.addModifiers($mods);
            $result.addAnnotations($annos);
        }
    |   ';'
    ;

interfaceMemberDeclaration
returns [List<Declaration> result]
@init {
    $result = new LinkedList<>();       
}       
    :   constDeclaration                  { $result.addAll($constDeclaration.result); }
    |   interfaceMethodDeclaration        { $result.add($interfaceMethodDeclaration.result); }
    |   genericInterfaceMethodDeclaration { $result.add($genericInterfaceMethodDeclaration.result); }
    |   interfaceDeclaration              { $result.add($interfaceDeclaration.result); }
    |   annotationTypeDeclaration         { $result.add($annotationTypeDeclaration.result); }
    |   classDeclaration                  { $result.add($classDeclaration.result); }
    |   enumDeclaration                   { $result.add($enumDeclaration.result); }
    ;

constDeclaration
returns [List<VariableDecl> result] 
@init {
    $result = new LinkedList<>();
}
    :   type constantDeclarator { $result.add($constantDeclarator.result.createVariableDecl($type.result)); } 
        (',' constantDeclarator { $result.add($constantDeclarator.result.createVariableDecl($type.result)); })* ';'
    ;

constantDeclarator
returns [UntypedVariable result]
    :   Identifier { $result = new UntypedVariable($Identifier.text); }
        ('[' ']' { $result = new UntypedVariable($result); })* 
        '=' variableInitializer
        {
            $result.setValue($variableInitializer.result); 
        } 
    ;

// see matching of [] comment in methodDeclaratorRest
interfaceMethodDeclaration
returns [FunctionDecl result]
locals [Type retType, 
        List<String> exceptions = Collections.emptyList()]
    :   (type {$retType = $type.result;} | 'void' {$retType = new PrimitiveType("void");}) 
        Identifier formalParameters ('[' ']' { $retType = new ArrayType($retType); })*
        ('throws' qualifiedNameList { $exceptions = $qualifiedNameList.result; })?
        ';'
        {
            $result = new FunctionDecl.Builder($retType, $Identifier.text)
                      .params($formalParameters.result)
                      .exceptions($exceptions)
                      .build();
        }
    ;

genericInterfaceMethodDeclaration
returns [FunctionDecl result]
    :   typeParameters interfaceMethodDeclaration
        {
            FunctionDecl t = $interfaceMethodDeclaration.result;
            $result = new FunctionDecl.Builder(t.getRetType(), t.getName())
                      .params(t.getParams())
                      .body(t.getBody())
                      .exceptions(t.getExceptions())
                      .templates($typeParameters.result)
                      .build(); 
        }
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
    :   'boolean' { $result = new PrimitiveType("boolean"); }
    |   'char'    { $result = new PrimitiveType("char"); }
    |   'byte'    { $result = new PrimitiveType("byte"); }
    |   'short'   { $result = new PrimitiveType("short"); }
    |   'int'     { $result = new PrimitiveType("int"); }
    |   'long'    { $result = new PrimitiveType("long"); }
    |   'float'   { $result = new PrimitiveType("float"); }
    |   'double'  { $result = new PrimitiveType("double"); }
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
returns [List<ParameterDecl> result]
@init {
    $result = Collections.emptyList();
}
    :   '(' ( formalParameterList { $result = $formalParameterList.result; })? ')'
    ;

formalParameterList
returns [List<ParameterDecl> result]
@init {
    $result = new LinkedList<>();
}
    :   formalParameter { $result.add($formalParameter.result); } 
        (',' formalParameter { $result.add($formalParameter.result); })* 
        (',' lastFormalParameter { $result.add($lastFormalParameter.result); })?
    |   lastFormalParameter { $result.add($lastFormalParameter.result); }
    ;

formalParameter
returns [ParameterDecl result]
locals [List<Annotation> annos = new LinkedList<>(),
        List<String> mods = new LinkedList<>()]
    :   ( variableModifier
          {
              if ($variableModifier.mod != null) {
                  $mods.add($variableModifier.mod);
              }
              if ($variableModifier.anno != null) {
                  $annos.add($variableModifier.anno);
              }
          }
        )* type variableDeclaratorId
        {
            VariableDecl var = $variableDeclaratorId.result.createVariableDecl($type.result);
            $result = new ParameterDecl(var, false);
            $result.addModifiers($mods);                                    
            $result.addAnnotations($annos);                                    
        }
    ;

lastFormalParameter
returns [ParameterDecl result]
locals [List<Annotation> annos = new LinkedList<>(),
        List<String> mods = new LinkedList<>()]
    :   ( variableModifier
          {
              if ($variableModifier.mod != null) {
                  $mods.add($variableModifier.mod);
              }
              if ($variableModifier.anno != null) {
                  $annos.add($variableModifier.anno);
              }
          }
        )* type '...' variableDeclaratorId
        {
            VariableDecl var = $variableDeclaratorId.result.createVariableDecl($type.result);
            $result = new ParameterDecl(var, true);
            $result.addModifiers($mods);                                    
            $result.addAnnotations($annos);                                    
        }
    ;

methodBody
returns [StatementBlock result]
    :   block { $result = $block.result; }
    ;

constructorBody
returns [StatementBlock result]
    :   block { $result = $block.result; }
    ;

qualifiedName
returns [String result]
locals [StringBuilder res]
    :   Identifier
        { $res = new StringBuilder($Identifier.text); }
        ( '.' Identifier { $res.append(".").append($Identifier.text); } )*
        { $result = $res.toString(); }
    ;

literal
returns [Literal result]
    :   IntegerLiteral       {$result = new IntegerLiteral(new PrimitiveType("int"), $IntegerLiteral.text);}
    |   FloatingPointLiteral {$result = new FloatLiteral(new PrimitiveType("double"), $FloatingPointLiteral.text);}
    |   CharacterLiteral     {$result = new CharLiteral(new PrimitiveType("char"), $CharacterLiteral.text);}
    |   StringLiteral        {$result = new StringLiteral(new ClassType("java.lang.String"), $StringLiteral.text);}
    |   BooleanLiteral       {$result = new BooleanLiteral(new PrimitiveType("boolean"), $BooleanLiteral.text);}
    |   'null'               {$result = new SpecialLiteral("null");}
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
    :   expression                   { $result = $expression.result; }
    |   annotation                   { $result = $annotation.result; }
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
    $result = new DeclBody(new LinkedList<Declaration>()); ;
}
    :   '{' 
        (
            ( annotationTypeElementDeclaration 
              {
                  if ($annotationTypeElementDeclaration.result != null) {
                      $result.add($annotationTypeElementDeclaration.result);
                  }
              }
            )*
        |   ( annotationConstantsDeclaration
              {
                  $result.addAll($annotationConstantsDeclaration.result);
              }
            )+
        )
        '}'
    ;

annotationTypeElementDeclaration
returns [Declaration result]
locals [List<String> mods = new LinkedList<>(), 
        List<Annotation> annos = new LinkedList<>()]
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
            $result = $annotationTypeElementRest.result;
            $result.addModifiers($mods);
            $result.addAnnotations($annos);
        }
    |   ';' { $result = null; } // this is not allowed by the grammar, but apparently allowed by the actual compiler
    ;

annotationTypeElementRest
returns [Declaration result]
    :   annotationMethod ';'           { $result = $annotationMethod.result; }
    |   classDeclaration ';'?          { $result = $classDeclaration.result; }
    |   interfaceDeclaration ';'?      { $result = $interfaceDeclaration.result; }
    |   enumDeclaration ';'?           { $result = $enumDeclaration.result; }
    |   annotationTypeDeclaration ';'? { $result = $annotationTypeDeclaration.result; }
    ;


annotationConstantsDeclaration
returns [List<Declaration> result]
locals [List<String> mods = new LinkedList<>(), 
        List<Annotation> annos = new LinkedList<>()]
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
returns [FunctionDecl result]
    :   type annotationMethodRest
        {
            $result = new FunctionDecl.Builder($type.result, $annotationMethodRest.result).build();
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
returns [StatementBlock result]
@init {
    $result = new StatementBlock();       
}
    :   '{' 
        ( blockStatement { $result.addAll($blockStatement.result); })* 
        '}'
    ;

blockStatement
returns [List<Statement> result]
@init {
    $result = new LinkedList<>();
}
    :   localVariableDeclarationStatement { $result.addAll($localVariableDeclarationStatement.result); }
    |   statement                         { $result.add($statement.result); }
    //|   typeDeclaration                   { $result = $typeDeclaration.result; }
    ;

localVariableDeclarationStatement
returns [List<VariableDeclStatement> result]
    :    localVariableDeclaration ';' { $result = $localVariableDeclaration.result; }
    ;

localVariableDeclaration
returns [List<VariableDeclStatement> result]
locals [List<Annotation> annos = new LinkedList<>(),
        List<String> mods = new LinkedList<>()]
@init {
    $result = new LinkedList<>();       
}       
    :   ( variableModifier
          {
              if ($variableModifier.mod != null) {
                  $mods.add($variableModifier.mod);
              }
              if ($variableModifier.anno != null) {
                  $annos.add($variableModifier.anno);
              }
          }
        )* type variableDeclarators
        {
            for (final UntypedVariable var : $variableDeclarators.result) {
                VariableDecl varDecl = var.createVariableDecl($type.result);
                varDecl.addModifiers($mods);                                    
                varDecl.addAnnotations($annos);
                $result.add(new VariableDeclStatement(varDecl));
            }
        } 
    ;

statement
returns [Statement result]
locals [List<SwitchStatement.Label> labels = new LinkedList<>(),
        List<CatchStatement> catches = new LinkedList<>(),
        StatementBlock tfinal]
    :   block { $result = $block.result; }
    |   ASSERT carExpr=expression (':' cdrExpr=expression)? ';'
        {
            $result = new EmptyStatement();
        }
    |   'if' parExpression car=statement ('else' cdr=statement)?
        {
            $result = 
                new IfStatement($parExpression.result, $car.result, $cdr.ctx == null ? null : $cdr.result);
        } 
    |   forStatement     { $result = $forStatement.result; }
    |   forEach          { $result = $forEach.result; }
    |   'while' parExpression state=statement 
        { $result = new WhileStatement($parExpression.result, $state.result); }
    |   'do' state=statement 'while' parExpression ';' 
        { $result = new DoWhileStatement($parExpression.result, $state.result); }
    |   'try' block 
        (
            (catchClause {$catches.addAll($catchClause.result);})+ 
            (finallyBlock {$tfinal = $finallyBlock.result;})? 
        |   finallyBlock {$tfinal = $finallyBlock.result;}
        )
        {
            $result = new TryStatement($block.result, $catches, $tfinal);
        }
    |   'try' resourceSpecification block (catchClause {$catches.addAll($catchClause.result);})* 
        (finallyBlock {$tfinal = $finallyBlock.result;})?
        {
            $result = new TryWithResourcesStatement($resourceSpecification.result, $block.result, $catches, $tfinal);
        }
    |   'switch' parExpression '{' 
        ( switchBlockStatementGroup {$labels.addAll($switchBlockStatementGroup.result); })* 
        ( switchLabel {$labels.add($switchLabel.result); })* '}'
        {
            $result = new SwitchStatement($parExpression.result, $labels);
        }
    |   'synchronized' parExpression block { $result = new EmptyStatement(); }
    |   'return' expression? ';'   
        { $result = new ReturnStatement($expression.ctx == null ? null : $expression.result); }
    |   'throw' expression ';'     
        { $result = new ThrowStatement($expression.result); }
    |   'break' Identifier? ';'
        { $result = new BreakStatement($Identifier == null ? "" : $Identifier.text); }
    |   'continue' Identifier? ';'
        { $result = new ContinueStatement($Identifier == null ? "" : $Identifier.text); }
    |   ';'                        { $result = new EmptyStatement(); }
    |   statementExpression ';'    { $result = $statementExpression.result; }
    |   Identifier ':' statement   
        { $result = new LabelStatement($Identifier.text, $statement.result); }
    ;

catchClause
returns [List<CatchStatement> result]
locals [List<Annotation> annos = new LinkedList<>(),
        List<String> mods = new LinkedList<>()]
@init {
    $result = new LinkedList<>();
}
    :   'catch' '(' 
        ( variableModifier
          {
              if ($variableModifier.mod != null) {
                  $mods.add($variableModifier.mod);
              }
              if ($variableModifier.anno != null) {
                  $annos.add($variableModifier.anno);
              }
          }
        )* catchType Identifier ')' block
        {
            for (final String typeName : $catchType.result) {
                final Type type = new ClassType(typeName);
                VariableDecl var = new VariableDecl(type, $Identifier.text);
                var.addModifiers($mods);
                var.addAnnotations($annos);
                VariableDeclStatement varState = new VariableDeclStatement(var);
                $result.add(new CatchStatement(varState, $block.result));
            }
        } 
    ;

catchType
returns [List<String> result]
@init {
    $result = new LinkedList<>();       
}
    :   qualifiedName { $result.add($qualifiedName.result); } 
        ('|' qualifiedName { $result.add($qualifiedName.result); })*
    ;

finallyBlock
returns [StatementBlock result]
    :   'finally' block { $result = $block.result; }
    ;

resourceSpecification
returns [List<VariableDecl> result]
    :   '(' resources { $result = $resources.result; } ';'? ')'
    ;

resources
returns [List<VariableDecl> result]
@init {
    $result = new LinkedList<>();       
}  
    :   resource { $result.add($resource.result); } (';' resource { $result.add($resource.result); })*
    ;

resource
returns [VariableDecl result]
locals [List<Annotation> annos = new LinkedList<>(),
        List<String> mods = new LinkedList<>()]
    :   ( variableModifier
          {
              if ($variableModifier.mod != null) {
                  $mods.add($variableModifier.mod);
              }
              if ($variableModifier.anno != null) {
                  $annos.add($variableModifier.anno);
              }
          }
        )* classOrInterfaceType variableDeclaratorId '=' expression
        {
            final UntypedVariable var = $variableDeclaratorId.result;
            var.setValue($expression.result);
            $result = var.createVariableDecl($classOrInterfaceType.result);
            $result.addModifiers($mods);                                    
            $result.addAnnotations($annos);                                    
        }
    ;

/** Matches cases then statements, both of which are mandatory.
 *  To handle empty cases at the end, we add switchLabel* to statement.
 */
switchBlockStatementGroup
returns [List<SwitchStatement.Label> result]
locals [SwitchStatement.Label label = null]
@init {
    $result = new LinkedList<>();
}
    :   ( 
            switchLabel 
            {
                if ($label != null) {
                    $result.add($label);
                }
                $label = $switchLabel.result;
            }
        )+ 
        ( blockStatement { $label.addAll($blockStatement.result); })+
        {
            $result.add($label);
        }
    ;

switchLabel
returns [SwitchStatement.Label result]
    :   'case' constantExpression ':' { $result = new SwitchStatement.Label($constantExpression.result); }
    |   'case' enumConstantName ':' 
        { $result = new SwitchStatement.Label(new VariableReference($enumConstantName.result)); }
    |   'default' ':' { $result = SwitchStatement.Label.DEFAULT; }
    ;

forStatement
returns [ForStatement result]
    :   'for' '(' forInit? ';' expression? ';' forUpdate? ')' statement
        {
            Statement init = $forInit.ctx == null ? null : $forInit.result;
            Expression cond = $expression.ctx == null ? null : $expression.result;
            Statement action = $forUpdate.ctx == null ? null : $forUpdate.result;
            $result = new ForStatement(init, cond, action, $statement.result);
        } 
    ;    
        
forEach
returns [ForEachStatement result]
    :   'for' '(' enhancedForControl ')' statement 
        {
            $result = 
                new ForEachStatement($enhancedForControl.init, $enhancedForControl.range, $statement.result);
        } 
    ;    

forInit
returns [Statement result]
    :   localVariableDeclaration 
        { 
            StatementList t = new StatementList();
            t.addAll($localVariableDeclaration.result);
            $result = t;
        }
    |   expressionList { $result = $expressionList.result; }
    ;

enhancedForControl
returns [VariableDeclStatement init, Expression range]
locals [List<Annotation> annos = new LinkedList<>(),
        List<String> mods = new LinkedList<>()]
    :   ( variableModifier
          {
              if ($variableModifier.mod != null) {
                  $mods.add($variableModifier.mod);
              }
              if ($variableModifier.anno != null) {
                  $annos.add($variableModifier.anno);
              }
          }
        )* type Identifier ':' expression
        {
            VariableDecl decl = new VariableDecl($type.result, $Identifier.text);
            decl.addModifiers($mods);
            decl.addAnnotations($annos);
            $init = new VariableDeclStatement(decl);  
            $range = $expression.result;
        }
    ;

forUpdate
returns [ExpressionList result]
    :   expressionList { $result = $expressionList.result; }
    ;

// EXPRESSIONS

parExpression
returns [Expression result]
    :   '(' expression ')' { $result = $expression.result; }
    ;

expressionList
returns [ExpressionList result]
@init {
    $result = new ExpressionList();
}
    :   expression { $result.add($expression.result); } 
        (',' expression { $result.add($expression.result); })*
    ;

statementExpression
returns [Expression result]
    :   expression { $result = $expression.result; }
    ;

constantExpression
returns [Expression result]
    :   expression { $result = $expression.result; }
    ;

expression
returns [Expression result]
locals [String operation]
    :   primary { $result = $primary.result; }
    |   param=expression '.' Identifier 
        { $result = new AttributeAccess($param.result, $Identifier.text); }
    |   param=expression '.' 'this'
        { $result = new AttributeAccess($param.result, "this"); }
    //|   expression '.' 'new' nonWildcardTypeArguments? innerCreator
    //|   expression '.' 'super' superSuffix
    |   param=expression '.' explicitGenericInvocation
        { 
            $result = new FunctionCall($explicitGenericInvocation.name, 
                                       $param.result, 
                                       $explicitGenericInvocation.params, 
                                       $explicitGenericInvocation.templates); 
        }
    |   caller=expression '[' param=expression ']'
        { $result = new ArrayAccessExpression($caller.result, $param.result); }
    |   Identifier '(' expressionList? ')'
        { 
            List<Expression> params = $expressionList.ctx == null ? Collections.<Expression>emptyList() : $expressionList.result.asList();
            $result = new FunctionCall($Identifier.text, null, params);
        }
    |   'this' '(' expressionList? ')'
        { 
            List<Expression> params = $expressionList.ctx == null ? Collections.<Expression>emptyList() : $expressionList.result.asList();
            $result = new FunctionCall("this", null, params);
        }
    |   param=expression '.' Identifier '(' expressionList? ')'
        { 
            List<Expression> params = $expressionList.ctx == null ? Collections.<Expression>emptyList() : $expressionList.result.asList();
            $result = new FunctionCall($Identifier.text, $param.result, params);
        }
    |   'new' creator { $result = $creator.result; }
    |   '(' type ')' param=expression 
        { $result = new CastExpression($type.result, $param.result); }
    |   param=expression ('++' { $operation = "++"; } | '--' { $operation = "--"; })
        { $result = new UnaryExpression($param.result, $operation, true); }
    |   ('+' { $operation = "+"; } | '-' { $operation = "-"; } | '++' { $operation = "++"; } | '--' { $operation = "--"; }) 
        param=expression
        { $result = new UnaryExpression($param.result, $operation); } 
    |   ('~' { $operation = "~"; } | '!' { $operation = "!"; }) param=expression
        { $result = new UnaryExpression($param.result, $operation); } 
    |   lhs=expression ('*' { $operation = "*"; } | '/' { $operation = "/"; } | '%' { $operation = "%"; }) rhs=expression
        { $result = new BinaryExpression($lhs.result, $rhs.result, $operation); } 
    |   lhs=expression ('+' { $operation = "+"; } | '-' { $operation = "-"; }) rhs=expression
        { $result = new BinaryExpression($lhs.result, $rhs.result, $operation); }
    |   lhs=expression ('<' '<' { $operation = "<<"; } | '>' '>' '>' { $operation = ">>>"; } | '>' '>' { $operation = ">>"; }) rhs=expression
        { $result = new BinaryExpression($lhs.result, $rhs.result, $operation); }
    |   lhs=expression ('<=' { $operation = "<="; } | '>=' { $operation = ">="; } | '>' { $operation = ">"; } | '<' { $operation = "<"; }) rhs=expression
        { $result = new BinaryExpression($lhs.result, $rhs.result, $operation); }
    |   expression 'instanceof' type
        { $result = new BinaryExpression($expression.result, new TypeExpression($type.result), "instanceof"); }
    |   lhs=expression ('==' { $operation = "=="; } | '!=' { $operation = "!="; }) rhs=expression
        { $result = new BinaryExpression($lhs.result, $rhs.result, $operation); }
    |   lhs=expression '&' rhs=expression
        { $result = new BinaryExpression($lhs.result, $rhs.result, "&"); }
    |   lhs=expression '^' rhs=expression
        { $result = new BinaryExpression($lhs.result, $rhs.result, "^"); }
    |   lhs=expression '|' rhs=expression
        { $result = new BinaryExpression($lhs.result, $rhs.result, "|"); }
    |   lhs=expression '&&' rhs=expression
        { $result = new BinaryExpression($lhs.result, $rhs.result, "&&"); }
    |   lhs=expression '||' rhs=expression
        { $result = new BinaryExpression($lhs.result, $rhs.result, "||"); }
    |   cond=expression '?' ifBranch=expression ':' elseBranch=expression
        { $result = new TernaryExpression($cond.result, $ifBranch.result, $elseBranch.result); }
    |   <assoc=right> lhs=expression '=' rhs=expression
        { $result = new AssignmentExpression($lhs.result, $rhs.result); }
    |   <assoc=right> lhs=expression
        (   '+='   { $operation = "+"; }
        |   '-='   { $operation = "-"; }
        |   '*='   { $operation = "*"; }
        |   '/='   { $operation = "/"; }
        |   '&='   { $operation = "&"; }
        |   '|='   { $operation = "|"; }
        |   '^='   { $operation = "^"; }
        |   '>>='  { $operation = ">>"; }
        |   '>>>=' { $operation = ">>>"; }
        |   '<<='  { $operation = "<<"; }
        |   '%='   { $operation = "%"; }
        )
        rhs=expression
        { $result = new AssignmentExpression($lhs.result, new BinaryExpression($lhs.result, $rhs.result, $operation)); }
    ;

primary
returns [Expression result]
    :   '(' expression ')' { $result = $expression.result; }
    |   ( 'this' { $result = new VariableReference("this"); }
        | 'super' { $result = new VariableReference("super"); })
    |   literal            { $result = $literal.result; }
    |   Identifier         { $result = new VariableReference($Identifier.text); }    
    |   type '.' 'class'
        {
            Type type = TypeFactory.createJavaClassType($type.result);
            VariableReference var = new VariableReference("class"); 
            $result = new StaticAttributeAccess($type.result, var);
        }
    |   'void' '.' 'class'
        {
            Type type = TypeFactory.createJavaClassType(new PrimitiveType("void"));
            VariableReference var = new VariableReference("class"); 
            $result = new StaticAttributeAccess(new PrimitiveType("void"), var);
        }
    //|   nonWildcardTypeArguments (explicitGenericInvocationSuffix | 'this' arguments)
    ;

creator
returns [Expression result]
locals [List<Type> types = Collections.emptyList()]
    :   (nonWildcardTypeArguments {$types = $nonWildcardTypeArguments.result;})? createdName classCreatorRest
        {
            $result = new ConstructorCall($createdName.result, 
                                          $classCreatorRest.params, 
                                          $types, 
                                          $classCreatorRest.body, true); 
        }
    |   createdName arrayCreatorRest
        {
            $result = new ArrayConstructorCall($createdName.result, $arrayCreatorRest.params, true);
        }
    ;

createdName
returns [Type result]
locals [ClassType t]
    :   Identifier typeArgumentsOrDiamond?
        {
            if ($typeArgumentsOrDiamond.ctx == null) {
                $t = new ClassType($Identifier.text);                                          
            } else {
                $t = new ClassType($Identifier.text, $typeArgumentsOrDiamond.result);    
            }         
        } 
        ( '.' Identifier typeArgumentsOrDiamond?
          {
              if ($typeArgumentsOrDiamond.ctx == null) {
                  $t = new ClassType($Identifier.text, Collections.<TemplateParameter>emptyList(), $t);                                          
              } else {
                  $t = new ClassType($Identifier.text, $typeArgumentsOrDiamond.result, $t);    
              }         
          }
        )*
        { $result = $t; }
    |   primitiveType { $result = $primitiveType.result; }
    ;

innerCreator
    :   Identifier nonWildcardTypeArgumentsOrDiamond? classCreatorRest
    ;

arrayCreatorRest
returns [ExpressionList params]
@init {
    $params = new ExpressionList();       
}       
    :   '[' ']' { $params.add(null); } ('[' ']' { $params.add(null); })* 
        arrayInitializer
    |   '[' expression { $params.add($expression.result); } ']' 
        ('[' expression ']' { $params.add($expression.result); })* 
        ('[' ']' { $params.add(null); })*
    ;

classCreatorRest
returns [List<Expression> params, DeclBody body]
    :   arguments { $params = $arguments.result; } (classBody {$body = $classBody.result;} )?
    ;

explicitGenericInvocation
returns [List<Type> templates, String name, List<Expression> params]
    :   nonWildcardTypeArguments explicitGenericInvocationSuffix
        {
            $templates =  $nonWildcardTypeArguments.result;
            $name = $explicitGenericInvocationSuffix.name;
            $params = $explicitGenericInvocationSuffix.args;
        }
    ;

nonWildcardTypeArguments
returns [List<Type> result]
    :   '<' typeList {$result = $typeList.result;} '>'
    ;

typeArgumentsOrDiamond
returns [List<TemplateParameter> result]
    :   '<' '>'       { $result = Collections.emptyList(); }
    |   typeArguments { $result = $typeArguments.result; }
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
returns [String name, List<Expression> args]
    :   //'super' superSuffix
    //|   Identifier arguments
    Identifier arguments
    {
        $name = $Identifier.text;
        $args = $arguments.result;
    }
    ;

arguments
returns [List<Expression> result]      
    :   '(' expressionList? ')'
        {
            $result = $expressionList.ctx == null ? Collections.<Expression>emptyList() : $expressionList.result.asList();
        } 
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
package lombok.core.handlers;

import static lombok.ast.AST.Annotation;
import static lombok.ast.AST.FieldDecl;
import static lombok.ast.AST.String;
import static lombok.ast.AST.Type;
import static lombok.core.util.Names.decapitalize;

import java.util.List;

import lombok.ast.FieldDecl;
import lombok.ast.IMethod;
import lombok.ast.IType;

public abstract class AbstractArtemisHandler<COMPILER_BINDING, TYPE_TYPE extends IType<METHOD_TYPE,?,?,?,?,?>, METHOD_TYPE extends IMethod<TYPE_TYPE,?,?,?>>
{
	public void handle(TYPE_TYPE type, List<Object> mappedComponentTypes, List<Object> systemTypes, List<Object> managerTypes)
	{
		for (Object component : mappedComponentTypes)
			type.editor().injectField(createMapperField(getBinding(type, component)));
		
		for (Object system : systemTypes)
			type.editor().injectField(createField(getBinding(type, system)));
		
		for (Object manager : managerTypes)
			type.editor().injectField(createField(getBinding(type, manager)));
		
		type.editor().rebuild();
	}

	private FieldDecl createField(COMPILER_BINDING type)
	{
		String name = toFieldName(type);
		
		return FieldDecl(Type(toQualifiedName(type)), name)
				.makePrivate();
	}

	private FieldDecl createMapperField(COMPILER_BINDING componentType)
	{
		String name = decapitalize(toFieldName(componentType)) + "Mapper";
		
		return FieldDecl(Type("com.artemis.ComponentMapper")
				.withTypeArgument(Type(toQualifiedName(componentType))), name)
				.withAnnotation(Annotation(Type(SuppressWarnings.class))
					.withValue(String("all"))) // not sure why this bleeds...
				.makePrivate();
	}

	protected abstract COMPILER_BINDING getBinding(TYPE_TYPE type,Object classLiteral);
	protected abstract String toFieldName(COMPILER_BINDING binding);
	protected abstract String toQualifiedName(COMPILER_BINDING binding);
}
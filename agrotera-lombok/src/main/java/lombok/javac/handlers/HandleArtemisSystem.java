package lombok.javac.handlers;

import static lombok.core.util.Names.decapitalize;
import static lombok.javac.handlers.ast.JavacResolver.CLASS;

import java.util.List;

import org.kohsuke.MetaInfServices;

import lombok.ArtemisSystem;
import lombok.ast.Annotation;
import lombok.core.AnnotationValues;
import lombok.core.handlers.ArtemisSystemHandler;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.handlers.ast.JavacMethod;
import lombok.javac.handlers.ast.JavacType;

import com.sun.tools.javac.code.Symbol.TypeSymbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;

@MetaInfServices(JavacAnnotationHandler.class)
public class HandleArtemisSystem extends JavacAnnotationHandler<ArtemisSystem>
{

	@Override
	public void handle(final AnnotationValues<ArtemisSystem> annotation, final JCAnnotation source, final JavacNode annotationNode)
	{
		JavacType type = JavacType.typeOf(annotationNode, source);
		for (Annotation a : type.annotations())
		{
			// because all else is null... 
			if (a.toString().startsWith("@WovenByTheHuntress"))
				return;
		}
		
		List<Object> mappedComponentTypes = annotation.getActualExpressions("requires");
		mappedComponentTypes.addAll(annotation.getActualExpressions("requiresOne"));
		mappedComponentTypes.addAll(annotation.getActualExpressions("optional"));
		List<Object> systemTypes = annotation.getActualExpressions("systems");
		List<Object> managerTypes = annotation.getActualExpressions("managers");
		
		if (mappedComponentTypes.size() == 0 
			&& annotation.getActualExpressions("excludes").size() > 0)
		{
			annotationNode.addError(
				"Excludes is only possible with at least 'requires' or 'requiresOne'");
			
			return;
		}
		
		new Handler(annotationNode)
			.handle(type, mappedComponentTypes, systemTypes, managerTypes);
	}

	private static class Handler extends ArtemisSystemHandler<TypeSymbol,JavacType,JavacMethod>
	{
		private JavacNode annotationNode;

		public Handler(JavacNode diagnostic)
		{
			super(diagnostic);
			this.annotationNode = diagnostic;
		}

		@Override
		protected TypeSymbol getBinding(JavacType type, Object classLiteral)
		{
			JCFieldAccess literal = (JCFieldAccess)classLiteral;
			Type resolvedType = CLASS.resolveMember(annotationNode, literal.selected);
			return resolvedType.asElement();
		}

		@Override
		protected String toFieldName(TypeSymbol binding)
		{
			return decapitalize(binding.getSimpleName().toString());
		}

		@Override
		protected String toQualifiedName(TypeSymbol binding)
		{
			return binding.getQualifiedName().toString();
		}
	}
}

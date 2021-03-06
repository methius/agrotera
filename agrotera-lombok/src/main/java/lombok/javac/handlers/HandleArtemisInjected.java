package lombok.javac.handlers;


import java.util.List;

import lombok.ast.pg.Annotation;
import lombok.core.AnnotationValues;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.handlers.ast.JavacType;
import net.onedaybeard.agrotera.annotations.ArtemisInjected;

import org.kohsuke.MetaInfServices;

import com.sun.tools.javac.tree.JCTree.JCAnnotation;

@MetaInfServices(JavacAnnotationHandler.class)
public class HandleArtemisInjected extends JavacAnnotationHandler<ArtemisInjected>
{
	@Override
	public void handle(final AnnotationValues<ArtemisInjected> annotation, final JCAnnotation source, final JavacNode annotationNode)
	{
		JavacType type = JavacType.typeOf(annotationNode, source);
		for (Annotation a : type.annotations())
		{
			// because all else is null... 
			if (a.toString().startsWith("@WovenByTheHuntress"))
				return;
		}
		
		List<Object> mappedComponentTypes = annotation.getActualExpressions("mappers");
		List<Object> systemTypes = annotation.getActualExpressions("systems");
		List<Object> managerTypes = annotation.getActualExpressions("managers");
		
		new JavacHandler(type, annotationNode)
				.handle(mappedComponentTypes, systemTypes, managerTypes)
				.injectInitialize()
				.rebuild();
	}
}

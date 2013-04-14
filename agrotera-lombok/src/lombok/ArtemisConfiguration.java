package lombok;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.EntitySystem;
import com.artemis.Manager;

/**
 * Configures an artemis {@link EntitySystem} or {@link Manager} by injecting
 * code during the compilation phase.</p>
 * 
 * Fields for {@link ComponentMapper}s, <code>EntitySystem</code>s and
 * <code>Manger</code>s are wired in the <code>initialize</code> method, prior
 * to any existing code in the <code>initialize</code> method is executed.</p>
 * 
 * Component mappers are named according to the component type they operate on,
 * suffixed with <code>-Mapper</code>. Systems and managers retain their full
 * type name. All field names are <code>camelCased</code>.</p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ArtemisConfiguration
{
	/**
	 * Required systems are matched by the {@link EntitySystem}'s
	 * aspect. 
	 */
	Class<? extends Component>[] requires() default {};
	
	/**
	 * Only mappers are created for optional components, no bearing on
	 * the system's aspect.
	 */
	Class<? extends Component>[] optional() default {};
	
	/**
	 * Only affects the system's aspect, no mapper is created.
	 */
	Class<? extends Component>[] excludes() default {};
	
	/**
	 * Systems to inject as fields.
	 */
	Class<? extends EntitySystem>[] systems() default {};
	
	/**
	 * Managers to inject as fields.
	 */
	Class<? extends Manager>[] managers() default{};
}

package net.onedaybeard.agrotera.transform;

import net.onedaybeard.agrotera.meta.ArtemisConfigurationData;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

class ProfileEndWeaver extends AdviceAdapter implements Opcodes
{
	private ArtemisConfigurationData info;
	
	ProfileEndWeaver(MethodVisitor methodVisitor, ArtemisConfigurationData info, int access, String name, String desc)
	{
		super(ASM4, methodVisitor, access, name, desc);
		this.info = info;
	}

	@Override
	protected void onMethodExit(int opcode)
	{
		String systemName = info.current.getInternalName();
		String profiler = info.profilerClass.getInternalName();
		String profileDescriptor = info.profilerClass.getDescriptor();
		
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, systemName, "$profiler", profileDescriptor);
		mv.visitMethodInsn(INVOKEVIRTUAL, profiler, "stop", "()V");
	}
}

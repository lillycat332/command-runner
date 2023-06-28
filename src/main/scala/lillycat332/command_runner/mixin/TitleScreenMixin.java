package lillycat332.command_runner.mixin;

import com.google.common.base.Supplier;
import lillycat332.command_runner.CommandRunner$;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Inject(method = "init", at = @At("TAIL"))
	public void onInit(CallbackInfo ci) {
		CommandRunner$ cmd = CommandRunner$.MODULE$;
		cmd.LOGGER().info("Initialized Command Runner!");
	}
}

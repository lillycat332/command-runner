package lillycat332.command_runner

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.arguments.StringArgumentType.{greedyString, getString}
import com.mojang.brigadier.LiteralMessage
import org.quiltmc.loader.api.ModContainer
import net.minecraft.server.command.CommandManager.*
import net.minecraft.command.CommandBuildContext
import net.minecraft.text.Text
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer
import org.quiltmc.qsl.base.api.event.Event
import org.quiltmc.qsl.command.api
import org.quiltmc.qsl.command.api.CommandRegistrationCallback
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import scala.language.postfixOps
import sys.process.*

object CommandRunner:
  val LOGGER: Logger = LoggerFactory.getLogger("Command Runner")


class CommandRunner extends ModInitializer:
  override def onInitialize(mod: ModContainer): Unit =
    CommandRunner.LOGGER.info("Hello Quilt world from {}!", mod.metadata.name)
    CommandRegistrationCallback.EVENT.register((dispatcher, ctx, env) =>

      val serverCommandExecutor = ctx =>
        val result = runServerCommand(getString(ctx, "command"))
        ctx.getSource.sendFeedback(() => Text.literal("Command ran with code: $result"), false)
        result

      val command = literal("runcmd")
        .requires(source => source.hasPermissionLevel(4))
        .`then`(argument("command", greedyString())
          .executes(serverCommandExecutor)
        )

      dispatcher.register(command)
    )

val runServerCommand = (command: String) =>
  CommandRunner.LOGGER.info("Executing {}", command)
  val result = command !

  if result != 0 then
    CommandRunner.LOGGER.warn("Command {} failed to run! Code: {}", command, result)
  else
    CommandRunner.LOGGER.info("Ran command {}", result)

  result



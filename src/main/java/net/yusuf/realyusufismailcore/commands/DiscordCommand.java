package net.yusuf.realyusufismailcore.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Random;


public class DiscordCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> Discord
                = Commands.literal("discord")
                .requires((commandSource) -> commandSource.hasPermission(1))
                .then(Commands.literal("discord")
                        .executes(commandContext -> sendMessage(commandContext, QuoteSource.DISCORD.getQuote()))
                );
    }

    static int sendMessage(CommandContext<CommandSource> commandContext, String message) throws CommandSyntaxException {
        TranslationTextComponent finalText = new TranslationTextComponent("chat.type.announcement",
                commandContext.getSource().getDisplayName(), new StringTextComponent(message));

        Entity entity = commandContext.getSource().getEntity();
        if (entity != null) {
            commandContext.getSource().getServer().getPlayerList().broadcastMessage(finalText, ChatType.CHAT, entity.getUUID());
            //func_232641_a_ is sendMessage()
        } else {
            commandContext.getSource().getServer().getPlayerList().broadcastMessage(finalText, ChatType.SYSTEM, Util.NIL_UUID);
        }
        return 1;
    }

    enum QuoteSource {
        DISCORD(new String[]{"https://discord.gg/kbVDCfvVKe"});

        public String getQuote() {
            return quotes[new Random().nextInt(quotes.length)];
        }

        QuoteSource(String[] quotes) {
            this.quotes = quotes;
        }

        private String[] quotes;
    }
}


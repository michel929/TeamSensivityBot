package schachAPI.buttons;

import schachAPI.EloEntity;
import schachAPI.Function;
import schachAPI.Schach;
import types.ServerButton;
import mysql.BotInfos;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReloadBoard implements ServerButton {

    @Override
    public void performCommand(ButtonInteractionEvent event) {
        event.editMessageEmbeds(Function.createBoard().build()).queue();
    }
}

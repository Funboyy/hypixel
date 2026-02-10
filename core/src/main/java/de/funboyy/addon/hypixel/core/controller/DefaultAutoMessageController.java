package de.funboyy.addon.hypixel.core.controller;

import de.funboyy.addon.hypixel.api.Hypixel;
import de.funboyy.addon.hypixel.api.chat.ChatRegistry;
import de.funboyy.addon.hypixel.api.chat.filter.AdvancedChatFilter;
import de.funboyy.addon.hypixel.api.chat.filter.SimpleChatFilter;
import de.funboyy.addon.hypixel.api.configuration.AutoMessageConfiguration;
import de.funboyy.addon.hypixel.api.controller.AutoMessageController;
import de.funboyy.addon.hypixel.api.controller.FastPlayController;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import net.labymod.api.Laby;
import net.labymod.api.util.time.TimeUtil;

public class DefaultAutoMessageController implements AutoMessageController {

  // Player Names will be considered valid if they are between 1 and 20 characters long

  private static final Pattern FIRST_KILLER_PATTERN = Pattern.compile(
      "^ +1st Killer(?: -| ?:) (?:\\[[^]]+] )?\\w{1,20}(?: -| ?:) \\d+(?: Kills?)?$");

  private static final Pattern FIRST_PLACE_PATTERN = Pattern.compile(
      "^ *1st Place(?: -| ?:) (?:\\[[^]]+] )?\\w{1,20}(?:(?: -| ?:) .+)?$");

  private static final Pattern FIRST_PATTERN = Pattern.compile(
      "^ *1st(?: -| ?:) (?:\\d+|(?:\\[[^]]+] )?\\w{1,20}(?: \\([^)]+\\) - \\d+ Kills?"
          + "|: \\d*:?\\d{2}:\\d{2}| - \\[LINK]))$");

  private static final Pattern WINNER_PATTERN = Pattern.compile(
      "^ +Winn(?:er(?: -| ?:) (?:Red|Blue|Green|Yellow|RED|BLU|Hiders|Seekers|Defenders|Attackers)(?: Team)?"
          + "|ing Team(?: -| ?:) (?:Red|Blue|Green|Yellow|RED|BLU|Animals|Hunters|Survivors|Vampires))$");

  private static final Pattern WINNING_TEAM_PATTERN = Pattern.compile(
      "^ +(?:Red|Blue|Green|Yellow) Team has won the game!$");

  private static final Pattern ARCADE_CREEPER_ATTACK_PATTERN = Pattern.compile(
      "^ +You survived \\d+ rounds!$");

  private static final Pattern ARCADE_DISASTERS_PATTERN = Pattern.compile(
      "^ +(?:Survivor(?:s \\(\\d+\\))?:|Nobody survived!)$");

  private static final Pattern ARCADE_DROPPER_PATTERN = Pattern.compile(
      "^ +#1 (?:\\[[^]]+] )?\\w{1,20} \\(\\d{2,}:\\d{2}:\\d{3}\\)$");

  private static final Pattern ARCADE_PIXEL_PARTY_PATTERN = Pattern.compile(
      "^ {36}Winners?$");

  private static final Pattern ARCADE_ZOMBIES_PATTERN = Pattern.compile(
      "^ +Zombies - \\d*:?\\d+:\\d+ \\(Round \\d+\\)$");

  private static final Pattern CLASSIC_ARENA_BRAWL_PATTERN = Pattern.compile(
      "^ +[\\d.]+k?/[\\d.]+k? \\w{1,20}$");

  private static final Pattern COPS_AND_CRIMINALS_PATTERN = Pattern.compile(
      "^ +\\w{1,20} won the game!$");

  private static final Pattern MURDER_MYSTERY_PATTERN = Pattern.compile(
      "^ +Winner(?: #1 \\(\\d+ Kills?\\): "
      + "\\w{1,20} \\(\\w{3,16}\\)(?:, \\w{1,20} \\(\\w{3,16}\\))*$|: "
      + "(?:PLAYERS?|MURDER|MURDERERS?|INFECTED|SURVIVORS?)$)");

  private static final Pattern DUELS_PATTERN = Pattern.compile(
      "^ +(?:UHC|SkyWars|Bridge|Bridge CTF|Sumo|Classic|OP|MegaWalls|Bow|NoDebuff|Blitz|Combo|Bow Spleef|Hypixel Parkour|Duel Arena) "
          + "(?:Duel|Doubles|Threes|3v3|4v4|Teams|Deathmatch|2v2v2v2|3v3v3v3)? ?- \\d+:\\d{2}$");

  private static final Pattern WOOL_WARS_PATTERN = Pattern.compile(
      "^ +Most Kills: (?:\\[[^]]+] )?\\w{1,20} - \\d+$");

  private static final String REPEAT_MESSAGE = "You cannot say the same message twice!";

  private static final long GAME_END_MESSAGE_DELAY = 5_000;
  private static final long REPEAT_MESSAGE_DETECTION = 250;

  private final AutoMessageConfiguration configuration;
  private final FastPlayController fastPlayController;
  private final ScheduledExecutorService scheduler;

  private long lastMessage;
  private boolean repeat;

  public DefaultAutoMessageController(final Hypixel hypixel) {
    this.configuration = hypixel.configuration().autoMessageConfiguration();
    this.fastPlayController = hypixel.fastPlayController();
    this.scheduler = Executors.newScheduledThreadPool(1);
    this.lastMessage = 0;
    this.repeat = false;

    final ChatRegistry chatRegistry = hypixel.chatRegistry();
    chatRegistry.register("auto_message_repeat",
        new SimpleChatFilter(REPEAT_MESSAGE, this::handleRepeat));
    chatRegistry.register("auto_message_first_killer",
        new AdvancedChatFilter(FIRST_KILLER_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_first_place",
        new AdvancedChatFilter(FIRST_PLACE_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_first",
        new AdvancedChatFilter(FIRST_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_winner",
        new AdvancedChatFilter(WINNER_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_winning_team",
        new AdvancedChatFilter(WINNING_TEAM_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_arcade_creeper_attack",
        new AdvancedChatFilter(ARCADE_CREEPER_ATTACK_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_arcade_disasters",
        new AdvancedChatFilter(ARCADE_DISASTERS_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_arcade_dropper",
        new AdvancedChatFilter(ARCADE_DROPPER_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_arcade_pixel_party",
        new AdvancedChatFilter(ARCADE_PIXEL_PARTY_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_arcade_zombies",
        new AdvancedChatFilter(ARCADE_ZOMBIES_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_classic_arena_brawl",
        new AdvancedChatFilter(CLASSIC_ARENA_BRAWL_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_cops_and_criminals",
        new AdvancedChatFilter(COPS_AND_CRIMINALS_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_murder_mystery",
        new AdvancedChatFilter(MURDER_MYSTERY_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_duels",
        new AdvancedChatFilter(DUELS_PATTERN, matcher -> this.handleGameEnd()));
    chatRegistry.register("auto_message_wool_wars",
        new AdvancedChatFilter(WOOL_WARS_PATTERN, matcher -> this.handleGameEnd()));
  }

  @Override
  public boolean handleGameEnd() {
    final long currentTimeMillis = TimeUtil.getCurrentTimeMillis();

    if (this.lastMessage + GAME_END_MESSAGE_DELAY > currentTimeMillis) {
      return false;
    }

    if (!this.configuration.enabled().get()) {
      this.fastPlayController.handleGameEnd();
      return false;
    }

    final String text = this.configuration.text().get().trim();

    if (text.isEmpty()) {
      this.fastPlayController.handleGameEnd();
      return false;
    }

    final int delay = this.configuration.delay().get();

    this.lastMessage = currentTimeMillis;
    this.repeat = true;

    if (delay == 0) {
      Laby.references().chatExecutor().chat("/ac " + text, false);
    } else {
      this.scheduler.schedule(() -> Laby.references().chatExecutor()
          .chat("/ac " + text, false), delay, TimeUnit.SECONDS);
    }

    this.scheduler.schedule(this.fastPlayController::handleGameEnd,
        TimeUnit.SECONDS.toMillis(delay) + (REPEAT_MESSAGE_DETECTION * 2), TimeUnit.MILLISECONDS);
    return false;
  }

  @Override
  public boolean handleRepeat() {
    if (!this.repeat) {
      return false;
    }

    if (!this.configuration.enabled().get()) {
      return false;
    }

    final long currentTimeMillis = TimeUtil.getCurrentTimeMillis();

    if (this.lastMessage + REPEAT_MESSAGE_DETECTION < currentTimeMillis) {
      return false;
    }

    this.repeat = false;

    final String text = this.configuration.repeatText().get().trim();

    if (text.isEmpty()) {
      return false;
    }

    this.scheduler.schedule(() -> Laby.references().chatExecutor()
        .chat("/ac " + text, false), 250, TimeUnit.MILLISECONDS);
    return false;
  }

}

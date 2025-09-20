package de.funboyy.addon.hypixel.api.location;

import org.jetbrains.annotations.Nullable;

public enum GameMode implements Mode {

  // Blitz Survival Games
  BLITZ_SOLO_NORMAL(ServerType.SURVIVAL_GAMES, "Solo", "SOLO_NORMAL"),
  BLITZ_TEAMS_NORMAL(ServerType.SURVIVAL_GAMES, "Teams", "TEAMS_NORMAL"),

  // The TNT Games
  TNT_TNTAG(ServerType.TNTGAMES, "TNT Tag", "TNTAG"),
  TNT_TNTRUN(ServerType.TNTGAMES, "TNT Run", "TNTRUN"),
  TNT_CAPTURE(ServerType.TNTGAMES, "Wizards", "CAPTURE"),
  TNT_PVPRUN(ServerType.TNTGAMES, "PVP Run", "PVPRUN"),
  TNT_BOWSPLEEF(ServerType.TNTGAMES, "Bow Spleef", "BOWSPLEEF"),

  // Mega Walls
  MW_STANDARD(ServerType.WALLS3, "Standard", "STANDARD"),
  MW_FACE_OFF(ServerType.WALLS3, "Face Off", "FACE_OFF"),

  // Arcade
  ARCADE_DAY_ONE(ServerType.ARCADE, "Blocking Dead", "DAYONE"),
  ARCADE_BOUNTY_HUNTERS(ServerType.ARCADE, "Bounty Hunters", "ONEINTHEQUIVER"),
  ARCADE_CREEPER_DEFENSE(ServerType.ARCADE, "Creeper Attack", "DEFENDER"),
  ARCADE_DRAGON_WARS(ServerType.ARCADE, "Dragon Wars", "DRANGONWARS2"),
  ARCADE_DROPPER(ServerType.ARCADE, "Dropper", "DROPPER"),
  ARCADE_ENDER_SPLEEF(ServerType.ARCADE, "Ender Spleef", "ENDER"),
  ARCADE_FARM_HUNT(ServerType.ARCADE, "Farm Hunt", "FARM_HUNT"),
  ARCADE_SOCCER(ServerType.ARCADE, "Football", "SOCCER"),
  ARCADE_STARWARS(ServerType.ARCADE, "Galaxy Wars", "STARWARS"),
  ARCADE_HIDE_AND_SEEK_PROP_HUNT(ServerType.ARCADE, "Prop Hunt", "HIDE_AND_SEEK_PROP_HUNT"),
  ARCADE_HIDE_AND_SEEK_PARTY_POOPER(ServerType.ARCADE, "Party Pooper", "HIDE_AND_SEEK_PARTY_POOPER"),
  ARCADE_HOLE_IN_THE_WALL(ServerType.ARCADE, "Hole In The Wall", "HOLE_IN_THE_WALL"),
  ARCADE_SIMON_SAYS(ServerType.ARCADE, "Hypixel Says", "SIMON_SAYS"),
  ARCADE_MINI_WALLS(ServerType.ARCADE, "Mini Walls", "MINI_WALLS"),
  ARCADE_PARTY_GAMES_1(ServerType.ARCADE, "Party Games", "PARTY"),
  ARCADE_PIXEL_PAINTERS(ServerType.ARCADE, "Pixel Painters", "DRAW_THEIR_THING"),
  ARCADE_PIXEL_PARTY(ServerType.ARCADE, "Pixel Party", "PIXEL_PARTY"),
  ARCADE_THROW_OUT(ServerType.ARCADE, "Throw Out", "THROW_OUT"),
  ARCADE_ZOMBIES_DEAD_END(ServerType.ARCADE, "Zombies Dead End", "ZOMBIES_DEAD_END"),
  ARCADE_ZOMBIES_BAD_BLOOD(ServerType.ARCADE, "Zombies Bad Blood", "ZOMBIES_BAD_BLOOD"),
  ARCADE_ZOMBIES_ALIEN_ARCADIUM(ServerType.ARCADE, "Zombies Alien Arcadium", "ZOMBIES_ALIEN_ARCADIUM"),
  ARCADE_ZOMBIES_PRISON(ServerType.ARCADE, "Zombies Prison", "ZOMBIES_PRISON"),
  ARCADE_GRINCH_SIMULATOR_V2(ServerType.ARCADE, "Grinch Simulator", "GRINCH_SIMULATOR_V2"),
  ARCADE_SANTA_SAYS(ServerType.ARCADE, "Santa Says", "SANTA_SAYS"),
  ARCADE_EASTER_SIMULATOR(ServerType.ARCADE, "Easter Simulator", "EASTER_SIMULATOR"),
  ARCADE_HALLOWEEN_SIMULATOR(ServerType.ARCADE, "Halloween Simulator", "HALLOWEEN_SIMULATOR"),
  ARCADE_SCUBA_SIMULATOR(ServerType.ARCADE, "Scuba Simulator", "SCUBA_SIMULATOR"),

  // Cops and Crims
  MCGO_NORMAL(ServerType.MCGO, "Defusal", "NORMAL"),
  MCGO_NORMAL_PARTY(ServerType.MCGO, "Challenge Defusal", "NORMAL_PARTY"),
  MCGO_DEATHMATCH(ServerType.MCGO, "Challenge Team Deathmatch", "DEATHMATCH"),
  MCGO_DEATHMATCH_PARTY(ServerType.MCGO, "Challenge Team Deathmatch", "DEATHMATCH_PARTY"),
  MCGO_GUNGAME(ServerType.MCGO, "Gun Game", "GUNGAME"),

  // UHC Champions
  UHC_SOLO(ServerType.UHC, "Solo", "SOLO"),
  UHC_TEAM(ServerType.UHC, "Team", "TEAM"),
  SPEED_SOLO_NORMAL(ServerType.UHC, ServerType.SPEED_UHC, "Solo Normal", "SOLO_NORMAL"),
  SPEED_TEAM_NORMAL(ServerType.UHC, ServerType.SPEED_UHC, "Team Normal", "TEAM_NORMAL"),

  // Warlords
  WARLORDS_CTF_MINI(ServerType.BATTLEGROUND, "Capture The Flag", "CTF_MINI"),
  WARLORDS_DOMINATION(ServerType.BATTLEGROUND, "Domination", "DOMINATION"),
  WARLORDS_TEAM_DEATHMATCH(ServerType.BATTLEGROUND, "Team Deathmatch", "TEAM_DEATHMATCH"),

  // Smash Heroes
  SUPER_SMASH_SOLO_NORMAL(ServerType.SUPER_SMASH, "Solo", "SOLO_NORMAL"),
  SUPER_SMASH_FRIENDS_NORMAL(ServerType.SUPER_SMASH, "Friends", "FRIENDS_NORMAL"),
  SUPER_SMASH_TEAMS_NORMAL(ServerType.SUPER_SMASH, "Team", "TEAMS_NORMAL"),
  SUPER_SMASH_1V1_NORMAL(ServerType.SUPER_SMASH, "1v1", "1V1_NORMAL"),
  SUPER_SMASH_2V2_NORMAL(ServerType.SUPER_SMASH, "2v2", "2V2_NORMAL"),

  // Housing
  HOUSING(ServerType.HOUSING, "Base", "BASE", false),

  // SkyWars
  MINI_NORMAL(ServerType.SKYWARS, "Mini Normal"),
  SOLO_NORMAL(ServerType.SKYWARS, "Solo Normal"),
  TEAMS_NORMAL(ServerType.SKYWARS, "Doubles Normal"),
  SOLO_INSANE(ServerType.SKYWARS, "Solo Insane"),
  TEAMS_INSANE(ServerType.SKYWARS, "Doubles Insane"),
  MEGA_NORMAL(ServerType.SKYWARS, "Mega"),
  MEGA_DOUBLES(ServerType.SKYWARS, "Mega Doubles"),
  SOLO_INSANE_LUCKY(ServerType.SKYWARS, "Solo Lucky Block"),
  TEAMS_INSANE_LUCKY(ServerType.SKYWARS, "Doubles Lucky Block"),
  //SOLO_INSANE_SLIME(ServerType.SKYWARS, "Solo Slime"),
  //TEAMS_INSANE_SLIME(ServerType.SKYWARS, "Doubles Slime"),
  //SOLO_INSANE_RUSH(ServerType.SKYWARS, "Solo Rush"),
  //TEAMS_INSANE_RUSH(ServerType.SKYWARS, "Doubles Rush"),
  //SOLO_INSANE_TNT_MADNESS(ServerType.SKYWARS, "Solo TNT Madness"),
  //TEAMS_INSANE_TNT_MADNESS(ServerType.SKYWARS, "Doubles TNT Madness"),
  //SOLO_INSANE_HUNTERS_VS_BEASTS(ServerType.SKYWARS, "Solo Hunters vs Beasts"),
  //RANKED_NORMAL(ServerType.SKYWARS, "Ranked"),

  // Classic Games
  PAINTBALL(ServerType.LEGACY, ServerType.PAINTBALL, "Paintball", "NORMAL"),
  TKR(ServerType.LEGACY, ServerType.GINGERBREAD, "Turbo Kart Racers", "NORMAL"),
  VAMPIREZ(ServerType.LEGACY, ServerType.VAMPIREZ, "VampireZ", "NORMAL"),
  WALLS(ServerType.LEGACY, ServerType.WALLS, "The Walls", "NORMAL"),
  QUAKE_SOLO(ServerType.LEGACY, ServerType.QUAKECRAFT, "Solo Quakecraft", "SOLO"),
  QUAKE_TEAMS(ServerType.LEGACY, ServerType.QUAKECRAFT, "Teams Quakecraft", "TEAMS"),
  ARENA_1V1(ServerType.LEGACY, ServerType.ARENA, "1v1 Arena Brawl", "1V1"),
  ARENA_2V2(ServerType.LEGACY, ServerType.ARENA, "2v2 Arena Brawl", "2V2"),
  ARENA_4V4(ServerType.LEGACY, ServerType.ARENA, "4v4 Arena Brawl", "4V4"),

  // Prototype
  SKYBLOCK(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "SkyBlock"),
  PROTOTYPE_DISASTERS(ServerType.PROTOTYPE, "Disasters", "DISASTERS"),

  // Bed Wars
  BEDWARS_EIGHT_ONE(ServerType.BEDWARS, "Solo"),
  BEDWARS_EIGHT_TWO(ServerType.BEDWARS, "Doubles"),
  BEDWARS_FOUR_THREE(ServerType.BEDWARS, "3v3v3v3"),
  BEDWARS_FOUR_FOUR(ServerType.BEDWARS, "4v4v4v4"),
  BEDWARS_TWO_FOUR(ServerType.BEDWARS, "4v4"),
  BEDWARS_PRACTICE(ServerType.BEDWARS, "Practice"),
  BEDWARS_EIGHT_ONE_RUSH(ServerType.BEDWARS, "Rush Solo"),
  BEDWARS_EIGHT_TWO_RUSH(ServerType.BEDWARS, "Rush Doubles"),
  BEDWARS_FOUR_FOUR_RUSH(ServerType.BEDWARS, "Rush 4v4v4v4"),
  BEDWARS_EIGHT_ONE_ULTIMATE(ServerType.BEDWARS, "Ultimate Solo"),
  BEDWARS_EIGHT_TWO_ULTIMATE(ServerType.BEDWARS, "Ultimate Doubles"),
  BEDWARS_FOUR_FOUR_ULTIMATE(ServerType.BEDWARS, "Ultimate 4v4v4v4"),
  BEDWARS_CASTLE(ServerType.BEDWARS, "Castle 40v40"),
  BEDWARS_EIGHT_TWO_VOIDLESS(ServerType.BEDWARS, "Voidless Doubles"),
  BEDWARS_FOUR_FOUR_VOIDLESS(ServerType.BEDWARS, "Voidless 4v4v4v4"),
  BEDWARS_EIGHT_TWO_ARMED(ServerType.BEDWARS, "Armed Doubles"),
  BEDWARS_FOUR_FOUR_ARMED(ServerType.BEDWARS, "Armed 4v4v4v4"),
  BEDWARS_EIGHT_TWO_LUCKY(ServerType.BEDWARS, "Lucky Doubles"),
  BEDWARS_FOUR_FOUR_LUCKY(ServerType.BEDWARS, "Lucky 4v4v4v4"),
  BEDWARS_EIGHT_TWO_SWAP(ServerType.BEDWARS, "Swappage Doubles"),
  BEDWARS_FOUR_FOUR_SWAP(ServerType.BEDWARS, "Swappage 4v4v4v4"),
  BEDWARS_EIGHT_TWO_UNDERWORLD(ServerType.BEDWARS, "Underworld Doubles"),
  BEDWARS_FOUR_FOUR_UNDERWORLD(ServerType.BEDWARS, "Underworld 4v4v4v4"),
  BEDWARS_EIGHT_ONE_ONEBLOCK(ServerType.BEDWARS, "One Block"),
  // this is theoretically a duel, but server type is 'BEDWARS' and it is in both lobbies
  BEDWARS_TWO_ONE_DUELS(ServerType.BEDWARS, "Bed Wars Duel"),
  BEDWARS_TWO_ONE_DUELS_RUSH(ServerType.BEDWARS, "Bed Wars Rush Duel"),

  // Murder Mystery
  MURDER_CLASSIC(ServerType.MURDER_MYSTERY, "Classic"),
  MURDER_DOUBLE_UP(ServerType.MURDER_MYSTERY, "Double Up"),
  MURDER_ASSASSINS(ServerType.MURDER_MYSTERY, "Assassins"),
  MURDER_INFECTION(ServerType.MURDER_MYSTERY, "Infection"),

  // Build Battle
  BUILD_BATTLE_SOLO_NORMAL(ServerType.BUILD_BATTLE, "Solo"),
  BUILD_BATTLE_TEAMS_NORMAL(ServerType.BUILD_BATTLE, "Teams"),
  BUILD_BATTLE_SOLO_PRO(ServerType.BUILD_BATTLE, "Pro"),
  BUILD_BATTLE_GUESS_THE_BUILD(ServerType.BUILD_BATTLE, "Guess The Build"),
  BUILD_BATTLE_SPEED_BUILDERS(ServerType.BUILD_BATTLE, "Speed Builders"),

  // Duels
  DUELS_CLASSIC_DUEL(ServerType.DUELS, "Classic Duel"),
  DUELS_CLASSIC_DOUBLES(ServerType.DUELS, "Classic Doubles"),
  DUELS_BOW_DUEL(ServerType.DUELS, "Bow Duel"),
  DUELS_BOXING_DUEL(ServerType.DUELS, "Boxing Duel"),
  DUELS_COMBO_DUEL(ServerType.DUELS, "Combo Duel"),
  DUELS_SUMO_DUEL(ServerType.DUELS, "Sumo Duel"),
  DUELS_POTION_DUEL(ServerType.DUELS, "NoDebuff Duel"),
  DUELS_PARKOUR_EIGHT(ServerType.DUELS, "Hypixel Parkour"),
  DUELS_DUEL_ARENA(ServerType.DUELS, "Duel Arena"),
  DUELS_BLITZ_DUEL(ServerType.DUELS, "Blitz Duel"),
  DUELS_BOWSPLEEF_DUEL(ServerType.DUELS, "Bow Spleef Duel"),
  DUELS_SPLEEF_DUEL(ServerType.DUELS, "Spleef Duel"),
  DUELS_BRIDGE_DUEL(ServerType.DUELS, "Bridge Duel"),
  DUELS_BRIDGE_DOUBLES(ServerType.DUELS, "Bridge Doubles"),
  DUELS_BRIDGE_THREES(ServerType.DUELS, "Bridge 3v3"),
  DUELS_BRIDGE_FOUR(ServerType.DUELS, "Bridge Teams"),
  DUELS_BRIDGE_2V2V2V2(ServerType.DUELS, "Bridge 2v2v2v2"),
  DUELS_BRIDGE_3V3V3V3(ServerType.DUELS, "Bridge 3v3v3v3"),
  DUELS_CAPTURE_THREES(ServerType.DUELS, "Bridge CTF Threes"),
  DUELS_MW_DUEL(ServerType.DUELS, "MegaWalls Duel"),
  DUELS_MW_DOUBLES(ServerType.DUELS, "MegaWalls Doubles"),
  DUELS_OP_DUEL(ServerType.DUELS, "OP Duel"),
  DUELS_OP_DOUBLES(ServerType.DUELS, "OP Doubles"),
  DUELS_SW_DUEL(ServerType.DUELS, "SkyWars Duel"),
  DUELS_SW_DOUBLES(ServerType.DUELS, "SkyWars Doubles"),
  DUELS_UHC_DUEL(ServerType.DUELS, "UHC Duel"),
  DUELS_UHC_DOUBLES(ServerType.DUELS, "UHC Doubles"),
  DUELS_UHC_FOUR(ServerType.DUELS, "UHC Teams"),
  DUELS_UHC_MEETUP(ServerType.DUELS, "UHC Deathmatch"),
  DUELS_QUAKE_DUEL(ServerType.DUELS, "Quakecraft Duel"),

  // SkyBlock
  SKYBLOCK_HUB(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Hub", "HUB", false),
  SKYBLOCK_ISLAND(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Private Island", "DYNAMIC", false),
  SKYBLOCK_DARK_AUCTION(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Dark Auction", "DARK_AUCTION", false),
  SKYBLOCK_JERRY_WORKSHOP(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Jerry's Workshop", "WINTER", false),
  SKYBLOCK_GARDEN(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "The Garden", "GARDEN", false),
  SKYBLOCK_RIFT(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "The Rift", "RIFT", false),
  SKYBLOCK_FARMING_ISLAND(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "The Farming Islands", "FARMING_1", false),
  SKYBLOCK_BACKWATER_BAYOU(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Backwater Bayou", "FISHING_1", false),
  SKYBLOCK_PARK(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "The Park", "FORAGING_1", false),
  SKYBLOCK_GALATEA(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Galatea", "FORAGING_2", false),
  SKYBLOCK_GOLD_MINE(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Gold Mine", "MINING_1", false),
  SKYBLOCK_DEEP_CAVERNS(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Deep Caverns", "MINING_2", false),
  SKYBLOCK_DWARVEN_MINES(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Dwarven Mines", "MINING_3", false),
  SKYBLOCK_CRYSTAL_HOLLOWS(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Crystal Hollows", "CRYSTAL_HOLLOWS", false),
  SKYBLOCK_GALACITE_MINESHAFT(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Glacite Mineshafts", "MINESHAFT", false),
  SKYBLOCK_SPIDER_DEN(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Spider's Den", "COMBAT_1", false),
  SKYBLOCK_BLAZING_FORTRESS(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Blazing Fortress", "COMBAT_2", false),
  SKYBLOCK_END(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "The End", "COMBAT_3", false),
  SKYBLOCK_CRIMSON_ISLE(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Crimson Isle", "CRIMSON_ISLE", false),
  SKYBLOCK_KUDDRA_HOLLOW(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Kuudra's Hollow", "KUUDRA", false),
  SKYBLOCK_KUDDRA_HOLLOW_LEGACY(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Kuudra's Hollow", "INSTANCED", false),
  SKYBLOCK_DUNGEON_HUB(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Dungeon Hub", "DUNGEON_HUB", false),
  SKYBLOCK_DUNGEON(ServerType.PROTOTYPE, ServerType.SKYBLOCK, "Dungeons", "DUNGEON", false),

  // Pit
  PIT(ServerType.PIT, "Pit"),

  // Replay
  REPLAY(ServerType.REPLAY, "Base", "BASE", false),

  // Wool Games
  WOOL_SHEEP_WARS_TWO_SIX(ServerType.WOOL_GAMES, "Sheep Wars", "SHEEP_WARS_TWO_SIX"),
  WOOL_WOOL_WARS_TWO_FOUR(ServerType.WOOL_GAMES, "Wool Wars", "WOOL_WARS_TWO_FOUR"),
  WOOL_CAPTURE_THE_WOOL_TWO_TWENTY(ServerType.WOOL_GAMES, "Capture The Wool", "CAPTURE_THE_WOOL_TWO_TWENTY");

  private final ServerType lobbyType;
  private final ServerType serverType;
  private final String name;
  private final String modeName;
  private final boolean queueable;

  GameMode(final ServerType lobbyType, final ServerType serverType, final String name, final String modeName, final boolean queueable) {
    this.lobbyType = lobbyType;
    this.serverType = serverType;
    this.name = name;
    this.modeName = modeName;
    this.queueable = queueable;
  }

  GameMode(final ServerType type, final String name, final String modeName, final boolean queueable) {
    this.lobbyType = type;
    this.serverType = type;
    this.name = name;
    this.modeName = modeName;
    this.queueable = queueable;
  }

  GameMode(final ServerType lobbyType, final ServerType serverType, final String name, final String modeName) {
    this.lobbyType = lobbyType;
    this.serverType = serverType;
    this.name = name;
    this.modeName = modeName;
    this.queueable = true;
  }

  GameMode(final ServerType type, final String name, final String modeName) {
    this.lobbyType = type;
    this.serverType = type;
    this.name = name;
    this.modeName = modeName;
    this.queueable = true;
  }

  GameMode(final ServerType lobbyType, final ServerType serverType, final String name) {
    this.lobbyType = lobbyType;
    this.serverType = serverType;
    this.name = name;
    this.modeName = super.name();
    this.queueable = true;
  }

  GameMode(final ServerType type, final String name) {
    this.lobbyType = type;
    this.serverType = type;
    this.name = name;
    this.modeName = super.name();
    this.queueable = true;
  }

  @Override
  public ServerType lobbyType() {
    return this.lobbyType;
  }

  public ServerType gameType() {
    return this.serverType;
  }

  @Override
  public String displayName() {
    return this.name;
  }

  public String modeName() {
    return this.modeName;
  }

  @Override
  public boolean queueable() {
    return this.queueable;
  }

  @Nullable
  public static GameMode fromName(final String name) {
    if (name == null) {
      return null;
    }

    for (final GameMode mode : values()) {
      if (mode.displayName().equals(name)) {
        return mode;
      }
    }

    return null;
  }

}

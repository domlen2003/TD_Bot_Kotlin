package security;

public enum DiscordRank {
  OP(773649381445599232L),
  BOT(773649384159182868L),
  TEAM(806517496702173196L),
  VIP(806517497725452338L),
  THE_NATION(806517498513457162L),
  UNVERIFIED(697220825132171275L);

  private final long ID;


  DiscordRank(long ID) {
    this.ID=ID;
  }

  /**
   * find a rank by name
   *
   * @param name the rank to search for
   * @return rank || null
   */
  public static DiscordRank findRank(String name) {
    for (DiscordRank discordRank : values()) {
      if (discordRank.name().equalsIgnoreCase(name)) return discordRank;
    }
    return null;
  }

  /**
   * find a rank by id
   *
   * @param id the rank to search for
   * @return rank || null
   */
  public static DiscordRank findRank(long id) {
    for (DiscordRank discordRank : values()) {
      if (discordRank.ID==id) return discordRank;
    }
    return null;
  }

  public boolean isAtLeast(DiscordRank rank) {
    return this.ordinal() <= rank.ordinal();
  }

}

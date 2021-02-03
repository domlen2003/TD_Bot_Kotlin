package security;

public enum DiscordRank {
  OP(645019781053022228L),
  BOT(695789952839057490L),
  TEAM(747185518827012156L),
  VIP(721076804210786366L),
  THE_NATION(721076810120429610L),
  UNVERIFIED(432905467753136128L);

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

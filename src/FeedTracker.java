public class FeedTracker extends Tracker {

  /**
   * ratio between increments and decrements
   */
  private static final double INC_DEC_RATIO = 33.0;

  /**
   * from ideal, number of increments required to kill
   */
  private static final double DEADLY_INCS = 3.0;

  /**
   * from ideal, number of decrements required to kill
   */
  private static final double DEADLY_DECS = 66.0;

  // derived values
  private static final double DECREMENT =
      (100 / DEADLY_INCS) / (INC_DEC_RATIO + DEADLY_DECS / DEADLY_INCS);
  private static final double INCREMENT = DECREMENT * INC_DEC_RATIO;
  private static final double IDEAL = DECREMENT * DEADLY_DECS;

  private static final Tracker.Levels LEVELS = new Tracker.Levels(
      40.0,
      20.0 / 33.0, 8.0,
      20.0, 8.0
  );

  private static final Tracker.Zone[] ZONES =
      {
          new Tracker.Zone(
              "+X", 100.0, false, true,
              new String[]{
                  "It has burnt to a crisp. ",
                  "The leaves are all shrivelled up and burnt. "
              }
          ),
          new Tracker.Zone(
              "+2", (2.0 / 3) * (100 - IDEAL) + IDEAL,
              false, false,
              new String[]{
                  "Some leaves near the top are shrivelled up. " +
                  "The tips of some others are yellowing. "
              }
          ),
          new Tracker.Zone(
              "+1", (5.0 / 12) * (100 - IDEAL) + IDEAL,
              true, false,
              new String[]{
                  "Some of the top-most leaves are looking a little yellow. " +
                  "The tips of them are also a little burnt. "
              }
          ),
          new Tracker.Zone(
              "±0", (3.0 / 4) * IDEAL,
              true, false,
              new String[]{
                  "The leaves are a nice deep green. ",
                  "The leaves look so healthy! "
              }
          ),
          new Tracker.Zone(
              "-1", (1.0 / 4) * IDEAL,
              true, false,
              new String[]{
                  "Some of the bottom leaves are turning yellow. ",
                  "There's some yellowing on the lower leaves. "
              }
          ),
          new Tracker.Zone(
              "-2", 0.0, false, false,
              new String[]{
                  "Many leaves are yellow. " +
                  "Some of them are drying up and falling off. "
              }
          ),
          new Tracker.Zone(
              "-X", -1.0, false, true,
              new String[]{
                  "The leaves have all brown and crinkly. ",
                  "The leaves are falling off. "
              }
          )
      };

  FeedTracker() {
    super(LEVELS, ZONES);
  }

  @Override
  public synchronized String toString() {
    return super.toString("Feed ");
  }

  @Override
  public synchronized String getLevelCode() {
    return super.getLevelCode("f");
  }

}

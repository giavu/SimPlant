class WaterTracker extends Tracker {

  /**
   * ratio between increments and decrements
   */
  private static final double INC_DEC_RATIO = 8.0;

  /**
   * from ideal, number of increments required to kill
   */
  private static final double DEADLY_INCS = 3.0;

  /**
   * from ideal, number of decrements required to kill
   */
  private static final double DEADLY_DECS = 16.0;

  // derived values
  private static final double DECREMENT =
      (100 / DEADLY_INCS) / (INC_DEC_RATIO + DEADLY_DECS / DEADLY_INCS);
  private static final double INCREMENT = DECREMENT * INC_DEC_RATIO;
  private static final double IDEAL = DECREMENT * DEADLY_DECS;


  private static final Tracker.Levels LEVELS = new Tracker.Levels(
      IDEAL,
      DECREMENT, 8.0,
      INCREMENT, 8.0
  );

  private static final Tracker.Zone[] ZONES =
      {
          // IDEAL + 4 applications  (minimum for zone)
          new Tracker.Zone(
              "+X", 100.0, false, true,
              new String[]{
                  "It has drowned. ",
                  "Your plant got so wet it developed a gnarly " +
                  "fungus that took over everything. ",
                  "There is so much water it suffocated. "
              }
          ),
          // IDEAL + 2 applications  (minimum for zone)
          new Tracker.Zone(
              "+2", (2.0 / 3) * (100 - IDEAL) + IDEAL,
              false, false,
              new String[]{
                  "The leaves are drooping quite badly. ",
                  "It's wilting quite a bit. "
              }
          ),
          // IDEAL + 1.25 applications  (minimum for zone)
          new Tracker.Zone(
              "+1", (5.0 / 12) * (100 - IDEAL) + IDEAL,
              true, false,
              new String[]

                  {
                      "The leaves are a little bit limp. ",
                      "It looks a little sad. "
                  }
          ),
          // IDEAL ZONE
          new Tracker.Zone(
              "±0", (3.0 / 4) * IDEAL,
              true, false,
              new String[]

                  {
                      "The leaves are perfectly perky. "
                  }
          ),
          // IDEAL + 8 days  (maximum for zone)
          new Tracker.Zone(
              "-1", (1.0 / 4) * IDEAL,
              true, false,
              new String[]

                  {
                      "The leaves are a little bit limp. "
                  }
          ),
          // IDEAL + 12 days  (maximum for zone)
          new Tracker.Zone(
              "-2", 0.0, false, false,
              new String[]

                  {
                      "The leaves are drooping quite badly. "
                  }
          ),
          // IDEAL + 16 days  (maximum for zone)
          new Tracker.Zone(
              "-X", -1.0, false, true,
              new String[]

                  {
                      "There is nothing left of it but a dried-out husk. "
                  }
          )
      };

  WaterTracker() {
    super(LEVELS, ZONES);
  }

  @Override
  public synchronized String toString() {
    return super.toString("Water ");
  }

  @Override
  public synchronized String getLevelCode() {
    return super.getLevelCode("w");
  }

}

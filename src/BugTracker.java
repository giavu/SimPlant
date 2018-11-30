import java.util.Random;

public class BugTracker extends Tracker {

  private static final Tracker.Levels LEVELS = new Tracker.Levels(
      0.0,
      0.0, 0.0, // not used
      0.0, 0.0  // not used
  );

  private static final Tracker.Zone[] ZONES =
      {
          // must be in order from largest to smallest
          // minimum number, healthy, deadly, array of status messages
          new Tracker.Zone("+X", 100.0, false, true,
                           new String[]{
                               "It has turned into a plant skelton. ",
                               "The bugs ate it all up. "
                           }
          ),
          new Tracker.Zone("+4", 80.0, false, false,
                           new String[]{
                               "The bugs are sending scouting parties to find new plants to eat. ",
                               "There's not much plant left. "
                           }
          ),
          new Tracker.Zone("+3", 65.0, false, false,
                           new String[]{
                               "The bugs are starting to take over. ",
                               "The bugs are making themselves at home. "
                           }
          ),
          new Tracker.Zone("+2", 30.0, false, false,
                           new String[]{
                               "It's getting a buggy. "
                           }
          ),
          new Tracker.Zone("+1", 10, true, false,
                           new String[]{
                               "What's a few bugs between friends? "
                           }
          ),
          new Tracker.Zone("+0", -1.0, true, false,
                           new String[]{
                               null
                           }
          )
      };

  private static final int MIN_SPRAY_LEVEL = 10;
  private static final int MAX_SPRAY_LEVEL = 50;
  private static final int SPRAY_AMOUNT = 10;
  private static final int STARTING_SPRAY_AMOUNT = 20;
  private static final int MAX_MULTIPLIER = 2;
  private static final double PROBABILITY_OF_NEW_BUGS = 0.10;
  private static final double PROBABILITY_OF_MORE_BUGS = 0.15;

  private int spray;
  private Random random = new Random();

  BugTracker() {
    super(LEVELS, ZONES);
    spray = STARTING_SPRAY_AMOUNT;
  }

  private synchronized boolean hasBugs() {
    return getLevel() > 0;
  }

  // decrement spray-level every day,
  // calls bugs if spray level is low
  @Override
  public synchronized void step() {
    spray -= 2;
    if ( spray < 0 )
      spray = 0;
    if ( !hasBugs() ) {
      if ( spray < MIN_SPRAY_LEVEL )
        bugCaller();
    } else {
      bugMultiplier();
    }
  }

  // apply pesticides
  @Override
  public synchronized void apply() {
    spray += SPRAY_AMOUNT;
    if ( spray > MAX_SPRAY_LEVEL )
      spray = MAX_SPRAY_LEVEL;
    // kill bugs
    bugKiller();
  }

  // if spray level is below a certain point bugs might appear
  private void bugCaller() {
    if ( random.nextDouble() < PROBABILITY_OF_NEW_BUGS )
      setLevel(1.0); // will multiply quickly
  }

  // should only be called weekly, once a week? - put in random chance
  private void bugMultiplier() {
    if ( random.nextDouble() < PROBABILITY_OF_MORE_BUGS )
      setLevel(getLevel() * (random.nextDouble() * MAX_MULTIPLIER + 1));
  }

  // kill some bugs (if there are any)
  private void bugKiller() {
    if ( hasBugs() )
      setLevel(getLevel() * random.nextDouble()); // maybe add a multiplier constant?
    if ( getLevel() < 1 )
      setLevel(0);
  }

  @Override
  public synchronized String toString() {
    return super.toString("Bug ") + String.format(", Spray level: %02d", spray);
  }

  @Override
  public synchronized String getLevelCode() {
    return super.getLevelCode("p") + String.format("s%02d", spray);
  }

}
package uci.mswe.swe244p.ex35_bank_account;

import java.util.concurrent.atomic.*;

/**
 * DynamicOrderDeadlock
 * <p/>
 * Dynamic lock-ordering deadlock
 *
 * @author Brian Goetz and Tim Peierls
 * @author Crista Lopes
 */
public class BankAccounts {

  /**
   * The method transferMoney already has commented code that tries to fix the race conditions.
   * Uncomment that code, compile and run the program again. This time, you should run into another
   * problem: the dreadful deadlocks, which, again, may or may not happen. You may need to run the
   * program several times. When a deadlock happens, the program will block, and won't ever exit.
   *
   * Page 129 - 10.1.2. Dynamic Lock Order Deadlocks
   *
   * Sometimes it is not obvious that you have sufficient control over lock ordering to prevent
   * deadlocks. Consider the harmlesslooking code in Listing 10.2 that transfers funds from one
   * account to another. It acquires the locks on both Account objects before executing the
   * transfer, ensuring that the balances are updated atomically and without violating invariants
   * such as "an account cannot have a negative balance".
   */
  public static void transferMoney(Account fromAccount, Account toAccount, DollarAmount amount)
      throws InsufficientFundsException {
    // synchronized (fromAccount) {
    // synchronized (toAccount) {
    if (fromAccount.getBalance().compareTo(amount) < 0)
      throw new InsufficientFundsException();
    else {
      fromAccount.debit(amount);
      toAccount.credit(amount);
    }
    // }
    // }
  }

  static class DollarAmount implements Comparable<DollarAmount> {
    // Needs implementation
    private int value;

    public DollarAmount(int amount) {
      value = amount;
    }

    public int get() {
      return value;
    }

    public DollarAmount add(DollarAmount d) {
      return new DollarAmount(value + d.get());
    }

    public DollarAmount subtract(DollarAmount d) {
      return new DollarAmount(value - d.get());
    }

    public int compareTo(DollarAmount d) {
      if (value < d.get())
        return -1;
      else if (value > d.get())
        return 1;
      return 0;
    }

    public String toString() {
      return String.valueOf(value);
    }
  }

  static class Account {
    private DollarAmount balance;
    private final int accountNumber;
    private static final AtomicInteger sequence = new AtomicInteger();

    public Account() {
      accountNumber = sequence.incrementAndGet();
      balance = new DollarAmount(100);
    }

    // ! read then write
    void debit(DollarAmount d) throws InsufficientFundsException {
      DollarAmount balanceAfter = balance.subtract(d);
      if (balanceAfter.get() < 0) {
        throw new InsufficientFundsException();
      } else {
        balance = balanceAfter;
      }
    }

    // ! read then write
    void credit(DollarAmount d) {
      balance = balance.add(d);
    }

    DollarAmount getBalance() {
      return balance;
    }

    int getAccountNumber() {
      return accountNumber;
    }
  }

  static class InsufficientFundsException extends Exception {
    private static final long serialVersionUID = 1L;
  }

  static class Teller implements Runnable {
    private int id;
    private Account from;
    private Account to;
    private boolean running;

    private Account firstAccountToLock;
    private Account SecondAccountToLock;
    private Object tieLock = null;

    public Teller(int id, Account from, Account to) {
      this.id = id;
      this.from = from;
      this.to = to;
      this.running = true;

      // So the strategy here is that, always lock the account with a smaller hash first
      var fromHash = System.identityHashCode(from);
      var toHash = System.identityHashCode(to);
      if (fromHash < toHash) {
        firstAccountToLock = from;
        SecondAccountToLock = to;
      } else if (fromHash > toHash) {
        firstAccountToLock = to;
        SecondAccountToLock = from;
      } else {
        this.tieLock = new Object();
        firstAccountToLock = from;
        SecondAccountToLock = to;
      }
    }

    public void run() {
      while (running) {

        try {
          Thread.sleep(RandomUtils.randomInteger());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        DollarAmount amount = new DollarAmount(RandomUtils.randomInteger());

        try {
          // multiple threads may transferMoney at the same time
          // transferMoney(from, to, amount);

          /**
           * Page 130
           *
           * Deadlocks like this one can be spotted the same way as in Listing 10.1 look for nested
           * lock acquisitions. Since the order of arguments is out of our control, to fix the
           * problem we must **induce an ordering on the locks and acquire them according to the
           * induced ordering consistently throughout the application**. One way to induce an
           * ordering on objects is to use System.identityHashCode, which returns the value that
           * would be returned by Object.hashCode. Listing 10.3 shows a version of transferMoney
           * that uses System.identityHashCode to induce a lock ordering. It involves a few extra
           * lines of code, but eliminates the possibility of deadlock. In the rare case that two
           * objects have the same hash code, we must use an arbitrary means of ordering the lock
           * acquisitions, and this reintroduces the possibility of deadlock. To prevent
           * inconsistent lock ordering in this case, a third "tie breaking" lock is used. By
           * acquiring the tiebreaking lock before acquiring either Account lock, we ensure that
           * only one thread at a time performs the risky task of acquiring two locks in an
           * arbitrary order, eliminating the possibility of deadlock (so long as this mechanism is
           * used consistently). If hash collisions were common, this technique might become a
           * concurrency bottleneck (just as having a single, programwide lock would), but because
           * hash collisions with System.identityHashCode are vanishingly infrequent, this technique
           * provides that last bit of safety at little cost.
           */

          if (tieLock == null) {
            // If the hashCodes are different
            synchronized (firstAccountToLock) {
              synchronized (SecondAccountToLock) {
                transferMoney(from, to, amount);
              }
            }
          } else {
            // If the hashCodes are the same
            synchronized (tieLock) {
              synchronized (firstAccountToLock) {
                synchronized (SecondAccountToLock) {
                  transferMoney(from, to, amount);
                }
              }
            }
          }

          RandomUtils.print("Transferred " + amount + ", total: " + to.getBalance(), id);
        } catch (InsufficientFundsException e) {
          RandomUtils.print("Insufficient funds", id);
        }

      }
    }

    public void stop() {
      running = false;
    }
  }

  private static void nap(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    var acc1 = new Account();
    var acc2 = new Account();

    var b1 = acc1.getBalance();
    var b2 = acc2.getBalance();
    System.out.println("Acc1:" + b1 + " Acc2:" + b2 + " Total:" + b1.add(b2));
    System.out.println("--------------------------------");

    nap(1000);

    // One teller one way
    // from acc1 to acc2
    var t1 = new Teller(0, acc1, acc2);

    // Two tellers the other way
    // from acc2 to acc1
    var t2 = new Teller(1, acc2, acc1);
    var t3 = new Teller(2, acc2, acc1);

    // start transferring money
    new Thread(t1).start();
    new Thread(t2).start();
    new Thread(t3).start();

    // let the teller threads run
    nap(10000);

    t1.stop();
    t2.stop();
    t3.stop();

    nap(1000);

    System.out.println("--------------------------------");
    b1 = acc1.getBalance();
    b2 = acc2.getBalance();
    System.out.println("Acc1:" + b1 + " Acc2:" + b2 + " Total:" + b1.add(b2));
  }
}

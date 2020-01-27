package misc;

import java.util.ArrayList;
import java.util.List;

/**
 * Code Review - Inventory
 */
public class Inventory {

  private class Item {
    int identifier;

    boolean isQuestItem() {
      return true;
    }
  }

  private static class AchievementSystem {
    final static AchievementSystem instance = new AchievementSystem();

    public void didModifyItem(String s, int id, int quantity) {
    }
  }

  private List<Item> items = new ArrayList<Item>(); // in our current game design, each item is either a quest item or a normal item
  private List<Item> questItems = new ArrayList<Item>(); // for convenience, a subset of items, containing only quest items
  private List<Item> normalItems = new ArrayList<Item>(); // for convenience, a subset of items, containing only normal items

  // the user interface will display the last item we picked up
  private Item lastItemCollected;

  public Item getLastItemCollected() {
    return lastItemCollected;
  }

  public void setLastItemCollected(Item item) {
    lastItemCollected = item;
  }

  public void didPickUpItem(Item item) {
    setLastItemCollected(item);
  }

  public void getItem(Item item, int quantity) {
    for (int x = 0; x < quantity; ++x) {
      items.add(item);
      if (item.isQuestItem()) {
        questItems.add(item);
      } else {
        normalItems.add(item);
      }
    }

    AchievementSystem.instance.didModifyItem("gain", item.identifier, quantity);
    didPickUpItem(item);
  }

  public void loseItem(Item item, int quantity) {
    for (int x = 0; x < quantity; ++x) {
      if (items.remove(item)) {
        if (item.isQuestItem()) {
          questItems.remove(item);
        } else {
          normalItems.remove(item);
        }
      } else {
        System.err.println("loseItem: item not found in items");
        return;
      }
    }

    AchievementSystem.instance.didModifyItem("lose", item.identifier, quantity);
  }

}
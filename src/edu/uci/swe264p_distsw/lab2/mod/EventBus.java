package edu.uci.swe264p_distsw.lab2.mod;

/**
 * @(#)EventBus.java
 *
 * Copyright: Copyright (c) 2003 Carnegie Mellon University
 *
 */

import java.util.Observable;
import java.util.Observer;

/**
 * This class provides a mechanism for subscribing to and announcing events. Events must be defined
 * as integer codes before use. Internally, <code>Observer</code>/<code>Observable</code> classes
 * are used for implementation, which means event passing between two components only works within
 * single address space (for now). Note that none of the components are typed as an <code>Observable</code>
 * object. This ensures that the existence of other components is completely hidden to a component
 * and it's safe to subscribe to an event when there is no component that can announce the event.
 */
@SuppressWarnings("deprecation")
public class EventBus {

  /**
   * This class wraps the <code>Observable</code> class. It provides a way to set the changed flag
   * of an <code>Observable</code> object from outside.
   */
  public static class Event extends Observable {

    /**
     * Set the changed flag of this event.
     */
    public void setChanged() {
      super.setChanged();
    }
  }

  /**
   * Show event definition.
   */
  public static final int EV_SHOW = 0;

  /**
   * Command event #1 definition: list all students.
   */
  public static final int EV_LIST_ALL_STUDENTS = 1;

  /**
   * Command event #2 definition: list all courses.
   */
  public static final int EV_LIST_ALL_COURSES = 2;

  /**
   * Command event #3 definition: list students who registered for a course.
   */
  public static final int EV_LIST_STUDENTS_REGISTERED = 3;

  /**
   * Command event #4 definition: list courses a student has registered for.
   */
  public static final int EV_LIST_COURSES_REGISTERED = 4;

  /**
   * Command event #5 definition: list courses a student has completed.
   */
  public static final int EV_LIST_COURSES_COMPLETED = 5;

  /**
   * Command event #6 definition: register a student for a course.
   */
  public static final int EV_REGISTER_STUDENT = 6;

  /**
   * The number of defined events.
   */
  public static final int MAX_NUM_EVENTS = 100;

  /**
   * An array containing event objects for all defined events.
   */
  protected static Event[] aEvent = new Event[MAX_NUM_EVENTS];

  /**
   * Initialize the event bus. Existing subscriptions (event/receiver mappings) are cleared.
   */
  public static void initialize() {
    for (int i = 0; i < MAX_NUM_EVENTS; i++) {
      aEvent[i] = new Event();
    }
  }

  /**
   * Subscribe to an event. By calling this method, the <code>update</code> method of
   * <code>objSubscriber</code>, which is an <code>Observer</code> object, will be invoked
   * whenever an <code>iEventCode</code> event is announced.
   *
   * @param iEventCode event to subscribe to
   * @param objSubscriber subscriber whose <code>update</code> method is to be invoked
   */
  public static void subscribeTo(int iEventCode, Observer objSubscriber) {
    aEvent[iEventCode].addObserver(objSubscriber);
  }

  /**
   * Announce an event. As a result, the <code>update</code> method of all <code>Observer</code>
   * objects that subscribed to <code>iEventCode</code> event are invoked. The order of
   * invocation is arbitrary when there are multiple subscribers.
   *
   * @param iEventCode event to announce
   * @param sEventParam event parameter as a string
   */
  public static void announce(int iEventCode, String sEventParam) {
    aEvent[iEventCode].setChanged();
    aEvent[iEventCode].notifyObservers(sEventParam);
  }
}

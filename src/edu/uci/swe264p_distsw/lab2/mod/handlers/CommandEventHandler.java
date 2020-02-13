package edu.uci.swe264p_distsw.lab2.mod.handlers;

/**
 * @(#)CommandEventHandler.java
 *
 * Copyright: Copyright (c) 2003,2004 Carnegie Mellon University
 *
 */

import java.util.Observable;
import java.util.Observer;

import edu.uci.swe264p_distsw.lab2.mod.EventBus;
import edu.uci.swe264p_distsw.lab2.mod.database.*;

/**
 * This class is a superclass for command event handler classes. Subclasses only need to
 * define the <code>execute</code> method for command processing if they handle
 * one event and generate one output event.
 */
@SuppressWarnings("deprecation")
abstract public class CommandEventHandler implements Observer {

  /**
   * Reference to the database object.
   */
  protected DataBase objDataBase;

  /**
   * Output event code to send the command processing result.
   */
  protected int iOutputEvCode;

  /**
   * Constructs a command event handler. At the time of creation, it subscribes to the given
   * command event by default.
   *
   * @param objDataBase reference to the database object
   * @param iCommandEvCode command event code to receive the commands to process
   * @param iOutputEvCode output event code to send the command processing result
   */
  public CommandEventHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
    // Subscribe to command event.
    EventBus.subscribeTo(iCommandEvCode, this);

    // Remember the database reference and output event name.
    this.objDataBase = objDataBase;
    this.iOutputEvCode = iOutputEvCode;
  }

  /**
   * Process the received command events. The processing result is announced as an event
   * along with a string message.
   *
   * @param event an event object. (caution: not to be directly referenced)
   * @param param a parameter object of the event. (to be cast to appropriate data type)
   */
  public void update(Observable event, Object param) {
    // Announce a new output event with the execution result.
    EventBus.announce(this.iOutputEvCode, this.execute((String) param));
  }

  /**
   * The command processing routine. To be customized by inheriting classes.
   *
   * @param param a string parameter for command
   * @return a string result of command processing
   */
  abstract protected String execute(String param);
}
package uci.mswe.swe264p_distsw.lab1.templates;

/******************************************************************************************************************
 * File:SourceFilterTemplate.java Project: Assignment 1 Copyright: Copyright (c) 2003 Carnegie
 * Mellon University Versions: 1.0 November 2008 - Initial rewrite of original assignment 1 (ajl).
 *
 * Description:
 *
 * This class serves as a template for creating source filters. The details of threading,
 * connections writing output are contained in the FilterFramework super class. In order to use this
 * template the program should rename the class. The template includes the run() method which is
 * executed when the filter is started. The run() method is the guts of the filter and is where the
 * programmer should put their filter specific code.The run() method is the main read-write loop for
 * reading data from some source and writing to the output port of the filter. This template assumes
 * that the filter is a source filter that reads data from a file, device (sensor),or generates the
 * data interally, and then writes data to its output port. In this case, only the output port is
 * used. In cases where the filter is a standard filter or a sink filter, you should use the
 * FilterTemplate.java or SinkFilterTemplate.java as a starting point for creating standard or sink
 * filters.
 *
 * Parameters: None
 *
 * Internal Methods:
 *
 * public void run() - this method must be overridden by this class.
 *
 ******************************************************************************************************************/

public class SourceFilterTemplate extends FilterFramework {
  public void run() {

    byte databyte = 0;

    /*************************************************************
     * This is the main processing loop for the filter. Since this is a source filter, the programer
     * will have to determine when the loop ends.
     **************************************************************/

    while (true) {

      /*************************************************************
       * The programer can insert code for the filter operations here to include reading the data
       * from some device or file. Note that regardless how the data is read, data must be sent one
       * byte at a time out the output pipe. This has been done to adhere to the pipe and filter
       * paradigm and provide a high degree of portabilty between filters. However, you must convert
       * input data to byte type on your own. The following line of code will write a byte of data
       * out the filter's output port. If you break from the loop you should call ClosePorts() to
       * close the filter ports in an orderly way. This is shown below, but commented out. Where you
       * close the ports will depend upon how you terminate the loop.
       **************************************************************/

      WriteFilterOutputPort(databyte);

    } // while

    /*
     * ClosePorts();
     */

  } // run

} // SourceFilterTemplate

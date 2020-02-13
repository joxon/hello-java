package edu.uci.swe264p_distsw.lab1.system_a;

/******************************************************************************************************************
* File:Plumber.java
* Project: Lab 1
* Copyright:
*   Copyright (c) 2020 University of California, Irvine
*   Copyright (c) 2003 Carnegie Mellon University
* Versions:
*   1.1 January 2020 - Revision for SWE 264P: Distributed Software Architecture, Winter 2020, UC Irvine.
*   1.0 November 2008 - Sample Pipe and Filter code (ajl).
*
* Description:
* This class serves as an example to illstrate how to use the PlumberTemplate to create a main thread that
* instantiates and connects a set of filters. This example consists of three filters: a source, a middle filter
* that acts as a pass-through filter (it does nothing to the data), and a sink filter which illustrates all kinds
* of useful things that you can do with the input stream of data.
* Parameters: None
* Internal Methods:	None
******************************************************************************************************************/

public class Plumber {
  public static void main(String argv[]) {

    // Here we instantiate three filters.
    SourceFilter src = new SourceFilter();
    MiddleFilter mid = new MiddleFilter();
    SinkFilter snk = new SinkFilter();

    /****************************************************************************
    * Here we connect the filters starting with the sink filter (Filter 3) which
    * we connect to Filter2 the middle filter. Then we connect Filter2 to the
    * source filter (Filter1).
    ****************************************************************************/

    snk.connect(mid); // This esstially says, "connect Filter3 input port to Filter2 output port
    mid.connect(src); // This esstially says, "connect Filter2 input port to Filter1 output port

    // Here we start the filters up.
    src.start();
    mid.start();
    snk.start();
  }
}
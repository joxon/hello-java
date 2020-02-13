package edu.uci.swe264p_distsw.lab1.templates;

/******************************************************************************************************************
 * File:FilterTemplate.java Project: Assignment 1 Copyright: Copyright (c) 2003 Carnegie Mellon
 * University Versions: 1.0 November 2008 - Initial rewrite of original assignment 1 (ajl).
 *
 * Description:
 *
 * This class serves as a template for creating filters. The details of threading, filter
 * connections, input, and output are contained in the FilterFramework super class. In order to use
 * this template the program should rename the class. The template includes the run() method which
 * is executed when the filter is started. The run() method is the guts of the filter and is where
 * the programmer should put their filter specific code. In the template there is a main read-write
 * loop for reading from the input port of the filter and writing to the output port of the filter.
 * This template assumes that the filter is a "normal" that it both reads and writes data. That is
 * both the input and output ports are used - its input port is connected to a pipe from an
 * up-stream filter and its output port is connected to a pipe to a down-stream filter. In cases
 * where the filter is a source or sink, you should use the SourceFilterTemplate.java or
 * SinkFilterTemplate.java as a starting point for creating source or sink filters.
 *
 * Parameters: None
 *
 * Internal Methods:
 *
 * public void run() - this method must be overridden by this class.
 *
 ******************************************************************************************************************/

public class FilterTemplate extends FilterFramework
{
	public void run()
    {
		byte databyte;

		while (true)
		{

/***************************************************************
*	The program can insert code for the filter operations
* 	here. Note that data must be received and sent one
* 	byte at a time. This has been done to adhere to the
* 	pipe and filter paradigm and provide a high degree of
* 	portabilty between filters. However, you must reconstruct
* 	data on your own. First we read a byte from the input
* 	stream...
***************************************************************/

			try
			{
				databyte = ReadFilterInputPort();

/***************************************************************
*	Here we could insert code to operate on the input stream...
*  	Then we write a byte out to the output port.
***************************************************************/

           		WriteFilterOutputPort(databyte);

			} // try

/***************************************************************
*	When we reach the end of the input stream, an exception is
* 	thrown which is shown below. At this point, you should
* 	finish up any processing, close your ports and exit.
***************************************************************/

			catch (EndOfStreamException e)
			{
				ClosePorts();
				break;

			} // catch

		} // while

   } // run

} // FilterTemplate
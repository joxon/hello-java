package uci.mswe.swe264p_distsw.lab1.system_a;

import uci.mswe.swe264p_distsw.lab1.FilterFramework;

/******************************************************************************************************************
* File:SourceFilter.java
* Project: Lab 1
* Copyright:
*   Copyright (c) 2020 University of California, Irvine
*   Copyright (c) 2003 Carnegie Mellon University
* Versions:
*   1.1 January 2020 - Revision for SWE 264P: Distributed Software Architecture, Winter 2020, UC Irvine.
*   1.0 November 2008 - Sample Pipe and Filter code (ajl).
*
* Description:
* This class serves as an example for how to use the SourceFilterTemplate to create a source filter. This particular
* filter is a source filter that reads some input from the FlightData.dat file and writes the bytes up stream.
* Parameters: 		None
* Internal Methods: None
******************************************************************************************************************/

import java.io.*;

public class SourceFilter extends FilterFramework {

  public void run() {
    String fileName = "data/swe264p_lab1/FlightData.dat"; // Input data file.
    DataInputStream in = null; // File stream reference.

    int readCount = 0; // Number of bytes read from the input file.
    int writeCount = 0; // Number of bytes written to the stream.
    byte dataByte = 0; // The byte of data read from the file

    try {
      // Here we open the file and write a message to the terminal.
      in = new DataInputStream(new FileInputStream(fileName));
      System.out.println(this.getName() + "::Source reading...");

      /***********************************************************************************
      *	Here we read the data from the file and send it out the filter's output port one
      * 	byte at a time. The loop stops when it encounters an EOFException.
      ***********************************************************************************/
      while (true) {
        dataByte = in.readByte();
        ++readCount;

        writeFilterOutputPort(dataByte);
        ++writeCount;
      }
    }
    /***********************************************************************************
    *	The following exception is raised when we hit the end of input file. Once we
    * 	reach this point, we close the input file, close the filter ports and exit.
    ***********************************************************************************/
    catch (EOFException eoferr) {
      System.out.println(this.getName() + "::End of file reached...");
      try {
        in.close();
        closePorts();
        System.out.println(
            this.getName() + "::Read file complete; bytes read:" + readCount + "; bytes written: " + writeCount);
      }
      /***********************************************************************************
      *	The following exception is raised should we have a problem closing the file.
      ***********************************************************************************/
      catch (Exception closeerr) {
        System.out.println(this.getName() + "::Problem closing input data file::" + closeerr);
      }
    }
    /***********************************************************************************
    *	The following exception is raised should we have a problem opening the file.
    ***********************************************************************************/
    catch (IOException iox) {
      System.out.println(this.getName() + "::Problem reading input data file::" + iox);
    }
  } // run
} // SourceFilter
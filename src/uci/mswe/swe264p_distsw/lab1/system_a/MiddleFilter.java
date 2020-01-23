package uci.mswe.swe264p_distsw.lab1.system_a;

/******************************************************************************************************************
* File:MiddleFilter.java
* Project: Lab 1
* Copyright:
*   Copyright (c) 2020 University of California, Irvine
*   Copyright (c) 2003 Carnegie Mellon University
* Versions:
*   1.1 January 2020 - Revision for SWE 264P: Distributed Software Architecture, Winter 2020, UC Irvine.
*   1.0 November 2008 - Sample Pipe and Filter code (ajl).
*
* Description:
* This class serves as an example for how to use the FilterRemplate to create a standard filter. This particular
* example is a simple "pass-through" filter that reads data from the filter's input port and writes data out the
* filter's output port.
* Parameters: None
* Internal Methods: None
******************************************************************************************************************/

public class MiddleFilter extends FilterFramework {
  public void run() {
    int readCount = 0; // Number of bytes read from the input file.
    int writeCount = 0; // Number of bytes written to the stream.
    byte dataByte = 0; // The byte of data read from the file

    // Next we write a message to the terminal to let the world know we are alive...
    System.out.println(this.getName() + "::Middle Reading... ");

    while (true) {
      // Here we read a byte and write a byte
      try {
        dataByte = readFilterInputPort();
        ++readCount;

        writeFilterOutputPort(dataByte);
        ++writeCount;
      } catch (EndOfStreamException e) {
        closePorts();
        System.out
            .println(this.getName() + "::Middle Exiting; bytes read: " + readCount + "; bytes written: " + writeCount);
        break;
      }
    }
  }
}
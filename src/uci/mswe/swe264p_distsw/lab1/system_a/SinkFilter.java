package uci.mswe.swe264p_distsw.lab1.system_a;

/******************************************************************************************************************
* File:SinkFilter.java
* Project: Lab 1
* Copyright:
*   Copyright (c) 2020 University of California, Irvine
*   Copyright (c) 2003 Carnegie Mellon University
* Versions:
*   1.1 January 2020 - Revision for SWE 264P: Distributed Software Architecture, Winter 2020, UC Irvine.
*   1.0 November 2008 - Sample Pipe and Filter code (ajl).
*
* Description:
* This class serves as an example for using the SinkFilterTemplate for creating a sink filter. This particular
* filter reads some input from the filter's input port and does the following:
*	1) It parses the input stream and "decommutates" the measurement ID
*	2) It parses the input steam for measurments and "decommutates" measurements, storing the bits in a long word.
* This filter illustrates how to convert the byte stream data from the upstream filterinto useable data found in
* the stream: namely time (long type) and measurements (double type).
* Parameters: None
* Internal Methods: None
******************************************************************************************************************/

import java.util.*; // This class is used to interpret time words
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat; // This class is used to format and write time in a string format.

public class SinkFilter extends FilterFramework {

  public void run() {

    final int ID_TIME = 0;
    final int ID_VELOCITY = 1;
    final int ID_ALTITUDE = 2;
    final int ID_PRESSURE = 3;
    final int ID_TEMPERATURE = 4;

    double velocity = 0;
    double altitude = 0;
    double pressure = 0;
    double temperature = 0;

    final PrintWriter out = createPrintWriter("data/swe264p_lab1/OutputA.csv");
    out.println("Time,Velocity,Altitude,Pressure,Temperature");

    /************************************************************************************
    *	TimeStamp is used to compute time using java.util's Calendar class.
    * 	TimeStampFormat is used to format the time value so that it can be easily printed
    *	to the terminal.
    *************************************************************************************/
    Calendar timestamp = Calendar.getInstance();
    SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy:dd:hh:mm:ss");

    final int ID_LENGTH = 4; // This is the length of IDs in the byte stream
    int id; // This is the measurement id

    final int MEASUREMENT_LENGTH = 8; // This is the length of all measurements (including time) in bytes
    long measurement; // This is the word used to store all measurements - conversions are illustrated.

    byte dataByte = 0; // This is the data byte read from the stream
    int readCount = 0; // This is the number of bytes read from the stream

    // First we announce to the world that we are alive...
    System.out.println(this.getName() + "::Sink Reading...");

    while (true) {
      try {
        /***************************************************************************
        // We know that the first data coming to this filter is going to be an ID and
        // that it is IdLength long. So we first get the ID bytes.
        ****************************************************************************/
        id = 0;
        for (int i = 0; i < ID_LENGTH; i++) {
          dataByte = readFilterInputPort(); // This is where we read the byte from the stream...
          id = id | (dataByte & 0xFF); // We append the byte on to ID...
          // 0xff == 1111 1111
          // dataByte ==     ?
          // &
          // ==================
          //         1111 111?
          // id ==   ???? ????
          // |
          // ==================
          //         ???? ????

          if (i != ID_LENGTH - 1) {
            // If this is not the last byte, then slide the
            // previously appended byte to the left by one byte
            id = id << 8; // to make room for the next byte we append to the ID
          }
          readCount++; // Increment the byte count
        }

        /****************************************************************************
        // Here we read measurements. All measurement data is read as a stream of bytes
        // and stored as a long value. This permits us to do bitwise manipulation that
        // is neccesary to convert the byte stream into data words. Note that bitwise
        // manipulation is not permitted on any kind of floating point types in Java.
        // If the id = 0 then this is a time value and is therefore a long value - no
        // problem. However, if the id is something other than 0, then the bits in the
        // long value is really of type double and we need to convert the value using
        // Double.longBitsToDouble(long val) to do the conversion which is illustrated below.
        *****************************************************************************/
        measurement = 0;
        for (int i = 0; i < MEASUREMENT_LENGTH; i++) {
          dataByte = readFilterInputPort();

          measurement = measurement | (dataByte & 0xFF); // We append the byte on to measurement...

          if (i != MEASUREMENT_LENGTH - 1) {
            // If this is not the last byte, then slide the
            // previously appended byte to the left by one byte
            measurement = measurement << 8;
            // to make room for the next byte we append to the
            // measurement
          }
          readCount++; // Increment the byte count
        }

        switch (id) {
        /****************************************************************************
        // Here we look for an ID of 0 which indicates this is a time measurement.
        // Every frame begins with an ID of 0, followed by a time stamp which correlates
        // to the time that each proceeding measurement was recorded. Time is stored
        // in milliseconds since Epoch. This allows us to use Java's calendar class to
        // retrieve time and also use text format classes to format the output into
        // a form humans can read. So this provides great flexibility in terms of
        // dealing with time arithmetically or for string display purposes. This is
        // illustrated below.
        ****************************************************************************/
        case ID_TIME:
          timestamp.setTimeInMillis(measurement);
          break;

        case ID_VELOCITY:
          velocity = Double.longBitsToDouble(measurement);
          break;

        case ID_ALTITUDE:
          altitude = Double.longBitsToDouble(measurement);
          break;

        case ID_PRESSURE:
          pressure = Double.longBitsToDouble(measurement);
          break;

        /****************************************************************************
        // Here we pick up a measurement (ID = 4 in this case), but you can pick up
        // any measurement you want to. All measurements in the stream are
        // captured by this class. Note that all data measurements are double types
        // This illustrates how to convert the bits read from the stream into a double
        // type. Its pretty simple using Double.longBitsToDouble(long value). So here
        // we print the time stamp and the data associated with the ID we are interested in.
        ****************************************************************************/
        case ID_TEMPERATURE:
          temperature = Double.longBitsToDouble(measurement);

          String sTime = timestampFormat.format(timestamp.getTime());
          String sVelo = String.format("%3.5f", velocity);
          String sAlti = String.format("%3.5f", altitude);
          String sPres = String.format("%3.5f", pressure);
          String sTemp = String.format("%3.5f", temperature);

          out.println(String.join(",", sTime, sVelo, sAlti, sPres, sTemp));

          break;

        default:
          System.err.println(this.getName() + ":: Unknown measurement id=" + id);
          break;
        }
      }
      /*******************************************************************************
      *	The EndOfStreamExeception below is thrown when you reach end of the input
      *	stream. At this point, the filter ports are closed and a message is
      *	written letting the user know what is going on.
      ********************************************************************************/
      catch (EndOfStreamException e) {
        out.close();
        closePorts();
        System.out.println(this.getName() + "::Sink Exiting; bytes read: " + readCount);
        break;
      }
    } // while
  } // run

  private PrintWriter createPrintWriter(String filePath) {
    var fout = new File(filePath);
    // Java does not create folder for us
    fout.getParentFile().mkdirs();
    try {
      if (fout.createNewFile()) {
        System.out.println(this.getName() + "::" + filePath + " is created!");
      } else {
        System.out.println(this.getName() + "::" + filePath + " already exists.");
      }
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    try {
      return new PrintWriter(new FileWriter(fout));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
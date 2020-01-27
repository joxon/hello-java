package uci.mswe.swe264p_distsw.lab1.system_b;

import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import uci.mswe.swe264p_distsw.lab1.FilterFramework;
import static uci.mswe.swe264p_distsw.lab1.Utils.createPrintWriter;

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

  final int ID_TIME = 0;
  final int ID_VELOCITY = 1;
  final int ID_ALTITUDE = 2;
  final int ID_PRESSURE = 3;
  final int ID_TEMPERATURE = 4;

  Calendar timestamp = Calendar.getInstance();
  SimpleDateFormat timestampFormat = new SimpleDateFormat("YYYY:MM:dd:HH:mm:ss:SSS");

  double velocity = 0;

  final double ALTITUDE_BASE = 10000.0;
  final double ALTITUDE_WILD_JUMP = 100.0;
  double altitude = 0;
  double altitudeLast = ALTITUDE_BASE;
  double altitudeLastLast = ALTITUDE_BASE;
  int altitudeCount = 0;
  boolean isWildJump = false;
  int wildJumpCount = 0;

  double pressure = 0;

  double temperature = 0;

  int readCount = 0; // Number of bytes read from the input file.
  int writeCount = 0; // Number of bytes written to the stream.
  byte dataByte = 0; // The byte of data read from the file

  final int ID_LENGTH = 4; // This is the length of IDs in the byte stream
  int id; // This is the measurement id

  final int MEASUREMENT_LENGTH = 8; // This is the length of all measurements (including time) in bytes
  long measurement; // This is the word used to store all measurements - conversions are illustrated.

  public void run() {

    final PrintWriter csv = createPrintWriter("data/swe264p_lab1/WildPoints.csv");
    csv.println("Time,Velocity,Altitude,Pressure,Temperature");

    outPrintln("Middle Reading... ");

    while (true) {
      try {
        id = 0;
        for (int i = 0; i < ID_LENGTH; i++) {
          dataByte = readFilterInputPort();
          writeFilterOutputPort();

          id = id | (dataByte & 0xFF);
          if (i != ID_LENGTH - 1) {
            id = id << 8;
          }
          readCount++;
        }

        switch (id) {
        case ID_TIME:
          timestamp.setTimeInMillis(getMeasurementAndSend());
          break;

        case ID_VELOCITY:
          velocity = Double.longBitsToDouble(getMeasurementAndSend());
          break;

        // !
        case ID_ALTITUDE:
          ++altitudeCount;
          if (altitudeCount == 1) {
            //
            // Reading the first record
            // Just read and send as normal
            //
            altitude = Double.longBitsToDouble(getMeasurementAndSend());
            altitudeLast = altitude;
          } else if (altitudeCount >= 2) {
            //
            // Reading records after
            // Process Wild jumps before sending
            //
            altitude = Double.longBitsToDouble(getMeasurementNoSend());

            //
            // If ir is a wild jump
            //
            if (Math.abs(altitude - altitudeLast) >= ALTITUDE_WILD_JUMP) {
              outPrintln("Wild jump " + (++wildJumpCount) + " row ID = " + (altitudeCount + 1));
              isWildJump = true;
              //
              // Update the altitude
              //
              // System.out.print(">= ALTITUDE_BASE: old:" + altitude);
              altitude = (altitudeLast + altitudeLastLast) / 2;
              // System.out.println("; new:" + altitude);
            } else {
              // System.out.println("< ALTITUDE_BASE: old:" + altitude);
              isWildJump = false;
            }

            //
            // Send the updated value to sink filter
            //
            ByteBuffer altitudeByteBuffer = ByteBuffer.allocate(Long.BYTES);
            altitudeByteBuffer.putLong(Double.doubleToLongBits(altitude));
            byte[] altitudeBytes = altitudeByteBuffer.array();
            for (int i = 0; i < MEASUREMENT_LENGTH; i++) {
              dataByte = altitudeBytes[i];
              writeFilterOutputPort();
            }

            //
            // Prepare for next reading
            //
            altitudeLastLast = altitudeLast;
            altitudeLast = altitude;
          }
          break;
        // !

        case ID_PRESSURE:
          pressure = Double.longBitsToDouble(getMeasurementAndSend());
          break;

        case ID_TEMPERATURE:
          temperature = Double.longBitsToDouble(getMeasurementAndSend());

          if (isWildJump) {
            String sTime = timestampFormat.format(timestamp.getTime());
            String sVelo = String.format("%3.5f", velocity);
            String sAlti = String.format("%3.5f", altitude);
            String sPres = String.format("%3.5f", pressure);
            String sTemp = String.format("%3.5f", temperature);

            csv.println(String.join(",", sTime, sVelo, sAlti, sPres, sTemp));
          }

          break;

        default:
          errPrintln("Unknown measurement id=" + id);
          break;
        }

      } catch (EndOfStreamException e) {
        closePorts();
        outPrintln("Middle Exiting; bytes read: " + readCount + "; bytes written: " + writeCount);
        break;
      }
    }

  }

  private long getMeasurementNoSend() {
    measurement = 0;

    try {
      for (int i = 0; i < MEASUREMENT_LENGTH; i++) {
        dataByte = readFilterInputPort();
        // writeFilterOutputPort();

        measurement = measurement | (dataByte & 0xFF);
        if (i != MEASUREMENT_LENGTH - 1) {
          measurement = measurement << 8;
        }
        readCount++;
      }
    } catch (EndOfStreamException e) {
      errPrintln("getMeasurementNoSend(): EndOfStreamException");
    }

    return measurement;
  }

  private long getMeasurementAndSend() {
    measurement = 0;

    try {
      for (int i = 0; i < MEASUREMENT_LENGTH; i++) {
        dataByte = readFilterInputPort();
        writeFilterOutputPort();

        measurement = measurement | (dataByte & 0xFF);
        if (i != MEASUREMENT_LENGTH - 1) {
          measurement = measurement << 8;
        }
        readCount++;
      }
    } catch (EndOfStreamException e) {
      errPrintln("getMeasurementAndSend(): EndOfStreamException");
    }

    return measurement;
  }

  private void writeFilterOutputPort() {
    writeFilterOutputPort(dataByte);
    ++writeCount;
  }

  public byte[] longToBytes(long x) {
    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
    buffer.putLong(x);
    return buffer.array();
  }
}
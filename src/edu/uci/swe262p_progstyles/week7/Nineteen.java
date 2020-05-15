package edu.uci.swe262p_progstyles.week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Junxian Chen on 2020-05-14.
 */
public class Nineteen {
    public static void main(String[] args) throws IOException {
        final var DISABLED = true;
        if (DISABLED) {
            System.out.println("Please go to 'nineteen/bin' and use 'java -jar framework.jar pride-and-prejudice.txt'");
        } else {
            var PATH = "/home/runner/swe262p-exercises/exercise7/nineteen/bin/";
            var CMD = String.format("java -jar %sframework.jar %spride-and-prejudice.txt", PATH, PATH);
            var process = Runtime.getRuntime().exec(new String[]{"cd", PATH, "&&", CMD});

            BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            lineReader.lines().forEach(System.out::println);

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            errorReader.lines().forEach(System.out::println);
        }
    }
}


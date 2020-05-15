package edu.uci.swe262p_progstyles.week7.nineteen.src.framework;

import java.util.List;
import java.util.Map;

/**
 * Created by Junxian Chen on 2020-05-14.
 */
public interface ITermFreqApp {
    List<String> extractWords(String pathString);

    List<Map.Entry<String, Integer>> top25(List<String> words);
}

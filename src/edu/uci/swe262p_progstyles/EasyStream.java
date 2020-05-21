package edu.uci.swe262p_progstyles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Junxian Chen on 2020-05-21.
 */
public class EasyStream {

    public static final String STOP_WORDS = "a,able,about,across,after,all,almost,also,am,among,an,and,any,are,as,at,be,because,been,but,by,can,cannot,could,dear,did,do,does,either,else,ever,every,for,from,get,got,had,has,have,he,her,hers,him,his,how,however,i,if,in,into,is,it,its,just,least,let,like,likely,may,me,might,most,must,my,neither,no,nor,not,of,off,often,on,only,or,other,our,own,rather,said,say,says,she,should,since,so,some,than,that,the,their,them,then,there,these,they,this,tis,to,too,twas,us,wants,was,we,were,what,when,where,which,while,who,whom,why,will,with,would,yet,you,your";
    public static final Set<String> STOP_WORD_SET = Arrays.stream(STOP_WORDS.split(",")).collect(Collectors.toUnmodifiableSet());

    public static void main(String[] args) throws IOException {
        // load text file into lines
        Files.lines(Path.of(args[0]))
                // split line to words: Stream<String>
                .flatMap(line -> Arrays.stream(line.split("[^a-zA-Z]+"))
                        // normalize words
                        .map(String::toLowerCase)
                        // ignore single letters
                        .filter(word -> word.length() > 1)
                        // ignore stop words
                        .filter(word -> !STOP_WORD_SET.contains(word)))
                // wordFreqMap: Map<String, Integer>
                .collect(Collectors.toMap(word -> word, count -> 1, Integer::sum))
                // Set<Map.Entry<String, Integer>>
                .entrySet()
                // Stream<Map.Entry<String, Integer>>
                .stream()
                // sort and print top 25
                .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                .limit(25)
                .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));
    }
}

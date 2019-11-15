import collections
import re  # regular expression
import sys  # system
import time
import os  # operating system

if __name__ == "__main__":
    data_dir = "data/in/244p-ex42/"
    stop_words = set(open(data_dir + "stop_words").read().split(","))
    """
    https://docs.python.org/3/library/timeit.html

    ! time.clock()
        Deprecated since version 3.3: The behavior of this function depends on the platform:
        ! use perf_counter() or process_time() instead,
        depending on your requirements, to have a well-defined behavior.

    ! time.perf_counter()
        Return the value (in fractional seconds) of a performance counter,
        i.e. a clock with the highest available resolution to measure a short duration.
        It does include time elapsed during sleep and is system-wide.

    ! time.process_time()
        Return the value (in fractional seconds) of the sum of
        the system and user CPU time of the current process.
        It does not include time elapsed during sleep.
    """
    start_time = time.perf_counter_ns()

    txt_files = [
        os.path.join(data_dir, filename) for filename in os.listdir(data_dir)
        if filename.endswith(".txt")
    ]
    all_words = list()
    for file in txt_files:
        print("Started reading", file)
        # append(object):   length += 1
        # extend(iterable): length += len(iterable)
        all_words.extend(re.findall("\w{3,}", open(file).read().lower()))
        print("Ended reading", file)

    counts = collections.Counter(word for word in all_words
                                 if word not in stop_words)
    print("---------- Word counts (top 40) -----------")
    i = 1
    for (word, count) in counts.most_common(40):
        print("[" + str(i) + "]", '"' + word + "\":", count)
        i += 1

    end_time = time.perf_counter_ns()
    elapsed_time = end_time - start_time
    print("MTFC Elapsed time:", elapsed_time / 1e6, "ns")

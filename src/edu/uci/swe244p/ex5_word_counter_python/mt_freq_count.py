import collections
import re  # regular expression
import sys  # system
import time
import os  # operating system
from os.path import abspath, dirname
# ! ThreadPoolExecutor is NOT in concurrent.futures
from concurrent.futures.thread import ThreadPoolExecutor
import threading


def read_file_task(path, file_words):
    tname = threading.currentThread().getName()
    print(tname + ":", "Started reading", path)
    file_words.extend(re.findall("\w{3,}", open(path).read().lower()))
    print(tname + ":", "Ended reading", path)


if __name__ == "__main__":
    data_dir = dirname(abspath(__file__)) + "\\data\\"
    stop_words = set(open(data_dir + "stop_words").read().split(","))

    start_time = time.perf_counter_ns()

    txt_file_paths = [
        os.path.join(data_dir, filename) for filename in os.listdir(data_dir)
        if filename.endswith(".txt")
    ]
    all_file_words = list()
    all_words = list()

    # ! https://docs.python.org/3/library/concurrent.futures.html
    with ThreadPoolExecutor(max_workers=4) as executor:
        for path in txt_file_paths:
            file_words = list()
            all_file_words.append(file_words)
            executor.submit(read_file_task, path, file_words)
        executor.shutdown(wait=True)

    for words in all_file_words:
        all_words.extend(words)

    counts = collections.Counter(word for word in all_words
                                 if word not in stop_words)
    print("---------- Word counts (top 40) -----------")
    i = 1
    for (word, count) in counts.most_common(40):
        print("[" + str(i) + "]", '"' + word + "\":", count)
        i += 1

    end_time = time.perf_counter_ns()
    elapsed_time = end_time - start_time
    print("MTFC Elapsed time:", elapsed_time / 1e6, "ms")

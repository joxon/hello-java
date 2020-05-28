# https://github.com/crista/exercises-in-programming-style/blob/master/03-arrays/tf-03.py
import sys

import numpy as np

leet_dict = {
    "A": "4",  # A: 4, /-\, /_\, @, /\, Д
    "B": "ß",  # B: 8,|3, 13, |}, |:, |8, 18, 6, |B, |8, lo, |o, j3, ß,
    "C": "¢",  # C: <, {, [, (, ©, ¢,
    "D": "D",  # D:|), |}, |], |>,
    "E": "£",  # E: 3, £, ₤, €
    "F": "F",  # F: |=, ph, |#, |"
    "G": "6",  # G: [, -, [+, 6, C-,
    "H": "#",  # H: #, 4, |-|, [-], {-}, }-{, }{, |=|, [=], {=}, /-/, (-), )-(, :-:, I+I,
    "I": "1",  # I: 1, |, !, 9
    "J": "J",  # J: _|, _/, _7, _), _], _}
    "K": "K",  # K: |<, 1<, l<, |{, l{
    "L": "L",  # L: |_, |, 1, ][
    "M": "M",  # M: 44, |\/|, ^^, /\/\, /X\, []\/][, []V[], ][\\//][, (V),//., .\\, N\,
    "N": "И",  # N: |\|, /\/, /V, ][\\][, И
    "O": "0",  # O: 0, (), [], {}, <>, Ø, oh,
    "P": "P",  # P: |o, |O, |>, |*, |°, |D, /o, []D, |7
    "Q": "Q",  # Q: O_, 9, (,), 0,kw,
    "R": "Я",  # R: |2, 12, .-, |^, l2, Я, ®
    "S": "$",  # S: 5, $, §,
    "T": "7",  # T: 7, +, 7`, '|' , `|` , ~|~ , -|-, '][',
    "U": "U",  # U: |_|, \_\, /_/, \_/, (_), [_], {_}
    "V": "V",  # V: \/
    "W": "W",  # W: \/\/, (/\), \^/, |/\|, \X/, \\', '//, VV, \_|_/, \\//\\//, Ш, 2u, \V/,
    "X": "%",  # X: %, *, ><, }{, )(, Ж,
    "Y": "¥",  # Y: `/, ¥, \|/, Ч,
    "Z": "2",  # Z: 2, 5, 7_, >_,(/),
}

# Load raw characters
characters = np.array([' '] + list(open(sys.argv[1]).read()) + [' '])

# Replace non-alphabets with spaces
characters[~np.char.isalpha(characters)] = ' '

# To uppercase
characters = np.char.upper(characters)

# To leet letters: vectorize a scalar function first and apply it to the array
characters = np.vectorize(lambda ch: leet_dict.get(ch) or ' ')(characters)

# Split the words by finding the indices of spaces
space_indices = np.where(characters == ' ')

# A little trick: let's double each index, and then take pairs
repeated_indices = np.repeat(space_indices, 2)

# Get the pairs as a 2D matrix, skip the first and the last
word_ranges = np.reshape(repeated_indices[1:-1], (-1, 2))

# Remove the indexing to the spaces themselves
# space_ranges[:, 1]: the index of next space
# space_ranges[:, 0]: the index of previus space
# space_ranges[:, 1] - space_ranges[:, 0] = len(word) + 1
# Remove single letter word:
# len(word) > 1
# len(word) + 1 > 2
# space_ranges[:, 1] - space_ranges[:, 0] > 2
word_ranges = word_ranges[np.where(word_ranges[:, 1] - word_ranges[:, 0] > 2)]

# Voila! Words are in between spaces, given as pairs of indices
# range[0] = the index of starting space
# range[1] = the index of the last character of the word
# characters[range[0]:range[1]] = a sublist of characters
# map: ranges -> characters of a word
word_chars = list(map(lambda ranges: characters[ranges[0]:ranges[1]], word_ranges))

# Let's recode the characters as strings
word_strings = np.array(list(map(lambda w: ''.join(w).strip(), word_chars)))

# Similar to the little trick above: double each word, and then take pairs
repeated_word_strings = np.repeat(word_strings, 2)

# repeated_word_strings[1:-1]: skip the first and the last
two_grams = np.reshape(repeated_word_strings[1:-1], (-1, 2))

# Let's recode the two words as a two-gram
two_gram_strings = np.array(list(map(lambda two_words: ' '.join(two_words), two_grams)))

# Finally, count the two gram occurrences
unique_two_grams, counts = np.unique(two_gram_strings, axis=0, return_counts=True)
sorted_list = sorted(zip(unique_two_grams, counts), key=lambda pair: pair[1], reverse=True)
for two_gram, count in sorted_list[:5]:
    print(two_gram, '-', count)

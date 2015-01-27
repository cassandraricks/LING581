This code is used to predict the ranking of spelling errors of first names.  The problem is explained here with "Britney Spears" as an example:
http://www.google.com/jobs/archive/britney.html

How to run:
===============

*run argument1 argument2 ...*

Arguments:
----------------
The code takes multiple arguments:

- argument1: String (put in double quotes) the target name that has been misspelled (ex. "britney")
- arguments2+: String (put in double quotes) each misspelling that you'd like to rank with a space between each (ex. "britni" "brittany" "britteny")

Theoretical approach:
=================

There are many different reasons why someone may end up misspelling a name, but they aren't all equal in terms of "level of error."  Below is a list of the types of errors ranked from "smallest error" (*i.e.* the first item is the most understandable error):

- person uses an acceptable alternative spelling of the name (*e.g.* "Brittany" instead of "Britney")
- person transposes two letters (*e.g.* "Brintey") requiring a switch
- person forgets a letter (*e.g.* "Britey") or adds an extra letter (*e.g.* "Britnbey") requiring an insertion or deletion
- person uses a wrong letter (*e.g.* "Britmey") requiring a deletion

This implementation incorporates a means of measuring the first item (alternative spellings) and a simple algorithm for calculating Levenshtein distance.

Alternative Spelling Implementation:
---------------------------------
If a person uses an acceptable alternative spelling, a distance score of 0.5 is given, guaranteeing that this misspelling will be higher in rank than all other misspellings.  In order to identify acceptable alternative spellings, a codec for Soundex is used.

Soundex is often used by genealogists to collapse multiple spellings of the same name (whether it be first or last) for cataloging purposes.  There are four steps to coding into Soundex (from http://docs.oracle.com/cd/B19306_01/server.102/b14200/functions148.htm)

    1. Retain the first letter of the string and remove all other occurrences of the following letters: a, e, h, i, o, u, w, y.

    2. Assign numbers to the remaining letters (after the first) as follows:

        b, f, p, v = 1
        c, g, j, k, q, s, x, z = 2
        d, t = 3
        l = 4
        m, n = 5
        r = 6

    3. If two or more letters with the same number were adjacent in the original name (before step 1), or adjacent except for any               intervening h and w, then omit all but the first.

    4. Return the first four bytes padded with 0.


Levenshtein distance Implementation:
-------------------------------
I used code posted on RosettaCode for calculating Levenshtein distance (from http://rosettacode.org/wiki/Levenshtein_distance#Scala)
, but made an adjustment so that insertions and deletions had a cost of one while substitution had a cost of two.

Algorithm Implemented:
-------------------------------
The intuition here is that a misspelling could either be an attempt at spelling the target spelling *or* an attempt at spelling an acceptable alternative spelling of the target.  And so in an attempt to capture that intuition algorithmically the following is implemented:

If the misspelling is an accepted alternative spelling, assign a distance of 0.5
Otherwise, take the smaller distance of the following two choices:
    -the distance between the misspelling and the target
    -the smallest distance between the misspelling and any accepted alternative spelling

![](https://cloud.githubusercontent.com/assets/8990766/5927154/1a58783c-a62e-11e4-9408-5f5bb5587365.jpeg)
Example Application
---------------------------------
Below are the results of the algorithm matched with the actual Google search results for "Britney Spears" (from http://www.google.com/jobs/archive/britney.html)

        List((brittany,0.5), (brittney,0.5), (britany,0.5), (britteny,0.5), (brittny,0.5), (britny,1.0), (briteny,1.0), (briney,1.0), (britanny,1.0), (britiney,1.0), (britaney,1.0), (brithney,1.0), (brintney,1.0), (brintey,2.0), (britiny,2.0), (britnet,2.0), (britnay,2.0), (brtiney,2.0), (birtney,2.0))

Ranking according to Google spellings | Ranking according to algorithm | Algorithm Ranking
--- | --- | ---
brittany | brittany | 1
brittney | brittney | 1
britany | britany | 1
britny | britteny | 1
briteny | brittny | 1
britteny | britny | 2
briney | briteny | 2
brittny | briney | 2
brintey | britanny | 2
britanny | britiney  | 2
britiny | britaney  | 2
britnet  | brithney | 2
britiney | brintney | 2
britaney  | brintey | 3
britnay | britiny  | 3
brithney | britnet  | 3
brtiney  | britnay  | 3
birtney  | brtiney  | 3
brintney  | birtney | 3


Problems with the implementation:
----------------------------------
There are a few issues with this implementation:

First names collected from the 1990 Census were used to generate the bank of all first name spellings.  This data is out-of-date, and a more list from a more recent Census would be more accurate.  Regardless, while a Census list contains most spellings of first names, there is no "master list" that contains all spellings of all first names, so any list used will be less-than-perfect.

The Soundex coding creates false positives.  While the coding for Soundex succeeds in capturing most alternative spellings, it also captures inaccurate spellings.  For example, "britney", "burton", and "barton" are all coded by Soundex as "B635" yet neither "burton" or "barton" would ever be considered alternative spellings of "britney."  And while it is unlikely that someone will type "burton" when the target name was "britney", it is possible that any misspellings the person did make could be ranked according to their distance from "burton" thus generating an inaccurately small distance ranking.

The simplicity of the minimum edit distance calculation leads to many different misspellings with the same distance score. (see below)

Next steps:
-------------------------------
Future enhancements to the implementation will seek to incorporate an adjusted scoring mechanism for calculating Levenshtein distance:

 -acceptable alternative spelling = 0.5 total distance score
 -each insertion required = 1
 -each deletion required = 1
 -each substitution required = 2
 -each transposition required (where two adjacent letters simply need to be switched) = .5


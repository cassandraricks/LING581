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

The approach:
=================

There are many different reasons why someone may end up misspelling a name, but they aren't all equal in terms of "level of error."  Below is a list of the types of errors ranked from "smallest error" (*i.e.* the first item is the most understandable error):

- person uses an acceptable alternative spelling of the name (*e.g.* "Brittany" instead of "Britney")
- person transposes two letters (*e.g.* "Brintey")
- person forgets a letter (*e.g.* "Britey")
- person adds an extra letter (*e.g.* "Britnbey")






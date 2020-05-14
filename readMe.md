# Sudoku Solver using Genetic Algorithms 
## Tree Directory Structure
```$xslt
.
├── doc
│   ├── A2.pdf
│   ├── Report.pdf
│   ├── Solving\ and\ Analyzing\ Sudokus\ with\ Cultural\ Algorithms.pdf
│   └── Solving,\ rating\ and\ generating\ Sudoku\ puzzles\ with\ GA.pdf
├── readMe.md
├── src
│   ├── Chromosome.java
│   ├── FitnessFunction.java
│   ├── GeneticOperator.java
│   ├── Main.java
│   ├── Meta.java
│   ├── input.txt
│   ├── parameters.txt
│   └── run.sh
└── test\ files
    ├── 1
    │   ├── s01a.txt
    │   ├── s01a_s.txt
    │   ├── s01b.txt
    │   ├── s01b_s.txt
    │   ├── s01c.txt
    │   └── s01c_s.txt
    ├── 2
    │   ├── s02a.txt
    │   ├── s02a_s.txt
    │   ├── s02b.txt
    │   ├── s02b_s.txt
    │   ├── s02c.txt
    │   └── s02c_s.txt
    ├── 3
    │   ├── s03a.txt
    │   ├── s03a_s.txt
    │   ├── s03b.txt
    │   ├── s03b_s.txt
    │   ├── s03c.txt
    │   └── s03c_s.txt
    ├── 4
    │   ├── s04a.txt
    │   ├── s04a_s.txt
    │   ├── s04b.txt
    │   ├── s04b_s.txt
    │   ├── s04c.txt
    │   └── s04c_s.txt
    ├── 5
    │   ├── s05a.txt
    │   ├── s05a_s.txt
    │   ├── s05b.txt
    │   ├── s05b_s.txt
    │   ├── s05c.txt
    │   └── s05c_s.txt
    ├── AI\ Escargot
    │   ├── s16.txt
    │   └── s16_s.txt
    ├── C
    │   ├── s07a.txt
    │   ├── s07a_s.txt
    │   ├── s07b.txt
    │   ├── s07b_s.txt
    │   ├── s07c.txt
    │   └── s07c_s.txt
    ├── D
    │   ├── s08a.txt
    │   ├── s08a_s.txt
    │   ├── s08b.txt
    │   ├── s08b_s.txt
    │   ├── s08c.txt
    │   └── s08c_s.txt
    ├── E
    │   ├── s06a.txt
    │   ├── s06a_s.txt
    │   ├── s06b.txt
    │   ├── s06b_s.txt
    │   ├── s06c.txt
    │   └── s06c_s.txt
    ├── Easy
    │   ├── s10a.txt
    │   ├── s10a_s.txt
    │   ├── s10b.txt
    │   ├── s10b_s.txt
    │   ├── s10c.txt
    │   └── s10c_s.txt
    ├── GA-E
    │   ├── s13a.txt
    │   ├── s13a_s.txt
    │   ├── s13b.txt
    │   ├── s13b_s.txt
    │   ├── s13c.txt
    │   └── s13c_s.txt
    ├── GA-H
    │   ├── s15a.txt
    │   ├── s15a_s.txt
    │   ├── s15b.txt
    │   ├── s15b_s.txt
    │   ├── s15c.txt
    │   └── s15c_s.txt
    ├── GA-M
    │   ├── s14a.txt
    │   ├── s14a_s.txt
    │   ├── s14b.txt
    │   ├── s14b_s.txt
    │   ├── s14c.txt
    │   └── s14c_s.txt
    ├── Hard
    │   ├── s12a.txt
    │   ├── s12a_s.txt
    │   ├── s12b.txt
    │   ├── s12b_s.txt
    │   ├── s12c.txt
    │   └── s12c_s.txt
    ├── Medium
    │   ├── s11a.txt
    │   ├── s11a_s.txt
    │   ├── s11b.txt
    │   ├── s11b_s.txt
    │   ├── s11c.txt
    │   └── s11c_s.txt
    └── SD
        ├── s09a.txt
        ├── s09a_s.txt
        ├── s09b.txt
        ├── s09b_s.txt
        ├── s09c.txt
        └── s09c_s.txt 
```
## Report and Research
The report pdf is located in the doc directory as shown above and explains the approach I took. The respective research is included in the same directory as a reference to the case studies I based my approach off of.

## How to Run:

I have provided a shell script (run.sh) to make running the Sudoku solver a trivial process.
The following explains how to do so:
  cd into the src directory. This directory contains the run.sh script. Once doing so run the following command in your terminal:

    sh run.sh parameters.txt input.txt
    
## Parameter Layout

In the parameter text file, the following structure exists:
```
population size:
Mutation Probability:
Crossover Probability:
Mating Pool:
Occurrences:
Seed:
Debug Mode:
```
Please ensure that the numbers are directly after the semi-colon.

The Mutation and Crossover probability need to be represented as a percentage out of 100.

The Mating Pool refers to the size of the pool that chromosomes are selected for the next generation.

The Occurrences parameter refers to the second heuristic used to control whether a mutation is applied. Specifically, when the puzzle is solved, any randomly selected two digits should appear in any randomly selected two rows and two columns total of 4 times. Hence, ensure that you give the system some 'slack' by ensuring that this value ranges between 4 and 6 to ensure that mutation is applied more frequently in order to move the search into a different search space if the solution is stuck in a local optima (harder puzzles tend to to this, hence, for those puzzles, give the system some slack of 5-6, whereas easier solutions that tend to converge to a solution quicker need only have a frequency occurrence of 4 - this is your choice at the end of the day).

The seed value is optional, however, when the GA finds a solution, the seed will be outputted to the terminal along with the solution and generation number, hence, paste that seed in the parameter text file in order to reproduce that run. This seed is calculated by taking the currentTimeMillis of the System at the time of execution.

The Debug mode parameter need be either "true" or "false" and not discarded empty. If true, the average fitness of a specific run will be outputted to the terminal in every generation. Hence, for difficult Sudokus that do not converge to a solution easily, you may set this parameter to true to see the where the GA is headed (this will allow you to graphically see the effect that your parameters have on a specific Sudoku, allowing you to fine tune them to make the Sudoku converge to a solution).

below shows an example parameter file for the following situations:

Found a solution (the seed is given):
```
population size:100
Mutation Probability:10
Crossover Probability:20
Mating Pool:100
Occurrences:4
Seed:1589407797948
Debug Mode:false
```
Set up in the hopes to find a solution (debug mode set to true to see where the GA is going):
```
population size:100
Mutation Probability:10
Crossover Probability:20
Mating Pool:100
Occurrences:4
Seed:
Debug Mode:true
```
## Input File layout

The input.txt file includes the initial Sudoku that is needed to be solved. Hence, please paste your given test file text into that input.txt file. The layout is exactly the same as the benchmark test files.

## Test Files

I have included a single text file and solution of each benchmark Sudoku from the [Sudoku research page](http://lipas.uwasa.fi/~timan/sudoku/) in the test files directory. Within this directory, the name of each benchmark test is stored in their corresponding directories with the name of the Benchmark Sudoku as the directory name. For each corresponding solution, I have included the parameters I used in order to solve those benchmark test in their respective solutions file (text files with a subscript "_s.txt" is the respective solution for a given benchmark test). Hence, it would be beneficial to refer to those respective solutions text files in order to see the parameters used - for the solutions text files that do not have parameters in them, I was not able to converge to a solution, however, I have not done extensive parameter tuning.


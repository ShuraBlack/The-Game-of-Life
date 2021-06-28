# The-Game-of-Life
Java based and dependencies free version of the popular simulation
=====================================================
Code written: [Ronny Klotz](https://github.com/ShuraBlack)

The Game of Life is a famous ceullular Automata simulation. The universe of the game of Life is an (technically) infite two dimensional grid of square cells, each of which is in one of two possible states, dead(0) or alive(1). Every cell interacts with its eight neighbours. At each step in time (or generation), the following transitions occur:


1. Any live cell with fewer than two live neighbours dies **(underpopulation)**
2. Any live cell with two or three live neighbours lives on to the next generation
3. Any live cell with more than three live neighbours dies **(overcrowding)**
4. Any dead cell with exactly three live neighbours becomes alive **(reproduction)**

![Simulation](https://s20.directupload.net/images/210628/xy9i99ng.gif)

## Project information

Type | Version
:--- | ---:
Java JDK  | 1.8 

Dependencies |
:--- |
None  |

Its fully based around the Java SDK and uses Java Swing and Graphics to draw basic geometry

## How to use it?

Basic:

- **SPACE** - Will create a fresh generation and kill all existing cells

- **Color Indicator** - The longer the cell lives, the redder it becomes

- **ESC** - Close the application

Edit:

- **ENTER** - Enter or leave the Edit mode

- **LEFT MOUSE** - Make a cell alive *(Only edit mode)*

- **RIGHT MOUSE** - Kills a cell *(Only edit mode)*

- **DELETE** - Will kill all cells *(Only edit mode)*

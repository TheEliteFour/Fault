Fault Tutorial
--------------

You are a programmer programming virus's for a new type of super computer called the Cyclone.

The Cyclone is a machine that processes data in its processor in a circle, executing the first cell 
first and the last cell last.  Each rotation is called a Cycle.

The objective is to program a virus that leaves you the last virus standing at the end of a cycle.

There are 100 cells.  Ranging from 0 to 100.  You can never know what cell you are in at any given time
and can only access cells by referencing the ones in front of you or in back of you by so many spaces.

There are 50 memory blocks.  Each memory block can store a number.  These will not be wiped at the end
of a cycle.  These are labled 0-49 and CAN be accessed and referenced directly and only directly.

Cells can be corrupted.  If a accessing program tries to access a Corrupted Cell it will crash and be removed
from the processor.  Once a Corrupted Cell is accessed and the program crashes the Cell returns to normal.

Memory Blocks work in the same fasion  when it comes to corruption.

Each virus has 15 CPU points per cycle to use code from the custom language BlueCode.

Programs are chosen from the programs folder and ending with .bcv.  Only 5 viruses will be chosen and there
can't be any 2 program with the same name.

Bellow is a reference sheet of BlueCode.



BlueCode
--------

The first line MUST be the name, and the #NAME command MUST have the space and MUST be capital.
The second line MUST be the author, and the #AUTHOR command MUST have the space and MUST be capital.

All other commands must be spaced PROPERLY and MUST be spelled properly.  Case is not important for those.

Free commands, except for PUT and EXTRA, MUST be used with another command, can be PUT and EXTRA.  Otherwise it will crash.

RAND, MATH and GET commands can not be nested within eachother.  No RAND{RAND{5,7},20} and so forth.

IF can only accept commands RAND, MATH and GET as commands.  IFs may be nested!

You may use the 2 viruses included as examples and templates.

Commands
--------

MOV # - 1 point - Moves to the specified number.
COPY # - 8 points - Copy program to a specified cell
CORRUPT CELL/BLOCK # - 4 points - Corrupt a specified Cell/Memory Block.
SHIFT CELL/BLOCK # - 3 points - Shifts a specified Cell/Memory Block to the other specified Cell/Memory
                                Block.  If you shift a Corrupted Cell onto a program you will crash that
								program.  If you shift a Corrupted Cell to an empty Cell it becomes a
								Corrupted Cell.  If you shift a program onto a Corrupted Cell it crashes
								that program.  It will move Corrupted Memory Blocks.
RAND - 0 points - Get a random number between 0 and 255.
RAND{#,#} - 0 points - Get a random number specified between the limits of -255 and 255.
DELETE CELL/BLOCK - 1 points - Deletes a specified Cell/Block.
EXTRA # # # - 0 points - Lock the 3 specified Memory Blocks as a CPU Cache for 2 extra CPU Points.
PUT # # - 0 points - Puts the second value into the Memory Block specified in the First value.
GET[#] - 0 points - Get the specified Memory Blocks contents.
MATH(#_SYMBOL_#) - 0 points - Does math on the 2 specified numbers.  The only accepted symbols are + - and *.
IF # SYMBOL # - 0 points - Continues with the IF Block if the given is TRUE.  Symbols supported are ! and = 
                for not equal and equal
ENDIF - 0 points Ends IF Block

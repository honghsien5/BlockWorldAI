BlockWorldAI
==============

Synopsis
--------------

BlockWorld AI is a project that is programmed to demonstrate the forward chaining process by calculating the path to reach from an initial state to the goal state.


Code Example
--------------
	
Run it with the following command format:  
`java BlockWorldAI [input file]`

Sample input file format:  

	BLOCKS: 
	A, B, C, D

	INITIAL STATE:
	ON_TABLE(A)
	CLEAR(A)
	ON_TABLE(B)
	CLEAR(B)
	ON_TABLE(C)
	ON(D,C)
	CLEAR(D)
	HE

	GOAL STATE:
	ON_TABLE(A)
	ON(B,A)
	ON(C,B)
	HOLDING(D)
	
Sample output:  

	Starting the parsing.....
	Calculating path.....

	PICKUP(B)	:ON_TABLE(A),	CLEAR(A),	ON_TABLE(C),	ON(D,C),	CLEAR(D),	HOLDING(B)
	STACK(B, A)	:ON_TABLE(A),	ON(B,A),	CLEAR(B),	ON_TABLE(C),	ON(D,C),	CLEAR(D),	HE
	UNSTACK(D, C)	:ON_TABLE(A),	ON(B,A),	CLEAR(B),	ON_TABLE(C),	CLEAR(C),	HOLDING(D)
	PUTDOWN(D)	:ON_TABLE(A),	ON(B,A),	CLEAR(B),	ON_TABLE(C),	CLEAR(C),	ON_TABLE(D),	CLEAR(D),	HE
	PICKUP(C)	:ON_TABLE(A),	ON(B,A),	CLEAR(B),	ON_TABLE(D),	CLEAR(D),	HOLDING(C)
	STACK(C, B)	:ON_TABLE(A),	ON(B,A),	ON(C,B),	CLEAR(C),	ON_TABLE(D),	CLEAR(D),	HE
	PICKUP(D)	:ON_TABLE(A),	ON(B,A),	ON(C,B),	CLEAR(C),	HOLDING(D)

	7 Moves performed
	1832 states considered.

	Improved version:
	Calculating path.....

	PICKUP(B)	:ON_TABLE(A),	CLEAR(A),	ON_TABLE(C),	ON(D,C),	CLEAR(D),	HOLDING(B)
	STACK(B, A)	:ON_TABLE(A),	ON(B,A),	CLEAR(B),	ON_TABLE(C),	ON(D,C),	CLEAR(D),	HE
	UNSTACK(D, C)	:ON_TABLE(A),	ON(B,A),	CLEAR(B),	ON_TABLE(C),	CLEAR(C),	HOLDING(D)
	PUTDOWN(D)	:ON_TABLE(A),	ON(B,A),	CLEAR(B),	ON_TABLE(C),	CLEAR(C),	ON_TABLE(D),	CLEAR(D),	HE
	PICKUP(C)	:ON_TABLE(A),	ON(B,A),	CLEAR(B),	ON_TABLE(D),	CLEAR(D),	HOLDING(C)
	STACK(C, B)	:ON_TABLE(A),	ON(B,A),	ON(C,B),	CLEAR(C),	ON_TABLE(D),	CLEAR(D),	HE
	PICKUP(D)	:ON_TABLE(A),	ON(B,A),	ON(C,B),	CLEAR(C),	HOLDING(D)

	7 Moves performed
	192 states considered.

Motivation
--------------

This project was created to practice a heuristic algorithm to find a shortest path in a complex setting.

Installation
--------------

1.Download the code into a directory  
2.Build the files by using the following command:  
`javac BlockWorldAI.java`  
3.The program is now ready to run  

Contributors
--------------

Shien Hong			honghsien5@gmail.com

License
--------------

	This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

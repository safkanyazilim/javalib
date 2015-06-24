About the project:

This is an Eclipse project, created with Eclipse Luna. Built with
JDK 8. Contains a few unit tests, which require JUnit4.

The Main class is org.safkan.trains.Main.java

It expects a filename to read the graph from.

You can find the file to use right next to this README.txt file. It is
called graph.txt. Its content is just:

Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7

The parser knows to ignore the "Graph" part.

If you open this in Eclipse, editing the run configuration, and in the arguments
tab just putting in graph.txt makes everything work.

The output I get is as below:

Output #1: 9.0
Output #2: 5.0
Output #3: 13.0
Output #4: 22.0
Output #5: NO SUCH ROUTE
Output #6: 2
Output #7: 3
Output #8: 9.0
Output #9: 9.0
Output #10: 7

(My distances are doubles, hence the .0 business.)


===================================================================

The story of the problem, design of the solution, and other thoughts...

Initially, this looked like a standard graph-type problem. However, it does
indeed have some interesting points to it. 

I wanted my "Main" program to be as simple to write and understand as possible. 
In other words, I wanted to create a library or a base set of classes that can
be used to solve not only this problem, but many similar ones as well.

So, my initial idea was to come up with classes that had external visibility. 
The initial classes were to define the graph we wish to work on. These ended 
up being as follows:

Node: 

Since in the problem the nodes are represented by just letters of the alphabet,
I chose to represent a node with just a String. This is good for this problem,
and would be useful enough for a more general set of problems. So, a node is 
just an immutable class with a String id, its equals() and hashCode() methods
properly overridden so it works as expected.

Edge:

An edge is two nodes, plus a distance. Edge equality is also overridden, and
two edges are equal if their endpoints are the same, regardless of distance. 
This is so because the fundamental question to ask here is "Does this graph
contain this edge?" and that does not depend on the distance.

Also, I decided the distance of an edge should not be negative. 

Oh, and I made all the distances doubles rather than ints. So my outputs where
distances are involved look like 5.0 rather than 5. 

Graph:

Not exactly immutable, but it is "build-only", that is, you can add edges and
nodes, but not remove anything from it. I also invented two strictness parameters
here. Strict nodes means that you may add nodes, but not add any node twice. It also
means you can not add an edge without adding the nodes first. Without node strictness,
you can add nodes multiple times with no ill effect; you can also add edges
without adding the nodes, in which case they will be added to the graph automatically.

Edge strictness means that you may not add an edge twice. Without strictness here,
adding an edge a second time corresponds to updating the distance; the last one added
wins.


The Graph is just a definition, and it is not functional. I wanted to make the functional
part opaque, just to show an interface to the world, and generate its implementation through
the Graph object. The end result is the compile() method, which returns a CompiledGraph,
which exposes all the necessary methods for the stuff required in the problems, and more.

For the input and output, I created the following classes:

Path:

Just a list of nodes. This can again be built by adding nodes.

TraversedPath:

Created by the library only. Contains both nodes, and their distances. Also a useful toString()
method.

The rest of the classes are to do with the implementation, and I intended them to be package
visible only. (I might have made some stuff public, those are probably not intentional.)

The first five cases are solved by the "traverse" method of CompiledGraph. This method just
takes a path, and tries to walk it on the graph, returning a TraversedPath. This worked 
just great. 

Counting the number of paths with different constraints was a different problem. Cases 6, 7, and 10
could be solved with the same method, I figured. The results are the generatePaths
and countPaths methods. These work by expanding all possible paths as a tree (the question requires
nothing less). Although this is exponential, this is the fundamental complexity of the problem
and can not be escaped. The Tree and TreeElement classes work together with 
CompiledGraphImplementation to make everything work. 

Cases 9 and 10 require shortest path calculation. I only recently did an implementation
of A-star, so I lifted my own code from there. Some of the helper stuff I put in the util
package. (These contain stuff I implemented from "Introduction to Algorithms" by Cormen...)
(It contains a PriorityQueue implementation. Turns out Java has one too now, but mine works
for me, so why not...)

I realize A-Star is not really required since we do not have a heuristic here, but it does
not hurt, either. And there is nothing like previously tested code.

Case #8 worked like a charm. Case #9 however, the path from C to C... Kansas go bye-bye...
A-star is ill-equipped to solve a circular path! It just returns "you are already there".
So, I modified it. If a solution seems to be found, yet the distance is still zero, it marks
the current node non-closed again, so it may be visited again, and rejects the solution.

That worked like a charm, yet again.

So, this is not really as gold-plated as I wanted to make it. And I did not have time
to write sufficient unit tests for it. (Really too busy in three different places.) 

But I am happy with the overall design.






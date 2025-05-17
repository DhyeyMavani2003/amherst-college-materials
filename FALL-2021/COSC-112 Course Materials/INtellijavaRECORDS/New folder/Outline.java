/*
I am planning to use two additional classes in my program. The first class will be called Stock.
The instance variables of this class will contain important information pertinent to a
company's stock, such as the company's name, the number of stocks currently available at
the company, as well as the stock value. The importance of this class is that it prevents
me from declaring too many separate arrays and instead just a single array of Stock objects.
The second class I am planning to use will be called Investor, which will include instance
variables relating to the stocks owned by the investor, the value of the assets of the user,
the amount of cash held by the user, etc. This might not be useful right
now since there will only be one investor in the game, but it gives the program the scope to
handle additional investors (players) very easily in the future and also gives me the flexibility
to possibly add an additional investor (player) to the game if I wish to make it more interesting.
The instance methods that will be used in each class will include getting all three instance variables.
Other instance methods will be defined as required along the course of the development of the
program.
*/

/*
I am planning to use the following methods in my main program:

1) drawStockTable(), which takes as arguments the Stock objects and returns nothing (void). The
purpose of this method is to output the table displaying all stock information on the terminal
window in order to allow the user to make informed decisions about his portfolio and how much
more can he invest in a company.

2) stockChange(), which takes as argument the array of Stock objects and returns the array back.
The purpose of this method is to randomise changes to the stock value using Math.random(). These
changes take place at the end of each quarter, and the user is notified of this through an up or
down arrow (along with the corresponding percentages) next to the stock value on the StdDraw output
(please see below for my outline of the StdDraw canvas). I am also planning to output a reason for
the changes in the stock values on the StdDraw window. These reasons will also be randomised in order
to make the game more interesting.

3) draw(), which takes several arguments including the array of Stock objects and the Investor
object and returns nothing (void). The purpose of this method is to display the user's portfolio
and the current stock values on the StdDraw canvas. A separate method is needed here since it will
condense the code and make it much easier to debug if any problems show up. The StdDraw output will
be made much more attractive, with a separate box for news headlines, images of the six companies,
as well as a backdrop that gives a Wall Street vibe.

* Please note that the above plan for the methods and classes is subject to slight change in case
there is scope for further improvement or if I made a misjudgement.
 */

/*
For sound, I am planning to play a motivational tune at the end of each quarter. I hope this will
add to the Wall Street vibe. I will be using StdAudio to achieve this, along with possibly a
separate method for this to condense and simplify my code.

I could potentially use recursion in order to implement the merge sort algorithm in my program. This,
for example, could help me sort the investment returns of the user in ascending order, giving the user
an idea of the best performing stock and the worst performing stock that they invested in. This could
be achieved using a separate method.

I could also possibly implement a feature that allows the reader to navigate headlines using the mouse.
I can include left and right arrows, which allow the reader to read different headlines explaining the
respective stock value changes.
 */

//As for the outline of my program, I have briefly described it implicitly in words in my main final idea

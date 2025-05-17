import java.util.Scanner;
import java.awt.*;

public class Project
{
    public static Scanner keyboard = new Scanner(System.in);
    public static void main (String[] args)
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(0.5, 0.5, 0.5, 0.5);

        StdDraw.setPenColor(StdDraw.WHITE);
        Font font1 = new Font("Arial", Font.ITALIC, 0);
        StdDraw.setFont(font1);
        StdDraw.text(0.505, 0.50, "Wall Street Simulator");

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(0.5, 0.50, "Wall Street Simulator");

        StdDraw.enableDoubleBuffering();

        for (int i = 0; i < 50; i++)
        {
            StdDraw.clear();

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(0.5, 0.5, 0.5, 0.5);
            StdDraw.setPenColor(StdDraw.WHITE);
            Font font2 = new Font("Arial", Font.ITALIC, i);
            StdDraw.setFont(font2);
            StdDraw.text(0.505, 0.50, "Wall Street Simulator");
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.text(0.5, 0.50, "Wall Street Simulator");

            StdDraw.show();
            StdDraw.pause(20);
        }

        System.out.println(" ");
        System.out.println("Hi there, fellow investor! Welcome to the Wall Street Simulator!");
        System.out.println("It's the start of 2021, a brand new year and a brand new quarter! Do you know what that means?");
        System.out.println("Yes! You can have a fresh start to a promising way to earn some money.");
        System.out.println("Wall Street has several amazing investment opportunities waiting for you that can potentially have very high returns.");
        System.out.println(" ");
        System.out.println("We have arranged for you a personal broker who has suggested the following companies to invest in:");
        System.out.println(" ");
        System.out.println("AMAZON      MICROSOFT      APPLE      SAMSUNG      FACEBOOK      TWITTER");
        System.out.println(" ");

        System.out.println("You have a total of $1000 as liquid assets (cash). Details about the stocks of each of these six companies is given below");
        System.out.println(" ");
        System.out.println(" COMPANY NAME  |   STOCK VALUE  |  NUMBER OF STOCKS AVAILABLE");
        System.out.println("--------------------------------------------------------------");
        System.out.println("    Amazon     |     $15.00     |             15              ");
        System.out.println("--------------------------------------------------------------");
        System.out.println("   Microsoft   |     $9.00      |             25              ");
        System.out.println("--------------------------------------------------------------");
        System.out.println("    Apple      |     $12.00     |             15              ");
        System.out.println("--------------------------------------------------------------");
        System.out.println("    Samsung    |     $20.00     |             11              ");
        System.out.println("--------------------------------------------------------------");
        System.out.println("   Facebook    |     $14.00     |             20              ");
        System.out.println("--------------------------------------------------------------");
        System.out.println("    Twitter    |     $22.00     |              8              ");
        System.out.println("--------------------------------------------------------------");

        Stock[] stocks = new Stock[6];

        stocks[0] = new Stock("Amazon", 15.00, 15);
        stocks[1] = new Stock("Microsoft", 9.00, 25);
        stocks[2] = new Stock("Apple", 12.00, 15);
        stocks[3] = new Stock("Samsung", 20.00, 11);
        stocks[4] = new Stock("Facebook", 14.00, 20);
        stocks[5] = new Stock("Twitter", 22.00, 8);

        System.out.println(" ");
        System.out.println("How many of these companies do you want to invest in?");
        String inputTest = keyboard.nextLine();

        if (inputTest.isEmpty())
        {
            System.err.println("Nothing was entered! Please enter a single positive integer between 1 and 6 (both inclusive).");
            System.exit(1);
        }

        if (inputTest.length() > 1)
        {
            System.err.println("Too many inputs! Please enter a single positive integer between 1 and 6 (both inclusive).");
            System.exit(1);
        }

        int noOfInvestments = Integer.parseInt(inputTest);

        if ((noOfInvestments < 1) || (noOfInvestments > 6))
        {
            System.err.println("Please enter a single positive integer between 1 and 6 (both inclusive).");
            System.exit(1);
        }

        int[] investments = new int[noOfInvestments];

        System.out.println(" ");
        System.out.println("Enter the names of the companies you want to invest in using the following digit designations:");
        System.out.println(" 0 - Amazon, 1 - Microsoft, 2 - Apple, 3 - Samsung, 4 - Facebook, 5 - Twitter");

        for (int i = 0; i < noOfInvestments; i++)
        {
            System.out.println(" ");
            System.out.print("Digit for company number " + (i+1) + ": ");
            inputTest = keyboard.nextLine();

            if(inputTest.length() != 1)
            {
                System.err.println("Please enter a single digit");
                System.exit(1);
            }

            investments[i] = Integer.parseInt(inputTest);

            if ((investments[i] < 0) || (investments[i] > 5))
            {
                System.err.println("Please enter a single integer between 0 and 5 (both inclusive).");
                System.exit(1);
            }
        }

        int[] stocksBought = new int[noOfInvestments];

        for (int i = 0; i < noOfInvestments; i++)
        {
            if (investments[i] == 0)
            {
                System.out.println(" ");
                System.out.println("How many stocks do you want to buy at Amazon?");
                inputTest = keyboard.nextLine();

                if(!(inputTest.length() == 1 || inputTest.length() == 2))
                {
                    System.err.println("Please enter an appropriate number of digits");
                    System.exit(1);
                }

                stocksBought[i] = Integer.parseInt(inputTest);

                if ((stocksBought[i] < 1) || (stocksBought[i] > 15))
                {
                    System.err.println("Please enter a single positive integer between 1 and 15 (both inclusive).");
                    System.exit(1);
                }

                stocks[investments[i]].changeNoOfStocks(-stocksBought[i]);
            }

            if (investments[i] == 1)
            {
                System.out.println(" ");
                System.out.println("How many stocks do you want to buy at Microsoft?");
                inputTest = keyboard.nextLine();

                if(!(inputTest.length() == 1 || inputTest.length() == 2))
                {
                    System.err.println("Please enter an appropriate number of digits");
                    System.exit(1);
                }

                stocksBought[i] = Integer.parseInt(inputTest);

                if ((stocksBought[i] < 1) || (stocksBought[i] > 25))
                {
                    System.err.println("Please enter a single positive integer between 1 and 25 (both inclusive).");
                    System.exit(1);
                }

                stocks[investments[i]].changeNoOfStocks(-stocksBought[i]);
            }

            if (investments[i] == 2)
            {
                System.out.println(" ");
                System.out.println("How many stocks do you want to buy at Apple?");
                inputTest = keyboard.nextLine();

                if(!(inputTest.length() == 1 || inputTest.length() == 2))
                {
                    System.err.println("Please enter an appropriate number of digits");
                    System.exit(1);
                }

                stocksBought[i] = Integer.parseInt(inputTest);

                if ((stocksBought[i] < 1) || (stocksBought[i] > 15))
                {
                    System.err.println("Please enter a single positive integer between 1 and 15 (both inclusive).");
                    System.exit(1);
                }

                stocks[investments[i]].changeNoOfStocks(-stocksBought[i]);
            }

            if (investments[i] == 3)
            {
                System.out.println(" ");
                System.out.println("How many stocks do you want to buy at Samsung?");
                inputTest = keyboard.nextLine();

                if(!(inputTest.length() == 1 || inputTest.length() == 2))
                {
                    System.err.println("Please enter an appropriate number of digits");
                    System.exit(1);
                }

                stocksBought[i] = Integer.parseInt(inputTest);

                if ((stocksBought[i] < 1) || (stocksBought[i] > 11))
                {
                    System.err.println("Please enter a single positive integer between 1 and 11 (both inclusive).");
                    System.exit(1);
                }

                stocks[investments[i]].changeNoOfStocks(-stocksBought[i]);
            }

            if (investments[i] == 4)
            {
                System.out.println(" ");
                System.out.println("How many stocks do you want to buy at Facebook?");
                inputTest = keyboard.nextLine();

                if(!(inputTest.length() == 1 || inputTest.length() == 2))
                {
                    System.err.println("Please enter an appropriate number of digits");
                    System.exit(1);
                }

                stocksBought[i] = Integer.parseInt(inputTest);

                if ((stocksBought[i] < 1) || (stocksBought[i] > 20))
                {
                    System.err.println("Please enter a single positive integer between 1 and 20 (both inclusive).");
                    System.exit(1);
                }

                stocks[investments[i]].changeNoOfStocks(-stocksBought[i]);
            }

            if (investments[i] == 5)
            {
                System.out.println(" ");
                System.out.println("How many stocks do you want to buy at Twitter?");
                inputTest = keyboard.nextLine();

                if(!(inputTest.length() == 1 || inputTest.length() == 2))
                {
                    System.err.println("Please enter an appropriate number of digits");
                    System.exit(1);
                }

                stocksBought[i] = Integer.parseInt(inputTest);

                if ((stocksBought[i] < 1) || (stocksBought[i] > 8))
                {
                    System.err.println("Please enter a single positive integer between 1 and 8 (both inclusive).");
                    System.exit(1);
                }

                stocks[investments[i]].changeNoOfStocks(-stocksBought[i]);
            }
        }

        double currentPortfolioValue = 0.00;

        for (int i = 0; i < noOfInvestments; i++)
        {
            currentPortfolioValue += stocksBought[i] * stocks[investments[i]].getStockValue();
        }

        double assetsValue = 1000.00;

        double cashHeld = assetsValue - currentPortfolioValue;

        if (cashHeld < 0)
        {
            System.err.println("You do not have enough cash to buy this combination of stocks!");
            System.exit(1);
        }

        String CPVString = String.format("%.2f", currentPortfolioValue);
        String CHString = String.format("%.2f", cashHeld);
        String AVString = String.format("%.2f", assetsValue);

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");
        System.out.println("AT THE START OF QUARTER 1...");
        System.out.println("The value of your portfolio is $" + CPVString);
        System.out.println("The cash (liquid assets) held by you is $" + CHString);
        System.out.println("The value of your total assets is $" + AVString);

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");

        System.out.println("We will now be simulating Quarter 1. Enter 0 to continue.");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int Continue = Integer.parseInt(inputTest);

        if (Continue != 0)
        {
            System.err.println("Please enter 0 to continue");
            System.exit(1);
        }

        double[] percentChange1 = randomChange(stocks);

        System.out.println(" ");
        System.out.println("Are you surprised by this news of 31st March 2021?");
        System.out.println("Regardless, what matters more is how you learn from this news and make better decisions for the next quarter!");
        System.out.println("Let's see how the stock values have changed at the end of this quarter. Enter 0 to continue.");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int Headline = Integer.parseInt(inputTest);

        if (Headline != 0)
        {
            System.err.println("Please enter 0 to see the end-of-quarter results");
            System.exit(1);
        }

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");
        System.out.println("AT THE END OF QUARTER 1...");

        drawTable(stocks, percentChange1);
        drawPortfolio(noOfInvestments, investments, stocksBought, stocks);

        currentPortfolioValue = 0.00;

        for (int i = 0; i < noOfInvestments; i++)
        {
            currentPortfolioValue += stocksBought[i] * stocks[investments[i]].getStockValue();
        }

        assetsValue = currentPortfolioValue + cashHeld;

        if (cashHeld < 0)
        {
            System.err.println("You do not have enough cash to buy this combination of stocks!");
            System.exit(1);
        }

        CPVString = String.format("%.2f", currentPortfolioValue);
        CHString = String.format("%.2f", cashHeld);
        AVString = String.format("%.2f", assetsValue);

        System.out.println(" ");
        System.out.println("The value of your portfolio is $" + CPVString);
        System.out.println("The cash (liquid assets) held by you is $" + CHString);
        System.out.println("The value of your total assets is $" + AVString);

        System.out.println(" ");
        if (assetsValue - 1000 > 0)
        {
            System.out.println("Great job! You are currently making an overall profit of $" + String.format("%.2f", (assetsValue - 1000)) + ". Keep up the good work!");
        }
        else if (assetsValue - 1000 == 0)
        {
            System.out.println("You are currently breaking even! You should now try your best to make a profit");
        }
        else
        {
            System.out.println("You are currently making an overall loss of $" + String.format("%.2f", -(assetsValue - 1000)) + ". You need to up your game!");
        }

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");
        System.out.println("Let's move on to Quarter 2. Enter 0 to continue.");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int Continue2 = Integer.parseInt(inputTest);

        if (Continue2 != 0)
        {
            System.err.println("Please enter 0 to continue");
            System.exit(1);
        }

        System.out.println(" ");
        System.out.println("For Quarter 2 (based on these stock value changes)...");

        System.out.println("Do you wish to buy more stocks at your current companies?");
        System.out.println("If yes, enter the number of companies you want to buy more of. If no, enter 0");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int more = Integer.parseInt(inputTest);

        if (more < 0 || more > noOfInvestments)
        {
            System.err.println("Please enter a valid input");
            System.exit(1);
        }

        if (more > 0)
        {
            System.out.println(" ");
            System.out.println("You have currently invested in the following companies (denoted by the codes):");
            System.out.println(" ");
            for (int i = 0; i < noOfInvestments; i++)
            {
                System.out.println(investments[i] + " ");
            }
            System.out.println(" ");

            System.out.println("Now, enter the digits for the companies you want to invest more in");

            for (int j = 0; j < more; j++)
            {
                System.out.println(" ");
                System.out.print("Digit for company number " + (j+1) + ": ");
                inputTest = keyboard.nextLine();

                if(inputTest.length() != 1)
                {
                    System.err.println("Please enter a single digit");
                    System.exit(1);
                }

                int temp = Integer.parseInt(inputTest);

                if ((temp < 0) || (temp > 5))
                {
                    System.err.println("Please enter a single integer between 0 and 5 (both inclusive).");
                    System.exit(1);
                }

                int check = 0;

                for (int i = 0; i < noOfInvestments; i++)
                {
                    if (temp == investments[i])
                    {
                        check = 1;
                        break;
                    }
                }

                if (check == 0)
                {
                    System.err.println("You have not invested in this company!");
                    System.exit(1);
                }

                if (temp == 0)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Amazon?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[0].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[0].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 0)
                        {
                            stocksBought[i] += add;
                        }
                    }
                    stocks[0].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[0].getStockValue());
                }

                if (temp == 1)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Microsoft?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[1].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[1].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 1)
                        {
                            stocksBought[i] += add;
                        }
                    }
                    stocks[1].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[1].getStockValue());
                }

                if (temp == 2)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Apple?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[2].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[2].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 2)
                        {
                            stocksBought[i] += add;
                        }
                    }
                    stocks[2].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[2].getStockValue());
                }

                if (temp == 3)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Samsung?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[3].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[3].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 3)
                        {
                            stocksBought[i] += add;
                        }
                    }
                    stocks[3].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[3].getStockValue());
                }

                if (temp == 4)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Facebook?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[4].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[4].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 4)
                        {
                            stocksBought[i] += add;
                        }
                    }
                    stocks[4].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[4].getStockValue());
                }

                if (temp == 5)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Twitter?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[5].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[5].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 5)
                        {
                            stocksBought[i] += add;
                        }
                    }
                    stocks[5].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[5].getStockValue());
                }
            }
        }

        System.out.println(" ");
        System.out.println("Do you wish to sell stocks at your current companies?");
        System.out.println("If yes, enter the number of companies you want to sell stocks of. If no, enter 0");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int sell = Integer.parseInt(inputTest);

        if (sell < 0 || sell > noOfInvestments)
        {
            System.err.println("Please enter a valid input");
            System.exit(1);
        }

        if (sell > 0)
        {
            System.out.println(" ");
            System.out.println("You have currently invested in the following companies (denoted by the codes):");
            System.out.println(" ");

            for (int i = 0; i < noOfInvestments; i++)
            {
                System.out.println(investments[i] + " ");
            }
            System.out.println(" ");

            System.out.println("Now, enter the digits for the companies you want to sell stocks of");

            for (int j = 0; j < sell; j++)
            {
                System.out.println(" ");
                System.out.print("Digit for company number " + (j+1) + ": ");
                inputTest = keyboard.nextLine();

                if(inputTest.length() != 1)
                {
                    System.err.println("Please enter a single digit");
                    System.exit(1);
                }

                int temp = Integer.parseInt(inputTest);

                if ((temp < 0) || (temp > 5))
                {
                    System.err.println("Please enter a single integer between 0 and 5 (both inclusive).");
                    System.exit(1);
                }

                int check = 0;

                for (int i = 0; i < noOfInvestments; i++)
                {
                    if (temp == investments[i])
                    {
                        check = 1;
                        break;
                    }
                }

                if (check == 0)
                {
                    System.err.println("You have not invested in this company!");
                    System.exit(1);
                }

                if (temp == 0)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Amazon do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 0)
                        {
                            if ((remove < 1) || (remove > stocksBought[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 0)
                        {
                            stocksBought[i] -= remove;
                        }
                    }
                    stocks[0].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[0].getStockValue());
                }

                if (temp == 1)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Microsoft do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 1)
                        {
                            if ((remove < 1) || (remove > stocksBought[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 1)
                        {
                            stocksBought[i] -= remove;
                        }
                    }
                    stocks[1].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[1].getStockValue());
                }

                if (temp == 2)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Apple do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 2)
                        {
                            if ((remove < 1) || (remove > stocksBought[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 2)
                        {
                            stocksBought[i] -= remove;
                        }
                    }
                    stocks[2].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[2].getStockValue());
                }

                if (temp == 3)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Samsung do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 3)
                        {
                            if ((remove < 1) || (remove > stocksBought[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 3)
                        {
                            stocksBought[i] -= remove;
                        }
                    }
                    stocks[3].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[3].getStockValue());
                }

                if (temp == 4)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Facebook do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 4)
                        {
                            if ((remove < 1) || (remove > stocksBought[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 4)
                        {
                            stocksBought[i] -= remove;
                        }
                    }
                    stocks[4].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[4].getStockValue());
                }

                if (temp == 5)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Twitter do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 5)
                        {
                            if ((remove < 1) || (remove > stocksBought[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments[i] == 5)
                        {
                            stocksBought[i] -= remove;
                        }
                    }
                    stocks[5].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[5].getStockValue());
                }
            }
        }

        System.out.println(" ");
        System.out.println("How many NEW companies do you wish to buy stocks at (Type 0 if you do not wish to invest in new companies)?");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int New = Integer.parseInt(inputTest);

        int[] investments2 = new int[noOfInvestments + New];
        int[] stocksBought2 = new int[noOfInvestments + New];

        if (New < 0 || New > 6 - noOfInvestments)
        {
            System.err.println("Please enter a valid input");
            System.exit(1);
        }

        for (int i = 0; i < noOfInvestments; i++)
        {
            investments2[i] = investments[i];
            stocksBought2[i] = stocksBought[i];
        }

        if (New > 0)
        {
            System.out.println(" ");
            System.out.println("You have currently invested in the following companies (denoted by the codes):");
            System.out.println(" ");

            for (int i = 0; i < noOfInvestments; i++)
            {
                System.out.println(investments[i] + " ");
            }
            System.out.println(" ");

            System.out.println();
            System.out.println("Now, enter the digits for the new companies you want to invest in");

            for (int i = 0; i < New; i++)
            {
                System.out.println(" ");
                System.out.print("Digit for new company number " + (i+1) + ": ");
                inputTest = keyboard.nextLine();

                if(inputTest.length() != 1)
                {
                    System.err.println("Please enter a single digit");
                    System.exit(1);
                }

                investments2[noOfInvestments + i] = Integer.parseInt(inputTest);

                for (int a = 0; a < noOfInvestments; a++)
                {
                    if (investments2[noOfInvestments + i] == investments[a])
                    {
                        if(stocksBought[a] != 0)
                        {
                            System.err.println("You have already invested in this company!");
                            System.exit(1);
                        }
                    }
                }

                if ((investments2[noOfInvestments + i] < 0) || (investments2[noOfInvestments + i] > 5))
                {
                    System.err.println("Please enter a single integer between 0 and 5 (both inclusive).");
                    System.exit(1);
                }
            }

            for (int i = 0; i < New; i++)
            {
                if (investments2[noOfInvestments + i] == 0)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Amazon?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought2[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought2[noOfInvestments + i] < 1) || (stocksBought2[noOfInvestments + i] > stocks[0].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[0].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments2[noOfInvestments + i]].changeNoOfStocks(-stocksBought2[noOfInvestments + i]);
                    cashHeld -= (stocksBought2[noOfInvestments + i] * stocks[0].getStockValue());
                }

                if (investments2[noOfInvestments + i] == 1)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Microsoft?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought2[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought2[noOfInvestments + i] < 1) || (stocksBought2[noOfInvestments + i] > stocks[1].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[1].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments2[noOfInvestments + i]].changeNoOfStocks(-stocksBought2[noOfInvestments + i]);
                    cashHeld -= (stocksBought2[noOfInvestments + i] * stocks[1].getStockValue());
                }

                if (investments2[noOfInvestments + i] == 2)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Apple?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought2[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought2[noOfInvestments + i] < 1) || (stocksBought2[noOfInvestments + i] > stocks[2].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[2].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments2[noOfInvestments + i]].changeNoOfStocks(-stocksBought2[noOfInvestments + i]);
                    cashHeld -= (stocksBought2[noOfInvestments + i] * stocks[2].getStockValue());
                }

                if (investments2[noOfInvestments + i] == 3)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Samsung?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought2[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought2[noOfInvestments + i] < 1) || (stocksBought2[noOfInvestments + i] > stocks[3].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[3].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments2[noOfInvestments + i]].changeNoOfStocks(-stocksBought2[noOfInvestments + i]);
                    cashHeld -= (stocksBought2[noOfInvestments + i] * stocks[3].getStockValue());
                }

                if (investments2[noOfInvestments + i] == 4)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Facebook?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought2[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought2[noOfInvestments + i] < 1) || (stocksBought2[noOfInvestments + i] > stocks[4].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[4].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments2[noOfInvestments + i]].changeNoOfStocks(-stocksBought2[noOfInvestments + i]);
                    cashHeld -= (stocksBought2[noOfInvestments + i] * stocks[4].getStockValue());
                }

                if (investments2[noOfInvestments + i] == 5)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Twitter?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought2[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought2[noOfInvestments + i] < 1) || (stocksBought2[noOfInvestments + i] > stocks[5].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[5].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments2[noOfInvestments + i]].changeNoOfStocks(-stocksBought2[noOfInvestments + i]);
                    cashHeld -= (stocksBought2[noOfInvestments + i] * stocks[5].getStockValue());
                }
            }

            noOfInvestments += New;
        }

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");
        System.out.println("We will now be simulating Quarter 2. Enter 0 to continue.");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int Continue3 = Integer.parseInt(inputTest);

        if (Continue3 != 0)
        {
            System.err.println("Please enter 0 to continue");
            System.exit(1);
        }

        double[] percentChange2 = randomChange(stocks);

        System.out.println(" ");
        System.out.println("Are you surprised by this news of 30th June 2021?");
        System.out.println("Regardless, what matters more is how you learn from this news and make better decisions for the next quarter!");
        System.out.println("Let's see how the stock values have changed at the end of this quarter. Enter 0 to continue.");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int Headline2 = Integer.parseInt(inputTest);

        if (Headline2 != 0)
        {
            System.err.println("Please enter 0 to see the end-of-quarter results");
            System.exit(1);
        }

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");
        System.out.println("AT THE END OF QUARTER 2...");

        drawTable(stocks, percentChange2);
        drawPortfolio(noOfInvestments, investments2, stocksBought2, stocks);

        currentPortfolioValue = 0.00;

        for (int i = 0; i < noOfInvestments; i++)
        {
            currentPortfolioValue += stocksBought2[i] * stocks[investments2[i]].getStockValue();
        }

        assetsValue = currentPortfolioValue + cashHeld;

        if (cashHeld < 0)
        {
            System.err.println("You do not have enough cash to buy this combination of stocks!");
            System.exit(1);
        }

        CPVString = String.format("%.2f", currentPortfolioValue);
        CHString = String.format("%.2f", cashHeld);
        AVString = String.format("%.2f", assetsValue);

        System.out.println(" ");
        System.out.println("The value of your portfolio is $" + CPVString);
        System.out.println("The cash (liquid assets) held by you is $" + CHString);
        System.out.println("The value of your total assets is $" + AVString);

        System.out.println(" ");
        if (assetsValue - 1000 > 0)
        {
            System.out.println("Great job! You are currently making an overall profit of $" + String.format("%.2f", (assetsValue - 1000)) + ". Keep up the good work!");
        }
        else if (assetsValue - 1000 == 0)
        {
            System.out.println("You are currently breaking even! You should now try your best to make a profit");
        }
        else
        {
            System.out.println("You are currently making an overall loss of $" + String.format("%.2f", -(assetsValue - 1000)) + ". You need to up your game!");
        }

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");
        System.out.println("Let's move on to Quarter 3. Enter 0 to continue.");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int Continue4 = Integer.parseInt(inputTest);

        if (Continue4 != 0)
        {
            System.err.println("Please enter 0 to continue");
            System.exit(1);
        }

        System.out.println(" ");
        System.out.println("For Quarter 3 (based on these stock value changes)...");

        System.out.println("Do you wish to buy more stocks at your current companies?");
        System.out.println("If yes, enter the number of companies you want to buy more of. If no, enter 0");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int more2 = Integer.parseInt(inputTest);

        if (more2 < 0 || more2 > noOfInvestments)
        {
            System.err.println("Please enter a valid input");
            System.exit(1);
        }

        if (more2 > 0)
        {
            System.out.println(" ");
            System.out.println("You have currently invested in the following companies (denoted by the codes):");
            System.out.println(" ");
            for (int i = 0; i < noOfInvestments; i++)
            {
                System.out.println(investments2[i] + " ");
            }
            System.out.println(" ");

            System.out.println("Now, enter the digits for the companies you want to invest more in");

            for (int j = 0; j < more2; j++)
            {
                System.out.println(" ");
                System.out.print("Digit for company number " + (j+1) + ": ");
                inputTest = keyboard.nextLine();

                if(inputTest.length() != 1)
                {
                    System.err.println("Please enter a single digit");
                    System.exit(1);
                }

                int temp = Integer.parseInt(inputTest);

                if ((temp < 0) || (temp > 5))
                {
                    System.err.println("Please enter a single integer between 0 and 5 (both inclusive).");
                    System.exit(1);
                }

                int check = 0;

                for (int i = 0; i < noOfInvestments; i++)
                {
                    if (temp == investments2[i])
                    {
                        check = 1;
                        break;
                    }
                }

                if (check == 0)
                {
                    System.err.println("You have not invested in this company!");
                    System.exit(1);
                }

                if (temp == 0)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Amazon?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[0].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[0].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 0)
                        {
                            stocksBought2[i] += add;
                        }
                    }
                    stocks[0].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[0].getStockValue());
                }

                if (temp == 1)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Microsoft?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[1].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[1].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 1)
                        {
                            stocksBought2[i] += add;
                        }
                    }
                    stocks[1].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[1].getStockValue());
                }

                if (temp == 2)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Apple?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[2].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[2].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 2)
                        {
                            stocksBought2[i] += add;
                        }
                    }
                    stocks[2].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[2].getStockValue());
                }

                if (temp == 3)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Samsung?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[3].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[3].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 3)
                        {
                            stocksBought2[i] += add;
                        }
                    }
                    stocks[3].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[3].getStockValue());
                }

                if (temp == 4)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Facebook?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[4].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[4].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 4)
                        {
                            stocksBought2[i] += add;
                        }
                    }
                    stocks[4].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[4].getStockValue());
                }

                if (temp == 5)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Twitter?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[5].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[5].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 5)
                        {
                            stocksBought2[i] += add;
                        }
                    }
                    stocks[5].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[5].getStockValue());
                }
            }
        }

        System.out.println(" ");
        System.out.println("Do you wish to sell stocks at your current companies?");
        System.out.println("If yes, enter the number of companies you want to sell stocks of. If no, enter 0");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int sell2 = Integer.parseInt(inputTest);

        if (sell2 < 0 || sell2 > noOfInvestments)
        {
            System.err.println("Please enter a valid input");
            System.exit(1);
        }

        if (sell2 > 0)
        {
            System.out.println(" ");
            System.out.println("You have currently invested in the following companies (denoted by the codes):");
            System.out.println(" ");

            for (int i = 0; i < noOfInvestments; i++)
            {
                System.out.println(investments2[i] + " ");
            }
            System.out.println(" ");

            System.out.println("Now, enter the digits for the companies you want to sell stocks of");

            for (int j = 0; j < sell2; j++)
            {
                System.out.println(" ");
                System.out.print("Digit for company number " + (j+1) + ": ");
                inputTest = keyboard.nextLine();

                if(inputTest.length() != 1)
                {
                    System.err.println("Please enter a single digit");
                    System.exit(1);
                }

                int temp = Integer.parseInt(inputTest);

                if ((temp < 0) || (temp > 5))
                {
                    System.err.println("Please enter a single integer between 0 and 5 (both inclusive).");
                    System.exit(1);
                }

                int check = 0;

                for (int i = 0; i < noOfInvestments; i++)
                {
                    if (temp == investments2[i])
                    {
                        check = 1;
                        break;
                    }
                }

                if (check == 0)
                {
                    System.err.println("You have not invested in this company!");
                    System.exit(1);
                }

                if (temp == 0)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Amazon do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 0)
                        {
                            if ((remove < 1) || (remove > stocksBought2[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought2[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 0)
                        {
                            stocksBought2[i] -= remove;
                        }
                    }
                    stocks[0].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[0].getStockValue());
                }

                if (temp == 1)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Microsoft do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 1)
                        {
                            if ((remove < 1) || (remove > stocksBought2[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought2[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 1)
                        {
                            stocksBought2[i] -= remove;
                        }
                    }
                    stocks[1].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[1].getStockValue());
                }

                if (temp == 2)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Apple do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 2)
                        {
                            if ((remove < 1) || (remove > stocksBought2[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought2[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 2)
                        {
                            stocksBought2[i] -= remove;
                        }
                    }
                    stocks[2].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[2].getStockValue());
                }

                if (temp == 3)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Samsung do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 3)
                        {
                            if ((remove < 1) || (remove > stocksBought2[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought2[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 3)
                        {
                            stocksBought2[i] -= remove;
                        }
                    }
                    stocks[3].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[3].getStockValue());
                }

                if (temp == 4)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Facebook do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 4)
                        {
                            if ((remove < 1) || (remove > stocksBought2[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought2[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 4)
                        {
                            stocksBought2[i] -= remove;
                        }
                    }
                    stocks[4].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[4].getStockValue());
                }

                if (temp == 5)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Twitter do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 5)
                        {
                            if ((remove < 1) || (remove > stocksBought2[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought2[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments2[i] == 5)
                        {
                            stocksBought2[i] -= remove;
                        }
                    }
                    stocks[5].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[5].getStockValue());
                }
            }
        }

        System.out.println(" ");
        System.out.println("How many NEW companies do you wish to buy stocks at (Type 0 if you do not wish to invest in new companies)?");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int New2 = Integer.parseInt(inputTest);

        int[] investments3 = new int[noOfInvestments + New2];
        int[] stocksBought3 = new int[noOfInvestments + New2];

        if (New2 < 0 || New2 > 6 - noOfInvestments)
        {
            System.err.println("Please enter a valid input");
            System.exit(1);
        }

        for (int i = 0; i < noOfInvestments; i++)
        {
            investments3[i] = investments2[i];
            stocksBought3[i] = stocksBought2[i];
        }

        if (New2 > 0)
        {
            System.out.println(" ");
            System.out.println("You have currently invested in the following companies (denoted by the codes):");
            System.out.println(" ");

            for (int i = 0; i < noOfInvestments; i++)
            {
                System.out.println(investments2[i] + " ");
            }
            System.out.println(" ");

            System.out.println();
            System.out.println("Now, enter the digits for the new companies you want to invest in");

            for (int i = 0; i < New2; i++)
            {
                System.out.println(" ");
                System.out.print("Digit for new company number " + (i+1) + ": ");
                inputTest = keyboard.nextLine();

                if(inputTest.length() != 1)
                {
                    System.err.println("Please enter a single digit");
                    System.exit(1);
                }

                investments3[noOfInvestments + i] = Integer.parseInt(inputTest);

                for (int a = 0; a < noOfInvestments; a++)
                {
                    if (investments3[noOfInvestments + i] == investments2[a])
                    {
                        if(stocksBought2[a] != 0)
                        {
                            System.err.println("You have already invested in this company!");
                            System.exit(1);
                        }
                    }
                }

                if ((investments3[noOfInvestments + i] < 0) || (investments3[noOfInvestments + i] > 5))
                {
                    System.err.println("Please enter a single integer between 0 and 5 (both inclusive).");
                    System.exit(1);
                }
            }

            for (int i = 0; i < New2; i++)
            {
                if (investments3[noOfInvestments + i] == 0)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Amazon?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought3[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought3[noOfInvestments + i] < 1) || (stocksBought3[noOfInvestments + i] > stocks[0].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[0].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments3[noOfInvestments + i]].changeNoOfStocks(-stocksBought3[noOfInvestments + i]);
                    cashHeld -= (stocksBought3[noOfInvestments + i] * stocks[0].getStockValue());
                }

                if (investments3[noOfInvestments + i] == 1)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Microsoft?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought3[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought3[noOfInvestments + i] < 1) || (stocksBought3[noOfInvestments + i] > stocks[1].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[1].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments3[noOfInvestments + i]].changeNoOfStocks(-stocksBought3[noOfInvestments + i]);
                    cashHeld -= (stocksBought3[noOfInvestments + i] * stocks[1].getStockValue());
                }

                if (investments3[noOfInvestments + i] == 2)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Apple?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought3[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought3[noOfInvestments + i] < 1) || (stocksBought3[noOfInvestments + i] > stocks[2].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[2].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments3[noOfInvestments + i]].changeNoOfStocks(-stocksBought3[noOfInvestments + i]);
                    cashHeld -= (stocksBought3[noOfInvestments + i] * stocks[2].getStockValue());
                }

                if (investments3[noOfInvestments + i] == 3)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Samsung?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought3[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought3[noOfInvestments + i] < 1) || (stocksBought3[noOfInvestments + i] > stocks[3].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[3].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments3[noOfInvestments + i]].changeNoOfStocks(-stocksBought3[noOfInvestments + i]);
                    cashHeld -= (stocksBought3[noOfInvestments + i] * stocks[3].getStockValue());
                }

                if (investments3[noOfInvestments + i] == 4)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Facebook?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought3[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought3[noOfInvestments + i] < 1) || (stocksBought3[noOfInvestments + i] > stocks[4].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[4].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments3[noOfInvestments + i]].changeNoOfStocks(-stocksBought3[noOfInvestments + i]);
                    cashHeld -= (stocksBought3[noOfInvestments + i] * stocks[4].getStockValue());
                }

                if (investments3[noOfInvestments + i] == 5)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Twitter?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought3[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought3[noOfInvestments + i] < 1) || (stocksBought3[noOfInvestments + i] > stocks[5].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[5].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments3[noOfInvestments + i]].changeNoOfStocks(-stocksBought3[noOfInvestments + i]);
                    cashHeld -= (stocksBought3[noOfInvestments + i] * stocks[5].getStockValue());
                }
            }

            noOfInvestments += New2;
        }

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");
        System.out.println("We will now be simulating Quarter 3. Enter 0 to continue.");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int Continue5 = Integer.parseInt(inputTest);

        if (Continue5 != 0)
        {
            System.err.println("Please enter 0 to continue");
            System.exit(1);
        }

        double[] percentChange3 = randomChange(stocks);

        System.out.println(" ");
        System.out.println("Are you surprised by this news of 30th September 2021?");
        System.out.println("Regardless, what matters more is how you learn from this news and make better decisions for the next quarter!");
        System.out.println("Let's see how the stock values have changed at the end of this quarter. Enter 0 to continue.");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int Headline3 = Integer.parseInt(inputTest);

        if (Headline3 != 0)
        {
            System.err.println("Please enter 0 to see the end-of-quarter results");
            System.exit(1);
        }

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");
        System.out.println("AT THE END OF QUARTER 3...");

        drawTable(stocks, percentChange3);
        drawPortfolio(noOfInvestments, investments3, stocksBought3, stocks);

        currentPortfolioValue = 0.00;

        for (int i = 0; i < noOfInvestments; i++)
        {
            currentPortfolioValue += stocksBought3[i] * stocks[investments3[i]].getStockValue();
        }

        assetsValue = currentPortfolioValue + cashHeld;

        if (cashHeld < 0)
        {
            System.err.println("You do not have enough cash to buy this combination of stocks!");
            System.exit(1);
        }

        CPVString = String.format("%.2f", currentPortfolioValue);
        CHString = String.format("%.2f", cashHeld);
        AVString = String.format("%.2f", assetsValue);

        System.out.println(" ");
        System.out.println("The value of your portfolio is $" + CPVString);
        System.out.println("The cash (liquid assets) held by you is $" + CHString);
        System.out.println("The value of your total assets is $" + AVString);

        System.out.println(" ");
        if (assetsValue - 1000 > 0)
        {
            System.out.println("Great job! You are currently making an overall profit of $" + String.format("%.2f", (assetsValue - 1000)) + ". Keep up the good work!");
        }
        else if (assetsValue - 1000 == 0)
        {
            System.out.println("You are currently breaking even! You should now try your best to make a profit");
        }
        else
        {
            System.out.println("You are currently making an overall loss of $" + String.format("%.2f", -(assetsValue - 1000)) + ". You need to up your game!");
        }

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");
        System.out.println("Let's move on to Quarter 4. Enter 0 to continue.");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int Continue6 = Integer.parseInt(inputTest);

        if (Continue6 != 0)
        {
            System.err.println("Please enter 0 to continue");
            System.exit(1);
        }

        System.out.println(" ");
        System.out.println("For Quarter 4 (based on these stock value changes)...");

        System.out.println("Do you wish to buy more stocks at your current companies?");
        System.out.println("If yes, enter the number of companies you want to buy more of. If no, enter 0");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int more3 = Integer.parseInt(inputTest);

        if (more3 < 0 || more3 > noOfInvestments)
        {
            System.err.println("Please enter a valid input");
            System.exit(1);
        }

        if (more3 > 0)
        {
            System.out.println(" ");
            System.out.println("You have currently invested in the following companies (denoted by the codes):");
            System.out.println(" ");
            for (int i = 0; i < noOfInvestments; i++)
            {
                System.out.println(investments3[i] + " ");
            }
            System.out.println(" ");

            System.out.println("Now, enter the digits for the companies you want to invest more in");

            for (int j = 0; j < more3; j++)
            {
                System.out.println(" ");
                System.out.print("Digit for company number " + (j+1) + ": ");
                inputTest = keyboard.nextLine();

                if(inputTest.length() != 1)
                {
                    System.err.println("Please enter a single digit");
                    System.exit(1);
                }

                int temp = Integer.parseInt(inputTest);

                if ((temp < 0) || (temp > 5))
                {
                    System.err.println("Please enter a single integer between 0 and 5 (both inclusive).");
                    System.exit(1);
                }

                int check = 0;

                for (int i = 0; i < noOfInvestments; i++)
                {
                    if (temp == investments3[i])
                    {
                        check = 1;
                        break;
                    }
                }

                if (check == 0)
                {
                    System.err.println("You have not invested in this company!");
                    System.exit(1);
                }

                if (temp == 0)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Amazon?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[0].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[0].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 0)
                        {
                            stocksBought3[i] += add;
                        }
                    }
                    stocks[0].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[0].getStockValue());
                }

                if (temp == 1)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Microsoft?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[1].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[1].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 1)
                        {
                            stocksBought3[i] += add;
                        }
                    }
                    stocks[1].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[1].getStockValue());
                }

                if (temp == 2)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Apple?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[2].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[2].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 2)
                        {
                            stocksBought3[i] += add;
                        }
                    }
                    stocks[2].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[2].getStockValue());
                }

                if (temp == 3)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Samsung?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[3].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[3].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 3)
                        {
                            stocksBought3[i] += add;
                        }
                    }
                    stocks[3].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[3].getStockValue());
                }

                if (temp == 4)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Facebook?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[4].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[4].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 4)
                        {
                            stocksBought3[i] += add;
                        }
                    }
                    stocks[4].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[4].getStockValue());
                }

                if (temp == 5)
                {
                    System.out.println(" ");
                    System.out.println("How many more stocks do you want to buy at Twitter?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int add = Integer.parseInt(inputTest);

                    if ((add < 1) || (add > stocks[5].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[5].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 5)
                        {
                            stocksBought3[i] += add;
                        }
                    }
                    stocks[5].changeNoOfStocks(-add);
                    cashHeld -= (add * stocks[5].getStockValue());
                }
            }
        }

        System.out.println(" ");
        System.out.println("Do you wish to sell stocks at your current companies?");
        System.out.println("If yes, enter the number of companies you want to sell stocks of. If no, enter 0");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int sell3 = Integer.parseInt(inputTest);

        if (sell3 < 0 || sell3 > noOfInvestments)
        {
            System.err.println("Please enter a valid input");
            System.exit(1);
        }

        if (sell3 > 0)
        {
            System.out.println(" ");
            System.out.println("You have currently invested in the following companies (denoted by the codes):");
            System.out.println(" ");

            for (int i = 0; i < noOfInvestments; i++)
            {
                System.out.println(investments3[i] + " ");
            }
            System.out.println(" ");

            System.out.println("Now, enter the digits for the companies you want to sell stocks of");

            for (int j = 0; j < sell3; j++)
            {
                System.out.println(" ");
                System.out.print("Digit for company number " + (j+1) + ": ");
                inputTest = keyboard.nextLine();

                if(inputTest.length() != 1)
                {
                    System.err.println("Please enter a single digit");
                    System.exit(1);
                }

                int temp = Integer.parseInt(inputTest);

                if ((temp < 0) || (temp > 5))
                {
                    System.err.println("Please enter a single integer between 0 and 5 (both inclusive).");
                    System.exit(1);
                }

                int check = 0;

                for (int i = 0; i < noOfInvestments; i++)
                {
                    if (temp == investments3[i])
                    {
                        check = 1;
                        break;
                    }
                }

                if (check == 0)
                {
                    System.err.println("You have not invested in this company!");
                    System.exit(1);
                }

                if (temp == 0)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Amazon do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 0)
                        {
                            if ((remove < 1) || (remove > stocksBought3[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought3[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 0)
                        {
                            stocksBought3[i] -= remove;
                        }
                    }
                    stocks[0].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[0].getStockValue());
                }

                if (temp == 1)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Microsoft do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 1)
                        {
                            if ((remove < 1) || (remove > stocksBought3[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought3[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 1)
                        {
                            stocksBought3[i] -= remove;
                        }
                    }
                    stocks[1].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[1].getStockValue());
                }

                if (temp == 2)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Apple do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 2)
                        {
                            if ((remove < 1) || (remove > stocksBought3[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought3[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 2)
                        {
                            stocksBought3[i] -= remove;
                        }
                    }
                    stocks[2].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[2].getStockValue());
                }

                if (temp == 3)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Samsung do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 3)
                        {
                            if ((remove < 1) || (remove > stocksBought3[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought3[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 3)
                        {
                            stocksBought3[i] -= remove;
                        }
                    }
                    stocks[3].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[3].getStockValue());
                }

                if (temp == 4)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Facebook do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 4)
                        {
                            if ((remove < 1) || (remove > stocksBought3[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought3[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 4)
                        {
                            stocksBought3[i] -= remove;
                        }
                    }
                    stocks[4].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[4].getStockValue());
                }

                if (temp == 5)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks of Twitter do you want to sell?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    int remove = Integer.parseInt(inputTest);

                    for (int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 5)
                        {
                            if ((remove < 1) || (remove > stocksBought3[i]))
                            {
                                System.err.println("Please enter a single positive integer between 1 and " + stocksBought3[i] + " (both inclusive).");
                                System.exit(1);
                            }
                        }
                    }

                    for(int i = 0; i < noOfInvestments; i++)
                    {
                        if (investments3[i] == 5)
                        {
                            stocksBought3[i] -= remove;
                        }
                    }
                    stocks[5].changeNoOfStocks(remove);
                    cashHeld += (remove * stocks[5].getStockValue());
                }
            }
        }

        System.out.println(" ");
        System.out.println("How many NEW companies do you wish to buy stocks at (Type 0 if you do not wish to invest in new companies)?");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int New3 = Integer.parseInt(inputTest);

        int[] investments4 = new int[noOfInvestments + New3];
        int[] stocksBought4 = new int[noOfInvestments + New3];

        if (New3 < 0 || New3 > 6 - noOfInvestments)
        {
            System.err.println("Please enter a valid input");
            System.exit(1);
        }

        for (int i = 0; i < noOfInvestments; i++)
        {
            investments4[i] = investments3[i];
            stocksBought4[i] = stocksBought3[i];
        }

        if (New3 > 0)
        {
            System.out.println(" ");
            System.out.println("You have currently invested in the following companies (denoted by the codes):");
            System.out.println(" ");

            for (int i = 0; i < noOfInvestments; i++)
            {
                System.out.println(investments3[i] + " ");
            }
            System.out.println(" ");

            System.out.println();
            System.out.println("Now, enter the digits for the new companies you want to invest in");

            for (int i = 0; i < New3; i++)
            {
                System.out.println(" ");
                System.out.print("Digit for new company number " + (i+1) + ": ");
                inputTest = keyboard.nextLine();

                if(inputTest.length() != 1)
                {
                    System.err.println("Please enter a single digit");
                    System.exit(1);
                }

                investments4[noOfInvestments + i] = Integer.parseInt(inputTest);

                for (int a = 0; a < noOfInvestments; a++)
                {
                    if (investments4[noOfInvestments + i] == investments3[a])
                    {
                        if(stocksBought3[a] != 0)
                        {
                            System.err.println("You have already invested in this company!");
                            System.exit(1);
                        }
                    }
                }

                if ((investments4[noOfInvestments + i] < 0) || (investments4[noOfInvestments + i] > 5))
                {
                    System.err.println("Please enter a single integer between 0 and 5 (both inclusive).");
                    System.exit(1);
                }
            }

            for (int i = 0; i < New3; i++)
            {
                if (investments4[noOfInvestments + i] == 0)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Amazon?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought4[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought4[noOfInvestments + i] < 1) || (stocksBought4[noOfInvestments + i] > stocks[0].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[0].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments4[noOfInvestments + i]].changeNoOfStocks(-stocksBought4[noOfInvestments + i]);
                    cashHeld -= (stocksBought4[noOfInvestments + i] * stocks[0].getStockValue());
                }

                if (investments4[noOfInvestments + i] == 1)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Microsoft?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought4[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought4[noOfInvestments + i] < 1) || (stocksBought4[noOfInvestments + i] > stocks[1].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[1].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments4[noOfInvestments + i]].changeNoOfStocks(-stocksBought4[noOfInvestments + i]);
                    cashHeld -= (stocksBought4[noOfInvestments + i] * stocks[1].getStockValue());
                }

                if (investments4[noOfInvestments + i] == 2)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Apple?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought4[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought4[noOfInvestments + i] < 1) || (stocksBought4[noOfInvestments + i] > stocks[2].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[2].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments4[noOfInvestments + i]].changeNoOfStocks(-stocksBought4[noOfInvestments + i]);
                    cashHeld -= (stocksBought4[noOfInvestments + i] * stocks[2].getStockValue());
                }

                if (investments4[noOfInvestments + i] == 3)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Samsung?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought4[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought4[noOfInvestments + i] < 1) || (stocksBought4[noOfInvestments + i] > stocks[3].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[3].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments4[noOfInvestments + i]].changeNoOfStocks(-stocksBought4[noOfInvestments + i]);
                    cashHeld -= (stocksBought4[noOfInvestments + i] * stocks[3].getStockValue());
                }

                if (investments4[noOfInvestments + i] == 4)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Facebook?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought4[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought4[noOfInvestments + i] < 1) || (stocksBought4[noOfInvestments + i] > stocks[4].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[4].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments4[noOfInvestments + i]].changeNoOfStocks(-stocksBought4[noOfInvestments + i]);
                    cashHeld -= (stocksBought4[noOfInvestments + i] * stocks[4].getStockValue());
                }

                if (investments4[noOfInvestments + i] == 5)
                {
                    System.out.println(" ");
                    System.out.println("How many stocks do you want to buy at Twitter?");
                    inputTest = keyboard.nextLine();

                    if(!(inputTest.length() == 1 || inputTest.length() == 2))
                    {
                        System.err.println("Please enter an appropriate number of digits");
                        System.exit(1);
                    }

                    stocksBought4[noOfInvestments + i] = Integer.parseInt(inputTest);

                    if ((stocksBought4[noOfInvestments + i] < 1) || (stocksBought4[noOfInvestments + i] > stocks[5].getNumberOfStocks()))
                    {
                        System.err.println("Please enter a single positive integer between 1 and " + stocks[5].getNumberOfStocks() + " (both inclusive).");
                        System.exit(1);
                    }

                    stocks[investments4[noOfInvestments + i]].changeNoOfStocks(-stocksBought4[noOfInvestments + i]);
                    cashHeld -= (stocksBought4[noOfInvestments + i] * stocks[5].getStockValue());
                }
            }

            noOfInvestments += New3;
        }

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");
        System.out.println("We will now be simulating Quarter 4. Enter 0 to continue.");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int Continue7 = Integer.parseInt(inputTest);

        if (Continue7 != 0)
        {
            System.err.println("Please enter 0 to continue");
            System.exit(1);
        }

        double[] percentChange4 = randomChange(stocks);

        System.out.println(" ");
        System.out.println("Are you surprised by this news of 31st December 2021?");
        System.out.println("Regardless, what matters more is how you learn from this news and make better decisions for next year!");
        System.out.println("Let's see how the stock values have changed at the end of this quarter (and year). Enter 0 to continue.");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int Headline4 = Integer.parseInt(inputTest);

        if (Headline4 != 0)
        {
            System.err.println("Please enter 0 to see the end-of-quarter results");
            System.exit(1);
        }

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");
        System.out.println("AT THE END OF QUARTER 4...");

        drawTable(stocks, percentChange4);
        drawPortfolio(noOfInvestments, investments4, stocksBought4, stocks);

        currentPortfolioValue = 0.00;

        for (int i = 0; i < noOfInvestments; i++)
        {
            currentPortfolioValue += stocksBought4[i] * stocks[investments4[i]].getStockValue();
        }

        assetsValue = currentPortfolioValue + cashHeld;

        if (cashHeld < 0)
        {
            System.err.println("You do not have enough cash to buy this combination of stocks!");
            System.exit(1);
        }

        CPVString = String.format("%.2f", currentPortfolioValue);
        CHString = String.format("%.2f", cashHeld);
        AVString = String.format("%.2f", assetsValue);

        System.out.println(" ");
        System.out.println("The value of your portfolio is $" + CPVString);
        System.out.println("The cash (liquid assets) held by you is $" + CHString);
        System.out.println("The value of your total assets is $" + AVString);

        System.out.println(" ");
        if (assetsValue - 1000 > 0)
        {
            System.out.println("Great job! You are currently making an overall profit of $" + String.format("%.2f", (assetsValue - 1000)) + ". Keep up the good work!");
        }
        else if (assetsValue - 1000 == 0)
        {
            System.out.println("You are currently breaking even! You should now try your best to make a profit");
        }
        else
        {
            System.out.println("You are currently making an overall loss of $" + String.format("%.2f", -(assetsValue - 1000)) + ". You need to up your game!");
        }

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");
        System.out.println("The Wall Street Simulator has ended. Enter 0 to see results");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int Continue8 = Integer.parseInt(inputTest);

        if(Continue8 != 0)
        {
            System.err.println("Please enter 0 to see the results!");
            System.exit(1);
        }

        System.out.println(" ");
        System.out.println("*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*----------*");

        System.out.println(" ");
        System.out.println("THE GAME HAS ENDED: 2021 HAS ENDED");
        System.out.println("All your assets will be liquidated now (converted into cash)");

        System.out.println(" ");
        System.out.println("You currently have $" + AVString + " in cash");

        double profit = assetsValue - 1000.00;

        String profitString = String.format("%.2f", profit);
        String lossString = String.format("%.2f", -profit);

        if (profit > 0)
        {
            System.out.println(" ");
            System.out.println("You have made a profit of $" + profitString);
            System.out.println("CONGRATS! YOU HAVE WON THE GAME!");
        }
        else if (profit == 0)
        {
            System.out.println(" ");
            System.out.println("You have broken even (profit = $0)");
            System.out.println("CONGRATS! YOU HAVE WON THE GAME!");
        }
        else
        {
            System.out.println(" ");
            System.out.println("You have suffered a loss of $" + lossString);
            System.out.println("SORRY, YOU HAVE LOST THE GAME! BETTER LUCK NEXT TIME!");
        }

        System.out.println(" ");
        System.out.println("Do you wish to arrange each company's return to you in descending order?");
        System.out.println("Please enter 1 for YES and 0 for NO");
        inputTest = keyboard.nextLine();

        if(inputTest.length() != 1)
        {
            System.err.println("Please enter a single digit");
            System.exit(1);
        }

        int sort = Integer.parseInt(inputTest);

        if (!(sort == 0 || sort == 1))
        {
            System.err.println("Please enter either 0 or 1");
            System.exit(1);
        }

        if (sort == 1)
        {
            int[] companyReturns = new int[noOfInvestments];

            for (int i = 0; i < noOfInvestments; i++)
            {
                companyReturns[i] = (int)(stocksBought4[i] * stocks[investments4[i]].getStockValue());
            }

            int[] investments5 = new int [noOfInvestments];

            for (int i = 0; i < noOfInvestments; i++)
            {
                investments5[i] = investments4[i];
            }

            mergeSort(companyReturns, investments5);

            System.out.println(" ");
            for (int i = 0; i < noOfInvestments; i++)
            {
                if (investments5[i] == 0)
                {
                    System.out.println("#" + (i + 1) + ": AMAZON     (rounded return value = " + companyReturns[i] + ")");
                }

                if (investments5[i] == 1)
                {
                    System.out.println("#" + (i + 1) + ": MICROSOFT     (rounded return value = " + companyReturns[i] + ")");
                }

                if (investments5[i] == 2)
                {
                    System.out.println("#" + (i + 1) + ": APPLE     (rounded return value = " + companyReturns[i] + ")");
                }

                if (investments5[i] == 3)
                {
                    System.out.println("#" + (i + 1) + ": SAMSUNG     (rounded return value = " + companyReturns[i] + ")");
                }

                if (investments5[i] == 4)
                {
                    System.out.println("#" + (i + 1) + ": FACEBOOK     (rounded return value = " + companyReturns[i] + ")");
                }

                if (investments5[i] == 5)
                {
                    System.out.println("#" + (i + 1) + ": TWITTER     (rounded return value = " + companyReturns[i] + ")");
                }
            }
        }
    }

    public static double[] randomChange(Stock[] stocks)
    {
        double[] tempValue = new double[6];

        for (int i = 0; i < 6; i++)
        {
            tempValue[i] = stocks[i].getStockValue();
        }

        int decider = (int)(Math.random() * 6);

        if (decider == 0)
        {
            tempValue[0] *= 1.15;
            tempValue[1] *= 0.87;
            tempValue[2] *= 0.85;
            tempValue[3] *= 0.90;
            tempValue[4] *= 1.10;
            tempValue[5] *= 1.10;

            System.out.println(" ");
            System.out.println("What happened during the Quarter?");
            System.out.println("HEADLINE: Recent lockdown boosts online services and social media usage");
        }
        else if (decider == 1)
        {
            tempValue[0] *= 1.20;
            tempValue[1] *= 1.15;
            tempValue[2] *= 0.95;
            tempValue[3] *= 0.80;
            tempValue[4] *= 1.12;
            tempValue[5] *= 0.85;

            System.out.println(" ");
            System.out.println("What happened during the Quarter?");
            System.out.println("HEADLINE: New gaming consoles released by Amazon, Microsoft and Facebook during high demand");
        }
        else if (decider == 2)
        {
            tempValue[0] *= 0.84;
            tempValue[1] *= 1.05;
            tempValue[2] *= 1.20;
            tempValue[3] *= 1.17;
            tempValue[4] *= 0.79;
            tempValue[5] *= 0.91;

            System.out.println(" ");
            System.out.println("What happened during the Quarter?");
            System.out.println("HEADLINE: Foldable smartphones introduced by Apple, Samsung, and Microsoft");
        }
        else if (decider == 3)
        {
            tempValue[0] *= 0.91;
            tempValue[1] *= 0.85;
            tempValue[2] *= 0.83;
            tempValue[3] *= 0.78;
            tempValue[4] *= 1.21;
            tempValue[5] *= 1.30;

            System.out.println(" ");
            System.out.println("What happened during the Quarter?");
            System.out.println("HEADLINE: Biden gets impeached! Social media conversations increase while consumer demand reduces");
        }
        else if (decider == 4)
        {
            tempValue[0] *= 1.19;
            tempValue[1] *= 1.15;
            tempValue[2] *= 1.25;
            tempValue[3] *= 1.20;
            tempValue[4] *= 1.29;
            tempValue[5] *= 1.22;

            System.out.println(" ");
            System.out.println("What happened during the Quarter?");
            System.out.println("HEADLINE: Heavy borrowing from abroad boosts firm investments. Government invests more in Research & Development");
        }
        else if (decider == 5)
        {
            tempValue[0] *= 0.80;
            tempValue[1] *= 0.71;
            tempValue[2] *= 0.86;
            tempValue[3] *= 0.72;
            tempValue[4] *= 0.77;
            tempValue[5] *= 0.83;

            System.out.println(" ");
            System.out.println("What happened during the Quarter?");
            System.out.println("HEADLINE: Pandemic worsens! Restrictions become stricter and firm's cut down on production");
        }

        double[] change = new double[6];
        double[] percentChange = new double[6];

        for (int i = 0; i < 6; i++)
        {
            change[i] = tempValue[i] - stocks[i].getStockValue();
            percentChange[i] = (change[i] / stocks[i].getStockValue()) * 100;
        }

        for (int i = 0; i < 6; i++)
        {
            stocks[i].changeStockValue(change[i]);
        }

        return percentChange;
    }

    public static void drawTable(Stock[] stocks, double[] percentChange)
    {
        String[] valueWithTwoDecimals = new String[6];
        String[] percentWithTwoDecimals = new String[6];

        for (int i = 0; i < 6; i++)
        {
            valueWithTwoDecimals[i] = String.format("%.2f", stocks[i].getStockValue());
            percentWithTwoDecimals[i] = String.format("%.0f", percentChange[i]);
        }

        System.out.println(" ");
        System.out.println(" COMPANY NAME  |   STOCK VALUE  |  NUMBER OF STOCKS AVAILABLE  |  PERCENT CHANGE  |");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("    Amazon     |     $" + valueWithTwoDecimals[0] + "     |             " + stocks[0].getNumberOfStocks() + "               |      " + percentWithTwoDecimals[0] + " %       |");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("   Microsoft   |     $" + valueWithTwoDecimals[1] + "     |             " + stocks[1].getNumberOfStocks() + "               |      " + percentWithTwoDecimals[1] + " %       |");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("     Apple     |     $" + valueWithTwoDecimals[2] + "     |             " + stocks[2].getNumberOfStocks() + "               |      " + percentWithTwoDecimals[2] + " %       |");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("    Samsung    |     $" + valueWithTwoDecimals[3] + "     |             " + stocks[3].getNumberOfStocks() + "               |      " + percentWithTwoDecimals[3] + " %       |");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("    Facebook   |     $" + valueWithTwoDecimals[4] + "     |             " + stocks[4].getNumberOfStocks() + "               |      " + percentWithTwoDecimals[4] + " %       |");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("    Twitter    |     $" + valueWithTwoDecimals[5] + "     |             " + stocks[5].getNumberOfStocks() + "               |      " + percentWithTwoDecimals[5] + " %       |");
        System.out.println("----------------------------------------------------------------------------------");
    }

    public static void drawPortfolio(int noOfInvestments, int[] investments, int[] stocksBought, Stock[] stocks)
    {
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("YOUR PORTFOLIO");
        System.out.println("--------------");
        System.out.println(" ");
        System.out.println(" COMPANY NAME  |  NUMBER OF STOCKS BOUGHT  |  TOTAL COMPANY RETURN  |");
        System.out.println("--------------------------------------------------------------------");

        String[] companyReturns = new String[noOfInvestments];

        for (int i = 0; i < noOfInvestments; i++)
        {
            companyReturns[i] = String.format("%.2f", (stocksBought[i] * stocks[investments[i]].getStockValue()));
        }

        for (int i = 0; i < noOfInvestments; i++)
        {
            if (investments[i] == 0)
            {
                System.out.println("    AMAZON     |             " + stocksBought[i] + "             |         $" + companyReturns[i] + "         |");
                System.out.println("--------------------------------------------------------------------");
            }

            if (investments[i] == 1)
            {
                System.out.println("   MICROSOFT   |             " + stocksBought[i] + "             |         $" + companyReturns[i] + "         |");
                System.out.println("--------------------------------------------------------------------");
            }

            if (investments[i] == 2)
            {
                System.out.println("     APPLE     |             " + stocksBought[i] + "             |         $" + companyReturns[i] + "         |");
                System.out.println("--------------------------------------------------------------------");
            }

            if (investments[i] == 3)
            {
                System.out.println("    SAMSUNG    |             " + stocksBought[i] + "             |         $" + companyReturns[i] + "         |");
                System.out.println("--------------------------------------------------------------------");
            }

            if (investments[i] == 4)
            {
                System.out.println("   FACEBOOK    |             " + stocksBought[i] + "             |         $" + companyReturns[i] + "         |");
                System.out.println("--------------------------------------------------------------------");
            }

            if (investments[i] == 5)
            {
                System.out.println("    TWITTER    |             " + stocksBought[i] + "             |         $" + companyReturns[i] + "         |");
                System.out.println("--------------------------------------------------------------------");
            }
        }
    }

    public static void mergeSort(int[] companyReturns, int[] investments)
    {
        mergeSortHelper(companyReturns, investments, 0, companyReturns.length);
    }

    public static void mergeSortHelper(int[] arr, int[] arr2, int low, int high)
    {
        if (high - low <= 1)
        {
            return;
        }
        else
        {
            int mid = low + (high - low) / 2;
            mergeSortHelper(arr, arr2, low, mid);
            mergeSortHelper(arr, arr2, mid, high);
            merge(arr, arr2, low, high);
        }
    }

    public static void merge(int[] arr, int[] arr2, int low, int high)
    {
        int mid = low + (high - low) / 2;
        int[] merged = new int[high - low];
        int[] merged2 = new int[high - low];
        int low_i = low;
        int upp_i = mid;

        for (int mer_i = 0; mer_i < merged.length; mer_i++)
        {
            if (low_i == mid)
            {
                while (upp_i < high)
                {
                    merged[mer_i] = arr[upp_i];
                    merged2[mer_i] = arr2[upp_i];
                    upp_i++;
                    mer_i++;
                }
                break;
            }
            else if (upp_i == high)
            {
                while (low_i < mid)
                {
                    merged[mer_i] = arr[low_i];
                    merged2[mer_i] = arr2[low_i];
                    low_i++;
                    mer_i++;
                }
                break;
            }
            else if (arr[low_i] > arr[upp_i])
            {
                merged[mer_i] = arr[low_i];
                merged2[mer_i] = arr2[low_i];
                low_i++;
            }
            else
            {
                merged[mer_i] = arr[upp_i];
                merged2[mer_i] = arr2[upp_i];
                upp_i++;
            }
        }

        for (int i = 0; i < merged.length; i++)
        {
            arr[low + i] = merged[i];
            arr2[low + i] = merged2[i];
        }
    }
}



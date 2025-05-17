public class Stock
{
    private String company;
    private double stockValue;
    private int numberOfStocks;

    public Stock(String name, double value, int num)
    {
        this.company = name;
        this.stockValue = value;
        this.numberOfStocks = num;
    }

    public String getCompany()
    {
        return this.company;
    }

    public double getStockValue()
    {
        return this.stockValue;
    }

    public int getNumberOfStocks()
    {
        return this.numberOfStocks;
    }

    public void changeStockValue(double change)
    {
        this.stockValue += change;
    }

    public void changeNoOfStocks(int change)
    {
        this.numberOfStocks += change;
    }
}

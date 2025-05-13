
save_plots = FALSE

set.seed(0) 


# Problem 4: An example MC simultation: 
#
niter = 1e5 # number of simulations of our price process
below = rep(0,niter) # indice as to whether we have to liquidate our portfolio during this 45 days
set.seed(2009)
for( i in 1:niter ){
  r = rnorm( 45, mean=0.05/253, sd=0.23/sqrt(253) )
  logPrice = log(1e6) + cumsum(r) # the time series of portfolio values
  minlogP = min(logPrice)
  below[i] = as.numeric(minlogP < log(950000))
}
mean(below)


# Problems 5-7:
#
niter = 1.e5 # number of simulations of our price process
n_days_simulation = 100

profit_target = 1.e6 + 100000.
log_profit_target = log(profit_target) 

stop_loss = 1.e6 - 50000.
log_stop_loss = log(stop_loss) 

above = rep(0,niter) # indices as to whether we have to liquidate our portfolio during this 100 days due to a profit 
below = rep(0,niter) # indices as to whether we have to liquidate our portfolio during this 100 days due to a loss
middle = rep(0,niter) # we don't liquidate our portfolio but hold till the end of the time frame 
profit = rep(0,niter)
strat_return = rep(0,niter) # over the 100 days 
set.seed(2009)
for( i in 1:niter ){
  r = rnorm( n_days_simulation, mean=0.05/253, sd=0.23/sqrt(253) ) # trade for n_days_simulation days 
  logPrice = log(1.e6) + cumsum(r) # the time series of portfolio values
  minlogP = min(logPrice)
  maxlogP = max(logPrice)
  
  # Find out whether either of the stoploss/profit targets are triggered during this MC price history: 
  #
  sl_triggered = minlogP <= log_stop_loss
  pt_triggered = maxlogP >= log_profit_target
  
  # only stoploss triggered in our MC simulation of price: 
  # 
  if( sl_triggered & !pt_triggered ){ 
    below[i] = 1
    profit[i] = -50000 # at the moment the value crosses below 950,000 we must exit with a loss of 50K
    days_in_trade = which( logPrice <= log_stop_loss )[1] 
  }
  
  # only profit target triggered in our MC simulation of price: 
  # 
  if( !sl_triggered & pt_triggered ){ 
    above[i] = 1
    profit[i] = +100000 # at the moment the value crosses above 1,100,000 we exit with a profit of 100K
    days_in_trade = which( logPrice >= log_profit_target )[1] 
  }
  
  # both values triggered ... need to find out which one happend first
  #
  if( sl_triggered & pt_triggered ){ 
    days_in_trade_to_min = which( logPrice <= log_stop_loss )[1] 
    days_in_trade_to_max = which( logPrice >= log_profit_target )[1] 
    
    if( days_in_trade_to_min < days_in_trade_to_max ){ # Minimum happens first and we hit our stoploss: 
      below[i] = 1
      profit[i] = -50000
      days_in_trade = days_in_trade_to_min
    }else{                                             # Maximum happens first and we hit our profit target: 
      above[i] = 1 
      profit[i] = +100000
      days_in_trade = days_in_trade_to_max
    }
  }
  
  # neither values triggered: 
  if( !sl_triggered & !pt_triggered ){
    middle[i] = 1
    profit[i] = exp(logPrice[n_days_simulation]) - 1.e6 # we get how much we are above / below 1
    days_in_trade = n_days_simulation
  }
  
  # Compute returns: 
  strat_return[i] = profit[i] / 50000.
  strat_return[i] = strat_return[i] / days_in_trade # convert the raw return into daily returns 
}
stopifnot( abs( mean(above)+mean(below)+mean(middle) - 1) < 1.e-6 )
print(mean(above))
print(mean(below))
print(mean(profit))
print(mean(strat_return))




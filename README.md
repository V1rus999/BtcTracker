# BtcTracker
Bitcoin tracker using kotlin

Gathers bitcoin pricing information from different exchanges every 15 minutes and then saves the values into a CSV file. This file can then be opened in Excel and analyzed.

Fiat information is also added to every ticker in the CSV file. The idea is to run this for a few weeks and gather historic analytic data.

# Exchanges used for now

* Cryptowatch
* Luno (For ZAR)

# Usage

Run the jar and a data folder will be created where the CSV files will be stored (one file is created each run)

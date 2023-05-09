DROP DATABASE IF EXISTS postgres;
CREATE DATABASE postgres;
\c postgres
DROP TABLE IF EXISTS exchangeRates;
DROP TABLE IF EXISTS currencies;
CREATE TABLE currencies (
    currencyId SERIAL PRIMARY KEY,
    currencySymbol VARCHAR(50) UNIQUE NOT NULL
);
CREATE TABLE exchangeRates (
    exchangeId SERIAL PRIMARY KEY,
    currencyId INTEGER NOT NULL REFERENCES currencies(currencyId),
    exchangeValue DECIMAL NOT NULL,
    lastExchange VARCHAR(50) NOT NULL
);
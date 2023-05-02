DROP DATABASE IF EXISTS test;
CREATE DATABASE test;
\c test

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


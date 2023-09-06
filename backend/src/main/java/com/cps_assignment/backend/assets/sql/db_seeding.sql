USE public;
INSERT INTO currencies (currencysymbol) VALUES
('DKK'),
('SEK'),
('NOK'),
('GBP');

INSERT INTO exchangerates (currencyid, exchangevalue, lastexchange) VALUES
((SELECT currencyid FROM currencies WHERE currencysymbol = 'DKK'), '6.0929','2021-01-01')


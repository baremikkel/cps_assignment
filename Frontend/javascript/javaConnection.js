function getSymbols() {
    $.ajax({
        url: "http://localhost:8080/symbols",
        success: function (data) {
            insertSymbolArr(data.message)
        }
    });
}
function getDates() {
    return new Promise((resolve, reject) => {
      $.ajax({
        url: "http://localhost:8080/exchangedates",
        success: function (data) {
          insertDatesArr(data.message);
          resolve();
        },
        error: function (xhr, ajaxOptions, thrownError) {
          console.log(thrownError);
          reject(thrownError);
        },
      });
    });
  }
  function getRates() {
    return new Promise((resolve, reject) => {
      $.ajax({
        url: "http://localhost:8080/exchangevalues",
        success: function (data) {
          if (data != "") {
            insertRatesArr(data.message);
          }
          resolve();
        },
        error: function (xhr, ajaxOptions, thrownError) {
          console.log(thrownError);
          reject(thrownError);
        },
      });
    });
  }
  


function PostChoice() {
  if (document.getElementById("currencyChoice").value.length == 3) {
    var data = document.getElementById("currencyChoice").value;
    fetch("http://localhost:8080/wantedSymbol", {
      method: "POST",
      body: data,
    })
      .then((response) => response.text())
      .then((data) => {
        return getDates();
      })
      .then((data) => {
        return getRates();
      })
      .then((data) => {
        createChart(datesArr, ratesArr);
      })
      .catch((error) => console.error(error));
  }
}


$(document).ready(function () {
    getSymbols();
});

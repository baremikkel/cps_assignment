function getSymbols() {
    $.ajax({
        url: "http://localhost:8080/symbols",
        success: function (data) {
            getSymbolArr(data.message)
            //$("#currency").html(data.message);
        }
    });
}

function getDates() {
    $.ajax({
        url: "http://localhost:8080/exchangedates",
        success: function (data) {
            getDatesArr(data.message)
            //$("#rate").html(data.message);
        }
    });
}

function getRates() {
    $.ajax({
        url: "http://localhost:8080/exchangevalues",
        success: function (data) {
            if(data!=""){
                getDatesArr(data.message)
            }
            //$("#date").html(data.message);
        }
    });
}

function PostChoice() {
    if (document.getElementById("currencyChoice").value.length == 3) {
        var data = document.getElementById("currencyChoice").value
        fetch('http://localhost:8080/wantedSymbol', {
            method: 'POST',
            body: data
        })
            .then(response => response.text())
            .then(data => console.log(data))
            .catch(error => console.error(error));
    }

}

$(document).ready(function () {
    getSymbols();
    getRates();
    getDates();
});

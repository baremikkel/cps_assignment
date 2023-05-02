function getSymbols() {
    $.ajax({
        url: "http://localhost:8080/symbols",
        success: function(data) {
            getSymbolArr(data.message)
            //$("#currency").html(data.message);
        }
    });
}

function getDates() {
    $.ajax({
        url: "http://localhost:8080/exchangedates",
        success: function(data) {
            $("#rate").html(data.message);
        }
    });
}

function getRates() {
    $.ajax({
        url: "http://localhost:8080/exchangevalues",
        success: function(data) {
            $("#date").html(data.message);
        }
    });
}


$(document).ready(function() {
    getSymbols();
    getRates();
    getDates();
    //addOptions()
});

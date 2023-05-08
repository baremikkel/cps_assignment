let arr;
let datesArr
let ratesArr


function insertSymbolArr(i) {
    var s = i
    arr = s.split(" ");

    const datalist = document.getElementById('currencies');
    const option = document.createElement('option');
    for(let i = 0; i < arr.length; i++)
    {
        //No clue why all these are needed but it don't work without either
        datalist.innerHTML += '<option value"'+arr[i]+'"></option>'
        option.value = arr[i];
        datalist.appendChild(option);
    } 
}

function insertDatesArr(i) {
    datesArr = null
    var s = i
    datesArr = s.split(" ")
}
function insertRatesArr(i) {
    ratesArr = null
    var s = i
    ratesArr = s.split(" ")
}



let arr;

function getSymbolArr(i) {
    var cook = i
    arr = cook.split(" ");
    console.log(arr)

    const datalist = document.getElementById('currencies');
    const option = document.createElement('option');
    console.log(arr)
    for(let i = 0; i < arr.length; i++)
    {
        //No clue why all these are needed but it don't work without either
        datalist.innerHTML += '<option value"'+arr[i]+'"></option>'
        option.value = arr[i];
        datalist.appendChild(option);
    } 
}

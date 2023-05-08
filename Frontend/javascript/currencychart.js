var myChart;
var borderColor = [];

function createChart() {
  displayLatest()
  if (myChart)
    myChart.destroy()
  addColors()

  var ctx = document.getElementById('myChart').getContext('2d');
  myChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: datesArr,
      datasets: [{
        label: '1$ in chosen currency',
        data: ratesArr,
        borderColor: borderColor,
        backgroundColor:'#0e0e0e',
        pointRadius: 0.2
      }]
    },
    options: {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true,
            precision: 1
          }
        }]
      }
    }
  });
}

function addColors(){
  if (datesArr != null) {
    for (var i = 0; i < ratesArr.length; i++) {
      if (i > 0) {
        if (ratesArr[i] >= ratesArr[i + 1]) {

          borderColor.push('#20df4f');
          
        } else {
          borderColor.push('red');
        }
      }
    }
  }
}

function displayLatest(){
  var date = datesArr.slice(-1)
  let i = document.getElementById("latest_rate").textContent = datesArr[datesArr.length-2] + " " + ratesArr[ratesArr.length-2]
}
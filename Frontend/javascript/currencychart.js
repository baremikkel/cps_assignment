var myChart;
var borderColor = [];
var backgroundColor = [];

function createChart() {
  /*document.getElementById('myChart').innerHTML = ""
  var newChart = document.createElement("canvas")
  newChart.id = "myChart"
  newChart.style = "width:100%;max-width:800px"
  document.getElementById('canvas_div').appendChild(newChart)*/
  if (myChart)
    myChart.destroy()
  if (datesArr != null) {
    for (var i = 0; i < ratesArr.length; i++) {
      if (i > 0) {
        if (ratesArr[i] >= ratesArr[i + 1]) {

          borderColor.push('#20df4f');
          backgroundColor.push('#20df4f')
          
        } else {
          borderColor.push('red');
          backgroundColor.push('#red')
        }
      }
    }
  }
  var ctx = document.getElementById('myChart').getContext('2d');
  myChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: datesArr,
      datasets: [{
        label: '1$ in chosen currency',
        data: ratesArr,
        borderColor: borderColor,
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


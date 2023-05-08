/**
 * This file contants everything that has to do with the chart
 * @author Baremikkel, Tiomann99
 */

var chart;
var borderColor = [];

function createChart() {
//
  if (chart)
    chart.destroy()
  addColors()
  displayLatest()
  var ctx = document.getElementById('chart').getContext('2d');
  chart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: datesArr,
      datasets: [{
        label: '1$ in chosen currency',
        data: ratesArr,
        borderColor: borderColor,
        //backgroundColor:'#0e0e0e',
        pointRadius: 0.3
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

function addColors() {
  //Adds color to point depending on if the rate is going up or down
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

function displayLatest() {
  //displays the latest rate on screen for better reading
  document.getElementById("latest_rate").textContent = datesArr[datesArr.length - 2] + ":       " + ratesArr[ratesArr.length - 2]
}
var chart;
var chartData = [];
var chartCursor;

AmCharts.ready(function () {
    // generate some data first
    generateChartData();

    // SERIAL CHART
    chart = new AmCharts.AmSerialChart();
    chart.pathToImages = "../img/";
    chart.zoomOutButton = {
        backgroundColor: '#000000',
        backgroundAlpha: 0.15
    };
    chart.dataProvider = chartData;
    chart.categoryField = "date";
    chart.balloon.bulletSize = 5;

    // listen for "dataUpdated" event (fired when chart is rendered) and call zoomChart method when it happens
    chart.addListener("dataUpdated", zoomChart);

    // AXES
    // category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
    categoryAxis.minPeriod = "DD"; // our data is daily, so we set minPeriod to DD
    categoryAxis.dashLength = 1;
    categoryAxis.gridAlpha = 0.15;
    categoryAxis.autoGridCount = false;
    categoryAxis.gridCount = 10;
    categoryAxis.position = "top";
    categoryAxis.axisColor = "#CACACA";
    categoryAxis.dateFormats = [{
        period: "DD",
        format: "DD"
    }, {
        period: "WW",
        format: "MMM DD"
    }, {
        period: "MM",
        format: "MMM"
    }, {
        period: "YYYY",
        format: "YYYY"
    }];

    // value
    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.axisAlpha = 0.15;
    valueAxis.title = "# of Apps";
    valueAxis.dashLength = 1;
    chart.addValueAxis(valueAxis);

    // GRAPH
    var graph = new AmCharts.AmGraph();
    graph.title = "Apps Ran Over Time";
    graph.valueField = "count";
    graph.bullet = "round";
    graph.bulletBorderColor = "#FFF";
    graph.bulletBorderThickness = 2;
    graph.lineThickness = 2;
    graph.lineColor = "#0098AA";
    graph.negativeLineColor = "#AADDCC";
    graph.hideBulletsCount = 50; // this makes the chart to hide bullets when there are more than 50
    chart.addGraph(graph);

    // CURSOR
    chartCursor = new AmCharts.ChartCursor();
    chartCursor.cursorPosition = "mouse";
    chartCursor.pan = true; // set it to false if you want the cursor to work in "select" mode
    chart.addChartCursor(chartCursor);

    // SCROLLBAR
    var chartScrollbar = new AmCharts.ChartScrollbar();
    chartScrollbar.graph = graph;
    chartScrollbar.autoGridCount = true;
    chartScrollbar.scrollbarHeight = 25;
    chart.addChartScrollbar(chartScrollbar);

    // WRITE
    chart.write("chartdiv");
});

function generateChartData() {

    dates = [];
    count = {};

    var request = new XMLHttpRequest();
    request.open("GET", "/get-all-apps");

    request.onreadystatechange = function() {
        if (request.readyState == 4) {
            var response = JSON.parse(request.responseText);

            function formatDate (element){
                var utcSeconds = element;
                var d = new Date(element * 1).toDateString();

                dates.push(d);
            }
            //console.log(dates);
            response.forEach(formatDate);

            for(var i = 0; i < dates.length; i++)
                count[dates[i]] = (count[dates[i]] || 0) + 1
        }
    }
    request.send();

    len = Object.keys(count).length;
    for (var i = 0; i < len; i++){
        var key = Object.keys(count)[i];
        console.log(Object.keys(count)[i]);
        chartData.push({
            date: Date(key),
            count: count[key]
        });
    }
        //console.log(chartData);

    // generate some random data, quite different range
    /*var firstDate = new Date();
    firstDate.setDate(firstDate.getDate() - 500);

    for (var i = 0; i < 500; i++) {
        var newDate = new Date(firstDate);
        newDate.setDate(newDate.getDate() + i);

        var apps = Math.round(Math.random() * 40);

        chartData.push({
            date: newDate,
            count: apps
        });
    }*/
}

// this method is called when chart is first inited as we listen for "dataUpdated" event
function zoomChart() {
    // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
    chart.zoomToIndexes(chartData.length - 40, chartData.length - 1);
}

// changes cursor mode from pan to select
function setPanSelect() {
    if (document.getElementById("rb1").checked) {
        chartCursor.pan = false;
        chartCursor.zoomable = true;
    } else {
        chartCursor.pan = true;
    }
    chart.validateNow();
}

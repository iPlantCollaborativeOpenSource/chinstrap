var chart;
var chartData = [];
var monthNames = [ "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December" ];

AmCharts.ready(function () {

    generateChartData();

    // SERIAL CHART
    chart = new AmCharts.AmSerialChart();
    chart.dataProvider = chartData;
    chart.categoryField = "date";
    chart.startDuration = 1;
    chart.balloon.adjustBorderColor = true;
    chart.balloon.fillColor = "#db6619";
    chart.balloon.borderThickness = 0;
    chart.balloon.cornerRadius = 3;

    // AXES
    // category
    var categoryAxis = chart.categoryAxis;
    categoryAxis.labelRotation = 65;
    //categoryAxis.parseDates = true;
    //categoryAxis.minPeriod = "MM";
    categoryAxis.gridAlpha = 0.07;
    categoryAxis.axisColor = "#CACACA";
    //categoryAxis.dateFormats = [{period: "MM", format: "MMM YYYY"}]

    // GRAPH
    var graph = new AmCharts.AmGraph();
    graph.valueField = "count";
    graph.balloonText = "[[category]]: [[value]]";
    graph.type = "column";
    graph.lineAlpha = 0;
    graph.lineColor = "#0098AA";
    graph.fillAlphas = 0.8;
    chart.addGraph(graph);

    chart.write("chart");
});

function generateChartData() {

    var response;
    request = $.ajax({
        url: "/get-month-data/Completed",
        async: false,
        contentType: "application/json",
        success: function(data){
            response = data;
        }
    });

    response.forEach(pushData);
    function pushData (element) {
            date = new Date(element['date']);
            primedDate = monthNames[date.getMonth()] + " " + date.getFullYear();
        chartData.push({
            date: primedDate,
            count: element['count']});
    }
}
